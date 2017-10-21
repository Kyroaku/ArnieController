package myapp.arniecontroller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Szymon on 21.10.2017.
 */

public class jostick_control extends Activity implements OnTouchListener {
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jostick_control);


        imageView = (ImageView) this.findViewById(R.id.ImageView);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();

        bitmap = Bitmap.createBitmap((int) dw, (int) dh,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Do Something
                Log.d("Touch", "Touch down");
                break;

            case MotionEvent.ACTION_MOVE:

                // Do Something
                Log.d("Touch", "Touch move");
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