package com.chengfan.xiyou.ui.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.ChatGroupCreateContract;
import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
import com.chengfan.xiyou.domain.presenter.ChatGroupCreatePresenterImpl;
import com.chengfan.xiyou.im.GroupChatInfo;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.adapter.ChatCreateGroupAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.BitmapCompressor;
import com.chengfan.xiyou.utils.RongCreateGroup;
import com.chengfan.xiyou.utils.UserStorage;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.othershe.combinebitmap.CombineBitmap;
import com.othershe.combinebitmap.layout.WechatLayoutManager;
import com.othershe.combinebitmap.listener.OnProgressListener;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        implements ChatGroupCreateContract.View, HttpCallBack {
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

    private Context mContext;
    private HttpCallBack mCallBack;
    private String mGroupChatImage = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_create_group);
        ButterKnife.bind(this);

        mContext = this;
        mCallBack = this;

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
        Log.e("listsize", "" + mChatCreteEntityList.size());
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

                                GroupChatInfo info = new GroupChatInfo();
                                info.setTargetId(createGroupEntity.getData());
                                info.setName(mStringBuffer.toString());
                                info.setImage(mGroupChatImage);
                                info.save();

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
                createImage();
                break;
        }
    }

    /**
     * 创建头像
     */
    private void createImage() {
        showLoading();

        String imageString = APIContents.HOST + "/" + UserStorage.getInstance().getLogin().getAvatarUrl();
        for (int i = 0; i < mSelectList.size(); i++) {
            imageString = imageString + "|" + APIContents.HOST + "/" + mSelectList.get(i).getAvatarUrl();
        }
        String[] images = getArrayFromString(imageString);
        CombineBitmap.init(mContext)
                .setLayoutManager(new WechatLayoutManager())
                .setSize(100)
                .setGap(2)
                .setGapColor(Color.parseColor("#ffffff"))
                .setUrls(images)
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onStart() {
                        Log.e("TAG", "onStart -- 开始合成图片");
                    }

                    @Override
                    public void onComplete(Bitmap bitmap) {
                        String data = BitmapCompressor.compreeBitmapToString(bitmap, 200, 200);
                        uploadImage(data);
                    }
                })
                .build();
    }

    /**
     * 上传合成后图片
     *
     * @param data
     */
    private void uploadImage(final String data) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String filename = createFileName();

                    JSONObject object = new JSONObject();
                    object.put("MemberId", AppData.getString(AppData.Keys.AD_USER_ID));
                    object.put("Source", 0);
                    object.put("FileName", filename);
                    object.put("FileData", data);

                    String url = APIContents.HOST + "/api/Upload/UploadFile";
                    OkHttpUtils.doPostJson(url, object.toString(), mCallBack, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 创建群聊
     */
    private void createGroupChat(String imagePath) {
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
            if (i != 0) {
                mStringBuffer.append("、");
            }
            mStringBuffer.append(mSelectList.get(i).getNickname());
        }
        bean.setName(mStringBuffer.toString());
        bean.setAvatarUrl(imagePath);

        mGroupChatImage = APIContents.HOST + "/" + imagePath;

        Logger.d("ChatCreateGroupActivity ===>>>  " + new Gson().toJson(bean));
        mPresenter.createParameter(bean);
    }

    /**
     * 获取字符数组
     *
     * @param string
     * @return
     */
    public static String[] getArrayFromString(String string) {
        if (string.contains("|")) {
            return string.split("\\|");
        } else {
            return new String[]{string};
        }
    }

    /**
     * 根据当前时间生成文件名
     *
     * @return
     */
    public static String createFileName() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString + ".png";
    }

    private BaseNiceDialog mDialog;

    /**
     * 显示loading
     */
    public void showLoading() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        mDialog = dialog;
                    }
                })
                .setOutCancel(false)
                .setWidth(48)
                .setHeight(48)
                .setShowBottom(false)
                .show(getSupportFragmentManager());
    }

    /**
     * 隐藏loading
     */
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
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
        hideLoading();
        String imagePath = "";
        try {
            JSONObject object = new JSONObject(response);
            imagePath = object.getString("filePath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        createGroupChat(imagePath);
    }
}
