package la.baibu.youwoexample;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import la.baibu.youwoexample.fragments.HomeFragment;
import la.baibu.youwoexample.fragments.HomeFragment1;
import la.baibu.youwoexample.fragments.MapFragment;
import la.baibu.youwoexample.fragments.MyFragment;
import la.baibu.youwoexample.ui.BaseActivity;
import la.baibu.youwoexample.view.MainBottomTabLayout;

public class MainActivity extends BaseActivity {

    private MainFragmentAdapter mAdapter;
    private MainBottomTabLayout mainBottomTabLayout;
    private ViewPager viewPager;


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndDatas() {
        mainBottomTabLayout = (MainBottomTabLayout) findViewById(R.id.main_bottom_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initView();

    }

    @Override
    protected int getStatusBarColor() {
        return Color.parseColor(getString(R.string.red_color_str));
    }


    private void initView() {
        viewPager.setOffscreenPageLimit(4);
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        mainBottomTabLayout.setViewPager(viewPager);
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
                case 2:
                    fragment = MapFragment.newInstance(position);
                    break;
                case 3:
                    fragment = MyFragment.newInstance(position);
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
