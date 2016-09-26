//package com.sherlockkk.snail.utils;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.support.design.widget.Snackbar;
//import android.widget.Toast;
//
//import com.avos.avoscloud.AVException;
//import com.avos.avoscloud.AVFile;
//import com.avos.avoscloud.AVOSServices;
//import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVQuery;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.FindCallback;
//import com.avos.avoscloud.ProgressCallback;
//import com.avos.avoscloud.SaveCallback;
//import com.sherlockkk.snail.activity.SecondaryPubActivity;
//import com.sherlockkk.snail.model.Secondary;
//import com.sherlockkk.snail.tools.ToolLog;
//
//import org.json.JSONException;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Stack;
//import java.util.Vector;
//
///**
// * @author SongJian
// * @created 2016/3/14.
// * @e-mail 1129574214@qq.com
// */
//
//public class AVOSServiceUtil {
//
//    /**
//     * 通过文件本地地址批量上传文件,并发布
//     *  @param context
//     * @param dialog
//     * @param secondary
//     * @param owner
//     * @param secondary_name
//     * @param secondary_description
//     * @param curPrice
//     * @param costPrice
//     * @param secondary_catgory
//     * @param imagePaths
//     */
//    public void uploadPic(final Context context, final Dialog dialog, final Secondary secondary, final AVUser owner, final String secondary_name, final String secondary_description, final String curPrice, final String costPrice, final String secondary_catgory, final ArrayList<String> imagePaths) {
//        final List<String> picUrl = new Stack<>();
//        for (int i = 0; i < imagePaths.size(); i++) {
//            try {
//                final AVFile avFile = AVFile.withAbsoluteLocalPath(owner.getObjectId() + "_" + secondary_name, imagePaths.get(i));
//                avFile.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(AVException e) {
//                        if (e == null) {
//                            picUrl.add(avFile.getUrl());
//                            if (picUrl.size() == imagePaths.size()) {
//                                pubSecondary(context, dialog, secondary, owner, secondary_name, secondary_description, curPrice, costPrice, secondary_catgory, picUrl);
//                                System.out.println(">>>>>>>>>" + picUrl);
//                            }
//                        } else {
//                            ToolLog.i(">>>>>>>>>>>>>>", "上传失败：" + e.getMessage());
//                        }
//                    }
//                });
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void pubSecondary(final Context context, final Dialog dialog, Secondary secondary, AVUser user, String name, String des, String curPirce, String costPrice, String catgory, List<String> picUrl) {
//        secondary.setOwner(user);
//        secondary.setName(name);
//        secondary.setDescription(des);
//        secondary.setCururentPrice(curPirce);
//        secondary.setCostPrice(costPrice);
//        secondary.setCatgory(catgory);
//        secondary.setPicUrlList(picUrl);
//        secondary.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    dialog.dismiss();
//                    Toast.makeText(context, "发布成功，请注意审核信息", Toast.LENGTH_SHORT).show();
//                } else {
//                    dialog.dismiss();
//                    try {
//                        String msg = JsonParseUtil.parseJSONObject(e.getMessage(), "error");
//                        Toast.makeText(context, "发布失败：" + msg, Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//
//    public List<Secondary> fetchSecondary() {
//        List<Secondary> lists = new ArrayList<>();
//        AVQuery<Secondary> query = AVQuery.getQuery(Secondary.class);
//        query.orderByDescending("createdAt");
//        try {
//            lists = query.find();
//        } catch (AVException e) {
//            e.printStackTrace();
//        }
//        return lists;
//    }
//
//}
