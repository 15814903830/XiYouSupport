package com.chengfan.xiyou.ui.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.DynamicMineContract;
import com.chengfan.xiyou.domain.model.entity.DynamicMineDelBean;
import com.chengfan.xiyou.domain.model.entity.DynamicMineEntity;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.chengfan.xiyou.domain.presenter.DynamicMinePresenterImpl;
import com.chengfan.xiyou.ui.WebActivity;
import com.chengfan.xiyou.ui.adapter.DynamicMineAdapter;
import com.chengfan.xiyou.ui.dialog.DynamicMineDelDialog;
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
import com.zero.ci.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/18:54
 * @Description: 我的动态
 */
public class DynamicMineFragment extends BaseFragment<DynamicMineContract.View, DynamicMinePresenterImpl> implements DynamicMineContract.View {
    View mView;
    @BindView(R.id.dynamic_mine_rv)
    RecyclerView mDynamicMineRv;
    @BindView(R.id.dynamic_mine_zrl)
    ZRefreshLayout mDynamicMineZrl;
    Unbinder mUnbinder;

    List<DynamicMineEntity> mDynamicMineEntityList;
    DynamicMineAdapter mDynamicMineAdapter;

    DynamicMineDelDialog mDynamicMineDelDialog;

    int page = 1;
    int delId;

    int delPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_mine, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mPresenter = new DynamicMinePresenterImpl();
        mPresenter.onAttach(getActivity(), this);
        mDynamicMineEntityList = new ArrayList<>();
        mDynamicMineDelDialog = new DynamicMineDelDialog(getActivity());
        iniRv();
        initZrl();
        mPresenter.dynamicMineParameter(page, true);
        return mView;
    }

    private void iniRv() {

        mDynamicMineAdapter = new DynamicMineAdapter(R.layout.adapter_dynamic_mine, mDynamicMineEntityList);
        mDynamicMineRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDynamicMineRv.setAdapter(mDynamicMineAdapter);
        mDynamicMineAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                turnToDetail(position);
            }
        });

        mDynamicMineAdapter.setDelOnClickListener(new DynamicMineAdapter.DelOnClickListener() {
            @Override
            public void onDelOnClickListener(int position) {
                mDynamicMineDelDialog.show();
                delId = mDynamicMineEntityList.get(position).getId();
                delPosition = position;
            }
        });

        mDynamicMineAdapter.setLikeListener(new DynamicMineAdapter.LikeListener() {
            @Override
            public void onLikeListener(final int position) {
                Toast.makeText(getContext(), "点赞", Toast.LENGTH_SHORT).show();
                MemberShipBean shipBean = new MemberShipBean();
                shipBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
                shipBean.setMemberId(mDynamicMineEntityList.get(position).getMemberId());
                HttpRequest.post("/api/MemberNews/Praise")
                        .paramsJsonString(new Gson().toJson(shipBean))
                        .execute(new AbstractResponse<BaseApiResponse>() {
                            @Override
                            public void onSuccess(BaseApiResponse result) {
                                if (result.isSuc()) {
                                    if (mDynamicMineEntityList.get(position).isHavePraise()) {
                                        Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();

                                        mDynamicMineEntityList.get(position).setHavePraise(false);
                                        mDynamicMineEntityList.get(position).setTotalPraise(mDynamicMineEntityList.get(position).getTotalPraise() - 1);
                                    } else {
                                        Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
                                        mDynamicMineEntityList.get(position).setHavePraise(true);
                                        mDynamicMineEntityList.get(position).setTotalPraise(mDynamicMineEntityList.get(position).getTotalPraise() + 1);
                                    }
                                    mDynamicMineAdapter.setNewData(mDynamicMineEntityList);
                                    mDynamicMineAdapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
        mDynamicMineDelDialog.setDynamicMineDelListener(new DynamicMineDelDialog.DynamicMineDelListener() {
            @Override
            public void onDynamicMineDelListener() {
                DynamicMineDelBean dynamicMineDelBean = new DynamicMineDelBean();
                dynamicMineDelBean.setId(delId);
                dynamicMineDelBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                mPresenter.dynamicMineDelParameter(dynamicMineDelBean);
            }
        });
    }

    private void turnToDetail(int position) {
        if (mDynamicMineEntityList.get(position).getImages().contains(".mp4")) {
            int id = mDynamicMineEntityList.get(position).getId();
            String userId = AppData.getString(AppData.Keys.AD_USER_ID);
            String url = APIContents.HOST + "/WapNews/MemberNewsVoidDetail?" + "id=" + id + "&memberId=" + userId;
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.KEY_URL, url);
            startActivity(intent);
        } else {
            Bundle toBundle = new Bundle();
            toBundle.putString(APPContents.BUNDLE_DYNAMIC_ID, mDynamicMineEntityList.get(position).getId() + "");
            ForwardUtil.getInstance(getActivity()).forward(DynamicDetailActivity.class, toBundle);
        }
    }

    private void initZrl() {
        mDynamicMineZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mDynamicMineZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.dynamicMineParameter(page, false);
                        mDynamicMineZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mDynamicMineZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.dynamicMineParameter(page, true);
                        mDynamicMineZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void dynamicMineLoad(List<DynamicMineEntity> dynamicMineEntityList, boolean isPtr) {
        if (isPtr) {
            mDynamicMineAdapter.replaceData(mDynamicMineEntityList);
            mDynamicMineEntityList = dynamicMineEntityList;
        } else {
            mDynamicMineEntityList.addAll(dynamicMineEntityList);
        }
        mDynamicMineAdapter.setNewData(mDynamicMineEntityList);
    }

    @Override
    public void dynamicMineDelLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
        mDynamicMineAdapter.remove(delPosition);
    }


}
