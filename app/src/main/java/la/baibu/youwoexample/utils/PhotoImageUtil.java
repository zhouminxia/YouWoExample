package la.baibu.youwoexample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import la.baibu.youwoexample.ActionSheetDialog;
import la.baibu.youwoexample.MyApplication;

public class PhotoImageUtil {
    /* 头像名称 */
    private static String PHOTO_FILE_NAME = "youwoexample_photo.jpg";
    private static String PHOTO_FILE_CUT_NAME = "youwoexample_photo_cut.jpg";
    public static File appDir;
    public static final int PHOTO_REQUEST_CAMERA = 0xF1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 0xF2;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 0xF3;// 剪切后的图片

    /**
     * 初始化目录
     */
    public static void initDir() {
        appDir = FileUtil.getDiskCacheDir(MyApplication.instance, "pic");
        System.out.println("--appDir=" + appDir.getAbsolutePath().toString());
        // --appDir=/storage/emulated/0/Android/data/la.baibu.youwoexample/cache/pic
        if (!appDir.exists()) {
            appDir.mkdirs();
        } else {
            if (!appDir.isDirectory()) {
                appDir.delete();
                appDir.mkdirs();
            }
        }
    }

    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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

    private static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (degress == 0) {
            return bitmap;
        }
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    public static byte[] compressImage(Bitmap image, Boolean ifCompress, int maxSize) {
        if (image == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        if (ifCompress) {
            int options = 100;
            while (baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 5;// 每次都减少10
            }
        }
        return baos.toByteArray();
    }

    public static byte[] compressImage(Bitmap image, Boolean ifCompress) {
        return compressImage(image, ifCompress, 200);
    }

    /**
     * 处理图片的入口
     *
     * @param srcPath
     * @return
     */
    public static byte[] getimage(String srcPath, Boolean ifCompress) {
        if (TextUtils.isEmpty(srcPath)) return null;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        String[] src = srcPath.split("://");
        // 传进来的路径格式为file://...
        if (src.length == 2) {
            if (src[0].equals("file") && !TextUtils.isEmpty(src[1])) {
                srcPath = src[1];
            }
        }

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1280f;// 这里设置高度为800f
        float ww = 680f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if (bitmap == null) return null;
        return compressImage(rotateBitmap(bitmap, readPictureDegree(srcPath)),
                ifCompress);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * byte->file
     *
     * @param b
     * @param file
     */
    public static void saveByte2file(byte[] b, File file) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bitmap->file
     *
     * @param bmp
     * @param file
     * @return
     */
    public static boolean saveBitmap2file(Bitmap bmp, File file) {
        CompressFormat format = CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }

    /**
     * Bitmap->Bytes
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        if (url == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Uri->FileSrc
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, proj,
                    null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    res = cursor.getString(column_index);
                }
                cursor.close();
            } else {
                File file = new File(contentUri.getPath());
                if (file.exists()) {
                    res = file.getAbsolutePath();
                }
            }
        } catch (Error e) {
            res = null;
        }
        return res;
    }

    /**
     * 上传图片统一接口
     *
     * @param context
     */
    public static void uploadPic(String tile, final Context context) {
        uploadPic(tile, context, null, null);
    }

    public static void uploadPic(String tile, final Context context,
                                 OnDismissListener dismissListener, OnCancelListener cancelListener) {
        new ActionSheetDialog(context, dismissListener, cancelListener)
                .builder()
                .setTitle(tile)
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("立即拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which, String name) {
                                camera(context);
                            }
                        })
                .addSheetItem("从本地相册选取", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which, String name) {
                                gallery(context);
                            }
                        }).show();
    }

    // * 从相机获取
    public static String camera(Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PHOTO_FILE_NAME = "youwoexample_photo" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, PHOTO_FILE_NAME);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        } else {
            Toast.makeText(context, "请检查手机存储卡", Toast.LENGTH_LONG).show();
        }
        ((Activity) context).startActivityForResult(intent,
                PHOTO_REQUEST_CAMERA);
        //拍照存储的地方
        return file.getPath();
    }

    private static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    // * 从相册获取
    public static void gallery(Context context) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        ((Activity) context).startActivityForResult(intent,
                PHOTO_REQUEST_GALLERY);
    }

    // *本地获取剪切照片
    public static void crop(Context context, Uri uri, int size) {
        PHOTO_FILE_NAME = "youwoexample_photo" + System.currentTimeMillis() + ".jpg";
        PHOTO_FILE_CUT_NAME = "youwoexample_cut_photo" + System.currentTimeMillis()
                + ".jpg";
        File tempFile = new File(appDir, PHOTO_FILE_CUT_NAME);
        File recutfile = new File(appDir, PHOTO_FILE_NAME);
        // 处理下否则有些超大的图片会横着进入剪切
        byte[] temp = getimage(getRealPathFromURI(context, uri), false);
        if (temp == null) {
            Toast.makeText(context, "抱歉，图像获取失败", Toast.LENGTH_LONG).show();
            return;
        }
        saveByte2file(temp, recutfile);
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(recutfile), "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("output", Uri.fromFile(tempFile)); // 专入目标文件
        intent.putExtra("outputFormat", "JPEG"); // 输入文件格式
        ((Activity) context).startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // *本地获取剪切照片
    public static void crop(Context context, Uri uri) {
        crop(context, uri, 600);
    }

    // * 拍照剪切照相的图片
    public static void crop(Context context, int size) {
        File tempFile = new File(appDir, PHOTO_FILE_NAME);
        File tempFile2 = new File(appDir, PHOTO_FILE_CUT_NAME);
        byte[] temp = getimage(tempFile.getPath(), false);
        if (temp == null) {
            Toast.makeText(context, "抱歉，图像获取失败", Toast.LENGTH_LONG).show();
            return;
        }
        saveByte2file(temp, tempFile);
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(tempFile), "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("output", Uri.fromFile(tempFile2)); // 专入目标文件
        intent.putExtra("outputFormat", "JPEG"); // 输入文件格式
        ((Activity) context).startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public static void crop(Context context) {
        crop(context, 600);
    }


    /**
     * 缩放图片
     *
     * @param icon
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap icon, int h) {
        // 缩放图片
        Matrix m = new Matrix();
        float sx = (float) 2 * h / icon.getWidth();
        float sy = (float) 2 * h / icon.getHeight();
        m.setScale(sx, sy);
        // 重新构造一个2h*2h的图片
        return Bitmap.createBitmap(icon, 0, 0, icon.getWidth(),
                icon.getHeight(), m, false);
    }

    /**
     * 保存图片
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // insertMediaStore(context, file);
    }

    /**
     * 刷新图库
     */
    public static void insertMediaStore(Context context, File file) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(file)));
    }
}
