import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Szymon on 02.11.2017.
 */

public class Joystick  {
    ImageView imageJoystick1;
    float joystickX = 0;
    float joystickY = 0;
    float joystickR = 100.0f;
        public Joystick ()
    {
        // Co ja kurwa mowilem o nie dzialajacym kodzie :o
        //imageJoystick1 = (ImageView) this.findViewById(R.id.imageJoystick1);
    }




    public void SetXY (float joystickX, float joystickY)
    {
          /* Set joystick position to base joystick position, when finger isn't on screen. */
       float x = joystickX;
       float y = joystickY;
    }


    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX(); /* touch x position. */
        float y = event.getY(); /* touch y position. */
        float dx = 0;           /* joystick x axis. */
        float dy = 0;           /* joystick y axis. */

        switch(event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN: {
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

            case MotionEvent.ACTION_UP: {
                /* Set joystick position to base joystick position, when finger isn't on screen. */
                x = joystickX;
                y = joystickY;
            } break;
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
       // textXY.setText(String.format(Locale.getDefault(), "x=%f y=%f ", dx, dy));

        return true;
    }

}
