package la.baibu.youwoexample.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.BaseActivity;
import la.baibu.youwoexample.view.CheckView;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 * 自定义三：画布画图片
 */
public class CustomeThreeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.checkview)
    CheckView checkview;
    @BindView(R.id.btn_check)
    Button btn_check;
    @BindView(R.id.btn_uncheck)
    Button btn_uncheck;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_custome_three;
    }

    @Override
    protected void initViewsAndDatas() {

        btn_check.setOnClickListener(this);
        btn_uncheck.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.btn_check == id) {
            checkview.check();
        } else if (R.id.btn_uncheck == id) {
            checkview.unCheck();
        }
    }
}
