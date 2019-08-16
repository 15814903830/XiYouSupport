package com.chengfan.xiyou.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.ui.UIApplication;
import com.chengfan.xiyou.ui.chat.ChatFamilyActivity;
import com.chengfan.xiyou.ui.chat.ChatFriendActivity;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/10:43
 * @Description:
 */
public class ChatFragment extends BaseFragment {
    View mView;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chat, null);
        initRong();
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    private void initRong() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://com.chengfan.xiyou").buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.chat_go_family_ll, R.id.xy_more_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_go_family_ll:
                ForwardUtil.getInstance(getActivity()).forward(ChatFamilyActivity.class);
                break;
            case R.id.xy_more_tv:
                ForwardUtil.getInstance(getActivity()).forward(ChatFriendActivity.class);
                break;
        }
    }
}
