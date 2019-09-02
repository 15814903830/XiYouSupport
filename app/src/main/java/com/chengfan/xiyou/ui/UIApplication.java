package com.chengfan.xiyou.ui;

import android.app.Activity;
import android.net.Uri;

import com.chengfan.xiyou.common.APIContents;
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
    private UserUtils userUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        initAuto();
        LitePal.initialize(this);
        RongIM.init(this);
        //设置接收消息的监听器
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
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
        //设置消息体内携带用户信息
        RongIM.getInstance().setMessageAttachedUserInfo(true);

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


}
