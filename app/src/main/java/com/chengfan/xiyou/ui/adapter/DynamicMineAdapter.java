package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.DynamicMineEntity;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.ui.dialog.ViewPagerDialog;
import com.chengfan.xiyou.utils.GlideImageLoader;
import com.chengfan.xiyou.widget.ninegridview.NineGirdImageContainer;
import com.chengfan.xiyou.widget.ninegridview.NineGridBean;
import com.chengfan.xiyou.widget.ninegridview.NineGridView;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-09/12:45
 * @Description: 我的动态 {@link com.chengfan.xiyou.ui.dynamic.DynamicMineFragment}
 */
public class DynamicMineAdapter extends BaseRVAdapter<DynamicMineEntity, BaseViewHolder> {
    DelOnClickListener mDelOnClickListener;
    LikeListener mLikeListener;

    public void setLikeListener(LikeListener likeListener) {
        mLikeListener = likeListener;
    }

    public void setDelOnClickListener(DelOnClickListener delOnClickListener) {
        mDelOnClickListener = delOnClickListener;
    }

    public DynamicMineAdapter(int layoutResId, @Nullable List<DynamicMineEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, DynamicMineEntity item) {
        Log.e("showImage",""+item.getImages());
        ImageLoaderManager.getInstance().showImage(helper.getView(R.id.attention_user_pic_civ), APIContents.HOST + "/" + item.getMember().getAvatarUrl());
        if (item.getMember().isVip()) {
            helper.getView(R.id.attention_is_hy_iv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.attention_is_hy_iv).setVisibility(View.GONE);
        }


        if (item.isHavePraise()) {
            helper.getView(R.id.mine_like_iv).setBackgroundResource(R.drawable.ap_dynamic_licked_num);
        } else {
            helper.getView(R.id.mine_like_iv).setBackgroundResource(R.drawable.ap_dynamic_lick_num);
        }

        helper.getView(R.id.mine_like_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLikeListener.onLikeListener(helper.getAdapterPosition());
            }
        });

        helper.setText(R.id.attention_des_tv, item.getContent());
        Log.e("time",switchCreateTime(item.getCreateTime()));
        helper.setText(R.id.attention_time_tv, switchCreateTime(item.getCreateTime()));
        helper.setText(R.id.attention_comment_num_tv, item.getTotalComment() + "");
        helper.setText(R.id.attention_lick_num_tv, item.getTotalPraise() + " ");
        if (item.getMember().getAccompanyPlay().size() > 0)
            helper.setText(R.id.attention_game_name_tv, item.getMember().getAccompanyPlay().get(0).getTitle() + ". ￥" + item.getMember().getAccompanyPlay().get(0).getPrice());
        helper.getView(R.id.attention_del_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelOnClickListener.onDelOnClickListener(helper.getAdapterPosition());
            }
        });
        NineGridView nineGridView = helper.getView(R.id.attention_ngv);
        ImageView imageView = helper.getView(R.id.attention_iv);
        List<ImageEntity> imageEntityList = new ArrayList<>();
        List<NineGridBean> nineGridBeanList = new ArrayList<>();
        String imageStr = (String) item.getImages();
        if (imageStr != null) {
            String[] strArr = imageStr.split("\\|");
            for (String str : strArr) {
                Log.e("imgurlstr",str);
                imageEntityList.add(new ImageEntity(APIContents.HOST + "/" + str));
                nineGridBeanList.add(new NineGridBean(APIContents.HOST + "/" + str));
            }
        }
        if (imageEntityList.size() > 1) {
            imageView.setVisibility(View.GONE);
            nineGridView.setVisibility(View.VISIBLE);
            setPhoneNgV(nineGridView);
            nineGridView.addData(nineGridBeanList);
        } else {
            nineGridView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Log.e("imgurl",""+imageEntityList.get(0).getImgUrl());
            ImageLoaderManager.getInstance().showImage(imageView, imageEntityList.get(0).getImgUrl());
            //ImageLoaderManager.getInstance().showImage(imageView, "https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=a9e671b9a551f3dedcb2bf64a4eff0ec/4610b912c8fcc3cef70d70409845d688d53f20f7.jpg");
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

    public interface DelOnClickListener {
        void onDelOnClickListener(int position);

    }


    public interface LikeListener {
        void onLikeListener(int position);
    }
    public String switchCreateTime(String createTime) {
        String formatStr2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        try {
            Date time = format.parse(createTime );
            String date = time.toString();
            //将西方形式的日期字符串转换成java.util.Date对象
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.US);
            Date datetime = (Date) sdf.parse(date);
            //再转换成自己想要显示的格式
            formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr2;
    }
    private String activityStartTime;//活动开始时间

    public String getActivityStartTime() {
        return switchCreateTime(activityStartTime);
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }
}
