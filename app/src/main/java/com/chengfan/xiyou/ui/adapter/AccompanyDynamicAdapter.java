package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.ui.dialog.ViewPagerDialog;
import com.chengfan.xiyou.utils.DataFormatUtil;
import com.chengfan.xiyou.utils.GlideImageLoader;
import com.chengfan.xiyou.widget.ninegridview.NineGirdImageContainer;
import com.chengfan.xiyou.widget.ninegridview.NineGridBean;
import com.chengfan.xiyou.widget.ninegridview.NineGridView;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/15:43
 * @Description: 个人动态 {@link com.chengfan.xiyou.ui.accompany.AccompanyDynamicFragment}
 */
public class AccompanyDynamicAdapter extends BaseRVAdapter<AccompanyUserInfoEntity.MemberNewsBean, BaseViewHolder> {

    private OnItemClickListen onItemClickListen;

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        this.onItemClickListen = onItemClickListen;
    }

    public AccompanyDynamicAdapter(int layoutResId, @Nullable List<AccompanyUserInfoEntity.MemberNewsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AccompanyUserInfoEntity.MemberNewsBean item) {
        helper.setText(R.id.ap_dynamic_title_tv, item.getContent());
        helper.setText(R.id.ap_dynamic_time_tv, DataFormatUtil.formatDate(item.getCreateTime()));
        helper.setText(R.id.dynamic_total_praise_tv, item.getTotalPraise() + "");
        helper.setText(R.id.dynamic_total_comment_tv, item.getTotalComment() + "");

        NineGridView nineGridView = helper.getView(R.id.dynamic_accompany_ngv);
        final ImageView imageViewtu = helper.getView(R.id.ap_dynamic_iv);
        CardView cardView = helper.getView(R.id.cv_dynamic);
        ImageView iv_video = helper.getView(R.id.iv_video_dynamic);

        final ImageView imglike = helper.getView(R.id.iv_imglike);
        if (item.isHavePraise()) {
            imglike.setImageResource(R.drawable.ap_dynamic_licked_num);
        } else {
            imglike.setImageResource(R.drawable.ap_dynamic_lick_num);
        }

        List<ImageEntity> imageEntityList = new ArrayList<>();
        List<NineGridBean> nineGridBeanList = new ArrayList<>();

        String imageStr = item.getImages();
        if (imageStr != null) {
            String[] strArr = imageStr.split("\\|");
            for (String str : strArr) {
                imageEntityList.add(new ImageEntity(APIContents.HOST + "/" + str));
                nineGridBeanList.add(new NineGridBean(APIContents.HOST + "/" + str));
            }
        }

        if (imageEntityList.size() > 1) {
            cardView.setVisibility(View.GONE);
            imageViewtu.setVisibility(View.GONE);
            nineGridView.setVisibility(View.VISIBLE);
            nineGridView.addData(nineGridBeanList);
            setPhoneNgV(nineGridView);
        } else {
            nineGridView.setVisibility(View.GONE);
            if (imageEntityList.size() > 0) {
                cardView.setVisibility(View.VISIBLE);
                imageViewtu.setVisibility(View.VISIBLE);
                ImageLoaderManager.getInstance().showImage(imageViewtu, imageEntityList.get(0).getImgUrl());
                if (DataFormatUtil.isVideo(imageEntityList.get(0).getImgUrl())) {
                    iv_video.setVisibility(View.VISIBLE);
                } else {
                    iv_video.setVisibility(View.GONE);
                }
            } else {
                cardView.setVisibility(View.GONE);
                imageViewtu.setVisibility(View.GONE);
            }
        }

        helper.getView(R.id.ll_comment_dynamic_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListen != null) {
                    onItemClickListen.onCommentClick(helper.getAdapterPosition());
                }
            }
        });
        helper.getView(R.id.ll_liker_dynamic_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListen != null) {
                    onItemClickListen.onLikeClick(helper.getAdapterPosition());
                }
            }
        });
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

    public interface OnItemClickListen {
        void onCommentClick(int position);

        void onLikeClick(int position);
    }

}
