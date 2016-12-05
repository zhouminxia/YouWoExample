package la.baibu.youwoexample.bean;

import java.io.Serializable;

/**
 * Created by minna_Zhou on 2016/12/3 0003.
 * 选择图片
 */
public class SelectImageBean implements Serializable {
    private int imageType;
    private String imagePath;

    public static final int TYPE_DEFAULT_IMAGE = 1;//加号类型
    public static final int TYPE_IMAGE = 2;//图片类型

    public SelectImageBean(int imageType, String imagePath) {
        this.imageType = imageType;
        this.imagePath = imagePath;
    }

    public SelectImageBean() {
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
