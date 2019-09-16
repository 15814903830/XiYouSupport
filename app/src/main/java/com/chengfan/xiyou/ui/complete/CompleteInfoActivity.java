package com.chengfan.xiyou.ui.complete;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.CompleterInfoContract;
import com.chengfan.xiyou.domain.model.entity.JsonBean;
import com.chengfan.xiyou.domain.model.entity.LoginEntity;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.SystemConfigEntity;
import com.chengfan.xiyou.domain.model.entity.UploadEntity;
import com.chengfan.xiyou.domain.model.response.LoginResponse;
import com.chengfan.xiyou.domain.presenter.CompleterInfoPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.MainActivity;
import com.chengfan.xiyou.ui.login.LoginActivity;
import com.chengfan.xiyou.ui.mine.order.FileBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
import com.chengfan.xiyou.utils.FontHelper;
import com.chengfan.xiyou.utils.GetJsonDataUtil;
import com.chengfan.xiyou.utils.UserStorage;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.widget.pickerview.builder.OptionsPickerBuilder;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectChangeListener;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectListener;
import com.chengfan.xiyou.widget.pickerview.view.OptionsPickerView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONArray;
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
import io.rong.imlib.model.UserInfo;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/16:56
 * @Description: ~\(≧▽≦)完善信息
 */
public class CompleteInfoActivity
        extends BaseActivity<CompleterInfoContract.View, CompleterInfoPresenterImpl>
        implements CompleterInfoContract.View , HttpCallBack {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.complete_head_civ)
    CircleImageView mCompleteHeadCiv;
    @BindView(R.id.complete_name_et)
    EditText mCompleteNameEt;
    @BindView(R.id.complete_age_et)
    EditText mCompleteAgeEt;
    @BindView(R.id.complete_city_tv)
    TextView mCompleteCityTv;
    @BindView(R.id.complete_face_tv)
    TextView mCompleteFaceTv;
    @BindView(R.id.complete_wx_et)
    EditText mCompleteWxEt;
    @BindView(R.id.complete_job_tv)
    TextView mCompleteJobTv;
    @BindView(R.id.xy_back_btn)
    ImageView mXyBackBtn;
    @BindView(R.id.xy_more_tv)
    TextView mXyMoreTv;
    @BindView(R.id.xy_tb)
    Toolbar mXyTb;
    @BindView(R.id.complete_z_head_tv)
    TextView mCompleteZHeadTv;
    @BindView(R.id.complete_head_rl)
    RelativeLayout mCompleteHeadRl;
    @BindView(R.id.complete_nick_name_tv)
    TextView mCompleteNickNameTv;
    @BindView(R.id.complete_z_age_tv)
    TextView mCompleteZAgeTv;
    @BindView(R.id.complete_z_city_tv)
    TextView mCompleteZCityTv;
    @BindView(R.id.complete_city_rl)
    RelativeLayout mCompleteCityRl;
    @BindView(R.id.complete_z_face_tv)
    TextView mCompleteZFaceTv;
    @BindView(R.id.complete_face_rl)
    RelativeLayout mCompleteFaceRl;
    @BindView(R.id.complete_z_wx_tv)
    TextView mCompleteZWxTv;
    @BindView(R.id.complete_z_job_tv)
    TextView mCompleteZJobTv;
    @BindView(R.id.complete_job_rl)
    RelativeLayout mCompleteJobRl;
    @BindView(R.id.complete_info_layout)
    LinearLayout mCompleteInfoLayout;
    @BindView(R.id.complete_save_btn)
    Button mCompleteSaveBtn;
    private ArrayList<AlbumFile> mAlbumFiles;


    OptionsPickerView pvOptions;
    OptionsPickerView pvJobCustomOptions;
    OptionsPickerView pvFaceOptions;
    private List<JsonBean> mJsonBeanArrayList = new ArrayList<>();
    private ArrayList<ArrayList<JsonBean.CityBean>> mCityList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private FileBase fileBase;
    private LoginResponse loginResponse;


    private ArrayList<String> shenGaoList = new ArrayList<>();
    private ArrayList<String> tiZhongList = new ArrayList<>();
    private ArrayList<String> faceList = new ArrayList<>();

    private ArrayList<String> job = new ArrayList<>();

    String nameStr, ageStr, cityStr, faceStr, wxStr, jobStr;
    UploadFile mUploadFile;
    String areaName, areaCode;

    private HttpCallBack mHttpCallBack;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Logger.d("开始解析数据");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Logger.e("解析完成");
                    break;

                case MSG_LOAD_FAILED:
                    Logger.e("解析失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);
        ButterKnife.bind(this);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();

        mXyMiddleTv.setText(getResources().getText(R.string.complete_title_txt));

        mAlbumFiles = new ArrayList<>();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        String sex = AppData.getString(AppData.Keys.AD_SELECT_SEX);
        Log.e("sex",sex);
        if (sex.equals("1")) {
            mCompleteHeadCiv.setBackgroundResource(R.drawable.complete_nan);
        } else {
            mCompleteHeadCiv.setBackgroundResource(R.drawable.complete_nv);
        }

        FontHelper.applyFont(this, mXyMiddleTv, APPContents.FONTS_MEDIUM);
        FontHelper.applyFont(this, mCompleteZHeadTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteZAgeTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteZCityTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteZFaceTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteZFaceTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteZWxTv, APPContents.FONTS_REGULAR);
        FontHelper.applyFont(this, mCompleteZJobTv, APPContents.FONTS_REGULAR);

        requestSystemConfig();
        requestSystemConfigJob();

        mHttpCallBack=this;
    }

    @OnClick({R.id.xy_back_btn, R.id.complete_save_btn, R.id.complete_head_rl,
            R.id.complete_city_rl, R.id.complete_face_rl, R.id.complete_job_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.complete_save_btn:
                nameStr = mCompleteNameEt.getText().toString().trim();
                ageStr = mCompleteAgeEt.getText().toString().trim();
                wxStr = mCompleteWxEt.getText().toString().trim();
                if (nameStr.equals(""))
                    ToastUtil.show(R.string.complete_hint_name_txt);
                else if (ageStr.equals(""))
                    ToastUtil.show(R.string.complete_hint_age_txt);
                else if (cityStr.equals(""))
                    ToastUtil.show(R.string.complete_hint_city_txt);
                else if (faceStr.equals(""))
                    ToastUtil.show(R.string.complete_hint_face_txt);
                else if (jobStr.equals(""))
                    ToastUtil.show(R.string.complete_hint_job_txt);
                else if (fileBase == null)
                    ToastUtil.show("请选择您的头像");
                else
                    {
                    MemberInfoBean bean = new MemberInfoBean();
                    bean.setAge(ageStr);
                    bean.setAvatarUrl(fileBase.getFilePath());
                    Log.e("setAvatarUrl",bean.getAvatarUrl());
                    bean.setGender(AppData.getString(AppData.Keys.AD_SELECT_SEX));
                    bean.setNickname(nameStr);
                    bean.setAreaName(areaName);
                    bean.setAreaCode(areaCode);
                    bean.setExterior(faceStr);
                    bean.setWeiXinUId(wxStr);
                    bean.setJob(jobStr);
                    bean.setId(AppData.getString(AppData.Keys.AD_USER_ID));
                    mPresenter.saveInfoParameter(bean);
                }
                break;
            case R.id.complete_head_rl:
                selectImage();
                break;
            case R.id.complete_city_rl:
                showPickerView();
                break;
            case R.id.complete_face_rl:
                pvFaceOptions.show();
                break;
            case R.id.complete_job_rl:
                if (pvJobCustomOptions != null)
                    pvJobCustomOptions.show();
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
        postimg(FileToBase64.best64(result.get(0).getPath()));

        mUploadFile = new UploadFile(0, tempFile, fileName);
        ImageLoaderManager.getInstance().showImage(mCompleteHeadCiv, result.get(0).getPath());
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


    private void showPickerView() {

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String cityName = mCityList.get(options1).get(options2).getName();
                areaName = cityName;
                areaCode = mCityList.get(options1).get(options2).getId() + "";

                String tx = cityName + areaName;
                cityStr = tx;

                mCompleteCityTv.setText(tx);

            }
        })

                .setTitleText("选择城市")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(mJsonBeanArrayList, mCityList);
        pvOptions.show();
    }


    private void initJsonData() {//解析数据

        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        mJsonBeanArrayList = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<JsonBean.CityBean> cityList = new ArrayList<>();
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {
                cityList.add(jsonBean.get(i).getCity().get(c));
            }
            mCityList.add(cityList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    private void initFaceOptionsPicker() {
        pvFaceOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = shenGaoList.get(options1)
                        + " " + tiZhongList.get(options2)
                        + " " + faceList.get(options3);

                mCompleteFaceTv.setText(str);
                faceStr = str;
                Logger.d(str);
            }
        })
                .setTitleText("选择面貌")
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = options1 + " " + options2 + " " + options3;
                        Logger.d(str);
                        faceStr = str;
                        mCompleteFaceTv.setText(str);
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvFaceOptions.setNPicker(shenGaoList, tiZhongList, faceList);
        pvFaceOptions.setSelectOptions(0, 1, 1);


    }

    /* 获取工作*/
    private void initJobOptionPicker() {

        pvJobCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = job.get(options1);
                jobStr = str;
                mCompleteJobTv.setText(str);
                Logger.d(str);
            }
        })
                .setTitleText("选择职业")
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = options1 + " ";
                        Logger.d(str);
                        jobStr = str;
                        mCompleteJobTv.setText(str);
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvJobCustomOptions.setPicker(job);
        pvJobCustomOptions.setSelectOptions(0);

    }


    private void requestSystemConfig() {
        HttpRequest.get(APIContents.config)
                .execute(new AbstractResponse<List<SystemConfigEntity>>() {
                    @Override
                    public void onSuccess(List<SystemConfigEntity> result) {
                        for (SystemConfigEntity systemConfigEntity : result) {
                            String faceStr = systemConfigEntity.getValue();
                            if (faceStr != null) {
                                String[] strArr = faceStr.split("\\;");
                                for (String str : strArr) {
                                    Logger.d(str);
                                    //tiZhongList.add(str);
                                    if (systemConfigEntity.getId() == 4)
                                        shenGaoList.add(str);
                                    if (systemConfigEntity.getId() == 2)
                                        faceList.add(str);
                                    if (systemConfigEntity.getId() == 5)
                                        tiZhongList.add(str);
                                }
                            }
                        }
                        initFaceOptionsPicker();
                    }


                });
    }

    private void requestSystemConfigJob() {
        HttpRequest.get(APIContents.Job)
                .execute(new AbstractResponse<SystemConfigEntity>() {
                    @Override
                    public void onSuccess(SystemConfigEntity result) {

                        String faceStr = result.getValue();
                        if (faceStr != null) {
                            String[] strArr = faceStr.split("\\;");
                            for (String str : strArr) {
                                Logger.d(str);
                                job.add(str);
                            }
                        }

                        initJobOptionPicker();
                    }
                });
    }

    @Override
    public void saveInfo(BaseApiResponse baseApiResponse) {
        if (baseApiResponse.isSuc()) {
//            LoginEntity loginEntity = new LoginEntity();
//            loginEntity.setAreaCode(Integer.parseInt(areaCode));
//            loginEntity.setAreaName(areaName);
//            UserStorage.getInstance().saveLoginInfo(loginEntity);
//            int areaCode = UserStorage.getInstance().getLogin().getAreaCode();
//            String areaName = UserStorage.getInstance().getLogin().getAreaName();
          //  ForwardUtil.getInstance(this).forward(CompleteVideoActivity.class);
            ForwardUtil.getInstance(this).forward(MainActivity.class);
        } else {
            ToastUtil.show(baseApiResponse.getMsg());
        }


    }

    @Override
    public void uploadFile(UploadEntity result) {
        Logger.d("info : " + new Gson().toJson(result));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
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
        Log.e("response",response);
        switch (requestId) {
            case 0:
                try {
                    fileBase = JSON.parseObject(response, FileBase.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case 1:
                try {
                    loginResponse = JSON.parseObject(response, LoginResponse.class);
                    if (loginResponse!=null){
                        if (loginResponse.isSuc()) {
                            APPContents.ID = "" + loginResponse.getData().getId();
                            UserStorage.getInstance().saveLoginInfo(loginResponse.getData());

                            //设置当前用户信息
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(String.valueOf(loginResponse.getData().getId()),
                                    loginResponse.getData().getNickname(),
                                    Uri.parse(APIContents.HOST + "/" + loginResponse.getData().getAvatarUrl())));

                            AppData.putString(AppData.Keys.AD_USER_ID, loginResponse.getData().getId() + "");
                            if (loginResponse.getData().getAge() > 0) {
                                ForwardUtil.getInstance(this).forward(MainActivity.class);
                            } else {
                                ForwardUtil.getInstance(this).forward(CompleteSexActivity.class);
                            }

                        } else {
                            ToastUtil.show(loginResponse.getMsg());

                        }

                    }else {
                        ToastUtil.show("登录失败");
                    }




                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
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

    public void mylogin(final String userName, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userName", userName);
                    jsonObject.put("password", password);
                    OkHttpUtils.doPostJson(APIContents.LOGIN, jsonObject.toString(), mHttpCallBack, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
