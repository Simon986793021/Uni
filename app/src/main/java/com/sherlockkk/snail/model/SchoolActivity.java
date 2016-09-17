package com.sherlockkk.snail.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Administrator on 2016/9/10.
 */
@AVClassName("SchoolActivity")
public class SchoolActivity extends AVObject {
    public static final Creator CREATOR = AVObjectCreator.instance;
    private String activityTitle; // 活动标题
    private String activityDescription; // 活动描述
    private String activityPersonAcount; // 活动人数
    private String activityTime; // 活动时间
    private String deadTime; // 报名截止时间
    private String activityPlace; // 活动地点
    private String activityDetail;// 活动详情
    private String addpicBase64;// 添加图片的Base64编码

    public SchoolActivity() {
    }

    public String getPic() {
        return getString("addpicBase64");

    }

    public void setPic(String addpicBase64) {
        put("addpicBase64", addpicBase64);
    }

    public String getActivityDetail() {
        return getString("activityDetail");
    }

    public void setActivityDetail(String activityDetail) {
        put("activityDetail", activityDetail);
    }

    public String getActivityTitle() {
        return getString("activityTitle");
    }

    public void setActivityTitle(String activityTitle) {
        put("activityTitle", activityTitle);
    }

    public String getActivityDescription() {
        return getString("activityDescription");
    }

    public void setActivityDescription(String activityDescription) {
        put("activityDescription", activityDescription);
    }

    public String getActivityPersonAcount() {
        return getString("activityPersonAcount");
    }

    public void setActivityPersonAcount(String activityPersonAcount) {
        put("activityPersonAcount", activityPersonAcount);
    }

    public String getActivityTime() {
        return getString("activityTime");
    }

    public void setActivityTime(String activityTime) {
        put("activityTime", activityTime);
    }

    public String getDeadTime() {
        return getString("deadTime");
    }

    public void setDeadTime(String deadTime) {
        put("deadTime", deadTime);
    }

    public String getActivityPlace() {
        return getString("activityPlace");
    }

    public void setActivityPlace(String activityPlace) {
        put("activityPlace", activityPlace);
    }

}