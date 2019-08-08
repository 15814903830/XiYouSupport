package com.chengfan.xiyou.ui.complete;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.utils.FontHelper;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/23:52
 * @Description: 完善信息完毕
 */
public class CompleteOverActivity extends BaseActivity {
    @BindView(R.id.complete_over_time_tv)
    TextView mCompleteOverTimeTv;
    @BindView(R.id.complete_over_des_tv)
    TextView mCompleteOverDesTv;
    private Disposable mdDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_over);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mdDisposable = Flowable.intervalRange(0, 6, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mCompleteOverTimeTv.setText((5 - aLong) + "秒后进入主页");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        ForwardUtil.getInstance(CompleteOverActivity.this).forward(MainActivity.class);
                    }
                })
                .subscribe();

        FontHelper.applyFont(this, mCompleteOverTimeTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteOverDesTv, APPContents.FONTS_REGULAR);
    }

    @OnClick(R.id.complete_now_btn)
    public void onClick() {

        if (mdDisposable != null) {
            mdDisposable.dispose();
        }

        ForwardUtil.getInstance(this).forward(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }
}
