package com.chengfan.xiyou.ui.mine.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/23:29
 * @Description: 订单详情
 */
public class MineOrderDetailActivity extends BaseActivity {

    @BindView(R.id.mine_order_detail_wv)
    WebView mMineOrderDetailWv;
    Bundle revBundle;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mine_order_detail);
        ButterKnife.bind(this);

        revBundle = getIntent().getExtras();
        if (revBundle != null)
            id = revBundle.getInt(APPContents.E_ID);
        mMineOrderDetailWv.loadUrl("http://xy.gx11.cn/WapFinance/AccompanyPlayOrderComment?id=" + id + "&memberId=" + AppData.getString(AppData.Keys.AD_USER_ID));

    }
}
