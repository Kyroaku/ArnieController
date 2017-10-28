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
    Button buttonSimulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Create callback for all buttons.
        ButtonCallback buttonCallback = new ButtonCallback();

        // Set callback to each button.
        buttonSimpleControl = (Button) findViewById(R.id.buttonSimpleControl);
        buttonSimpleControl.setOnClickListener(buttonCallback);

        buttonJoystickControl = (Button) findViewById(R.id.buttonJoystickControl);
        buttonJoystickControl.setOnClickListener(buttonCallback);

        buttonSimulator = (Button) findViewById(R.id.buttonSimulator);
        buttonSimulator.setOnClickListener(buttonCallback);
    }

    /**
     * Callback class for all buttons in main menu.
     */
    private class ButtonCallback implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // Simple control button.
                case R.id.buttonSimpleControl:
                    try {
                        startActivity(new Intent(MainActivity.this, SimpleControlActivity.class));
                    }
                    catch(Exception e) {
                        Log.e("startActivity()", e.getMessage());
                    }
                    break;

                // Joystick control button.
                case R.id.buttonJoystickControl:
                    //Toast.makeText(getApplicationContext(), "Not implemented!", Toast.LENGTH_SHORT).show();
                    try {
                        startActivity(new Intent(MainActivity.this, jostick_control.class));
                    }
                    catch(Exception e) {
                        Log.e("startActivity()", e.getMessage());
                    }
                    break;

                // Simulator button.
                case R.id.buttonSimulator:
                    try {
                        startActivity(new Intent(MainActivity.this, SimulatorActivity.class));
                    }
                    catch(Exception e) {
                        Log.e("startActivity()", e.getMessage());
                    }
                    break;
            }
        }
    }
}
