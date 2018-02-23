package myapp.arniecontroller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


/**
 * Created by hmaciejczyk on 2018-02-18.
 */

public class JoystickActivity extends Activity implements JoystickView.JoystickListener {
    private TextView angleTextView;
    private TextView powerTextView;
    private TextView directionTextView;
    private JoystickView joystick;
    private SliderView slider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jostick_control);
        angleTextView = (TextView) findViewById(R.id.angleTextView);
        powerTextView = (TextView) findViewById(R.id.powerTextView);
        angleTextView.setText(getString(R.string.joy_contr_text1) + " 0");
        powerTextView.setText(getString(R.string.joy_contr_text2) + " 0");
        //JoystickView joystickView = new JoystickView(this);
        //SliderView sliderView = new SliderView(this);

    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        angleTextView.setText(getString(R.string.joy_contr_text1) + " " + xPercent);
        powerTextView.setText(getString(R.string.joy_contr_text2) + " " + yPercent);
    }
}

