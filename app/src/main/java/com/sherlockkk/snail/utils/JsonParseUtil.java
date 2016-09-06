package com.sherlockkk.snail.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author SongJian
 * @created 2016/3/13.
 * @e-mail 1129574214@qq.com
 */
public class JsonParseUtil {
    public static String parseJSONObject(String json,String name) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String msg = (String) jsonObject.get(name);
        return msg;
    }
}
