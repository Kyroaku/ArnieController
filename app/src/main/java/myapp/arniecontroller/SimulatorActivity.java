package myapp.arniecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Marcin Dziedzic on 2017-10-26.
 */

public class SimulatorActivity extends Activity {

    EditText mTextToSend;
    TextView mTextReceived;
    Button mButtonSend;
    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulator);

        ButtonCallback buttonCallback = new ButtonCallback();

        mTextToSend = (EditText)findViewById(R.id.textToSend);
        mTextReceived = (TextView)findViewById(R.id.textReceived);
        mButtonSend = (Button)findViewById(R.id.buttonSend);
        mButtonSend.setOnClickListener(buttonCallback);

        // Open server and accept client.
        WifiManager.OpenServer();

        // Create thread to receive data.
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    // Do nothint if not connected.
                    if(!WifiManager.IsConnected()) {
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                        }
                        continue;
                    }
                    // If received data, update receveived text on layout.
                    final FrameAngles frame = (FrameAngles)WifiManager.ReceiveObject();
                    if(frame != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String text = "Frame ==========\n";
                                text += "Command=" + String.valueOf(frame.Command()) + "\n";
                                text += "Angles=[" + String.valueOf(frame.Angle1()) + ", ";
                                text += String.valueOf(frame.Angle2()) + ", ";
                                text += String.valueOf(frame.Angle3()) + "]\n";
                                text += "================\n";
                                mTextReceived.setText(mTextReceived.getText() + text);
                            }
                        });
                    }
                }
            }
        }).start();

        // Connect to server.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket();
                    mSocket.setReuseAddress(true);
                    mSocket.connect(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 1440));
                } catch(Exception e) {
                    Log.e("Socket", "Can't connect to server. " + "(" + e.getMessage() + ")");
                }
            }
        }).start();
    }

    /**
     * Callback class for all buttons in main menu.
     */
    private class ButtonCallback implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonSend:
                    // Send message from EditText.
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FrameAngles frame = new FrameAngles();
                                Scanner scan = new Scanner(mTextToSend.getText().toString());
                                frame.Angles(scan.nextByte(), scan.nextByte(), scan.nextByte());
                                ObjectOutputStream oos = new ObjectOutputStream(mSocket.getOutputStream());
                                oos.writeObject(frame);
                            } catch(Exception e) {

                            }
                        }
                    }).start();
                    break;
            }
        }
    }
}
