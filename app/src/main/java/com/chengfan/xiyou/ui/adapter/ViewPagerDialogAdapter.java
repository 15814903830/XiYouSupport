package com.chengfan.xiyou.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.widget.viewpager.JazzyViewPager;
import com.chengfan.xiyou.widget.viewpager.OutlineContainer;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import java.util.List;

import io.rong.imageloader.core.assist.FailReason;
import io.rong.imageloader.core.listener.ImageLoadingListener;
import io.rong.photoview.PhotoView;

/**
 * Created with IntelliJ IDEA
 *
 * @AUTHER zero
 * @Email zero_ci@163.com
 * @Description
 * @DATE 2015/11/24/14:23
 */
public class ViewPagerDialogAdapter extends PagerAdapter {
    List<ImageEntity> list;
    Context context;
    JazzyViewPager mViewPager;

    public ViewPagerDialogAdapter(Context context, List<ImageEntity> list, JazzyViewPager mViewPager) {
        this.context = context;
        this.list = list;
        this.mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return list.size();
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
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view_pager_dialog, null);
        ImageView mPhotoView = view.findViewById(R.id.vp_dialog_img);
        ImageLoaderManager.getInstance().showImage(mPhotoView, list.get(position).getImgUrl());
        Log.e("getImgUrl",list.get(position).getImgUrl());
        container.addView(view);
        return view;
    }
}
