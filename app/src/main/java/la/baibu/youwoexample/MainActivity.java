package la.baibu.youwoexample;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import la.baibu.youwoexample.fragments.HomeFragment;
import la.baibu.youwoexample.fragments.HomeFragment1;
import la.baibu.youwoexample.fragments.MapFragment;
import la.baibu.youwoexample.fragments.MyFragment;
import la.baibu.youwoexample.view.MainBottomTabLayout;

public class MainActivity extends AppCompatActivity {

    private MainFragmentAdapter mAdapter;
    private MainBottomTabLayout mainBottomTabLayout;
    private ViewPager viewPager;
    //沉浸式通知栏的一个开源库SystemBarTint
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSystemBar();
        setContentView(R.layout.activity_main);
        mainBottomTabLayout = (MainBottomTabLayout) findViewById(R.id.main_bottom_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initView();
    }

    //沉浸式状态
    public void setupSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(setStautausBarColor());
    }

    private int setStautausBarColor() {
        return R.color.system_bar_color;
    }

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

    private void initView() {
        viewPager.setOffscreenPageLimit(4);
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        mainBottomTabLayout.setViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class MainFragmentAdapter extends FragmentPagerAdapter {

        private final String[] tabCount = new String[]{"A", "B", "C", "D"};
        private int mCount = tabCount.length;

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = HomeFragment.newInstance(position);
                    break;
                case 1:
                    fragment = HomeFragment1.newInstance(position);
                    break;
                case 3:
                    fragment = MyFragment.newInstance(position);
                    break;
                case 2:
                    fragment = MapFragment.newInstance(position);
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabCount[position % tabCount.length];
        }

    }
}
