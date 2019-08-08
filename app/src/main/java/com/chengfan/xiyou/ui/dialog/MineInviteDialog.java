package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.zero.ci.tool.ToastUtil;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-08/16:11
 * @Description: 我的邀请
 */
public class MineInviteDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;
    EditText mEditText;

    InviteListener mInviteListener;

    public void setInviteListener(InviteListener inviteListener) {
        mInviteListener = inviteListener;
    }

    public MineInviteDialog(@NonNull Context context) {
        super(context);
    }

    public MineInviteDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public MineInviteDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mine_invite);
        mCancelTv = findViewById(R.id.invite_cancel_tv);
        mSubmitTv = findViewById(R.id.invite_submit_tv);
        mEditText = findViewById(R.id.invite_d_num_et);

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et = mEditText.getText().toString().trim();
                if (et.equals("")) {
                    ToastUtil.show(R.string.invite_hint_des_txt);
                } else {
                    mInviteListener.onInviteListener(et);
                    dismiss();
                }
            }
        });
    }


    public interface InviteListener {
        void onInviteListener(String numEt);
    }
}
