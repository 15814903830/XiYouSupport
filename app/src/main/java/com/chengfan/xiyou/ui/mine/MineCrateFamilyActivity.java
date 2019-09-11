package com.chengfan.xiyou.ui.mine;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.ChatGroupCreateContract;
import com.chengfan.xiyou.domain.contract.MineCrateFamilyContract;
import com.chengfan.xiyou.domain.model.entity.ChatCreateGroupEntity;
import com.chengfan.xiyou.domain.model.entity.ChatCreteEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupCreateBean;
import com.chengfan.xiyou.domain.model.entity.CreateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.chengfan.xiyou.domain.presenter.MineCrateFamilyPresenterImpl;
import com.chengfan.xiyou.im.GroupChatInfo;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.chat.ChatCreateGroupActivity;
import com.chengfan.xiyou.ui.mine.order.FileBase;
import com.chengfan.xiyou.ui.mine.order.MinEBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
import com.chengfan.xiyou.utils.GlideImageLoader;
import com.chengfan.xiyou.utils.RongCreateGroup;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.widget.ninegridview.NineGirdImageContainer;
import com.chengfan.xiyou.widget.ninegridview.NineGridBean;
import com.chengfan.xiyou.widget.ninegridview.NineGridView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-08/17:11
 * @Description: 创建家族
 */
public class MineCrateFamilyActivity extends BaseActivity<MineCrateFamilyContract.View, MineCrateFamilyPresenterImpl> implements MineCrateFamilyContract.View, HttpCallBack, ChatGroupCreateContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.create_name_et)
    EditText mCreateNameEt;
    @BindView(R.id.crate_des_et)
    EditText mCrateDesEt;
    @BindView(R.id.create_ngv)
    NineGridView mCreateNgv;
    private CountDownTimer timer;

    private ArrayList<AlbumFile> mAlbumFiles;
    private List<UploadFile> mUploadFileList;
    List<NineGridBean> resultList;
    private HttpCallBack mHttpCallBack;

    Bundle revBundle;

    MineFamilyEntity mMineFamilyEntity;
    boolean isCreateNew;
    private FileBase fileBase;
    private String stringimg = "";
    private BaseNiceDialog mdialog;
    private int imgsdialog=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_create_family);
        ButterKnife.bind(this);

        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        mHttpCallBack = this;
        mXyMiddleTv.setText(getResources().getText(R.string.create_title_txt));
        mXyMoreTv.setText(getResources().getText(R.string.create_more_txt));

        mAlbumFiles = new ArrayList<>();
        mUploadFileList = new ArrayList<>();
        resultList = new ArrayList<>();
        setPhoneRgv();


        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            isCreateNew = revBundle.getBoolean(APPContents.BUNDLE_BOOLEAN);
            Log.e("isCreateNew", "" + isCreateNew);
            if (isCreateNew) {
                mXyMiddleTv.setText("编辑家族");
                mMineFamilyEntity = AppData.getObject(AppData.Keys.AD_FAMILY_OBJECT, MineFamilyEntity.class);
            } else {
            }
        }


    }
    /**
     * 创建群聊
     */
    private void createGroupChat(String imagePath) {
//        ChatGroupCreateBean bean = new ChatGroupCreateBean();
//        ChatGroupCreateBean.TeamMemberBean teamMemberBean = new ChatGroupCreateBean.TeamMemberBean();
//        teamMemberBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
//        teamMemberBean.setRole("1");
//        List<ChatGroupCreateBean.TeamMemberBean> teamMemberBeanList = new ArrayList<>();
//        teamMemberBeanList.add(teamMemberBean);
//        bean.setTeamMember(teamMemberBeanList);
//        bean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
//        bean.setName(mCreateNameEt.getText().toString()+"T");
//        bean.setAvatarUrl(imagePath);
//        Logger.d("ChatCreateGroupActivity ===>>>  " + new Gson().toJson(bean));
//        mPresenter.createParameter(bean);
//        mPresenter.chatGroupParameter();
    }

    @Override
    public void createFamilyLoad(BaseApiResponse result) {
        if (result.getMsg().equals("添加成功")) {
            finish();
          //  createGroupChat("");
        }
        Toast.makeText(this, result.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadListLoad(BaseApiResponse baseApiResponse) {

    }

    @Override
    public void updateFamilyLoad(BaseApiResponse result) {
        if (result.getMsg().equals("编辑成功")) {
            finish();
        }
        Toast.makeText(this, result.getMsg(), Toast.LENGTH_SHORT).show();
    }

    private void setPhoneRgv() {

        mCreateNgv.setImageLoader(new GlideImageLoader());
        mCreateNgv.setIsEditMode(true);
        mCreateNgv.setSingleImageSize(105);
        mCreateNgv.setSingleImageRatio(0.8f);
        mCreateNgv.setMaxNum(9);
        mCreateNgv.setRatioOfDeleteIcon(0.2f);
        mCreateNgv.setOnItemClickListener(new NineGridView.onItemClickListener() {
            @Override
            public void onNineGirdAddMoreClick(int cha) {
                selectImage();
            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                previewImage(position);
            }

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                mAlbumFiles.remove(position);
            }
        });
    }

    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(9)
                .checkedList(mAlbumFiles)
                .widget(
                        Widget.newDarkBuilder(this)
                                //.title(mToolbar.getTitle().toString())
                                .build()
                )
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

    private void previewImage(int position) {
        if (mAlbumFiles == null || mAlbumFiles.size() == 0) {
            Toast.makeText(this, R.string.no_selected, Toast.LENGTH_LONG).show();
        } else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(mAlbumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .build()
                    )
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles = result;
                            notifyData(mAlbumFiles);
                        }
                    })
                    .start();
        }
    }

    private void notifyData(ArrayList<AlbumFile> result) {
        resultList.clear();
        for (AlbumFile albumFile : result) {
            NineGridBean nineGirdData = new NineGridBean(albumFile.getPath());
            resultList.add(nineGirdData);
        }
        mCreateNgv.addData(resultList);
        for (int i = 0; i < mAlbumFiles.size(); i++) {
            String path = mAlbumFiles.get(i).getPath();
            File tempFile = new File(path.trim());
            String fileName = tempFile.getName();
            mUploadFileList.add(new UploadFile(0, tempFile, fileName));
        }
        showLoading();
        imgsdialog=result.size();
        for (int i = 0; i < result.size(); i++) {
            postimg(best64(result.get(i).getPath()));
        }

    }

    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                String nameStr = mCreateNameEt.getText().toString().trim();
                String desStr = mCrateDesEt.getText().toString().trim();

                for (int i = 0; i < mUploadFileList.size(); i++) {
                    XYUploadEntity xyUploadEntity = new XYUploadEntity();
                    xyUploadEntity.setFileData(FileToBase64.best64(mUploadFileList.get(i)));
                    xyUploadEntity.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    xyUploadEntity.setFileName(mUploadFileList.get(i).getKey());
                    xyUploadEntity.setSource("Member");
                }

                if (isCreateNew) {
                    UpdateFamilyBean updateFamilyBean = new UpdateFamilyBean();
                    if (stringimg.equals("")) {
                        updateFamilyBean.setBanner(APPContents.Banner);
                    } else {
                        updateFamilyBean.setBanner(stringimg);
                        Log.e("setBannerstringimg",stringimg);
                    }
                    updateFamilyBean.setDescribe(desStr);
                    updateFamilyBean.setName(nameStr);
                    updateFamilyBean.setAvatarUrl(mMineFamilyEntity.getAvatarUrl());
                    updateFamilyBean.setId(mMineFamilyEntity.getId());
                    updateFamilyBean.setMemberId(mMineFamilyEntity.getMemberId());
                    updateFamilyBean.setCreateTime(mMineFamilyEntity.getCreateTime());
                    mPresenter.updateFamilyParameter(updateFamilyBean);

                } else {
                    CreateFamilyBean createFamilyBean = new CreateFamilyBean();
                    createFamilyBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    createFamilyBean.setName(nameStr);
                    List<CreateFamilyBean.FamilyMemberBean> familyMemberBeans = new ArrayList<>();
                    CreateFamilyBean.FamilyMemberBean familyMemberBean = new CreateFamilyBean.FamilyMemberBean();
                    familyMemberBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    familyMemberBean.setRole("1");
                    createFamilyBean.setBanner(stringimg);
                    familyMemberBeans.add(familyMemberBean);
                    createFamilyBean.setFamilyMember(familyMemberBeans);
                    mPresenter.createFamilyParameter(createFamilyBean);
                    Log.e("createFamilyBean", "" + familyMemberBeans.size());

                }

                break;
        }
    }

    public static String best64(String file) {
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream("" + file);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static String listToString(List<UploadFile> mList) {
        final String SEPARATOR = "|";
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            for (UploadFile item : mList) {
                sb.append(item.getKey());
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length()
                    - SEPARATOR.length());
            return convertedListStr;
        } else
            return "List is null!!!";
    }

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


    private void postimg(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("source", "AccompanyPlayNews");
                    jsonObject.put("fileName", "png");
                    jsonObject.put("fileData", data);
                    OkHttpUtils.doPostJson(APIContents.UPLOAD_FILE, jsonObject.toString(), mHttpCallBack, 0);
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
        switch (requestId) {
            case 0:
                try {
                    fileBase = JSON.parseObject(response, FileBase.class);
                    stringimg = stringimg + fileBase.getFilePath() + "|";
                    Log.e("stringimg", stringimg);
                    imgsdialog--;
                    if (imgsdialog==0){
                        hideLoading();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                        mdialog = dialog;
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
        if (mdialog != null) {
            mdialog.dismiss();
        }
    }

    @Override
    public void createdLoad(final ChatCreateGroupEntity createGroupEntity) {
//        final List<ChatCreteEntity> mSelectList=new ArrayList<>();
//        ChatCreteEntity chatCreteEntity=new ChatCreteEntity();
//        chatCreteEntity.setAvatarUrl("UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb");
//        chatCreteEntity.setId(1083);
//        chatCreteEntity.setm
//        chatCreteEntity.setGender(0);
//        chatCreteEntity.setNickname("胡椒");
//        chatCreteEntity.setUserName("15814903830");
//        mSelectList.add(chatCreteEntity);
//            new Thread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            String code = RongCreateGroup.CreateGroup(mSelectList, createGroupEntity.getData(),  mCreateNameEt.getText().toString()+"T");
//                            Log.e("rongyuncode","code: "+code);
//                            if (code.equals("200")) {
//                                GroupChatInfo info = new GroupChatInfo();
//                                info.setTargetId(createGroupEntity.getData());
//                                info.setName(mCreateNameEt.getText().toString()+"T");
//                                info.setImage("http://img0.imgtn.bdimg.com/it/u=2786741331,312930537&fm=26&gp=0.jpg");
//                                info.save();
//                                finish();
//                                RongIM.getInstance().startGroupChat(MineCrateFamilyActivity.this, createGroupEntity.getData(), mCreateNameEt.getText().toString()+"T");
//                            }
//                        }
//                    }
//            ).start();
    }

    @Override
    public void chatGroupLoad(List<ChatCreteEntity> chatCreteEntityList) {
//        for (int i=0;i<chatCreteEntityList.size();i++){
//            Log.e("chatCreteEntityList",chatCreteEntityList.get(i).getNickname());
//        }

    }
}
