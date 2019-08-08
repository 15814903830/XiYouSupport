package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chengfan.xiyou.R;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-11/12:37
 * @Description: 好友 「{@link com.chengfan.xiyou.ui.chat.ChatFriendFragment}」
 */
public class ChatFriendDialog extends Dialog {

    TextView mCancelTv, mSubmitTv;

    ChatFriendListener mChatFriendListener;

    public void setChatFriendListener(ChatFriendListener chatFriendListener) {
        mChatFriendListener = chatFriendListener;
    }

    public ChatFriendDialog(Context context) {
        super(context);
    }

    public ChatFriendDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ChatFriendDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chat_friend);
        mCancelTv = findViewById(R.id.chat_friend_cancel_tv);
        mSubmitTv = findViewById(R.id.chat_friend_submit_tv);

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChatFriendListener.onChatFriendListener();
                dismiss();
            }
        });
    }


    public interface ChatFriendListener {
        void onChatFriendListener();
    }
}
