package com.chengfan.xiyou.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.MineEntity;
import com.chengfan.xiyou.domain.model.entity.XiYouBean;
import com.chengfan.xiyou.ui.adapter.XiYouSelectAdapter;
import com.chengfan.xiyou.ui.login.LoginActivity;
import com.chengfan.xiyou.ui.login.WeiXingActivity;
import com.chengfan.xiyou.ui.mine.MineAboutActivity;
import com.chengfan.xiyou.ui.mine.MineAttentionActivity;
import com.chengfan.xiyou.ui.mine.MineEditInfoActivity;
import com.chengfan.xiyou.ui.mine.MineFamilyActivity;
import com.chengfan.xiyou.ui.mine.MineFansActivity;
import com.chengfan.xiyou.ui.mine.MineGameActivity;
import com.chengfan.xiyou.ui.mine.MineInviteActivity;
import com.chengfan.xiyou.ui.mine.MineMemberActivity;
import com.chengfan.xiyou.ui.mine.MineMoneyActivity;
import com.chengfan.xiyou.ui.mine.MineOrderActivity;
import com.chengfan.xiyou.ui.mine.MineRecordActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.BoldTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.chengfan.xiyou.view.WebKeFuActivity;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/10:44
 * @Description: 我的
 */
public class MineFragment extends BaseFragment {
    View mView;
    @BindView(R.id.mine_user_name_tv)
    BoldTextView mMineUserNameTv;
    @BindView(R.id.mine_phone_tv)
    RegularTextView mMinePhoneTv;
    @BindView(R.id.mine_pic_civ)
    CircleImageView mMinePicCiv;
    @BindView(R.id.mini_huiyuan_iv)
    ImageView mMiniHuiyuanIv;
    @BindView(R.id.mine_guan_tv)
    BoldTextView mMineGuanTv;
    @BindView(R.id.mine_fen_tv)
    BoldTextView mMineFenTv;
    @BindView(R.id.mine_dong_tv)
    BoldTextView mMineDongTv;
    @BindView(R.id.mine_order_ll)
    LinearLayout mMineOrderLl;
    @BindView(R.id.mine_game_ll)
    LinearLayout mMineGameLl;
    @BindView(R.id.mine_money_ll)
    LinearLayout mMineMoneyLl;
    @BindView(R.id.mine_rv)
    RecyclerView mMineRv;

    Unbinder mUnbinder;
    private boolean data = true;
    List<XiYouBean> mXiYouBeanList;
    AttentionSelectListener mAttentionSelectListener;
    MineEntity mMineEntity;

    public void setAttentionSelectListener(AttentionSelectListener attentionSelectListener) {
        mAttentionSelectListener = attentionSelectListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mXiYouBeanList = new ArrayList<>();
        mMineEntity = new MineEntity();
        setMineData();
        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
        request();
    }

    @OnClick({R.id.mine_top_cl, R.id.mine_guan_ll, R.id.mine_fen_ll, R.id.mine_dong_ll, R.id.mine_order_ll, R.id.mine_game_ll, R.id.mine_money_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_top_cl:
                /*编辑资料*/
                ForwardUtil.getInstance(getActivity()).forward(MineEditInfoActivity.class);
                break;
            case R.id.mine_guan_ll:
                /*关注*/
                ForwardUtil.getInstance(getActivity()).forward(MineAttentionActivity.class);
                break;
            case R.id.mine_fen_ll:
                /*粉丝  ===>>>  wv  <> */
                ForwardUtil.getInstance(getActivity()).forward(MineFansActivity.class);
                break;
            case R.id.mine_dong_ll:
                if (mAttentionSelectListener != null)
                    mAttentionSelectListener.onAttentionSelectListener();
                break;
            case R.id.mine_order_ll:
                /*陪玩订单*/
                ForwardUtil.getInstance(getActivity()).forward(MineOrderActivity.class);
                break;
            case R.id.mine_game_ll:
                /*我的陪玩*/
                ForwardUtil.getInstance(getActivity()).forward(MineGameActivity.class);
                break;
            case R.id.mine_money_ll:
                /*我的钱包  ====>> wv  <>*/
                ForwardUtil.getInstance(getActivity()).forward(MineMoneyActivity.class);
                break;
        }
    }


    private void setMineData() {
    //    mXiYouBeanList.add(new XiYouBean(R.drawable.mine_huiyuan, getResources().getString(R.string.mine_huiyuan_txt), "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.mine_yaoqing, getResources().getString(R.string.mine_yaoqing_txt), "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.mine_jiazu, getResources().getString(R.string.mine_familys_txt), "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.mine_xiaofei, getResources().getString(R.string.mine_xiaofei_txt), "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.ss, "提交认证", "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.mine_kefu, getResources().getString(R.string.mine_kefu_txt), "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.mine_about, getResources().getString(R.string.mine_about_txt), "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.mine_logout, getResources().getString(R.string.mine_logout_txt), "1"));
        XiYouSelectAdapter xiYouSelectAdapter = new XiYouSelectAdapter(R.layout.adapter_accompany_select_game, mXiYouBeanList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        mMineRv.setLayoutManager(gridLayoutManager);
        mMineRv.setAdapter(xiYouSelectAdapter);
        xiYouSelectAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {

                switch (position) {
//                    case 0:
//                        /*我的会员*/
//                        ForwardUtil.getInstance(getActivity()).forward(MineMemberActivity.class);
//                        break;
                    case 0:
                        /*我的邀请 ====>>>  wv*/
                        ForwardUtil.getInstance(getActivity()).forward(MineInviteActivity.class);
                        break;
                    case 1:
                        /*我的家族*/
                        ForwardUtil.getInstance(getActivity()).forward(MineFamilyActivity.class);
                        break;
                    case 2:
                        /*消费记录  =====>>>  wv 《》*/
                        ForwardUtil.getInstance(getActivity()).forward(MineRecordActivity.class);
                        break;
                    case 3:
                        /*提交认证*/
                        ForwardUtil.getInstance(getActivity()).forward(SubmitLabelActivity.class);
                        break;
                    case 4:
                        /*联系客服*/
                        startActivity(new Intent(getContext(), WebKeFuActivity.class));
                        break;
                    case 5:
                        /*关于我们  =====>>>  wv*/
                        ForwardUtil.getInstance(getActivity()).forward(MineAboutActivity.class);
                        break;
                    case 6:
                        /*退出账号*/
                        ForwardUtil.getInstance(getActivity()).forward(WeiXingActivity.class);
                        if (getActivity()!=null)
                            getActivity().finish();
                        break;

                }
            }
        });

    }


    private void request() {
        HttpRequest.get(APIContents.Conter)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {

                        Type type = new TypeToken<MineEntity>() {
                        }.getType();
                        mMineEntity = new Gson().fromJson(result, type);
                        if (mMineEntity != null)
                            initView(mMineEntity);
                    }
                });
    }


    private void initView(MineEntity mineEntity) {

        if (mineEntity.getAvatarUrl() == null) {
            if (mineEntity.getGender() == 1) {
                mMinePicCiv.setImageResource(R.drawable.complete_nan);
            } else {
                mMinePicCiv.setImageResource(R.drawable.complete_nv);
            }
        } else {
            ImageLoaderManager.getInstance().showImage(mMinePicCiv, APIContents.HOST + "/" + mineEntity.getAvatarUrl());
        }
        mMineUserNameTv.setText(mineEntity.getNickname());
        ImageLoaderManager.getInstance().showImage(mMinePicCiv, APIContents.HOST + "/" + mineEntity.getAvatarUrl());
        if (mineEntity.isVip()) {
            mMiniHuiyuanIv.setVisibility(View.VISIBLE);
        } else {
            mMiniHuiyuanIv.setVisibility(View.GONE);
        }
        mMinePhoneTv.setText(mineEntity.getUserName());
        mMineGuanTv.setText(mineEntity.getTotalCare() + "");
        mMineFenTv.setText(mineEntity.getTotalFans() + "");
        mMineDongTv.setText(mineEntity.getTotalNews() + "");
    }


    public interface AttentionSelectListener {
        void onAttentionSelectListener();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (data) {
                data = false;
            } else {
                data = false;
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
