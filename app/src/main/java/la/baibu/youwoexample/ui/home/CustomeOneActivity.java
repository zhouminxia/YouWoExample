package la.baibu.youwoexample.ui.home;

import android.os.Bundle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.bean.PercentBean;
import la.baibu.youwoexample.ui.BaseActivity;
import la.baibu.youwoexample.view.hyviews.PercentView;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 */
public class CustomeOneActivity extends BaseActivity {


    @BindView(R.id.percenterview)
    PercentView percenterview;

    private ArrayList<PercentBean> mPercentBeen = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_custome_one;
    }

    @Override
    protected void initViewsAndDatas() {
        percenterview.setData(getDatas());
    }

    private ArrayList<PercentBean> getDatas() {
        mPercentBeen.add(new PercentBean(30, "第一部分", 0xFFCCFF00));
        mPercentBeen.add(new PercentBean(20, "第二部分", 0xFF6495ED));
        mPercentBeen.add(new PercentBean(10, "第三部分", 0xFFE32636));
        mPercentBeen.add(new PercentBean(35, "第四部分", R.color.colorPrimaryDark));
        mPercentBeen.add(new PercentBean(5, "第五部分", R.color.main_tab_text_color_normal));//android.R.color.holo_blue_dark这种出不来颜色
        return mPercentBeen;
    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
