package com.chengfan.xiyou.ui.mine;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.contract.MineMemberContract;
import com.chengfan.xiyou.domain.model.entity.GetAccountEntity;
import com.chengfan.xiyou.domain.model.entity.MemberSelectBean;
import com.chengfan.xiyou.domain.model.entity.VIPOrderBean;
import com.chengfan.xiyou.domain.presenter.MineMemberPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MineMemberSelectAdapter;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.wxapi.Payutils;
import com.chengfan.xiyou.wxapi.WxpayBase;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/13:01
 * @Description: 我的会员
 */
public class MineMemberActivity extends BaseActivity<MineMemberContract.View, MineMemberPresenterImpl> implements MineMemberContract.View {

    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.member_title_tv)
    TextView mMemberTitleTv;
    @BindView(R.id.member_des_tv)
    TextView mMemberDesTv;
    @BindView(R.id.member_rv)
    RecyclerView mMemberRv;
    @BindView(R.id.member_take_money_tv)
    TextView mMemberTakeMoneyTv;
    private Map<String, String> parm;
    public static Set<Integer> positionSet = new HashSet<>();
    List<MemberSelectBean> mMemberSelectBeanList;
    MineMemberSelectAdapter mMemberSelectAdapter;
    GetAccountEntity mGetAccountEntity;
    @BindView(R.id.member_pay_tv)
    TextView mMemberPayTv;
    private static MineMemberActivity instance;
    MemberSelectBean mMemberSelectBean;
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI msgApi;
    private int paytape=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_member);
        ButterKnife.bind(this);
        this.instance = this;
        UltimateBar.Companion.with(this)
                .applyNavigation(false)
                .statusDark(false)
                .create()
                .immersionBar();
        regToWx();

        mXyMiddleTv.setText(getResources().getText(R.string.member_top_title_txt));

        mMemberSelectBeanList = new ArrayList<>();
        mGetAccountEntity = new GetAccountEntity();


        mPresenter.accountParameter();
        mPresenter.memberSelectParameter();

        mMemberSelectAdapter = new MineMemberSelectAdapter(mMemberSelectBeanList);
        mMemberRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberRv.setAdapter(mMemberSelectAdapter);
        addOrRemove(0);
        mMemberSelectAdapter.setOnItemClickListener(new MineMemberSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!positionSet.contains(position)) {
                    // 选择不同的单位时取消之前选中的单位
                    positionSet.clear();
                }
                mMemberTakeMoneyTv.setText(mMemberSelectBeanList.get(position).getPrice() + "");
                mMemberSelectBean = mMemberSelectBeanList.get(position);
                addOrRemove(position);
            }


        });


    }

    public static MineMemberActivity getInstance() {
        return instance;
    }
    private void initView() {
        if (mGetAccountEntity != null) {
            if (mGetAccountEntity.isVip()) {
                mMemberTitleTv.setText(getResources().getString(R.string.member_title_txt));
                mMemberDesTv.setText(mGetAccountEntity.getVipEffectiveDate().toString());
                mMemberPayTv.setText("立即续费");
            } else {
                mMemberTitleTv.setText(getResources().getString(R.string.member_no_title_txt));
                mMemberDesTv.setText(getResources().getString(R.string.member_no_des_txt));
                mMemberPayTv.setText("立即开通");
            }
        }
    }


    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            //positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
        }
        if (positionSet.size() == 0) {
            // 如果没有选中任何的item，则退出多选模式
            mMemberSelectAdapter.notifyDataSetChanged();
        } else {
            // 更新列表界面，否则无法显示已选的item
            mMemberSelectAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void accountLoad(GetAccountEntity accountEntity) {
        mGetAccountEntity = accountEntity;
        initView();
    }

    @Override
    public void memberSelectLoad(List<MemberSelectBean> memberSelectBeanList) {

        mMemberSelectBeanList = memberSelectBeanList;
        mMemberSelectAdapter.notify(mMemberSelectBeanList);
        mMemberSelectBean = mMemberSelectBeanList.get(0);
    }

    @Override
    public void vipOrderLoad(String result) {
        Log.e("resylt",result);
        Logger.d("MineMemberActivity  === >>> " + result);

        paystart("");
//        try {
//            WxpayBase wxpayBase = JSON.parseObject(result,WxpayBase.class);
//            wxpay(wxpayBase.getData().getAppid(),
//                    wxpayBase.getData().getPartnerid(),
//                    wxpayBase.getData().getPrepayid(),
//                   "Sign=WXPay",
//                    wxpayBase.getData().getNoncestr(),
//                    wxpayBase.getData().getTimestamp(),
//                    wxpayBase.getData().getSign()
//            );
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(instance, "服务器繁忙", Toast.LENGTH_SHORT).show();
//        }
    }

    @OnClick({R.id.xy_back_btn, R.id.member_pay_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.member_pay_tv:
                VIPOrderBean vipOrderBean = new VIPOrderBean();
                vipOrderBean.setMemberId(mGetAccountEntity.getId());
                vipOrderBean.setVipSetMealId(mMemberSelectBean.getId());
                vipOrderBean.setPaymentChannel(2);
                vipOrderBean.setBody(mMemberSelectBean.getTitle());
                vipOrderBean.setPayPrice(mMemberSelectBean.getPrice());
                vipOrderBean.setVipDay(mMemberSelectBean.getDays());

                mPresenter.vipOrderParameter(vipOrderBean);
                break;
        }
    }

    private void regToWx() {
        msgApi = WXAPIFactory.createWXAPI(this, Payutils.APP_ID, false);
        // 将该app注册到微信
        msgApi.registerApp(Payutils.APP_ID);
    }

    private void wxpay(String appId, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String sign) {
        Log.e("wxpay", "-------wxpay");
        Log.e("appId",""+appId);
        Log.e("partnerId",""+partnerId);
        Log.e("prepayId",""+prepayId);
        Log.e("packageValue",""+packageValue);
        Log.e("nonceStr",""+nonceStr);
        Log.e("timeStamp",""+timeStamp);
        Log.e("sign",""+sign);
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = ""+nonceStr;
        request.timeStamp = ""+timeStamp;
        request.sign = ""+sign;
            boolean result = msgApi.sendReq(request);
        if (result) {
            Toast.makeText(this, "准备跳转支付", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        }
    }


    private void paystart(final String info) {
        Log.e("info", info);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e("info", info);
                PayTask alipay = new PayTask(MineMemberActivity.this);
                Map<String, String> result = alipay.payV2("app_id=2019072966075097&biz_content=%7b%22body%22%3a%22%e5%8c%85%e6%9c%88%e4%bc%9a%e5%91%98%22%2c%22out_trade_no%22%3a%22950573%22%2c%22product_code%22%3a%221%22%2c%22subject%22%3a%22%e5%8c%85%e6%9c%88%e4%bc%9a%e5%91%98%22%2c%22timeout_express%22%3a%2260m%22%2c%22total_amount%22%3a%220.01%22%7d&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fxy.gx11.cn%2fAPI%2f%2fOrder%2fNotifyVIPOrder%2fAliPay&sign_type=RSA2&timestamp=2019-08-09+11%3a51%3a05&version=1.0&sign=MhQdk9xtWb5kQ2ovaXwFoV66EIZbGdT9%2fEHJqeVHLSqEpAyFmuCVJtX4hLPZT3xCgidMKeychZapQvh7oaEFTwfwAanqRSc3PCGyYnRYxt25FZGn2Xg1%2bPRsR5k5%2f0%2ba0dz4s84NK2gkzRIc9gqhwB9w5vez8nKazHW3A0zmMnmMPv818T9A54ai9mi9w8squx%2f8LvxFtIlLeEMCyFWS6g%2blWc8dcjM1fZZONQIntTEAa%2fnXeUUzFwxUkdr230JysygLIRkebNI49nxonI2%2fv6gL8wvgDpbj7uTU3yq34xDwez6LqUtAce0j%2fQJ%2bBeYbjT3c69tBJxn%2fUR%2fVzRADOA%3d%3d", true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



}
