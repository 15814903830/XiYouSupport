package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.ApplyLableListBean;
import com.chengfan.xiyou.domain.model.entity.SearchUserEntity;
import com.chengfan.xiyou.ui.main.LableAdapter;
import com.chengfan.xiyou.ui.main.LableAdapter2;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.view.SignKeyWordTextView;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/18:22
 * @Description: 用户
 */
public class SearchUserAdapter extends BaseRVAdapter<SearchUserEntity, BaseViewHolder> {

    private String searchStr;

    public SearchUserAdapter(int layoutResId, @Nullable List<SearchUserEntity> data) {
        super(layoutResId, data);

    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchUserEntity item) {
        SignKeyWordTextView signKeyWordTextView = helper.getView(R.id.search_user_name_tv);
        signKeyWordTextView.setSignText(searchStr);

        LableAdapter2 lableAdapter2;
        RecyclerView recyclerView=helper.getView(R.id.rv_lable);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(layoutManager);

        if (item.getApplyLableList()!=null){
        List<ApplyLableListBean> lableListBeans=new ArrayList<>();
        if (item.getApplyLableList()!=null){
        for (int i=0;i<item.getApplyLableList().size();i++){
       ApplyLableListBean applyLableListBean=new ApplyLableListBean();
        applyLableListBean.setEnabled(item.getApplyLableList().get(i).isEnabled());
        applyLableListBean.setIcon(item.getApplyLableList().get(i).getIcon());
        applyLableListBean.setId(item.getApplyLableList().get(i).getId());
        applyLableListBean.setNeedApproval(item.getApplyLableList().get(i).isNeedApproval());
        applyLableListBean.setText(item.getApplyLableList().get(i).getText());
            lableListBeans.add(applyLableListBean);
        }
        }
        if (item.getApprovalLableList()!=null){
            for (int i=0;i<item.getApprovalLableList().size();i++){
                ApplyLableListBean applyLableListBean=new ApplyLableListBean();
                applyLableListBean.setEnabled(item.getApprovalLableList().get(i).isEnabled());
                applyLableListBean.setIcon(item.getApprovalLableList().get(i).getIcon());
                applyLableListBean.setId(item.getApprovalLableList().get(i).getId());
                applyLableListBean.setNeedApproval(item.getApprovalLableList().get(i).isNeedApproval());
                applyLableListBean.setText(item.getApprovalLableList().get(i).getText());
                lableListBeans.add(applyLableListBean);
            }
        }
        Log.e("lableListBeans",lableListBeans.get(0).getIcon());
        lableAdapter2 = new LableAdapter2(mContext, lableListBeans);
        recyclerView.setAdapter(lableAdapter2);
        lableAdapter2.notifyDataSetChanged();
        }
        if (item.getNickname()!=null)
        helper.setText(R.id.search_user_name_tv, item.getNickname())
                .setText(R.id.search_user_id_num_tv, item.getId() + "")
                .setText(R.id.search_user_num_tv, item.getTotalFans() + "");
        if (item.getGender() == 1) {
            helper.getView(R.id.search_user_sex_iv).setBackgroundResource(R.drawable.home_nan);
        } else {
            helper.getView(R.id.search_user_sex_iv).setBackgroundResource(R.drawable.home_nv);
        }
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.search_user_civ), APIContents.HOST + "/" + item.getAvatarUrl());

        FontHelper.applyFont(mContext, signKeyWordTextView, APPContents.FONTS_MEDIUM);
        FontHelper.applyFont(mContext, helper.getView(R.id.user_z_id_tv), APPContents.FONTS_REGULAR);
        FontHelper.applyFont(mContext, helper.getView(R.id.search_user_id_num_tv), APPContents.FONTS_REGULAR);
        FontHelper.applyFont(mContext, helper.getView(R.id.user_z_num_tv), APPContents.FONTS_REGULAR);
        FontHelper.applyFont(mContext, helper.getView(R.id.search_user_num_tv), APPContents.FONTS_REGULAR);

    }
}
