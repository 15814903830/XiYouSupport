package com.chengfan.xiyou.ui.mine.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AutonymtrueforflaseActivity extends AppCompatActivity implements View.OnClickListener, HttpCallBack {

    private HttpCallBack mHttpCallBack;
    private LinearLayout llReturnAutonym;
    private ImageView ivIdcard1Auttonym;
    private ImageView ivIdcard2Auttonym;
    private Button mButton;
    private ArrayList<AlbumFile> mAlbumFiles;
    private ArrayList<AlbumFile> mAlbumFiles2;
    private TextView etIdcar;
    private EditText etName;
    private EditText etIdnum;
    private FileBase fileBase1;//上传正面
    private FileBase fileBase2;//上传反面
    private String img = "";
    private BaseNiceDialog mBaseNiceDialog;
    private IdCard mIdCard;
    @SuppressLint("HandlerLeak")
    private Handler mHandlere = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };
    private TextView tvRz;
    private ImageView tvImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonymtrueforflase);
        initView();
    }

    private void initView() {
        llReturnAutonym = findViewById(R.id.ll_return_autonym);
        ivIdcard1Auttonym = findViewById(R.id.iv_idcard1_auttonym);
        ivIdcard2Auttonym = findViewById(R.id.iv_idcard2_auttonym);
        mButton = findViewById(R.id.btn_promptly_autonym);
        tvRz = findViewById(R.id.tv_rz);
        tvImg = findViewById(R.id.tv_img);
        etIdcar = findViewById(R.id.et_idcar);
        etName = findViewById(R.id.et_name);
        etIdnum = findViewById(R.id.et_idnum);
        llReturnAutonym.setOnClickListener(this);
        ivIdcard1Auttonym.setOnClickListener(this);
        ivIdcard2Auttonym.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mHttpCallBack = this;
        etName.setText(getIntent().getStringExtra("name"));
        etIdnum.setText(getIntent().getStringExtra("carsum"));
        Glide.with(this).load(APIContents.HOST + "/" + getIntent().getStringExtra("img").split("\\|")[0]).into(ivIdcard1Auttonym);
        Glide.with(this).load(APIContents.HOST + "/" + getIntent().getStringExtra("img").split("\\|")[1]).into(ivIdcard2Auttonym);
        if (getIntent().getStringExtra("tv").equals("认证成功")) {
            tvImg.setSelected(true);
            mButton.setVisibility(View.GONE);
        }
        tvRz.setText(getIntent().getStringExtra("tv"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return_autonym:
                finish();
                break;
            case R.id.iv_idcard1_auttonym://上传身份证正面
                showLoading();
                selectImage();
                break;
            case R.id.iv_idcard2_auttonym://上传身份证反面
                selectImage2();
                showLoading();
                break;
            case R.id.btn_promptly_autonym://确认上传
                postidcar();
                showLoading();
                break;
        }
    }

    private void postimg(final String data, final int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("source", "AccompanyPlayNews");
                    jsonObject.put("fileName", "png");
                    jsonObject.put("fileData", data);
                    OkHttpUtils.doPostJson(APIContents.UPLOAD_FILE, jsonObject.toString(), mHttpCallBack, i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postidcar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", AppData.getString(AppData.Keys.AD_USER_ID));
                    jsonObject.put("IdImages", img);
                    jsonObject.put("fullName", etName.getText().toString().trim());
                    jsonObject.put("idNo", etIdcar.getText().toString().trim());
                    Log.e("IdImages", img);
                    OkHttpUtils.doPostJson(APIContents.HOST + "/api/Account/RealName", jsonObject.toString(), mHttpCallBack, 3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(1)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                //.title(mToolbar.getTitle().toString())
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        notifyData(mAlbumFiles);

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        ToastUtil.show("取消");
                    }
                })
                .start();
    }

    private void selectImage2() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(1)
                .checkedList(mAlbumFiles2)
                .widget(
                        Widget.newDarkBuilder(this)
                                //.title(mToolbar.getTitle().toString())
                                .build()
                )
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles2 = result;
                        notifyData2(mAlbumFiles2);

                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        ToastUtil.show("取消");
                    }
                })
                .start();
    }

    private void notifyData(ArrayList<AlbumFile> result) {
        Toast.makeText(this, "上传图片中", Toast.LENGTH_SHORT).show();
        Log.e("path", result.get(0).getPath());
        ImageLoaderManager.getInstance().showImage(ivIdcard1Auttonym, result.get(0).getPath());
        postimg(best64(result.get(0).getPath()), 0);
    }

    private void notifyData2(ArrayList<AlbumFile> result) {
        Toast.makeText(this, "上传图片中", Toast.LENGTH_SHORT).show();
        Log.e("path", result.get(0).getPath());
        ImageLoaderManager.getInstance().showImage(ivIdcard2Auttonym, result.get(0).getPath());
        postimg(best64(result.get(0).getPath()), 1);
    }

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandlere.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandlere.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        switch (requestId) {
            case 0:
                Log.e("response0:", response);
                try {
                    fileBase1 = JSON.parseObject(response, FileBase.class);
                    img = fileBase1.getFilePath();
                    mBaseNiceDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                Log.e("response1:", response);
                try {
                    fileBase2 = JSON.parseObject(response, FileBase.class);
                    img = img + "|" + fileBase2.getFilePath();
                    mBaseNiceDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    mIdCard = JSON.parseObject(response, IdCard.class);
                    if (mIdCard.isSuc()) {
                        //实名认证成功
                    } else {
                        //实名认证失败
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mBaseNiceDialog.dismiss();
                break;
        }
    }

    public static String best64(String file) {
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream("" + file);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 显示loading
     */
    public void showLoading() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        mBaseNiceDialog = dialog;
                    }
                })
                .setOutCancel(false)
                .setWidth(48)
                .setHeight(48)
                .setShowBottom(false)
                .show(getSupportFragmentManager());
    }
}
