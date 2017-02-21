package com.sherlockkk.snail.model;

import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Simon on 2016/9/10.
 */
@AVClassName("PartTimeJob")
public class PartTimeJob extends AVObject implements Parcelable{
    public static final Creator CREATOR = AVObjectCreator.instance;
    private String activityTitle; // 兼职标题
    private String activityDescription; // 兼职描述
    private String activityPersonAcount; // 兼职人数
    private String activityTime; // 兼职活动开始时间
    private String deadTime; // 报名截止时间
    private String activityPlace; // 兼职地点
    private String activityDetail;// 兼职详情
    private String addpicBase64;// 添加图片的Base64编码
    private String signupUsername;
    private String signupPhonenum;
    private String signupTime;

    public PartTimeJob() {
    }
    public String getSignupUsername()
    {
        return  getString("signupUsername");
    }

    public void setSignupUsername(String signupUsername) {
        put("signupUsername",signupUsername);
    }

    public String getSignupTime() {
        return getString("signupTime");
    }

    public void setSignupTime(String signupTime) {
        put("signupTime",signupTime);
    }

    public String getSignupPhonenum() {
       return getString("signupPhonenum");
    }

    public void setSignupPhonenum(String signupPhonenum) {
        put("signupPhonenum",signupPhonenum);
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