package myapp.arniecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button buttonSimpleControl;
    Button buttonJoystickControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        ButtonCallback buttonCallback = new ButtonCallback();

        buttonSimpleControl = (Button) findViewById(R.id.buttonSimpleControl);
        buttonSimpleControl.setOnClickListener(buttonCallback);

        buttonJoystickControl = (Button) findViewById(R.id.buttonJoystickControl);
        buttonJoystickControl.setOnClickListener(buttonCallback);

//        WifiManager mainWifiObj;
//        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        //funkcja do skania listy sieci bezprzewodowych
//        class WifiScanReceiver extends BroadcastReceiver {
//            public void onReceive(Context c, Intent intent) {
//            }
//        }
//
//        WifiScanReceiver wifiReciever = new WifiScanReceiver();
//        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private class ButtonCallback implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonSimpleControl:
                    try {
                        startActivity(new Intent(MainActivity.this, SimpleControlActivity.class));
            }
                    catch(Exception e) {
                        Log.e("startActivity()", e.getMessage());
                    }
                    break;

                case R.id.buttonJoystickControl:
                    //Toast.makeText(getApplicationContext(), "Not implemented!", Toast.LENGTH_SHORT).show();
                    try{
                        startActivity(new Intent(MainActivity.this, jostick_control.class));
                    }
                    catch(Exception e) {
                        Log.e("startActivity()", e.getMessage());
                    }
                    break;
            }
        }
    }
}
