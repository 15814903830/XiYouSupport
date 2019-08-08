package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.SearchGameEntity;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.view.SignKeyWordTextView;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/19:44
 * @Description: 陪玩项目
 */
public class SearchGameAdapter extends BaseRVAdapter<SearchGameEntity, BaseViewHolder> {
    private String searchStr;

    public SearchGameAdapter(int layoutResId, @Nullable List<SearchGameEntity> data) {
        super(layoutResId, data);
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchGameEntity item) {
        SignKeyWordTextView signKeyWordTextView = helper.getView(R.id.search_game_name_tv);
        signKeyWordTextView.setSignText(searchStr);
        helper.setText(R.id.search_game_money_tv, "￥" + item.getPrice() + "小时")
                .setText(R.id.search_game_name_tv, item.getTitle());
        //.setText(R.id.search_game_type_tv, item.get());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.search_game_iv), APIContents.HOST + "/" + item.getImages());

        FontHelper.applyFont(mContext, signKeyWordTextView, APPContents.FONTS_MEDIUM);
        FontHelper.applyFont(mContext, helper.getView(R.id.search_game_money_tv), APPContents.FONTS_MEDIUM);
        FontHelper.applyFont(mContext, helper.getView(R.id.search_game_type_tv), APPContents.FONTS_REGULAR);

    }
}
