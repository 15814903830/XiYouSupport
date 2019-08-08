package com.chengfan.xiyou.ui.mine;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.MineEditInfoContract;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.GetMemberInfoEntity;
import com.chengfan.xiyou.domain.model.entity.JsonBean;
import com.chengfan.xiyou.domain.model.entity.MemberInfoBean;
import com.chengfan.xiyou.domain.model.entity.SystemConfigEntity;
import com.chengfan.xiyou.domain.model.entity.XYUploadEntity;
import com.chengfan.xiyou.domain.presenter.MineEditInfoPresenterImpl;
import com.chengfan.xiyou.ui.complete.CompleteVideoActivity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.FileToBase64;
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
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.BaseApiResponse;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.network.zrequest.response.AbstractUploadResponse;
import com.zero.ci.network.zrequest.response.AdaptResponse;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.tool.ToastUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class MineEditInfoActivity extends BaseActivity<MineEditInfoContract.View, MineEditInfoPresenterImpl> implements MineEditInfoContract.View {
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

    @BindView(R.id.edit_sex_et)
    RegularEditText mEditSexEt;


    private ArrayList<AlbumFile> mAlbumFiles;


    OptionsPickerView pvOptions;
    private List<JsonBean> mJsonBeanArrayList = new ArrayList<>();
    private ArrayList<ArrayList<JsonBean.CityBean>> mCityList = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<JsonBean.CityBean.AreaBean>>> mAreaList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    private ArrayList<String> shenGaoList = new ArrayList<>();
    private ArrayList<String> tiZhongList = new ArrayList<>();
    private ArrayList<String> faceList = new ArrayList<>();

    private ArrayList<String> job = new ArrayList<>();

    String nameStr, ageStr, cityStr, faceStr, wxStr, jobStr, sexStr;
    String areaName, areaCode;
    UploadFile mUploadFile;


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
    }

    @OnClick({R.id.xy_back_btn, R.id.complete_city_rl, R.id.complete_face_rl, R.id.complete_job_rl,
            R.id.edit_info_check_rl, R.id.edit_info_save_btn, R.id.edit_head_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xy_back_btn:
                finish();
                break;
            case R.id.complete_city_rl:
                showPickerView();
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
                if (mUploadFile != null)
                    mPresenter.uploadParameter(mUploadFile);

                MemberInfoBean bean = new MemberInfoBean();
                bean.setId(AppData.getString(AppData.Keys.AD_USER_ID));
                bean.setNickname(nameStr);
                bean.setAge(ageStr);
                if (sexStr.equals("男"))
                    bean.setGender("1");
                else
                    bean.setGender("0");
                bean.setAreaCode(areaCode);
                bean.setAreaName(areaName);
                bean.setExterior(faceStr);
                bean.setWeiXinUId(wxStr);
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
        ToastUtil.show(baseApiResponse.getMsg());
    }

    @Override
    public void memberInfoUpdateLoad(BaseApiResponse baseApiResponse) {
        ToastUtil.show(baseApiResponse.getMsg());
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
        ImageLoaderManager.getInstance().showImage(mEditHeadCiv, result.get(0).getPath());
    }


    private void showPickerView() {// 弹出选择器

        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String cityName = mCityList.get(options1).get(options2).getName();

                areaName = cityName;
                areaCode = mAreaList.get(options1).get(options2).get(options3).getId() + "";

                String tx = cityName + areaName;


                Logger.d("选择的城市为：" + tx + " code : " + areaCode);
                mEditCityTv.setText(tx);

            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(mJsonBeanArrayList, mCityList, mAreaList);//三级选择器
        pvOptions.show();
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
}
