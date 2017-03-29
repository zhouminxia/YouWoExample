package la.baibu.youwoexample.view.hyviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import la.baibu.youwoexample.R;
import la.baibu.youwoexample.bean.PercentBean;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 * 饼状图
 */
public class PercentView extends View {
    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private ArrayList<PercentBean> mPercentBean;

    private float mStartAngle = 0;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public PercentView(Context context) {
        this(context, null);
    }

    public PercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//代码中获取的长宽单位都是px,比如布局中写宽：200dp，在xxhdpi手机上的宽就是600px
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mPercentBean || mPercentBean.size() == 0) {
            return;
        }

        float radius = (float) (Math.min(mWidth, mHeight) / 2 * 0.5);
        RectF rectF = new RectF(radius, radius, radius * 4, radius * 4);
        mPaint.setColor(getResources().getColor(R.color.main_page_text_color));//给矩形绘制一个灰色的背景
        canvas.drawRect(rectF, mPaint);
        float currentStartAngle = mStartAngle;
        for (int i = 0; i < mPercentBean.size(); i++) {
            PercentBean percentBean = mPercentBean.get(i);
            mPaint.setColor(percentBean.getColor());
            canvas.drawArc(rectF, currentStartAngle, percentBean.getPercentAngel(), true, mPaint);//内切圆
            float percentAngel = percentBean.getPercentAngel();
            currentStartAngle += percentAngel;
        }
    }

    public void initDatas(ArrayList<PercentBean> percentBeen) {
        mPercentBean = percentBeen;
        if (null == mPercentBean || mPercentBean.size() == 0) {
            return;
        }
        //初始化数据
        float sumValue = 0;//总角度
        for (int i = 0; i < mPercentBean.size(); i++) {
            PercentBean percentBean = mPercentBean.get(i);
            sumValue += percentBean.getValue();
        }

        for (int i = 0; i < mPercentBean.size(); i++) {
            PercentBean percentBean = mPercentBean.get(i);
            float value = percentBean.getValue();
            percentBean.setPercent(value / sumValue * 100);//给每个设置占比
            percentBean.setPercentAngel(value / sumValue * 365);

            if (percentBean.getColor() == -1) {
                int j = i % mPercentBean.size();
                percentBean.setColor(mColors[j]);
            }
        }
    }

    public void setData(ArrayList<PercentBean> percentBeanArrayList) {
        mPercentBean = percentBeanArrayList;
        initDatas(mPercentBean);
        invalidate();
    }

    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();   // 刷新
    }

}
