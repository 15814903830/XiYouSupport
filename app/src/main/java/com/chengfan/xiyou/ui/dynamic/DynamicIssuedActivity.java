package com.chengfan.xiyou.ui.dynamic;

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
import com.chengfan.xiyou.domain.contract.DynamicIssuedContract;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.chengfan.xiyou.domain.presenter.DynamicIssuedPresenterImpl;
import com.chengfan.xiyou.utils.GlideImageLoader;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.widget.ninegridview.NineGirdImageContainer;
import com.chengfan.xiyou.widget.ninegridview.NineGridBean;
import com.chengfan.xiyou.widget.ninegridview.NineGridView;
import com.github.zackratos.ultimatebar.UltimateBar;
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
 * @DATE : 2019-07-09/12:03
 * @Description: 发布动态
 */
public class DynamicIssuedActivity extends BaseActivity<DynamicIssuedContract.View, DynamicIssuedPresenterImpl> implements DynamicIssuedContract.View {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.issued_des_et)
    EditText mIssuedDesEt;
    @BindView(R.id.issued_ngv)
    NineGridView mIssuedNgv;
    String content;

    private ArrayList<AlbumFile> mAlbumFiles;
    private List<UploadFile> mUploadFileList;
    List<NineGridBean> resultList;
    List<UploadEntity> mUploadEntityList;
    int requestNum = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_issued);
        ButterKnife.bind(this);


        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText(getResources().getText(R.string.issued_title_txt));
        mXyMoreTv.setText(getResources().getText(R.string.issued_title_done_txt));
        mXyMoreTv.setTextColor(getResources().getColor(R.color.color_D1AE81));

        mAlbumFiles = new ArrayList<>();
        mUploadFileList = new ArrayList<>();
        resultList = new ArrayList<>();
        mUploadEntityList = new ArrayList<>();

        setPhoneRgv();
    }

    @Override
    public void publistLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void uploadLoad(UploadEntity result) {
        mUploadEntityList.add(result);
        Logger.d("DynamicIssuedActivity==>>" + mUploadEntityList.size());
        if (mUploadEntityList.size() == mUploadFileList.size())
            mPresenter.publishParameter(content, mUploadEntityList);
    }

    private void setPhoneRgv() {

        //设置图片加载器，这个是必须的，不然图片无法显示
        mIssuedNgv.setImageLoader(new GlideImageLoader());
        //设置显示列数，默认3列
        //mIssuedNgv.setColumnCount(3);
        //设置是否为编辑模式，默认为false
        mIssuedNgv.setIsEditMode(true);
        //设置单张图片显示时的尺寸，默认100dp
        mIssuedNgv.setSingleImageSize(105);
        //设置单张图片显示时的宽高比，默认1.0f
        mIssuedNgv.setSingleImageRatio(0.8f);
        //设置最大显示数量，默认9张
        mIssuedNgv.setMaxNum(9);
        //设置图片显示间隔大小，默认3dp
        //  mIssuedNgv.setSpcaeSize(4);
        //设置删除图片
        //   mIssuedNgv.setIcDeleteResId(R.drawable.ic_ninegrid_image_delete);
        //设置删除图片与父视图的大小比例，默认0.35f
        mIssuedNgv.setRatioOfDeleteIcon(0.2f);
        //设置“+”号的图片
        //mIssuedNgv.setIcAddMoreResId(R.drawable.ic_ninegrid_addmore);
        //设置各类点击监听
        mIssuedNgv.setOnItemClickListener(new NineGridView.onItemClickListener() {
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

    /**
     * Select picture, from album.
     */
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

    /**
     * Preview image, to album.
     */
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
        mIssuedNgv.addData(resultList);

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
                content = mIssuedDesEt.getText().toString().trim();
                if (content.equals(""))
                    ToastUtil.show("与我们分享您的故事吧");
                else if (mUploadFileList.size() < 0)
                    ToastUtil.show("请选择图片");
                else {
                    for (UploadFile uploadFile : mUploadFileList) {
                        mPresenter.uploadParameter(uploadFile);
                        requestNum++;
                    }
                }
                break;
        }
    }


}
