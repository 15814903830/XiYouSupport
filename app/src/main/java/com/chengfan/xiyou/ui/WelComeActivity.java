package com.chengfan.xiyou.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.ui.login.LoginActivity;
import com.chengfan.xiyou.utils.AppData;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/12:37
 * @Description: 启动页
 */
public class WelComeActivity extends BaseActivity {

    private Disposable mdDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();


        mdDisposable = Flowable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d((3 - aLong) + "秒后跳转");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        String uid = AppData.getString(AppData.Keys.AD_USER_ID);
                        if (uid.equals("") || uid == null) {
                            Logger.d("WelComeActivity ===>> go login");
                            ForwardUtil.getInstance(WelComeActivity.this).forward(LoginActivity.class);
                        } else {
                            Logger.d("WelComeActivity ===>> go main");
                            ForwardUtil.getInstance(WelComeActivity.this).forward(MainActivity.class);
                        }

                    }
                })
                .subscribe();


        AppData.putBoolen(AppData.Keys.AD_SHOW_SAY_HI, true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }
}
