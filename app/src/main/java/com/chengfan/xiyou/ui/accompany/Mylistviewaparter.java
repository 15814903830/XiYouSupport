package com.chengfan.xiyou.ui.accompany;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ApplyLableListBean;
import com.chengfan.xiyou.ui.main.LableAdapter;
import com.chengfan.xiyou.ui.main.LableAdapter2;
import com.chengfan.xiyou.ui.main.LableAdapter4;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.zero.ci.widget.CircleImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */

public class Mylistviewaparter extends BaseAdapter {
    private  Context context;
    private MediaPlayer mPlayer = null;
    private  List<String>  icon=new ArrayList<>();

    public void setList( List<ListviewBase>  list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    List<LableBase> listlable;
    private  List<ListviewBase>  list;
    public Mylistviewaparter(Context context, List<ListviewBase>  list,List<LableBase> listlable){
        this.context=context;
        this.list=list;
        this.listlable=listlable;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        VH vh=null;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.adapter_accompany_game,null);
            vh=new VH();
            vh.tvmoney=convertView.findViewById(R.id.accompany_game_money_tv);
            vh.tvname=convertView.findViewById(R.id.accompany_game_name_tv);
            vh.age=convertView.findViewById(R.id.accompany_game_age_tv);
            vh.sum=convertView.findViewById(R.id.accompany_game_num_tv);
            vh.place=convertView.findViewById(R.id.accompany_game_address_tv);
            vh.headimg=convertView.findViewById(R.id.accompany_game_civ);
            vh.mseximg=convertView.findViewById(R.id.accompany_game_sex_iv);
            vh.linearLayout=convertView.findViewById(R.id.ll_accomgane_play);
            vh.textView=convertView.findViewById(R.id.tv_totle);
            vh.bfzt=convertView.findViewById(R.id.iv_bfzt_img);
            vh.tv_time=convertView.findViewById(R.id.tv_time);
            vh.recyclerView=convertView.findViewById(R.id.rv_labless);
            convertView.setTag(vh);
        }else {
            vh = (VH) convertView.getTag();
        }
        vh.tvmoney.setText(" ¥ "+list.get(position).getPrice()+"/小时");
        vh.tvname.setText(""+list.get(position).getNickname());
        vh.age.setText(""+list.get(position).getAge()+"岁");
        vh.sum.setText(""+list.get(position).getTotalFans()+"粉丝");
        vh.place.setText(""+list.get(position).getAreaName());
        vh.textView.setText("接单量： "+list.get(position).getTotal());

        icon.clear();
        try {
            if (list.get(position).getMember().getApplyLable()!=null){
              String []icons= list.get(position).getMember().getApplyLable().split(",");
              if (icons.length>0){
                  for (int i=0;i<icons.length;i++){
                      for (int j=0;j<listlable.size();j++){
                          if (listlable.get(j).getId()== Integer.parseInt(icons[i])){
                              icon.add(listlable.get(i).getIcon());
                          }
                      }
                  }
              }
                if (list.get(position).getMember().getApprovalLable()!=null) {
                    String[] icons2 = list.get(position).getMember().getApprovalLable().split(",");
                    if (icons2.length > 0) {
                        for (int i = 0; i < icons2.length; i++) {
                            for (int j = 0; j < listlable.size(); j++) {
                                if (listlable.get(j).getId() == Integer.parseInt(icons2[i])) {
                                    icon.add(listlable.get(i).getIcon());
                                }
                            }
                        }
                    }
                    GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
                    vh.recyclerView.setLayoutManager(layoutManager);
                    LableAdapter4 lableAdapter4 = new LableAdapter4(context, icon);
                    vh.recyclerView.setAdapter(lableAdapter4);
                    lableAdapter4.notifyDataSetChanged();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            if (list.get(position).getAudioPath().equals("")){
            }else {
                vh.linearLayout.setVisibility(View.VISIBLE);
                MediaPlayer md = new MediaPlayer();
                try {
                    md.setDataSource(APIContents.HOST+"/"+list.get(position).getAudioPath());//获取音频时长
                    md.prepare();
                    vh.tv_time.setText(md.getDuration()/1000+"s");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final VH finalVh = vh;
        vh.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getAudioPath().equals("")){

                }else {
                    if (finalVh.bfzt.isSelected()){
                        stopplaymusic();
                        finalVh.bfzt.setSelected(false);
                    }else {
                        playmusic(APIContents.HOST+"/"+list.get(position).getAudioPath());
                        finalVh.bfzt.setSelected(true);
                    }
                }

            }
        });


        if (list.get(position).getGender()==1){
            //男
            vh.mseximg.setImageResource(R.drawable.home_nan);
        }else {
            //女
            vh.mseximg.setImageResource(R.drawable.home_nv);
        }
        Glide.with(context).load(APIContents.HOST+"/"+list.get(position).getAvatarUrl()).into(vh.headimg);
        return convertView;
    }

    class VH{
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


}
