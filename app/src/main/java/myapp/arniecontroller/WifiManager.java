package myapp.arniecontroller;

import android.util.Log;

import java.net.ServerSocket;

/**
 * Created by Marcin Dziedzic on 2017-10-25.
 */

public class WifiManager {
    ServerSocket mServerSocket;

    public WifiManager() {
        try {
            mServerSocket = new ServerSocket(1440);

        } catch(Exception e) {
            Log.e("ServerSocket", e.getMessage());
        }
    }
}
