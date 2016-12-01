package la.baibu.youwoexample.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by minna_Zhou on 2016/11/30 0030.
 * 基类：1.状态栏的颜色控制
 * 2.布局和初始化数据的抽象
 */
public abstract class BaseActivity extends AppCompatActivity {

    //沉浸式通知栏的一个开源库SystemBarTint,..
    private SystemBarTintManager tintManager;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSystemBar();
        mContext = this;
        if (getLayoutResID() != 0) {
            setContentView(getLayoutResID());
        } else {
            throw new IllegalArgumentException("you must imple abstract method getLayoutResID");
        }
        ButterKnife.bind(this);
        initViewsAndDatas();
    }

    protected abstract int getLayoutResID();

    protected abstract void initViewsAndDatas();


    //沉浸式状态
    public void setupSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(getStatusBarColor());
        }
    }

    protected abstract int getStatusBarColor();


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
