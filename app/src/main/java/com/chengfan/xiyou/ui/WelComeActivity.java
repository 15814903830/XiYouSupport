package com.chengfan.xiyou.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.login.LoginActivity;
import com.chengfan.xiyou.ui.login.WeiXingActivity;
import com.chengfan.xiyou.ui.main.VersionBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.BaseUtils;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/12:37
 * @Description: 启动页
 */
public class WelComeActivity extends BaseActivity implements HttpCallBack {

    private HttpCallBack mHttpCallBack;
    private Disposable mdDisposable;
    private boolean mBoolean=true;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mHttpCallBack=this;
        getversiondata();
        mdDisposable = Flowable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d((3 - aLong) + "秒后跳转");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        String uid = AppData.getString(AppData.Keys.AD_USER_ID);

                        if (uid.equals("") || uid == null) {
                            ForwardUtil.getInstance(WelComeActivity.this).forward(WeiXingActivity.class);
                            finish();
                        } else {
                            if (mBoolean){
                                ForwardUtil.getInstance(WelComeActivity.this).forward(MainActivity.class);
                                finish();
                            }
                        }
                    }
                })
                .subscribe();


        AppData.putBoolen(AppData.Keys.AD_SHOW_SAY_HI, true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }
    private void getversiondata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list=new ArrayList<>();
                list.add(new RequestParams("id","1"));
                OkHttpUtils.doGet(APIContents.HOST +"/api/Common/AppManage",list,mHttpCallBack,0);
            }
        }).start();
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    /**
     * 检查版本
     *
     * @param version
     * @param downloadUrl
     * @param description
     */
    private void checkVersion(String version, final String downloadUrl, final String description) {
        if (BaseUtils.checkVersion(this,version)) {
            mBoolean=false;
            NiceDialog.init()
                    .setLayoutId(R.layout.dialog_app_version_layout)
                    .setConvertListener(new ViewConvertListener() {
                        @Override
                        protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                            TextView tv_download = holder.getView(R.id.tv_download_app_version_dialog);
                            TextView tv_description = holder.getView(R.id.tv_description);
                            tv_description.setText(description);
                            tv_download.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    turnToDownload(downloadUrl);
                                }
                            });
                        }
                    })
                    .setWidth(286)
                    .setOutCancel(false)
                    .show(getSupportFragmentManager());
        }
    }

    /**
     * 前往下载
     *
     * @param downloadUrl
     */
    private void turnToDownload(String downloadUrl) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(downloadUrl);
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandler.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandler.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        try {
            VersionBase versionBase= JSON.parseObject(response,VersionBase.class);
            versionBase.getAndroidDownloadUrl();
            checkVersion(""+versionBase.getAndroidAppVersion(),  versionBase.getAndroidDownloadUrl(), versionBase.getAndroidDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
