package com.chengfan.xiyou.ui;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.MediaLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.zero.ci.base.BaseApplication;
import com.zero.ci.widget.lightkv.GlobalConfig;

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.LogUtils;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-02/14:04
 * @Description:
 */
public class UIApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initAuto();

        RongIM.init(this);


        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );

        GlobalConfig.setAppContext(mContext);
        AppData.data();

    }



    private void initAuto() {
        AutoSize.initCompatMultiProcess(this);

        AutoSizeConfig.getInstance()

                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                })

        ;
    }


}
