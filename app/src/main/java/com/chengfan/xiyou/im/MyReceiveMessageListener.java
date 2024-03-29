package com.chengfan.xiyou.im;

import android.content.Context;
import android.util.Log;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * 融云消息接收器
 */
public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {



    private String TAG="MyReceiveMessageListener";

    @Override
    public boolean onReceived(Message message, int i) {
        if (message.getConversationType() == Conversation.ConversationType.PRIVATE) {
            UserIMInfo userInfo = new UserIMInfo();
            userInfo.setId(stringToInt(message.getContent().getUserInfo().getUserId()));
            userInfo.setNickname(message.getContent().getUserInfo().getName());
            userInfo.setAvatarUrl(message.getContent().getUserInfo().getPortraitUri().toString());
            userInfo.save();
        }
        return false;
    }

    /**
     * String转int
     * 如果格式错误，返回-1
     *
     * @param value
     * @return
     */
    public static int stringToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
