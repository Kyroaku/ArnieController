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
                    mServerSocket.bind(new InetSocketAddress("127.0.0.1", SERVER_PORT));
                    Log.i("Socket", "Server openned");
                    mServerSocket.setSoTimeout(3000);
                    mSocket = mServerSocket.accept();
                    Log.i("Socket", "Client accepted");
                } catch(Exception e) {
                    Log.e("Socket", "Server error (" + e.getMessage() + ")");
                    try {
                        if(mServerSocket != null && !mServerSocket.isClosed())
                            mServerSocket.close();
                        if(mSocket != null && !mSocket.isClosed())
                            mSocket.close();
                    } catch(Exception e2) {
                        Log.e("Socket", "Close error (" + e2.getMessage() + ")");
                    }
                }

            }
        });
        acceptThread.start();
    }

    public static void Connect() {
        try {
            mSocket = new Socket("127.0.0.1", SERVER_PORT);
        } catch(Exception e) {
            if(e.getMessage() != null)
                Log.e("Socket", e.getMessage());
            else
                Log.e("Socket", "Socket error.");
        }
    }

    public static int Receive(byte[] buffer) {
        int received = 0;
        try {
            received = mSocket.getInputStream().read(buffer);
        } catch(Exception e) {
            if(e.getMessage() != null)
                Log.e("Socket", e.getMessage());
            else
                Log.e("Socket", "Receive error.");
        }
        return received;
    }

    public static void Send(byte[] buffer) {
        try {
            mSocket.getOutputStream().write(buffer);
        } catch(Exception e) {
            if (e.getMessage() != null)
                Log.e("Socket", e.getMessage());
            else
                Log.e("Socket", "Send error.");
        }
    }

    public static boolean IsConnected() {
        if(mSocket != null)
            return mSocket.isConnected();
        else
            return false;
    }
}
