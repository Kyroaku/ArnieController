package myapp.arniecontroller;

/**
 * Created by Marcin Dziedzic on 2017-11-06.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth
{
    BluetoothAdapter mAdapter;
    Handler handler;
    Context context;

    public Bluetooth(Context context) {
        this.context = context;
        handler = new Handler(context.getMainLooper());

        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            toast("No bluetooth adapter");
            Log.e("Bluetooth", "No bluetooth adapter");
        } else if (!mAdapter.isEnabled()) {
            toast("Bluetooth dissabled");
            Log.e("Bluetooth", "Bluetooth disabled");
        }
    }

    public BluetoothSocket Accept()
    {
        BluetoothServerSocket server = null;
        try {
            server = mAdapter.listenUsingRfcommWithServiceRecord(
                    context.getString(R.string.app_name),
                    UUID.fromString("b2b097ed-d105-4cb6-8d03-4809d7efeda4")
            );
        } catch (IOException e) {
            Log.e("Bluetooth", "Server error: " + e.getMessage());
        }

        BluetoothSocket socket = null;
        try {
            socket = server.accept();
        }
        catch (IOException e) {
            Log.e("Bluetooth", "Accept error: " + e.getMessage());
        }
        try {
            server.close();
        } catch (IOException e) {
            Log.e("Bluetooth", "Close error: " + e.getMessage());
        }

        return socket;
    }

    public BluetoothSocket Connect(String name)
    {
        BluetoothSocket socket = null;
        Set<BluetoothDevice> devices = mAdapter.getBondedDevices();
        BluetoothDevice device = null;
        for(BluetoothDevice d : devices)
            if(d.getName().equals(name))
            {
                device = d;
                break;
            }
        if(device == null) {
            toast("No device named '"+name+"'.");
            Log.i("Bluetooth", "No device named '"+name+"'");
            return null;
        }
        try {
            socket = device.createRfcommSocketToServiceRecord(
                    //UUID.fromString("b2b097ed-d105-4cb6-8d03-4809d7efeda4")
                    UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            );
            socket.connect();
        }
        catch (IOException e) {
            Log.e("Bluetooth", "Connect error: " + e.getMessage());
            toast("Connect error.");
            socket = null;
            try {
                socket.close();
            } catch (IOException e2) {
                Log.e("Bluetooth", "Close error: " + e2.getMessage());
            }
        }
        return socket;
    }

    public void Send(BluetoothSocket socket, byte[] data)
    {
        OutputStream stream = null;
        try
        {
            stream = socket.getOutputStream();
            stream.write(data);
        }
        catch (IOException e) {
            Log.i("Bluetooth", "Send error: " + e.getMessage());
        }
    }


    void Recv(BluetoothSocket socket, byte[] data)
    {
        InputStream stream = null;
        try
        {
            stream = socket.getInputStream();
            stream.read(data);

        }
        catch (IOException e) {
            Log.i("Bluetooth", "Receive error: " + e.getMessage());
        }
    }

    private void toast(final String text)
    {
        handler.post(new Runnable() {
            public void run()
            {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
