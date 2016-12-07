package la.baibu.youwoexample;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;

import la.baibu.youwoexample.utils.LocationService;

/**
 * Created by minna_Zhou on 2016/11/24 0024.
 */
public class MyApplication extends Application {
    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());//百度地图定位的SDK初始化
        Fresco.initialize(this);
    }

    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        synchronized (MyApplication.class) {
            return instance;

        }
    }

}
