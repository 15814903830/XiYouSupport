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
 * @DATE : 2019-07-15/21:23
 * @Description: 关注/取消关注
 */
public class MemberShipDialog extends Dialog {
    TextView mTitleTv, mCancelTv, mSubmitTv;
    MemberShipListener mMemberShipListener;
    private String title;

    public void setMemberShipListener(MemberShipListener memberShipListener) {
        mMemberShipListener = memberShipListener;
    }

    public MemberShipDialog(Context context) {
        super(context);
    }

    public MemberShipDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public MemberShipDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_member_ship);

        mTitleTv = findViewById(R.id.member_title_tv);
        mCancelTv = findViewById(R.id.cancel_tv);
        mSubmitTv = findViewById(R.id.submit_tv);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMemberShipListener.onMemberShipListener();
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        show();
        this.title = title;
        mTitleTv.setText(title);
    }

    public interface MemberShipListener {
        void onMemberShipListener();
    }
}
