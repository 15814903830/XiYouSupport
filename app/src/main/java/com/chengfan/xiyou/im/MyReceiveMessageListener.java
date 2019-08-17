package com.chengfan.xiyou.im;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 融云消息接收器
 */
public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

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
