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
 * @DATE : 2019-07-13/18:58
 * @Description: 修改群名称
 */
public class ChatChangeNameDialog extends Dialog {
    public ChatChangeNameDialog(Context context) {
        super(context);
    }

    public ChatChangeNameDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ChatChangeNameDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    TextView mCancelTv, mSubmitTv;
    EditText mEditText;

    ChangeListener mChangeListener;

    public void setChangeListener(ChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_name);
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
                    ToastUtil.show("请输入您的群名称");
                } else {
                    mChangeListener.onChangeListener(et);
                    dismiss();
                }

            }
        });
    }


    public interface ChangeListener {
        void onChangeListener(String numEt);
    }
}
