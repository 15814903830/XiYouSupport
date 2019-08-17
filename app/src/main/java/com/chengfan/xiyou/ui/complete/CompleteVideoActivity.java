package com.chengfan.xiyou.ui.complete;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.CompleteVideoContract;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.chengfan.xiyou.domain.presenter.CompleteVideoPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.widget.SquareRelativeLayout;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/23:04
 * @Description: 视频验证
 */
public class CompleteVideoActivity
        extends BaseActivity<CompleteVideoContract.View, CompleteVideoPresenterImpl>
        implements CompleteVideoContract.View, HttpCallBack {
    @BindView(R.id.xy_middle_tv)
    TextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.iv_album_content_image)
    ImageView mIvAlbumContentImage;
    @BindView(R.id.comment_select_iv)
    ImageView mCommentSelectIv;
    @BindView(R.id.complete_select_tv)
    TextView mCompleteSelectTv;
    @BindView(R.id.complete_update_ll)
    LinearLayout mCompleteUpdateLl;
    @BindView(R.id.complete_video_srl)
    SquareRelativeLayout mCompleteVideoSrl;
    @BindView(R.id.video_z_get_tv)
    TextView mVideoZGetTv;
    @BindView(R.id.view_z_m_tv)
    TextView mViewZMTv;
    @BindView(R.id.complete_des_ll)
    LinearLayout mCompleteDesLl;
    @BindView(R.id.view_z_check_tv)
    TextView mViewZCheckTv;
    @BindView(R.id.complete_save_btn)
    Button mCompleteSaveBtn;
    private ArrayList<AlbumFile> mAlbumFiles;
    UploadFile mUploadFile;
    Bundle revBundle;
    boolean isShow;
    BaseNiceDialog mBaseNiceDialog;

    private HttpCallBack mHttpCallBack;
    private String video = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandlere = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_video);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mXyMiddleTv.setText(getResources().getText(R.string.complete_video_title_txt));
        mHttpCallBack = this;
        revBundle = getIntent().getExtras();
        if (revBundle != null)
            isShow = revBundle.getBoolean(APPContents.BUNDLE_BOOLEAN);
        if (isShow) {
            mXyMiddleTv.setText("");
        } else {
            mXyMoreTv.setText(getResources().getText(R.string.complete_leapfrog_txt));
        }
        mAlbumFiles = new ArrayList<>();


        mCommentSelectIv.setBackgroundResource(R.drawable.icon_video);
        mCompleteSelectTv.setText(getResources().getText(R.string.complete_update_video_txt));


        FontHelper.applyFont(this, mXyMiddleTv, APPContents.FONTS_MEDIUM);
        FontHelper.applyFont(this, mXyMoreTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCommentSelectIv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mVideoZGetTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mViewZCheckTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mViewZMTv, APPContents.FONTS_REGULAR);


    }

    @Override
    public void updateVideoLoad(UpdateEntity updateEntity) {
        mBaseNiceDialog.dismiss();
        if (updateEntity.isSuccess()) {
            video = updateEntity.getFilePath();
            //ForwardUtil.getInstance(this).forward(CompleteOverActivity.class);
        } else {
            ToastUtil.show(updateEntity.getMsg());
        }
    }


    private void selectVideo() {
        Album.video(this)
                .multipleChoice()
                .columnCount(2)
                .selectCount(1)
                .camera(true)
                .checkedList(mAlbumFiles)

                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        notifyData(mAlbumFiles);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        ToastUtil.show("取消");
                    }
                })
                .start();
    }


    private void notifyData(ArrayList<AlbumFile> result) {
        String path = result.get(0).getPath();
        AlbumFile albumFile = result.get(0);
        File tempFile = new File(path.trim());
        String fileName = tempFile.getName();
        mUploadFile = new UploadFile(0, tempFile, fileName);
        Album.getAlbumConfig().
                getAlbumLoader().
                load(mIvAlbumContentImage, albumFile);
        mCommentSelectIv.setBackgroundResource(R.drawable.icon_new_video);
        mCompleteSelectTv.setText(getResources().getText(R.string.complete_update_new_video_txt));
        mCompleteSelectTv.setTextColor(getResources().getColor(R.color.color_FFFFFF));
        mPresenter.updateParameter(mUploadFile);
        showLoading();
        Toast.makeText(this, "视频上传中", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv, R.id.complete_save_btn, R.id.complete_update_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                ForwardUtil.getInstance(this).forward(CompleteOverActivity.class);
                break;
            case R.id.complete_save_btn:
                if (mUploadFile == null)
                    ToastUtil.show("请选择视频");
                else {
                    postvideo(video);
                }

                break;
            case R.id.complete_update_ll:
                selectVideo();
                break;
        }
    }

    /**
     * 显示loading
     */
    public void showLoading() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_loading_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        mBaseNiceDialog = dialog;
                    }
                })
                .setOutCancel(false)
                .setWidth(48)
                .setHeight(48)
                .setShowBottom(false)
                .show(getSupportFragmentManager());
    }

    private void postvideo(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", "" + AppData.getString(AppData.Keys.AD_USER_ID));
                    jsonObject.put("genderVideo", data);
                    OkHttpUtils.doPostJson(APIContents.HOST + "/api/Account/VerificationGender", jsonObject.toString(), mHttpCallBack, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandlere.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandlere.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        VodeoBase vodeoBase = JSON.parseObject(response, VodeoBase.class);
        if (vodeoBase.isSuc()) {
            Toast.makeText(this, "认证信息提交成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "认证信息提交异常", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
