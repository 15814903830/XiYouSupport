package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.XiYouBean;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/15:51
 * @Description: 选择
 */
public class XiYouSelectAdapter extends BaseRVAdapter<XiYouBean, BaseViewHolder> {


    public XiYouSelectAdapter(int layoutResId, @Nullable List<XiYouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XiYouBean item) {
        helper.setText(R.id.game_name_tv, item.getTitle())
                .setBackgroundRes(R.id.game_icon_iv, item.getShowDrawable());
    }
}
