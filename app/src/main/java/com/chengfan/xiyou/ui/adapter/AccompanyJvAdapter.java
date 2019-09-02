package com.chengfan.xiyou.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.XiYouBean;
import com.chengfan.xiyou.ui.accompany.AccompanyGameActivity;
import com.chengfan.xiyou.widget.viewpager.JazzyViewPager;
import com.chengfan.xiyou.widget.viewpager.OutlineContainer;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-26/11:28
 * @Description:
 */
public class AccompanyJvAdapter extends PagerAdapter {
    Context context;
    JazzyViewPager mViewPager;

    public AccompanyJvAdapter(Context context,JazzyViewPager m) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        if (view instanceof OutlineContainer) {
            return ((OutlineContainer) view).getChildAt(0) == obj;
        } else {
            return view == obj;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mViewPager.findViewFromObject(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_accompany_vp, null);
        RecyclerView mAccompanyGameRv = view.findViewById(R.id.acc_jv_rv);
        final List<XiYouBean> mXiYouBeanList = new ArrayList<>();
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_shouwang, "守望先锋", "1"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_heping, "和平精英", "2"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_wangzhe, "王者荣耀", "3"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_pubg, "绝地求生", "4"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_lol, "LOL", "5"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_music, "音乐", "6"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_apex, "堡垒之夜", "7"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_peiliao, "陪聊倾诉", "8"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_hongshui, "哄睡FM", "9"));
        mXiYouBeanList.add(new XiYouBean(R.drawable.accompany_more, "更多", "0"));
        XiYouSelectAdapter xiYouSelectAdapter = new XiYouSelectAdapter(R.layout.adapter_accompany_select_game, mXiYouBeanList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        mAccompanyGameRv.setLayoutManager(gridLayoutManager);
        mAccompanyGameRv.setAdapter(xiYouSelectAdapter);

        xiYouSelectAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Logger.e("AccompanyFragment  subject Id  " + mXiYouBeanList.get(position).getSubjectId());
                Bundle toBundle = new Bundle();
                toBundle.putString(APPContents.E_SUBJECT_ID, mXiYouBeanList.get(position).getSubjectId());
                toBundle.putString("TITLE", mXiYouBeanList.get(position).getTitle());
                ForwardUtil.getInstance(context).forward(AccompanyGameActivity.class, toBundle);
            }
        });
        container.addView(view);
        return view;
    }
}
