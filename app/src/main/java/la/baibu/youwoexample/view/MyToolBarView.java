package la.baibu.youwoexample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import la.baibu.youwoexample.R;
import la.baibu.youwoexample.utils.StringUtil;

/**
 * Created by minna_Zhou on 2016/11/23 0023.
 * 我的toolbar自定义view，左边（文字或者图片，点击事件） 中间 右边
 */
public class MyToolBarView extends RelativeLayout {
    private Context mContext;
    private View topBarView;
    private ImageView leftIv;
    private ImageView middleIv;
    private ImageView rightIv;
    private TextView leftTv;
    private TextView middleTv;
    private TextView rightTv;
    private TypedArray typedArray;
    private String leftTextText;
    private float leftTextSize;
    private int leftTextColor;
    private boolean leftTextIsVisible;
    private boolean leftTextIsClickable;
    private String middleTextText;
    private float middleTextSize;
    private int middleTextColor;
    private boolean middleTextIsVisible;
    private boolean middleTextIsClickable;
    private String rightTextText;
    private float rightTextSize;
    private int rightTextColor;
    private boolean rightTextIsVisible;
    private boolean rightTextIsClickable;
    private boolean leftImageIsVisible;
    private boolean leftImageIsClickable;
    private boolean middleImageIsVisible;
    private boolean middleImageIsClickable;
    private boolean rightImageIsVisible;
    private boolean rightImageIsClickable;
    private int leftImageResId;
    private int middleImageResId;
    private int rightImageResId;
    private OnClickListener lister;
    private int backgroundColor;

    public MyToolBarView(Context context) {
        this(context, null);
    }

    public MyToolBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        getLayoutAndViews();
        getAttrs(attrs, defStyleAttr);
//        setClickable(true);
    }


    private void getAttrs(AttributeSet attrs, int defStyleAttr) {
        typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MyToolBarStyle);

        getMyToolbarBg();
        getLeftText();
        getMiddleText();
        getRightText();
        getLeftImage();
        getMiddleImage();
        getRightImage();

        typedArray.recycle();//记得回收

        setMyToolbarBgColor(backgroundColor);
        setLeftText();
        setMiddleText();
        setRightTex();

        setLeftImage();
        setMiddleImage();
        setRightImage();
    }

    private void getMyToolbarBg() {
        backgroundColor = typedArray.getResourceId(R.styleable.MyToolBarStyle_backgroundColor, R.color.mytoolbar_bg_color);
    }

    private void getRightImage() {
        //右边图片
        rightImageResId = typedArray.getResourceId(R.styleable.MyToolBarStyle_rightImageSrc, 0);
        rightImageIsVisible = typedArray.getBoolean(R.styleable.MyToolBarStyle_rightImageIsVisible, true);
        rightImageIsClickable = typedArray.getBoolean(R.styleable.MyToolBarStyle_rightImageIsClikable, true);
    }

    private void getMiddleImage() {
        //中间图片
        middleImageResId = typedArray.getResourceId(R.styleable.MyToolBarStyle_middleImageSrc, 0);
        middleImageIsVisible = typedArray.getBoolean(R.styleable.MyToolBarStyle_middleImageIsVisible, false);
        middleImageIsClickable = typedArray.getBoolean(R.styleable.MyToolBarStyle_middleImageIsClikable, true);
    }

    private void getLeftImage() {
        //左边图片
        leftImageResId = typedArray.getResourceId(R.styleable.MyToolBarStyle_leftImageSrc, 0);
        leftImageIsVisible = typedArray.getBoolean(R.styleable.MyToolBarStyle_leftImageIsVisible, true);
        leftImageIsClickable = typedArray.getBoolean(R.styleable.MyToolBarStyle_leftImageIsClikable, true);
    }

    private void setLeftImage() {
        if (leftImageResId != 0) {
            leftIv.setImageResource(leftImageResId);
        }
        leftIv.setVisibility(leftImageIsVisible ? View.VISIBLE : View.GONE);
        leftIv.setClickable(leftImageIsClickable);
    }

    private void setMyToolbarBgColor(int colorRes) {
        if (backgroundColor != 0) {
            topBarView.setBackgroundColor(colorRes);
        }
    }

    private void setMiddleImage() {
        if (middleImageResId != 0) {
            middleIv.setImageResource(middleImageResId);
        }
        middleIv.setVisibility(middleImageIsVisible ? View.VISIBLE : View.GONE);
        middleIv.setClickable(middleImageIsClickable);
    }


    private void setRightImage() {
        if (rightImageResId != 0) {
            rightIv.setImageResource(rightImageResId);
        }
        rightIv.setVisibility(rightImageIsVisible ? View.VISIBLE : View.GONE);
        rightIv.setClickable(rightImageIsClickable);
    }

    private void getRightText() {
        //右边文字
        rightTextText = typedArray.getString(R.styleable.MyToolBarStyle_rightTextText);
        rightTextSize = typedArray.getDimension(R.styleable.MyToolBarStyle_rightTextSize, mContext.getResources().getDimension(R.dimen.common_text_size));
        rightTextColor = typedArray.getColor(R.styleable.MyToolBarStyle_rightTextColor, mContext.getResources().getColor(R.color.main_tab_text_color_normal));
        rightTextIsVisible = typedArray.getBoolean(R.styleable.MyToolBarStyle_rightTextIsVisible, false);
        rightTextIsClickable = typedArray.getBoolean(R.styleable.MyToolBarStyle_rightTextIsClikable, true);
    }

    private void getMiddleText() {
        //中间文字
        middleTextText = typedArray.getString(R.styleable.MyToolBarStyle_middleTextText);
        middleTextSize = typedArray.getDimension(R.styleable.MyToolBarStyle_middleTextSize, mContext.getResources().getDimension(R.dimen.common_text_size));
        middleTextColor = typedArray.getColor(R.styleable.MyToolBarStyle_middleTextColor, mContext.getResources().getColor(R.color.main_tab_text_color_normal));
        middleTextIsVisible = typedArray.getBoolean(R.styleable.MyToolBarStyle_middleTextIsVisible, false);
        middleTextIsClickable = typedArray.getBoolean(R.styleable.MyToolBarStyle_middleTextIsClikable, true);
    }

    private void getLeftText() {
        //左边文字
        leftTextText = typedArray.getString(R.styleable.MyToolBarStyle_leftTextText);
        leftTextSize = typedArray.getDimension(R.styleable.MyToolBarStyle_leftTextSize, mContext.getResources().getDimension(R.dimen.common_text_size));
        leftTextColor = typedArray.getColor(R.styleable.MyToolBarStyle_leftTextColor, mContext.getResources().getColor(R.color.main_tab_text_color_normal));
        leftTextIsVisible = typedArray.getBoolean(R.styleable.MyToolBarStyle_leftTextIsVisible, false);
        leftTextIsClickable = typedArray.getBoolean(R.styleable.MyToolBarStyle_leftTextIsClikable, true);


    }

    private void setLeftText() {
        if (!StringUtil.isEmpty(leftTextText)) {
            leftTv.setText(leftTextText);
        }
        if (leftTextColor > 0) {
            leftTv.setTextColor(leftTextColor);
        }
        leftTv.setClickable(leftTextIsClickable);
        leftTv.setVisibility(leftTextIsVisible ? View.VISIBLE : View.GONE);
    }

    private void setMiddleText() {
        if (!StringUtil.isEmpty(middleTextText)) {
            middleTv.setText(middleTextText);
        }
        if (middleTextColor > 0) {
            middleTv.setTextColor(middleTextColor);
        }
        middleTv.setClickable(middleTextIsClickable);
        middleTv.setVisibility(middleTextIsVisible ? View.VISIBLE : View.GONE);
    }

    private void setRightTex() {
        if (!StringUtil.isEmpty(rightTextText)) {
            rightTv.setText(rightTextText);
        }
        if (rightTextColor > 0) {
            rightTv.setTextColor(rightTextColor);
        }
        rightTv.setClickable(rightTextIsClickable);
        rightTv.setVisibility(rightTextIsVisible ? View.VISIBLE : View.GONE);
    }


    /**
     * 得到布局和view
     */
    private void getLayoutAndViews() {
        topBarView = LayoutInflater.from(mContext).inflate(R.layout.template_topbar_base, this, true);
        leftIv = (ImageView) topBarView.findViewById(R.id.iv_left_img);
        middleIv = (ImageView) topBarView.findViewById(R.id.iv_middle_img);
        rightIv = (ImageView) topBarView.findViewById(R.id.iv_right_img);

        leftTv = (TextView) topBarView.findViewById(R.id.tv_left_text);
        middleTv = (TextView) topBarView.findViewById(R.id.tv_middle_text);
        rightTv = (TextView) topBarView.findViewById(R.id.tv_right_text);
    }

    public String getLeftTextText() {
        return leftTextText;
    }

    public void setLeftTextText(String leftTextText) {
        this.leftTextText = leftTextText;
    }

    public float getLeftTextSize() {
        return leftTextSize;
    }

    public void setLeftTextSize(float leftTextSize) {
        this.leftTextSize = leftTextSize;
    }

    public int getLeftTextColor() {
        return leftTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
    }

    public boolean isLeftTextIsVisible() {
        return leftTextIsVisible;
    }

    public void setLeftTextIsVisible(boolean leftTextIsVisible) {
        this.leftTextIsVisible = leftTextIsVisible;
    }

    public boolean isLeftTextIsClickable() {
        return leftTextIsClickable;
    }

    public void setLeftTextIsClickable(boolean leftTextIsClickable) {
        this.leftTextIsClickable = leftTextIsClickable;
    }

    public String getMiddleTextText() {
        return middleTextText;
    }

    public void setMiddleTextText(String middleTextText) {
        this.middleTextText = middleTextText;
    }

    public float getMiddleTextSize() {
        return middleTextSize;
    }

    public void setMiddleTextSize(float middleTextSize) {
        this.middleTextSize = middleTextSize;
    }

    public int getMiddleTextColor() {
        return middleTextColor;
    }

    public void setMiddleTextColor(int middleTextColor) {
        this.middleTextColor = middleTextColor;
    }

    public boolean isMiddleTextIsVisible() {
        return middleTextIsVisible;
    }

    public void setMiddleTextIsVisible(boolean middleTextIsVisible) {
        this.middleTextIsVisible = middleTextIsVisible;
    }

    public boolean isMiddleTextIsClickable() {
        return middleTextIsClickable;
    }

    public void setMiddleTextIsClickable(boolean middleTextIsClickable) {
        this.middleTextIsClickable = middleTextIsClickable;
    }

    public String getRightTextText() {
        return rightTextText;
    }

    public void setRightTextText(String rightTextText) {
        this.rightTextText = rightTextText;
    }

    public float getRightTextSize() {
        return rightTextSize;
    }

    public void setRightTextSize(float rightTextSize) {
        this.rightTextSize = rightTextSize;
    }

    public int getRightTextColor() {
        return rightTextColor;
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
    }

    public boolean isRightTextIsVisible() {
        return rightTextIsVisible;
    }

    public void setRightTextIsVisible(boolean rightTextIsVisible) {
        this.rightTextIsVisible = rightTextIsVisible;
    }

    public boolean isRightTextIsClickable() {
        return rightTextIsClickable;
    }

    public void setRightTextIsClickable(boolean rightTextIsClickable) {
        this.rightTextIsClickable = rightTextIsClickable;
    }

    public boolean isLeftImageIsVisible() {
        return leftImageIsVisible;
    }

    public void setLeftImageIsVisible(boolean leftImageIsVisible) {
        this.leftImageIsVisible = leftImageIsVisible;
    }

    public boolean isLeftImageIsClickable() {
        return leftImageIsClickable;
    }

    public void setLeftImageIsClickable(boolean leftImageIsClickable) {
        this.leftImageIsClickable = leftImageIsClickable;
    }

    public boolean isMiddleImageIsVisible() {
        return middleImageIsVisible;
    }

    public void setMiddleImageIsVisible(boolean middleImageIsVisible) {
        this.middleImageIsVisible = middleImageIsVisible;
    }

    public boolean isMiddleImageIsClickable() {
        return middleImageIsClickable;
    }

    public void setMiddleImageIsClickable(boolean middleImageIsClickable) {
        this.middleImageIsClickable = middleImageIsClickable;
    }

    public boolean isRightImageIsVisible() {
        return rightImageIsVisible;
    }

    public void setRightImageIsVisible(boolean rightImageIsVisible) {
        this.rightImageIsVisible = rightImageIsVisible;
    }

    public boolean isRightImageIsClickable() {
        return rightImageIsClickable;
    }

    public void setRightImageIsClickable(boolean rightImageIsClickable) {
        this.rightImageIsClickable = rightImageIsClickable;
    }

    public int getLeftImageResId() {
        return leftImageResId;
    }

    public void setLeftImageResId(int leftImageResId) {
        this.leftImageResId = leftImageResId;
    }

    public int getMiddleImageResId() {
        return middleImageResId;
    }

    public void setMiddleImageResId(int middleImageResId) {
        this.middleImageResId = middleImageResId;
    }

    public int getRightImageResId() {
        return rightImageResId;
    }

    public void setRightImageResId(int rightImageResId) {
        this.rightImageResId = rightImageResId;
    }


    public void setOnClickMyToolbarLeftImageview(OnClickListener lister) {
        leftIv.setOnClickListener(lister);
    }

    public void setOnClickMyToolbarLeftTextview(OnClickListener lister) {
        leftTv.setOnClickListener(lister);
    }

    public void setOnClickMyToolbarMiddleTextview(OnClickListener lister) {
        middleTv.setOnClickListener(lister);
    }

    public void setOnClickMyToolbarMiddleImageview(OnClickListener lister) {
        middleIv.setOnClickListener(lister);
    }

    public void setOnClickMyToolbarRightImageview(OnClickListener lister) {
        rightIv.setOnClickListener(lister);
    }

    public void setOnClickMyToolbarRightTextview(OnClickListener lister) {
        rightTv.setOnClickListener(lister);
    }
}
