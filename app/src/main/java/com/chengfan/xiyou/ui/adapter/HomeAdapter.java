package com.chengfan.xiyou.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.domain.model.entity.MemberBean;
import com.chengfan.xiyou.ui.dialog.ViewPagerDialog;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.base.adapter.BaseViewHolder;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-14/21:48
 * @Description: 首页「{@link com.chengfan.xiyou.ui.main.HomeFragment}」
 */
public class HomeAdapter extends BaseRVAdapter<MemberBean, BaseViewHolder> {

    public HomeAdapter(int layoutResId, @Nullable List<MemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MemberBean item) {
        helper.setText(R.id.home_user_name_tv, item.getNickname());
        if (item.isVip()) {
            helper.getView(R.id.home_is_hy_iv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.home_is_hy_iv).setVisibility(View.GONE);
        }

        if (item.getAccompanyPlay() != null) {
            helper.getView(R.id.home_user_game_name_tv).setVisibility(View.VISIBLE);
            helper.setText(R.id.home_user_game_name_tv,
                    item.getAccompanyPlay().getSubject().getTitle()
                            + " ▪ ￥" + item.getAccompanyPlay().getPrice() + "/小时");
        } else {
            helper.getView(R.id.home_user_game_name_tv).setVisibility(View.GONE);
            helper.setText(R.id.home_user_game_name_tv, "");
        }

        helper.setText(R.id.home_user_address_tv, item.getAreaName());
        helper.setText(R.id.home_user_age_tv, item.getAge() + "岁");
        helper.setText(R.id.home_user_fanse_num_tv, item.getTotalFans() + "粉丝");


        ImageView seximg = helper.getView(R.id.home_user_sex_iv);

        if (item.getGender() == 1) {
            seximg.setImageResource(R.drawable.home_nan);
        } else {
            seximg.setImageResource(R.drawable.home_nv);
        }

        Glide.with(mContext).load(APIContents.HOST + "/" + item.getAvatarUrl()).into((ImageView) helper.getView(R.id.home_user_pic_civ));
       // ImageLoaderManager.getInstance().showImage(helper.getView(R.id.home_user_pic_civ), APIContents.HOST + "/" + item.getAvatarUrl());

        List<ImageEntity> imageEntityList = new ArrayList<>();
        String imageStr = item.getMemberNews().getImages();
        if (imageStr != null) {
            String[] strArr = imageStr.split("\\|");
            for (String str : strArr) {
                Logger.d(APIContents.HOST + "/" + str);
                imageEntityList.add(new ImageEntity(APIContents.HOST + "/" + str));
            }
        }
        if (imageEntityList.size() >= 1)
            Glide.with(mContext).load(imageEntityList.get(0).getImgUrl()).into((ImageView) helper.getView(R.id.home_user_url_iv));

        //final ViewPagerDialog viewPagerDialog = new ViewPagerDialog(mContext, imageEntityList);
//        helper.getView(R.id.home_user_url_iv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPagerDialog.show();
//            }
//        });


    }

}
