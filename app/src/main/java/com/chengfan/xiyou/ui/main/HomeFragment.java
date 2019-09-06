package com.chengfan.xiyou.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.HomeContract;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.chengfan.xiyou.domain.model.entity.JsonBean;
import com.chengfan.xiyou.domain.model.entity.MemberBean;
import com.chengfan.xiyou.domain.presenter.HomePresenterImpl;
import com.chengfan.xiyou.ui.WebActivity;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.HomeAdapter;
import com.chengfan.xiyou.ui.dialog.SayHiDialog;
import com.chengfan.xiyou.ui.search.SearchActivity;
import com.chengfan.xiyou.ui.search.Web2Activity;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.utils.BaiDuEntity;
import com.chengfan.xiyou.utils.GetJsonDataUtil;
import com.chengfan.xiyou.utils.LocationUtils;
import com.chengfan.xiyou.utils.dialog.BaseNiceDialog;
import com.chengfan.xiyou.utils.dialog.NiceDialog;
import com.chengfan.xiyou.utils.dialog.ViewConvertListener;
import com.chengfan.xiyou.utils.dialog.ViewHolder;
import com.chengfan.xiyou.widget.CardPageTransformer;
import com.chengfan.xiyou.widget.pickerview.builder.OptionsPickerBuilder;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectListener;
import com.chengfan.xiyou.widget.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;
import com.zero.ci.widget.logger.Logger;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/10:43
 * @Description: 首页
 */
public class HomeFragment extends BaseFragment<HomeContract.View, HomePresenterImpl> implements HomeContract.View {
    View mView;
    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.home_rv)
    RecyclerView mHomeRv;
    @BindView(R.id.home_zrl)
    ZRefreshLayout mHomeZrl;

    @BindView(R.id.search_address_tv)
    TextView mSearchAddressTv;
    @BindView(R.id.swp_news)
    SwipeRefreshLayout swp_news;

    Unbinder unbinder;
    int page;
    HomeBannerEntity mHomeBannerEntity;
    HomeAdapter mHomeAdapter;
    private BaseNiceDialog mDialog;
    private CardPageTransformer mTransformer;
    // banner图片数据
    private List<String> data_banner_string = new ArrayList<>();

    Bundle toBundle;

    OptionsPickerView pvOptions;
    private List<JsonBean> mJsonBeanArrayList = new ArrayList<>();
    private ArrayList<ArrayList<JsonBean.CityBean>> mCityList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private int areaCodes;
    private String areaName;
    List<MemberBean> mMemberBeanList;
    SayHiDialog mSayHiDialog;
    private boolean aBooleantu=true;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        Logger.d("开始解析数据");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    Logger.e("解析完成");
                    showLoading();
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    Looper.prepare();
                                    Message message = new Message();
                                    Bundle bundle = new Bundle();
                                    Handler handler = new Handler() {
                                        @Override
                                        public void handleMessage(Message message) {
                                            super.handleMessage(message);
                                            Bundle bundle = message.getData();
                                            HttpRequest.get("http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + LocationUtils.getNetIp())
                                                    .execute(new AbstractResponse<BaiDuEntity>() {
                                                        @Override
                                                        public void onSuccess(BaiDuEntity result) {
                                                            String addressStr = result.getContent().getAddress_detail().getCity();
                                                            Logger.d(addressStr);
                                                            mSearchAddressTv.setText(result.getContent().getAddress_detail().getCity());
                                                            for (int i = 0; i < mJsonBeanArrayList.size(); i++) {//遍历省份
                                                                for (int c = 0; c < mJsonBeanArrayList.get(i).getCity().size(); c++) {
                                                                    Logger.d("areaCode : " + mJsonBeanArrayList.get(i).getCity().get(c).getName() + "   ===  " + addressStr);
                                                                    if (mJsonBeanArrayList.get(i).getCity().get(c).getName().contains(addressStr)) {
                                                                        areaCodes = mCityList.get(i).get(c).getId();
                                                                        Logger.d("areaCode = mCityList.get(i).get(a).getId() :" + areaCodes);
                                                                    }
                                                                }
                                                            }

                                                            Log.e("areaCodeareaCode", "onSuccess: "+areaCodes );
                                                            mPresenter.homePageParameter(areaCodes, 1);

                                                        }
                                                    });
                                        }
                                    };
                                    bundle.putString("msg", "你好！！！");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    Looper.loop();


                                }
                            }
                    ).start();
                    break;
                case MSG_LOAD_FAILED:
                    Logger.e("解析失败");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, mView);

        mPresenter = new HomePresenterImpl();
        mPresenter.onAttach(getActivity(), this);
        mHomeBannerEntity = new HomeBannerEntity();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        toBundle = new Bundle();
        mMemberBeanList = new ArrayList<>();
        iniView();
        iniBanner();
        initZrl();
        mSayHiDialog = new SayHiDialog(getActivity());

        swp_news.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.homePageParameter(areaCodes, 1);
                Log.e("swp_news0","swp_news");
            }
        });

        return mView;
    }

    private void iniView() {
        mHomeAdapter = new HomeAdapter(R.layout.adapter_home, mMemberBeanList);
        mHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeRv.setAdapter(mHomeAdapter);
        mHomeAdapter.setOnItemClickListener(new BaseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRVAdapter adapter, View view, int position) {
                if (position<0||position>mMemberBeanList.size()){
                }else {
                    toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mMemberBeanList.get(position).getId());
                    ForwardUtil.getInstance(getActivity()).forward(AccompanyUserInfoActivity.class, toBundle);
                }
            }
        });

    }

    private void iniBanner() {
        mTransformer = new CardPageTransformer(0.9f, 0.22f);
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);

        mConvenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        mConvenientBanner.startTurning(5000);
        mConvenientBanner.setManualPageable(true);
        mConvenientBanner.setPageTransformer(mTransformer);
        if (mConvenientBanner.getViewPager() != null) {
            mConvenientBanner.getViewPager().setClipToPadding(false);
            mConvenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) mConvenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mConvenientBanner.getViewPager().setOffscreenPageLimit(3);
        }
    }

    public void showBanner() {
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);
        mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position>mHomeBannerEntity.getNews().size()){

                }else {
                    if (mHomeBannerEntity!=null){
                        Log.e("getImgHref",mHomeBannerEntity.getNews().get(position).getImgHref());
                        if (mHomeBannerEntity.getNews().get(position).getImgHref().split(":")[0].equals("http")){
                            Intent intent=new Intent(getContext(), Web2Activity.class);
                            intent.putExtra("url",mHomeBannerEntity.getNews().get(position).getImgHref());
                            startActivity(intent);
                        }else {
                            toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, Integer.parseInt(mHomeBannerEntity.getNews().get(position).getImgHref()));
                            ForwardUtil.getInstance(getActivity()).forward(AccompanyUserInfoActivity.class, toBundle);
                        }
                    }else {
                        //后台未设置
                    }
                }

            }
        });
    }


    private void initZrl() {
        mHomeZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mHomeZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.homePageMoreParameter(areaCodes, page);
                        mHomeZrl.finishLoadMore();
                        Log.e("pageonLoadMore",""+areaCodes);
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mHomeZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.homePageParameter(areaCodes, 1);
                        mHomeZrl.finishRefresh();
                        Log.e("pageonRefresh",""+page);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void homePageMoreLoad(List<MemberBean> memberBeanList) {
        mMemberBeanList.addAll(memberBeanList);
        mHomeAdapter.setNewData(mMemberBeanList);
    }

    @Override
    public void homePageLoad(HomeBannerEntity result) {
        hideLoading();
            for (HomeBannerEntity.NewsBean bannerBean : result.getNews()) {
                data_banner_string.add(APIContents.HOST + "/" + bannerBean.getImgUrl());
            }
            mHomeBannerEntity=null;
            mHomeBannerEntity = result;
            showBanner();
            mMemberBeanList.clear();
            mMemberBeanList = mHomeBannerEntity.getMember();
            mHomeAdapter.setNewData(mMemberBeanList);
            swp_news.setRefreshing(false);
            aBooleantu=false;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick({R.id.search_address_ll, R.id.search_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_address_ll:
                showPickerView();
                break;
            case R.id.search_ll:
                ForwardUtil.getInstance(getActivity()).forward(SearchActivity.class);
                break;

        }
    }


    private void showPickerView() {
        pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String cityName = null;
                int areaCode = 0;
                if (options1 == 28) {
                    cityName = "香港特别行政区";
                } else if (options1 == 29) {
                    cityName = "澳门特别行政区";
                } else if (options1 == 27) {
                    cityName = "台湾省";
                } else {
                    cityName = mCityList.get(options1).get(options2).getName();
                    areaCode = mCityList.get(options1).get(options2).getId();
                }

                String tx = cityName;

                areaCodes=areaCode;
                Logger.d("选择的城市为：" + tx + " code : " + areaCode);
                mSearchAddressTv.setText(tx);
                mPresenter.homePageParameter(areaCode, 1);
                AppData.putBoolen(AppData.Keys.AD_SHOW_SAY_HI, true);

            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(mJsonBeanArrayList, mCityList);
        pvOptions.show();
    }

    private void initJsonData() {


        String JsonData = new GetJsonDataUtil().getJson(getActivity(), "province.json");

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


    class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_banner_item, null);
            imageView = view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageLoaderManager.getInstance().showImage(imageView, data);
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
                            mDialog = dialog;
                        }
                    })
                    .setOutCancel(false)
                    .setWidth(48)
                    .setHeight(48)
                    .setShowBottom(false)
                    .show(getChildFragmentManager());
        }

        /**
         * 隐藏loading
         */
        public void hideLoading() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
}
