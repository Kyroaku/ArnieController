package myapp.arniecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button buttonSimpleControl;
    Button buttonJoystickControl;
    Button buttonSimulator;
    EditText editIp, editPort;

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

        editIp = (EditText) findViewById(R.id.textIp);
        editIp.setText(Settings.ServerIp);

        editPort = (EditText) findViewById(R.id.textPort);
        editPort.setText(String.valueOf(Settings.ServerPort));

        //Bluetooth bt = new Bluetooth(this);
        //bt.Connect("SASHA");
    }

    /**
     * Callback class for all buttons in main menu.
     */
    private class ButtonCallback implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Save address settings if any button has been clicked.
            Settings.ServerIp = editIp.getText().toString();
            Settings.ServerPort = Integer.valueOf(editPort.getText().toString());
            Log.i("Settings", "ServerIp="+Settings.ServerIp+", ServerPort="+Settings.ServerPort);

            switch (v.getId()) {
                // Simple control button.
                case R.id.buttonSimpleControl:
                    try {
                        MoveSequence.ResetNumInstances();
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
                        startActivity(new Intent(MainActivity.this, JoystickControlActivity.class));
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
