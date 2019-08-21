package com.chengfan.xiyou.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.LoginEntity;
import com.chengfan.xiyou.domain.model.entity.MineEntity;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.adapter.VpAdapter;
import com.chengfan.xiyou.ui.main.AccompanyFragment;
import com.chengfan.xiyou.ui.main.ChatFragment;
import com.chengfan.xiyou.ui.main.DynamicFragment;
import com.chengfan.xiyou.ui.main.HomeFragment;
import com.chengfan.xiyou.ui.main.MineFragment;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.RongGetToken;
import com.chengfan.xiyou.utils.UpdateApk;
import com.chengfan.xiyou.utils.UserStorage;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.navigation.BottomNavigationViewEx;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.tool.ActManager;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;



public class MainActivity extends BaseActivity implements HttpCallBack {

    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.fragment_navigation_vp)
    ViewPager mFragmentNavigationVp;
    private VpAdapter adapter;
    private List<Fragment> fragments;
    HomeFragment mHomeFragment;
    DynamicFragment mDynamicFragment;
    AccompanyFragment mAccompanyFragment;
    ChatFragment mChatFragment;
    MineFragment mMineFragment;

    private  HttpCallBack mHttpCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mHttpCallBack=this;
        UpdateApk manager = new UpdateApk(this);
         //manager.checkUpdate();
      //  commitanswers();
        HttpRequest.get(APIContents.Conter)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Type type = new TypeToken<MineEntity>() {
                        }.getType();
                        final MineEntity mineEntity = new Gson().fromJson(result, type);
                        LoginEntity loginEntity = new LoginEntity();
                        Log.e("result",result);
                        loginEntity.setId(mineEntity.getId());
                        loginEntity.setWeiXinUid(String.valueOf(mineEntity.getWeiXin()));
                        loginEntity.setNickname(mineEntity.getNickname());
                        loginEntity.setAvatarUrl(mineEntity.getAvatarUrl());
                        loginEntity.setAge(mineEntity.getAge());
                        loginEntity.setAreaName(mineEntity.getAreaName());
                        loginEntity.setAreaCode(mineEntity.getAreaCode());
                        loginEntity.setJob(mineEntity.getJob());
                        UserStorage.getInstance().saveLoginInfo(loginEntity);
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

        bottomInit();

        getGroupChatInfo();
    }

    /**
     * 获取群组信息
     */
    private void getGroupChatInfo() {

    }


    private void bottomInit() {
        fragments = new ArrayList<>(5);
        mHomeFragment = new HomeFragment();
        mAccompanyFragment = new AccompanyFragment();
        mChatFragment = new ChatFragment();
        mDynamicFragment = new DynamicFragment();
        mMineFragment = new MineFragment();


        // add to fragments for adapter
        fragments.add(mHomeFragment);
        fragments.add(mAccompanyFragment);
        fragments.add(mChatFragment);
        fragments.add(mDynamicFragment);
        fragments.add(mMineFragment);


        //set enable
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.enableAnimation(true);
        mBotNav.setIconSize(20, 20);
        mBotNav.setIconsMarginTop(25);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mFragmentNavigationVp.setAdapter(adapter);
        mFragmentNavigationVp.setOffscreenPageLimit(4);

        // binding with ViewPager
        mBotNav.setupWithViewPager(mFragmentNavigationVp);


        mMineFragment.setAttentionSelectListener(new MineFragment.AttentionSelectListener() {
            @Override
            public void onAttentionSelectListener() {
                mBotNav.setCurrentItem(3);
                Logger.e("AttentionSelectListener ----  is going ");
                AppData.putBoolen(AppData.Keys.AD_IS_SHOW, true);
            }
        });
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
    private void commitanswers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list=new ArrayList<>();
                list.add(new RequestParams(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID)));

                Log.e("commitanswers",APPContents.E_ID);
                Log.e("commitanswers",AppData.getString(AppData.Keys.AD_USER_ID));
                OkHttpUtils.doGet(APIContents.Conter,list,mHttpCallBack,1);

            }
        }).start();
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
        Log.e("response",response);
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
}
