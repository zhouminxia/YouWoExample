package la.baibu.youwoexample.ui.my;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.BaseActivity;
import la.baibu.youwoexample.view.ObservableScrollView;

/**
 * Created by minna_Zhou on 2016/11/30 0030.
 * 个人资料
 */
public class InfoActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_menu_1)
    ImageView ivMenu1;
    @BindView(R.id.iv_menu2)
    ImageView ivMenu2;
    @BindView(R.id.simpledraweeview_big_image)
    SimpleDraweeView simpledraweeviewBigImage;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R.id.gv_photo_wall)
    GridView gvPhotoWall;
    @BindView(R.id.horizontal_scroll_view)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.toolbar_info)
    Toolbar toolbarInfo;
    @BindView(R.id.scrollview)
    ObservableScrollView scrollview;
    private int imageHeight;
    private int statusBarColor = R.color.transparent;


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_info;
    }

    @Override
    protected void initViewsAndDatas() {

//        FrescoUtil.displayImageHttpOrFile("", simpledraweeviewBigImage);//头像
//
        initScrollListeners();

    }

    @Override
    protected int setStautausBarColor() {
        return R.color.transparent;
    }

    private void initScrollListeners() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = simpledraweeviewBigImage.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                simpledraweeviewBigImage.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = simpledraweeviewBigImage.getHeight();

                scrollview.setScrollViewListener(InfoActivity.this);
            }
        });
    }


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        System.out.println("imageHeight=" + imageHeight);//335dp====1005px
        // TODO Auto-generated method stub
        // Log.i("TAG", "y--->" + y + "    height-->" + height);
        if (y <= 0) {
            toolbarInfo.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
            statusBarColor = Color.argb((int) 0, 227, 29, 26);
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            toolbarInfo.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
            statusBarColor = Color.argb((int) alpha, 227, 29, 26);//改变状态栏的颜色

        } else {
            toolbarInfo.setBackgroundColor(Color.argb((int) 255, 227, 29, 26));
            statusBarColor = Color.argb((int) 255, 227, 29, 26);
        }
    }

}
