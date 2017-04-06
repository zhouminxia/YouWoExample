package la.baibu.youwoexample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2017/4/6 0006.
 */
public class CanvusView extends View {
    private Canvas mCanvas;
    private int mWidth;
    private int mHeight;

    public CanvusView(Context context) {
        this(context, null);
    }

    public CanvusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            mCanvas = canvas;
        }
        if (mCanvas == null) {
            return;
        }
//        startTranslateAndScale();
//        startRotate();
//        start1();
        start2();
    }

    /**
     * 用旋转画出不明觉厉的东西
     */
    private void start2() {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.black));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//描边


        mCanvas.translate(mWidth / 2, mHeight / 2);//将坐标系移到屏幕中间
        mCanvas.drawCircle(0, 0, 400, paint);//画大圆
        mCanvas.drawCircle(0, 0, 380, paint);//画小圆

        //画中间的短横线
        for (int i = 0; i < 360; i += 10) {
            mCanvas.drawLine(380, 0, 400, 0, paint);//就是（380,0）和（400,0）这个点连起来的横线
            mCanvas.rotate(10);//每次旋转这条线10度
        }
    }


    /**
     * 旋转
     */
    private void startRotate() {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.black));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//描边
        paint.setStrokeWidth(5);

        Rect rect = new Rect(0, -400, 400, 0);
        mCanvas.translate(mWidth / 2, mHeight / 2);//坐标系的圆点从左上角平移到屏幕中心
        mCanvas.drawRect(rect, paint);


        mCanvas.rotate(180, 200, 0);
        mCanvas.rotate(100);

        paint.setColor(getResources().getColor(R.color.actionsheet_blue));
        mCanvas.drawRect(rect, paint);
    }

    /**
     * 画很多层的图形
     */
    private void start1() {
        mCanvas.translate(mWidth / 2, mHeight / 2);//把坐标系圆点平移到页面的中心
        Paint paint = new Paint();

        paint.setColor(getResources().getColor(R.color.black));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//描边
        paint.setStrokeWidth(5);


        Rect rect = new Rect(-400, -400, 400, 400);
        for (int i = 0; i < 10; i++) {
            mCanvas.scale(0.9f, 0.9f);
            mCanvas.drawRect(rect, paint);
        }
    }


    /**
     * 代码中获取的长宽单位都是px,比如布局中写宽：200dp，在xxhdpi手机上的宽就是600px
     * 1080*1920px--------300多dp*600多dp
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void startTranslateAndScale() {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.black));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//描边
        paint.setStrokeWidth(5);

        Rect rect = new Rect(0, -400, 400, 0);
        mCanvas.translate(mWidth / 2, mHeight / 2);//坐标系的圆点从左上角平移到屏幕中心
        mCanvas.drawRect(rect, paint);


        mCanvas.scale(0.5f, 0.5f, 0, -200);
        paint.setColor(getResources().getColor(R.color.actionsheet_blue));
        mCanvas.drawRect(rect, paint);

    }
}
