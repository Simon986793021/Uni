package com.sherlockkk.snail.activity;

import android.app.Dialog;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.tools.ToolDialog;
import com.sherlockkk.snail.tools.ToolLog;
import com.sherlockkk.snail.tools.ToolString;
import com.sherlockkk.snail.ui.EditText.ClearableEditText;
import com.sherlockkk.snail.ui.EditText.PasswordInputView;
import com.sherlockkk.snail.ui.EditText.PhoneTextWatcher;
import com.sherlockkk.snail.ui.WheelView;
import com.sherlockkk.snail.utils.JsonParseUtil;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Calendar;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private static String TAG = "RegisterActivity";
    public static  String USEROBJECTID;
    public static final String DATEPICKER_TAG = "datepicker";
    private Toolbar toolbar;
    private ClearableEditText nickName;
    private ClearableEditText phoneNumber;
    private ClearableEditText password;
    private PasswordInputView verificationCode;
    private Button btn_register;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout_info;
    private Spinner spinner;
    private TextView select_gender;
    private TextView select_birth;
    DatePickerDialog datePickerDialog;
    AVUser user = new AVUser();
    private String school;
    private String gender;
    private String birthDay;


    @Override
    protected void setListeners() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_register);
        final Calendar calendar = Calendar.getInstance();

        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
        }
    }

    @Override
    protected void findViews() {
        initToolbar();
        linearLayout = (LinearLayout) findViewById(R.id.ll_registerInfo);
        nickName = (ClearableEditText) findViewById(R.id.et_nickname_register);
        phoneNumber = (ClearableEditText) findViewById(R.id.et_username_register);
        phoneNumber.addTextChangedListener(new PhoneTextWatcher(phoneNumber));
        password = (ClearableEditText) findViewById(R.id.et_password_register);
        verificationCode = (PasswordInputView) findViewById(R.id.et_verification_code);
        verificationCode.isShowContent(true);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        linearLayout_info = (LinearLayout) findViewById(R.id.ll_personalInfo);
        spinner = (Spinner) findViewById(R.id.spinner_select_school);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_school, R.layout.layout_selectchool_spinner);
        adapter.setDropDownViewResource(R.layout.layout_selectchool_spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        select_gender = (TextView) findViewById(R.id.tv_select_gender);
        select_gender.setOnClickListener(this);
        select_birth = (TextView) findViewById(R.id.tv_select_birth);
        select_birth.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        toolbar.setTitle("注册新用户");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String nName = nickName.getText().toString().trim();
                String pWord = password.getText().toString().trim();
                String pNumber = phoneNumber.getText().toString().trim();
                if (isEmpty(nName, pNumber, pWord)) {
                    registerAction(nName, pWord, pNumber);
                }
                break;
            case R.id.tv_select_gender:
                View view = LayoutInflater.from(this).inflate(R.layout.item_select_gender, null);
                WheelView wheelView = (WheelView) view.findViewById(R.id.wv_select_gender);
                wheelView.setItems(Arrays.asList(getResources().getStringArray(R.array.select_gender)));
                wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        select_gender.setText(item);
                        gender = item;
                    }
                });
                new AlertDialog.Builder(this).setTitle("选择性别").setView(view).setPositiveButton("OK", null).show();
                break;
            case R.id.tv_select_birth:
                datePickerDialog.setVibrate(true);
                datePickerDialog.setYearRange(1980, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
                break;
            default:
                break;
        }
    }

    /*出生日期选择*/
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        int m = month + 1;//月份要加一
        birthDay = year + "-" + m + "-" + day;
        select_birth.setText(year + "-" + m + "-" + day);
    }

    //Spinner的选择监听

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CharSequence[] s = getResources().getTextArray(R.array.select_school);
        school = String.valueOf(s[position]);
        ToolLog.i("选择学校是：", String.valueOf(s[position]));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        school = null;
    }


    boolean isNext = false;
    Dialog dialog;

    /*注册按钮点击*/
    private void registerAction(String nNickName, String pWord, String pNumber) {
        if (isFinal) {
            //在这里提交个人信息到服务器
            dialog = ToolDialog.showSpinnerDialog(RegisterActivity.this, "提交中，请稍候...");
            double account = 0.00;//账户金额
            int level = 0;//等级
            int exp = 0;//经验值
            if (school == null || school.equals("请选择学校")) {
                showShortToast(RegisterActivity.this, "请选择你的学校");
                return;
            }
            if (gender == null) {
                showShortToast(RegisterActivity.this, "请选择你的性别");
                return;
            }
            if (birthDay == null) {
                showShortToast(RegisterActivity.this, "请选择你的生日,说不定会有神秘礼物呢");
                return;
            }
            user.put("gender", gender);
            user.put("birthDay", birthDay);
            user.put("school", school);
            user.put("account", account);
            user.put("level", level);
            user.put("exp", exp);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    dialog.dismiss();
                    if (e == null) {
                        startActivity(LoginActivity.class, null, true);
                        USEROBJECTID=user.getObjectId();
                    } else {
                        showShortToast(RegisterActivity.this, "提交出错了，稍后试试吧~");
                    }
                }
            });
            return;
        }

        if (isNext) {
            String vCode = verificationCode.getText().toString();
            verifiyCode(vCode);
            return;
        }

        String phoneNum = ToolString.removeAllSpace(pNumber);
        if (phoneNum == null || phoneNum.equals("")) {
            showShortToast(RegisterActivity.this, "手机号码不能为空");
            return;
        } else if (phoneNum.length() < 11) {
            showShortToast(RegisterActivity.this, "请输入正确的手机号码");
            return;
        }
        user.setUsername(nNickName);
        user.setPassword(pWord);
        user.setMobilePhoneNumber(phoneNum);
        registNoVerify(user);
    }


    /*注册未验证账户,获取验证码*/
    private void registNoVerify(AVUser user) {
        dialog = ToolDialog.showSpinnerDialog(RegisterActivity.this, "获取验证码...");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                dialog.dismiss();
                if (e == null) {
                    isNext = true;
                    nickName.setVisibility(View.GONE);
                    phoneNumber.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    verificationCode.setVisibility(View.VISIBLE);
                    btn_register.setText("验证");
                } else {
                    try {
                        String msg = JsonParseUtil.parseJSONObject(e.getMessage(), "error");
                        showShortToast(RegisterActivity.this, "注册未验证账户失败:" + msg);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    boolean isFinal = false;

    private void verifiyCode(String vCode) {
        dialog = ToolDialog.showSpinnerDialog(RegisterActivity.this, "验证中...");
        final String code = ToolString.removeAllSpace(vCode);
        if (code == null || code.equals("")) {
            showShortToast(RegisterActivity.this, "验证码不能为空");
            return;
        }
        AVUser.verifyMobilePhoneInBackground(code, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                dialog.dismiss();
                if (e == null) {
                    //验证成功,进入下一步
                    isFinal = true;
                    verificationCode.setVisibility(View.GONE);
                    linearLayout_info.setVisibility(View.VISIBLE);
                    showShortToast(RegisterActivity.this, "欢迎来到Uni,请补全个人信息");
                    btn_register.setText("完成");
                } else {
                    showShortToast(RegisterActivity.this, "验证码错误，请重试");
                }
            }
        });
    }

    /*判断相关输入是否为空*/
    public boolean isEmpty(String nickName, String phoneNumber, String password) {
        if (TextUtils.isEmpty(nickName)) {
            showShortToast(this, "昵称不能为空");
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            showShortToast(this, "手机号码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            showShortToast(this, "密码不能为空");
            return false;
        }
        return true;
    }


    /*倒计时类*/
    class CodeCountDown extends CountDownTimer {

        public CodeCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            btn_VCode.setClickable(false);
//            btn_VCode.setText(millisUntilFinished / 1000 + "秒后可重新获取");
        }

        @Override
        public void onFinish() {
//            btn_VCode.setText("重新获取验证码");
//            btn_VCode.setClickable(true);
        }
    }


}