package la.baibu.youwoexample.utils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;

import la.baibu.youwoexample.MyApplication;

/**
 * 百度定位相关工具
 */
public class BaiDuLocationUtil {

    private static LocationClient mLocClient;
    public static LocationClientOption bd_option;
    private static ArrayList<BDLocationListener> callBacks;
    private static BDLocation tempLocation;
    private static long oldTime = 0;

    public static void initLocationClient() {//在application中初始化一次
        if (mLocClient != null)
            return;
        mLocClient = new LocationClient(MyApplication.instance);
        // 默认百度的option
        bd_option = new LocationClientOption();
        bd_option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        bd_option.setOpenGps(true);// 打开gps
        bd_option.setCoorType("wgs84"); // 设置坐标类型
        bd_option.setIsNeedAddress(true);
        bd_option.setAddrType("all");
        bd_option.setScanSpan(2000);
        mLocClient.setLocOption(bd_option);
        mLocClient.registerLocationListener(MylocationListener);
        callBacks = new ArrayList<BDLocationListener>();
    }

    public static void stopLocation() {
        mLocClient.stop();
    }

    public static void setOption(LocationClientOption option) {
        mLocClient.setLocOption(option);
    }

    static BDLocationListener MylocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            tempLocation = location;
            int length = callBacks.size();
            for (int i = 0; i < length; i++) {
                callBacks.get(i).onReceiveLocation(location);
            }
            callBacks.clear();
            mLocClient.stop();
        }

    };

    public static void requestLocation(BDLocationListener callBack) {
        long time = System.currentTimeMillis();
        if (time - oldTime < 10000 && tempLocation != null) {
            callBacks.add(callBack);
            MylocationListener.onReceiveLocation(tempLocation);
            mLocClient.stop();
            return;
        }

        if (callBack == null) {
            mLocClient.stop();
            return;
        }
        if (callBacks.size() == 0) {
            mLocClient.start();
            mLocClient.requestLocation();
            oldTime = time;
        }
        callBacks.add(callBack);
    }
}
