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
 * @DATE : 2019-07-12/00:38
 * @Description: 非群主
 */
public class ChatGroupNoMemberDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;
    GroupNoMemberListener mGroupNoMemberListener;

    public void setGroupNoMemberListener(GroupNoMemberListener groupNoMemberListener) {
        mGroupNoMemberListener = groupNoMemberListener;
    }

    public ChatGroupNoMemberDialog(Context context) {
        super(context);
    }

    public ChatGroupNoMemberDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ChatGroupNoMemberDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chat_group_no_member);
        mCancelTv = findViewById(R.id.chat_group_cancel_tv);
        mSubmitTv = findViewById(R.id.chat_group_submit_tv);

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupNoMemberListener.onGroupNoMemberListener();
                dismiss();
            }
        });

    }


    public interface GroupNoMemberListener {
        void onGroupNoMemberListener();
    }
}
