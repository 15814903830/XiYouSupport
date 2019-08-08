package com.chengfan.xiyou.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.MemberSelectBean;
import com.chengfan.xiyou.ui.mine.MineMemberActivity;

import java.util.List;
import java.util.Set;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-08/10:54
 * @Description:
 */
public class MineMemberSelectAdapter extends RecyclerView.Adapter<MineMemberSelectAdapter.ViewHolder> {

    List<MemberSelectBean> mMemberSelectBeanList;
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView dataTv;
        TextView moneyTv;
        RelativeLayout mLayout;

        public ViewHolder(View view) {
            super(view);
            nameTv = view.findViewById(R.id.member_title_tv);
            dataTv = view.findViewById(R.id.member_time_tv);
            moneyTv = view.findViewById(R.id.member_money_tv);
            mLayout = view.findViewById(R.id.member_layout);
        }
    }

    public MineMemberSelectAdapter(List<MemberSelectBean> memberSelectBeanList) {
        mMemberSelectBeanList = memberSelectBeanList;
    }

    public void notify(List<MemberSelectBean> selectBeans) {
        mMemberSelectBeanList = selectBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_member, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Set<Integer> positionSet = MineMemberActivity.positionSet;
        //检查set里是否包含position，包含则显示选中的背景色，不包含则反之
        if (positionSet.contains(position)) {
            holder.mLayout.setBackgroundResource(R.drawable.xy_member_select);
            holder.nameTv.setTextColor(mContext.getResources().getColor(R.color.color_D1AE81));
        } else {
            holder.mLayout.setBackgroundResource(R.drawable.xy_member_unselect);
            holder.nameTv.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
        holder.moneyTv.setText(mMemberSelectBeanList.get(position).getPrice());
        holder.dataTv.setText(mMemberSelectBeanList.get(position).getDays());
        holder.nameTv.setText(mMemberSelectBeanList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMemberSelectBeanList.size();
    }


}
