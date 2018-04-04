package myapp.arniecontroller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.widget.TextView;


/**
 * Created by hmaciejczyk on 2018-02-18.
 */

public class JoystickActivity extends Activity implements JoystickView.JoystickListener, SliderView.SliderListener {
    private TextView joystickXTextView;
    private TextView joystickYTextView;
    private TextView sliderXTextView;
    private TextView sliderYTextView;
    private JoystickView joystick;
    private SliderView slider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jostick_control);

        joystickXTextView = (TextView) findViewById(R.id.joysticAngleTextView);
        joystickYTextView = (TextView) findViewById(R.id.joysticPowerTextView);
        sliderXTextView = (TextView) findViewById(R.id.sliderAngleTextView);
        sliderYTextView = (TextView) findViewById(R.id.sliderPowerTextView);

        joystickXTextView.setText(getString(R.string.joy_contr_text1) + " 0");
        joystickYTextView.setText(getString(R.string.joy_contr_text2) + " 0");
        sliderXTextView.setText(getString(R.string.slid_contr_text1) + " 0");
        sliderXTextView.setText(getString(R.string.slid_contr_text2) + " 0");


        new Thread(new Runnable() {
            @Override
            public void run() {
                Wifi.Connect(Settings.ServerIp, Settings.ServerPort);
            }
        }).start();


    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        joystickXTextView.setText(getString(R.string.joy_contr_text1) + " " + xPercent);
        joystickYTextView.setText(getString(R.string.joy_contr_text2) + " " + yPercent);

                    /* Send angles. */
        final int a1 = (int)(Math.atan2(yPercent,xPercent) * (180/Math.PI));
        final int a2 = (int) (180 * Math.sqrt((xPercent * xPercent) + (yPercent * yPercent)));
        final int a3 = 180;
        final int a4 = 0;
        final FrameAngles frame = new FrameAngles(a1, a2, a3, a4);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Wifi.Send(frame.ToByteArray());
            }
        }).start();
    }


    @Override
    public void onSliderMoved(float xPercent, float yPercent, int source) {
        sliderXTextView.setText(getString(R.string.joy_contr_text1) + " " + xPercent);
        sliderYTextView.setText(getString(R.string.joy_contr_text2) + " " + yPercent);
    }
}

