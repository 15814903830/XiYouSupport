package com.chengfan.xiyou.ui.chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatFriendContract;
import com.chengfan.xiyou.domain.model.entity.ChatFriendEntity;
import com.chengfan.xiyou.domain.presenter.ChatFriendPresenterImpl;
import com.chengfan.xiyou.ui.adapter.ChatFriendAdapter;
import com.chengfan.xiyou.ui.dialog.ChatFriendDialog;
import com.chengfan.xiyou.utils.AppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;
import com.zero.ci.widget.recyclerview.OnItemClickListener;
import com.zero.ci.widget.recyclerview.OnItemMenuClickListener;
import com.zero.ci.widget.recyclerview.SwipeMenu;
import com.zero.ci.widget.recyclerview.SwipeMenuBridge;
import com.zero.ci.widget.recyclerview.SwipeMenuCreator;
import com.zero.ci.widget.recyclerview.SwipeMenuItem;
import com.zero.ci.widget.recyclerview.SwipeRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-10/19:33
 * @Description: 好友 「{@link ChatFriendActivity}」
 */
public class ChatFriendFragment
        extends BaseFragment<ChatFriendContract.View, ChatFriendPresenterImpl>
        implements ChatFriendContract.View {
    View mView;
    View emptyView;
    @BindView(R.id.chat_friend_srv)
    SwipeRecyclerView mChatFriendSrv;
    @BindView(R.id.chat_friend_zrl)
    ZRefreshLayout mChatFriendZrl;
    Unbinder unbinder;

    List<ChatFriendEntity> mChatFriendEntityList;
    ChatFriendAdapter mChatFriendAdapter;
    ChatFriendDialog mChatFriendDialog;
    String firendId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat_firend, null);
        unbinder = ButterKnife.bind(this, mView);

        emptyView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) mChatFriendSrv.getParent(), false);

        mPresenter = new ChatFriendPresenterImpl();
        mPresenter.onAttach(getActivity(), this);

        mChatFriendDialog = new ChatFriendDialog(getActivity());

        mPresenter.friendListParameter();
        initRv();


        mChatFriendDialog.setChatFriendListener(new ChatFriendDialog.ChatFriendListener() {
            @Override
            public void onChatFriendListener() {
                mPresenter.removeParameter(firendId);
            }
        });
        return mView;
    }


    private void initRv() {
        mChatFriendEntityList = new ArrayList<>();

        mChatFriendSrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatFriendSrv.setSwipeMenuCreator(swipeMenuCreator);
        mChatFriendSrv.setOnItemMenuClickListener(mMenuItemClickListener);
        mChatFriendSrv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                Log.e("adapterPosition",""+mChatFriendEntityList.get(adapterPosition).getMemberId());
                Log.e("adapterPosition",""+mChatFriendEntityList.get(adapterPosition).getId());
                RongIM.getInstance().startPrivateChat(getActivity(), mChatFriendEntityList.get(adapterPosition).getId() + "", mChatFriendEntityList.get(adapterPosition).getNickname());
            }
        });


        mChatFriendAdapter = new ChatFriendAdapter(R.layout.adapter_chat_friend, mChatFriendEntityList);
        mChatFriendSrv.setAdapter(mChatFriendAdapter);


    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.padding_80);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {

                SwipeMenuItem addItem = new SwipeMenuItem(getActivity()).setBackground(R.color.color_F44336)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();
            mChatFriendDialog.show();
            firendId = mChatFriendEntityList.get(position).getId() + "";
            Log.e("myurl:---",""+position);
        }
    };


    @Override
    public void removeFriend(BaseApiResponse baseApiResponse) {
        mChatFriendDialog.dismiss();
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void getFriendList(List<ChatFriendEntity> chatFriendEntityList) {
        if (chatFriendEntityList.size() < 1) {
            mChatFriendAdapter.setEmptyView(emptyView);
        } else {
            mChatFriendEntityList = chatFriendEntityList;
            mChatFriendAdapter.setNewData(mChatFriendEntityList);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
