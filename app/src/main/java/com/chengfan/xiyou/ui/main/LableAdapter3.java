package com.chengfan.xiyou.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.ApplyLableListBean;
import com.chengfan.xiyou.ui.accompany.AccomLable2Base;
import com.chengfan.xiyou.ui.accompany.AccompanyGameActivity;
import com.zero.ci.tool.ForwardUtil;

import java.util.List;

/**
 * 更多陪玩
 */
public class LableAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public List<AccomLable2Base> getList() {
        return mList;
    }

    public void setList(List<AccomLable2Base> list) {
        mList = list;
    }

    private List<AccomLable2Base> mList;
    public LableAdapter3(Context context, List<AccomLable2Base> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclvie_lable_itme3, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final AccomLable2Base accomLable2Base = mList.get(i);
        if (accomLable2Base.getPid()!=0){
            if (accomLable2Base.getImages()!=null)
                Glide.with(mContext).load(APIContents.HOST+"/"+accomLable2Base.getImages()).into(((ViewHolder) viewHolder).iv_lable);
            ((ViewHolder) viewHolder).texttitle.setText(accomLable2Base.getTitle());
            ((ViewHolder) viewHolder).iv_lable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle toBundle = new Bundle();
                    toBundle.putString(APPContents.E_SUBJECT_ID, ""+accomLable2Base.getId());
                    toBundle.putString("TITLE", accomLable2Base.getTitle());
                    ForwardUtil.getInstance(mContext).forward(AccompanyGameActivity.class, toBundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {

    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_lable;
        TextView texttitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_lable = itemView.findViewById(R.id.rv_lable_img);
            texttitle = itemView.findViewById(R.id.tv_title);
        }
    }

}
