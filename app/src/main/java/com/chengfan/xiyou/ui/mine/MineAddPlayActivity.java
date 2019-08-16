package com.chengfan.xiyou.ui.mine;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.contract.MineAddPlayContract;
import com.chengfan.xiyou.domain.model.entity.GetMemberInfoEntity;
import com.chengfan.xiyou.domain.model.entity.MineAddBean;
import com.chengfan.xiyou.domain.model.entity.MineAddPlayEntity;
import com.chengfan.xiyou.domain.model.entity.UpdateEntity;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.chengfan.xiyou.domain.presenter.MineAddPlayPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.dialog.MineDataDialog;
import com.chengfan.xiyou.ui.dialog.MineTimeDialog;
import com.chengfan.xiyou.ui.mine.order.FileBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.widget.pickerview.builder.OptionsPickerBuilder;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectChangeListener;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectListener;
import com.chengfan.xiyou.widget.pickerview.view.OptionsPickerView;
import com.chengfan.xiyou.widget.pickerview.view.TimePickerView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.network.zrequest.response.AbstractUploadResponse;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-07/16:22
 * @Description: 新增陪玩
 */
public class MineAddPlayActivity extends BaseActivity<MineAddPlayContract.View, MineAddPlayPresenterImpl> implements MineAddPlayContract.View , HttpCallBack {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.add_content_image)
    ImageView mAddContentImage;
    @BindView(R.id.add_game_name_et)
    EditText mAddGameNameEt;
    @BindView(R.id.add_type_tv)
    TextView mAddTypeTv;
    @BindView(R.id.add_data_tv)
    TextView mAddDataTv;
    @BindView(R.id.add_price_et)
    EditText mAddPriceEt;
    @BindView(R.id.add_introduce_et)
    EditText mAddIntroduceEt;
    @BindView(R.id.add_select_iv)
    ImageView mAddSelectIv;
    @BindView(R.id.add_select_tv)
    TextView mAddSelectTv;
    @BindView(R.id.add_time_tv)
    TextView mAddTimeTv;

    String titleStr, subjectStr, startTimeStr, endTimeStr, priceStr, remarkStr, weekStr;

    private ArrayList<AlbumFile> mAlbumFiles;
    UploadFile mUploadFile;
    private FileBase fileBase;//上传图片

    private HttpCallBack mHttpCallBack;
    List<MineAddPlayEntity> mAddPlayEntityList;
    MineDataDialog mMineDataDialog;
    MineTimeDialog mMineTimeDialog;
    String mString="";
    UpdateEntity response;
    BaseNiceDialog mBaseNiceDialog;

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
        setContentView(R.layout.activity_mine_add_play);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText("新增陪玩");
        mXyMoreTv.setText("完成");
        mHttpCallBack=this;
        mAddPlayEntityList = new ArrayList<>();


        mMineDataDialog = new MineDataDialog(this, R.style.dialog_bottom_full);
        mMineDataDialog.setMineDataListener(new MineDataDialog.MineDataListener() {
            @Override
            public void onMineDataListener(String data) {

                weekStr = data;
                mAddDataTv.setText(data);
                Logger.d("weak :" + data);
            }
        });


        mMineTimeDialog = new MineTimeDialog(this, R.style.dialog_bottom_full);
        mMineTimeDialog.setMineTimeListener(new MineTimeDialog.MineTimeListener() {
            @Override
            public void onMineTimeListener(String startTime, String endTime) {

                mAddTimeTv.setText(startTime + " 至 " + endTime);
                startTimeStr = startTime;
                endTimeStr = endTime;

            }
        });
        requestSubject();

    }


    @Override
    public void mineAddLoad(BaseApiResponse response) {
        Log.e("mineAddLoad",response.getMsg());
        Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void uploadLoad(UpdateEntity response) {
        this.response=response;
        Log.e("uploadLoad",response.getMsg());
        ToastUtil.show(response.getMsg());
        mBaseNiceDialog.dismiss();
    }

    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv, R.id.add_type_rl, R.id.add_data_rl, R.id.add_time_rl, R.id.add_update_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                priceStr = mAddPriceEt.getText().toString().trim();
                remarkStr = mAddIntroduceEt.getText().toString().trim();
                titleStr = mAddGameNameEt.getText().toString().trim();
                if (titleStr.equals("")) {
                    ToastUtil.show("请输入陪玩标题");
                } else if (remarkStr.equals("")) {
                    ToastUtil.show("请添加描述");
                } else if (priceStr.equals("")) {
                    ToastUtil.show("请填写单价");
                } else if (startTimeStr.equals("")) {
                    ToastUtil.show("请选择接单时间");
                } else if (subjectStr.equals("")) {
                    ToastUtil.show("请选择陪玩类型");
                } else if (weekStr.equals("")) {
                    ToastUtil.show("请选择接单日期");
                } else {

                    MineAddBean mineAddBean = new MineAddBean();
                    mineAddBean.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
                    mineAddBean.setPrice(priceStr);
                    mineAddBean.setTitle(titleStr);
                    mineAddBean.setImages(response.getFilePath());
                    mineAddBean.setRemark(remarkStr);
                    String str[]=weekStr.split(",");
                    for (int i = 0; i <str.length ; i++) {
                        switch (str[i]) {
                            case "星期一":
                                if (i==str.length-1){
                                    str[i]="1";
                                }else {
                                    str[i]="1,";
                                }
                                break;
                            case "星期二":
                                if (i==str.length-1){
                                    str[i]="2";
                                }else {
                                    str[i]="2,";
                                }
                                break;
                            case "星期三":
                                if (i==str.length-1){
                                    str[i]="3";
                                }else {
                                    str[i]="3,";
                                }
                                break;
                            case "星期四":
                                if (i==str.length-1){
                                    str[i]="4";
                                }else {
                                    str[i]="4,";
                                }
                                break;
                            case "星期五":
                                if (i==str.length-1){
                                    str[i]="5";
                                }else {
                                    str[i]="5,";
                                }
                                break;
                            case "星期六":
                                if (i==str.length-1){
                                    str[i]="6";
                                }else {
                                    str[i]="6,";
                                }
                                break;
                            case "星期日":
                                if (i==str.length-1){
                                    str[i]="7";
                                }else {
                                    str[i]="7,";
                                }
                                break;
                        }
                    }

                    // 遍历
                    StringBuffer str5 = new StringBuffer();
                    for (String s : str) {
                        str5.append(s);
                    }
                    Log.e("mString",str5.toString());
                    mineAddBean.setWeekDay(str5.toString());
                    mineAddBean.setSubjectId(subjectStr);
                    mineAddBean.setServiceStartTime(startTimeStr);
                    mineAddBean.setServiceEndTime(endTimeStr);
                    mPresenter.mineAddParameter(mineAddBean);


                }
                break;
            case R.id.add_type_rl:
                initCustomOptionPicker();
                break;
            case R.id.add_data_rl:
                mMineDataDialog.show();


                break;
            case R.id.add_time_rl:
                mMineTimeDialog.show();

                break;
            case R.id.add_update_ll:
                selectImage();
                break;
        }
    }


    private void selectImage() {
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(1)
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

    private void notifyData(ArrayList<AlbumFile> result) {
        String path = mAlbumFiles.get(0).getPath();

        File tempFile = new File(path.trim());

        String fileName = tempFile.getName();

        mUploadFile = new UploadFile(0, tempFile, fileName);
        ImageLoaderManager.getInstance().showImage(mAddContentImage, result.get(0).getPath());

        showLoading();
//        XYUploadEntity xyUploadEntity = new XYUploadEntity();
//        xyUploadEntity.setFileData(FileToBase64.best64(mUploadFile));
//        xyUploadEntity.setMemberId(AppData.getString(AppData.Keys.AD_USER_ID));
//        xyUploadEntity.setFileName("png");
//        xyUploadEntity.setSource("AccompanyPlayNews");
//        mPresenter.mineUploadParameter(xyUploadEntity);

        postimg(FileToBase64.best64(mUploadFile));

    }

    private void initCustomOptionPicker() {

        OptionsPickerView pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = mAddPlayEntityList.get(options1).getTitle();
                subjectStr = mAddPlayEntityList.get(options1).getId() + "";
                mAddTypeTv.setText(str);
                Logger.d(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = options1 + " ";
                        Logger.d(str);
                        subjectStr = str;
                        mAddTypeTv.setText(str);
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvCustomOptions.setPicker(mAddPlayEntityList);
        pvCustomOptions.setSelectOptions(0);
        pvCustomOptions.show();
    }


    private void requestSubject() {
        HttpRequest.get(APIContents.AccompanyPlaySubject)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Type type = new TypeToken<List<MineAddPlayEntity>>() {
                        }.getType();
                        mAddPlayEntityList = new Gson().fromJson(result, type);
                    }
                });
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

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandlere.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandlere.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        try {
            fileBase = JSON.parseObject(response, FileBase.class);
            ToastUtil.show(fileBase.getMsg());
            mBaseNiceDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
}
