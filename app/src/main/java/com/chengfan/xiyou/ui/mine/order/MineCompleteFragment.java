package com.chengfan.xiyou.ui.mine.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineCompleteContract;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.presenter.MineCompletePresenterImpl;
import com.chengfan.xiyou.ui.adapter.MinePlaceCompleteAdapter;
import com.chengfan.xiyou.ui.adapter.MineTakingCompleteAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/22:25
 * @Description: 完成
 */
public class MineCompleteFragment extends BaseFragment<MineCompleteContract.View, MineCompletePresenterImpl> implements MineCompleteContract.View {
    View mView;
    @BindView(R.id.order_o_rv)
    RecyclerView mOrderORv;
    @BindView(R.id.order_o_zrl)
    ZRefreshLayout mOrderOZrl;
    Unbinder mUnbinder;

    MineTakingCompleteAdapter mTakingCompleteAdapter;
    MinePlaceCompleteAdapter mPlaceCompleteAdapter;

    List<MineOrderPlaceEntity> mPlaceEntityList;
    List<MineOrderTakingEntity> mTakingEntityList;

    String toWho;
    int page = 1;

    View emptyView;

    public static MineCompleteFragment getInstance(String toWho) {
        MineCompleteFragment newsFragment = new MineCompleteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(APPContents.BUNDLE_FRAGMENT, toWho);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine_order_o, null);
        mUnbinder = ButterKnife.bind(this, mView);


        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mOrderORv.getParent(), false);

        mPresenter = new MineCompletePresenterImpl();
        mPresenter.onAttach(getActivity(), this);


        Bundle arguments = this.getArguments();
        toWho = arguments.getString(APPContents.BUNDLE_FRAGMENT);

        if (toWho.equals(APPContents.TO_PLACE)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, true);
            mPresenter.placeCompleteParameter(1, true);
        } else if (toWho.equals(APPContents.TO_TAKING)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, false);
            mPresenter.takingCompleteParameter(1, true);
        }


        initView();
        initZrl();

        return mView;
    }

    private void initView() {
        if (toWho.equals(APPContents.TO_PLACE)) {

            mPlaceEntityList = new ArrayList<>();
            mPlaceCompleteAdapter = new MinePlaceCompleteAdapter(R.layout.adapter_mine_complete, mPlaceEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mPlaceCompleteAdapter);
            mPlaceCompleteAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mPlaceEntityList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });
        } else {
            mTakingEntityList = new ArrayList<>();
            mTakingCompleteAdapter = new MineTakingCompleteAdapter(R.layout.adapter_mine_complete, mTakingEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mTakingCompleteAdapter);
            mTakingCompleteAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mTakingEntityList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });

        }
    }

    private void initZrl() {
        mOrderOZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mOrderOZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;

                        if (toWho.equals(APPContents.TO_PLACE)) {
                            mPresenter.placeCompleteParameter(page, false);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingCompleteParameter(page, false);
                        }
                        mOrderOZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                mOrderOZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;

                        if (toWho.equals(APPContents.TO_PLACE)) {
                            mPresenter.placeCompleteParameter(page, true);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingCompleteParameter(page, true);
                        }
                        mOrderOZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void placeCompleteLoad(List<MineOrderPlaceEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mPlaceCompleteAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mPlaceEntityList = mineOrderEntityList;
            } else {
                mPlaceEntityList.addAll(mineOrderEntityList);
            }
            mPlaceCompleteAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void takingCompleteLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mTakingCompleteAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mTakingEntityList = mineOrderEntityList;
            } else {
                mTakingEntityList.addAll(mineOrderEntityList);
            }
            mTakingCompleteAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void updateOrderStatusLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }
}
