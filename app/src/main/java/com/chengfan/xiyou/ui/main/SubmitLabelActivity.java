package com.chengfan.xiyou.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.mine.order.FileBase;
import com.chengfan.xiyou.ui.mine.order.MyLableBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.GlideImageLoader;
import com.chengfan.xiyou.utils.KeyBoardUtils;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.chengfan.xiyou.widget.ninegridview.NineGirdImageContainer;
import com.chengfan.xiyou.widget.ninegridview.NineGridBean;
import com.chengfan.xiyou.widget.ninegridview.NineGridView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.tool.ToastUtil;

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

public class SubmitLabelActivity extends AppCompatActivity implements HttpCallBack, LableAdapter.defaultAddress {

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
    List<NineGridBean> resultList;
    List<UploadEntity> mUploadEntityList;
    private ArrayList<AlbumFile> mAlbumFiles;
    private HttpCallBack mHttpCallBack;
    private ImageView ivBackWebView;
    private RecyclerView rvLable;
    private List<LableBase> lableBases;
    private LableAdapter lableAdapter;
    List<String> lableitemlist = new ArrayList<>();
    private EditText etContent;
    private int type = -1;  //type0为图片，1为视频
    private NineGridView mIssuedNgv;
    private String imgdata="";
    private FileBase fileBase;//上传图片

    private static final String LOG_TAG = "AudioRecordTest";
    //语音文件保存路径
    private String FileName = null;
    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

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
    private CountDownTimer timer,timer1;
    private int luztime=0;
    private boolean img=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_label);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        initView();
        getlable();
        setPhoneRgv();
        mAlbumFiles = new ArrayList<>();
        resultList = new ArrayList<>();
        mUploadEntityList = new ArrayList<>();
        //设置sdcard的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/audiorecordtest.3gp";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.tv_luying,R.id.ll_two,R.id.iv_bfzt,R.id.tv_new,R.id.btn_put,R.id.iv_back_web_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_web_view:
                finish();
                KeyBoardUtils.closeKeybord(etContent,this);
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
                /** 倒计时3秒，一次1秒 */
                timer = new CountDownTimer(3 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondstart.setText(millisUntilFinished / 1000 + "");
                    }

                    @Override
                    public void onFinish() {
                        timeronFinish();
                        startmusic();
                    }

                }.start();
                break;
            case R.id.ll_two://点击立即完成
                llthree.setVisibility(View.VISIBLE);
                lltwo.setVisibility(View.GONE);
                llone.setVisibility(View.GONE);
                finish.setText("录制完成"+luztime+"s");
                stopmusic();
                break;
            case R.id.iv_bfzt://播放暂停
                if (img){
                    imgbfzt.setSelected(true);
                    img=false;
                    playmusic();
                }else {
                    imgbfzt.setSelected(false);
                    img=true;
                    stopplaymusic();
                }
                break;
            case R.id.tv_new://重新录制
                llthree.setVisibility(View.VISIBLE);
                timer1.cancel();
                timer.cancel();
                //timeronFinish();
                llone.setVisibility(View.VISIBLE);
                timer.start();
                break;
            case R.id.btn_put://提交

                if (lableitemlist.size()<4){
                    Toast.makeText(this, "请选择4个标签", Toast.LENGTH_SHORT).show();
                }else {
                    if (lableitemlist==null){
                        Toast.makeText(this, "请选择标签", Toast.LENGTH_SHORT).show();
                    }else if (etContent.getText().toString().equals("")){
                        Toast.makeText(this, "请完善自我介绍", Toast.LENGTH_SHORT).show();
                    }else if (imgdata.equals("")){
                        Toast.makeText(this, "请选择上传图片", Toast.LENGTH_SHORT).show();
                    }else {//提交
                        postvodeo(FileName);
                    }
                }
                break;

        }
    }
    //开始录音
    public void  startmusic(){
        // TODO Auto-generated method stub
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(FileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    //停止录音

    public void  stopmusic(){
        if (mRecorder!=null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    //播放录音
    public void  playmusic(){
        // TODO Auto-generated method stub
        mPlayer = new MediaPlayer();
        try{
            mPlayer.setDataSource(FileName);
            mPlayer.prepare();
            mPlayer.start();
        }catch(IOException e){
            Log.e(LOG_TAG,"播放失败");
        }
    }


    //停止播放录音
    public void  stopplaymusic(){
        mPlayer.release();
        mPlayer = null;
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

    private void initView() {
        mHttpCallBack = this;
        ivBackWebView = findViewById(R.id.iv_back_web_view);
        rvLable = findViewById(R.id.rv_lable);
        etContent = findViewById(R.id.et_content);
        mIssuedNgv=findViewById(R.id.issued_ngv);
    }

    @Override
    public void onResponse(String response, int requestId) {
        Message message = mHandler.obtainMessage();
        message.what = requestId;
        message.obj = response;
        mHandler.sendMessage(message);
    }

    @Override
    public void onHandlerMessageCallback(String response, int requestId) {
        Log.e("responslableBases", response);
        switch (requestId) {
            case 0:
                Log.e("responslableBases1", response);
                lableBases = JSONArray.parseArray(response, LableBase.class);
                GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
                rvLable.setLayoutManager(layoutManager);
                lableAdapter = new LableAdapter(this, lableBases, this);
                rvLable.setAdapter(lableAdapter);
                lableAdapter.notifyDataSetChanged();
                break;
            case 1:
                try {
                    fileBase = JSON.parseObject(response, FileBase.class);
                    imgdata=imgdata+fileBase.getFilePath()+"|";
                    Log.e("Basesimgdata1", imgdata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                  FileBase  fileBasess = JSON.parseObject(response, FileBase.class);
                  String lable = "";
                  for (int i=0;i<lableitemlist.size();i++){
                      if (i==lableitemlist.size()-1){
                          lable=lable+lableitemlist.get(i);
                      }else {
                          lable=lable+lableitemlist.get(i)+",";
                      }
                  }
                  Log.e("lable",lable);
                postlabel(lable,etContent.getText().toString(),imgdata,fileBasess.getFilePath());
                break;
            case 3:
                MyLableBase myLableBase = JSON.parseObject(response, MyLableBase.class);
                if (myLableBase.isSuc()){
                    finish();
                }else {
                    Toast.makeText(this, myLableBase.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void getlable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet(APIContents.HOST + "/api/Common/Lables", mHttpCallBack, 0);
            }
        }).start();
    }

    @Override
    public void lableitem(int i) {

        lableitemlist.add("" + i);
    }

    @Override
    public void lableitemremove(int i) {
        lableitemlist.remove(""+i);
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
                if (type == 0) {
                    selectImage();
                } else if (type == 1) {
                    chooseVideo();
                } else {
                    showDynamicType();
                }
            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                previewImage(position);
            }

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                mAlbumFiles.remove(position);
                if (mAlbumFiles.isEmpty()) {
                    type = -1;
                }
            }
        });
    }


    /**
     * 显示动态类型
     */
    private void showDynamicType() {
        selectImage();
    }

    /**
     * Select picture, from album.
     */
    private void selectImage() {
        type = 0;
        Album.image(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(9)
                .checkedList(mAlbumFiles)
                .widget(Widget.newDarkBuilder(this).build())
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

    private void chooseVideo() {
        if (!mAlbumFiles.isEmpty()) {
            return;
        }
        type = 1;
        Album.video(this)
                .multipleChoice()
                .camera(true)
                .columnCount(2)
                .selectCount(1)
                .checkedList(mAlbumFiles)
                .widget(Widget.newDarkBuilder(this).build())
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
                    .widget(Widget.newDarkBuilder(this).build())
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
        if (result.isEmpty() && mAlbumFiles.isEmpty()) {
            type = -1;
        }
        resultList.clear();
        for (AlbumFile albumFile : result) {
            NineGridBean nineGirdData = new NineGridBean(albumFile.getPath());
            //  Logger.d("notifyData : " + albumFile.getPath());
            resultList.add(nineGirdData);
        }

        for (int i=0;i<result.size();i++){
            postimg(result.get(i).getPath());
        }
        // Logger.d("notify  result list :" + resultList.size());
        mIssuedNgv.addData(resultList);
        Toast.makeText(this, "正在压缩图片上传,请等待", Toast.LENGTH_SHORT).show();
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
                    OkHttpUtils.doPostJson(APIContents.UPLOAD_FILE, jsonObject.toString(), mHttpCallBack, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //提交标签
    private void postlabel(final String applyLable, final String introduction, final String lableAudio, final String lableVideo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", AppData.getString(AppData.Keys.AD_USER_ID));
                    jsonObject.put("applyLable", applyLable);
                    jsonObject.put("introduction", introduction);
                    jsonObject.put("lableAudio", lableAudio);
                    jsonObject.put("lableVideo", lableVideo);
                    Log.e("jsonObject",jsonObject.toString());
                    OkHttpUtils.doPostJson(APIContents.HOST+"/api/Account/LableAuthentication", jsonObject.toString(), mHttpCallBack, 3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }





    private void postvodeo(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("source", "AccompanyPlayNews");
                    jsonObject.put("fileName", "png");
                    jsonObject.put("fileData", data);
                    OkHttpUtils.doPostJson(APIContents.UPLOAD_FILE, jsonObject.toString(), mHttpCallBack, 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyBoardUtils.closeKeybord(etContent,this);
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



}
