package com.chengfan.xiyou.wxpay;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Payutils {
    public static final String APP_ID = "wx9d039eb839d60758";
    private IWXAPI msgApi;
    public static void pot(){

    }

    private void regToWx(Context context,String appId,String partnerId,String prepayId,String packageValue,String nonceStr,String timeStamp,String sign) {
        msgApi =  WXAPIFactory.createWXAPI(context, Payutils.APP_ID,false);
        // 将该app注册到微信
        msgApi.registerApp(Payutils.APP_ID);
        wxpay(appId,partnerId,prepayId,packageValue,nonceStr,timeStamp,sign);
    }

    private void wxpay(String appId,String partnerId,String prepayId,String packageValue,String nonceStr,String timeStamp,String sign) {
        Log.e("wxpay", "-------wxpay");
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = packageValue;
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        msgApi.sendReq(request);
    }
}
