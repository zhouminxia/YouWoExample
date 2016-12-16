package la.baibu.youwoexample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import la.baibu.youwoexample.view.PullLoadView.PullHeadView;

/**
 * Created by minna_Zhou on 2016/12/16 0016.
 * 下拉刷新，上拉加载，自定义控件
 */
public class PullLoadListView extends ListView {
    private Context mContext;

    public PullLoadListView(Context context) {
        this(context, null);

    }

    public PullLoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();//添加头部和脚部
    }

    private void initView() {
        addMyHeadView();//添加头部
//        addMyFootView();//添加尾部
    }

    private void addMyHeadView() {
        PullHeadView pullHeadView=new PullHeadView(mContext);
        addHeaderView(pullHeadView);


    }
}
