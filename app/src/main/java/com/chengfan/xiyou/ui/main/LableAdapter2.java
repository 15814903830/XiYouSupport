package com.chengfan.xiyou.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.ApplyLableListBean;

import java.util.List;

/**
 * 地址列表适配器
 */
public class LableAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public List<ApplyLableListBean> getList() {
        return mList;
    }

    public void setList(List<ApplyLableListBean> mList) {
        mList = mList;
    }

    private List<ApplyLableListBean> mList;
    public LableAdapter2(Context context, List<ApplyLableListBean> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclvie_lable_itme2, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final ApplyLableListBean addressBean = mList.get(i);
        Glide.with(mContext).load(APIContents.HOST+"/"+addressBean.getIcon()).into(((ViewHolder) viewHolder).iv_lable);
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
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_lable = itemView.findViewById(R.id.rv_lable_img);
            view = itemView.findViewById(R.id.view_backround);
        }
    }

}
