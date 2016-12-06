package la.baibu.youwoexample.utils.storeutils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by minna_Zhou on 2016/12/5 0005.
 */
public class ExternalCacheUtil {

    /**
     * 获取sd卡的缓存目录
     * @param context
     * @param uniqueName
     * @return
     */

    // --appCacheDir=/storage/emulated/0/Android/data/la.baibu.youwoexample/cache/
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = ("mounted".equals(Environment.getExternalStorageState()) || !isExternalStorageRemovable()) && getExternalCacheDir(context) != null ? getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public static boolean isExternalStorageRemovable() {
        boolean hasGingerBread = Build.VERSION.SDK_INT >= 9;
        return hasGingerBread ? Environment.isExternalStorageRemovable() : true;
    }


    public static File getExternalCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= 8) {
            return context.getExternalCacheDir();
        } else {
            String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
            return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
        }
    }
}
