package la.baibu.youwoexample.bean;

import la.baibu.youwoexample.utils.HanziUtil;

/**
 * Created by minna_Zhou on 2017/3/7 0007.
 * 联系人bean
 */
public class Person {
    private String name;
    //拼音
    private String pinyin;
    //拼音首字母
    private String headerWord;

    public Person(String name) {
        this.name = name;
        this.pinyin = HanziUtil.getPinyin(name);
        headerWord = pinyin.substring(0, 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getHeaderWord() {
        return headerWord;
    }

    public void setHeaderWord(String headerWord) {
        this.headerWord = headerWord;
    }
}
