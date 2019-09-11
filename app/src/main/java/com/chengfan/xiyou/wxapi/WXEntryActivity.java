package com.chengfan.xiyou.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.LoginEntity;
import com.chengfan.xiyou.domain.model.response.LoginResponse;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.ui.accompany.DetailBase;
import com.chengfan.xiyou.ui.complete.CompleteSexActivity;
import com.chengfan.xiyou.ui.login.ForgetActivity;
import com.chengfan.xiyou.ui.login.IsBoundActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.UserStorage;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zero.ci.tool.ForwardUtil;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler, HttpCallBack {
    private HttpCallBack mHttpCallBack;
    public   static  String openId;
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
    // IWXAPI 是第三方app和微信通信的openApi接口
    public IWXAPI api;
    private boolean aBoolean = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHttpCallBack = this;
        api = WXAPIFactory.createWXAPI(this, Payutils.APP_ID);
        api.handleIntent(getIntent(), this);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }


    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("baseRespbaseResp", "" + baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (aBoolean) {
                    if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                        SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
                        //memberId = authResp.transaction;
                        String code = authResp.code;
                        getAccessToken(code);
                    } else {
                        finish();
                    }
                    aBoolean = false;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_UNSUPPORT:
            default:
                finish();
                break;
        }
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
        Log.e("openIdopenIdrequestId", response);
        switch (requestId) {
            case 0:
                try {
                    JSONObject object = new JSONObject(response);
                    openId = object.getString("openid");
                    Log.e("openidopenid", openId);
                    weixinglogin();
                    // bindWX(openId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                Log.e("JSONObject", response);
                try {
                    JSONObject object = new JSONObject(response);
                  String  suc = object.getString("suc");
                    String  msg = object.getString("msg");
                  if (suc.equals("true")){
                      finish();
                      Log.e("JSONObject",msg);
                      LoginResponse loginResponse= JSON.parseObject(response,LoginResponse.class);
                      APPContents.ID = "" + loginResponse.getData().getId();
                      UserStorage.getInstance().saveLoginInfo(loginResponse.getData());
                      //设置当前用户信息
                      RongIM.getInstance().setCurrentUserInfo(new UserInfo(String.valueOf(loginResponse.getData().getId()),
                              loginResponse.getData().getNickname(),
                              Uri.parse(APIContents.HOST + "/" + loginResponse.getData().getAvatarUrl())));

                      AppData.putString(AppData.Keys.AD_USER_ID, loginResponse.getData().getId() + "");
                      if (loginResponse.getData().getAge() > 0) {
                          ForwardUtil.getInstance(this).forward(MainActivity.class);
                      } else {
                          ForwardUtil.getInstance(this).forward(CompleteSexActivity.class);
                      }
                  }else {
                      finish();
                      Log.e("JSONObject",msg);
                      ForwardUtil.getInstance(this).forward(IsBoundActivity.class);
                  }
                    // bindWX(openId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void weixinglogin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("weChatUId", openId);//登录
                    OkHttpUtils.doPostJson(APIContents.HOST+"/api/Account/Login", jsonObject.toString(), mHttpCallBack, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 获取accessToken
     * fc4ac451240d8459577c5fbf93ab31f2
     *
     * @param code
     */
    private void getAccessToken(String code) {
        final String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Payutils.APP_ID +
                "&secret=" + "fc4ac451240d8459577c5fbf93ab31f2" + "&code=" + code + "&grant_type=authorization_code";
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpUtils.doGet(url, mHttpCallBack, 0);
            }
        }.start();
    }


}
