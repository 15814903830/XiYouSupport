package com.chengfan.xiyou.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.SearchUserEntity;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.SearchUserAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/17:50
 * @Description: 用户
 */
public class SearchUserFragment extends BaseFragment {
    View mView;
    @BindView(R.id.search_user_rv)
    RecyclerView mSearchUserRv;
    @BindView(R.id.search_user_zrl)
    ZRefreshLayout mSearchUserZrl;
    Unbinder mUnbinder;

    SearchUserAdapter mSearchUserAdapter;
    List<SearchUserEntity> mSearchUserEntityList;

    String searchStr;
    int page = 1;

    public static SearchUserFragment getInstance(String search) {
        SearchUserFragment newsFragment = new SearchUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(APPContents.BUNDLE_SEARCH, search);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_user, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mSearchUserEntityList = new ArrayList<>();
        mSearchUserZrl.setEnableRefresh(false);

        Bundle arguments = this.getArguments();
        searchStr = arguments.getString(APPContents.BUNDLE_SEARCH);
        Logger.d("SearchUserFragment ===>>> searchStr :" + searchStr);

        initAdapter();
        initZrl();
        requestUser(searchStr, 1, true);
        return mView;
    }


    private void initAdapter() {
        mSearchUserAdapter = new SearchUserAdapter(R.layout.adapter_search_user, mSearchUserEntityList);
        mSearchUserAdapter.setSearchStr(searchStr);
        mSearchUserRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchUserRv.setAdapter(mSearchUserAdapter);
        mSearchUserAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mSearchUserEntityList.get(position).getId());
                ForwardUtil.getInstance(getActivity()).forward(AccompanyUserInfoActivity.class, toBundle);
            }
        });



    }


    private void initZrl() {
        mSearchUserZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mSearchUserZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        requestUser(searchStr, page, false);
                        mSearchUserZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mSearchUserZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestUser(searchStr, 1, true);
                        mSearchUserZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    private void requestUser(String search, int page, final boolean isPtr) {
        HttpRequest.get(APIContents.SearchMember)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params("q", search)
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("[]")) {
                        } else {
                            Type type = new TypeToken<List<SearchUserEntity>>() {
                            }.getType();
                            List<SearchUserEntity> searchUserEntityList = new Gson().fromJson(result, type);
                            if (searchUserEntityList.equals("")) {
                                return;
                            } else {
                                if (isPtr) {
                                    mSearchUserEntityList = searchUserEntityList;
                                } else {
                                    mSearchUserEntityList.addAll(searchUserEntityList);
                                }
                                mSearchUserAdapter.addData(mSearchUserEntityList);
                            }
                        }
                    }
                });
    }
}
