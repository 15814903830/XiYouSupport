package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.SayHiBean;
import com.chengfan.xiyou.domain.model.entity.SayHiEntity;
import com.chengfan.xiyou.ui.adapter.SayHiAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FontHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.widget.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/12:27
 * @Description:
 */
public class SayHiDialog extends Dialog {
    RecyclerView mRecyclerView;
    Button mSubmitBtn, mCloseBtn;
    SayHiAdapter mSayHiAdapter;
    List<SayHiEntity> mSayHiEntityList;

    Context mContext;

    public SayHiDialog(Context context) {
        super(context, R.style.card_dialog_style);
        mContext = context;
    }

    public SayHiDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public SayHiDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_say_hi);

        TextView titleTv = findViewById(R.id.sayhi_title_tv);
        FontHelper.applyFont(mContext, titleTv, APPContents.FONTS_REGULAR);

        mSubmitBtn = findViewById(R.id.hi_submit_btn);
        mCloseBtn = findViewById(R.id.hi_close_btn);
        mRecyclerView = findViewById(R.id.hi_rv);


        mSayHiEntityList = new ArrayList<>();
        mSayHiAdapter = new SayHiAdapter(R.layout.adapter_say_hi, mSayHiEntityList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mRecyclerView.setAdapter(mSayHiAdapter);
        requestSayHi();
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSayHiEntityList.size() > 0)
                    requestAdd();
            }
        });
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }


    private void requestSayHi() {
        HttpRequest.get(APIContents.NearbyMember)
                .params(APPContents.E_ID, AppData.getString(AppData.Keys.AD_USER_ID))
                .params(APPContents.E_LIMIT, 10)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Logger.d("requestSayHi : " + result);
                        Type type = new TypeToken<List<SayHiEntity>>() {
                        }.getType();
                        List<SayHiEntity> sayHiEntityList = new Gson().fromJson(result, type);
                        mSayHiEntityList = sayHiEntityList;
                        mSayHiAdapter.addData(mSayHiEntityList);
                    }
                });
    }


    private void requestAdd() {
        List<SayHiBean> sayHiBeanList = new ArrayList<>();
        for (int i = 0; i < mSayHiEntityList.size(); i++) {
            SayHiBean sayHiBean = new SayHiBean();
            sayHiBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
            sayHiBean.setMemberId(mSayHiEntityList.get(i).getId());
            sayHiBeanList.add(sayHiBean);
        }
        Logger.d("requestAdd  : " + new Gson().toJson(sayHiBeanList));
        HttpRequest.post(APIContents.SayHi + "/" + AppData.getString(AppData.Keys.AD_USER_ID))
                .paramsJsonString(new Gson().toJson(sayHiBeanList))
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Logger.e("accompany detail say hi :" + result);

                        Type type = new TypeToken<BaseApiResponse>() {
                        }.getType();
                        BaseApiResponse baseApiResponse = new Gson().fromJson(result, type);
                        if (baseApiResponse.isSuc()) {
                            dismiss();
                        }
                    }
                });
    }
}
