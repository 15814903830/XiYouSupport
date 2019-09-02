package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-18/00:58
 * @Description: 我的关注
 */
public class DynamicDetailAdapter extends BaseRVAdapter<DynamicDetailEntity.AccompanyPlayBean, BaseViewHolder> {
    public DynamicDetailAdapter(int layoutResId, @Nullable List<DynamicDetailEntity.AccompanyPlayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicDetailEntity.AccompanyPlayBean item) {

        Log.e("getPrice", ""+item.getPrice());
        helper.setText(R.id.search_game_money_tv, "￥" + item.getPrice() + "/小时")
                .setText(R.id.search_game_name_tv, item.getTitle())
                .setText(R.id.search_game_type_tv, item.getSubject().getTitle());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.search_game_iv), APIContents.HOST + "/" + item.getImages());
    }
}
