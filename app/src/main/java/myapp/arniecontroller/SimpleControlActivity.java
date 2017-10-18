package myapp.arniecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import static myapp.arniecontroller.R.id.seekBar;

/**
 * Created by Kyroaku on 2017-10-18.
 */

public class SimpleControlActivity extends Activity {

    SeekBar seekbar;
    SeekBar seekbar2;
    SeekBar seekbar3;
    TextView textView;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_control);

        SeekBar.OnSeekBarChangeListener seekBarCallback = new SeekBarCallback();

        seekbar = (SeekBar) findViewById(seekBar);
        seekbar.setMax(180);
        seekbar.setOnSeekBarChangeListener(seekBarCallback);

        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekbar2.setMax(180);
        seekbar2.setOnSeekBarChangeListener(seekBarCallback);

        seekbar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekbar3.setMax(180);
        seekbar3.setOnSeekBarChangeListener(seekBarCallback);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("0");

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("0");

        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("0");
    }

    private class SeekBarCallback implements SeekBar.OnSeekBarChangeListener
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekBar:
                    textView.setText(String.format(Locale.getDefault(), "%d", progress));
                    break;

                case R.id.seekBar2:
                    textView2.setText(String.format(Locale.getDefault(), "%d", progress));
                    break;

                case R.id.seekBar3:
                    textView3.setText(String.format(Locale.getDefault(), "%d", progress));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
