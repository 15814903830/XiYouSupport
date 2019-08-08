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
public class CheckLetterDialog extends Dialog {

    TextView mCancelTv, mSubmitTv, mTitle;

    CheckListener mCheckListener;

    public void setCheckListener(CheckListener checkListener) {
        mCheckListener = checkListener;
    }

    public CheckLetterDialog(Context context) {
        super(context);
    }

    public CheckLetterDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CheckLetterDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_letter);
        mCancelTv = findViewById(R.id.chat_friend_cancel_tv);
        mSubmitTv = findViewById(R.id.chat_friend_submit_tv);
        mTitle = findViewById(R.id.check_title_tv);

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckListener.onCheckListener();
                dismiss();
            }
        });
    }


    public interface CheckListener {
        void onCheckListener();
    }
}
