package la.baibu.youwoexample.okhttp;

import android.content.Context;

import java.io.File;
import java.util.Map;

/**
 * Http请求,封装了workerId
 * Created by liucanwen on 15/12/7.
 */
public class HttpHelper {

    private static volatile HttpHelper instance = null;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (null == instance) {
            synchronized (HttpHelper.class) {
                if (null == instance) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param params
     * @param responseHandler
     */
    public void post(Context context, String url, Map<String, String> params,
                     OkHttpResponseHandler responseHandler) {

        if (context == null)
            return;

//        if (!(ApiContants.getRootUrl() + ApiContants.Urls.LOGIN_ACCOUNT).equals(url)) {
//            int userId = PreferenceUtils.getPrefInt(context, Contants.Preference.userCode, -1);
//            if (userId != -1) {
//                params.put("workerId", userId + "");
//            }
//            String token = PreferenceUtils.getPrefString(context, Contants.Preference.userToken, "");
//            String ticket = PreferenceUtils.getPrefString(context, Contants.Preference.userTiket, "");
//            if (!TextUtil.isEmpty(token) && !TextUtil.isEmpty(ticket)) {
//                params.put("token", token);
//                params.put("ticket", ticket);
//            } else {
//                SystemUtil.exitToLoginActvity(context, "token为空，请重新登录！");
//            }
//        }
        OkHttpClientManager.postAsyn(url, params, responseHandler);

    }

    /**
     * 上传文件
     *
     * @param context
     * @param url
     * @param fileKeys
     * @param files
     * @param params
     * @param responseHandler
     */
    public void upload(Context context, String url, String[] fileKeys, File[] files, Map<String, String> params,
                       OkHttpResponseHandler responseHandler) {

        if (context == null)
            return;

//        if (!(ApiContants.getRootUrl() + ApiContants.Urls.LOGIN_ACCOUNT).equals(url)) {
//            int userId = PreferenceUtils.getPrefInt(context, Contants.Preference.userCode, -1);
//            if (userId != -1) {
//                params.put("workerId", userId + "");
//            }
//            String token = PreferenceUtils.getPrefString(context, Contants.Preference.userToken, "");
//            String ticket = PreferenceUtils.getPrefString(context, Contants.Preference.userTiket, "");
//            if (!TextUtil.isEmpty(token) && !TextUtil.isEmpty(ticket)) {
//                params.put("token", token);
//                params.put("ticket", ticket);
//            } else {
//                SystemUtil.exitToLoginActvity(context, "token为空，请重新登录！");
//            }
//        }

//        XLog.i(params.toString());

        OkHttpClientManager.getUploadDelegate().postAsyn(url, fileKeys, files, OkHttpClientManager.getInstance().map2Params(params), responseHandler);
    }
}
