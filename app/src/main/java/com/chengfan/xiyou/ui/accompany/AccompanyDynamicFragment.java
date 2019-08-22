package com.chengfan.xiyou.ui.accompany;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyUserInfoEntity;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.WebActivity;
import com.chengfan.xiyou.ui.adapter.AccompanyDynamicAdapter;
import com.chengfan.xiyou.ui.dynamic.DynamicDetailActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.MyToastUtil;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.tool.ForwardUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-05/14:52
 * @Description: 个人动态
 */
public class AccompanyDynamicFragment extends BaseFragment implements HttpCallBack {

    View mView;
    @BindView(R.id.ap_dynamic_rv)
    RecyclerView mApDynamicRv;
    Unbinder mUnbinder;

    AccompanyDynamicAdapter mAccompanyDynamicAdapter;

    AccompanyUserInfoEntity mAccompanyUserInfoEntity;

    private int mOperatePosition = -1;

    private BottomSheetDialog dialog;

    private HttpCallBack mCallBack;

    private Context mContext;

    public static AccompanyDynamicFragment getInstance(AccompanyUserInfoEntity accompanyUserInfoEntity) {
        AccompanyDynamicFragment newsFragment = new AccompanyDynamicFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(APPContents.BUNDLE_FRAGMENT, accompanyUserInfoEntity);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_accompany_dynamic, null);
        mUnbinder = ButterKnife.bind(this, mView);
        mAccompanyUserInfoEntity = new AccompanyUserInfoEntity();

        Bundle arguments = this.getArguments();
        mAccompanyUserInfoEntity = (AccompanyUserInfoEntity) arguments.getSerializable(APPContents.BUNDLE_FRAGMENT);

        mCallBack = this;

        if (mAccompanyUserInfoEntity != null) {
            mAccompanyDynamicAdapter = new AccompanyDynamicAdapter(R.layout.adapter_accompany_dynamic, mAccompanyUserInfoEntity.getMemberNews());
            mApDynamicRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mApDynamicRv.setAdapter(mAccompanyDynamicAdapter);
            mApDynamicRv.setFocusable(false);
            mAccompanyDynamicAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                    turnToDetail(position);
                }
            });
            mAccompanyDynamicAdapter.setOnItemClickListen(new AccompanyDynamicAdapter.OnItemClickListen() {
                @Override
                public void onCommentClick(int position) {
                    showCommentDialog(position);
                }

                @Override
                public void onLikeClick(int position) {
                    like(position);
                }
            });
        }
        return mView;
    }

    private void turnToDetail(int position) {
        if (position < 0 || position >= mAccompanyUserInfoEntity.getMemberNews().size()) {
            return;
        }
        if (mAccompanyUserInfoEntity.getMemberNews().get(position).getImages().contains(".mp4")) {
            int id = mAccompanyUserInfoEntity.getMemberNews().get(position).getId();
            String userId = AppData.getString(AppData.Keys.AD_USER_ID);
            String url = APIContents.HOST + "/WapNews/MemberNewsVoidDetail?" + "id=" + id + "&memberId=" + userId;
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.KEY_URL, url);
            startActivity(intent);
        } else {
            Bundle toBundle = new Bundle();
            toBundle.putString(APPContents.BUNDLE_DYNAMIC_ID, mAccompanyUserInfoEntity.getMemberNews().get(position).getId() + "");
            ForwardUtil.getInstance(getActivity()).forward(DynamicDetailActivity.class, toBundle);
        }
    }

    private void like(int position) {
        if (position < 0 || position >= mAccompanyUserInfoEntity.getMemberNews().size()) {
            return;
        }
        if (mOperatePosition != -1) {
            MyToastUtil.showShortToast(mContext, "其他操作正在处理中，请稍后...");
            return;
        }
        mOperatePosition = position;
        final int dynamicId = mAccompanyUserInfoEntity.getMemberNews().get(position).getId();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    JSONObject object = new JSONObject();
                    object.put("memberNewsId", dynamicId);
                    object.put("memberId", AppData.getString(AppData.Keys.AD_USER_ID));
                    OkHttpUtils.doPostJson(APIContents.MEMBER_LIKE, object.toString(),
                            mCallBack, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 显示评论弹窗
     *
     * @param position
     */
    private void showCommentDialog(final int position) {
        if (position < 0 || position >= mAccompanyUserInfoEntity.getMemberNews().size()) {
            return;
        }
        if (mOperatePosition != -1) {
            MyToastUtil.showShortToast(mContext, "其他操作正在处理中，请稍后...");
            return;
        }
        if (getActivity() == null) {
            return;
        }
        final int dynamicID = mAccompanyUserInfoEntity.getMemberNews().get(position).getId();
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());
        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    mOperatePosition = position;
                    comment(dynamicID, commentContent);
                    dialog.dismiss();
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

    /**
     * 评论
     *
     * @param dynamicID
     * @param commentContent
     */
    private void comment(final int dynamicID, final String commentContent) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    JSONObject object = new JSONObject();
                    object.put("memberId", AppData.getString(AppData.Keys.AD_USER_ID));
                    object.put("memberNewsId", dynamicID);
                    object.put("content", commentContent);
                    OkHttpUtils.doPostJson(APIContents.PublishMemberNewsComment, object.toString(),
                            mCallBack, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandler.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        Log.e("TAG", "onHandlerMessageCallback -- " + requestId + "\n" + response);
        switch (requestId) {
            case 0:  //评论
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.optBoolean("suc", false);
                    if (success) {
                        int count = object.optInt("data", -1);
                        if (count >= 0 && mOperatePosition >= 0 &&
                                mOperatePosition < mAccompanyUserInfoEntity.getMemberNews().size()) {
                            mAccompanyUserInfoEntity.getMemberNews()
                                    .get(mOperatePosition).setTotalComment(count);
                            mAccompanyDynamicAdapter.notifyItemChanged(mOperatePosition);
                        }
                        MyToastUtil.showShortToast(mContext, "评论成功");
                    } else {
                        String msg = object.getString("msg");
                        MyToastUtil.showShortToast(mContext, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mOperatePosition = -1;
                break;
            case 1:  //点赞
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.optBoolean("suc", false);
                    if (success) {
                        int count = object.optInt("data", -1);
                        if (count >= 0 && mOperatePosition >= 0 &&
                                mOperatePosition < mAccompanyUserInfoEntity.getMemberNews().size()) {
                            boolean havePraise = !mAccompanyUserInfoEntity.getMemberNews()
                                    .get(mOperatePosition).isHavePraise();
                            mAccompanyUserInfoEntity.getMemberNews()
                                    .get(mOperatePosition).setHavePraise(havePraise);
                            mAccompanyUserInfoEntity.getMemberNews()
                                    .get(mOperatePosition).setTotalPraise(count);
                            mAccompanyDynamicAdapter.notifyItemChanged(mOperatePosition);
                        }
                    } else {
                        String msg = object.getString("msg");
                        MyToastUtil.showShortToast(mContext, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mOperatePosition = -1;
                break;
        }
    }
}
