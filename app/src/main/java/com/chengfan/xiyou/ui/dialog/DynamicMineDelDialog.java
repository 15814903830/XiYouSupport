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
 * @DATE : 2019-07-09/12:53
 * @Description: 我的动态 ---》  删除  {@link com.chengfan.xiyou.ui.dynamic.DynamicMineFragment}
 */
public class DynamicMineDelDialog extends Dialog {


    TextView mCancelTv, mSubmitTv;
    DynamicMineDelListener mDynamicMineDelListener;

    public void setDynamicMineDelListener(DynamicMineDelListener dynamicMineDelListener) {
        mDynamicMineDelListener = dynamicMineDelListener;
    }

    public DynamicMineDelDialog(@NonNull Context context) {
        super(context);
    }

    public DynamicMineDelDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public DynamicMineDelDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dynamic_mine);
        mCancelTv = findViewById(R.id.dynamic_cancel_tv);
        mSubmitTv = findViewById(R.id.dynamic_submit_tv);

        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDynamicMineDelListener.onDynamicMineDelListener();
                dismiss();
            }
        });
    }


    public interface DynamicMineDelListener {
        void onDynamicMineDelListener();
    }
}
