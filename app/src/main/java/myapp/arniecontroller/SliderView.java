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
 * Created by hmaciejczyk on 2018-02-23.
 */

public class SliderView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{

    private float sliderX;
    private float centerX;
    private float sliderY;
    private float centerY;
    private float sliderRadius;
    private static float DISPLAYSCALE = 6;
    private SliderListener slideListener;

    public interface SliderListener
    {
        public void onSliderMoved(float xPercent, float yPercent, int source);
    }


    public SliderView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof SliderListener)
        {
            slideListener = (SliderListener) context;
        }
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        setOnTouchListener(this);
        if(context instanceof SliderListener)
        {
            slideListener = (SliderListener) context;
        }
    }

    public SliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        setOnTouchListener(this);
        if(context instanceof SliderListener)
        {
            slideListener = (SliderListener) context;
        }
    }

    private void drawSlider(float newX, float newY){

        if(getHolder().getSurface().isValid()) {
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            myCanvas.drawColor(0xffffffff);
            colors.setARGB(255,50,50, 50); // Color of joystick base
            myCanvas.drawRect(sliderX,sliderY ,sliderX * 5,sliderY * 5,colors);
            colors.setARGB(255,0,0,255); // Color of joystick itself
            myCanvas.drawCircle(newX, newY, sliderRadius, colors); // Draw the joystick hat
            getHolder().unlockCanvasAndPost(myCanvas); // Write the new draving to the SurfaceView
        }

    }
    private void setupDimensions(){

        sliderX = getWidth()/ DISPLAYSCALE;
        centerX = getWidth()/2;
        sliderY = getHeight()/DISPLAYSCALE;
        centerY = getHeight()/2;
        sliderRadius = Math.min(getWidth(), getHeight())/7;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();
        drawSlider(centerX,centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    public boolean onTouch(View view, MotionEvent e){
        if(equals(this)){

            if(e.getAction() != MotionEvent.ACTION_UP)
            {
                float constrainedX;
                float constrainedY;

                if(e.getX() < sliderX) {
                    constrainedX = sliderX;
                }
                else if (e.getX() > sliderX * 5) {
                    constrainedX = sliderX * 5;
                }
                else {
                    constrainedX = e.getX();
                }


                if(e.getY() < sliderY) {
                    constrainedY = sliderY;
                }
                else if (e.getY() > sliderY * 5){
                    constrainedY = sliderY * 5;
                }
                else {
                    constrainedY = e.getY();
                }

                drawSlider(constrainedX, constrainedY);
                slideListener.onSliderMoved((e.getX()-centerX) / (sliderX / 4),(e.getY()-centerY) / (sliderY / 4),getId());
            }
            else{
                //drawSlider(centerX,centerY);
                //slideListener.onSliderMoved(e.getX(),e.getY(),getId());
            }
        }

        return true;
    }



}

