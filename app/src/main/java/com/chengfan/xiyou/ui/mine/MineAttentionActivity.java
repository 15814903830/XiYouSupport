package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineAttentionContract;
import com.chengfan.xiyou.domain.model.entity.MineAttentionEntity;
import com.chengfan.xiyou.domain.presenter.MineAttentionPresenterImpl;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.MineAttentionAdapter;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
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
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/12:37
 * @Description: 关注
 */
public class MineAttentionActivity extends BaseActivity<MineAttentionContract.View, MineAttentionPresenterImpl> implements MineAttentionContract.View {

    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.attention_srv)
    SwipeRecyclerView mAttentionSrv;
    @BindView(R.id.attention_zrl)
    ZRefreshLayout mAttentionZrl;

    List<MineAttentionEntity> mMineAttentionEntityList;
    MineAttentionAdapter mMineAttentionAdapter;
    int page = 1;
    int delPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_attention);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText(getResources().getText(R.string.attention_title_txt));

        mMineAttentionEntityList = new ArrayList<>();

        mAttentionSrv.setLayoutManager(new LinearLayoutManager(this));
        mAttentionSrv.setSwipeMenuCreator(swipeMenuCreator);
        mAttentionSrv.setOnItemMenuClickListener(mMenuItemClickListener);

        mMineAttentionAdapter = new MineAttentionAdapter(R.layout.adapter_mine_attention, mMineAttentionEntityList);
        mAttentionSrv.setAdapter(mMineAttentionAdapter);
        mMineAttentionAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mMineAttentionEntityList.get(position).getMemberId());
                ForwardUtil.getInstance(MineAttentionActivity.this).forward(AccompanyUserInfoActivity.class, toBundle);
            }
        });
        mPresenter.attentionParameter(1, true);
        initZrl();
    }


    private void initZrl() {
        mAttentionZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAttentionZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.attentionParameter(page, false);
                        mAttentionZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mAttentionZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.attentionParameter(page, true);
                        mAttentionZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void attentionLoad(List<MineAttentionEntity> attentionEntityList, boolean isPtr) {
        if (attentionEntityList.size() > 0) {
            if (isPtr) {
                mMineAttentionEntityList = attentionEntityList;
            } else {
                mMineAttentionEntityList.addAll(attentionEntityList);
            }
            mMineAttentionAdapter.setNewData(mMineAttentionEntityList);
        }
    }

    @Override
    public void memberShipLoad(BaseApiResponse baseApiResponse) {
        if (baseApiResponse.isSuc()) {
            ToastUtil.show("取消关注");
            mMineAttentionAdapter.remove(delPosition);
        }
    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.padding_180);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {

                SwipeMenuItem addItem = new SwipeMenuItem(MineAttentionActivity.this).setBackground(R.color.color_F44336)
                        .setText("取消关注")
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
            delPosition = position;
            mPresenter.memberShip(mMineAttentionEntityList.get(position).getMemberId());

        }
    };

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }
}
