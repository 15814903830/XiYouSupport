package com.chengfan.xiyou.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.chengfan.xiyou.ui.accompany.AccomPanyActivity;
import com.chengfan.xiyou.ui.accompany.AccompanyGameActivity;
import com.zero.ci.tool.ForwardUtil;

import java.util.List;

/**
 * 地址列表适配器
 */
public class ListByAreaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public RecyBaee getList() {
        return mList;
    }

    public void setList(RecyBaee mList) {
        mList = mList;
    }

    private RecyBaee mList;
    public ListByAreaAdapter(Context context,RecyBaee mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclvietime, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final RecyBaee.SubjectBean recyBaee = mList.getSubject().get(i);
        if (recyBaee.getId()==0){
            Glide.with(mContext).load(R.drawable.accompany_more).into(((ViewHolder) viewHolder).tv_homeimg);
        }else {
            Glide.with(mContext).load(APIContents.HOST+"/"+recyBaee.getImages()).into(((ViewHolder) viewHolder).tv_homeimg);
        }
        ((ViewHolder) viewHolder).tv_homeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyBaee.getId()==0){
                    Intent intent=new Intent(mContext, AccomPanyActivity.class);
                    mContext.startActivity(intent);
                }else {
                    Bundle toBundle = new Bundle();
                    toBundle.putString(APPContents.E_SUBJECT_ID,""+recyBaee.getId());
                    toBundle.putString("TITLE",""+ recyBaee.getTitle());
                    ForwardUtil.getInstance(mContext).forward(AccompanyGameActivity.class, toBundle);
                }
            }
        });
        ((ViewHolder) viewHolder).tv_hometext.setText(recyBaee.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.getSubject().size();
    }

    @Override
    public void onClick(View v) {

    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tv_homeimg;
        TextView tv_hometext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_homeimg = itemView.findViewById(R.id.iv_homeimg);
            tv_hometext = itemView.findViewById(R.id.tv_hometext);
        }
    }

}
