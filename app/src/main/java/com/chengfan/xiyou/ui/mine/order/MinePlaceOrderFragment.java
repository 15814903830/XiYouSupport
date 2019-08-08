package com.chengfan.xiyou.ui.mine.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APPContents;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.widget.tablelayout.BottomTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/22:17
 * @Description: 我的下单
 */
public class MinePlaceOrderFragment extends BaseFragment {
    View mView;

    @BindView(R.id.order_tab_layout)
    BottomTabLayout mOrderTabLayout;

    Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mine_order, null);
        mUnbinder = ButterKnife.bind(this, mView);
        initTab();

        return mView;
    }

    private void initTab() {
        // set buttons from menu resource
        mOrderTabLayout.setItems(R.menu.menu_tab_order_flag);
        //set on selected tab listener.
        mOrderTabLayout.setListener(new BottomTabLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switchFragment(id);
            }
        });
        //set button that will be select on start activity
        mOrderTabLayout.setSelectedTab(R.id.menu_all);
        //enable indicator
        mOrderTabLayout.setIndicatorVisible(true);
        //indicator height
        mOrderTabLayout.setIndicatorHeight(getResources().getDimension(R.dimen.padding_2));
        //indicator color
        mOrderTabLayout.setIndicatorColor(R.color.color_D1AE81);
        //indicator line color
        mOrderTabLayout.setIndicatorLineColor(R.color.color_FFFFFF);

    }

    /**
     * Show fragment in container
     *
     * @param id Menu item res id
     */
    public void switchFragment(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.menu_all:
                fragment = MineOrderAllFragment.getInstance(APPContents.TO_PLACE);
                break;
            case R.id.menu_no_started:
                fragment = MineNotStartedFragment.getInstance(APPContents.TO_PLACE);
                break;
            case R.id.menu_ongoing:
                fragment = MineOngoingFragment.getInstance(APPContents.TO_PLACE);
                break;
            case R.id.menu_unproved:
                fragment = MineUnprovedFragment.getInstance(APPContents.TO_PLACE);
                break;
            case R.id.menu_completed:
                fragment = MineCompleteFragment.getInstance(APPContents.TO_PLACE);
                break;

        }
        if (fragment != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.order_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
