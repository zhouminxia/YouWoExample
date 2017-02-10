package la.baibu.youwoexample.utils.asynchttp;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * 网络请求方法
 */
public class AsyncHttpHelper {

    private static final String TAG = "HttpClient";

    private static final int TIME_OUT_MILLIS = 30 * 1000;

    private static AsyncHttpClient client = null;

    public static void get(Context context, String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {

//		long time = System.currentTimeMillis();
//		params.put("t", time);
//		params.put("pwd", CryptUtil.enCrypt(time + ""));
//
//        String workerId = PreferenceUtils.getPrefString(context, Contants.BAIBU_SELLER_SALESMAN_ID, "");
//        params.put("workerId", workerId);
//        params.put("adminId", workerId);
//
//        String accessToken = PreferenceUtils.getPrefString(context, Contants.PRE_ACCESS_TOKEN, "");
//        params.put("accessToken", accessToken);
//
//		String appVersion = PhoneInfoUtil.getVerName(context);
//		params.put("appVersion", appVersion);
//
//        //设备号唯一性
//        String deviceId = phoneInfoUtil.getDeviceId();
//        params.put("deviceId", deviceId);
//
//        if (!(ApiContants.getRootUrl() + ApiContants.Urls.LOGIN_ACCOUNT).equals(url)) {
//            int userId = PreferenceUtils.getPrefInt(context, Contants.Preference.userCode, -1);
//            if (userId != -1) {
//                params.put("workerId", userId + "");
//            }
//            String token = PreferenceUtils.getPrefString(context,Contants.Preference.userToken,"");
//            String ticket = PreferenceUtils.getPrefString(context,Contants.Preference.userTiket,"");
//            if (!TextUtil.isEmpty(token) && !TextUtil.isEmpty(ticket)) {
//                params.put("token", token);
//                params.put("ticket", ticket);
//            } else {
//                SystemUtil.exitToLoginActvity(context, "token为空，请重新登录！");
//            }
//        }

        getInstance().setTimeout(TIME_OUT_MILLIS);
        getInstance().setConnectTimeout(TIME_OUT_MILLIS);
        getInstance().setResponseTimeout(TIME_OUT_MILLIS * 2);
//        getInstance().addHeader("X-Baibu-Agent", "BaibuBusiness/1.0");
        getInstance().addHeader("xInnerAgent", "android");
        getInstance().get(url, params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {

//		long time = System.currentTimeMillis();
//		params.put("t", time);
//		params.put("pwd", CryptUtil.enCrypt(time + ""));
//
//        String workerId = PreferenceUtils.getPrefString(context, Contants.BAIBU_SELLER_SALESMAN_ID, "");
//        params.put("workerId", workerId);
//
//        String accessToken = PreferenceUtils.getPrefString(context, Contants.PRE_ACCESS_TOKEN, "");
//        params.put("accessToken", accessToken);
//
//		String appVersion = PhoneInfoUtil.getVerName(context);
//		params.put("appVersion", appVersion);
//
//        if (!(ApiContants.getRootUrl() + ApiContants.Urls.LOGIN_ACCOUNT).equals(url)) {
//            int userId = PreferenceUtils.getPrefInt(context, Contants.Preference.userCode, -1);
//            if (userId != -1) {
//                params.put("workerId", userId + "");
//            }
//            String token = PreferenceUtils.getPrefString(context,Contants.Preference.userToken,"");
//            String ticket = PreferenceUtils.getPrefString(context,Contants.Preference.userTiket,"");
//            if (!TextUtil.isEmpty(token) && !TextUtil.isEmpty(ticket)) {
//                params.put("token", token);
//                params.put("ticket", ticket);
//            } else {
//                SystemUtil.exitToLoginActvity(context, "token为空，请重新登录！");
//            }
//        }
//
//        XLog.i("封装好的params="+params.toString());
        getInstance().setTimeout(TIME_OUT_MILLIS);
        getInstance().setConnectTimeout(TIME_OUT_MILLIS);
        getInstance().setResponseTimeout(TIME_OUT_MILLIS * 2);
//        getInstance().addHeader("X-Baibu-Agent", "BaibuBusiness/1.0");
        getInstance().addHeader("xInnerAgent", "android");
        getInstance().post(url, params, responseHandler);


    }

    /**
     * 单例模式
     *
     * @return
     */
    private synchronized static AsyncHttpClient getInstance() {
        if (null == client) {
            client = new AsyncHttpClient();
        }
        return client;
    }
}
