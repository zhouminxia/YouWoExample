package la.baibu.youwoexample.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.MyBaiduMapActivity;
import la.baibu.youwoexample.view.MyToolBarView;

/**
 * Created by minna_Zhou on 2016/11/24 0024.
 * 首页--我fragment
 * 功能：1、普通地图（2D，3D）、卫星图；2、POI检索（ktv，覆盖物）；3、线路搜索（b7公交--一条线路）
 */
public class MyFragment extends Fragment {
    private static int position;
    private View view;
    private MyToolBarView myToolBarView;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myToolBarView = (MyToolBarView) view.findViewById(R.id.my_toolbar_view);
        myToolBarView.setOnClickMyToolbarLeftImageview(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyBaiduMapActivity.class);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static Fragment newInstance(int position) {
        MyFragment.position = position;
        MyFragment myFragment = new MyFragment();
        return myFragment;
    }


}
