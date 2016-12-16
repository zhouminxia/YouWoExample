package la.baibu.youwoexample.view.PullLoadView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2016/12/16 0016.
 * 头部的view
 */
public class PullHeadView extends LinearLayout {
    private Context mContext;
    private View headView;
    private ImageView ivHeadArrow;
    private ProgressBar pbHeadProgress;
    private TextView tvHeadPullState;
    private TextView tvHeadTime;

    public PullHeadView(Context context) {
        this(context, null);
    }

    public PullHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        inflateLayout();
        addLayout();

    }

    private void addLayout() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(headView, params);
    }

    private void inflateLayout() {
        headView = LayoutInflater.from(mContext).inflate(R.layout.item_head_view, null);
        ivHeadArrow = (ImageView) headView.findViewById(R.id.iv_head_arrow);
        pbHeadProgress = (ProgressBar) headView.findViewById(R.id.pb_head_progress);
        tvHeadPullState = (TextView) headView.findViewById(R.id.tv_head_pull_state);
        tvHeadTime = (TextView) headView.findViewById(R.id.tv_head_time);
    }
}
