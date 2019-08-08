package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.zero.ci.tool.ToastUtil;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-12/00:38
 * @Description: 群主
 */
public class ChatGroupMemberDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;
    GroupMemberListener mGroupMemberListener;

    public void setGroupMemberListener(GroupMemberListener groupMemberListener) {
        mGroupMemberListener = groupMemberListener;
    }

    public ChatGroupMemberDialog(Context context) {
        super(context);
    }

    public ChatGroupMemberDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ChatGroupMemberDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chat_group_member);
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
                mGroupMemberListener.onGroupMemberListener();
                dismiss();
            }
        });

    }


    public interface GroupMemberListener {
        void onGroupMemberListener();
    }
}
