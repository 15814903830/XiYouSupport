package com.chengfan.xiyou.ui.dynamic;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.DynamicDetailContract;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.domain.model.entity.MemberShipBean;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
import com.chengfan.xiyou.domain.presenter.DynamicDetailPresenterImpl;
import com.chengfan.xiyou.ui.accompany.AccompanyDetailActivity;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.CommentExpandAdapter;
import com.chengfan.xiyou.ui.adapter.DynamicDetailAdapter;
import com.chengfan.xiyou.ui.adapter.ViewPagerDialogAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.UserStorage;
import com.chengfan.xiyou.view.CommentExpandableListView;
import com.chengfan.xiyou.view.RegularTextView;
import com.chengfan.xiyou.widget.viewpager.JazzyViewPager;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/23:27
 * @Description: 动态详情
 */
public class DynamicDetailActivity extends BaseActivity<DynamicDetailContract.View, DynamicDetailPresenterImpl> implements DynamicDetailContract.View {

    @BindView(R.id.detail_page_userLogo)
    CircleImageView mDetailPageUserLogo;
    @BindView(R.id.detail_page_userName)
    RegularTextView mDetailPageUserName;
    @BindView(R.id.detail_page_time)
    RegularTextView mDetailPageTime;
    @BindView(R.id.detail_page_title)
    RegularTextView mDetailPageTitle;
    @BindView(R.id.detail_game_select_rv)
    RecyclerView mDetailGameSelectRv;
    @BindView(R.id.detail_page_lv_comment)
    CommentExpandableListView expandableListView;
    @BindView(R.id.detail_page_do_comment)
    RegularTextView mDetailPageDoComment;

    @BindView(R.id.detail_page_focus)
    RegularTextView mDetailPageFocus;

    @BindView(R.id.detail_jazzy_vp)
    JazzyViewPager mDetailJazzyVp;

    @BindView(R.id.detail_img_num_tv)
    RegularTextView mDetailImgNumTv;

    DynamicDetailAdapter mDynamicDetailAdapter;
    DynamicDetailEntity mDynamicDetailEntity;

    int mImageUrlsSize;
    List<ImageEntity> mImageEntityList;

    Bundle revBundle;
    String dynamicID;
    int selctPosition;

    private CommentExpandAdapter adapter;
    private List<DynamicDetailEntity.MemberNewsCommentBean> commentsList;
    DynamicDetailEntity.ReplyDetailBean mReplyDetailBean = new DynamicDetailEntity.ReplyDetailBean();
    DynamicDetailEntity.MemberNewsCommentBean mCommentBean = new DynamicDetailEntity.MemberNewsCommentBean();
    private BottomSheetDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_detail);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();


        revBundle = getIntent().getExtras();
        if (revBundle != null)
            dynamicID = revBundle.getString(APPContents.BUNDLE_DYNAMIC_ID);


        mDynamicDetailEntity = new DynamicDetailEntity();
        commentsList = new ArrayList<>();

        mPresenter.dynamicDetailParameter(Integer.parseInt(dynamicID));


    }

    private void initAdapter() {
        mDynamicDetailAdapter = new DynamicDetailAdapter(R.layout.adapter_search_game, mDynamicDetailEntity.getAccompanyPlay());
        mDetailGameSelectRv.setLayoutManager(new LinearLayoutManager(this));
        mDetailGameSelectRv.setAdapter(mDynamicDetailAdapter);
        mDynamicDetailAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mDynamicDetailEntity.getAccompanyPlay().get(position).getId());
                ForwardUtil.getInstance(DynamicDetailActivity.this).forward(AccompanyDetailActivity.class, toBundle);
            }
        });

    }


    @Override
    public void dynamicDetailLoad(DynamicDetailEntity dynamicDetailEntity) {
        mDynamicDetailEntity = dynamicDetailEntity;
        initView(mDynamicDetailEntity);
        commentsList = dynamicDetailEntity.getMemberNewsComment();
        initExpandableListView(commentsList);
    }

    @Override
    public void publishCommentLoad(BaseApiResponse baseApiResponse, boolean isNoChild) {
        if (baseApiResponse.isSuc())
            mPresenter.dynamicDetailParameter(Integer.parseInt(dynamicID));
        ToastUtil.show(baseApiResponse.getMsg());
    }

    private void initView(final DynamicDetailEntity dynamicDetailEntity) {

        mImageEntityList = new ArrayList<>();
        String imageStr = dynamicDetailEntity.getImages();
        if (imageStr != null) {
            String[] strArr = imageStr.split("\\|");
            for (String str : strArr) {
                Logger.d(APIContents.HOST + str);
                mImageEntityList.add(new ImageEntity(APIContents.HOST + "/" + str));
            }
        }
        initImageToViewPager(mImageEntityList);

        mDetailPageUserName.setText(dynamicDetailEntity.getMember().getNickname());
        mDetailPageTime.setText(dynamicDetailEntity.getCreateTime());
        mDetailPageTitle.setText(dynamicDetailEntity.getContent() + "");
        if (dynamicDetailEntity.isHavePraise()) {
            mDetailPageFocus.setText("已关注");
        } else {
            mDetailPageFocus.setText("关注");
        }

        mDetailPageFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamicDetailEntity.isHavePraise()) {
                    requestLike(true);
                } else {
                    requestLike(false);
                }

            }
        });
        initAdapter();
    }

    private void initImageToViewPager(final List<ImageEntity> list) {
        if (list != null)
            mImageUrlsSize = list.size();
        for (int i = 0; i < mImageUrlsSize; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15, 15);
            lp.setMargins(10, 0, 10, 0);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        mDetailJazzyVp.setTransitionEffect(JazzyViewPager.TransitionEffect.Standard);
        mDetailJazzyVp.setOnPageChangeListener(new MyPageChangeListener());
        ViewPagerDialogAdapter viewPagerAdapter = new ViewPagerDialogAdapter(this, list, mDetailJazzyVp);
        mDetailJazzyVp.setAdapter(viewPagerAdapter);

        mDetailJazzyVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mImageUrlsSize == 0 || mImageUrlsSize == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(final int position) {
            mDetailImgNumTv.setText(position + 1 + " / " + mImageEntityList.size());
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }

    private void requestMemberShip() {
        MemberShipBean shipBean = new MemberShipBean();
        shipBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
        shipBean.setMemberId(mDynamicDetailEntity.getMemberId());

        HttpRequest.get(APIContents.ACCOMPANY)
                .paramsJsonString(new Gson().toJson(shipBean))
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Logger.e("dynamicDetail ===>> requestMemberShip" + result);
                    }
                });
    }


    @OnClick({R.id.detail_back, R.id.detail_page_focus, R.id.detail_page_do_comment, R.id.detail_go_user_info_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.detail_page_focus:
                requestMemberShip();
                break;
            case R.id.detail_page_do_comment:
                showCommentDialog();
                break;
            case R.id.detail_go_user_info_ll:
                Bundle toBundles = new Bundle();
                toBundles.putInt(APPContents.E_CURRENT_MEMBER_ID, mDynamicDetailEntity.getMemberId());
                ForwardUtil.getInstance(this).forward(AccompanyUserInfoActivity.class, toBundles);
                break;
        }
    }


    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<DynamicDetailEntity.MemberNewsCommentBean> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Logger.e("onGroupClick: 当前的评论id>>>" + commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Logger.d(" expandableListView.setOnChildClickListener ====>>>> 点击了回复 ");
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }


    /**
     * 弹出评论框
     */
    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {

                    //commentOnWork(commentContent);
                    dialog.dismiss();
//                    DynamicDetailEntity.MemberNewsCommentBean bean = new DynamicDetailEntity.MemberNewsCommentBean();
//                    bean.setNickname(UserStorage.getInstance().getLogin().getNickname());
//                    bean.setContent(commentContent);
//                    adapter.addTheCommentData(bean);

                    mCommentBean.setNickname(UserStorage.getInstance().getLogin().getNickname());
                    mCommentBean.setContent(commentContent);

                    PublishCommentBean commentBean = new PublishCommentBean();
                    commentBean.setMemberNewsId(dynamicID);
                    commentBean.setContent(commentContent);
                    commentBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));

                    Logger.d("DynamicDetailActivity ====>>>>  detail_page_do_comment : " + new Gson().toJson(commentBean));
                    mPresenter.publishCommentParameter(commentBean, true);


                    //  Toast.makeText(DynamicDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DynamicDetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * 弹出回复框
     *
     * @param position
     */
    private void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickname() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    //  ReplyDetailBean detailBean = new ReplyDetailBean("小红", replyContent);

                    mReplyDetailBean.setNickname(UserStorage.getInstance().getLogin().getNickname());
                    mReplyDetailBean.setContent(replyContent);

                    selctPosition = position;
                    PublishCommentBean commentBean = new PublishCommentBean();
                    commentBean.setMemberNewsId(dynamicID);
                    commentBean.setContent(replyContent);
                    commentBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    commentBean.setMemberNewsCommentId(String.valueOf(commentsList.get(position).getId()));

                    Logger.d("DynamicDetailActivity ====>>>> showReply >>>> " + new Gson().toJson(commentBean));
                    mPresenter.publishCommentParameter(commentBean, false);

                    //  Toast.makeText(DynamicDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DynamicDetailActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }


    private void requestLike(final boolean isLike) {
        MemberShipBean shipBean = new MemberShipBean();
        shipBean.setFriendId(AppData.getString(AppData.Keys.AD_USER_ID));
        shipBean.setMemberId(mDynamicDetailEntity.getMemberId());
        HttpRequest.post(APIContents.MEMBER_SHIP)
                .paramsJsonString(new Gson().toJson(shipBean))
                .execute(new AbstractResponse<BaseApiResponse>() {
                    @Override
                    public void onSuccess(BaseApiResponse result) {
                        if (result.isSuc()) {
                            if (isLike) {
                                mDetailPageFocus.setText("已关注");
                            } else {
                                mDetailPageFocus.setText("关注");
                            }
                        }
                    }
                });
    }
}
