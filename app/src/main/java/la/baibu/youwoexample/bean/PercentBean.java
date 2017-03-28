package la.baibu.youwoexample.bean;

import java.io.Serializable;

/**
 * Created by minna_Zhou on 2017/3/28 0028.
 * 百分比bean
 */
public class PercentBean implements Serializable {
    private float percent;//占多少百分比
    private float percentAngel;//占多少百分比
    private float value;//多少值=========用户关心


    private String name;//名字
    private int color;//颜色

    public PercentBean() {
    }

    public float getPercentAngel() {
        return percentAngel;
    }

    public void setPercentAngel(float percentAngel) {
        this.percentAngel = percentAngel;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
