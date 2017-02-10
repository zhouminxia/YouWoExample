package la.baibu.youwoexample.okhttp;


import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by liucanwen on 15/12/30.
 */
public class HttpStatusUtil {
    //token失效码
    public static final int EXITCODE = 103;

    public static final int STATUS_CODE_UPDATE = 3;//需要更新
    public static final int STATUS_CODE_ILEGAL_PARAMS = 2;//非法参数
    public static final int STATUS_CODE_SUCCESS = 1;//请求成功
    public static final int STATUS_CODE_FAILURE = 0;//请求失败


    // 得到状态码
    public static boolean getStatus(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int statusCode = jsonObject.getInt("statusCode");

            if (STATUS_CODE_SUCCESS == statusCode) {
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }


    // 得到状态码
    public static int getIntStatus(String json) {
        int statusCode = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            statusCode = jsonObject.getInt("statusCode");
        } catch (Exception ex) {
        }
        return statusCode;
    }

    /**
     * 得到状态提示
     *
     * @param json
     * @return
     */
    public static String getStatusMsg(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String message = jsonObject.getString("message");

            if (!TextUtils.isEmpty(message)) {
                return message;
            }
        } catch (Exception ex) {

            return ex.getMessage();
        }
        return "服务器异常，返回格式不对";
    }

}
