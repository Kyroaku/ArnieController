package myapp.arniecontroller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static myapp.arniecontroller.R.id.imageView2;


/**
 * Created by Szymon on 21.10.2017.
 */

public class jostick_control extends Activity implements View.OnTouchListener {
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    TextView textXY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jostick_control);


        textXY = (TextView) this.findViewById(R.id.textXY);
        imageView = (ImageView) this.findViewById(imageView2);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();


        imageView.setOnTouchListener(this);

    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float BaseRadius=Math.min(imageView.getWidth(), imageView.getHeight() )/3;
        float x, y,image_width,image_height,R=100,cx,cy;



        image_height= imageView.getHeight()/(float)2;
        image_width= imageView.getWidth()/(float)2;

        if(event.getAction() != event.ACTION_UP)
        {
            float displacement = (float) Math.sqrt(Math.pow(event.getX() - image_width, 2) + Math.pow(event.getY() - image_height, 2));
            if(displacement>50)
            {imageView.setY(0);
                imageView.setX(375);


               // Log.d("Touch", "Touch move " + event.getX() + " " + event.getY());
            }
            else
            {
                x=imageView.getX();
                y=imageView.getY();

                float conX=event.getX()-image_width;
                float conY=event.getY()-image_height;
                imageView.setX(event.getX()+x);
                imageView.setY(event.getY()+y);
                Log.d("Touch", "Touch move x=" + conX + " y=" + conY);

                textXY.setText(String.format(Locale.getDefault(), "x=%f y=%f ", conX,conY));
            }
        }
        return true;
    }
}