package la.baibu.youwoexample.ui.my;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.adapter.MyGridViewAdapter;
import la.baibu.youwoexample.bean.SelectImageBean;
import la.baibu.youwoexample.ui.BaseActivity;
import la.baibu.youwoexample.utils.FrescoUtil;
import la.baibu.youwoexample.utils.PhotoImageUtil;
import la.baibu.youwoexample.utils.SizeUtils;
import la.baibu.youwoexample.view.ObservableScrollView;

/**
 * Created by minna_Zhou on 2016/11/30 0030.
 * 个人资料
 * 实现头部下拉，（状态栏高度和toolbar高度之和为H），下拉高度超过H，开始渐变；下拉高度>H && < 图片高度时
 */
public class InfoActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener {

    //    @BindView(R.id.iv_back)
//    ImageView ivBack;
//    @BindView(R.id.iv_menu_1)
//    ImageView ivMenu1;
//    @BindView(R.id.iv_menu2)
//    ImageView ivMenu2;
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
    private int statusBarColor = Color.parseColor("#00FFFFFF");
    private Toolbar toolbar;
    private List<SelectImageBean> mImages = new ArrayList<SelectImageBean>();
    private MyGridViewAdapter myGridViewAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_info;
    }

    @Override
    protected void initViewsAndDatas() {
        toolbar = ButterKnife.findById(InfoActivity.this, R.id.toolbar_info);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);//设置返回键不可用
            getSupportActionBar().setTitle("");
        }
//        getStatusBarHeightAndTitleBarHeight();
        tintManager.setStatusBarAlpha(0.0f);//完全透明
        FrescoUtil.displayImageHttpOrFile("", simpledraweeviewBigImage);//头像
        initScrollListeners();
        initHorizontolGridView();
    }

    /**
     * 初始化水平的照片墙
     */
    private void initHorizontolGridView() {
        addDefaultImage();//加一张默认的图片

        if (myGridViewAdapter == null) {

            setGridViewParams();
            myGridViewAdapter = new MyGridViewAdapter(mContext, mImages);
            gvPhotoWall.setAdapter(myGridViewAdapter);
            clickGridViewItem();
        } else {
            setGridViewParams();
            myGridViewAdapter.notifyDataSetChanged();
        }
    }

    private void clickGridViewItem() {
        gvPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectImageBean imageBean = mImages.get(position);
                int imageType = imageBean.getImageType();
                if (SelectImageBean.TYPE_DEFAULT_IMAGE == imageType) {
                    selectLocalImageOrPhoto();
                } else if (SelectImageBean.TYPE_IMAGE == imageType) {
                    showShortToast("TYPE_IMAGE");
                }
            }
        });
    }

    /*
    弹出底部对话框，选择本地图片或拍照
     */
    private void selectLocalImageOrPhoto() {
        PhotoImageUtil.uploadPic(getString(R.string.please_select_photo), mContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode){
            if(PhotoImageUtil.PHOTO_REQUEST_CAMERA==requestCode){
                showShortToast("PHOTO_REQUEST_CAMERA");
            }else if(PhotoImageUtil.PHOTO_REQUEST_GALLERY==requestCode){

            }
        }
    }

    private void addDefaultImage() {
        List<SelectImageBean> tempImages = new ArrayList<SelectImageBean>();
        tempImages.add(new SelectImageBean(SelectImageBean.TYPE_DEFAULT_IMAGE, ""));//加一张默认的
        mImages.addAll(tempImages);
    }


    /**
     * 设置GirdView参数，绑定数据
     */
    private void setGridViewParams() {
        int size = mImages.size();
        if (size < MyGridViewAdapter.maxPhotoes) {
            size++;//有add
        }
        Point point = SizeUtils.autoGetWidthAndHeight(this, 10, 10, 4, 6, 1.0f);
        int horizontalSpacing = SizeUtils.dip2px(this, 6);
        int width = point.x * size + (size + 1) * horizontalSpacing;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width, LinearLayout.LayoutParams.MATCH_PARENT);
        gvPhotoWall.setColumnWidth(point.x); // 设置列表项宽
        gvPhotoWall.setHorizontalSpacing(horizontalSpacing); // 设置列表项水平间距
        gvPhotoWall.setStretchMode(GridView.NO_STRETCH);
        gvPhotoWall.setNumColumns(size); // 设置列数量=列表集合数
        params.topMargin = SizeUtils.dip2px(this, 6);
//        params.leftMargin = SizeUtils.dip2px(this, 10);
//        params.rightMargin = SizeUtils.dip2px(this, 10);
        gvPhotoWall.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (android.R.id.home == itemId) {
            finish();
            return true;
        } else if (R.id.action_one == itemId) {
            showShortToast("action_one");
            return true;
        } else if (R.id.action_two == itemId) {
            showShortToast("action_two");
            return true;
        } else if (R.id.action_three == itemId) {
            showShortToast("action_three");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

//    private void getStatusBarHeightAndTitleBarHeight() {
//        int statusBarHeight = SizeUtils.getStatusBarHeight(mContext);
//        int titleBarHeight = toolbar.getMeasuredHeight();
//        showShortToast(titleBarHeight + "");
//    }

    @Override
    protected int getStatusBarColor() {
        return statusBarColor;
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
        //改变状态栏的颜色
        tintManager.setStatusBarTintColor(statusBarColor);
    }
}
