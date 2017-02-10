package la.baibu.youwoexample.okhttp;

import android.content.Context;

import com.squareup.okhttp.Request;

import org.json.JSONObject;


/**
 * 公共请求方法监听
 */
public class OkHttpResponseHandler<T> extends OkHttpClientManager.ResultCallback<String> {
	private static final String TAG = "MyAsyncHttpResponseHandler";

	public static final String ERROR_TIP = "网络不给力或者服务端异常！";

	private Context mContext;

	public OkHttpResponseHandler(Context context) {
		mContext = context;
	}

    @Override
    public void onBefore() {

        super.onBefore();
    }

    @Override
    public void onAfter() {

        super.onAfter();
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    @Override
    public void onResponse(Request request, String response) {

    }


    /**
     * 是否请求成功
     * @param json
     * @return
     */
    public boolean isRequestSuccess(String json){

        return getStatus(json);
    }

	// 得到状态码
	public boolean getStatus(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String statusCode = jsonObject.getString("status");

            if("true".equals(statusCode))
                return true;

		} catch (Exception ex) {
		}
        return false;
	}

}
