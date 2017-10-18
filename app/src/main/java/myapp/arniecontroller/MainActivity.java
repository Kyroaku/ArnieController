package myapp.arniecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import static myapp.arniecontroller.R.id.textView4;
//


public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;
    SeekBar seekbar2;
    SeekBar seekbar3;
    TextView textView;
    TextView textView2;
    TextView textView3;
    int progress1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setMax(180);
        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekbar2.setMax(180);
        seekbar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekbar3.setMax(180);
        textView= (TextView) findViewById(R.id.textView2);
        textView.setText("" + progress1);
        textView2= (TextView) findViewById(R.id.textView3);
        textView2.setText("" + progress1);
        textView3= (TextView) findViewById(textView4);
        textView3.setText("" + progress1);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1=progress;
                textView.setText("" + progress1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1=progress;
                textView3.setText("" + progress1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1=progress;
                textView2.setText("" + progress1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //funkcja do skania listy sieci bezprzewodowych
        class WifiScanReceiver extends BroadcastReceiver {
            public void onReceive(Context c, Intent intent) {
            }
        }

        WifiScanReceiver wifiReciever = new WifiScanReceiver();
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }
}
