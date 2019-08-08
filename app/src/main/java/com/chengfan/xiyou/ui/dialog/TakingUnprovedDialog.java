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
 * @DATE : 2019-07-24/01:48
 * @Description:
 */
public class TakingUnprovedDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;

    TakingUnprovedListener mTakingUnprovedListener;

    public void setTakingUnprovedListener(TakingUnprovedListener takingUnprovedListener) {
        mTakingUnprovedListener = takingUnprovedListener;
    }

    public TakingUnprovedDialog(Context context) {
        super(context);
    }

    public TakingUnprovedDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public TakingUnprovedDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
                mTakingUnprovedListener.onTakingUnprovedListener();
                dismiss();
            }
        });
    }


    public interface TakingUnprovedListener {
        void onTakingUnprovedListener();
    }
}
