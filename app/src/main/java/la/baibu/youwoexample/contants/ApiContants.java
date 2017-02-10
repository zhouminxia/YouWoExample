package la.baibu.youwoexample.contants;

/**
 * Created by minna_Zhou on 2017/1/17 0017.
 */
public class ApiContants {

    public static final class Urls {
        public final static String BASE_URL = "http://zhjapi.baibu.la/api/invoke?v=2.0&exec=";//正式机
        public final static String QA_URL = "http://qa.zhjapi.baibu.la/api/invoke?v=2.0&exec=";//正式机
        public final static String DEV_URL = "http://dev.zhjapi.baibu.la/api/invoke?v=2.0&exec=";//正式机

        public final static String LOGIN_ACCOUNT = "YpbUserLogin";//登录
    }

    public static String getRootUrl() {
        return ApiContants.Urls.QA_URL;
    }
}
