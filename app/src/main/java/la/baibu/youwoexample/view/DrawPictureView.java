package la.baibu.youwoexample.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2017/4/6 0006.
 * canvus.drawpicture和canvus.drawdrawable
 */
public class DrawPictureView extends View {
    private Canvas mCanvas;
    private int mHeight;
    private int mWidth;

    public DrawPictureView(Context context) {
        this(context, null);
    }

    public DrawPictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }
        mCanvas = canvas;
        startDrawPicture();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    /**
     * canvus.drawdrawable
     */
    private void startDrawPicture() {

        mCanvas.translate(mWidth / 2, mHeight / 2);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.checkres);
//        mCanvas.drawBitmap(bitmap, new Matrix(), new Paint());
//        mCanvas.drawBitmap(bitmap, 400, 200, new Paint());
        // 指定图片绘制区域(左上角的四分之一)
        Rect srcRect = new Rect(0,  bitmap.getWidth() / 2,bitmap.getHeight() / 2,bitmap.getHeight());
        Rect desRext = new Rect(0, 0, 200, 200);
        mCanvas.drawBitmap(bitmap, srcRect, desRext, null);
    }
}
