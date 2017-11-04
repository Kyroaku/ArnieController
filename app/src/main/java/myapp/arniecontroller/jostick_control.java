package myapp.arniecontroller;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Szymon on 21.10.2017.
 */

public class jostick_control extends Activity implements View.OnTouchListener {
    float joystickX = 0;
    float joystickY = 0;
    float joystickR = 100.0f;

    ImageView imageJoystick1;
    TextView textXY;
    View screenView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jostick_control);

        // Find views.
        textXY = (TextView) this.findViewById(R.id.textXY);

        imageJoystick1 = (ImageView) this.findViewById(R.id.imageJoystick1);

        screenView = findViewById(R.id.joystickScreenView);

        // Set touch listener to main layout (it is on whole screen, so we have global position of touch).
        screenView.setOnTouchListener(this);

        // Observer needed to wait for layout inizialization finished.
        screenView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                screenView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                // Set position set in designer as a position of joystick.
                Rect rect = new Rect();
                imageJoystick1.getGlobalVisibleRect(rect);
                joystickX = rect.left + imageJoystick1.getWidth() / 2;
                joystickY = rect.top + imageJoystick1.getHeight() / 2;
                screenView.getGlobalVisibleRect(rect);
                joystickX -= rect.left;
                joystickY -= rect.top;

                joystickR = Math.min(screenView.getHeight(), screenView.getWidth()) / 10;
            }
        });
    }

    /**
     * On Touch method - update joysticks.
     */
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX(); /* touch x position. */
        float y = event.getY(); /* touch y position. */
        float dx = 0;           /* joystick x axis. */
        float dy = 0;           /* joystick y axis. */

        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                /* Calculate distance between touch and joystick base position. */
                dx = x - joystickX;
                dy = y - joystickY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                /* If touch outside joystick area. */
                if (distance > joystickR) {
                    /* Move joystick position to edge of joystick area, where distance==joystickR. */
                    float ratio = joystickR / distance;
                    x = (x - joystickX) * ratio + joystickX;
                    y = (y - joystickY) * ratio + joystickY;
                }
            }
            break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                /* Set joystick position to base joystick position, when finger isn't on screen. */
                x = joystickX;
                y = joystickY;
            }
            break;
        }

        /* Set joystick image at new position. */
        imageJoystick1.setX(x - imageJoystick1.getWidth() / 2);
        imageJoystick1.setY(y - imageJoystick1.getHeight() / 2);

        /* Calculate joystick axes. */
        dx = x - joystickX;
        dy = y - joystickY;
        /* Normalize joystick axes. */
        dx /= joystickR;
        dy /= joystickR;

        /* Update text with axes info. */
        textXY.setText(String.format(Locale.getDefault(), "x=%f y=%f ", dx, dy));

        return true;
    }
}