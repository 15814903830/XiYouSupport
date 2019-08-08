package com.chengfan.xiyou.ui.mine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineCrateFamilyContract;
import com.chengfan.xiyou.domain.model.entity.CreateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.MineFamilyEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateFamilyBean;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.chengfan.xiyou.domain.presenter.MineCrateFamilyPresenterImpl;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
import com.chengfan.xiyou.utils.GlideImageLoader;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-08/17:11
 * @Description: 创建家族
 */
public class MineCrateFamilyActivity extends BaseActivity<MineCrateFamilyContract.View, MineCrateFamilyPresenterImpl> implements MineCrateFamilyContract.View {
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


    private ArrayList<AlbumFile> mAlbumFiles;
    private List<UploadFile> mUploadFileList;
    List<NineGridBean> resultList;

    Bundle revBundle;

    MineFamilyEntity mMineFamilyEntity;
    boolean isCreateNew;

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

        mXyMiddleTv.setText(getResources().getText(R.string.create_title_txt));
        mXyMoreTv.setText(getResources().getText(R.string.create_more_txt));

        mAlbumFiles = new ArrayList<>();
        mUploadFileList = new ArrayList<>();
        resultList = new ArrayList<>();
        setPhoneRgv();


        revBundle = getIntent().getExtras();
        if (revBundle != null) {
            isCreateNew = revBundle.getBoolean(APPContents.BUNDLE_BOOLEAN);
            Logger.d("MineCrateFamilyActivity ===>>>  " + isCreateNew);
            if (isCreateNew) {

            } else {
                mMineFamilyEntity = AppData.getObject(AppData.Keys.AD_FAMILY_OBJECT, MineFamilyEntity.class);
                mCreateNameEt.setText(mMineFamilyEntity.getName());
                mCrateDesEt.setText(mMineFamilyEntity.getDescribe());
                String imageStr = mMineFamilyEntity.getBanner();
                if (imageStr != null) {
                    String[] strArr = imageStr.split("\\|");
                    for (String str : strArr) {
                        Logger.d(APIContents.HOST + str);
                        NineGridBean nineGirdData = new NineGridBean(APIContents.HOST + "/" + str);
                        resultList.add(nineGirdData);
                        mCreateNgv.addData(resultList);
                    }
                }
            }
        }


    }


    @Override
    public void createFamilyLoad(BaseApiResponse result) {

        ToastUtil.show(result.getMsg());
    }

    @Override
    public void uploadListLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void updateFamilyLoad(BaseApiResponse result) {
        ToastUtil.show(result.getMsg());
    }

    private void setPhoneRgv() {

        mCreateNgv.setImageLoader(new GlideImageLoader());
        mCreateNgv.setIsEditMode(true);
        mCreateNgv.setSingleImageSize(105);
        mCreateNgv.setSingleImageRatio(0.8f);
        mCreateNgv.setMaxNum(5);
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
                .selectCount(5)
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
            //  Logger.d("notifyData : " + albumFile.getPath());
            resultList.add(nineGirdData);
        }

        // Logger.d("notify  result list :" + resultList.size());
        mCreateNgv.addData(resultList);

        for (int i = 0; i < mAlbumFiles.size(); i++) {
            String path = mAlbumFiles.get(i).getPath();

            File tempFile = new File(path.trim());

            String fileName = tempFile.getName();
            mUploadFileList.add(new UploadFile(0, tempFile, fileName));
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

                List<XYUploadEntity> xyUploadEntityList = new ArrayList<>();
                for (int i = 0; i < mUploadFileList.size(); i++) {
                    XYUploadEntity xyUploadEntity = new XYUploadEntity();
                    xyUploadEntity.setFileData(FileToBase64.best64(mUploadFileList.get(i)));
                    xyUploadEntity.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    xyUploadEntity.setFileName(mUploadFileList.get(i).getKey());
                    xyUploadEntity.setSource("Member");
                }

                if (isCreateNew) {
                    CreateFamilyBean createFamilyBean = new CreateFamilyBean();
                    createFamilyBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    createFamilyBean.setName(nameStr);
                    List<CreateFamilyBean.FamilyMemberBean> familyMemberBeans = new ArrayList<>();
                    CreateFamilyBean.FamilyMemberBean familyMemberBean = new CreateFamilyBean.FamilyMemberBean();
                    familyMemberBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    familyMemberBean.setRole("1");
                    familyMemberBeans.add(familyMemberBean);
                    createFamilyBean.setFamilyMember(familyMemberBeans);
                    mPresenter.createFamilyParameter(createFamilyBean);

                } else {
                    UpdateFamilyBean updateFamilyBean = new UpdateFamilyBean();
                    updateFamilyBean.setBanner(listToString(mUploadFileList));
                    updateFamilyBean.setDescribe(desStr);
                    updateFamilyBean.setName(nameStr);
                    Logger.d("MineCrateFamilyActivity ===>>>  update  ===>>> " + new Gson().toJson(updateFamilyBean));
                    mPresenter.updateFamilyParameter(updateFamilyBean);
                }

                mPresenter.uploadListParameter(xyUploadEntityList);
                break;
        }
    }


    public static String listToString(List<UploadFile> mList) {
        final String SEPARATOR = "|";
        // mList = Arrays.asList("AAA", "BBB", "CCC");
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
        } else return "List is null!!!";
    }
}
