package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.XiYouBean;
import com.chengfan.xiyou.domain.model.entity.XiYouBean2;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;

import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/15:51
 * @Description: 选择
 */
public class XiYouSelectAdapter2 extends BaseRVAdapter<XiYouBean2, BaseViewHolder> {


    public XiYouSelectAdapter2(int layoutResId, @Nullable List<XiYouBean2> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XiYouBean2 item) {
        helper.setText(R.id.game_name_tv, item.getTitle());
        Log.e("HOSTHOST",APIContents.HOST+"/"+item.getShowDrawable());
        Glide.with(mContext).load(APIContents.HOST+"/"+item.getShowDrawable()).into((ImageView) helper.getView(R.id.game_icon_iv))  ;  //.setBackgroundRes(R.id.game_icon_iv, item.getShowDrawable())
    }

}
