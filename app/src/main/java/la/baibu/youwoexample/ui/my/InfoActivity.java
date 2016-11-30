package la.baibu.youwoexample.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2016/11/30 0030.
 */
public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
    }

    private void init() {
        setTitle("设置照片墙");
    }
}
