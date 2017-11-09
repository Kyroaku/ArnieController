package myapp.arniecontroller;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Marcin Dziedzic on 2017-10-26.
 */

public class SimulatorActivity extends Activity {

    TextView mTextReceived, mTextAddress;
    ImageView mImgServo[];

    Thread mThreadReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulator);

        mTextAddress = (TextView)findViewById(R.id.textAddress);
        mTextReceived = (TextView)findViewById(R.id.textReceived);

        mImgServo = new ImageView[3];
        mImgServo[0] = (ImageView) findViewById(R.id.imgServoHorn1);
        mImgServo[1] = (ImageView) findViewById(R.id.imgServoHorn2);
        mImgServo[2] = (ImageView) findViewById(R.id.imgServoHorn3);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        // Open server and accept client.
        Thread openThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Wifi.OpenServer();
            }
        });
        openThread.start();
        try {
            openThread.join();
        } catch(Exception e) {
            Log.e("Socket", "Open server error (" + e.getMessage() + ")");
        }

        mTextAddress.setText("Address: " + ip + ":" + Wifi.GetPort());

        // Create thread to receive data.
        mThreadReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    // Do nothint if not connected.
                    if(!Wifi.IsConnected()) {
                        try {
                            Wifi.Accept();
                        } catch (Exception e) {
                            Log.i("Socket", "Accept error (" + e.getMessage() + ")");
                        }
                        continue;
                    }
                    // If received data, update receveived text on layout.
                    final Frame frame = (Frame) Wifi.ReceiveObject();
                    if(frame != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImgServo[0].setRotation(((FrameAngles)frame).Angle1()-45);
                                mImgServo[1].setRotation(((FrameAngles)frame).Angle2()-45);
                                mImgServo[2].setRotation(((FrameAngles)frame).Angle3()-45);
                            }
                        });
                    }
                }
            }
        });
        mThreadReceive.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //mThreadReceive.stop();
    }
}
