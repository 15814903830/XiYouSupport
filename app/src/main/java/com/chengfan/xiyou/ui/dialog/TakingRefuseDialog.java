package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.zero.ci.tool.ToastUtil;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/01:30
 * @Description:
 */
public class TakingRefuseDialog extends Dialog {
    TextView mCancelTv, mSubmitTv;
    EditText mEditText;


    TakingRefuseListener mTakingRefuseListener;

    public void setTakingRefuseListener(TakingRefuseListener takingRefuseListener) {
        mTakingRefuseListener = takingRefuseListener;
    }

    public TakingRefuseDialog(Context context) {
        super(context);
    }

    public TakingRefuseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public TakingRefuseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_taking_refuse);
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
                    ToastUtil.show("请输入您拒绝接单的理由");
                } else {
                    mTakingRefuseListener.onTakingRefuseListener(et);
                    dismiss();
                }

            }
        });
    }


    public interface TakingRefuseListener {
        void onTakingRefuseListener(String numEt);
    }
}
