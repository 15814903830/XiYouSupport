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
import com.chengfan.xiyou.domain.contract.MineNotStartedContract;
import com.chengfan.xiyou.domain.model.entity.MineOrderPlaceEntity;
import com.chengfan.xiyou.domain.model.entity.MineOrderTakingEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateOrderStatusBean;
import com.chengfan.xiyou.domain.presenter.MineNotStartedPresenterImpl;
import com.chengfan.xiyou.ui.adapter.MinePlaceNoStartAdapter;
import com.chengfan.xiyou.ui.adapter.MineTakingNoStartAdapter;
import com.chengfan.xiyou.ui.adapter.MineTakingOrderAllAdapter;
import com.chengfan.xiyou.ui.dialog.TakingRefuseDialog;
import com.chengfan.xiyou.ui.dialog.TakingTakersDialog;
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
 * @DATE : 2019-07-06/22:21
 * @Description: 待开始
 */
public class MineNotStartedFragment extends BaseFragment<MineNotStartedContract.View, MineNotStartedPresenterImpl> implements MineNotStartedContract.View {
    View mView;
    @BindView(R.id.order_o_rv)
    RecyclerView mOrderORv;
    @BindView(R.id.order_o_zrl)
    ZRefreshLayout mOrderOZrl;
    Unbinder mUnbinder;

    List<MineOrderTakingEntity> mTakingEntityList;
    List<MineOrderPlaceEntity> mPlaceEntityList;


    MinePlaceNoStartAdapter mPlaceNoStartAdapter;
    MineTakingNoStartAdapter mTakingNoStartAdapter;

    String toWho;
    int page = 1;

    View emptyView;
    int selectPosition;

    TakingRefuseDialog mTakingRefuseDialog;
    TakingTakersDialog mTakingTakersDialog;

    public static MineNotStartedFragment getInstance(String toWho) {
        MineNotStartedFragment newsFragment = new MineNotStartedFragment();
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

        mPresenter = new MineNotStartedPresenterImpl();
        mPresenter.onAttach(getActivity(), this);


        Bundle arguments = this.getArguments();
        toWho = arguments.getString(APPContents.BUNDLE_FRAGMENT);

        if (toWho.equals(APPContents.TO_PLACE)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, true);
            mPresenter.placeNoStartedParameter(1, true);
        } else if (toWho.equals(APPContents.TO_TAKING)) {
            AppData.putBoolen(AppData.Keys.AD_IS_PLACE_ORDER, false);
            mPresenter.takingNoStartedParameter(1, true);
        }


        mTakingRefuseDialog = new TakingRefuseDialog(getActivity());
        mTakingTakersDialog = new TakingTakersDialog(getActivity());

        initView();
        initZrl();
        return mView;
    }

    private void initView() {
        if (toWho.equals(APPContents.TO_PLACE)) {
            mPlaceEntityList = new ArrayList<>();
            mPlaceNoStartAdapter = new MinePlaceNoStartAdapter(R.layout.adapter_mine_no_start, mPlaceEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mPlaceNoStartAdapter);

            mPlaceNoStartAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mPlaceEntityList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });
        } else {

            mTakingEntityList = new ArrayList<>();
            mTakingNoStartAdapter = new MineTakingNoStartAdapter(R.layout.adapter_mine_no_start, mTakingEntityList);
            mOrderORv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mOrderORv.setAdapter(mTakingNoStartAdapter);
            mTakingNoStartAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    Bundle toBundle = new Bundle();
                    toBundle.putInt(APPContents.E_ID, mTakingEntityList.get(position).getAccompanyPlayId());
                    ForwardUtil.getInstance(getActivity()).forward(MineOrderDetailActivity.class, toBundle);
                }
            });


            mTakingNoStartAdapter.setTakingRefuseListener(new MineTakingNoStartAdapter.TakingRefuseListener() {
                @Override
                public void onTakingRefuseListener(int position) {
                    selectPosition = position;
                    mTakingRefuseDialog.show();
                }
            });

            mTakingNoStartAdapter.setTakingTakersListener(new MineTakingNoStartAdapter.TakingTakersListener() {
                @Override
                public void onTakingTakersListener(int position) {
                    selectPosition = position;
                    mTakingTakersDialog.show();
                }
            });

            mTakingRefuseDialog.setTakingRefuseListener(new TakingRefuseDialog.TakingRefuseListener() {
                @Override
                public void onTakingRefuseListener(String numEt) {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mTakingEntityList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mTakingEntityList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(-1);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
                }
            });

            mTakingTakersDialog.setTakingTakersListener(new TakingTakersDialog.TakingTakersListener() {
                @Override
                public void onTakingTakersListener() {
                    UpdateOrderStatusBean bean = new UpdateOrderStatusBean();
                    bean.setUniformOrderId(mTakingEntityList.get(selectPosition).getUniformOrderId());
                    bean.setAccompanyPlayId(mTakingEntityList.get(selectPosition).getAccompanyPlayId());
                    bean.setMemberId(Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID)));
                    bean.setStatus(2);
                    Logger.d("MineOrderAllFragment ==>>  " + new Gson().toJson(bean));
                    mPresenter.updateOrderStatusParameter(bean);
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
                            mPresenter.placeNoStartedParameter(page, false);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingNoStartedParameter(page, false);
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
                            mPresenter.placeNoStartedParameter(page, true);
                        } else if (toWho.equals(APPContents.TO_TAKING)) {
                            mPresenter.takingNoStartedParameter(page, true);
                        }
                        mOrderOZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void placeNoStartedLoad(List<MineOrderPlaceEntity> mineOrderPlaceEntityList, boolean isPtr) {
        if (mineOrderPlaceEntityList.size() < 1) {
            mPlaceNoStartAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mPlaceEntityList = mineOrderPlaceEntityList;
            } else {
                mPlaceEntityList.addAll(mineOrderPlaceEntityList);
            }

            mPlaceNoStartAdapter.setNewData(mineOrderPlaceEntityList);
        }
    }

    @Override
    public void takingNoStartedLoad(List<MineOrderTakingEntity> mineOrderEntityList, boolean isPtr) {
        if (mineOrderEntityList.size() < 1) {
            mTakingNoStartAdapter.setEmptyView(emptyView);
        } else {
            if (isPtr) {
                mTakingEntityList = mineOrderEntityList;
            } else {
                mTakingEntityList.addAll(mineOrderEntityList);
            }

            mTakingNoStartAdapter.setNewData(mineOrderEntityList);
        }
    }

    @Override
    public void updateOrderStatusLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }
}
