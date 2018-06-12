package com.example.huxianpei.mychart;



import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by HXP on 2016/12/30.
 */
public class YHTag implements Serializable, Comparable<YHTag> {
    private String id;
    private String short_name;
    private String name;
    private boolean checked = false;

    public YHTag() {
    }

    public YHTag(String typeName) {
        this.short_name = typeName;
    }

    public YHTag(String type, String typeName) {
        this.id = type;
        this.short_name = typeName;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    @Override
    public int compareTo(YHTag o) {
        Comparator c = Collator.getInstance(Locale.CHINA);
        return c.compare(this.short_name, o.getShort_name());
    }
}
