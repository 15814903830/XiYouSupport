package com.chengfan.xiyou.ui.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatGroupDetailContract;
import com.chengfan.xiyou.domain.model.entity.ChatGroupDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupInfoBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.chengfan.xiyou.domain.model.entity.UpdateTeamBean;
import com.chengfan.xiyou.domain.presenter.ChatGroupDetailPresenterImpl;
import com.chengfan.xiyou.ui.adapter.ChatGroupDetailAdapter;
import com.chengfan.xiyou.ui.dialog.ChatChangeNameDialog;
import com.chengfan.xiyou.ui.dialog.ChatGroupMemberDialog;
import com.chengfan.xiyou.ui.dialog.ChatGroupNoMemberDialog;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.RongDismiss;
import com.chengfan.xiyou.utils.RongQuit;
import com.chengfan.xiyou.utils.RongRefresh;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;
import com.zero.ci.widget.recyclerview.OnItemMenuClickListener;
import com.zero.ci.widget.recyclerview.SwipeMenu;
import com.zero.ci.widget.recyclerview.SwipeMenuBridge;
import com.zero.ci.widget.recyclerview.SwipeMenuCreator;
import com.zero.ci.widget.recyclerview.SwipeMenuItem;
import com.zero.ci.widget.recyclerview.SwipeRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/17:38
 * @Description: 群聊详情
 */
public class ChatGroupDetailActivity
        extends BaseActivity<ChatGroupDetailContract.View, ChatGroupDetailPresenterImpl>
        implements ChatGroupDetailContract.View {

    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.chat_group_detail_name_tv)
    RegularTextView mChatGroupDetailNameTv;
    @BindView(R.id.chat_group_detail_sw)
    Switch mChatGroupDetailSw;
    @BindView(R.id.chat_group_detail_rv)
    SwipeRecyclerView mChatGroupDetailRv;
    @BindView(R.id.chat_group_submit_btn)
    Button mChatGroupSubmitBtn;

    Bundle revBundle;
    String teamId, updateGroupName;

    ChatGroupDetailEntity mGroupDetailEntity;

    ChatChangeNameDialog mChatChangeNameDialog;
    ChatGroupMemberDialog mMemberDialog;
    ChatGroupNoMemberDialog mNoMemberDialog;

    ChatGroupDetailAdapter mChatGroupDetailAdapter;

    int pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group_detail);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();


        mXyMiddleTv.setText("群详情");

        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            teamId = revBundle.getString(APPContents.BUNDLE_GROUP_ID);
            Logger.e("ChatGroupDetailActivity ===>>>" + teamId);
            mPresenter.chatGroupParameter(teamId);
        }

        mGroupDetailEntity = new ChatGroupDetailEntity();
        mChatChangeNameDialog = new ChatChangeNameDialog(this);
        mMemberDialog = new ChatGroupMemberDialog(this);
        mNoMemberDialog = new ChatGroupNoMemberDialog(this);
    }


    private void initView() {
        mChatGroupDetailNameTv.setText(mGroupDetailEntity.getName());

        mChatChangeNameDialog.setChangeListener(new ChatChangeNameDialog.ChangeListener() {
            @Override
            public void onChangeListener(String numEt) {//修改群名称

                updateGroupName = numEt;
                UpdateTeamBean bean = new UpdateTeamBean();
                bean.setId(mGroupDetailEntity.getId());
                bean.setMemberId(mGroupDetailEntity.getMemberId());
                bean.setName(updateGroupName);
                mChatGroupDetailNameTv.setText(updateGroupName);
                mPresenter.groupNameParameter(bean);

            }
        });

        mMemberDialog.setGroupMemberListener(new ChatGroupMemberDialog.GroupMemberListener() {
            @Override
            public void onGroupMemberListener() {
                RemoveTeamBean removeTeamBean = new RemoveTeamBean();
                removeTeamBean.setMemberId(mGroupDetailEntity.getMemberId());
                removeTeamBean.setId(mGroupDetailEntity.getId());
                Logger.d("RemoveTeamBean ==>> " + new Gson().toJson(removeTeamBean));
                mPresenter.removeTeamParameter(removeTeamBean);
            }
        });


        mNoMemberDialog.setGroupNoMemberListener(new ChatGroupNoMemberDialog.GroupNoMemberListener() {
            @Override
            public void onGroupNoMemberListener() {
                RemoveTeamMemberBean removeTeamMemberBean = new RemoveTeamMemberBean();
                removeTeamMemberBean.setTeamId(String.valueOf(mGroupDetailEntity.getId()));
                removeTeamMemberBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                Logger.d("RemoveTeamMemberBean ===>>>" + new Gson().toJson(removeTeamMemberBean));
                mPresenter.removeTeamMemberParameter(removeTeamMemberBean);
            }
        });
        mChatGroupDetailSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ChatGroupInfoBean bean = new ChatGroupInfoBean();
                    bean.setMemberId(mGroupDetailEntity.getMemberId());
                    bean.setTeamId(mGroupDetailEntity.getId());
                    mPresenter.groupInfoParameter(bean);
                }
            }
        });


        String userID = AppData.getString(AppData.Keys.AD_USER_ID);
        String memberId = String.valueOf(mGroupDetailEntity.getMemberId());
        if (userID.equals(memberId)) {
            mXyMoreTv.setBackgroundColor(R.drawable.chat_add_more);
            mChatGroupSubmitBtn.setText("删除并解散");
            mChatGroupSubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMemberDialog.show();
                }
            });

        } else {

            mChatGroupSubmitBtn.setText("删除并退出");
            mChatGroupSubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNoMemberDialog.show();
                }
            });

        }


        mChatGroupDetailAdapter = new ChatGroupDetailAdapter(R.layout.adapter_chat_group_detial, mGroupDetailEntity.getTeamMember());
        mChatGroupDetailRv.setLayoutManager(new LinearLayoutManager(this));

        mChatGroupDetailRv.setSwipeMenuCreator(swipeMenuCreator);
        mChatGroupDetailRv.setOnItemMenuClickListener(mMenuItemClickListener);
        mChatGroupDetailRv.setAdapter(mChatGroupDetailAdapter);
    }

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.padding_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem addItem = new SwipeMenuItem(ChatGroupDetailActivity.this).setBackground(R.color.color_F44336)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            String userId = AppData.getString(AppData.Keys.AD_USER_ID);
            if (userId.equals(String.valueOf(mGroupDetailEntity.getMemberId()))) {  //如果是群主，才可以删除成员
                pos = position;
                String memberId = String.valueOf(mGroupDetailEntity.getTeamMember().get(position).getTeamMember().getMemberId());
                exitOrDelete(memberId);
            } else {
                ToastUtil.show("您不是群主，不能删除群成员");
            }
        }
    };

    private void exitOrDelete(String memberId) {
        String userId = AppData.getString(AppData.Keys.AD_USER_ID);

        if (userId.equals(memberId)) {  //群主
            //弹窗是否解散
            mMemberDialog.show();
        } else {
            //删除
            RemoveTeamMemberBean removeTeamMemberBean = new RemoveTeamMemberBean();
            removeTeamMemberBean.setTeamId(String.valueOf(mGroupDetailEntity.getId()));
            removeTeamMemberBean.setMemberId(memberId);
            mPresenter.removeTeamMemberParameter(removeTeamMemberBean);
        }
    }


    @Override
    public void getGroupInfoLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void getGroupNameLoad(BaseApiResponse baseApiResponse) {
        if (baseApiResponse.isSuc()) {
            {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                RongRefresh.group(String.valueOf(mGroupDetailEntity.getId()), updateGroupName);
                            }
                        }
                ).start();
            }

        }
    }

    @Override
    public void removeTeam(BaseApiResponse baseApiResponse) {
        Log.e("TAG", "removeTeam");
        if (baseApiResponse.isSuc()) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            String s = RongDismiss.group(AppData.getString(AppData.Keys.AD_USER_ID), String.valueOf(mGroupDetailEntity.getId()));
                            if (s.equals("200")) {
                                mHandler.sendEmptyMessage(0);
                            }
                        }
                    }
            ).start();
        }

        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void removeTeamMember(BaseApiResponse baseApiResponse) {
        Log.e("TAG", "removeTeamMember");
        if (baseApiResponse.isSuc()) {
            mChatGroupDetailAdapter.remove(pos);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            RongQuit.group(AppData.getString(AppData.Keys.AD_USER_ID), String.valueOf(mGroupDetailEntity.getId()));
                        }
                    }
            ).start();
        }
    }

    @Override
    public void chatGroupLoad(ChatGroupDetailEntity chatGroupEntity) {
        mGroupDetailEntity = chatGroupEntity;
        if (mGroupDetailEntity != null) {
            initView();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    back();
                    break;
            }
        }
    };

    private void back() {
        Intent intent = new Intent();
        intent.putExtra("needFinish", true);
        setResult(0, intent);
        finish();
    }

    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv, R.id.chat_group_name_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                Bundle toBundle = new Bundle();
                toBundle.putSerializable(APPContents.BUNDLE_GROUP, mGroupDetailEntity);
                ForwardUtil.getInstance(this).forward(ChatGroupAddActivity.class, toBundle);
                break;
            case R.id.chat_group_name_ll:
                String userID = AppData.getString(AppData.Keys.AD_USER_ID);
                String memberId = String.valueOf(mGroupDetailEntity.getMemberId());
                Logger.d("ChatGroupDetailActivity ==>>> " + userID + "   ......  " + memberId);
                if (userID.equals(memberId))
                    mChatChangeNameDialog.show();
                break;
        }
    }
}
