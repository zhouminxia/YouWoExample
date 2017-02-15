package la.baibu.youwoexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by minna_Zhou on 2017/2/15 0015.
 */
public class ResultActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imageView = (ImageView) findViewById(R.id.iv_pic);
        String path = getIntent().getStringExtra("path");

        //==========bitmap是横屏的，所以需要设置角度
//        if (!TextUtils.isEmpty(path)) {
////            int bitmapDegree = CameraUtil.getBitmapDegree(path);
////            Toast.makeText(ResultActivity.this, "角度为：" + bitmapDegree, Toast.LENGTH_LONG).show();
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            if (bitmap != null) {
//                imageView.setImageBitmap(bitmap);
//            }
//        }

        try {
            FileInputStream fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            Bitmap bitmap1 = bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap1 != null) {
                imageView.setImageBitmap(bitmap1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
