package myapp.arniecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static myapp.arniecontroller.R.id.seekBar;

/**
 * Created by Marcin Dziedzic on 2017-10-18.
 */

public class SimpleControlActivity extends Activity {

    SeekBar seekbar;
    SeekBar seekbar2;
    SeekBar seekbar3;
    TextView textView;
    TextView textView2;
    TextView textView3;
    Spinner spinnerMoveSequences;
    List<MoveSequence>moveSequences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_control);

        // Create callback for all seek bars.
        SeekBar.OnSeekBarChangeListener seekBarCallback = new SeekBarCallback();

        moveSequences = new ArrayList<>();
        moveSequences.add(new MoveSequence("Sequence 1"));

        spinnerMoveSequences = (Spinner)findViewById(R.id.spinnerMoveSequences);
        ArrayAdapter<MoveSequence> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, moveSequences
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMoveSequences.setAdapter(adapter);

        // Set callback to each seek bar.
        seekbar = (SeekBar) findViewById(seekBar);
        seekbar.setMax(180);
        seekbar.setOnSeekBarChangeListener(seekBarCallback);

        seekbar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekbar2.setMax(180);
        seekbar2.setOnSeekBarChangeListener(seekBarCallback);

        seekbar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekbar3.setMax(180);
        seekbar3.setOnSeekBarChangeListener(seekBarCallback);

        // Init text views.
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("0");

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("0");

        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("0");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Wifi.Connect(Settings.ServerIp, Settings.ServerPort);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Wifi.Close();
    }

    /**
     * Callback class for all seek bars in simple control.
     */
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

            final int a1 = seekbar.getProgress();
            final int a2 = seekbar2.getProgress();
            final int a3 = seekbar3.getProgress();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Wifi.Send(new FrameAngles(a1, a2, a3));
                }
            }).start();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
