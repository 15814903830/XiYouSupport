package com.chengfan.xiyou.ui.chat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatGroupAddContract;
import com.chengfan.xiyou.domain.contract.ChatGroupCreateContract;
import com.chengfan.xiyou.domain.model.entity.ChatAddBean;
import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupDetailEntity;
import com.chengfan.xiyou.domain.presenter.ChatGroupAddPresenterImpl;
import com.chengfan.xiyou.ui.adapter.ChatCreateGroupAdapter;
import com.chengfan.xiyou.ui.adapter.ChatGroupAddAdapter;
import com.chengfan.xiyou.utils.RongJoin;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/18:28
 * @Description: 添加联系人
 */
public class ChatGroupAddActivity extends BaseActivity<ChatGroupAddContract.View, ChatGroupAddPresenterImpl>
        implements ChatGroupCreateContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.chat_create_group_rv)
    RecyclerView mChatCreateGroupRv;

    List<ChatCreteEntity> mChatCreteEntityList;
    List<ChatCreteEntity> mSelectList;
    ChatGroupAddAdapter mChatGroupAddAdapter;

    View emptyView;

    Bundle revBundle;

    ChatGroupDetailEntity mGroupDetailEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_create_group);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mXyMiddleTv.setText("添加联系人");
        mXyMoreTv.setText("确定");
        mXyMoreTv.setTextColor(getResources().getColor(R.color.color_D1AE81));

        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            mGroupDetailEntity = (ChatGroupDetailEntity) revBundle.getSerializable(APPContents.BUNDLE_GROUP);
            mPresenter.chatGroupParameter(String.valueOf(mGroupDetailEntity.getId()));
        }
        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mChatCreateGroupRv.getParent(), false);


        initRv();
    }


    private void initRv() {
        mSelectList = new ArrayList<>();
        mChatCreteEntityList = new ArrayList<>();
        mChatGroupAddAdapter = new ChatGroupAddAdapter(R.layout.adapter_chat_create_group, mChatCreteEntityList);
        mChatCreateGroupRv.setLayoutManager(new LinearLayoutManager(this));
        mChatCreateGroupRv.setAdapter(mChatGroupAddAdapter);

        mChatGroupAddAdapter.setSelectedListener(new ChatCreateGroupAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!mChatGroupAddAdapter.isSelected.get(position)) {
                    mChatGroupAddAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mChatGroupAddAdapter.notifyItemChanged(position);
                    mSelectList.add(mChatCreteEntityList.get(position));

                } else {
                    mChatGroupAddAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    mChatGroupAddAdapter.notifyItemChanged(position);
                    mSelectList.add(mChatCreteEntityList.get(position));
                }
            }
        });


    }

    @Override
    public void createdLoad(ChatCreateGroupEntity baseApiResponse) {
        if (baseApiResponse.isSuc()) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            RongJoin.group(mSelectList, String.valueOf(mGroupDetailEntity.getId()), mGroupDetailEntity.getName());
                        }
                    }
            ).start();
        }

    }


    @Override
    public void chatGroupLoad(List<ChatCreteEntity> chatCreteEntityList) {
        if (chatCreteEntityList == null) {
            mChatGroupAddAdapter.setEmptyView(emptyView);
        } else {
            mChatCreteEntityList = chatCreteEntityList;

            mChatGroupAddAdapter.setNewData(mChatCreteEntityList);
        }
    }


    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                if (mSelectList.size() > 1) {
                    List<ChatAddBean> chatAddBeanList = new ArrayList<>();
                    for (int i = 0; i < mSelectList.size(); i++) {

                        ChatAddBean chatAddBean = new ChatAddBean();
                        chatAddBean.setMemberId(mSelectList.get(i).getMemberId() + "");
                        chatAddBean.setRole("10");
                        chatAddBean.setTeamId(String.valueOf(mGroupDetailEntity.getId()));
                        chatAddBeanList.add(chatAddBean);
                    }
                    mPresenter.addParameter(chatAddBeanList);
                }
                break;
        }
    }
}
