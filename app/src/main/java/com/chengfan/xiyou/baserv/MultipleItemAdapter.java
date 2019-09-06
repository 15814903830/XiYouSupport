package com.chengfan.xiyou.baserv;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MultipleItemAdapter extends BaseMultiItemQuickAdapter<MyMultipleItem, BaseViewHolder> {
    private TextView mViewtapy;

    public MultipleItemAdapter(List data) {
        super(data);
        addItemType(MyMultipleItem.FIRST_TYPE, R.layout.adapter_accompany2);//给对应编号布局绑定xml
    }



    @Override
    protected void convert(BaseViewHolder helper, final MyMultipleItem item) {
        switch (helper.getItemViewType()) {
            case MyMultipleItem.FIRST_TYPE:
                ImageView imageView = helper.getView(R.id.ac_img_riv);
                CircleImageView imghend = helper.getView(R.id.cirv_user_pic_civ);
                Glide.with(mContext).load(APIContents.HOST + "/" + item.getData().getMemberNews().getImages()).into(imageView);
                //设置陪玩分类瀑布流宽高
              //  Glide.with(mContext).load(APIContents.HOST + "/" + item.getData().getMemberNews().getImages()).override(200, 500) ;


                Glide.with(mContext).load(APIContents.HOST + "/" + item.getData().getAvatarUrl()).into(imghend);
                helper.setText(R.id.tv_name, item.getData().getNickname())
//                .setText(R.id.tv_yxpw,item.getData().getSubjectId())
                        .setText(R.id.tv_subject_tv, item.getData().getMemberNews().getContent())
                ;
//                if (item.getData().getSubjectId().equals("1")) {
//                    helper.setText(R.id.tv_yxpw, "游戏陪玩");
//                    helper.setText(R.id.jiage, "￥" + item.getData().getPrice() + "/小时");
//                } else {
//                    helper.getView(R.id.ll_dibu).setVisibility(View.GONE);
//                }
                helper.getView(R.id.ll_dibu).setVisibility(View.GONE);
                break;
        }

    }
}