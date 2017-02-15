package la.baibu.youwoexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by minna_Zhou on 2017/2/15 0015.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button button2;
    private Button button3;
    private ImageView imageview;
    private String photoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.btn);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        imageview = (ImageView) findViewById(R.id.imageView);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        String absolutePath = Environment.getExternalStorageDirectory().getPath();
        photoPath = absolutePath + "/" + "temp"+System.currentTimeMillis()+".png";
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        } else if (v.getId() == R.id.btn2) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(new File(photoPath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//更改系统存储图片的路径
            startActivityForResult(intent, 2);
        }else if(v.getId()==R.id.btn3){
            Intent intent = new Intent(TestActivity.this,CustomPhotoActivity.class);
            startActivityForResult(intent, 3);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//data返回的不是原图，是缩略图
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {//获取的是缩略图，清晰度不好
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    if (bitmap != null) {
                        imageview.setImageBitmap(bitmap);
                    }
                }

            } else if (requestCode == 2) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(photoPath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    imageview.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
