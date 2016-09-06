package com.sherlockkk.snail.tools;

import android.app.Activity;
import android.app.ProgressDialog;

import com.sherlockkk.snail.R;

/**
 * @author SongJian
 * @created 2016/3/14.
 * @e-mail 1129574214@qq.com
 */
public class ToolDialog {

    public static ProgressDialog showSpinnerDialog(Activity activity, String msg) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(msg);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static ProgressDialog showSpinnerDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getString(R.string.hardLoading));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
}
