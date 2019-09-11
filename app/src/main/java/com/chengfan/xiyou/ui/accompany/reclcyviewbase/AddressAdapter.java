package com.chengfan.xiyou.ui.accompany.reclcyviewbase;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.ui.accompany.LableBase;
import com.chengfan.xiyou.ui.accompany.ListviewBase;
import com.chengfan.xiyou.ui.main.LableAdapter4;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.zero.ci.widget.CircleImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 地址列表适配器
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<ListviewBase> mList;
    List<LableBase> listlable;
    private List<String> icon = new ArrayList<>();
    private MediaPlayer mPlayer = null;
    private OnItemClickListener onItemClickListener;
    private RecyclerView recyclerView;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public AddressAdapter(Context context, List<ListviewBase> list, List<LableBase> listlable) {
        this.mContext = context;
        this.mList = list;
        this.listlable = listlable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_accompany_game, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        ((ViewHolder) viewHolder).tvmoney.setText(" ¥ " + mList.get(i).getPrice() + "/小时");
        ((ViewHolder) viewHolder).tvname.setText("" + mList.get(i).getNickname());
        ((ViewHolder) viewHolder).age.setText("" + mList.get(i).getAge() + "岁");
        ((ViewHolder) viewHolder).sum.setText("" + mList.get(i).getTotalFans() + "粉丝");
        ((ViewHolder) viewHolder).place.setText("" + mList.get(i).getAreaName());
        ((ViewHolder) viewHolder).textView.setText("接单量： " + mList.get(i).getTotal());

        icon.clear();
        try {
            if (mList.get(i).getMember().getApplyLable() != null) {
                String[] icons = mList.get(i).getMember().getApplyLable().split(",");
                if (icons.length > 0) {
                    for (int k = 0; k < icons.length; k++) {
                        for (int j = 0; j < listlable.size(); j++) {
                            if (listlable.get(j).getId() == Integer.parseInt(icons[k])) {
                                icon.add(listlable.get(k).getIcon());
                            }
                        }
                    }
                }
                if (mList.get(i).getMember().getApprovalLable() != null) {
                    String[] icons2 = mList.get(i).getMember().getApprovalLable().split(",");
                    if (icons2.length > 0) {
                        for (int k = 0; k < icons2.length; k++) {
                            for (int j = 0; j < listlable.size(); j++) {
                                if (listlable.get(j).getId() == Integer.parseInt(icons2[k])) {
                                    icon.add(listlable.get(k).getIcon());
                                }
                            }
                        }
                    }
                }
            }

            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
            ((ViewHolder) viewHolder).recyclerView.setLayoutManager(layoutManager);
            LableAdapter4 lableAdapter4 = new LableAdapter4(mContext, icon);
            ((ViewHolder) viewHolder).recyclerView.setAdapter(lableAdapter4);
            Log.e("lableAdapter4","lableAdapter4");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            if (mList.get(i).getAudioPath().equals("")) {
            } else {
                ((ViewHolder) viewHolder).linearLayout.setVisibility(View.VISIBLE);
                MediaPlayer md = new MediaPlayer();
                try {
                    md.setDataSource(APIContents.HOST + "/" + mList.get(i).getAudioPath());//获取音频时长
                    md.prepare();
                    ((ViewHolder) viewHolder).tv_time.setText(md.getDuration() / 1000 + "s");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((ViewHolder) viewHolder).linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(i).getAudioPath().equals("")) {

                } else {
                    if ( ((ViewHolder) viewHolder).bfzt.isSelected()) {
                        stopplaymusic();
                        ((ViewHolder) viewHolder).bfzt.setSelected(false);
                    } else {
                        playmusic(APIContents.HOST + "/" + mList.get(i).getAudioPath());
                        ((ViewHolder) viewHolder).bfzt.setSelected(true);
                    }
                }
            }
        });

        if (mList.get(i).getGender() == 1) {
            //男
            ((ViewHolder) viewHolder).mseximg.setImageResource(R.drawable.home_nan);
        } else {
            //女
            ((ViewHolder) viewHolder).mseximg.setImageResource(R.drawable.home_nv);
        }
        Glide.with(mContext).load(APIContents.HOST + "/" + mList.get(i).getAvatarUrl()).into( ((ViewHolder) viewHolder).headimg);

    }
    //播放录音
    public void  playmusic(String playimg){
        // TODO Auto-generated method stub
        mPlayer = new MediaPlayer();
        try{
            mPlayer.setDataSource(playimg);
            mPlayer.prepare();
            mPlayer.start();
        }catch(IOException e){
        }
    }


    //停止播放录音
    public void  stopplaymusic(){
        mPlayer.release();
        mPlayer = null;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener{
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(RecyclerView parent,View view, int position, int id,List<String> icon);
    }
    @Override
    public void onClick(View v) {
        //根据RecyclerView获得当前View的位置
        int position = recyclerView.getChildAdapterPosition(v);
        //程序执行到此，会去执行具体实现的onItemClick()方法
        if (onItemClickListener!=null){
            onItemClickListener.onItemClick(recyclerView,v,position,mList.get(position).getId(),icon);
        }
    }
    /**
     *   将RecycleView附加到Adapter上
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView= recyclerView;
    }
    /**
     *   将RecycleView从Adapter解除
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView headimg;//头像
        MediumTextView tvmoney;//价格
        MediumTextView tvname;//名字
        RegularTextView age;//年龄
        RegularTextView sum;//粉丝数
        RegularTextView place;//位置
        ImageView mseximg;//性别
        LinearLayout linearLayout;//播放语音
        TextView textView;//订单量
        ImageView bfzt;//播放暂停
        TextView tv_time;//视频时间
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headimg = itemView.findViewById(R.id.accompany_game_civ);
            tvmoney = itemView.findViewById(R.id.accompany_game_money_tv);
            tvname = itemView.findViewById(R.id.accompany_game_name_tv);
            age = itemView.findViewById(R.id.accompany_game_age_tv);
            sum = itemView.findViewById(R.id.accompany_game_num_tv);
            place = itemView.findViewById(R.id.accompany_game_address_tv);
            mseximg = itemView.findViewById(R.id.accompany_game_sex_iv);
            linearLayout = itemView.findViewById(R.id.ll_accomgane_play);
            textView = itemView.findViewById(R.id.tv_totle);
            bfzt = itemView.findViewById(R.id.iv_bfzt_img);
            tv_time = itemView.findViewById(R.id.tv_time);
            recyclerView = itemView.findViewById(R.id.rv_labless);

        }
    }

}
