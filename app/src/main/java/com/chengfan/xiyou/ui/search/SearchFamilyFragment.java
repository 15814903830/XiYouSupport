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
import com.chengfan.xiyou.domain.model.entity.SearchFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.SearchGameEntity;
import com.chengfan.xiyou.ui.adapter.SearchFamilyAdapter;
import com.chengfan.xiyou.ui.chat.ChatFamilyDetailActivity;
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
 * @DATE : 2019-07-04/17:51
 * @Description: 家族专区
 */
public class SearchFamilyFragment extends BaseFragment {
    View mView;
    @BindView(R.id.search_family_rv)
    RecyclerView mSearchFamilyRv;
    @BindView(R.id.search_family_zrl)
    ZRefreshLayout mSearchFamilyZrl;
    Unbinder mUnbinder;

    SearchFamilyAdapter mSearchFamilyAdapter;
    List<SearchFamilyEntity> mSearchFamilyEntityList;

    String searchStr;
    int page = 1;

    public static SearchFamilyFragment getInstance(String search) {
        SearchFamilyFragment newsFragment = new SearchFamilyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(APPContents.BUNDLE_SEARCH, search);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_family, null);
        mUnbinder = ButterKnife.bind(this, mView);

        mSearchFamilyEntityList = new ArrayList<>();


        Bundle arguments = this.getArguments();
        searchStr = arguments.getString(APPContents.BUNDLE_SEARCH);
        Logger.d("SearchFamilyFragment ===>>> searchStr :" + searchStr);

        initAdapter();
        initZrl();
        //  requestFamily(searchStr, 1, true);
        return mView;
    }


    private void initAdapter() {
        mSearchFamilyAdapter = new SearchFamilyAdapter(R.layout.adapter_search_family, mSearchFamilyEntityList);
        mSearchFamilyAdapter.setSearchTxt(searchStr);
        mSearchFamilyRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchFamilyRv.setAdapter(mSearchFamilyAdapter);
        mSearchFamilyAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putString(APPContents.BUNDLE_FAMILY, mSearchFamilyEntityList.get(position).getNum());
                ForwardUtil.getInstance(getActivity()).forward(ChatFamilyDetailActivity.class, toBundle);
            }
        });
    }


    private void initZrl() {
        mSearchFamilyZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mSearchFamilyZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        requestFamily(searchStr, page, false);
                        mSearchFamilyZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mSearchFamilyZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestFamily(searchStr, 1, true);
                        mSearchFamilyZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    private void requestFamily(String search, int page, final boolean isPtr) {
        HttpRequest.get(APIContents.SearchFamily)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params("q", search)
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("[]")) {
                        } else {
                            Type type = new TypeToken<List<SearchFamilyEntity>>() {
                            }.getType();
                            List<SearchFamilyEntity> searchFamilyEntityList = new Gson().fromJson(result, type);
                            if (searchFamilyEntityList.equals("")) {
                                return;
                            } else {
                                if (isPtr) {
                                    mSearchFamilyEntityList = searchFamilyEntityList;
                                } else {
                                    mSearchFamilyEntityList.addAll(searchFamilyEntityList);
                                }
                                mSearchFamilyAdapter.addData(mSearchFamilyEntityList);
                            }
                        }
                    }
                });
    }
}
