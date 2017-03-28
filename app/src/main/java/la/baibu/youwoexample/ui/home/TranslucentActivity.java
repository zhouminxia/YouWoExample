package la.baibu.youwoexample.ui.home;

import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.BaseActivity;

/**
 * Created by minna_Zhou on 2017/3/27 0027.
 * 实现半透明activity
 */
public class TranslucentActivity extends BaseActivity {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_translucent;
    }

    @Override
    protected void initViewsAndDatas() {
//        Dialog dialog=new Dialog(mContext);
//        dialog.setTitle("标题");
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                return false;
//            }
//        });
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.mytoolbar_bg_color;
    }
}
