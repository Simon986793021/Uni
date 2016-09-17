package com.sherlockkk.snail.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.avos.avoscloud.AVUser;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.PhotoPickAdapter;
import com.sherlockkk.snail.adapter.SecondaryCatgoryAdapter;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.model.Secondary;
import com.sherlockkk.snail.model.SecondaryCatgory;
import com.sherlockkk.snail.tools.ToolDialog;
import com.sherlockkk.snail.ui.photopicker.PhotoPickerActivity;
import com.sherlockkk.snail.ui.photopicker.PhotoPreviewActivity;
import com.sherlockkk.snail.ui.photopicker.SelectModel;
import com.sherlockkk.snail.ui.photopicker.intent.PhotoPickerIntent;
import com.sherlockkk.snail.ui.photopicker.intent.PhotoPreviewIntent;
import com.sherlockkk.snail.utils.AVOSServiceUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * 二手商品发布
 *
 * @author SongJian
 * @created 2016/3/10.
 * @e-mail 1129574214@qq.com
 */
public class SecondaryPubActivity extends BaseActivity {
    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_PREVIEW_CODE = 2;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridView gridView;
    private int columnWidth;
    private PhotoPickAdapter pickAdapter;
    private Toolbar toolbar;
    private Spinner spinner_secondary_catgory;
    private EditText et_secondary_name;
    private EditText et_secondary_description;
    private EditText et_secondary_price_now;
    private EditText et_secondary_price;
    private String secondary_name;
    private String secondary_description;
    private String secondary_price_now;
    private String secondary_price;
    private String secondary_catgory;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_pulish_secondary);
        initToolbar();
        initSpinner();
    }

    Dialog dialog;
    AVOSServiceUtil avosServiceUtil = new AVOSServiceUtil();
    String picName;

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_publish_secondary);
        toolbar.setTitle("发布二手商品");
       // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.done://完成编辑后发布,图片要异步上传
                        secondary_name = et_secondary_name.getText().toString().trim();
                        secondary_description = et_secondary_description.getText().toString().trim();
                        secondary_price_now = et_secondary_price_now.getText().toString().trim();
                        secondary_price = et_secondary_price.getText().toString().trim();
                        if (isEmpty(secondary_name, secondary_description, secondary_price_now, secondary_catgory)) {
                            dialog = ToolDialog.showSpinnerDialog(SecondaryPubActivity.this, "正在发布...");
                            String curPrice = secondary_price_now;
                            String costPrice = secondary_price;
                            Secondary secondary = new Secondary();
                            AVUser owner = AVUser.getCurrentUser();
                            avosServiceUtil.uploadPic(SecondaryPubActivity.this, dialog, secondary, owner, secondary_name, secondary_description, curPrice, costPrice, secondary_catgory, imagePaths);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private boolean isEmpty(String secondary_name, String secondary_description, String secondary_price_now, String secondary_catgory) {
        if (TextUtils.isEmpty(secondary_name)) {
            showShortToast(SecondaryPubActivity.this, "请输入商品名称");
            return false;
        }
        if (TextUtils.isEmpty(secondary_description)) {
            showShortToast(SecondaryPubActivity.this, "请写明商品介绍");
            return false;
        }
        if (TextUtils.isEmpty(secondary_price_now)) {
            showShortToast(SecondaryPubActivity.this, "请给商品定个价吧");
            return false;
        }
        if (secondary_catgory.equals("请选择分类")) {
            showShortToast(SecondaryPubActivity.this, "请选择商品分类");
            return false;
        }
        return true;
    }


    @Override
    protected void findViews() {
        gridView = (GridView) findViewById(R.id.gv_publish_secondary);
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 4 ? 4 : cols;
        gridView.setNumColumns(cols);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == pickAdapter.getMaxPosition() - 1) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(SecondaryPubActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(SecondaryPubActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        pickAdapter = new PhotoPickAdapter(imagePaths, this);
        gridView.setAdapter(pickAdapter);
        et_secondary_name = (EditText) findViewById(R.id.et_secondary_name);
        et_secondary_description = (EditText) findViewById(R.id.et_secondary_description);
        et_secondary_price_now = (EditText) findViewById(R.id.et_secondary_price_now);
        et_secondary_price = (EditText) findViewById(R.id.et_secondary_price);
        spinner_secondary_catgory = (Spinner) findViewById(R.id.spinner_secondary_catgory);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    // 选择照片
                    case REQUEST_CAMERA_CODE:
                        loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                        break;
                    // 预览
                    case REQUEST_PREVIEW_CODE:
                        loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                        break;
                }
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);
        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        pickAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pub_secondary, menu);
        return true;
    }

    private void initSpinner() {
        spinner_secondary_catgory = (Spinner) findViewById(R.id.spinner_secondary_catgory);
        final List<SecondaryCatgory> catgories = new ArrayList<>();
        SecondaryCatgory catgory1 = new SecondaryCatgory();
        catgory1.setSecondaryCatgory("请选择分类");
        SecondaryCatgory catgory2 = new SecondaryCatgory();
        catgory2.setSecondaryCatgory("手机 电脑 耳机 电子产品");
        SecondaryCatgory catgory3 = new SecondaryCatgory();
        catgory3.setSecondaryCatgory("服饰 鞋包");
        SecondaryCatgory catgory4 = new SecondaryCatgory();
        catgory4.setSecondaryCatgory("生活用品 玩具");
        SecondaryCatgory catgory5 = new SecondaryCatgory();
        catgory5.setSecondaryCatgory("图书 影音");
        SecondaryCatgory catgory6 = new SecondaryCatgory();
        catgory6.setSecondaryCatgory("单车 户外 休闲");
        SecondaryCatgory catgory7 = new SecondaryCatgory();
        catgory7.setSecondaryCatgory("化妆 礼品 礼券");
        SecondaryCatgory catgory8 = new SecondaryCatgory();
        catgory8.setSecondaryCatgory("其他");

        catgories.add(catgory1);
        catgories.add(catgory2);
        catgories.add(catgory3);
        catgories.add(catgory4);
        catgories.add(catgory5);
        catgories.add(catgory6);
        catgories.add(catgory7);
        catgories.add(catgory8);

        final SecondaryCatgoryAdapter adapter = new SecondaryCatgoryAdapter(catgories, this);

        spinner_secondary_catgory.setAdapter(adapter);
        spinner_secondary_catgory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secondary_catgory = catgories.get(position).getSecondaryCatgory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                secondary_catgory = null;
            }
        });
    }

}
