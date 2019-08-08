package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.view.time.NumericWheelAdapter;
import com.chengfan.xiyou.view.time.WheelView;
import com.zero.ci.tool.ToastUtil;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/19:21
 * @Description:
 */
public class MineTimeDialog extends Dialog {
    Context mContext;
    private WheelView hourTwo;
    private WheelView minsTwo;
    private WheelView hour;
    private WheelView mins;


    MineTimeListener mMineTimeListener;

    public void setMineTimeListener(MineTimeListener mineTimeListener) {
        mMineTimeListener = mineTimeListener;
    }

    public MineTimeDialog(Context context) {
        super(context);
        mContext = context;
    }

    public MineTimeDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public MineTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View view = LayoutInflater.from(mContext).inflate(R.layout.time_picker_layout, null);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AnimBottom);
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏

        Calendar c = Calendar.getInstance();
        int curHour = c.get(Calendar.HOUR_OF_DAY);
        int curMin = c.get(Calendar.MINUTE);


        hour = (WheelView) view.findViewById(R.id.hour);
        initHour();
        mins = (WheelView) view.findViewById(R.id.mins);
        initMins();

        hourTwo = view.findViewById(R.id.hour_two);
        initHourTwo();
        minsTwo = view.findViewById(R.id.mins_two);
        initMinsTwo();

        // 设置当前时间
        hour.setCurrentItem(curHour);
        mins.setCurrentItem(curMin);
        hourTwo.setCurrentItem(curHour);
        minsTwo.setCurrentItem(curMin);


        hour.setVisibleItems(7);
        mins.setVisibleItems(7);

        hourTwo.setVisibleItems(7);
        minsTwo.setVisibleItems(7);
        // 设置监听
        Button cancel = (Button) view.findViewById(R.id.time_cancel_btn);
        Button submit = (Button) view.findViewById(R.id.time_submit_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Auto-generated method stub
                String str = String.format(Locale.CHINA, "%2d:%2d", hour.getCurrentItem(), mins.getCurrentItem());
                String twoStr = String.format(Locale.CHINA, "%2d:%2d", hourTwo.getCurrentItem(), minsTwo.getCurrentItem());
                mMineTimeListener.onMineTimeListener(str, twoStr);
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


    /**
     * 初始化时
     */
    private void initHour() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(mContext, 0, 23, "%02d");
        numericWheelAdapter.setLabel("");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        hour.setViewAdapter(numericWheelAdapter);
        hour.setCyclic(true);
    }

    /**
     * 初始化分
     */
    private void initMins() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(mContext, 0, 59, "%02d");
        numericWheelAdapter.setLabel("");
//		numericWheelAdapter.setTextSize(15);  设置字体大小
        mins.setViewAdapter(numericWheelAdapter);
        mins.setCyclic(true);
    }

    /**
     * 初始化时
     */
    private void initHourTwo() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(mContext, 0, 23, "%02d");
        numericWheelAdapter.setLabel("");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        hourTwo.setViewAdapter(numericWheelAdapter);
        hourTwo.setCyclic(true);
    }

    /**
     * 初始化分
     */
    private void initMinsTwo() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(mContext, 0, 59, "%02d");
        numericWheelAdapter.setLabel("");
//		numericWheelAdapter.setTextSize(15);  设置字体大小
        minsTwo.setViewAdapter(numericWheelAdapter);
        minsTwo.setCyclic(true);
    }


    public interface MineTimeListener {
        void onMineTimeListener(String startTime, String endTime);
    }
}
