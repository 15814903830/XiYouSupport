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
import com.chengfan.xiyou.domain.contract.MineOngoingContract;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.chengfan.xiyou.domain.presenter.MineOngoingPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MinePlaceOngoingAdapter;
import com.chengfan.xiyou.ui.adapter.MineTakingOngoingAdapter;
import com.chengfan.xiyou.ui.dialog.TakingUnprovedDialog;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/22:22
 * @Description: 陪玩中
 */
public class MineOngoingFragment extends BaseFragment<MineOngoingContract.View, MineOngoingPresenterImpl> implements MineOngoingContract.View {
    View mView;
    @BindView(R.id.order_o_rv)
    RecyclerView mOrderORv;
    @BindView(R.id.order_o_zrl)
    ZRefreshLayout mOrderOZrl;
    Unbinder mUnbinder;

    List<MineOrderPlaceEntity> mPlaceEntityList;
    List<MineOrderTakingEntity> mTakingEntityList;


    MinePlaceOngoingAdapter mPlaceOngoingAdapter;
    MineTakingOngoingAdapter mTakingOngoingAdapter;

    String toWho;
    int page = 1;

    View emptyView;

    int selectPosition;
    TakingUnprovedDialog mTakingUnprovedDialog;

    public static MineOngoingFragment getInstance(String toWho) {
        MineOngoingFragment newsFragment = new MineOngoingFragment();
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

        mPresenter = new MineOngoingPresenterImpl();
        mPresenter.onAttach(getActivity(), this);


        Bundle arguments = this.getArguments();
        toWho = arguments.getString(APPContents.BUNDLE_FRAGMENT);

        if (toWho.equals(APPContents.TO_PLACE)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, true);
            mPresenter.placeOngoingParameter(1, true);
        } else if (toWho.equals(APPContents.TO_TAKING)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, false);
            mPresenter.takingOngoingParameter(1, true);
        }


        mTakingUnprovedDialog = new TakingUnprovedDialog(getActivity());

        initView();
        initZrl();
        return mView;
    }

    private void initView() {
        if (toWho.equals(APPContents.TO_PLACE)) {
            mPlaceEntityList = new ArrayList<>();

            mPlaceOngoingAdapter = new MinePlaceOngoingAdapter(R.layout.adapter_mine_ongoing, mPlaceEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mPlaceOngoingAdapter);
            mPlaceOngoingAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mPlaceEntityList.get(position).getUniformOrderId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });
        } else {

            mTakingOngoingAdapter = new MineTakingOngoingAdapter(R.layout.adapter_mine_ongoing, mTakingEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mTakingOngoingAdapter);

            mTakingOngoingAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mTakingEntityList.get(position).getUniformOrderId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });

            mTakingUnprovedDialog.setTakingUnprovedListener(new TakingUnprovedDialog.TakingUnprovedListener() {
                @Override
                public void onTakingUnprovedListener() {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mTakingEntityList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mTakingEntityList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(3);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                }
            });


            mTakingOngoingAdapter.setTakingOngoingListener(new MineTakingOngoingAdapter.TakingOngoingListener() {
                @Override
                public void onTakingOngoingListener(int position) {
                    selectPosition = position;
                    mTakingUnprovedDialog.show();
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
                            mPresenter.placeOngoingParameter(page, false);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingOngoingParameter(page, false);
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
                            mPresenter.placeOngoingParameter(page, true);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingOngoingParameter(page, true);
                        }
                        mOrderOZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void placeOngoingLoad(List<MineOrderPlaceEntity> mineOrderPlaceEntityList, boolean isPtr) {
        if (mineOrderPlaceEntityList.size() < 1) {
            mPlaceOngoingAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mPlaceEntityList = mineOrderPlaceEntityList;
            } else {
                mPlaceEntityList.addAll(mineOrderPlaceEntityList);
            }

            mPlaceOngoingAdapter.setNewData(mineOrderPlaceEntityList);
        }
    }

    @Override
    public void takingOngoingLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mTakingOngoingAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mTakingEntityList = mineOrderEntityList;
            } else {
                mTakingEntityList.addAll(mineOrderEntityList);
            }
            mTakingOngoingAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void updateOrderStatusLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }
}
