package myapp.arniecontroller;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Marcin Dziedzic on 2017-10-25.
 */

public class Wifi {
    public static final int SERVER_PORT = 55555;

    private static ServerSocket mServerSocket;
    private static Socket mSocket;

    // Disable instances.
    private Wifi() {}

    /**
     * Open server. New thread is created.
     */
    public static void OpenServer() {
        try {
            mServerSocket = new ServerSocket(0);
            Log.i("Socket", "Server openned (" + mServerSocket.getLocalSocketAddress().toString() + ")");
        } catch(Exception e) {
            Log.e("Socket", "Server error (" + e.getMessage() + ")");
            try {
                if(mServerSocket != null && !mServerSocket.isClosed()) {
                    mServerSocket.close();
                    mServerSocket = null;
                }
            } catch(Exception e2) {
                Log.e("Socket", "Close error (" + e2.getMessage() + ")");
            }
        }
    }

    /**
     * Wait for client connected.
     */
    public static void Accept() {
        try {
            mSocket = mServerSocket.accept();
            Log.i("Socket", "Client accepted");
        } catch(Exception e) {
            Log.e("Socket", "Accept error (" + e.getMessage() + ")");
            try {
                if(mSocket != null && !mSocket.isClosed())
                    mSocket.close();
            } catch(Exception e2) {
                Log.e("Socket", "Close error (" + e2.getMessage() + ")");
            }
        }
    }

    /**
     * Connect to server. New thread is created.
     */
    public static void Connect(final String ip, final int port) {
        try {
            mSocket = new Socket();
            mSocket.setReuseAddress(true);
            mSocket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
            Log.i("Socket", "Connected to server");
        } catch(Exception e) {
            if(e.getMessage() != null)
                Log.e("Socket", e.getMessage());
            else
                Log.e("Socket", "Socket error.");
        }
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
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(mSocket.getOutputStream());
        } catch(IOException e) {
            Log.e("Socket", "Send error (" + e.getMessage() + ")");
        }
        try {
            oos.writeObject(obj);
        } catch(IOException e) {
            Log.e("Socket", "Send error (" + e.getMessage() + ")");
        }
    }

    /**
     * Check whether socket is connected to server.
     * @return true - if connected, false - otherwise.
     */
    public static boolean IsConnected() {
        if(mSocket != null)
            return mSocket.isConnected();
        else
            return false;
    }

    /**
     * Check whether server socket is open.
     * @return tru - if open, false - otherwise.
     */
    public static boolean IsServerOpen() {
        return (mServerSocket != null);
    }

    public static String GetIp() {
        return mServerSocket.getInetAddress().getHostAddress();
    }

    public static int GetPort() {
        return mServerSocket.getLocalPort();
    }
}
