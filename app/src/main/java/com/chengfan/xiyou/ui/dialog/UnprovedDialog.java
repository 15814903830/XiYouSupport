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
 * @DATE : 2019-07-24/01:23
 * @Description:
 */
public class UnprovedDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;

    UnprovedListener mUnprovedListener;

    public void setUnprovedListener(UnprovedListener unprovedListener) {
        mUnprovedListener = unprovedListener;
    }

    public UnprovedDialog(Context context) {
        super(context);
    }

    public UnprovedDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public UnprovedDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_unproved);
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
                mUnprovedListener.onUnprovedListener();
                dismiss();
            }
        });
    }


    public interface UnprovedListener {
        void onUnprovedListener();
    }
}
