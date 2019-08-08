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
 * @DATE : 2019-07-24/01:41
 * @Description:
 */
public class TakingTakersDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;

    TakingTakersListener mTakingTakersListener;

    public void setTakingTakersListener(TakingTakersListener takingTakersListener) {
        mTakingTakersListener = takingTakersListener;
    }

    public TakingTakersDialog(Context context) {
        super(context);
    }

    public TakingTakersDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public TakingTakersDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_taking_takers);
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
                mTakingTakersListener.onTakingTakersListener();
                dismiss();
            }
        });
    }


    public interface TakingTakersListener {
        void onTakingTakersListener();
    }
}
