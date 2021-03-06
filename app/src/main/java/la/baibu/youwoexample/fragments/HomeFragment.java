package la.baibu.youwoexample.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import la.baibu.youwoexample.MainActivity;
import la.baibu.youwoexample.R;
import la.baibu.youwoexample.ui.home.CustomViewActivity;
import la.baibu.youwoexample.ui.home.PushReciverActivity;
import la.baibu.youwoexample.ui.home.TranslucentActivity;

/**
 * Created by minna_Zhou on 2016/11/23 0023.
 * 首页的第一个fragment
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static int mPosition;
    private MainActivity mainActivity;
    private Button btn_goto;
    private Button btn_goto2;
    private Button btn_goto3;

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
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_goto = (Button) view.findViewById(R.id.btn_goto);
        btn_goto2 = (Button) view.findViewById(R.id.btn_goto2);
        btn_goto3 = (Button) view.findViewById(R.id.btn_goto3);
        btn_goto.setOnClickListener(this);
        btn_goto2.setOnClickListener(this);
        btn_goto3.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_goto:
                Intent intent = new Intent(mainActivity, PushReciverActivity.class);
                mainActivity.startActivity(intent);
                break;
            case R.id.btn_goto2:
                Intent intent2 = new Intent(mainActivity,TranslucentActivity.class);
                mainActivity.startActivity(intent2);
                break;
            case R.id.btn_goto3:
                Intent intent3 = new Intent(mainActivity,CustomViewActivity.class);
                mainActivity.startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
