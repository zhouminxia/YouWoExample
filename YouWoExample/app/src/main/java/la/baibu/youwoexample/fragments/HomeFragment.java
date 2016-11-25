package la.baibu.youwoexample.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.baibu.youwoexample.MainActivity;
import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2016/11/23 0023.
 * 首页的第一个fragment
 */
public class HomeFragment extends Fragment {
    private static int mPosition;
    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temp, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static Fragment newInstance(int position) {
        HomeFragment.mPosition = position;
        HomeFragment homeFragment = new HomeFragment();
//        mMyToolBarView.setMiddleTextText("中间的");
        return homeFragment;
    }
}