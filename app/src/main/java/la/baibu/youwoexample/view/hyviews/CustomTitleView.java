package la.baibu.youwoexample.view.hyviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 */
public class CustomTitleView extends View {
    private Rect mBound;
    private Paint mPaint;
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    public CustomTitleView(Context context) {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_customTitleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_customTitleTextColor:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_customTitleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();


        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
         mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        //Return in bounds (allocated by the caller) the smallest rectangle
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        canvas.drawRect(0, 0, measuredWidth, measuredHeight, mPaint);

        mPaint.setColor(mTitleTextColor);
        int width = getWidth();
        int height = getHeight();
        canvas.drawText(mTitleText, width / 2 - mBound.width() / 2, height / 2 + mBound.height() / 2, mPaint);

        Rect rect=new Rect(100,100,800,400);
        canvas.drawRect(rect,mPaint);

    }
}
