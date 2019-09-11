package com.chengfan.xiyou.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.wxapi.Payutils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zero.ci.tool.ForwardUtil;

public class WeiXingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIdlogin;
    private Button btnWeixinglogin;
    private TextView tvExist;
    // IWXAPI 是第三方app和微信通信的openApi接口
    public IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xing);
        initView();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Payutils.APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(Payutils.APP_ID);
    }

    private void initView() {
        btnIdlogin = (Button) findViewById(R.id.btn_idlogin);
        btnWeixinglogin = (Button) findViewById(R.id.btn_weixinglogin);
        tvExist = (TextView) findViewById(R.id.tv_exist);
        btnIdlogin.setOnClickListener(this);
        btnWeixinglogin.setOnClickListener(this);
        tvExist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_idlogin:
                ForwardUtil.getInstance(this).forward(RegisterActivity.class);
                break;
            case R.id.btn_weixinglogin:
                turnToWxLogin();
               // ForwardUtil.getInstance(this).forward(IsBoundActivity.class);
                break;
            case R.id.tv_exist:
                ForwardUtil.getInstance(this).forward(LoginActivity.class);
                break;
        }
    }

    /**
     * 前往微信登录
     */
    private void turnToWxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login";
        req.transaction = "";
        api.sendReq(req);
    }
}
