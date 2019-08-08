package com.chengfan.xiyou.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.contract.MineMemberContract;
import com.chengfan.xiyou.domain.model.entity.GetAccountEntity;
import com.chengfan.xiyou.domain.model.entity.MemberSelectBean;
import com.chengfan.xiyou.domain.model.entity.VIPOrderBean;
import com.chengfan.xiyou.domain.presenter.MineMemberPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MineMemberSelectAdapter;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/13:01
 * @Description: 我的会员
 */
public class MineMemberActivity extends BaseActivity<MineMemberContract.View, MineMemberPresenterImpl> implements MineMemberContract.View {

    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.member_title_tv)
    TextView mMemberTitleTv;
    @BindView(R.id.member_des_tv)
    TextView mMemberDesTv;
    @BindView(R.id.member_rv)
    RecyclerView mMemberRv;
    @BindView(R.id.member_take_money_tv)
    TextView mMemberTakeMoneyTv;
    private Map<String, String> parm;
    public static Set<Integer> positionSet = new HashSet<>();
    List<MemberSelectBean> mMemberSelectBeanList;
    MineMemberSelectAdapter mMemberSelectAdapter;
    GetAccountEntity mGetAccountEntity;
    @BindView(R.id.member_pay_tv)
    TextView mMemberPayTv;
    private static MineMemberActivity instance;
    MemberSelectBean mMemberSelectBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_member);
        ButterKnife.bind(this);
        this.instance = this;
        UltimateBar.Companion.with(this)
                .applyNavigation(false)
                .statusDark(false)
                .create()
                .immersionBar();


        mXyMiddleTv.setText(getResources().getText(R.string.member_top_title_txt));

        mMemberSelectBeanList = new ArrayList<>();
        mGetAccountEntity = new GetAccountEntity();


        mPresenter.accountParameter();
        mPresenter.memberSelectParameter();

        mMemberSelectAdapter = new MineMemberSelectAdapter(mMemberSelectBeanList);
        mMemberRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberRv.setAdapter(mMemberSelectAdapter);
        addOrRemove(0);
        mMemberSelectAdapter.setOnItemClickListener(new MineMemberSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!positionSet.contains(position)) {
                    // 选择不同的单位时取消之前选中的单位
                    positionSet.clear();
                }
                mMemberTakeMoneyTv.setText(mMemberSelectBeanList.get(position).getPrice() + "");
                mMemberSelectBean = mMemberSelectBeanList.get(position);
                addOrRemove(position);
            }


        });


    }

    public static MineMemberActivity getInstance() {
        return instance;
    }
    private void initView() {
        if (mGetAccountEntity != null) {
            if (mGetAccountEntity.isVip()) {
                mMemberTitleTv.setText(getResources().getString(R.string.member_title_txt));
                mMemberDesTv.setText(mGetAccountEntity.getVipEffectiveDate().toString());
                mMemberPayTv.setText("立即续费");
            } else {
                mMemberTitleTv.setText(getResources().getString(R.string.member_no_title_txt));
                mMemberDesTv.setText(getResources().getString(R.string.member_no_des_txt));
                mMemberPayTv.setText("立即开通");
            }
        }
    }


    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            //positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
        }
        if (positionSet.size() == 0) {
            // 如果没有选中任何的item，则退出多选模式
            mMemberSelectAdapter.notifyDataSetChanged();
        } else {
            // 更新列表界面，否则无法显示已选的item
            mMemberSelectAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void accountLoad(GetAccountEntity accountEntity) {
        mGetAccountEntity = accountEntity;
        initView();
    }

    @Override
    public void memberSelectLoad(List<MemberSelectBean> memberSelectBeanList) {

        mMemberSelectBeanList = memberSelectBeanList;
        mMemberSelectAdapter.notify(mMemberSelectBeanList);
        mMemberSelectBean = mMemberSelectBeanList.get(0);
    }

    @Override
    public void vipOrderLoad(String result) {
        Logger.d("MineMemberActivity  === >>> " + result);
    }

    @OnClick({R.id.xy_back_btn, R.id.member_pay_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.member_pay_tv:

                VIPOrderBean vipOrderBean = new VIPOrderBean();
                vipOrderBean.setMemberId(mGetAccountEntity.getId());
                vipOrderBean.setVipSetMealId(mMemberSelectBean.getId());

                Log.e("mMemberSelectBean:",""+mMemberSelectBean.getId());
                Log.e("mGetAccountEntity:",""+mGetAccountEntity.getId());

                vipOrderBean.setStatus(3);
                vipOrderBean.setBody(mMemberSelectBean.getTitle());
                vipOrderBean.setPayPrice(mMemberSelectBean.getPrice());
                vipOrderBean.setVipDay(mMemberSelectBean.getDays());
                mPresenter.vipOrderParameter(vipOrderBean);

                Log.e("vipSetMealId:",""+vipOrderBean.getVipSetMealId());
                Log.e("getMemberId:",""+vipOrderBean.getMemberId());
                initnext(vipOrderBean.getVipSetMealId(),""+vipOrderBean.getMemberId(),"2");
                break;
        }
    }

    private void initnext(String vipSetMealId,String getMemberId,String paymentChannel) {
    }
}
