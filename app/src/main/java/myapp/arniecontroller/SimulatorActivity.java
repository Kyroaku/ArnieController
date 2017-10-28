package myapp.arniecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

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
                // Data buffer.
                final byte[] buffer = new byte[256];
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
                    if(WifiManager.Receive(buffer) > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String text = "";
                                try {
                                    text = new String(buffer, "UTF-8");
                                } catch(Exception e) {

                                }
                                mTextReceived.setText(mTextReceived.getText() + "\n" + text);
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
                                mSocket.getOutputStream().write(mTextToSend.getText().toString().getBytes());
                            } catch(Exception e) {

                            }
                        }
                    }).start();
                    break;
            }
        }
    }
}
