package la.baibu.youwoexample.bean;

import java.io.Serializable;

/**
 * Created by minna_Zhou on 2016/9/22 0022.
 * 版本更新bean
 */
public class Version implements Serializable {


    /**
     * code : 测试内容m5db
     * desc : 测试内容hd4u
     * device : 测试内容9r82
     * download : 测试内容5ux3
     * updateLvl : 88243
     * version : 测试内容w723
     */

    private String code;
    private String desc;
    private String device;
    private String download;
    private int updateLvl;//0 无更新 1 强制更新 2 非强制更新
    private String version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public int getUpdateLvl() {
        return updateLvl;
    }

    public void setUpdateLvl(int updateLvl) {
        this.updateLvl = updateLvl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Version{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", device='" + device + '\'' +
                ", download='" + download + '\'' +
                ", updateLvl=" + updateLvl +
                ", version='" + version + '\'' +
                '}';
    }
}
