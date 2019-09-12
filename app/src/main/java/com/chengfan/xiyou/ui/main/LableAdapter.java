package com.chengfan.xiyou.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 地址列表适配器
 */
public class LableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public List<LableBase> getList() {
        return mList;
    }

    public void setList(List<LableBase> list) {
        mList = list;
    }

    private List<LableBase> mList;
    defaultAddress defaultAddress;
    public LableAdapter(Context context, List<LableBase> list,defaultAddress defaultAddress) {
        this.mContext = context;
        this.mList = list;
        this.defaultAddress=defaultAddress;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclvie_lable_itme, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        final LableBase addressBean = mList.get(i);
        Glide.with(mContext).load(APIContents.HOST+"/"+addressBean.getIcon()).into(((ViewHolder) viewHolder).iv_lable);


        switch (i){
            case 0:
                Glide.with(mContext).load(R.drawable.biaoqian005).into(((ViewHolder) viewHolder).view);
                break;
            case 1:
                Glide.with(mContext).load(R.drawable.biaoqian004).into(((ViewHolder) viewHolder).view);
                break;
            case 2:
                Glide.with(mContext).load(R.drawable.biaoqian003).into(((ViewHolder) viewHolder).view);
                break;
            case 3:
                Glide.with(mContext).load(R.drawable.biaoqian002).into(((ViewHolder) viewHolder).view);
                break;
            case 4:
                Glide.with(mContext).load(R.drawable.biaoqian001).into(((ViewHolder) viewHolder).view);
                break;
        }


        ((ViewHolder) viewHolder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ViewHolder) viewHolder).iv_lable.getVisibility()==View.VISIBLE){
                    ((ViewHolder) viewHolder).iv_lable.setVisibility(View.GONE);
                    defaultAddress.lableitemremove(addressBean.getId());
                }else {
                    ((ViewHolder) viewHolder).iv_lable.setVisibility(View.VISIBLE);
                    defaultAddress.lableitem(addressBean.getId());
                }
            }
        });
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
        ImageView view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_lable = itemView.findViewById(R.id.rv_lable_img);
            view = itemView.findViewById(R.id.view_backround);
        }
    }

    public interface defaultAddress{
        void lableitem(int i);
        void lableitemremove(int i);
    }
}
