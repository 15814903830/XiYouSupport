package com.chengfan.xiyou.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class CharNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
//        if (pushType.getName().equals("MI")){
//            UIApplication.context.startActivity(new Intent( UIApplication.context,MainActivity.class));
//            return true;
//        }
        return false;
    }

}
