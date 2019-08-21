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
import com.chengfan.xiyou.domain.contract.MineUnprovedContract;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.chengfan.xiyou.domain.presenter.MineUnprovedPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MinePlaceUnprovedAdapter;
import com.chengfan.xiyou.ui.adapter.MineTakingUnprovedAdapter;
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
 * @DATE : 2019-07-06/22:24
 * @Description: 待确认
 */
public class MineUnprovedFragment extends BaseFragment<MineUnprovedContract.View, MineUnprovedPresenterImpl> implements MineUnprovedContract.View {
    View mView;
    @BindView(R.id.order_o_rv)
    RecyclerView mOrderORv;
    @BindView(R.id.order_o_zrl)
    ZRefreshLayout mOrderOZrl;
    Unbinder mUnbinder;

    MineTakingUnprovedAdapter mTakingUnprovedAdapter;
    MinePlaceUnprovedAdapter mMinePlaceUnprovedAdapter;

    List<MineOrderTakingEntity> mTakingEntityList;
    List<MineOrderPlaceEntity> mPlaceEntityList;

    String toWho;
    int page = 1;

    View emptyView;
    int selectPosition;
    UnprovedDialog mUnprovedDialog;


    public static MineUnprovedFragment getInstance(String toWho) {
        MineUnprovedFragment newsFragment = new MineUnprovedFragment();
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

        mPresenter = new MineUnprovedPresenterImpl();
        mPresenter.onAttach(getActivity(), this);


        Bundle arguments = this.getArguments();
        toWho = arguments.getString(APPContents.BUNDLE_FRAGMENT);

        if (toWho.equals(APPContents.TO_PLACE)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, true);
            mPresenter.placeUnprovedParameter(1, true);
        } else if (toWho.equals(APPContents.TO_TAKING)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, false);
            mPresenter.takingUnprovedParameter(1, true);
        }


        mUnprovedDialog = new UnprovedDialog(getActivity());

        initView();
        initZrl();

        return mView;
    }

    private void initView() {

        if (toWho.equals(APPContents.TO_PLACE)) {
            mPlaceEntityList = new ArrayList<>();
            mUnprovedDialog.setUnprovedListener(new UnprovedDialog.UnprovedListener() {
                @Override
                public void onUnprovedListener() {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mPlaceEntityList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mPlaceEntityList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(4);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                }
            });

            mMinePlaceUnprovedAdapter = new MinePlaceUnprovedAdapter(R.layout.adapter_mine_unproved, mPlaceEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mMinePlaceUnprovedAdapter);
            mMinePlaceUnprovedAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mPlaceEntityList.get(position).getUniformOrderId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });

            mMinePlaceUnprovedAdapter.setOrderUnprovedListener(new MinePlaceUnprovedAdapter.OrderUnprovedListener() {
                @Override
                public void onOrderUnprovedListener(int position) {
                    selectPosition = position;
                    mUnprovedDialog.show();
                }
            });
        } else {
            mTakingEntityList = new ArrayList<>();
            mTakingUnprovedAdapter = new MineTakingUnprovedAdapter(R.layout.adapter_mine_unproved, mTakingEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mTakingUnprovedAdapter);
            mTakingUnprovedAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mTakingEntityList.get(position).getUniformOrderId());
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
                            mPresenter.placeUnprovedParameter(page, false);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingUnprovedParameter(page, false);
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
                            mPresenter.placeUnprovedParameter(page, true);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingUnprovedParameter(page, true);
                        }
                        mOrderOZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void placeUnprovedLoad(List<MineOrderPlaceEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mMinePlaceUnprovedAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mPlaceEntityList = mineOrderEntityList;
            } else {
                mPlaceEntityList.addAll(mineOrderEntityList);
            }

            mMinePlaceUnprovedAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void takingUnprovedLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mTakingUnprovedAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mTakingEntityList = mineOrderEntityList;
            } else {
                mTakingEntityList.addAll(mineOrderEntityList);
            }
            mTakingUnprovedAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void updateOrderStatusLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }
}
