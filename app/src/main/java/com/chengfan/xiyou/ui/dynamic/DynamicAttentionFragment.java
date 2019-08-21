package com.chengfan.xiyou.ui.dynamic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.DynamicAttentionContract;
import com.chengfan.xiyou.domain.model.entity.FinanceRecordEntity;
import com.chengfan.xiyou.domain.model.entity.LikeBase;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.chengfan.xiyou.domain.presenter.DynamicAttentionPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.ui.adapter.DynamicAttentionAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/18:53
 * @Description: 我的关注
 */
public class DynamicAttentionFragment extends BaseFragment<DynamicAttentionContract.View, DynamicAttentionPresenterImpl> implements DynamicAttentionContract.View, HttpCallBack {
    View mView;
    @BindView(R.id.attention_rv)
    RecyclerView mAttentionRv;
    @BindView(R.id.attention_zrl)
    ZRefreshLayout mAttentionZrl;

    Unbinder mUnbinder;

    List<FinanceRecordEntity> mFinanceRecordEntityList;
    DynamicAttentionAdapter mDynamicAttentionAdapter;

    HttpCallBack mHttpCallBack;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_attention, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mPresenter = new DynamicAttentionPresenterImpl();
        mPresenter.onAttach(getActivity(), this);
        mFinanceRecordEntityList = new ArrayList<>();
        mHttpCallBack=this;
        initRv();
        initZrl();
        mPresenter.listParameter(1, true);
        return mView;
    }
    private void initZrl() {
        mAttentionZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAttentionZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.listParameter(page, false);
                        mAttentionZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mAttentionZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.listParameter(page, true);
                        mAttentionZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void listLoad(List<FinanceRecordEntity> financeRecordEntityList, boolean isPtr) {

        Log.e("financeRecordEntityList",""+financeRecordEntityList.size());
        if (financeRecordEntityList != null) {
            if (isPtr) {
                mDynamicAttentionAdapter.replaceData(mFinanceRecordEntityList);
                mFinanceRecordEntityList = financeRecordEntityList;
            } else {
                mFinanceRecordEntityList.addAll(financeRecordEntityList);
            }
            Log.e("mFinanceRecordEntity",""+mFinanceRecordEntityList.size());
            mDynamicAttentionAdapter.setNewData(mFinanceRecordEntityList);
        }
    }


    private void initRv() {
        mDynamicAttentionAdapter = new DynamicAttentionAdapter(R.layout.adapter_dynamic_attention, mFinanceRecordEntityList);
        mAttentionRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAttentionRv.setAdapter(mDynamicAttentionAdapter);
        mDynamicAttentionAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putString(APPContents.BUNDLE_DYNAMIC_ID, mFinanceRecordEntityList.get(position).getId() + "");
                Logger.d("DynamicAttentionFragment === >>> " + mFinanceRecordEntityList.get(position).getId());
                ForwardUtil.getInstance(getActivity()).forward(DynamicDetailActivity.class, toBundle);
            }
        });


        mDynamicAttentionAdapter.setLickListener(new DynamicAttentionAdapter.LickListener() {
            @Override
            public void onLickListener(final int position) {
                LikeBase shipBean = new LikeBase();
                shipBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                shipBean.setMemberNewsId(""+mFinanceRecordEntityList.get(position).getId());
                HttpRequest.post(APIContents.MEMBER_LIKE)
                        .paramsJsonString(new Gson().toJson(shipBean))
                        .execute(new AbstractResponse<BaseApiResponse>() {
                            @Override
                            public void onSuccess(BaseApiResponse result) {
                                if (result.isSuc()) {
                                    if (mFinanceRecordEntityList.get(position).isHavePraise()) {
                                        mFinanceRecordEntityList.get(position).setHavePraise(false);
                                        mFinanceRecordEntityList.get(position).setTotalPraise(mFinanceRecordEntityList.get(position).getTotalPraise() - 1);
                                    } else {
                                        mFinanceRecordEntityList.get(position).setHavePraise(true);
                                        mFinanceRecordEntityList.get(position).setTotalPraise(mFinanceRecordEntityList.get(position).getTotalPraise() + 1);
                                    }
                                    mDynamicAttentionAdapter.setNewData(mFinanceRecordEntityList);
                                    mDynamicAttentionAdapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onResponse(String response, int requestId) {

    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {

    }
}
