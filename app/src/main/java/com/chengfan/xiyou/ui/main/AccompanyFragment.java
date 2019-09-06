package com.chengfan.xiyou.ui.main;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.baserv.MultipleItemAdapter;
import com.chengfan.xiyou.baserv.MyMultipleItem;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;
import com.chengfan.xiyou.domain.model.entity.HomeBannerEntity;
import com.chengfan.xiyou.domain.model.entity.JsonBean;
import com.chengfan.xiyou.domain.presenter.AccompanyPresenterImpl;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.adapter.AccompanyJvAdapter;
import com.chengfan.xiyou.ui.search.SearchActivity;
import com.chengfan.xiyou.ui.search.Web2Activity;
import com.chengfan.xiyou.utils.BaiDuEntity;
import com.chengfan.xiyou.utils.GetJsonDataUtil;
import com.chengfan.xiyou.utils.LocationUtils;
import com.chengfan.xiyou.widget.CardPageTransformer;
import com.chengfan.xiyou.widget.pickerview.builder.OptionsPickerBuilder;
import com.chengfan.xiyou.widget.pickerview.listener.OnOptionsSelectListener;
import com.chengfan.xiyou.widget.pickerview.view.OptionsPickerView;
import com.chengfan.xiyou.widget.viewpager.JazzyViewPager;
import com.google.gson.Gson;
import com.zero.ci.base.BaseFragment;
import com.zero.ci.navigation.BottomNavigationViewEx;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
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
 * @Description: 陪玩
 */
public class AccompanyFragment extends BaseFragment<AccompanyContract.View, AccompanyPresenterImpl> implements AccompanyContract.View {
    View mView;
    @BindView(R.id.search_address_tv)
    TextView mSearchAddressTv;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.accompany_jvp)
    JazzyViewPager mAccompanyJvp;
    @BindView(R.id.bot_nav)
    BottomNavigationViewEx mBotNav;
    @BindView(R.id.tal_recommendlist)
    TabLayout mTabLayout;
    @BindView(R.id.vp_accompany)
    ViewPager mViewPager;

    private List<MyMultipleItem> dast = new ArrayList<>();
    Unbinder mUnbinder;
    private MultipleItemAdapter adapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private boolean data = true;
    HomeBannerEntity mHomeBannerEntity;
    List<AccompanyEntity> mAccompanyEntityList;
    @BindView(R.id.linear)
    LinearLayout mLinear;
    private CardPageTransformer mTransformer;
    private List<String> data_banner_string = new ArrayList<>();
    OptionsPickerView pvOptions;
    private List<JsonBean> mJsonBeanArrayList = new ArrayList<>();
    private ArrayList<ArrayList<JsonBean.CityBean>> mCityList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private List<RecommendFragment> mRecommendFragmentList = new ArrayList<>();
    private RecommendAdapter mRecommendAdapter;
    List<String> mtitlelist=new ArrayList<>();

    private int areaCode;
    private String areaName;
    private ViewHolder holder;
    Bundle toBundle;
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
                                            String msg = bundle.getString("msg");
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
                                                                        areaCode = mCityList.get(i).get(c).getId();
                                                                        Logger.d("areaCode = mCityList.get(i).get(a).getId() :" + areaCode);
                                                                    }
                                                                }


                                                            }
                                                            mPresenter.accompanyBannerParameter(areaCode, 1);
                                                           // Log.e("areaCode",""+areaCode);

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_accompany, null);
        mUnbinder = ButterKnife.bind(this, mView);
        toBundle = new Bundle();
        getdata();
        iniBanner();
        bottomInit();
        setAccompanyData();
        addvptab();
        return mView;
    }

    public void addvptab() {
        mtitlelist.add("推荐陪练");
        mtitlelist.add("守望先锋");
        mtitlelist.add("和平精英");
        mtitlelist.add("王者荣耀");
        mtitlelist.add("绝地求生");
        mtitlelist.add("LOL");
        mtitlelist.add("音乐");
        mtitlelist.add("堡受之夜");
        mtitlelist.add("陪聊倾诉");
        mtitlelist.add("哄睡FM");
        for (int i = 0; i < 10; i++) {
            mRecommendFragmentList.add(RecommendFragment.getInstance(""+i));
        }
        mRecommendAdapter=new RecommendAdapter(getFragmentManager(),mRecommendFragmentList,mtitlelist);
        mViewPager.setAdapter(mRecommendAdapter);
        //设置TabLayout的模式
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mRecommendFragmentList.size()-1);
        mTabLayout.setSelectedTabIndicatorHeight(0);
        setTabView();
    }




    private void getdata() {
        mPresenter = new AccompanyPresenterImpl();
        mPresenter.onAttach(getActivity(), this);
        mSearchTv.setHint(R.string.accompany_search_txt);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        mAccompanyEntityList = new ArrayList<>();
        //mPresenter.accompanyPlayListParameter(1, true, "0");
        mBotNav.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), APPContents.FONTS_BOLD));
    }

    private void setAccompanyData() {
        mAccompanyJvp.setTransitionEffect(JazzyViewPager.TransitionEffect.Standard);
        mAccompanyJvp.setOnPageChangeListener(new MyPageChangeListener());
        AccompanyJvAdapter accompanyJvAdapter = new AccompanyJvAdapter(getActivity(), mAccompanyJvp);
        mAccompanyJvp.setAdapter(accompanyJvAdapter);

        //ViewPager切换的监听事件
        mAccompanyJvp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                position = position % 1;// 需要对position的值进行重新赋值，否则会造成数组越界
                // 更新小圆点的显示
                for (int i = 0; i < 1; i++) {
                    ImageView iv = (ImageView) mLinear.getChildAt(i);
                    // 当前滑到的是那一页就让第几个小圆点处于选中状态
                    if (position == i) {
                        iv.setEnabled(true);
                    } else {
                        iv.setEnabled(false);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initPoint();
    }

    private void initPoint() {
        for (int i = 0; i < 1; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(R.drawable.indicator_viewpager_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            iv.setLayoutParams(params);
            if (i == 0) {
                iv.setEnabled(true);
            } else {
                iv.setEnabled(false);
            }
            iv.setPadding(5, 5, 5, 5);
            mLinear.addView(iv);
        }
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

    private void bottomInit() {
        mBotNav.enableItemShiftingMode(false);
        mBotNav.enableShiftingMode(false);
        mBotNav.enableAnimation(true);
        mBotNav.setLargeTextSize(18);
        mBotNav.setSmallTextSize(15);
        mBotNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = mBotNav.getMenuItemPosition(item);
                mAccompanyEntityList.clear();
                mPresenter.accompanyPlayListParameter(1, true, "" + position);
                //重新获取数据
                return true;
            }
        });
    }


    @Override
    public void accompanyBanner(HomeBannerEntity homeBannerEntity) {
        mHomeBannerEntity = homeBannerEntity;
        if (mHomeBannerEntity != null) {
            for (HomeBannerEntity.NewsBean bannerBean : mHomeBannerEntity.getNews()) {
                data_banner_string.add(APIContents.HOST + "/" + bannerBean.getImgUrl());
            }
            showBanner();
        }
    }


    @Override
    public void accompanyPlayListLoad(List<AccompanyEntity> accompanyEntityList, boolean isPtr) {
        if (isPtr) {
            mAccompanyEntityList = accompanyEntityList;
        } else {
            mAccompanyEntityList.addAll(accompanyEntityList);
        }
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

                Logger.d("选择的城市为：" + tx + " code : " + areaCode);
                mSearchAddressTv.setText(tx);
               // mPresenter.accompanyBannerParameter(areaCode, 1);//轮播图440100
                mPresenter.accompanyBannerParameter(440100, 1);//轮播图440100
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(final int position) {
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 设置Tab的样式
     */
    private void setTabView() {
        holder = null;
        for (int i = 0; i < mtitlelist.size(); i++) {
            //依次获取标签
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.tab_item);
            holder = new ViewHolder(tab.getCustomView());
            //为标签填充数据
            holder.tvTabName.setText(mtitlelist.get(i));
            //默认选择第一项
            if (i == 0) {
                holder.tvTabName.setSelected(true);
                holder.tvTabName.setTextSize(22);
                holder.tvTabName.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }

        //tab选中的监听事件
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(true);
                //选中后字体变大
                holder.tvTabName.setTextSize(22);
                //让Viewpager跟随TabLayout的标签切换
                holder.tvTabName.setTypeface(Typeface.DEFAULT_BOLD);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                holder = new ViewHolder(tab.getCustomView());
                holder.tvTabName.setSelected(false);
                //恢复为默认字体大小
                holder.tvTabName.setTextSize(16);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewHolder {
        TextView tvTabName;

        public ViewHolder(View tabView) {
            tvTabName = (TextView) tabView.findViewById(R.id.tv_tab_name);
        }
    }

    //像素单位转换
    public int dip2px(int dip) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
