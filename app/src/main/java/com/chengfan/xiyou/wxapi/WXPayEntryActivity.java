package com.chengfan.xiyou.wxapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.ui.accompany.AccompanyOrderDetailActivity;
import com.chengfan.xiyou.ui.mine.MineOrderActivity;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zero.ci.tool.ForwardUtil;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler , View.OnClickListener {

    private IWXAPI msgApi;
    private Toolbar xyTb;
    private MediumTextView xyMiddleTv;
    private ImageView completeOverIv;
    private RegularTextView completeOverDesTv;
    private MediumTextView successMoneyTv;
    private Button successGoMainBtn;
    private Button successGoDetailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_order_success);
        regToWx();
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        msgApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Log.e("支付成功", "------");
                successMoneyTv = findViewById(R.id.success_money_tv);
                successMoneyTv.setText("$"+ APPContents.TO_ALLSTR);
            } else {
                successGoDetailBtn = findViewById(R.id.success_go_detail_btn);
                successGoDetailBtn.setText("返回");
                completeOverIv = findViewById(R.id.complete_over_iv);
                completeOverIv.setImageResource(R.mipmap.cuowu);
                successMoneyTv = findViewById(R.id.success_money_tv);
                completeOverDesTv = findViewById(R.id.complete_over_des_tv);
                completeOverDesTv.setText("抱歉\n支付已取消");
                successMoneyTv.setText("支付失败！");
                Log.e("支付失败", "------" + resp.errCode);
            }
        }
    }

    private void regToWx() {
        msgApi = WXAPIFactory.createWXAPI(this, Payutils.APP_ID);
        msgApi.handleIntent(getIntent(), this);
    }

    private void initView() {
        xyTb = findViewById(R.id.xy_tb);
        xyMiddleTv = findViewById(R.id.xy_middle_tv);
        successGoMainBtn = findViewById(R.id.success_go_main_btn);
        successGoDetailBtn = findViewById(R.id.success_go_detail_btn);
        successGoMainBtn.setOnClickListener(this);
        successGoDetailBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.success_go_main_btn:
                startActivity(new Intent(WXPayEntryActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.success_go_detail_btn:
                if (successGoDetailBtn.getText().equals("返回")){
                    finish();
                }else {
                    /*陪玩订单*/
                    ForwardUtil.getInstance(this).forward(MineOrderActivity.class);
                    finish();
                }
                break;
        }
    }
}
