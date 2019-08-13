package com.chengfan.xiyou.ui.accompany;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyConfirmOrderContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyConfirmOrderBean;
import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyConfirmOrderPresenterImpl;
import com.chengfan.xiyou.domain.presenter.AccompanyPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.mine.MineMemberActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.chengfan.xiyou.wxapi.Payutils;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/18:56
 * @Description: 确认订单
 */
public class AccompanyConfirmOrderActivity extends BaseActivity<AccompanyConfirmOrderContract.View, AccompanyConfirmOrderPresenterImpl> implements AccompanyConfirmOrderContract.View, HttpCallBack {

    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.confirm_game_pic_iv)
    ImageView mConfirmGamePicIv;
    @BindView(R.id.confirm_game_name_tv)
    MediumTextView mConfirmGameNameTv;
    @BindView(R.id.confirm_game_money_tv)
    MediumTextView mConfirmGameMoneyTv;
    @BindView(R.id.confirm_game_time_tv)
    RegularTextView mConfirmGameTimeTv;
    @BindView(R.id.confirm_type_name_tv)
    RegularTextView mConfirmTypeNameTv;
    @BindView(R.id.confirm_tim_tv)
    RegularTextView mConfirmTimTv;
    @BindView(R.id.confirm_heji_time_tv)
    RegularTextView mConfirmHejiTimeTv;
    @BindView(R.id.confirm_heji_money_tv)
    RegularTextView mConfirmHejiMoneyTv;
    @BindView(R.id.confirm_beizhu_et)
    EditText mConfirmBeizhuEt;
    @BindView(R.id.confirm_zfb_select_iv)
    ImageView mConfirmZfbSelectIv;
    @BindView(R.id.confirm_wx_select_iv)
    ImageView mConfirmWxSelectIv;
    @BindView(R.id.confirm_he_tv)
    MediumTextView mConfirmHeTv;

    @BindView(R.id.confirm_zfb_select_rl)
    RelativeLayout mConfirmZfbSelectRl;
    @BindView(R.id.confirm_wx_select_rl)
    RelativeLayout mConfirmWxSelectRl;

    Bundle revBundle;
    private HttpCallBack mHttpCallBack;
    AccompanyDetailEntity mAccompanyDetailEntity;

    int time = 1;
    String remarkStr, selectStr, allStr;
    AccompanyConfirmOrderBean confirmOrderBean;
    private IWXAPI api;
    private Handler mpayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.toString().length()>200){
                Intent intent=new Intent(AccompanyConfirmOrderActivity.this, AccompanyOrderSuccessActivity.class);
                intent.putExtra("key","支付成功");
                startActivity(intent);
                //成功
            }else {
//                Intent intent=new Intent(AccompanyConfirmOrderActivity.this, AccompanyOrderSuccessActivity.class);
//                intent.putExtra("key","支付失败");
//                startActivity(intent);

                Toast.makeText(AccompanyConfirmOrderActivity.this, "支付已取消", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_confirm_order);
        ButterKnife.bind(this);
        initwxsdk();
        mHttpCallBack = this;
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mXyMiddleTv.setText(getResources().getText(R.string.confirm_title_txt));
        mAccompanyDetailEntity = new AccompanyDetailEntity();
        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            mAccompanyDetailEntity = (AccompanyDetailEntity) revBundle.getSerializable(APPContents.BUNDLE_FRAGMENT);

            ImageLoaderManager.getInstance().showImage(mConfirmGamePicIv, APIContents.HOST + "/" + mAccompanyDetailEntity.getImages());
            mConfirmGameNameTv.setText(mAccompanyDetailEntity.getNickname());
            mConfirmGameTimeTv.setText(mAccompanyDetailEntity.getWeekDay() + "|" + mAccompanyDetailEntity.getServiceStartTime() + " " + mAccompanyDetailEntity.getServiceEndTime());
            mConfirmTypeNameTv.setText(mAccompanyDetailEntity.getSubjectTitle());
            mConfirmGameMoneyTv.setText("￥" + mAccompanyDetailEntity.getPrice() + "小时");
            mConfirmTimTv.setText(time + "");
            mConfirmHejiTimeTv.setText(time + "");
            mConfirmHejiMoneyTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
            mConfirmHeTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
            APPContents.TO_ALLSTR=time * mAccompanyDetailEntity.getPrice() + "";

        }

        mConfirmZfbSelectRl.setClickable(true);
        mConfirmZfbSelectIv.setVisibility(View.VISIBLE);
        mConfirmWxSelectIv.setVisibility(View.GONE);
        selectStr = "3";

    }

    @Override
    protected void onStart() {
        super.onStart();
        initwxsdk();
    }

    private void initwxsdk() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Payutils.APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(Payutils.APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(Payutils.APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }
    @Override
    public void AccompanyConfirmOrderLoad(BaseApiResponse baseApiResponse) {
        paystart("" + baseApiResponse.getData());
        //        if (baseApiResponse.isSuc()) {
        //            ForwardUtil.getInstance(this).forward(AccompanyOrderSuccessActivity.class);
        //        }
        //        ToastUtil.show(baseApiResponse.getMsg());
    }

    @OnClick({R.id.xy_back_btn, R.id.confirm_time_jian_iv, R.id.confirm_time_jia_iv, R.id.confirm_zfb_select_rl, R.id.confirm_wx_select_rl, R.id.confirm_submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.confirm_time_jian_iv:

                time--;
                if (time <= 0)
                    time = 1;
                mConfirmTimTv.setText(time + "");
                mConfirmHejiTimeTv.setText(time + "");
                mConfirmHejiMoneyTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
                mConfirmHeTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
                break;
            case R.id.confirm_time_jia_iv:


                time++;
                mConfirmTimTv.setText(time + "");
                mConfirmHejiTimeTv.setText(time + "");
                mConfirmHejiMoneyTv.setText(time * mAccompanyDetailEntity.getPrice() + "");
                mConfirmHeTv.setText(time * mAccompanyDetailEntity.getPrice() + "");

                break;
            case R.id.confirm_zfb_select_rl:
                selectStr = "3";
                mConfirmWxSelectIv.setVisibility(View.GONE);
                mConfirmZfbSelectIv.setVisibility(View.VISIBLE);
                break;
            case R.id.confirm_wx_select_rl:
                mConfirmZfbSelectIv.setVisibility(View.GONE);
                mConfirmWxSelectIv.setVisibility(View.VISIBLE);
                selectStr = "2";
                break;
            case R.id.confirm_submit_btn:
                Toast.makeText(this, "创建订单中", Toast.LENGTH_SHORT).show();
                allStr = mConfirmHeTv.getText().toString();
                remarkStr = mConfirmBeizhuEt.getText().toString().trim();
                confirmOrderBean = new AccompanyConfirmOrderBean();
                confirmOrderBean.setAccompanyPlayId("" + mAccompanyDetailEntity.getId());
                confirmOrderBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                confirmOrderBean.setHour(time + "");
                confirmOrderBean.setRemark(remarkStr);
                if (selectStr.equals("3")){//支付宝
                    payzfb("" + mAccompanyDetailEntity.getId(), AppData.getString(AppData.Keys.AD_USER_ID), "" + time,  3, remarkStr);
                }else if (selectStr.equals("2")){//微信
                    payzfb("" + mAccompanyDetailEntity.getId(), AppData.getString(AppData.Keys.AD_USER_ID), "" + time, 2, remarkStr);
                }

                break;
        }
    }
    private void wxpay(String appId, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String sign) {
        PayReq request = new PayReq();
        request.appId = ""+appId;//wx9d039eb839d60758
        request.partnerId = ""+partnerId;//1543012411
        request.prepayId = ""+prepayId;//wx13132230738957d2239775801447575300
        request.packageValue = "Sign=WXPay";
        request.nonceStr = ""+nonceStr;//4146574498
        request.timeStamp = ""+timeStamp;//1565673751
        request.sign = ""+sign;//B9F1C20CE351D204D102B7722084AF85
        boolean result = api.sendReq(request);
        if (result) {
            Log.e("跳转支付","--------------");
        } else {
            Log.e("系统异常","--------------");
            Toast.makeText(this, "系统异常", Toast.LENGTH_SHORT).show();
        }
    }
    private void paystart(final String info) {
        Log.e("info", info);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e("info", info);
                PayTask alipay = new PayTask(AccompanyConfirmOrderActivity.this);
                Map<String, String> result = alipay.payV2(info, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mpayHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
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

        switch (requestId) {
            case 3://支付宝
                PayzfbBase base = JSON.parseObject(response, PayzfbBase.class);
                paystart(base.getData());
                break;
            case 2://微信
                Log.e("response",response);
                WxPayBase wxPayBase = JSON.parseObject(response, WxPayBase.class);
                wxpay(wxPayBase.getData().getAppid(),
                        wxPayBase.getData().getPartnerid(),
                        wxPayBase.getData().getPrepayid(),
                        wxPayBase.getData().getPackageX(),
                        wxPayBase.getData().getNoncestr(),
                        wxPayBase.getData().getTimestamp(),
                        wxPayBase.getData().getSign());
                break;

        }
    }

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

    private void payzfb(final String accompanyPlayId, final String memberId, final String hour, final int paymentChannel, final String remark) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("accompanyPlayId", accompanyPlayId);
                    jsonObject.put("memberId", memberId);
                    jsonObject.put("hour", hour);//时长
                    jsonObject.put("paymentChannel", ""+paymentChannel);
                    jsonObject.put("remark", remark);//备注
                    OkHttpUtils.doPostJson(APIContents.CreateAccompanyPlayOrder, jsonObject.toString(), mHttpCallBack, paymentChannel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
