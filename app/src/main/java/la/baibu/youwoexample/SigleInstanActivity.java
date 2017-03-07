package la.baibu.youwoexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by minna_Zhou on 2017/2/16 0016.
 */
public class SigleInstanActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleinstance);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        System.out.println("——onCreate");
    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("——onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("——onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("——onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("——onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("——onRestart");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Intent intent = new Intent(SigleInstanActivity.this, TestActivity.class);
            startActivityForResult(intent, 4);
        }
    }
}
