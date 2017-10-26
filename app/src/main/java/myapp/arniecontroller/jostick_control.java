package myapp.arniecontroller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


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


        imageView = (ImageView) this.findViewById(R.id.ImageView);
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