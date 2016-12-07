package la.baibu.youwoexample.ui.my;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.adapter.MyGridViewAdapter;
import la.baibu.youwoexample.bean.SelectImageBean;
import la.baibu.youwoexample.contants.BroadcastContants;
import la.baibu.youwoexample.ui.BaseActivity;
import la.baibu.youwoexample.utils.CameraUtil;
import la.baibu.youwoexample.utils.FrescoUtil;
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
    private MyGridViewAdapter myGridViewAdapter;
    private File cameraFile;//拍照返回的文件
    private String cameraFilePath;//拍照返回文件的路径
    private ArrayList<String> addPhotoPathList = new ArrayList<String>();//添加的照片文件的路径集合
    private List<SelectImageBean> mImages = new ArrayList<SelectImageBean>();

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
        addImages(true, null);//加一张默认的图片

        refreshMyGridView();
    }

    private void refreshMyGridView() {
        if (myGridViewAdapter == null) {
            setGridViewParams();
            myGridViewAdapter = new MyGridViewAdapter(mContext, mImages);
            gvPhotoWall.setAdapter(myGridViewAdapter);
            clickGridViewItem();
        } else {
            setGridViewParams();
            myGridViewAdapter.notifyDataSetChanged();
        }

        for (int i = 0; i < mImages.size(); i++) {
            String imagePath = mImages.get(i).getImagePath();
            System.out.println("--现在有几个Image= i=" + i + "---path=" + imagePath);
        }
    }

    private void clickGridViewItem() {
        gvPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectImageBean imageBean = mImages.get(position);
                int imageType = imageBean.getImageType();
                if (SelectImageBean.TYPE_DEFAULT_IMAGE == imageType) {
//                    弹出底部对话框，选择本地图片或拍照
                    takePhoto();
//                    PhotoUtil.uploadPic(getString(R.string.please_select_photo), mContext);
                } else if (SelectImageBean.TYPE_IMAGE == imageType) {
                    showShortToast("TYPE_IMAGE");
                }
            }
        });
    }

    private void takePhoto() {
        cameraFile = CameraUtil.camera(InfoActivity.this);//拍照页面返回的文件

        if (cameraFile != null) {
            cameraFilePath = cameraFile.getAbsolutePath();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (CameraUtil.CAMERA_REQUEST_CODE == requestCode) {
                //默认情况下，即不需要指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                // 照相机有自己默认的存储路径，拍摄的照片将返回一个缩略图。如果想访问原始图片，可以通过dat extra能够得到原始图片位置。
                // 如果指定了目标uri，data就没有数据，如果没有指定uri，则data就返回有数据！
//                Uri uri = data.getData();//所以data是为空的
                if (cameraFilePath != null) {
                    if (cameraFilePath != null) {
                        addPhotoPathList.clear();
                        addPhotoPathList.add(cameraFilePath);
                        broadCastPhoto();//（压缩+广播）
                        CameraUtil.sendBroadCaseRemountSDcard(cameraFile);//插入到系统相册
                    }
                }
            }
        }
    }

    /**
     * 广播照片，然后需要压缩
     */
    private void broadCastPhoto() {
        Intent imageIntent = new Intent();
        imageIntent.setAction(BroadcastContants.IMAGE_SELECTED_FROM_MEDIA_ACTION);
        imageIntent.putStringArrayListExtra(
                BroadcastContants.IMAGE_SELECTED_LIST, addPhotoPathList);
        sendOrderedBroadcast(imageIntent, null);//有序广播
//        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mImageReceiver);
        System.out.println("--InfoActivity-onDestroy");
    }

    /**
     * 设置GirdView参数，绑定数据
     */
    private void setGridViewParams() {
        System.out.println("--mImages.size=" + mImages.size());
        int size = mImages.size();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (110 * size * density);
        System.out.println("--allWidth=" + allWidth);
        int itemWidth = (int) (100 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gvPhotoWall.setLayoutParams(params);
        gvPhotoWall.setColumnWidth(itemWidth);
        gvPhotoWall.setHorizontalSpacing(10);
        gvPhotoWall.setStretchMode(GridView.NO_STRETCH);
        gvPhotoWall.setNumColumns(size);

//        int size = mImages.size();
//        if (size < MyGridViewAdapter.maxPhotoes) {
//            size++;//有add
//        }
//        Point point = SizeUtils.autoGetWidthAndHeight(this, 10, 10, 4, 6, 1.0f);
//        int horizontalSpacing = SizeUtils.dip2px(this, 6);
//        int width = point.x * size + (size + 1) * horizontalSpacing;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                width, LinearLayout.LayoutParams.MATCH_PARENT);
//        gvPhotoWall.setColumnWidth(point.x); // 设置列表项宽
//        gvPhotoWall.setHorizontalSpacing(horizontalSpacing); // 设置列表项水平间距
//        gvPhotoWall.setStretchMode(GridView.NO_STRETCH);
//        gvPhotoWall.setNumColumns(size); // 设置列数量=列表集合数
//        params.topMargin = SizeUtils.dip2px(this, 6);
////        params.leftMargin = SizeUtils.dip2px(this, 10);
////        params.rightMargin = SizeUtils.dip2px(this, 10);
//        gvPhotoWall.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageBroadcastReceiver();//注册图片广播
    }

    private void initImageBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter(BroadcastContants.IMAGE_SELECTED_FROM_MEDIA_ACTION);
        registerReceiver(mImageReceiver, intentFilter);
    }

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

    private BroadcastReceiver mImageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> imageSelectedList = intent.getStringArrayListExtra(BroadcastContants.IMAGE_SELECTED_LIST);
            if (imageSelectedList.size() > 0) {
                addImages(false, imageSelectedList);//添加的那一张照片
                refreshMyGridView();//刷新
            }
        }
    };

    /**
     * @param imageSelectedList 选中照片的集合
     * @param isAddDefault      是否是添加一张默认图片
     */
    private void addImages(boolean isAddDefault, ArrayList<String> imageSelectedList) {
        if (isAddDefault) {
            List<SelectImageBean> tempImages = new ArrayList<SelectImageBean>();
            tempImages.add(new SelectImageBean(SelectImageBean.TYPE_DEFAULT_IMAGE, ""));//加一张默认的
            mImages.addAll(tempImages);
        } else {
            if (imageSelectedList != null && imageSelectedList.size() > 0) {
                for (int i = 0; i < imageSelectedList.size(); i++) {
                    SelectImageBean bean = new SelectImageBean();
                    String path = imageSelectedList.get(i);
                    bean.setImageType(SelectImageBean.TYPE_IMAGE);
                    bean.setImagePath(path);
                    mImages.add(bean);
                }
            }
        }
    }
}
