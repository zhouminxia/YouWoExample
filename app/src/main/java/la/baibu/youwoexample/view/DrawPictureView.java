package la.baibu.youwoexample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

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
//        startDrawPicture2();
    }


    private void startDrawPicture2() {

        Picture picture = new Picture();
        mCanvas.drawPicture(picture,new RectF(0,0,picture.getWidth(),200));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    /**
     * 使用canvus.drawpicture和canvus.drawdrawable
     * 无效，这种方式是不推荐的
     */
    private void startDrawPicture() {

        Picture picture = new Picture();

        Canvas canvas = picture.beginRecording(200, 200);//开始录制

        // 创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, 100, paint);
        picture.endRecording();//结束录制

        canvas.drawPicture(picture,new RectF(0,0,picture.getWidth(),200));
    }
}
