package com.chengfan.xiyou.ui.dynamic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.DynamicAttentionContract;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.FinanceRecordEntity;
import com.chengfan.xiyou.domain.model.entity.LikeBase;
import com.chengfan.xiyou.domain.model.entity.PublishCommentBean;
import com.chengfan.xiyou.domain.presenter.DynamicAttentionPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.ui.WebActivity;
import com.chengfan.xiyou.ui.adapter.DynamicAttentionAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.DataFormatUtil;
import com.chengfan.xiyou.utils.UserStorage;
import com.google.gson.Gson;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/18:53
 * @Description: 我的关注
 */
public class DynamicAttentionFragment extends
        BaseFragment<DynamicAttentionContract.View, DynamicAttentionPresenterImpl>
        implements DynamicAttentionContract.View, HttpCallBack {
    View mView;
    @BindView(R.id.attention_rv)
    RecyclerView mAttentionRv;
    @BindView(R.id.attention_zrl)
    ZRefreshLayout mAttentionZrl;

    Unbinder mUnbinder;

    List<FinanceRecordEntity> mFinanceRecordEntityList;
    DynamicAttentionAdapter mDynamicAttentionAdapter;

    HttpCallBack mHttpCallBack;
    int page = 1;

    private BottomSheetDialog dialog;
    DynamicDetailEntity.MemberNewsCommentBean mCommentBean = new DynamicDetailEntity.MemberNewsCommentBean();
    private int mCommentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic_attention, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mPresenter = new DynamicAttentionPresenterImpl();
        mPresenter.onAttach(getActivity(), this);
        mFinanceRecordEntityList = new ArrayList<>();
        mHttpCallBack = this;
        initRv();
        initZrl();
        mPresenter.listParameter(1, true);
        return mView;
    }

    private void initZrl() {
        mAttentionZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAttentionZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.listParameter(page, false);
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
                        mPresenter.listParameter(page, true);
                        mAttentionZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void listLoad(List<FinanceRecordEntity> financeRecordEntityList, boolean isPtr) {
        if (financeRecordEntityList != null) {
            if (isPtr) {
                mDynamicAttentionAdapter.replaceData(mFinanceRecordEntityList);
                mFinanceRecordEntityList = financeRecordEntityList;
            } else {
                mFinanceRecordEntityList.addAll(financeRecordEntityList);
            }
            mDynamicAttentionAdapter.setNewData(mFinanceRecordEntityList);
        }
    }

    @Override
    public void publishCommentLoad(BaseApiResponse baseApiResponse, boolean isNoChild) {
        if (mCommentPosition >= 0 && mCommentPosition < mFinanceRecordEntityList.size()) {
            int comment = mFinanceRecordEntityList.get(mCommentPosition).getTotalComment() + 1;
            mFinanceRecordEntityList.get(mCommentPosition).setTotalComment(comment);
            mDynamicAttentionAdapter.notifyItemChanged(mCommentPosition);
            mCommentPosition = -1;
        }
    }

    private void initRv() {
        mDynamicAttentionAdapter = new DynamicAttentionAdapter(R.layout.adapter_dynamic_attention, mFinanceRecordEntityList);
        mAttentionRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAttentionRv.setAdapter(mDynamicAttentionAdapter);
        mDynamicAttentionAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                turnToDetail(position);
            }
        });

        mDynamicAttentionAdapter.setLickListener(new DynamicAttentionAdapter.LickListener() {
            @Override
            public void onLickListener(final int position) {
                LikeBase shipBean = new LikeBase();
                shipBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                shipBean.setMemberNewsId("" + mFinanceRecordEntityList.get(position).getId());
                HttpRequest.post(APIContents.MEMBER_LIKE)
                        .paramsJsonString(new Gson().toJson(shipBean))
                        .execute(new AbstractResponse<BaseApiResponse>() {
                            @Override
                            public void onSuccess(BaseApiResponse result) {
                                if (result.isSuc()) {
                                    if (mFinanceRecordEntityList.get(position).isHavePraise()) {
                                        mFinanceRecordEntityList.get(position).setHavePraise(false);
                                        mFinanceRecordEntityList.get(position).setTotalPraise(mFinanceRecordEntityList.get(position).getTotalPraise() - 1);
                                    } else {
                                        mFinanceRecordEntityList.get(position).setHavePraise(true);
                                        mFinanceRecordEntityList.get(position).setTotalPraise(mFinanceRecordEntityList.get(position).getTotalPraise() + 1);
                                    }
                                    mDynamicAttentionAdapter.setNewData(mFinanceRecordEntityList);
                                    mDynamicAttentionAdapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });

        mDynamicAttentionAdapter.setOnItemCommentClick(new DynamicAttentionAdapter.OnItemCommentClick() {
            @Override
            public void onCommentClick(int position) {
                showCommentDialog(position);
            }
        });
    }

    private void turnToDetail(int position) {
        if (position < 0 || position >= mFinanceRecordEntityList.size()) {
            return;
        }
        if (DataFormatUtil.isVideo(mFinanceRecordEntityList.get(position).getImages())) {
            int id = mFinanceRecordEntityList.get(position).getId();
            String userId = AppData.getString(AppData.Keys.AD_USER_ID);
            String url = APIContents.HOST + "/WapNews/MemberNewsVoidDetail?" + "id=" + id + "&memberId=" + userId;
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.KEY_URL, url);
            startActivity(intent);
        } else {
            Bundle toBundle = new Bundle();
            toBundle.putString(APPContents.BUNDLE_DYNAMIC_ID, mFinanceRecordEntityList.get(position).getId() + "");
            ForwardUtil.getInstance(getActivity()).forward(DynamicDetailActivity.class, toBundle);
        }
    }

    /**
     * 显示评论弹窗
     *
     * @param position
     */
    private void showCommentDialog(final int position) {
        mCommentPosition = -1;
        if (position < 0 || position >= mFinanceRecordEntityList.size()) {
            return;
        }
        if (getActivity() == null) {
            return;
        }
        final int dynamicID = mFinanceRecordEntityList.get(position).getId();
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
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
                    mCommentPosition = position;
                    dialog.dismiss();
                    mCommentBean.setNickname(UserStorage.getInstance().getLogin().getNickname());
                    mCommentBean.setContent(commentContent);
                    PublishCommentBean commentBean = new PublishCommentBean();
                    commentBean.setMemberNewsId(String.valueOf(dynamicID));
                    commentBean.setContent(commentContent);
                    commentBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    mPresenter.publishCommentParameter(commentBean, true);
                } else {
                    Toast.makeText(getActivity(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResponse(String response, int requestId) {

    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {

    }
}
