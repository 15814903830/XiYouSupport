package com.chengfan.xiyou.ui.accompany;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyDetailContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyDetailEntity;
import com.chengfan.xiyou.domain.model.entity.CheckLetterEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyDetailPresenterImpl;
import com.chengfan.xiyou.im.UserIMInfo;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.adapter.AccompanyDetailAdapter;
import com.chengfan.xiyou.ui.dialog.CheckLetterDialog;
import com.chengfan.xiyou.ui.dialog.CheckVIPDialog;
import com.chengfan.xiyou.ui.mine.MineMemberActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.DataFormatUtil;
import com.chengfan.xiyou.utils.status.StatusBarUtil;
import com.chengfan.xiyou.view.BoldTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/17:12
 * @Description: 陪玩详情
 */
public class AccompanyDetailActivity extends
        BaseActivity<AccompanyDetailContract.View, AccompanyDetailPresenterImpl>
        implements AccompanyDetailContract.View, HttpCallBack {

    @BindView(R.id.order_pic_iv)
    ImageView mOrderPicIv;
    @BindView(R.id.order_game_name_tv)
    RegularTextView mOrderGameNameTv;
    @BindView(R.id.order_money_tv)
    RegularTextView mOrderMoneyTv;
    @BindView(R.id.order_type_tv)
    RegularTextView mOrderTypeTv;
    @BindView(R.id.order_time_tv)
    RegularTextView mOrderTimeTv;
    @BindView(R.id.order_user_logo_civ)
    CircleImageView mOrderUserLogoCiv;
    @BindView(R.id.order_user_name_tv)
    RegularTextView mOrderUserNameTv;
    @BindView(R.id.order_focus_tv)
    RegularTextView mOrderFocusTv;
    @BindView(R.id.order_shuoming_tv)
    RegularTextView mOrderShuomingTv;
    @BindView(R.id.order_pinglun_num_tv)
    BoldTextView mOrderPinglunNumTv;
    @BindView(R.id.order_rv)
    RecyclerView mOrderRv;
    private boolean imgmp3=true;

    @BindView(R.id.order_bottom_ll)
    LinearLayout mOrderBottomLl;

    MediaPlayer md = new MediaPlayer();


    //语音文件保存路径
    private String FileName = null;
    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;
    Bundle revBundle;
    int currentMemberId;
    AccompanyDetailEntity mAccompanyDetailEntity;
    CheckLetterEntity checkLetterEntity;
    String CheckVIP;
    CheckLetterDialog mCheckLetterDialog;
    CheckVIPDialog mCheckVIPDialog;
    AccompanyDetailAdapter mAccompanyDetailAdapter;
    @BindView(R.id.order_time_tvbb)
    RegularTextView orderTimeTvbb;
    @BindView(R.id.search_game_name_llbb)
    LinearLayout searchGameNameLlbb;
    @BindView(R.id.order_time_tvllcc)
    RegularTextView orderTimeTvllcc;
    @BindView(R.id.search_game_name_llcc)
    LinearLayout searchGameNameLlcc;
    @BindView(R.id.order_time_tvdd)
    RegularTextView orderTimeTvdd;
    @BindView(R.id.search_game_name_lldd)
    LinearLayout searchGameNameLldd;
    @BindView(R.id.ll_mp3)
    LinearLayout ll_mp3;

    @BindView(R.id.iv_bfzt)
    ImageView iv_bfzt;

    @BindView(R.id.tv_mp3time)
    TextView tv_mp3time;

    private HttpCallBack mHttpCallBack;
    private DetailBase detailBase;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_order);
        ButterKnife.bind(this);

        //修改状态栏的文字颜色为黑色
        int flag = StatusBarUtil.StatusBarLightMode(this);
        StatusBarUtil.StatusBarLightMode(this, flag);

        mHttpCallBack = this;
        revBundle = getIntent().getExtras();
        if (revBundle != null){
            currentMemberId = revBundle.getInt(APPContents.E_CURRENT_MEMBER_ID);
        }

        if (currentMemberId==0){
            currentMemberId=Integer.valueOf(getIntent().getStringExtra("currentMemberId"));
        }

        mAccompanyDetailEntity = new AccompanyDetailEntity();

        mPresenter.accompanyDetailParameter(currentMemberId);
        mPresenter.checkLetterParameter(currentMemberId);
        mPresenter.checkVIPParameter();

        mCheckLetterDialog = new CheckLetterDialog(this);
        mCheckLetterDialog.setCheckListener(new CheckLetterDialog.CheckListener() {
            @Override
            public void onCheckListener() {
                ForwardUtil.getInstance(AccompanyDetailActivity.this).forward(MineMemberActivity.class);
            }
        });

        mCheckVIPDialog = new CheckVIPDialog(this);
        mCheckVIPDialog.setCheckVIPListener(new CheckVIPDialog.CheckVIPListener() {
            @Override
            public void onCheckVIPListener() {
                ForwardUtil.getInstance(AccompanyDetailActivity.this).forward(MineMemberActivity.class);
            }
        });
        getdata();
    }

    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list = new ArrayList<>();
                list.add(new RequestParams("id", "" + currentMemberId));
                list.add(new RequestParams("currentMemberId", AppData.getString(AppData.Keys.AD_USER_ID)));
                OkHttpUtils.doGet(APIContents.ACCOMPANY_DETAIL, list, mHttpCallBack, 0);
            }
        }).start();
    }

    @Override
    public void accompanyDetailLoad(AccompanyDetailEntity accompanyDetailEntity) {
        mAccompanyDetailEntity = accompanyDetailEntity;
        try {
            if (accompanyDetailEntity.getAudioPath()!=null){
                FileName=APIContents.HOST+"/"+mAccompanyDetailEntity.getAudioPath();
            }else {
                ll_mp3.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ll_mp3.setVisibility(View.GONE);
        }
        initView(accompanyDetailEntity);
    }

    @Override
    public void checkLetterLoad(CheckLetterEntity checkLetterEntity) {
        this.checkLetterEntity = checkLetterEntity;
    }

    @Override
    public void checkVIPLoad(String result) {
        CheckVIP = result;
    }

    @Override
    public void memberShipLoad(BaseApiResponse baseApiResponse, boolean isLike) {
        if (baseApiResponse.isSuc()) {
            if (isLike) {
                mOrderFocusTv.setText("关注");
            } else {
                mOrderFocusTv.setText("已关注");
            }
        }
    }

    @Override
    public void sayHiLoad(BaseApiResponse baseApiResponse) {
        saveUserInfo(mAccompanyDetailEntity.getMemberId(), mAccompanyDetailEntity.getNickname(),
                mAccompanyDetailEntity.getAvatarUrl());
        RongIM.getInstance().startPrivateChat(AccompanyDetailActivity.this,
                mAccompanyDetailEntity.getMemberId() + "", mAccompanyDetailEntity.getNickname());
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

    private void initView(final AccompanyDetailEntity accompanyDetailEntity) {
        try {
            if (accompanyDetailEntity.getAudioPath()!=null)
            md.setDataSource(APIContents.HOST+"/"+accompanyDetailEntity.getAudioPath());
            md.prepare();
            tv_mp3time.setText((md.getDuration()/1000)+"s");
        } catch (Exception e) {
            e.printStackTrace();
        }



        mOrderGameNameTv.setText(accompanyDetailEntity.getTitle());
        ImageLoaderManager.getInstance().showImage(mOrderPicIv, APIContents.HOST + "/" + accompanyDetailEntity.getImages());

        mOrderTimeTv.setText(DataFormatUtil.formatWeekDay(accompanyDetailEntity.getWeekDay()));

        ImageLoaderManager.getInstance().showImage(mOrderUserLogoCiv, APIContents.HOST + "/" + accompanyDetailEntity.getAvatarUrl());

        if (accompanyDetailEntity.isIsFans()) {
            mOrderFocusTv.setText("已关注");
        } else {
            mOrderFocusTv.setText("关注");
        }

        mOrderFocusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accompanyDetailEntity.isIsFans()) {
                    mPresenter.memberShipLoad(APPContents.E_CURRENT_MEMBER_IDS, true);
                } else {
                    mPresenter.memberShipLoad(APPContents.E_CURRENT_MEMBER_IDS, false);
                }

            }
        });

        mOrderShuomingTv.setText(accompanyDetailEntity.getRemark());
        mOrderUserNameTv.setText(accompanyDetailEntity.getNickname());
        mOrderMoneyTv.setText("￥" + accompanyDetailEntity.getPrice() + "/小时");

        mAccompanyDetailAdapter = new AccompanyDetailAdapter(R.layout.adapter_accompany_detail, accompanyDetailEntity.getOrder());
        mOrderRv.setLayoutManager(new LinearLayoutManager(this));
        mOrderRv.setAdapter(mAccompanyDetailAdapter);
        mOrderRv.setFocusableInTouchMode(false);
        if (accompanyDetailEntity.getMemberId() == DataFormatUtil.stringToInt(AppData.getString(AppData.Keys.AD_USER_ID))) {
            mOrderBottomLl.setVisibility(View.GONE);
            mOrderFocusTv.setVisibility(View.GONE);
        } else {
            mOrderBottomLl.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.order_back_btn, R.id.order_info_tv, R.id.order_xiadan_tv, R.id.order_user_lick_ll,R.id.ll_mp3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_back_btn:
                finish();
                break;
            case R.id.order_info_tv:
                mPresenter.sayHiParameter(currentMemberId);
                break;
            case R.id.ll_mp3:
                if (imgmp3){
                    iv_bfzt.setSelected(true);
                    imgmp3=true;
                    playmusic();
                }else {
                    iv_bfzt.setSelected(false);
                    stopplaymusic();
                    imgmp3=false;
                }

                break;
            case R.id.order_xiadan_tv:
                //                if ("true".equals(CheckVIP)) {
                //                    Bundle toBundle = new Bundle();
                //                    toBundle.putSerializable(APPContents.BUNDLE_FRAGMENT, mAccompanyDetailEntity);
                //                    ForwardUtil.getInstance(this).forward(AccompanyConfirmOrderActivity.class, toBundle);
                //                } else {
                //                    mCheckVIPDialog.show();
                //                }

                Bundle toBundle = new Bundle();
                toBundle.putSerializable(APPContents.BUNDLE_FRAGMENT, mAccompanyDetailEntity);
                toBundle.putSerializable("detailBase", detailBase);
                ForwardUtil.getInstance(this).forward(AccompanyConfirmOrderActivity.class, toBundle);


                break;
            case R.id.order_user_lick_ll:
                Bundle toBundles = new Bundle();
                toBundles.putInt(APPContents.E_CURRENT_MEMBER_ID, mAccompanyDetailEntity.getMemberId());
                ForwardUtil.getInstance(this).forward(AccompanyUserInfoActivity.class, toBundles);
                break;
        }
    }
    //播放录音
    public void  playmusic(){
        // TODO Auto-generated method stub
        mPlayer = new MediaPlayer();
        try{
            mPlayer.setDataSource(FileName);
            mPlayer.prepare();
            mPlayer.start();

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//播放完成
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    iv_bfzt.setSelected(false);
//                    imgmp3=false;
//                    stopplaymusic();
                }
            });
        }catch(IOException e){
        }
    }


    //停止播放录音
    public void  stopplaymusic(){
        mPlayer.release();
        mPlayer = null;
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
        try {
            detailBase = JSON.parseObject(response, DetailBase.class);
            orderTimeTvbb.setText(detailBase.getServiceStartTime() + "至" + detailBase.getServiceEndTime() + "可陪玩");//陪玩时间
            orderTimeTvllcc.setText(detailBase.getAreaTitle().equals("") ? "暂无大区" : "大区—" + detailBase.getAreaTitle());
            orderTimeTvdd.setText(detailBase.getGradeTitle().equals("") ? "暂无段位" : "段位—" + detailBase.getGradeTitle());
            mOrderTypeTv.setText(detailBase.getSubject().getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
