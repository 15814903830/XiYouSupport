package com.chengfan.xiyou.ui.mine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.androidio.AudioRecorder;
import com.chengfan.xiyou.androidio.Player;
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
import com.chengfan.xiyou.ui.UIApplication;
import com.chengfan.xiyou.ui.dialog.MineDataDialog;
import com.chengfan.xiyou.ui.dialog.MineTimeDialog;
import com.chengfan.xiyou.ui.mine.order.AddPlayClassifyBase;
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
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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
public class MineAddPlayActivity extends BaseActivity<MineAddPlayContract.View, MineAddPlayPresenterImpl> implements MineAddPlayContract.View, HttpCallBack {
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
    @BindView(R.id.add_type_tv77)
    TextView mAdd66;
    @BindView(R.id.add_type_tv66)
    TextView mAdd77;
    @BindView(R.id.tv_luying)//开始录制
            TextView luyingstart;
    @BindView(R.id.tv_finish)//录制完成
            TextView finish;
    @BindView(R.id.tv_secondstart)//秒
            TextView secondstart;
    @BindView(R.id.tv_secondover)//秒
            TextView secondover;
    @BindView(R.id.ll_one)
    LinearLayout llone;
    @BindView(R.id.ll_two)
    LinearLayout lltwo;
    @BindView(R.id.ll_three)
    LinearLayout llthree;
    @BindView(R.id.pb_plan)//进度条
            ProgressBar plan;
    @BindView(R.id.iv_bfzt)//播放暂停图片按钮
            ImageView imgbfzt;
    @BindView(R.id.tv_new)//重新录制
            TextView tvnew;

    private static final String LOG_TAG = "AudioRecordTest";
    //语音文件保存路径
    private String FileName = null;
    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;


    String titleStr, subjectStr, startTimeStr, endTimeStr, priceStr, remarkStr, weekStr;
    private AddPlayClassifyBase addPlayClassifyBase;
    private ArrayList<AlbumFile> mAlbumFiles;
    UploadFile mUploadFile;
    private FileBase fileBase;//上传图片
    private List<String> list;
    private HttpCallBack mHttpCallBack;
    List<MineAddPlayEntity> mAddPlayEntityList;
    MineDataDialog mMineDataDialog;
    MineTimeDialog mMineTimeDialog;
    String mString = "";
    UpdateEntity response;
    BaseNiceDialog mBaseNiceDialog;
    List<String> regionAas = new ArrayList<>();
    List<String> regionAas0 = new ArrayList<>();
    private CountDownTimer timer,timer1;
    private int luztime=0;
    int str;
    private boolean img=true;
    AudioRecorder audioRecorder;
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
        getClassify();
        mXyMiddleTv.setText("新增陪玩");
        mXyMoreTv.setText("完成");
        mHttpCallBack = this;
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
        //设置sdcard的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/audiorecordtest.3gp";
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.xy_back_btn, R.id.xy_more_tv, R.id.add_type_rl, R.id.add_data_rl,
            R.id.add_time_rl, R.id.add_update_ll, R.id.add_type_rl77, R.id.add_type_rl66,
            R.id.tv_luying,R.id.ll_two,R.id.iv_bfzt,R.id.tv_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.xy_more_tv:
                xymoretv();
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
            case R.id.add_type_rl66:
                if (regionAas0.size() == 0) {
                    Toast.makeText(this, "该游戏未有大区选项", Toast.LENGTH_SHORT).show();
                } else {
                    initregionOptionPicker2();//大区
                }
                break;
            case R.id.add_type_rl77:
                if (regionAas.size() == 0) {
                    Toast.makeText(this, "该游戏未有段位选项", Toast.LENGTH_SHORT).show();
                } else {
                    initregionOptionPicker();//段位
                }
                break;
            case R.id.tv_luying:
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                            return;
                        }
                    }
                }
                llone.setEnabled(false);
                luyingstart.setText("s后开始录音，请做好准备");
                /** 倒计时60秒，一次1秒 */
                timer = new CountDownTimer(3 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondstart.setText(millisUntilFinished / 1000 + "");
                    }

                    @Override
                    public void onFinish() {
                        timeronFinish();
                    }

                }.start();
                break;
            case R.id.ll_two://点击立即完成
                llthree.setVisibility(View.VISIBLE);
                lltwo.setVisibility(View.GONE);
                llone.setVisibility(View.GONE);
                finish.setText("录制完成"+luztime+"s");
                break;
            case R.id.iv_bfzt://播放暂停
                if (img){
                    imgbfzt.setSelected(true);
                    img=false;
                }else {
                    imgbfzt.setSelected(false);
                    img=true;
                }
                break;
            case R.id.tv_new://重新录制
                llthree.setVisibility(View.VISIBLE);
                timer1.cancel();
                timeronFinish();
                break;
        }
    }

//    //开始录音
//    public void  startmusic(){
//        // TODO Auto-generated method stub
//        mRecorder = new MediaRecorder();
//        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mRecorder.setOutputFile(FileName);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        try {
//            mRecorder.prepare();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "prepare() failed");
//        }
//        mRecorder.start();
//    }
//
//    //停止录音
//
//    public void  stopmusic(){
//        mRecorder.stop();
//        mRecorder.release();
//        mRecorder = null;
//    }
//
//    //播放录音
//    public void  playmusic(){
//        // TODO Auto-generated method stub
//        mPlayer = new MediaPlayer();
//        try{
//            mPlayer.setDataSource(FileName);
//            mPlayer.prepare();
//            mPlayer.start();
//        }catch(IOException e){
//            Log.e(LOG_TAG,"播放失败");
//        }
//    }
//
//
//    //停止播放录音
//    public void  stopplaymusic(){
//        mPlayer.release();
//        mPlayer = null;
//    }



    private void xymoretv() {
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
            mineAddBean.setImages(fileBase.getFilePath());
            mineAddBean.setRemark(remarkStr);



            String str[] = weekStr.split(",");

            for (int i = 0; i < str.length; i++) {
                if (str[i]=="星期一"){
                    if (i==str.length){
                        str[i]="1";
                    }else {
                        str[i]="1,";
                    }
                }

                if (str[i]=="星期二"){
                    if (i==str.length){
                        str[i]="2";
                    }else {
                        str[i]="2,";
                    }
                }

                if (str[i]=="星期三"){
                    if (i==str.length){
                        str[i]="3";
                    }else {
                        str[i]="3,";
                    }
                }

                if (str[i]=="星期四"){
                    if (i==str.length){
                        str[i]="4";
                    }else {
                        str[i]="4,";
                    }
                }

                if (str[i]=="星期五"){
                    if (i==str.length){
                        str[i]="5";
                    }else {
                        str[i]="5,";
                    }
                }

                if (str[i]=="星期六"){
                    if (i==str.length){
                        str[i]="6";
                    }else {
                        str[i]="6,";
                    }
                }
                if (str[i]=="星期日"){
                    if (i==str.length){
                        str[i]="0";
                    }else {
                        str[i]="0,";
                    }
                }

            }
            // 遍历
            StringBuffer str5 = new StringBuffer();
            for (String s : str) {
                str5.append(s);
            }
            Log.e("mString", str5.toString());

//            mineAddBean.setWeekDay(str5.toString());
//            mineAddBean.setSubjectId(subjectStr);
//            mineAddBean.setServiceStartTime(startTimeStr);
//            mineAddBean.setServiceEndTime(endTimeStr);
//            mineAddBean.setAreaTitle(mAdd77.getText().toString());
//            mineAddBean.setGradeTitle(mAdd66.getText().toString());
//            mPresenter.mineAddParameter(mineAddBean);
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
    private void timeronFinish() {
        llone.setVisibility(View.GONE);
        lltwo.setVisibility(View.VISIBLE);
        timer1 = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.cancel();
                secondover.setText((30-(millisUntilFinished / 1000)) + "s");
                plan.setMax(30);
                plan.setProgress((int)(30-(millisUntilFinished / 1000)));
                luztime=(int)(30-(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                llthree.setVisibility(View.VISIBLE);
                lltwo.setVisibility(View.GONE);
                finish.setText("录制完成"+luztime+"s");
            }
        }.start();
    }
    private void notifyData(ArrayList<AlbumFile> result) {
        String path = mAlbumFiles.get(0).getPath();
        File tempFile = new File(path.trim());
        String fileName = tempFile.getName();
        mUploadFile = new UploadFile(0, tempFile, fileName);
        ImageLoaderManager.getInstance().showImage(mAddContentImage, result.get(0).getPath());
        showLoading();
        postimg(FileToBase64.best64(mUploadFile));

    }

    private void initCustomOptionPicker() {
        OptionsPickerView pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = addPlayClassifyBase.getSubject().get(options1).getTitle();
                subjectStr = mAddPlayEntityList.get(options1).getId() + "";
                Log.e("str", "" + mAddPlayEntityList.get(options1).getTitle());
                mAddTypeTv.setText(str);
                Logger.d(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        str = options1;
                        try {
                            String gradetitles = addPlayClassifyBase.getSubject().get(options1).getGradeTitles().split(":")[1];
                            regionAas = Arrays.asList(gradetitles.split(","));

                            String gettitle = addPlayClassifyBase.getSubject().get(options1).getAreaTitles().split(":")[1];
                            regionAas0 = Arrays.asList(gettitle.split(","));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvCustomOptions.setPicker(list);
        pvCustomOptions.setSelectOptions(0);
        pvCustomOptions.show();
    }

    private void initregionOptionPicker2() {//大区
        OptionsPickerView pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = regionAas0.get(options1);
                mAdd77.setText(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                })
                .build();
        pvCustomOptions.setPicker(regionAas0);
        pvCustomOptions.setSelectOptions(0);
        pvCustomOptions.show();
    }

    private void initregionOptionPicker() {//段位
        OptionsPickerView pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = regionAas.get(options1);
                mAdd66.setText(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                })
                .build();
        pvCustomOptions.setPicker(regionAas);
        pvCustomOptions.setSelectOptions(0);
        pvCustomOptions.show();
    }


    private void getClassify() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet("http://xy.gx11.cn/api/Member/ListByArea?isRecommend=true&subjectId=10048&newsCode=1101&page=1&limit=18", mHttpCallBack, 2);
            }
        }).start();
    }

    @Override
    public void mineAddLoad(BaseApiResponse response) {
        Log.e("mineAddLoad", response.getMsg());
        Toast.makeText(this, response.getMsg(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void uploadLoad(UpdateEntity response) {
        this.response = response;
        Log.e("uploadLoad", response.getMsg());
        ToastUtil.show(response.getMsg());
        mBaseNiceDialog.dismiss();
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

        switch (requestId) {
            case 0:
                try {
                    fileBase = JSON.parseObject(response, FileBase.class);
                    ToastUtil.show(fileBase.getMsg());
                    mBaseNiceDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    addPlayClassifyBase = JSON.parseObject(response, AddPlayClassifyBase.class);
                    list = new ArrayList<>();

                    for (int i = 0; i < addPlayClassifyBase.getSubject().size(); i++) {
                        list.add(addPlayClassifyBase.getSubject().get(i).getTitle());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("response", response);
                break;
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
