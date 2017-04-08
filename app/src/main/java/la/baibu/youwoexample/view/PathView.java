package la.baibu.youwoexample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by minna_Zhou on 2017/4/7 0007.
 */
public class PathView extends View {
    private Canvas mCanvas;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }
        mCanvas = canvas;
        startPath();
    }

    private void startPath() {

        // 创建画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(10);


//        path.lineTo(200, 200);
//        path.lineTo(0, 0);
//        path.setLastPoint(200,0);//把上一个点的绘制改变
//        path.moveTo(200,0);//上一个绘制的点，挪到这个点
//        path.lineTo(0,0);
//        path.close();

//        path.addRect(new RectF(-200, -200, 200, 200), Path.Direction.CCW);//Path.Direction.CW是顺时针
//        path.setLastPoint(-300, 300);


        mCanvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        mCanvas.scale(-1, -1);//y轴翻过去

//        Path path = new Path();
//
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        path.setLastPoint(-300, 300);
//
//        mCanvas.drawPath(path, mPaint);

        Path path = new Path();
        path.addRect(new RectF(-200, -200, 200, 200), Path.Direction.CW);

        Path src = new Path();
        src.addCircle(0, 0, 100, Path.Direction.CW);

        path.addPath(src, 200, 0);

        mCanvas.drawPath(path, mPaint);
    }
}
