package la.baibu.youwoexample.ui.home;

import android.os.Bundle;

import butterknife.ButterKnife;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.BaseActivity;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 * 自定义二：画布的操作
 */
public class CustomeTwoActivity extends BaseActivity {


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_custome_two;
    }

    @Override
    protected void initViewsAndDatas() {
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
