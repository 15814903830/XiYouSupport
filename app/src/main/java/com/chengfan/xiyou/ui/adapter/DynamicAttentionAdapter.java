package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.FinanceRecordEntity;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.ui.dialog.ViewPagerDialog;
import com.chengfan.xiyou.utils.GlideImageLoader;
import com.chengfan.xiyou.widget.ninegridview.NineGirdImageContainer;
import com.chengfan.xiyou.widget.ninegridview.NineGridBean;
import com.chengfan.xiyou.widget.ninegridview.NineGridView;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-09/12:21
 * @Description: 我的关注 {@link com.chengfan.xiyou.ui.dynamic.DynamicAttentionFragment}
 */
public class DynamicAttentionAdapter extends BaseRVAdapter<FinanceRecordEntity, BaseViewHolder> {

    LickListener mLickListener;

    public void setLickListener(LickListener lickListener) {
        mLickListener = lickListener;
    }

    public DynamicAttentionAdapter(int layoutResId, @Nullable List<FinanceRecordEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, FinanceRecordEntity item) {
        helper.setText(R.id.attention_des_tv, item.getMember().getNickname());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.attention_user_pic_civ), APIContents.HOST + "/" + item.getMember().getAvatarUrl());
        if (item.getMember().isVip()) {
            helper.getView(R.id.attention_is_hy_iv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.attention_is_hy_iv).setVisibility(View.GONE);
        }

        helper.setText(R.id.attention_des_tv, item.getContent());
        helper.setText(R.id.attention_time_tv, item.getCreateTime());
        helper.setText(R.id.attention_comment_num_tv, item.getTotalComment() + " ");
        helper.setText(R.id.attention_lick_num_tv, item.getTotalPraise() + "");
        helper.setText(R.id.attention_game_name_tv, item.getMember().getAccompanyPlay().get(0).getTitle() + ". ￥" + item.getMember().getAccompanyPlay().get(0).getPrice() + "/小时");
        if (item.isHavePraise()) {
            helper.getView(R.id.attention_fans_iv).setBackgroundResource(R.drawable.ap_dynamic_licked_num);
        } else {
            helper.getView(R.id.attention_fans_iv).setBackgroundResource(R.drawable.ap_dynamic_lick_num);
        }

        helper.getView(R.id.attention_fans_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLickListener.onLickListener(helper.getAdapterPosition());
            }
        });


        NineGridView nineGridView = helper.getView(R.id.attention_ngv);
        ImageView imageView = helper.getView(R.id.attention_iv);
        List<ImageEntity> imageEntityList = new ArrayList<>();
        List<NineGridBean> nineGridBeanList = new ArrayList<>();
        String imageStr = item.getImages();
        if (imageStr != null) {
            String[] strArr = imageStr.split("\\|");
            for (String str : strArr) {
                imageEntityList.add(new ImageEntity(APIContents.HOST + "/" + str));
                nineGridBeanList.add(new NineGridBean(APIContents.HOST + "/" + str));
                Logger.d("DynamicAttentionAdapter ==>>>" + APIContents.HOST + "/" + str);
            }
        }

        Logger.d("DynamicAttentionAdapter ===>>> " + imageEntityList.size());

        if (imageEntityList.size() > 1) {
            imageView.setVisibility(View.GONE);
            nineGridView.setVisibility(View.VISIBLE);
            setPhoneNgV(nineGridView);
            nineGridView.addData(nineGridBeanList);
        } else {
            nineGridView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            if (imageEntityList.size() > 0)
                ImageLoaderManager.getInstance().showImage(imageView, imageEntityList.get(0).getImgUrl());
        }
    }


    private void setPhoneNgV(NineGridView nineGridView) {


        //设置图片加载器，这个是必须的，不然图片无法显示
        nineGridView.setImageLoader(new GlideImageLoader());
        //设置显示列数，默认3列
        nineGridView.setColumnCount(3);
        //设置是否为编辑模式，默认为false
        nineGridView.setIsEditMode(false);
        //设置单张图片显示时的尺寸，默认100dp
        nineGridView.setSingleImageSize(150);
        //设置单张图片显示时的宽高比，默认1.0f
        nineGridView.setSingleImageRatio(0.8f);
        //设置最大显示数量，默认9张
        nineGridView.setMaxNum(6);
        //设置图片显示间隔大小，默认3dp
        nineGridView.setSpcaeSize(4);
        //设置删除图片
        //  nineGridView.setIcDeleteResId(R.drawable.ic_ninegrid_image_delete);
        //设置删除图片与父视图的大小比例，默认0.35f
        nineGridView.setRatioOfDeleteIcon(0.4f);
        //设置“+”号的图片
        // nineGridView.setIcAddMoreResId(R.drawable.ic_ninegrid_addmore);
        //设置各类点击监听
        nineGridView.setOnItemClickListener(new NineGridView.onItemClickListener() {
            @Override
            public void onNineGirdAddMoreClick(int cha) {

            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                List<ImageEntity> imageEntityList = new ArrayList<>();
                //for (int i = 0; i < nineGridBeanList.size(); i++) {
                imageEntityList.add(new ImageEntity(gridBean.getThumbUrl()));
                // }
                ViewPagerDialog viewPagerDialog = new ViewPagerDialog(mContext, imageEntityList);
                viewPagerDialog.show();

            }

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }
        });
    }


    public interface LickListener {
        void onLickListener(int position);
    }
}
