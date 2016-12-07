package la.baibu.youwoexample.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import la.baibu.youwoexample.ActionSheetDialog;
import la.baibu.youwoexample.MyApplication;
import la.baibu.youwoexample.R;

/**
 * Created by minna_Zhou on 2016/12/6 0006.
 * 拍照工具
 * 照片还是保存永久点
 * 在SD卡，不在/data/data/Android/包名/目录下
 */
public class CameraUtil {

    public static int CAMERA_REQUEST_CODE = 1000;
    public static String rootDir = Environment.getExternalStorageDirectory()
            + File.separator + "YOUWO/youwo_camera" + File.separator;//应用即使被卸载，这个文件夹也不会被删掉

    public static String fileName = "";

    // 调用系统照相机的方法
    public static File camera(Activity ac) {
        /*
         * Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
		 * startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
		 */
        File f = null;
        String name = "";
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                name = callTime();
                File dir = new File(rootDir);
                if (!dir.exists())
                    dir.mkdirs();

                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                f = new File(dir, name + ".jpeg");// localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                ac.startActivityForResult(intent, CAMERA_REQUEST_CODE);
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

    public static void selectLocalImageOrTakePhoto(String tile, final Context context,
                                                   DialogInterface.OnDismissListener dismissListener, DialogInterface.OnCancelListener cancelListener) {
        new ActionSheetDialog(context, dismissListener, cancelListener)
                .builder()
                .setTitle(tile)
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(context.getString(R.string.take_photo), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which, String name) {
//                                camera(context);
                            }
                        })
                .addSheetItem(context.getString(R.string.take_image_from_local), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which, String name) {
//                                gallery(context);
                            }
                        }).show();
    }

    // * 从相册获取
    public static void gallery(Context context) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        ((Activity) context).startActivityForResult(intent,
                CAMERA_REQUEST_CODE);//==============
    }

}
