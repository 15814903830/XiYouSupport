package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.MineDataBean;
import com.chengfan.xiyou.ui.adapter.MineDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/17:14
 * @Description: 选择日期
 */
public class MineDataDialog extends Dialog {
    List<MineDataBean> weakList = new ArrayList<>();
    List<MineDataBean> selectWeakList = new ArrayList<>();
    MineDataAdapter mMineDataAdapter;
    RecyclerView mRecyclerView;
    Context mContext;
    MineDataListener mMineDataListener;

    public void setMineDataListener(MineDataListener mineDataListener) {
        mMineDataListener = mineDataListener;
    }

    public MineDataDialog(Context context) {
        super(context);
        mContext = context;
    }

    public MineDataDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public MineDataDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_mine_data, null);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AnimBottom);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏

        mRecyclerView = view.findViewById(R.id.data_rv);
        weakList.add(new MineDataBean("星期一"));
        weakList.add(new MineDataBean("星期二"));
        weakList.add(new MineDataBean("星期三"));
        weakList.add(new MineDataBean("星期四"));
        weakList.add(new MineDataBean("星期五"));
        weakList.add(new MineDataBean("星期六"));
        weakList.add(new MineDataBean("星期日"));

        mMineDataAdapter = new MineDataAdapter(R.layout.adapter_mine_data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mMineDataAdapter);
        mMineDataAdapter.setSelectedListener(new MineDataAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!mMineDataAdapter.isSelected.get(position)) {
                    mMineDataAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mMineDataAdapter.notifyItemChanged(position);
                    selectWeakList.add(weakList.get(position));
                } else {
                    mMineDataAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    mMineDataAdapter.notifyItemChanged(position);
                    selectWeakList.remove(weakList.get(position));
                    //selectWeakList.add(weakList.get(position));
                }


            }
        });
        mMineDataAdapter.addData(weakList);
        TextView textView = findViewById(R.id.tvTitle);
        textView.setText("接单日期");

        Button cancel = (Button) view.findViewById(R.id.data_cancel_btn);
        Button submit = (Button) view.findViewById(R.id.data_submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMineDataListener.onMineDataListener(listToString(selectWeakList));
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static String listToString(List<MineDataBean> mList) {
        final String SEPARATOR = ",";
        // mList = Arrays.asList("AAA", "BBB", "CCC");
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            for (MineDataBean item : mList) {
                sb.append(item.getName());
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length()
                    - SEPARATOR.length());
            return convertedListStr;
        } else return "List is null!!!";
    }

    public interface MineDataListener {
        void onMineDataListener(String data);
    }
}
