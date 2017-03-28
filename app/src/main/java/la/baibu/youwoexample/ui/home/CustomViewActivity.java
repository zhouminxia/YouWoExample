package la.baibu.youwoexample.ui.home;

import android.content.Intent;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.BaseActivity;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 * 自定义view
 */
public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.btn_custome_one)
    Button btnCustomeOne;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void initViewsAndDatas() {

    }

    @Override
    protected int getStatusBarColor() {
        return 0;
    }

    @OnClick(R.id.btn_custome_one)
    public void gotoCustomeOne() {
        Intent intent = new Intent(mContext, CustomeOneActivity.class);
        mContext.startActivity(intent);
    }

}
