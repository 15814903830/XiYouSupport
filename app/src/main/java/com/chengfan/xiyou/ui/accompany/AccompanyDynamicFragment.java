package com.chengfan.xiyou.ui.accompany;

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
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.ui.adapter.AccompanyDynamicAdapter;
import com.zero.ci.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/14:52
 * @Description: 个人动态
 */
public class AccompanyDynamicFragment extends BaseFragment {
    View mView;
    @BindView(R.id.ap_dynamic_rv)
    RecyclerView mApDynamicRv;
    Unbinder mUnbinder;


    AccompanyDynamicAdapter mAccompanyDynamicAdapter;

    AccompanyUserInfoEntity mAccompanyUserInfoEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_accompany_dynamic, null);
        mUnbinder = ButterKnife.bind(this, mView);


        mAccompanyUserInfoEntity = new AccompanyUserInfoEntity();

        Bundle arguments = this.getArguments();
        mAccompanyUserInfoEntity = (AccompanyUserInfoEntity) arguments.getSerializable(APPContents.BUNDLE_FRAGMENT);

        if (mAccompanyUserInfoEntity != null) {
            mAccompanyDynamicAdapter = new AccompanyDynamicAdapter(R.layout.adapter_accompany_dynamic, mAccompanyUserInfoEntity.getMemberNews());
            mApDynamicRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mApDynamicRv.setAdapter(mAccompanyDynamicAdapter);
            mApDynamicRv.setFocusable(false);
        }
        return mView;
    }


    public static AccompanyDynamicFragment getInstance(AccompanyUserInfoEntity accompanyUserInfoEntity) {
        AccompanyDynamicFragment newsFragment = new AccompanyDynamicFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(APPContents.BUNDLE_FRAGMENT, accompanyUserInfoEntity);
        newsFragment.setArguments(bundle);
        return newsFragment;

    }

}
