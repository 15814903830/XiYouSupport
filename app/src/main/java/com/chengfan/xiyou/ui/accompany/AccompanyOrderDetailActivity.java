package com.chengfan.xiyou.ui.accompany;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.contract.AccompanyOrderDetailContract;
import com.chengfan.xiyou.domain.presenter.AccompanyOrderDetailPresenterImpl;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/19:38
 * @Description: 订单详情
 */
public class AccompanyOrderDetailActivity extends BaseActivity<AccompanyOrderDetailContract.View, AccompanyOrderDetailPresenterImpl> implements AccompanyOrderDetailContract.View {

    @BindView(R.id.order_detail_wv)
    WebView mOrderDetailWv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_order_detail);
        ButterKnife.bind(this);

        mOrderDetailWv.loadUrl("http://xy.gx11.cn/WapFinance/AccompanyPlayOrderDetail?id="+"s"+"&memberId="+ AppData.getString(AppData.Keys.AD_USER_ID));
        Log.e("weburl",""+"http://xy.gx11.cn/WapFinance/AccompanyPlayOrderDetail?id="+"s"+"&memberId="+ AppData.getString(AppData.Keys.AD_USER_ID));
    }

    @Override
    public void sendPrivateLetterLoad(String result) {

    }
}
