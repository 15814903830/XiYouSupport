package com.chengfan.xiyou.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.utils.DataFormatUtil;

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

    public void setList(List<AddTextBase> list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private List<AddTextBase> list;
    public Mylistviewaparter(Context context,List<AddTextBase> list){
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
            convertView=LayoutInflater.from(context).inflate(R.layout.addtextitem,null);
            vh=new VH();
            vh.mtiem=convertView.findViewById(R.id.tv_time);
            vh.mname=convertView.findViewById(R.id.tv_name);
            vh.tv_tapy_text=convertView.findViewById(R.id.tv_tapy_text);
            vh.tv_redtext=convertView.findViewById(R.id.tv_redtext);
            vh.tv_context_text=convertView.findViewById(R.id.tv_context_text);

            convertView.setTag(vh);
        }else {
            vh = (VH) convertView.getTag();
        }


        vh.mtiem.setText(DataFormatUtil.formatDate(list.get(position).getCreateTime()));
        vh.mname.setText(list.get(position).getComtent());
        if (list.get(position).getType()==1){//点赞
            vh.tv_tapy_text.setText("点赞通知");
            vh.tv_context_text.setVisibility(View.GONE);
        }else if (list.get(position).getType()==2){//评论
            vh.tv_tapy_text.setText("评论通知");
        }

        if (!list.get(position).isStatus()){
            vh.tv_redtext.setVisibility(View.GONE);
        }

//        vh.tv_redtext.setText(list.get(position).);
//        vh.tv_context_text.setText(list.get(position).);

        return convertView;
    }


    class VH{
        TextView mtiem;
        TextView mname;
        TextView tv_tapy_text;
        TextView tv_redtext;
        TextView tv_context_text;
    }
}
