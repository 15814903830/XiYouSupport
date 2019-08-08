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
 */
public class CheckVIPDialog extends Dialog {

    TextView mCancelTv, mSubmitTv;

    CheckVIPListener mCheckVIPListener;

    public void setCheckVIPListener(CheckVIPListener checkVIPListener) {
        mCheckVIPListener = checkVIPListener;
    }

    public CheckVIPDialog(Context context) {
        super(context);
    }

    public CheckVIPDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CheckVIPDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_letter);
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
                mCheckVIPListener.onCheckVIPListener();
                dismiss();
            }
        });
    }


    public interface CheckVIPListener {
        void onCheckVIPListener();
    }
}
