package com.chengfan.xiyou.ui.accompany;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.view.RegularTextView;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.widget.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/14:52
 * @Description: 资料信息
 */
public class AccompanyDataInfoFragment extends BaseFragment {
    View mView;
    @BindView(R.id.data_info_name_tv)
    RegularTextView mDataInfoNameTv;
    @BindView(R.id.data_info_id_tv)
    RegularTextView mDataInfoIdTv;
    @BindView(R.id.data_info_sex_tv)
    RegularTextView mDataInfoSexTv;
    @BindView(R.id.data_info_age_tv)
    RegularTextView mDataInfoAgeTv;
    @BindView(R.id.data_info_city_tv)
    RegularTextView mDataInfoCityTv;
    @BindView(R.id.data_info_face_tv)
    RegularTextView mDataInfoFaceTv;
    @BindView(R.id.data_info_wx_tv)
    RegularTextView mDataInfoWxTv;
    @BindView(R.id.data_info_job_tv)
    RegularTextView mDataInfoJobTv;
    Unbinder mUnbinder;

    AccompanyUserInfoEntity mAccompanyUserInfoEntity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_accompany_data_info, null);
        mUnbinder = ButterKnife.bind(this, mView);

        mAccompanyUserInfoEntity = new AccompanyUserInfoEntity();

        Bundle arguments = this.getArguments();
        mAccompanyUserInfoEntity = (AccompanyUserInfoEntity) arguments.getSerializable(APPContents.BUNDLE_FRAGMENT);

        Logger.d("AccompanyDataInfoFragment ==>>  " + mAccompanyUserInfoEntity.getNickname());
        if (mAccompanyUserInfoEntity != null) {

            mDataInfoNameTv.setText(mAccompanyUserInfoEntity.getNickname());
            mDataInfoIdTv.setText(mAccompanyUserInfoEntity.getId() + "");
            if (mAccompanyUserInfoEntity.getGender() == 1)
                mDataInfoSexTv.setText("男");
            else
                mDataInfoSexTv.setText("女");
            mDataInfoAgeTv.setText(mAccompanyUserInfoEntity.getAge() + "");
            mDataInfoCityTv.setText(mAccompanyUserInfoEntity.getAreaName());
            mDataInfoFaceTv.setText(mAccompanyUserInfoEntity.getExterior());
            mDataInfoJobTv.setText(mAccompanyUserInfoEntity.getJob());
            mDataInfoWxTv.setText(mAccompanyUserInfoEntity.getWeiXin());
        }
        return mView;
    }


    public static AccompanyDataInfoFragment getInstance(AccompanyUserInfoEntity accompanyUserInfoEntity) {
        AccompanyDataInfoFragment newsFragment = new AccompanyDataInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(APPContents.BUNDLE_FRAGMENT, accompanyUserInfoEntity);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

}
