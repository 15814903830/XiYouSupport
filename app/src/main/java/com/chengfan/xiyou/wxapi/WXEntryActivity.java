package com.chengfan.xiyou.wxapi;

import android.app.Activity;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    public void onReq(BaseReq baseReq) {
        //微信发送请求到你的应用
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //应用请求微信的响应结果
        Log.e("code",""+baseResp.errCode);
    }
}
