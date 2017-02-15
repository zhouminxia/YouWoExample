package la.baibu.youwoexample;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by minna_Zhou on 2017/2/15 0015.
 */
public class CustomPhotoActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback {

    private Camera mCamera;
    private Button captureBtn;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {//完整拍摄的数据
            File file = new File(rootDir);
            if (!file.exists()) {
                file.mkdir();
            }
            File tempFile = new File(rootDir, System.currentTimeMillis() + "temp.jpg");
            //把data数据写入tempFile
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(data);
                fos.close();
                Intent intent = new Intent(CustomPhotoActivity.this, ResultActivity.class);
                intent.putExtra("path", tempFile.getAbsolutePath());
//                int bitmapDegree = CameraUtil.getBitmapDegree(tempFile.getAbsolutePath());
//                Toast.makeText(CustomPhotoActivity.this, "前一个页面角度为：" + bitmapDegree, Toast.LENGTH_LONG).show();
                CustomPhotoActivity.this.startActivity(intent);
                CustomPhotoActivity.this.finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_photo);
        captureBtn = (Button) findViewById(R.id.capture);
        captureBtn.setOnClickListener(this);


        mPreview = (SurfaceView) findViewById(R.id.surfaceview);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.capture) {
            capture();
        }
    }

    private void capture() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(800, 400);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {//对焦成功
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    /*获取系统的一个camera对象*/
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    /*
    开始预览相机，将camera和surfaceview进行绑定
     */
    private void startPreview(Camera camera, SurfaceHolder surfaceHolder) {
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.setDisplayOrientation(90);//系统默认的是横屏的
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置camera资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                startPreview(mCamera, mHolder);//开始预览
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }


    //=============surfaceview的三个回调
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //重启功能
        mCamera.stopPreview();
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        releaseCamera();
    }
}
