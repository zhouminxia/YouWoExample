package la.baibu.youwoexample.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import la.baibu.youwoexample.MainActivity;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.my.InfoActivity;
import la.baibu.youwoexample.view.MyToolBarView;

/**
 * Created by minna_Zhou on 2016/11/23 0023.
 * 首页的第一个fragment
 */
public class MyFragment extends Fragment {

    private static int position;
    @BindView(R.id.my_toolbar_view)
    MyToolBarView myToolbarView;
    private MainActivity mContext;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
//        myToolBarView1 = (MyToolBarView) view.findViewById(R.id.my_toolbar_view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myToolbarView.setOnClickMyToolbarRightTextview(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoActivity.class);
                mContext.startActivity(intent);
            }
        });
        myToolbarView.setMiddleTextText("个人信息");
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
        unbinder.unbind();//解绑
        System.out.println("--MyFragment-onDestroyView");
    }

    public static Fragment newInstance(int position) {
        MyFragment.position = position;
        MyFragment homeFragment = new MyFragment();
        return homeFragment;
    }
}
