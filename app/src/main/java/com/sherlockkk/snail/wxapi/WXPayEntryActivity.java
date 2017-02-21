package com.sherlockkk.snail.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.SchoolActivity;
import com.sherlockkk.snail.utils.Utils;
import com.sherlockkk.snail.wxpay.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode==0)
			{
				Utils.showToast(WXPayEntryActivity.this,"支付成功");
				Utils.showNotification(WXPayEntryActivity.this);//通知
				/**
				 * 在leancloud保存数据
				 */
				SharedPreferences sharedPreferences=getSharedPreferences("objectId",Activity.MODE_PRIVATE);
				String objectId=sharedPreferences.getString("ObjectId","");
				AVQuery<SchoolActivity> avQuery=new AVQuery<>("SchoolActivity");
				avQuery.getInBackground(objectId, new GetCallback<SchoolActivity>() {
					@Override
					public void done(final SchoolActivity schoolActivity, AVException e) {
						final AVUser avUser=AVUser.getCurrentUser();
						final AVRelation<AVObject> avRelation=avUser.getRelation("SupportActivity");
						avRelation.add(schoolActivity);
						avUser.saveInBackground(new SaveCallback() {
							@Override
							public void done(AVException e) {
								if (e==null)
								{
									schoolActivity.add("supporterPhonenum",avUser.getMobilePhoneNumber().toString());
									AVRelation<AVObject> schoolactivityrelation=schoolActivity.getRelation("Supporter");
									schoolactivityrelation.add(avUser);
									schoolActivity.saveInBackground();
								}
							}
						});
					}
				});
			}
			else if (resp.errCode==-2)
			{
				Utils.showToast(WXPayEntryActivity.this,"取消支付");
			}
			else {
				Utils.showToast(WXPayEntryActivity.this,"未知错误,您可以检查微信是否正常登陆");
			}

		}
	}
}