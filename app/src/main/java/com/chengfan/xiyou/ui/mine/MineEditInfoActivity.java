package com.chengfan.xiyou.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineEditInfoContract;
import com.chengfan.xiyou.domain.model.entity.GetMemberInfoEntity;
import com.chengfan.xiyou.domain.model.entity.JsonBean;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.SystemConfigEntity;
import com.chengfan.xiyou.domain.presenter.MineEditInfoPresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.complete.CompleteVideoActivity;
import com.chengfan.xiyou.ui.mine.order.AutonymActivity;
import com.chengfan.xiyou.ui.mine.order.AutonymtrueforflaseActivity;
import com.chengfan.xiyou.ui.mine.order.FileBase;
import com.chengfan.xiyou.ui.mine.order.MinEBase;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.GetJsonDataUtil;
import com.chengfan.xiyou.view.MediumTextView;
import com.chengfan.xiyou.view.RegularEditText;
import com.chengfan.xiyou.view.RegularTextView;
import com.chengfan.xiyou.widget.pickerview.builder.OptionsPickerBuilder;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectChangeListener;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectListener;
import com.chengfan.xiyou.widget.pickerview.view.OptionsPickerView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/12:36
 * @Description: 编辑资料
 */
public class MineEditInfoActivity extends BaseActivity<MineEditInfoContract.View, MineEditInfoPresenterImpl> implements MineEditInfoContract.View, HttpCallBack {
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;

    @BindView(R.id.edit_info_is_check_tv)
    RegularTextView mEditInfoIsCheckTv;

    @BindView(R.id.edit_head_civ)
    CircleImageView mEditHeadCiv;

    @BindView(R.id.edit_name_et)
    RegularEditText mEditNameEt;

    @BindView(R.id.edit_age_et)
    RegularEditText mEditAgeEt;

    @BindView(R.id.edit_city_tv)
    RegularTextView mEditCityTv;

    @BindView(R.id.edit_face_tv)
    RegularTextView mEditFaceTv;

    @BindView(R.id.edit_wx_et)
    RegularEditText mEditWxEt;

    @BindView(R.id.edit_job_tv)
    RegularTextView mEditJobTv;

    @BindView(R.id.edit_z_name_tv)
    MediumTextView mEditZNameTv;

    @BindView(R.id.edit_check_tv)
    RegularTextView mEditCheckTv;

    @BindView(R.id.edit_check_tvSS)
    RegularTextView mEditCheckTvSS;

    @BindView(R.id.edit_sex_et)
    RegularEditText mEditSexEt;


    private MinEBase minEBase;
    private ArrayList<AlbumFile> mAlbumFiles;
    private String myprovince;
    private String mycity;
    private String mydistrict;
    OptionsPickerView pvOptions;
    private List<JsonBean> mJsonBeanArrayList = new ArrayList<>();
    private ArrayList<ArrayList<JsonBean.CityBean>> mCityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<JsonBean.CityBean.AreaBean>>> mAreaList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private CityPickerView mCityPickerView;
    private FileBase fileBase;//上传图片
    private ArrayList<String> shenGaoList = new ArrayList<>();
    private ArrayList<String> tiZhongList = new ArrayList<>();
    private ArrayList<String> faceList = new ArrayList<>();

    private ArrayList<String> job = new ArrayList<>();
    private HttpCallBack mHttpCallBack;
    String nameStr, ageStr, cityStr, faceStr, wxStr, jobStr, sexStr;
    String areaName, areaCode;
    UploadFile mUploadFile;
    private RelativeLayout mRelativeLayout;


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
        setContentView(R.layout.activity_mine_edit_info);
        mHttpCallBack = this;
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        ButterKnife.bind(this);


        mXyMiddleTv.setText(getResources().getText(R.string.complete_title_txt));

        mAlbumFiles = new ArrayList<>();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);


        mPresenter.memberInfoParameter();
        requestSystemConfigFace();
        requestSystemConfigShenGao();
        requestSystemConfigTiZhong();
        requestSystemConfigJob();
        showpicker();
        getdata();
    }

    @OnClick({R.id.xy_back_btn, R.id.complete_city_rl, R.id.complete_face_rl, R.id.complete_job_rl,
            R.id.edit_info_check_rl, R.id.edit_info_save_btn, R.id.edit_head_rl, R.id.edit_info_check_rl2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.complete_city_rl:
                showPickerView();
                break;
            case R.id.edit_info_check_rl2:
                //上传身份证信息
                if (minEBase.getRealNameTag().equals("审核中")){
                    Toast.makeText(this, "信息审核中", Toast.LENGTH_SHORT).show();
                }else if (minEBase.getRealNameTag().equals("未认证")){
                    startActivity(new Intent(this, AutonymActivity.class));
                    finish();
                }else {
                     Intent intent=new Intent(this, AutonymtrueforflaseActivity.class);
                                intent.putExtra("name",minEBase.getNickname());
                                intent.putExtra("carsum",minEBase.getIdNo());
                                intent.putExtra("img",minEBase.getIdImages());
                                intent.putExtra("tv",minEBase.getRealNameTag());
                                startActivity(intent);
                                finish();
                }
                break;
            case R.id.edit_head_rl:
                selectImage();
                break;
            case R.id.complete_face_rl:
                initNoLinkOptionsPicker();
                break;
            case R.id.complete_job_rl:
                initCustomOptionPicker();
                break;
            case R.id.edit_info_check_rl:
                Bundle toBundle = new Bundle();
                toBundle.putBoolean(APPContents.BUNDLE_BOOLEAN, true);
                ForwardUtil.getInstance(this).forward(CompleteVideoActivity.class, toBundle);
                break;
            case R.id.edit_info_save_btn:
                nameStr = mEditNameEt.getText().toString().trim();
                ageStr = mEditAgeEt.getText().toString().trim();
                wxStr = mEditWxEt.getText().toString().trim();
                sexStr = mEditSexEt.getText().toString().trim();
                MemberInfoBean bean = new MemberInfoBean();
                bean.setId(AppData.getString(AppData.Keys.AD_USER_ID));
                bean.setAvatarUrl(fileBase.getFilePath());
                bean.setNickname(nameStr);
                bean.setAge(ageStr);
                if (sexStr.equals("男"))
                    bean.setGender("1");
                else
                    bean.setGender("0");
                bean.setAreaCode(areaCode);
                bean.setAreaName(mEditCityTv.getText().toString().trim());
                bean.setExterior(faceStr);
                bean.setWeiXinUId(wxStr);

                Log.e("mEditWxEt", wxStr);
                bean.setJob(jobStr);
                mPresenter.memberInfoUpdateParameter(bean);
                break;
        }
    }


    @Override
    public void memberInfoLoad(GetMemberInfoEntity getMemberInfoEntity) {
        if (getMemberInfoEntity != null) {
            initView(getMemberInfoEntity);
        }
    }


    @Override
    public void uploadLoad(BaseApiResponse baseApiResponse) {
        Log.e("sctp", "" + baseApiResponse.getMsg());
    }

    @Override
    public void memberInfoUpdateLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
        finish();
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

        Log.e("path", result.get(0).getPath());
        postimg(best64(result.get(0).getPath()));
        //mPresenter.uploadParameter(mUploadFile);
        ImageLoaderManager.getInstance().showImage(mEditHeadCiv, result.get(0).getPath());
    }


    private void showPickerView() {// 弹出选择器
        mCityPickerView.showCityPicker();//三级联动
    }

    private void showpicker() {
        //初始化地区pop
        mCityPickerView = new CityPickerView();
        mCityPickerView.init(this);
        CityConfig cityConfig = new CityConfig.Builder().build();
        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                //省份
                if (province != null) {
                    myprovince = province.getName();
                }

                //城市
                if (city != null) {
                    mycity = city.getName();
                }

                //地区
                if (district != null) {
                    mydistrict = district.getName();
                }
                mEditCityTv.setText(province.getName() + "," + city.getName() + "," + district.getName());
            }

        });

    }


    private void initJsonData() {//解析数据

        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        mJsonBeanArrayList = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<JsonBean.CityBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<JsonBean.CityBean.AreaBean>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                cityList.add(jsonBean.get(i).getCity().get(c));//添加城市
                ArrayList<JsonBean.CityBean.AreaBean> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                for (int a = 0; a < jsonBean.get(i).getCity().get(c).getArea().size(); a++) {
                    city_AreaList.add(jsonBean.get(i).getCity().get(c).getArea().get(a));
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }
            mCityList.add(cityList);
            mAreaList.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
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


    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        OptionsPickerView pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = shenGaoList.get(options1)
                        + " " + tiZhongList.get(options2)
                        + " " + faceList.get(options3);

                mEditFaceTv.setText(str);
                faceStr = str;
                Logger.d(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = options1 + " " + options2 + " " + options3;
                        Logger.d(str);
                        faceStr = str;
                        mEditFaceTv.setText(str);
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvNoLinkOptions.setNPicker(shenGaoList, tiZhongList, faceList);
        pvNoLinkOptions.setSelectOptions(0, 1, 1);
        pvNoLinkOptions.show();

    }

    /* 获取工作*/
    private void initCustomOptionPicker() {

        OptionsPickerView pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = job.get(options1);
                jobStr = str;
                mEditJobTv.setText(str);
                Logger.d(str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = options1 + " ";
                        Logger.d(str);
                        jobStr = str;
                        mEditJobTv.setText(str);
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvCustomOptions.setPicker(job);
        pvCustomOptions.setSelectOptions(0);
        pvCustomOptions.show();
    }


    private void requestSystemConfigFace() {
        HttpRequest.get(APIContents.Face)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Type type = new TypeToken<SystemConfigEntity>() {
                        }.getType();
                        SystemConfigEntity systemConfigEntity = new Gson().fromJson(result, type);

                        String faceStr = systemConfigEntity.getValue();
                        if (faceStr != null) {
                            String[] strArr = faceStr.split("\\;");
                            for (String str : strArr) {
                                Logger.d(str);
                                faceList.add(str);
                            }
                        }

                    }
                });
    }

    private void requestSystemConfigShenGao() {
        HttpRequest.get(APIContents.ShenGao)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {

                        Type type = new TypeToken<SystemConfigEntity>() {
                        }.getType();
                        SystemConfigEntity systemConfigEntity = new Gson().fromJson(result, type);

                        String faceStr = systemConfigEntity.getValue();
                        if (faceStr != null) {
                            String[] strArr = faceStr.split("\\;");
                            for (String str : strArr) {
                                Logger.d(str);
                                shenGaoList.add(str);
                            }
                        }

                    }
                });
    }

    private void requestSystemConfigTiZhong() {
        HttpRequest.get(APIContents.TiZhong)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {


                        Type type = new TypeToken<SystemConfigEntity>() {
                        }.getType();
                        SystemConfigEntity systemConfigEntity = new Gson().fromJson(result, type);

                        String faceStr = systemConfigEntity.getValue();
                        if (faceStr != null) {
                            String[] strArr = faceStr.split("\\;");
                            for (String str : strArr) {
                                Logger.d(str);
                                tiZhongList.add(str);
                            }
                        }

                    }
                });
    }

    private void requestSystemConfigJob() {
        HttpRequest.get(APIContents.Job)
                .execute(new AbstractResponse<String>() {
                    @Override
                    public void onSuccess(String result) {

                        Type type = new TypeToken<SystemConfigEntity>() {
                        }.getType();
                        SystemConfigEntity systemConfigEntity = new Gson().fromJson(result, type);

                        String faceStr = systemConfigEntity.getValue();
                        if (faceStr != null) {
                            String[] strArr = faceStr.split("\\;");
                            for (String str : strArr) {
                                Logger.d(str);
                                job.add(str);
                            }
                        }

                    }
                });
    }


    private void initView(GetMemberInfoEntity getMemberInfoEntity) {
        ImageLoaderManager.getInstance().showImage(mEditHeadCiv, APIContents.HOST + "/" + getMemberInfoEntity.getAvatarUrl());
        mEditAgeEt.setText(getMemberInfoEntity.getAge() + "");
        mEditNameEt.setText(getMemberInfoEntity.getNickname());
        mEditCityTv.setText(getMemberInfoEntity.getAreaName());
        mEditFaceTv.setText(getMemberInfoEntity.getExterior());
        mEditWxEt.setText(getMemberInfoEntity.getWeiXin());
        Log.e("mEditWxEt", "" + getMemberInfoEntity.getWeiXin());
        if (getMemberInfoEntity.getGender() == 1) {
            mEditSexEt.setText("男");
        } else {
            mEditSexEt.setText("女");
        }

        mEditJobTv.setText(getMemberInfoEntity.getJob());
        if (getMemberInfoEntity.getGenderVideo() == null) {
            mEditCheckTv.setText("暂未验证");
        } else {
            mEditCheckTv.setText("已验证");
        }
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
        switch (requestId) {
            case 0:
                try {
                    fileBase = JSON.parseObject(response, FileBase.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                Log.e("response1", response);
                minEBase = JSON.parseObject(response, MinEBase.class);
                mEditCheckTvSS.setText(minEBase.getRealNameTag());
//                editCheckTv.setText(minEBase.getVerificationGenderStatusTag());
                break;
        }


    }

    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("idrun", AppData.getString(AppData.Keys.AD_USER_ID));
                OkHttpUtils.doGet(APIContents.HOST + "/api/Account/GetAccount/" + AppData.getString(AppData.Keys.AD_USER_ID), mHttpCallBack, 1);
            }
        }).start();
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
}
