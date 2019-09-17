package com.chengfan.xiyou.ui.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.ChatGroupContract;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamBean;
import com.chengfan.xiyou.domain.model.entity.RemoveTeamMemberBean;
import com.chengfan.xiyou.domain.presenter.ChatGroupPresenterImpl;
import com.chengfan.xiyou.im.GroupChatInfo;
import com.chengfan.xiyou.ui.adapter.ChatGroupAdapter;
import com.chengfan.xiyou.ui.dialog.ChatGroupMemberDialog;
import com.chengfan.xiyou.ui.dialog.ChatGroupNoMemberDialog;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.RongDismiss;
import com.chengfan.xiyou.utils.RongQuit;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshFooter;
import com.zero.ci.refresh.api.RefreshHeader;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.constant.RefreshState;
import com.zero.ci.refresh.api.listener.OnMultiPurposeListener;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.recyclerview.OnItemClickListener;
import com.zero.ci.widget.recyclerview.OnItemMenuClickListener;
import com.zero.ci.widget.recyclerview.SwipeMenu;
import com.zero.ci.widget.recyclerview.SwipeMenuBridge;
import com.zero.ci.widget.recyclerview.SwipeMenuCreator;
import com.zero.ci.widget.recyclerview.SwipeMenuItem;
import com.zero.ci.widget.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/19:34
 * @Description: 群组 「{@link ChatFriendActivity}」
 */
public class ChatGroupFragment
        extends BaseFragment<ChatGroupContract.View, ChatGroupPresenterImpl>
        implements ChatGroupContract.View ,SwipeRefreshLayout.OnRefreshListener{
    View mView;
    View emptyView;
    @BindView(R.id.chat_group_srv)
    SwipeRecyclerView mChatGroupSrv;
    @BindView(R.id.chat_group_zrl)
    SwipeRefreshLayout mChatGroupZrl;
    Unbinder unbinder;

    List<ChatGroupEntity> mChatGroupEntityList;
    ChatGroupAdapter mChatGroupAdapter;

    ChatGroupMemberDialog mGroupMemberDialog;
    ChatGroupNoMemberDialog mNoMemberDialog;

    int pos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat_group, null);
        unbinder = ButterKnife.bind(this, mView);
        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mChatGroupSrv.getParent(), false);
        mPresenter = new ChatGroupPresenterImpl();
        mPresenter.onAttach(getActivity(), this);
        mPresenter.listLoad();
        initAdapter();
        mGroupMemberDialog = new ChatGroupMemberDialog(getActivity());
        mNoMemberDialog = new ChatGroupNoMemberDialog(getActivity());
        mGroupMemberDialog.setGroupMemberListener(new ChatGroupMemberDialog.GroupMemberListener() {
            @Override
            public void onGroupMemberListener() {

                RemoveTeamBean removeTeamBean = new RemoveTeamBean();
                removeTeamBean.setId(mChatGroupEntityList.get(pos).getTeam().getId());
                removeTeamBean.setMemberId(mChatGroupEntityList.get(pos).getTeam().getMemberId());
                //解散群聊
                mPresenter.removeTeamParameter(removeTeamBean);
            }
        });

        mNoMemberDialog.setGroupNoMemberListener(new ChatGroupNoMemberDialog.GroupNoMemberListener() {
            @Override
            public void onGroupNoMemberListener() {
                RemoveTeamMemberBean removeTeamMemberBean = new RemoveTeamMemberBean();
                removeTeamMemberBean.setTeamId(String.valueOf(mChatGroupEntityList.get(pos).getTeam().getId()));
                removeTeamMemberBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                //退出群聊
                mPresenter.removeTeamMemberParameter(removeTeamMemberBean);

            }
        });
        mChatGroupZrl.setOnRefreshListener(this);//设置刷新监听器
//        mChatGroupZrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPresenter.listLoad();
//                initAdapter();
//                mChatGroupZrl.setRefreshing(false);
//            }
//        });
        return mView;
    }


    private void initAdapter() {
        mChatGroupEntityList = new ArrayList<>();
        mChatGroupAdapter = new ChatGroupAdapter(R.layout.adapter_chat_group, mChatGroupEntityList);
        mChatGroupSrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatGroupSrv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int adapterPosition) {
                RongIM.getInstance().startGroupChat(getActivity(),
                        mChatGroupEntityList.get(adapterPosition).getTeam().getId() + "",
                        mChatGroupEntityList.get(adapterPosition).getTeam().getName());
            }
        });
        mChatGroupSrv.setSwipeMenuCreator(swipeMenuCreator);
        mChatGroupSrv.setOnItemMenuClickListener(mMenuItemClickListener);
        mChatGroupSrv.setAdapter(mChatGroupAdapter);

    }

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {

        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            Log.e("menuBridgeswipeLeftMenu",""+position);
            int width = getResources().getDimensionPixelSize(R.dimen.padding_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            String text;
                if (mChatGroupEntityList.get(position).getTeam().getMemberId()==Integer.parseInt(AppData.getString(AppData.Keys.AD_USER_ID))){
                    text="解散";
                }else {
                    text="删除";
                }

            {
                SwipeMenuItem addItem = new SwipeMenuItem(getActivity()).setBackground(R.color.color_F44336)
                        .setText(text)
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
            Log.e("menuBridgeposition",""+position);
            menuBridge.closeMenu();
            pos = position;
            String userId = AppData.getString(AppData.Keys.AD_USER_ID);
            String memberId = String.valueOf(mChatGroupEntityList.get(position).getTeam().getMemberId());
            if (userId.equals(memberId)) {
                mGroupMemberDialog.show();
            } else {
                mNoMemberDialog.show();
            }

        }
    };

    @Override
    public void removeTeam(BaseApiResponse baseApiResponse) {
        mGroupMemberDialog.dismiss();
        if (baseApiResponse.isSuc()) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            RongDismiss.group(AppData.getString(AppData.Keys.AD_USER_ID),
                                    String.valueOf(mChatGroupEntityList.get(pos).getTeam().getId()));
                        }
                    }
            ).start();
        }

        ToastUtil.show(baseApiResponse.getMsg());
        mPresenter.listLoad();
    }

    @Override
    public void removeTeamMember(BaseApiResponse baseApiResponse) {
        mNoMemberDialog.dismiss();


        if (baseApiResponse.isSuc()) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            RongQuit.group(AppData.getString(AppData.Keys.AD_USER_ID),
                                    String.valueOf(mChatGroupEntityList.get(pos).getTeam().getId()));
                        }
                    }
            ).start();
        }
        ToastUtil.show(baseApiResponse.getMsg());
        mPresenter.listLoad();
    }

    @Override
    public void listLoad(List<ChatGroupEntity> chatGroupEntityList) {
        mChatGroupEntityList = chatGroupEntityList;
        mChatGroupAdapter.setNewData(mChatGroupEntityList);

        for (int i = 0; i < chatGroupEntityList.size(); i++) {
            ChatGroupEntity object = chatGroupEntityList.get(i);
            GroupChatInfo info = new GroupChatInfo();
            info.setTargetId(String.valueOf(object.getTeam().getId()));
            info.setName(object.getTeam().getName());
            info.setImage(APIContents.HOST + "/" + object.getTeam().getAvatarUrl());
            info.save();
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onRefresh() {
        mPresenter.listLoad();
        mChatGroupZrl.setRefreshing(false);
    }
}
