package myapp.arniecontroller;

import android.util.Log;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Marcin Dziedzic on 2017-10-25.
 */

public class WifiManager {
    public static final int SERVER_PORT = 1440;

    private static ServerSocket mServerSocket;
    private static Socket mSocket;

    // Disable instances.
    private WifiManager() {}

    public static void OpenServer() {
        Thread acceptThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mServerSocket = new ServerSocket();
                    mServerSocket.setReuseAddress(true);
                    mServerSocket.bind(new InetSocketAddress(SERVER_PORT));
                    mServerSocket.setSoTimeout(3000);
                    mSocket = mServerSocket.accept();
                } catch(Exception e) {
                    Log.e("Socket", e.getMessage());
                    try {
                        if(mServerSocket != null && !mServerSocket.isClosed())
                            mServerSocket.close();
                        if(mSocket != null && !mSocket.isClosed())
                            mSocket.close();
                    } catch(Exception e2) {
                        Log.e("Socket", e2.getMessage());
                    }
                }

            }
        });
        acceptThread.start();
        try {
            acceptThread.join();
        } catch(Exception e) {
            Log.e("Thread", e.getMessage());
        }
    }

    public static void Connect() {
        try {
            mSocket = new Socket("127.0.0.1", SERVER_PORT);
        } catch(Exception e) {
            e.printStackTrace();
            Log.e("Socket", "Can't connect: " + e.getMessage());
        }
    }
}
