package myapp.arniecontroller;

import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    /**
     * Open server and wait for new client. New thread is created.
     */
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

    /**
     * Connect to server. New thread is created.
     */
    public static void Connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket();
                    mSocket.setReuseAddress(true);
                    mSocket.connect(new InetSocketAddress("127.0.0.1", SERVER_PORT));
                    Log.i("Socket", "Connected to server");
                } catch(Exception e) {
                    if(e.getMessage() != null)
                        Log.e("Socket", e.getMessage());
                    else
                        Log.e("Socket", "Socket error.");
                }
            }
        }).start();

    }

    /**
     * Receive data from client/server. This method can't be called from UI thread.
     * @param buffer Received data.
     * @return Length of received data.
     */
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

    public static Object ReceiveObject() {
        try {
            ObjectInputStream ois = new ObjectInputStream(mSocket.getInputStream());
            return ois.readObject();
        } catch(Exception e) {
            Log.e("Socket", "Receive error (" + e.getMessage() + ")");
        }
        return null;
    }

    /**
     * Send data to client/server. This method can't be called from UI thread.
     * @param buffer Data to send.
     */
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

    public static void Send(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mSocket.getOutputStream());
            oos.writeObject(obj);
        } catch(Exception e) {
            Log.e("Socket", "Send error (" + e.getMessage() + ")");
        }
    }

    /**
     * Check whether socket is connected.
     * @return true - if connected, false - otherwise.
     */
    public static boolean IsConnected() {
        if(mSocket != null)
            return mSocket.isConnected();
        else
            return false;
    }
}
