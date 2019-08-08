package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.MemberIdEntity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-07/16:13
 * @Description: 我的陪玩
 */
public class MineGameAdapter extends BaseRVAdapter<MemberIdEntity, BaseViewHolder> {
    public MineGameAdapter(int layoutResId, @Nullable List<MemberIdEntity> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MemberIdEntity item) {
        helper.setText(R.id.game_name_tv, item.getTitle());
        helper.setText(R.id.game_type_tv, item.getSubjectTitle());
        helper.setText(R.id.game_money_tv, "￥" + item.getPrice() + "/小时");
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.game_iv), APIContents.HOST + "/" + item.getSubjectImages());

    }


}
