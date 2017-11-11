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
    private static ServerSocket mServerSocket;
    private static Socket mSocket;
    private static ObjectOutputStream mObjectOutputStream;
    private static ObjectInputStream mObjectInputStream;

    /**
     * Opens server on random, free port.
     */
    public static void OpenServer() {
        try {
            mServerSocket = new ServerSocket(55555);
            Log.d("Socket", "Server openned (" + mServerSocket.getLocalSocketAddress().toString() + ")");
        } catch(Exception e) {
            Log.e("Socket", "Server error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseServer();
        }
    }

    /**
     * Waits for new client.
     */
    public static void Accept() {
        try {
            mSocket = mServerSocket.accept();
            mObjectInputStream = new ObjectInputStream(mSocket.getInputStream());
            mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
            Log.d("Socket", "Client accepted (" + mSocket.getLocalAddress().toString() + ")");
        } catch(Exception e) {
            Log.e("Socket", "Accept error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseClient();
        }
    }

    /**
     * Connects to server with specified IP address and port number.
     * @param ip Server's IP addres.
     * @param port Server's port number.
     */
    public static void Connect(final String ip, final int port) {
        try {
            mSocket = new Socket();
            mSocket.setReuseAddress(true);
            mSocket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
            mObjectOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
            Log.d("Socket", "Connected to server");
        } catch(Exception e) {
            Log.e("Socket", "Connect error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseClient();
        }
    }

    /**
     * Receives data from connected socket.
     * @param buffer Received data.
     * @return Length of received data.
     */
    public static int Receive(byte[] buffer) {
        int received = 0;
        try {
            received = mSocket.getInputStream().read(buffer);
        } catch(Exception e) {
            Log.e("Socket", "Receive error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseClient();
        }
        return received;
    }

    /**
     * Receives serialized object from connected socket.
     * @return Received object.
     */
    public static Object ReceiveObject() {
        try {
            return mObjectInputStream.readObject();
        } catch(Exception e) {
            Log.e("Socket", "Receive error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseClient();
        }
        return null;
    }

    /**
     * Sends data to connected socket.
     * @param buffer Data to send.
     */
    public static void Send(byte[] buffer) {
        try {
            mSocket.getOutputStream().write(buffer);
        } catch(Exception e) {
            Log.e("Socket", "Send error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseClient();
        }
    }

    /**
     * Sends serialized object to connected socket.
     * @param obj Object to send.
     */
    public static void Send(Object obj) {
        try {
            mObjectOutputStream.writeObject(obj);
        } catch(IOException e) {
            Log.e("Socket", "Send error (" + e.getMessage() + ")");
            e.printStackTrace();
            CloseClient();
        }
    }

    /**
     * Checks whether socket is properly connected to server.
     * Method will also return false, if socket is connected, but couldn't create ObjectOutputStream.
     *
     * @return true - if connected, false - otherwise.
     */
    public static boolean IsConnected() {
        return (mSocket != null && mObjectOutputStream != null);
    }

    /**
     * Checks whether server socket is open.
     * @return true - if open, false - otherwise.
     */
    public static boolean IsServerOpen() {
        return (mServerSocket != null && !mServerSocket.isClosed());
    }

    /**
     * Closes both client and server socket.
     */
    public static void Close() {
        CloseClient();
        CloseServer();
        Log.d("Wifi", "Closed");
    }

    /**
     * Close client socket and all object streams.
     */
    public static void CloseClient() {
        try {
            if(mObjectOutputStream != null) {
                mObjectOutputStream.close();
                mObjectOutputStream = null;
            }
            if(mObjectInputStream != null) {
                mObjectInputStream.close();
                mObjectInputStream = null;
            }
            if(mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
            Log.d("Socket", "Client closed");
        } catch (Exception e) {
            Log.e("Socket", "Close error (" + e.getMessage() + ")");
            e.printStackTrace();
        }
    }

    /**
     * Closes server socket.
     */
    public static void CloseServer() {
        try {
            if(mServerSocket != null) {
                mServerSocket.close();
                mServerSocket = null;
                Log.d("Socket", "Server closed");
            }
        } catch (Exception e) {
            Log.e("Socket", "Close error (" + e.getMessage() + ")");
            e.printStackTrace();
        }
    }

    /**
     * Returns server's address as a string.
     * @return Server's address as a string.
     */
    public static String GetIp() {
        return mServerSocket.getInetAddress().getHostAddress();
    }

    /**
     * Returns server's port number.
     * @return Server's port number.
     */
    public static int GetPort() {
        return mServerSocket.getLocalPort();
    }
}
