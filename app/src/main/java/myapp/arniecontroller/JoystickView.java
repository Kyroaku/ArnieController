package myapp.arniecontroller;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by hmaciejczyk on 2018-02-18.
 */

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener
{
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private JoystickListener joystickCallback;

    public interface JoystickListener{

        void onJoystickMoved(float xPercent, float yPercent, int source);
    }

    private void setupDimensions()
    {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        baseRadius = Math.min(getWidth(), getHeight() / 3);
        hatRadius = Math.min((float)getWidth(), (float)getHeight() / 6.0f);

    }

    public JoystickView(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener) {
            joystickCallback = (JoystickListener) context;
        }

    }

    public JoystickView(Context context, AttributeSet attributeSet, int style)
    {
        super(context, attributeSet, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener) {
            joystickCallback = (JoystickListener) context;
        }
    }

    public JoystickView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof JoystickListener) {
            joystickCallback = (JoystickListener) context;
        }
    }

    private void drawJoystick(float newX, float newY)
    {
        if(getHolder().getSurface().isValid())
        {
            Canvas myCanvas = this.getHolder().lockCanvas(); // Stuff to draw
            Paint colors = new Paint();
            myCanvas.drawColor(0, PorterDuff.Mode.CLEAR); // clear
            myCanvas.drawColor(0xffffffff);
            myCanvas.drawARGB(0,0,0,0);
            colors.setARGB(255,50,50, 50); // Color of joystick base
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors); // Draw the joystick base
            colors.setARGB(255,0,0,255); // Color of joystick itself
            myCanvas.drawCircle(newX,newY, hatRadius, colors); // Draw the joystick hat
            getHolder().unlockCanvasAndPost(myCanvas); // Write the new draving to the SurfaceView
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        setupDimensions();
        drawJoystick(centerX, centerY);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    public boolean onTouch(View view, MotionEvent e) {

        if (view.equals(this)) {


            if (e.getAction() != e.ACTION_UP) {
                float displacement = (float) (Math.sqrt(Math.pow(e.getX() - centerX, 2) + Math.pow(e.getY() - centerY,2)));

                if (displacement < baseRadius) {
                    drawJoystick(e.getX(), e.getY());
                    joystickCallback.onJoystickMoved((e.getX() - centerX) / baseRadius, (e.getY() - centerY) / baseRadius, getId());
                } else {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());
                }
           // } else {
                //drawJoystick(centerX, centerY);
                //joystickCallback.onJoystickMoved(0, 0, getId());
            }

        }
        return true;
    }
}

