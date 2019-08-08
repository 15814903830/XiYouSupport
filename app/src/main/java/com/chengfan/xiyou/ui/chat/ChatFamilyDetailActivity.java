package com.chengfan.xiyou.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/16:42
 * @Description: 家族详情
 */
public class ChatFamilyDetailActivity extends BaseActivity {
    @BindView(R.id.family_detail_wv)
    WebView mFamilyDetailWv;
    Bundle revBundle;
    String familyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_family_detail);
        ButterKnife.bind(this);
        revBundle = getIntent().getBundleExtra(APPContents.BUNDLE_FAMILY);
        if (revBundle != null) {
            familyId = revBundle.getString(APPContents.BUNDLE_FAMILY);
            mFamilyDetailWv.loadUrl("http://xy.gx11.cn/Wap/FamilyDetail?id=" + AppData.getString(AppData.Keys.AD_USER_ID) + "&familyId=" + familyId);
        }
    }

    @OnClick(R.id.family_back_btn)
    public void onClick() {
        finish();
    }
}
