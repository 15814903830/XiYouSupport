package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.SearchFamilyEntity;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.view.SignKeyWordTextView;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/21:11
 * @Description:
 */
public class SearchFamilyAdapter extends BaseRVAdapter<SearchFamilyEntity, BaseViewHolder> {

    private String searchTxt;

    public SearchFamilyAdapter(int layoutResId, @Nullable List<SearchFamilyEntity> data) {
        super(layoutResId, data);
    }

    public void setSearchTxt(String searchTxt) {
        this.searchTxt = searchTxt;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchFamilyEntity item) {
        SignKeyWordTextView setsig = helper.getView(R.id.search_family_tv);
        setsig.setSignText(searchTxt);

        if (item.getFamilyName()!=null)
        helper.setText(R.id.search_family_tv, item.getFamilyName())
                .setText(R.id.search_family_user_name_tv, item.getUserName())
                .setText(R.id.search_family_num_tv, item.getNum() + "成员");
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.search_family_iv), APIContents.HOST+"/"+item.getUrl());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.search_family_user_civ), APIContents.HOST+"/"+item.getPic());
        FontHelper.applyFont(mContext, helper.getView(R.id.search_family_tv), APPContents.FONTS_BOLD);
        FontHelper.applyFont(mContext, helper.getView(R.id.search_family_user_name_tv), APPContents.FONTS_REGULAR);
        FontHelper.applyFont(mContext, helper.getView(R.id.search_family_num_tv), APPContents.FONTS_REGULAR);
    }
}
