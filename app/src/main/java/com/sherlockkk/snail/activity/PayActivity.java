package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.alipay.AlipayAPI;
import com.sherlockkk.snail.alipay.PayResult;
import com.sherlockkk.snail.model.SchoolActivity;
import com.sherlockkk.snail.utils.Utils;
import com.sherlockkk.snail.wxpay.Constants;
import com.sherlockkk.snail.wxpay.MD5;
import com.sherlockkk.snail.wxpay.Util;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static com.sherlockkk.snail.R.id.bt_sure;

/**
 * Created by Simon on 2016/12/10.
 */
public class PayActivity extends Activity implements View.OnClickListener{
    private Button alipaybutton;
    private Button wxpaybutton;
    private TextView backtextview;
    private TextView textView;
    private TextView supportitletextview;
    private TextView supporpricetextview;
    private static final int SDK_PAY_FLAG = 1;
    private Map<String,String> resultunifiedorder;
    private PayReq req;
    private StringBuffer sb;
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    private SchoolActivity schoolActivity;
    private ProgressDialog progressDialog;
    private  String price;
    private String objectId;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        backtextview= (TextView) findViewById(R.id.tv_back);
        textView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        textView.setText("支付");
        alipaybutton= (Button) findViewById(R.id.bt_alipay);
        wxpaybutton= (Button) findViewById(R.id.bt_wxpay);
        supporpricetextview= (TextView) findViewById(R.id.tv_activity_supportprice);
        supportitletextview= (TextView) findViewById(R.id.tv_activity_supporttitle);
        backtextview.setOnClickListener(this);
        alipaybutton.setOnClickListener(this);
        wxpaybutton.setOnClickListener(this);
        schoolActivity = (SchoolActivity) getIntent().getParcelableExtra("schoolactivity");
        supportitletextview.setText("赞助"+schoolActivity.getActivityTitle().toString());
        supporpricetextview.setText(schoolActivity.getSupportprice()+"元");
        req=new PayReq();
        sb=new StringBuffer();
        progressDialog=new ProgressDialog(this);
        saveObjectId();//存储活动的objectId，用于微信支付成功获取活动信息
    }

    private void saveObjectId() {
        objectId=schoolActivity.getObjectId();
        sharedPreferences=getSharedPreferences("objectId",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("ObjectId",objectId);
        editor.commit();
    }

    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Utils.showToast(PayActivity.this, "支付成功");
                        Utils.showNotification(PayActivity.this);//通知
                        saveObject();//在leancloud保存数据
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Utils.showToast(PayActivity.this,"支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Utils.showToast(PayActivity.this,"支付失败");
                        }
                    }
                    break;
                }
            }
        };
    };
                        /*
                       在用户中添加赞助的活动对象，在活动添加赞助者对象
                        */
    private void saveObject() {
        final AVUser avUser=AVUser.getCurrentUser();
        AVRelation<AVObject> relation=avUser.getRelation("SupportActivity");
        relation.add(schoolActivity);
        avUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null)
                {
                    schoolActivity.add("supporterPhonenum",avUser.getMobilePhoneNumber().toString());
                    AVRelation<AVObject> activityrelation=schoolActivity.getRelation("Supporter");
                    activityrelation.add(avUser);
                    schoolActivity.saveInBackground();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*
            返回
             */
            case R.id.tv_back:
                finish();
                break;
            /*
            支付宝支付
             */
            case R.id.bt_alipay:
                if (schoolActivity==null)
                {
                    schoolActivity=(SchoolActivity) getIntent().getParcelableExtra("schoolactivity");
                }

                    price=schoolActivity.getSupportprice();
                if (price!=null&&Utils.isNetworkAvailable(PayActivity.this)) {
                    showAliPaySelectDialog();
                }
                else {
                    Utils.showToast(PayActivity.this,"请检查是否有网络");
                }
                ;
                break;
            /*
            微信支付
             */
            case R.id.bt_wxpay:
                price=schoolActivity.getSupportprice();
                if (price!=null&&Utils.isNetworkAvailable(PayActivity.this))
                {
                    String urlString="https://api.mch.weixin.qq.com/pay/unifiedorder";
                    PrePayIdAsyncTask prePayIdAsyncTask=new PrePayIdAsyncTask();
                    prePayIdAsyncTask.execute(urlString);		//生成prepayId
                    showWXPayPaySelectDialog();
                }
                else {
                    Utils.showToast(PayActivity.this,"请检查是否有网络");
                }
                break;
            default:
                break;
        }
    }
    /*
    微信支付对话框
     */
    private void showWXPayPaySelectDialog() {
        final AlertDialog alertdialog=new AlertDialog.Builder(PayActivity.this).create();
        alertdialog.setCanceledOnTouchOutside(true);//点击旁边dialog消失
        View view= LayoutInflater.from(PayActivity.this).inflate(R.layout.dialog_pay,null,false);
        alertdialog.show();
        alertdialog.setContentView(view);
        alertdialog.getWindow().setGravity(Gravity.CENTER);
        Button surebutton= (Button) view.findViewById(bt_sure);
        Button cancelbutton= (Button) view.findViewById(R.id.bt_cancel);
        surebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.cancel();
                String phonenum=AVUser.getCurrentUser().getMobilePhoneNumber().toString();
                List<String> mylist=new ArrayList<String>();
                schoolActivity = (SchoolActivity) getIntent().getParcelableExtra("schoolactivity");

                mylist=schoolActivity.getList("supporterPhonenum");

                if (mylist!=null&&mylist.contains(phonenum))
                {
                    Utils.showToast(PayActivity.this,"非常感谢您的赞助，您已经赞助了");
                }
                else {
                    if (Utils.isWeixinAvilible(PayActivity.this)) {
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setTitle("前往支付...");
                        progressDialog.show();
                        genPayReq();//生成签名参数
                        sendPayReq();//调起支付

                    }
                    else
                    {
                        Utils.showToast(PayActivity.this,"请安装微信");
                    }
                }

            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.cancel();
            }
        });
    }

    private void showAliPaySelectDialog() {
        final AlertDialog alertdialog=new AlertDialog.Builder(PayActivity.this).create();
        alertdialog.setCanceledOnTouchOutside(true);//点击旁边dialog消失
        View view= LayoutInflater.from(PayActivity.this).inflate(R.layout.dialog_pay,null,false);
        alertdialog.show();
        alertdialog.setContentView(view);
        alertdialog.getWindow().setGravity(Gravity.CENTER);
        Button surebutton= (Button) view.findViewById(bt_sure);
        Button cancelbutton= (Button) view.findViewById(R.id.bt_cancel);
        surebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenum=AVUser.getCurrentUser().getMobilePhoneNumber().toString();
                List<String> mylist=new ArrayList<String>();
                schoolActivity = (SchoolActivity) getIntent().getParcelableExtra("schoolactivity");

                mylist=schoolActivity.getList("supporterPhonenum");

                if (mylist!=null&&mylist.contains(phonenum))
                {
                    Utils.showToast(PayActivity.this,"非常感谢您的赞助，您已经赞助了");
                }
                else
                {
                    new AliPayThread().start();
                }

                alertdialog.cancel();
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.cancel();
            }
        });
    }

    /**
     * 支付宝支付异步任务
     *
     * @author Simon
     */
    private class AliPayThread extends Thread {
        @Override
        public void run() {
            String payinfo=schoolActivity.getActivityTitle().toString();
            String price=schoolActivity.getSupportprice();
                String result = AlipayAPI.pay(PayActivity.this, "赞助"+payinfo,
                        "赞助"+payinfo, price);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
        }
    }


    /*
         * 调起微信支付
         */
    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
        progressDialog.dismiss();
    }
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        if (resultunifiedorder!=null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            req.packageValue = "prepay_id="+resultunifiedorder.get("prepay_id");
        }
        else {
           Utils.showToast(PayActivity.this,"生成订单号失败，请重新支付");
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        return appSign;
    }
    private class PrePayIdAsyncTask extends AsyncTask<String,Void, Map<String, String>>
    {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }
        @Override
        protected Map<String, String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url=String.format(params[0]);
            String entity=getProductArgs();
            byte[] buf=Util.httpPost(url, entity);
            String content = new String(buf);
            Map<String,String> xml=decodeXml(content);

            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            resultunifiedorder=result;
        }
    }

    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
        }
        return null;

    }
    private String getProductArgs() {
        // TODO Auto-generated method stub
        String priceee=schoolActivity.getSupportprice();
        int pri = 0;
        try {
             pri = (int) (100 * (Double.valueOf(priceee)) + 0.000001);
        }
        catch (Exception e)
        {

        }

            String lastPrice = pri + "";
            StringBuffer xml = new StringBuffer();
            try {
                String nonceStr = getNonceStr();
                xml.append("<xml>");
                List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
                packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
                packageParams.add(new BasicNameValuePair("body", "APP pay test"));
                packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
                packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
                packageParams.add(new BasicNameValuePair("notify_url", "https://www.baidu.com"));//写你们的回调地址
                packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
                packageParams.add(new BasicNameValuePair("total_fee", lastPrice));
                packageParams.add(new BasicNameValuePair("trade_type", "APP"));

                String sign = getPackageSign(packageParams);
                packageParams.add(new BasicNameValuePair("sign", sign));
                String xmlString = toXml(packageParams);
                return xmlString;
            } catch (Exception e) {
                // TODO: handle exception
            }

        return null;
    }

    //生成订单号,测试用，在客户端生成
    private String genOutTradNo() {
        Random random = new Random();
//		return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random=new Random();

        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    /**
     生成签名
     */

    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }
    /*
     * 转换成xml
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        return sb.toString();
    }
}
