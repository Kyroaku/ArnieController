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

import static myapp.arniecontroller.R.id.imageView2;


/**
 * Created by Szymon on 21.10.2017.
 */

public class jostick_control extends Activity implements View.OnTouchListener {
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jostick_control);



        imageView = (ImageView) this.findViewById(imageView2);
        //imageView.setImageResource(R.drawable\ic_notification_overlay);
       // imageView.setImageDrawable();
        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();

        //bitmap = Bitmap.createBitmap((int) dw, (int) dh,Bitmap.Config.ARGB_8888);
        //canvas = new Canvas(bitmap);
      //  paint = new Paint();
       // paint.setColor(Color.GREEN);
        //imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(this);

    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float x, y,image_width,image_height;
        image_height= imageView.getHeight();
        image_width= imageView.getWidth();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Do Something
                Log.d("Touch", "Touch down");
                break;

            case MotionEvent.ACTION_MOVE:
                x=event.getX() - image_width/(float)2;// srodek naszego obrazka
                y=event.getY()- image_height/(float)2;
                // Do Something
                Log.d("Touch", "Touch move " + x + " " + y);
                //imageView.invalidate();

                break;

            case MotionEvent.ACTION_UP:

                // Do Something
                Log.d("Touch", "Touch up");
                //imageView.invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:

                Log.d("Touch", "Touch cancel");
                break;

            default:
                break;
        }
        return true;
    }
}