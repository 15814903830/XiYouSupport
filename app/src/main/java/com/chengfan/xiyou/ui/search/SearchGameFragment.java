package com.chengfan.xiyou.ui.search;

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
import com.chengfan.xiyou.domain.model.entity.SearchGameEntity;
import com.chengfan.xiyou.ui.accompany.AccompanyDetailActivity;
import com.chengfan.xiyou.ui.adapter.SearchGameAdapter;
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

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/17:50
 * @Description: 陪玩项目
 */
public class SearchGameFragment extends BaseFragment {
    View mView;
    @BindView(R.id.search_game_rv)
    RecyclerView mSearchGameRv;
    @BindView(R.id.search_game_zrl)
    ZRefreshLayout mSearchGameZrl;
    Unbinder mUnbinder;

    SearchGameAdapter mSearchGameAdapter;
    List<SearchGameEntity> mSearchGameEntityList;
    String searchStr = "";
    int page = 1;

    public static SearchGameFragment getInstance(String search) {
        SearchGameFragment newsFragment = new SearchGameFragment();
        Bundle bundle = new Bundle();
        bundle.putString(APPContents.BUNDLE_SEARCH, search);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            searchStr = arguments.getString(APPContents.BUNDLE_SEARCH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_game, null);
        mUnbinder = ButterKnife.bind(this, mView);

        initAdapter();
        initZrl();

        return mView;
    }

    public void search(String searchKey) {
        searchStr = searchKey;
        if (mSearchGameAdapter == null) {
            initAdapter();
        }
        mSearchGameAdapter.setSearchStr(searchStr);
        page = 1;
        requestGame(searchKey, 1, true);
    }

    private void initAdapter() {
        mSearchGameAdapter = new SearchGameAdapter(R.layout.adapter_search_game, mSearchGameEntityList);
        mSearchGameAdapter.setSearchStr(searchStr);
        mSearchGameRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchGameRv.setAdapter(mSearchGameAdapter);

        mSearchGameAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mSearchGameEntityList.get(position).getMemberId());
                ForwardUtil.getInstance(getActivity()).forward(AccompanyDetailActivity.class);
            }
        });
    }


    private void initZrl() {
        mSearchGameZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mSearchGameZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        requestGame(searchStr, page, false);
                        mSearchGameZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mSearchGameZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        requestGame(searchStr, 1, true);
                        mSearchGameZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    private void requestGame(String search, int page, final boolean isPtr) {
        HttpRequest.get(APIContents.Search)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params("q", search)
                .params(APPContents.E_PAGE, page)
                .params(APPContents.E_LIMIT, 20)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("[]")) {
                        } else {
                            Type type = new TypeToken<List<SearchGameEntity>>() {
                            }.getType();
                            Log.e("result", result);
                            List<SearchGameEntity> searchGameEntityList = new Gson().fromJson(result, type);
                            if (searchGameEntityList.isEmpty()) {
                                return;
                            }
                            if (isPtr) {
                                mSearchGameEntityList = searchGameEntityList;
                                mSearchGameAdapter.replaceData(mSearchGameEntityList);
                            } else {
                                mSearchGameEntityList.addAll(searchGameEntityList);
                                mSearchGameAdapter.addData(mSearchGameEntityList);
                            }
                        }
                    }
                });
    }
}
