package la.baibu.youwoexample.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import la.baibu.youwoexample.MyApplication;
import la.baibu.youwoexample.contants.RequestCode;

/**
 * Created by minna_Zhou on 2016/12/6 0006.
 * 拍照工具
 * 照片还是保存永久点
 * 在SD卡，不在/data/data/Android/包名/目录下
 */
public class CameraUtil {

    public static String rootDir = Environment.getExternalStorageDirectory()
            + File.separator + "YOUWO/youwo_camera" + File.separator;//应用即使被卸载，这个文件夹也不会被删掉

    public static String fileName = "";

    // 调用系统照相机的方法
    public static File camera(Context ac) {
        File f = null;
        String name = "";
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                name = callTime();
                File dir = new File(rootDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                f = new File(dir, name + ".jpeg");// localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                ((Activity) ac).startActivityForResult(intent, RequestCode.CAMERA_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(ac, "没有找到储存目录", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ac, "没有储存卡", Toast.LENGTH_SHORT).show();
        }
        return f;
    }

    public static String callTime() {

        long backTime = new Date().getTime();

        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(backTime));

        int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH) + 1;

        int date = cal.get(Calendar.DAY_OF_MONTH);

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        int minute = cal.get(Calendar.MINUTE);

        int second = cal.get(Calendar.SECOND);

        String time = "" + year + month + date + hour + minute + second;

        return time;

    }


    /**
     * 发送广播，重新挂载SD卡
     */
    public static void sendBroadCaseRemountSDcard(File mPhotoFile) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(mPhotoFile); //out is your output file
                mediaScanIntent.setData(contentUri);
                MyApplication.getInstance().sendBroadcast(mediaScanIntent);
            } else {
                MyApplication.getInstance().sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }
        } catch (Exception e) {
        }
    }


    // * 从相册获取
    public static void gallery(Context context) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        ((Activity) context).startActivityForResult(intent,
                RequestCode.GALLERY_REQUEST_CODE);
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean isHaveCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的过滤器
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return newBitmap;
    }

}
