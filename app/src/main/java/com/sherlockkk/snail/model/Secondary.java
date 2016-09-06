package com.sherlockkk.snail.model;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * @author SongJian
 * @created 2016/3/15.
 * @e-mail 1129574214@qq.com
 */
@AVClassName("Secondary")
public class Secondary extends AVObject {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public Secondary() {
        super();
    }

    public Secondary(Parcel in) {
        super(in);
    }

    private AVUser owner;
    private String name;
    private String description;
    private String costPrice;
    private String cururentPrice;
    private List picUrlList;
    private String catgory;

    public AVUser getOwner() {
        return getAVUser("owner");
    }

    public void setOwner(AVUser owner) {
        put("owner", owner);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public String getCostPrice() {
        return getString("costPrice");
    }

    public void setCostPrice(String costPrice) {
        put("costPrice", costPrice);
    }

    public String getCururentPrice() {
        return getString("cururentPrice");
    }

    public void setCururentPrice(String cururentPrice) {
        put("cururentPrice", cururentPrice);
    }

    public List getPicUrlList() {
        return getList("picUrlList");
    }

    public void setPicUrlList(List picUrlList) {
        put("picUrlList", picUrlList);
    }

    public String getCatgory() {
        return getString("catgory");
    }

    public void setCatgory(String catgory) {
        put("catgory", catgory);
    }

    @Override
    public String toString() {
        return "Secondary{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", costPrice=" + costPrice +
                ", cururentPrice=" + cururentPrice +
                ", picUrlList=" + picUrlList +
                ", catgory='" + catgory + '\'' +
                '}';
    }
}
