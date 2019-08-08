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
import com.chengfan.xiyou.domain.contract.ChatGroupCreateContract;
import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
import com.chengfan.xiyou.domain.presenter.ChatGroupCreatePresenterImpl;
import com.chengfan.xiyou.ui.adapter.ChatCreateGroupAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.RongCreateGroup;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/19:47
 * @Description: 创建群聊
 */
public class ChatCreateGroupActivity
        extends BaseActivity<ChatGroupCreateContract.View, ChatGroupCreatePresenterImpl>
        implements ChatGroupCreateContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.chat_create_group_rv)
    RecyclerView mChatCreateGroupRv;

    List<ChatCreteEntity> mChatCreteEntityList;
    List<ChatCreteEntity> mSelectList;
    ChatCreateGroupAdapter mChatCreateGroupAdapter;

    View emptyView;
    StringBuffer mStringBuffer;

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
        mXyMiddleTv.setText("创建群聊");
        mXyMoreTv.setText("完成");
        mXyMoreTv.setTextColor(getResources().getColor(R.color.color_D1AE81));
        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mChatCreateGroupRv.getParent(), false);

        mStringBuffer = new StringBuffer();
        mPresenter.chatGroupParameter();
        initRv();
    }


    private void initRv() {
        mSelectList = new ArrayList<>();
        mChatCreteEntityList = new ArrayList<>();
        mChatCreateGroupAdapter = new ChatCreateGroupAdapter(R.layout.adapter_chat_create_group, mChatCreteEntityList);
        mChatCreateGroupRv.setLayoutManager(new LinearLayoutManager(this));
        mChatCreateGroupRv.setAdapter(mChatCreateGroupAdapter);

        mChatCreateGroupAdapter.setSelectedListener(new ChatCreateGroupAdapter.OnItemSelectedListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!mChatCreateGroupAdapter.isSelected.get(position)) {
                    mChatCreateGroupAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mChatCreateGroupAdapter.notifyItemChanged(position);
                    mSelectList.add(mChatCreteEntityList.get(position));

                } else {
                    mChatCreateGroupAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    mChatCreateGroupAdapter.notifyItemChanged(position);
                    mSelectList.add(mChatCreteEntityList.get(position));
                }
            }
        });


    }

    @Override
    public void createdLoad(final ChatCreateGroupEntity createGroupEntity) {
        if (createGroupEntity.isSuc()) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            String code = RongCreateGroup.CreateGroup(mSelectList, createGroupEntity.getData(), mStringBuffer.toString());
                            if (code.equals("200")) {
                                ToastUtil.show(createGroupEntity.getMsg());
                                finish();
                                RongIM.getInstance().startGroupChat(ChatCreateGroupActivity.this, createGroupEntity.getData(), mStringBuffer.toString());
                            }
                        }
                    }
            ).start();
        } else {
            ToastUtil.show(createGroupEntity.getMsg());
        }


    }

    @Override
    public void chatGroupLoad(List<ChatCreteEntity> chatCreteEntityList) {
        if (chatCreteEntityList == null) {
            mChatCreateGroupAdapter.setEmptyView(emptyView);
        } else {
            mChatCreteEntityList = chatCreteEntityList;

            mChatCreateGroupAdapter.setNewData(mChatCreteEntityList);
        }
    }


    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                ChatGroupCreateBean bean = new ChatGroupCreateBean();
                ChatGroupCreateBean.TeamMemberBean teamMemberBean = new ChatGroupCreateBean.TeamMemberBean();
                teamMemberBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                teamMemberBean.setRole("1");
                List<ChatGroupCreateBean.TeamMemberBean> teamMemberBeanList = new ArrayList<>();
                teamMemberBeanList.add(teamMemberBean);
                for (int i = 0; i < mSelectList.size(); i++) {
                    ChatGroupCreateBean.TeamMemberBean teamMemberBean1 = new ChatGroupCreateBean.TeamMemberBean();
                    teamMemberBean1.setMemberId(mSelectList.get(i).getId() + "");
                    teamMemberBean1.setRole("10");
                    teamMemberBeanList.add(teamMemberBean1);
                }
                bean.setTeamMember(teamMemberBeanList);
                bean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));

                for (int i = 0; i < mSelectList.size(); i++) {
                    mStringBuffer.append(mSelectList.get(i).getNickname()).append(",");
                }
                bean.setName(mStringBuffer.toString());

                Logger.d("ChatCreateGroupActivity ===>>>  " + new Gson().toJson(bean));
                mPresenter.createParameter(bean);
                break;
        }
    }


}
