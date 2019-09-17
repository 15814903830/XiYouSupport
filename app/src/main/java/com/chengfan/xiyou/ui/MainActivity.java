package com.chengfan.xiyou.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.LoginEntity;
import com.chengfan.xiyou.domain.model.entity.MineEntity;
import com.chengfan.xiyou.im.GroupChatInfo;
import com.chengfan.xiyou.im.UserIMInfo;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.main.AccompanyFragment;
import com.chengfan.xiyou.ui.main.ChatFragment;
import com.chengfan.xiyou.ui.main.DynamicFragment;
import com.chengfan.xiyou.ui.main.HomeFragment;
import com.chengfan.xiyou.ui.main.MineFragment;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.Attention;
import com.chengfan.xiyou.utils.RongGetToken;
import com.chengfan.xiyou.utils.UserStorage;
import com.chengfan.xiyou.utils.status.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.tool.ActManager;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.pushperm.ResultCallback;
import io.rong.pushperm.RongPushPremissionsCheckHelper;


public class MainActivity extends BaseActivity implements HttpCallBack , Attention {

    @BindView(R.id.ll_tab_one_main)
    LinearLayout ll_one;
    @BindView(R.id.ll_tab_two_main)
    LinearLayout ll_two;
    @BindView(R.id.ll_tab_three_main)
    LinearLayout ll_three;
    @BindView(R.id.ll_tab_four_main)
    LinearLayout ll_four;
    @BindView(R.id.ll_tab_five_main)
    LinearLayout ll_five;
    @BindView(R.id.tv_notice_dot)
    public TextView tvNoticeDot;
    private static final String TAG_ONE = "one";
    private static final String TAG_TWO = "two";
    private static final String TAG_THREE = "three";
    private static final String TAG_FOUR = "four";
    private static final String TAG_FIVE = "five";

    HomeFragment mHomeFragment;
    DynamicFragment mDynamicFragment;
    AccompanyFragment mAccompanyFragment;
    ChatFragment mChatFragment;
    MineFragment mMineFragment;

    private HttpCallBack mHttpCallBack;

    private FragmentManager fm;
    private String mTag;
    private Fragment mFragment;
    private CloudPushService mPushService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((UIApplication) this.getApplication()).getUserUtils().setattention(this);
        mPushService = PushServiceFactory.getCloudPushService();
        ButterKnife.bind(this);
        UIApplication.setMainActivity(this);
        //修改状态栏的文字颜色为黑色
        int flag = StatusBarUtil.StatusBarLightMode(this);
        StatusBarUtil.StatusBarLightMode(this, flag);

        mHttpCallBack = this;

        fm = getSupportFragmentManager();

        initView();

        initUserInfo();
        getGroupChatInfo();
//        RongPushPremissionsCheckHelper.checkPermissionsAndShowDialog(this, new ResultCallback() {
//            @Override
//            public void onAreadlyOpened(String value) {
//
//            }
//
//            @Override
//            public boolean onBeforeShowDialog(String value) {
//                return false;
//            }
//
//            @Override
//            public void onGoToSetting(String value) {
//
//            }
//
//            @Override
//            public void onFailed(String value, FailedType type) {
//
//            }
//        });


    }
    /**
     * 绑定账户接口:CloudPushService.bindAccount调用示例
     * 1. 绑定账号后,可以在服务端通过账号进行推送
     * 2. 一个设备只能绑定一个账号
     */
    private void bindAccount() {
        mPushService.bindAccount(AppData.getString(AppData.Keys.AD_USER_ID), new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("MainActivity", "绑定账号成功：" + AppData.getString(AppData.Keys.AD_USER_ID));
            }

            @Override
            public void onFailed(String errorCode, String errorMsg) {
                Log.e("MainActivity", "绑定账号失败：" + AppData.getString(AppData.Keys.AD_USER_ID));
            }
        });
    }
    private void getImei() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"请允许获取设备信息权限",Toast.LENGTH_SHORT);
            return;
        }
        String szImei = TelephonyMgr.getDeviceId();
        Log.i("MainActivity","设备ID：" + szImei);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindAccount();
        getImei();
    }


    private void initView() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        showFragment(mHomeFragment, TAG_ONE);
    }

    @OnClick({R.id.ll_tab_one_main, R.id.ll_tab_two_main, R.id.ll_tab_three_main,
            R.id.ll_tab_four_main, R.id.ll_tab_five_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_one_main:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                showFragment(mHomeFragment, TAG_ONE);
                break;
            case R.id.ll_tab_two_main:
                if (mAccompanyFragment == null) {
                    mAccompanyFragment = new AccompanyFragment();
                }
                showFragment(mAccompanyFragment, TAG_TWO);
                break;
            case R.id.ll_tab_three_main:
                tvNoticeDot.setVisibility(View.GONE);
                if (mChatFragment == null) {
                    mChatFragment = new ChatFragment();
                }
                showFragment(mChatFragment, TAG_THREE);
                break;
            case R.id.ll_tab_four_main:
                if (mDynamicFragment == null) {
                    mDynamicFragment = new DynamicFragment();
                }
                showFragment(mDynamicFragment, TAG_FOUR);
                break;
            case R.id.ll_tab_five_main:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                }
                showFragment(mMineFragment, TAG_FIVE);
                break;
        }
    }

    //收到推送
    public void receiveThePush(String text) {
        tvNoticeDot.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Fragment
     */
    private void showFragment(Fragment fragment, String tag) {
        if (tag.equals(mTag)) {
            return;
        }
        FragmentTransaction transaction = fm.beginTransaction();
        if (mFragment != null) {
            transaction.hide(mFragment);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.frame_main, fragment, tag);
        }
        transaction.commit();
        mTag = tag;
        mFragment = fragment;
        initTabLayout();
    }

    /**
     * 初始化TAB布局
     */
    private void initTabLayout() {
        ll_one.setSelected(false);
        ll_two.setSelected(false);
        ll_three.setSelected(false);
        ll_four.setSelected(false);
        ll_five.setSelected(false);
        switch (mTag) {
            case TAG_ONE:
                ll_one.setSelected(true);
                break;
            case TAG_TWO:
                ll_two.setSelected(true);
                break;
            case TAG_THREE:
                ll_three.setSelected(true);
                break;
            case TAG_FOUR:
                ll_four.setSelected(true);
                break;
            case TAG_FIVE:
                ll_five.setSelected(true);
                break;
        }
    }


    private void initUserInfo() {
        HttpRequest.get(APIContents.Conter)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(   new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.isEmpty()) {
                            return;
                        }
                        Type type = new TypeToken<MineEntity>() {
                        }.getType();
                        final MineEntity mineEntity = new Gson().fromJson(result, type);
                        LoginEntity loginEntity = new LoginEntity();
                        loginEntity.setId(mineEntity.getId());
                        loginEntity.setWeiXinUid(String.valueOf(mineEntity.getWeiXin()));
                        loginEntity.setNickname(mineEntity.getNickname());
                        loginEntity.setAvatarUrl(mineEntity.getAvatarUrl());
                        loginEntity.setAge(mineEntity.getAge());
                        loginEntity.setAreaName(mineEntity.getAreaName());
                        loginEntity.setAreaCode(mineEntity.getAreaCode());
                        loginEntity.setJob(mineEntity.getJob());
                        UserStorage.getInstance().saveLoginInfo(loginEntity);

                        saveUserInfo(mineEntity.getId(), mineEntity.getNickname(), mineEntity.getAvatarUrl());

                        new Thread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        connect(RongGetToken.GetRongCloudToken(String.valueOf(mineEntity.getId()), mineEntity.getNickname(), APIContents.HOST + "/" + mineEntity.getAvatarUrl()));
                                    }
                                }
                        ).start();
                    }
                });
    }

    /**
     * 保存用户信息
     *
     * @param userId
     * @param username
     * @param userImage
     */
    private void saveUserInfo(int userId, String username, String userImage) {
        UserIMInfo userInfo = new UserIMInfo();
        userInfo.setId(userId);
        userInfo.setNickname(username);
        userInfo.setAvatarUrl(APIContents.HOST + "/" + userImage);
        userInfo.save();
    }

    /**
     * 获取群组信息
     */
    private void getGroupChatInfo() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = APIContents.HOST + "/api/Member/TeamList/" + AppData.getString(AppData.Keys.AD_USER_ID);
                OkHttpUtils.doGet(url, mHttpCallBack, 0);
            }
        }.start();
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Logger.e("MainActivity ===>>> onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                APPContents.E_ROYUN_ID = userid;
                Log.e("ronyun", userid);
                Logger.e("MainActivity ===>>>  connect rong--->>> onSuccess " + userid);

            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("ronyunerrorCode", errorCode.getMessage());
            }
        });
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.show("再点击一次直接退出！！！");
                exitTime = System.currentTimeMillis();
            } else {
                ActManager.appExit();
                finish();
                //System.exit(0);
                //ActManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandler.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandler.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        Log.e("response", response);
        switch (requestId) {
            case 0:
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        JSONObject object = jsonObject.getJSONObject("team");
                        GroupChatInfo groupChatInfo = new GroupChatInfo();
                        groupChatInfo.setTargetId(object.getString("id"));
                        groupChatInfo.setName(object.getString("name"));
                        groupChatInfo.setImage(APIContents.HOST + "/" + object.getString("avatarUrl"));
                        groupChatInfo.save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };

    @Override
    public void attention() {
        if (mDynamicFragment == null) {
            mDynamicFragment = new DynamicFragment();
        }
        showFragment(mDynamicFragment, TAG_FOUR);
    }
}
