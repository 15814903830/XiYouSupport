package com.chengfan.xiyou.ui.accompany;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.zero.ci.widget.CircleImageView;
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
    public void setList( List<ListviewBase>  list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private  List<ListviewBase>  list;
    public Mylistviewaparter(Context context, List<ListviewBase>  list){
        this.context=context;
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            convertView.setTag(vh);
        }else {
            vh = (VH) convertView.getTag();
        }
//        vh.tvmoney.setText(list.get(position).getGradeTitle()+" ¥ "+list.get(position).getPrice());
        vh.tvmoney.setText(" ¥ "+list.get(position).getPrice()+"/小时");
        Log.e("vh.tvmoney",list.get(position).getGradeTitle());
        vh.tvname.setText(""+list.get(position).getNickname());
        vh.age.setText(""+list.get(position).getAge()+"岁");
        vh.sum.setText(""+list.get(position).getTotalFans()+"粉丝");
        vh.place.setText(""+list.get(position).getAreaName());

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
    }

}
