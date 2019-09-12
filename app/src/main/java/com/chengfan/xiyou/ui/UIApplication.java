package com.chengfan.xiyou.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.chengfan.xiyou.im.GroupChatInfo;
import com.chengfan.xiyou.im.MyReceiveMessageListener;
import com.chengfan.xiyou.im.UserIMInfo;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.MediaLoader;
import com.chengfan.xiyou.utils.UserUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.zero.ci.base.BaseApplication;
import com.zero.ci.widget.lightkv.GlobalConfig;
import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import java.util.List;
import java.util.Locale;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;
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
    private static final String TAG = "UIApplication";
    private static MainActivity mainActivity = null;
    private UserUtils userUtils;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initCloudChannel(this);//阿里云推送
        initAuto();
        PushConfig config = new PushConfig.Builder()
                //.enableHWPush(true)
               .enableMiPush("2882303761518159638", "5411815958638")
//                .enableMeiZuPush("魅族 appId", "魅族 appKey")
           //        .enableFCM(true)
                .build();


        //启用 OPPO vivo 推送服务
//        PushConfig.Builder builder = new PushConfig.Builder();
//        builder.enableVivoPush(true);
//        builder.enableOppoPush(OPPO_App_KEY, OPPO_App_Secret);
//        RongPushClient.setPushConfig(builder.build());




        RongPushClient.setPushConfig(config);
        LitePal.initialize(this);
        RongIM.init(this);
        //设置接收消息的监听器
        // 设置推送消息监听
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
        //设置消息体内携带用户信息
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                return findUserById(s);
            }
        }, true);
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                return findGroupById(s);
            }
        }, true);

        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );

        GlobalConfig.setAppContext(mContext);
        AppData.data();

        CrashReport.initCrashReport(getApplicationContext(), "91ac30603c", true);
        userUtils = new UserUtils();
    }


    public UserUtils getUserUtils() {
        return userUtils;
    }
    /**
     * 查找用户
     *
     * @param s
     * @return
     */
    private UserInfo findUserById(final String s) {
        LitePal.findAllAsync(UserIMInfo.class).listen(new FindMultiCallback<UserIMInfo>() {
            @Override
            public void onFinish(List<UserIMInfo> list) {
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        UserIMInfo userIMInfo = list.get(i);
                        if (userIMInfo.getId() == stringToInt(s)) {
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(
                                    String.valueOf(userIMInfo.getId()),
                                    userIMInfo.getNickname(),
                                    Uri.parse(userIMInfo.getAvatarUrl())));
                        }
                    }
                }
            }
        });
        return null;
    }

    /**
     * 查找群组
     *
     * @param s
     * @return
     */
    private Group findGroupById(final String s) {
        LitePal.findAllAsync(GroupChatInfo.class).listen(new FindMultiCallback<GroupChatInfo>() {
            @Override
            public void onFinish(List<GroupChatInfo> list) {
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        GroupChatInfo groupChatInfo = list.get(i);
                        if (groupChatInfo.getTargetId().equals(s)) {
                            RongIM.getInstance().refreshGroupInfoCache(new Group(
                                    groupChatInfo.getTargetId(),
                                    groupChatInfo.getName(),
                                    Uri.parse(groupChatInfo.getImage())));
                        }
                    }
                }
            }
        });
        return null;
    }

    /**
     * String转int
     * 如果格式错误，返回-1
     *
     * @param value
     * @return
     */
    public static int stringToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
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
                });
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(final Context applicationContext) {
        // 创建notificaiton channel
        this.createNotificationChannel();

        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @SuppressLint("WrongConstant")
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "init cloudchannel success");
                receiveThePush("init cloudchannel success");
                Intent intent = new Intent();
                intent.setAction("org.agoo.android.intent.8.RECEIVE");
                intent.setPackage("com.chengfan.xiyou");//pack为应用包名
                intent.putExtra("type", "common-push");
                intent.addFlags(32);
                applicationContext.sendBroadcast(intent);

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                receiveThePush("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

//        MiPushRegister.register(applicationContext, "XIAOMI_ID", "XIAOMI_KEY"); // 初始化小米辅助推送
//        HuaWeiRegister.register(this); // 接入华为辅助推送
//        VivoRegister.register(applicationContext);
//        OppoRegister.register(applicationContext, "OPPO_KEY", "OPPO_SECRET");
//        MeizuRegister.register(applicationContext, "MEIZU_ID", "MEIZU_KEY");
//        GcmRegister.register(applicationContext, "send_id", "application_id"); // 接入FCM/GCM初始化推送


    }

    public static void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    public static void receiveThePush(String text) {
        Log.e(TAG,text);
    }
    public static void receiveThePushs(String text) {
        Log.e(TAG,text);
        if (mainActivity != null && text != null) {
            mainActivity.receiveThePush(text);
        }
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i(TAG, "手机版本大于26=====");
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            Log.i(TAG, "手机版本小于26=====");
        }
    }
}
