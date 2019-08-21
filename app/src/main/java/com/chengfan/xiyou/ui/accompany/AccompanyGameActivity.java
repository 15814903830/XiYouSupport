package com.chengfan.xiyou.ui.accompany;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.contract.AccompanyGameContract;
import com.chengfan.xiyou.domain.model.entity.AccompanyGameEntity;
import com.chengfan.xiyou.domain.model.entity.ChatGroupEntity;
import com.chengfan.xiyou.domain.presenter.AccompanyGamePresenterImpl;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.ui.adapter.AccompanyGameAdapter;
import com.chengfan.xiyou.utils.AppData;
import com.chengfan.xiyou.view.MediumTextView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.base.BaseActivity;
import com.zero.ci.base.adapter.BaseRVAdapter;
import com.zero.ci.network.zrequest.request.HttpRequest;
import com.zero.ci.network.zrequest.response.AbstractResponse;
import com.zero.ci.refresh.ZRefreshLayout;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.tool.ForwardUtil;
import com.zero.ci.widget.logger.Logger;

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
 * @DATE : 2019-07-04/16:10
 * @Description: 游戏陪玩
 */
public class AccompanyGameActivity extends BaseActivity<AccompanyGameContract.View, AccompanyGamePresenterImpl> implements AccompanyGameContract.View , HttpCallBack ,MyAdapter.oncilckitem{
    @BindView(R.id.xy_middle_tv)
    MediumTextView mXyMiddleTv;
    @BindView(R.id.list_mylistview)
    ListView mListView;
    @BindView(R.id.accompany_game_zrl)
    ZRefreshLayout mAccompanyGameZrl;

    @BindView(R.id.ll_sort_game1)
    LinearLayout mLinearLayout1;

    @BindView(R.id.ll_sort_game2)
    LinearLayout mLinearLayout2;

    @BindView(R.id.ll_sort_game3)
    LinearLayout mLinearLayout3;

    @BindView(R.id.listview_list)
    ListView mListViewlist;

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;


    //列表标题list
    private List<String> listText1=new ArrayList<String>();
    private List<String> listText2=new ArrayList<String>();
    private List<String> listText3=new ArrayList<String>();
    //创建适配器对象
    private MyAdapter adapter;


    List<ListviewBase> list=new ArrayList<>();
    List<AccompanyGameEntity> mAccompanyGameEntityList;
    Bundle revBundle;
    private String subjectId;
    int page = 1;
    private String sortOrder="true";//是否按单量排序
    private String areaTitle="";//大区
    private String gradeTitle="";//段位
    private  Mylistviewaparter mylistviewaparter;
    private boolean mBooleanlist1=true;
    private boolean mBooleanlist2=true;
    private boolean mBooleanlist3=true;
    private Handler mHandlere = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int requestId = msg.what;
            String response = (String) msg.obj;
            onHandlerMessageCallback(response, requestId);
        }
    };

    private HttpCallBack mHttpCallBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_game);
        UltimateBar.Companion.with(this)
                .statusDrawable(new ColorDrawable(Color.WHITE))
                .statusDark(true)
                .create()
                .drawableBar();
        ButterKnife.bind(this);
        mXyMiddleTv.setText(getResources().getText(R.string.main_pei_game_txt));
        mHttpCallBack=this;
        revBundle = getIntent().getExtras();
        if (revBundle != null)
            subjectId = revBundle.getString(APPContents.E_SUBJECT_ID);
        //mPresenter.gameParameter("3", 1, true);
        initZrl();
        getClassify();
        getlistbase(subjectId,sortOrder,areaTitle,gradeTitle);
        initDate();
    }


    public  void initDate(){
        //模拟创建数据
            listText1.add("智能排序");
            listText1.add("按单量");
        adapter=new MyAdapter(listText1,this,this);
        mListViewlist.setAdapter(adapter);
    }

    @OnClick({R.id.ll_sort_game1,R.id.ll_sort_game2,R.id.ll_sort_game3})
    public void MyOnclic(View view){
        switch (view.getId()) {
            case R.id.ll_sort_game1:
                if (mBooleanlist1){
                    adapter.setListText(listText1);
                    adapter.notifyDataSetChanged();
                    mListViewlist.setVisibility(View.VISIBLE);
                    mBooleanlist1=false;
                }else {
                    mListViewlist.setVisibility(View.GONE);
                    mBooleanlist1=true;
                }
                break;
            case R.id.ll_sort_game2:
                if (mBooleanlist2){
                    //模拟创建数据
                    adapter.setListText(listText2);
                    adapter.notifyDataSetChanged();
                    mListViewlist.setVisibility(View.VISIBLE);
                    mBooleanlist2=false;
                }else {
                    mListViewlist.setVisibility(View.GONE);
                    mBooleanlist2=true;
                }
                break;
            case R.id.ll_sort_game3:
                if (mBooleanlist3){
                    adapter.setListText(listText3);
                    adapter.notifyDataSetChanged();
                    mListViewlist.setVisibility(View.VISIBLE);
                    mBooleanlist3=false;
                    Log.e("listText3",listText3.get(0));
                }else {
                    mListViewlist.setVisibility(View.GONE);
                    mBooleanlist3=true;
                }
                break;
        }
    }

    private void initZrl() {
        mAccompanyGameZrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAccompanyGameZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        mPresenter.gameParameter(subjectId, page, false);
                        mAccompanyGameZrl.finishLoadMore();
                    }
                }, 1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {


                mAccompanyGameZrl.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        mPresenter.gameParameter(subjectId, page, true);
                        mAccompanyGameZrl.finishRefresh();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void gameLoad(List<AccompanyGameEntity> gameEntityList, boolean isPtr) {

    }


    public void initlistadapter() {
         mylistviewaparter=new Mylistviewaparter(this,list);
        mListView.setAdapter(mylistviewaparter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick","position:---"+position);
                Bundle toBundle = new Bundle();
                toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, list.get(position).getId());
                Log.e("iddddd",""+list.get(position).getId());
                ForwardUtil.getInstance(AccompanyGameActivity.this).forward(AccompanyDetailActivity.class, toBundle);
            }
        });
    }

    @OnClick(R.id.xy_back_btn)
    public void onClick() {
        finish();
    }

    private void getClassify() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet("http://xy.gx11.cn/api/Member/ListByArea?isRecommend=true&subjectId=10048&newsCode=1101&page=1&limit=18", mHttpCallBack, 0);
            }
        }).start();
    }


    private void getlistbase(final String subjectId,final String sortOrder,final String areaTitle,final String gradeTitle) {
        Log.e("subjectId",subjectId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.doGet("http://xy.gx11.cn/api/AccompanyPlay/List?id="+AppData.getString(AppData.Keys.AD_USER_ID) +
                        "&subjectId="+subjectId +
                        "&sortOrder="+sortOrder +
                        "&areaTitle="+areaTitle +
                        "&gradeTitle="+gradeTitle +
                        "&page=1" +
                        "&limit=18", mHttpCallBack, 1);
            }
        }).start();
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
            case 0://获取分类数据
                Log.e("leibiao",response);
                try {
                    ListduanweiBase listduanweiBase=JSON.parseObject(response,ListduanweiBase.class);
                    for (int i = 0; i < listduanweiBase.getSubject().size(); i++) {
                        if (Integer.parseInt(subjectId) == listduanweiBase.getSubject().get(i).getId()){
                            if (listduanweiBase.getSubject().get(i).getAreaTitles()==null){
                                mLinearLayout2.setVisibility(View.GONE);//大区不存在

                            }else{
                                try {
                                    String gradetitles = listduanweiBase.getSubject().get(i).getAreaTitles().split(":")[1];
                                    listText2 = Arrays.asList(gradetitles.split(","));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String gradetitles = listduanweiBase.getSubject().get(i).getAreaTitles().split("：")[1];
                                    listText2 = Arrays.asList(gradetitles.split("，"));
                                }
                            }

                            if (listduanweiBase.getSubject().get(i).getGradeTitles()==null){
                                mLinearLayout3.setVisibility(View.GONE);//段位不存在
                            }else{
                                try {
                                    //存在
                                    String gettitle = listduanweiBase.getSubject().get(i).getGradeTitles().split(":")[1];
                                    text3.setText(listduanweiBase.getSubject().get(i).getGradeTitles().split(":")[0]);
                                    listText3 = Arrays.asList(gettitle.split(","));
                                    Log.e("listText3",listText3.get(0));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    String gettitle = listduanweiBase.getSubject().get(i).getGradeTitles().split("：")[1];
                                    text3.setText(listduanweiBase.getSubject().get(i).getGradeTitles().split("：")[0]);
                                    listText3 = Arrays.asList(gettitle.split(","));
                                    Log.e("listText3",listText3.get(0));
                                }
                            }



                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case 1://获取分类数据
                Log.e("duanwei",response);
                list= JSONArray.parseArray(response, ListviewBase.class);
                initlistadapter();
                break;
        }
    }

    @Override
    public void OnClickitem(String name) {
        if (mBooleanlist1==false){
            text1.setText(name);
            if (name.equals("智能排序")){
                sortOrder="false";
            }else {
                sortOrder="true";
            }
            getlistbase(subjectId,sortOrder,areaTitle,gradeTitle);
        }else if (mBooleanlist2==false){
            text2.setText(name);
            areaTitle=name;
            getlistbase(subjectId,sortOrder,areaTitle,gradeTitle);
        }else if (mBooleanlist3==false){
            text3.setText(name);
            gradeTitle=name;
            getlistbase(subjectId,sortOrder,areaTitle,gradeTitle);
        }
    }
}
