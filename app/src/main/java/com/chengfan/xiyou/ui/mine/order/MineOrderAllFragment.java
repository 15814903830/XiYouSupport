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
import com.chengfan.xiyou.domain.contract.MineOrderAllContract;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.chengfan.xiyou.domain.presenter.MineOrderAllPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MinePlaceOrderAllAdapter;
import com.chengfan.xiyou.ui.adapter.MineTakingOrderAllAdapter;
import com.chengfan.xiyou.ui.dialog.TakingRefuseDialog;
import com.chengfan.xiyou.ui.dialog.TakingTakersDialog;
import com.chengfan.xiyou.ui.dialog.TakingUnprovedDialog;
import com.chengfan.xiyou.ui.dialog.UnprovedDialog;
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
 * @DATE : 2019-07-06/22:19
 * @Description: 全部
 */
public class MineOrderAllFragment extends BaseFragment<MineOrderAllContract.View, MineOrderAllPresenterImpl> implements MineOrderAllContract.View {
    View mView;
    @BindView(R.id.order_o_rv)
    RecyclerView mOrderORv;
    @BindView(R.id.order_o_zrl)
    ZRefreshLayout mOrderOZrl;
    Unbinder mUnbinder;
    List<MineOrderPlaceEntity> mPlaceOrderList;
    List<MineOrderTakingEntity> mTakingOrderList;


    MinePlaceOrderAllAdapter mPlaceAdapter;
    MineTakingOrderAllAdapter mTakingAdapter;

    String toWho;
    int page = 1;
    int selectPosition;
    View emptyView;

    UnprovedDialog mUnprovedDialog;
    TakingRefuseDialog mTakingRefuseDialog;
    TakingTakersDialog mTakingTakersDialog;
    TakingUnprovedDialog mTakingUnprovedDialog;

    public static MineOrderAllFragment getInstance(String toWho) {
        MineOrderAllFragment newsFragment = new MineOrderAllFragment();
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

        mPresenter = new MineOrderAllPresenterImpl();
        mPresenter.onAttach(getActivity(), this);


        Bundle arguments = this.getArguments();
        toWho = arguments.getString(APPContents.BUNDLE_FRAGMENT);


        mUnprovedDialog = new UnprovedDialog(getActivity());
        mTakingRefuseDialog = new TakingRefuseDialog(getActivity());
        mTakingTakersDialog = new TakingTakersDialog(getActivity());
        mTakingUnprovedDialog = new TakingUnprovedDialog(getActivity());

        initView();
        initZrl();


        request();
        return mView;
    }


    private void initView() {
        if (toWho.equals(APPContents.TO_PLACE)) {

            mUnprovedDialog.setUnprovedListener(new UnprovedDialog.UnprovedListener() {
                @Override
                public void onUnprovedListener() {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mPlaceOrderList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mPlaceOrderList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(4);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                    request();
                }
            });


            mPlaceOrderList = new ArrayList<>();
            mPlaceAdapter = new MinePlaceOrderAllAdapter(R.layout.adapter_mine_all, mPlaceOrderList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mPlaceAdapter);

            mPlaceAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mPlaceOrderList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });


            mPlaceAdapter.setOrderUnprovedListener(new MinePlaceOrderAllAdapter.OrderUnprovedListener() {
                @Override
                public void onOrderUnprovedListener(int position) {
                    selectPosition = position;
                    mUnprovedDialog.show();
                }
            });

            mPlaceAdapter.setOrderEvaluateListener(new MinePlaceOrderAllAdapter.OrderEvaluateListener() {
                @Override
                public void onOrderEvaluateListener(int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mPlaceOrderList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });

        } else {

            mTakingOrderList = new ArrayList<>();
            mTakingAdapter = new MineTakingOrderAllAdapter(R.layout.adapter_mine_all, mTakingOrderList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mTakingAdapter);

            mTakingAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mTakingOrderList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });


            mTakingAdapter.setTakingRefuseListener(new MineTakingOrderAllAdapter.TakingRefuseListener() {
                @Override
                public void onTakingRefuseListener(int position) {
                    selectPosition = position;
                    mTakingRefuseDialog.show();
                }
            });

            mTakingAdapter.setTakingTakersListener(new MineTakingOrderAllAdapter.TakingTakersListener() {
                @Override
                public void onTakingTakersListener(int position) {
                    selectPosition = position;
                    mTakingTakersDialog.show();
                }
            });

            mTakingAdapter.setTakingOngoingListener(new MineTakingOrderAllAdapter.TakingOngoingListener() {
                @Override
                public void onTakingOngoingListener(int position) {
                    selectPosition = position;
                    mTakingUnprovedDialog.show();
                }
            });


            mTakingRefuseDialog.setTakingRefuseListener(new TakingRefuseDialog.TakingRefuseListener() {
                @Override
                public void onTakingRefuseListener(String numEt) {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mTakingOrderList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mTakingOrderList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(-1);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                    request();
                }
            });

            mTakingTakersDialog.setTakingTakersListener(new TakingTakersDialog.TakingTakersListener() {
                @Override
                public void onTakingTakersListener() {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mTakingOrderList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mTakingOrderList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(2);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                    request();
                }
            });

            mTakingUnprovedDialog.setTakingUnprovedListener(new TakingUnprovedDialog.TakingUnprovedListener() {
                @Override
                public void onTakingUnprovedListener() {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mTakingOrderList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mTakingOrderList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(3);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                    request();
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
                            mPresenter.placeOrderAllParameter(page, false);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingOrderAllParameter(page, false);
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
                            mPresenter.placeOrderAllParameter(page, true);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingOrderAllParameter(page, true);
                        }
                        mOrderOZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void placeOrderAllLoad(List<MineOrderPlaceEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mPlaceAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mPlaceAdapter.replaceData(mineOrderEntityList);
                mPlaceOrderList = mineOrderEntityList;
            } else {
                mPlaceOrderList.addAll(mineOrderEntityList);
            }
            mPlaceAdapter.setNewData(mineOrderEntityList);
        }
    }


    @Override
    public void takingOrderAllLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mTakingAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mTakingAdapter.replaceData(mineOrderEntityList);
                mTakingOrderList = mineOrderEntityList;
            } else {
                mTakingOrderList.addAll(mineOrderEntityList);
            }

            Logger.d("MineOrderAllFragment ====>>>takingOrderAll : " + mineOrderEntityList.get(0).getMember().getAvatarUrl());
            mTakingAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void updateOrderStatusLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }

    private void request() {
        if (toWho.equals(APPContents.TO_PLACE)) {
            mPresenter.placeOrderAllParameter(1, true);
        } else if (toWho.equals(APPContents.TO_TAKING)) {

            mPresenter.takingOrderAllParameter(1, true);
        }
    }
}
