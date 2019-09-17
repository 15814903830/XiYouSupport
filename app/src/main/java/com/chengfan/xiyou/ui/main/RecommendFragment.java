package com.chengfan.xiyou.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.baserv.MultipleItemAdapter;
import com.chengfan.xiyou.baserv.MyMultipleItem;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.common.APPContents;
import com.chengfan.xiyou.domain.model.entity.AccompanyEntity;
import com.chengfan.xiyou.okhttp.HttpCallBack;
import com.chengfan.xiyou.okhttp.OkHttpUtils;
import com.chengfan.xiyou.okhttp.RequestParams;
import com.chengfan.xiyou.ui.WebActivity;
import com.chengfan.xiyou.ui.accompany.AccompanyUserInfoActivity;
import com.chengfan.xiyou.ui.login.LoginActivity;
import com.chengfan.xiyou.utils.AppData;
import com.zero.ci.tool.ForwardUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
@SuppressLint("ValidFragment")
public class RecommendFragment extends Fragment implements HttpCallBack {
    View mView;
     private String subjectId;
    private int page=1;
    private boolean data=true;
    public static RecommendFragment getInstance(String subjectId) {
        Bundle bundle=new Bundle();
        bundle.putString("subjectId",subjectId);
        RecommendFragment recommendFragment=new RecommendFragment();
        recommendFragment.setArguments(bundle);
        return recommendFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if (bundle != null) {
            subjectId=bundle.getString("subjectId");
        }
    }

    private List<MyMultipleItem> dast = new ArrayList<>();
    private MultipleItemAdapter adapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private RecyclerView mAccompanyRv;
   AccompanyEntity mAccompanyEntityList;
    private HttpCallBack mHttpCallBack;
    private boolean datalist=true;
    private Boolean over = false;
    private boolean isErr = true;
    private int intover=0;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.recommendfragment,container,false);
        mAccompanyRv=mView.findViewById(R.id.rv_recommend);
        mHttpCallBack=this;
        initManager();
        commitanswers(page);
        return mView;
    }


    private void initManager() {
        //创建总布局管理
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAccompanyRv.setLayoutManager(mStaggeredGridLayoutManager);
        mAccompanyRv.setNestedScrollingEnabled(false);
    }

    private void initAdapter(AccompanyEntity mAccompanyEntityList) {
        for (int i = 0; i < mAccompanyEntityList.getAccompanyPlay().size(); i++) {
            dast.add(new MyMultipleItem(MyMultipleItem.FIRST_TYPE, mAccompanyEntityList.getAccompanyPlay().get(i)));
        }

        //创建适配器
        adapter = new MultipleItemAdapter(dast);
        //给RecyclerView设置适配器
        mAccompanyRv.setAdapter(adapter);
        adapter.bindToRecyclerView(mAccompanyRv);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //注意要到Adapter设置监听helper.addOnClickListener(view的id);//给item子类添加监听
            }
        });

        //上拉加载（设置这个监听就表示有上拉加载功能了）
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
              //  commitanswers(++page);
            }
        }, mAccompanyRv);


        initAdapter();
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
        Log.e("responsesssrecpm",response);
        try {
            mAccompanyEntityList = JSON.parseObject(response,AccompanyEntity.class);
            initAdapter(mAccompanyEntityList);
            adapter.loadMoreEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void commitanswers(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RequestParams> list=new ArrayList<>();
                list.add(new RequestParams("subjectId", subjectId));
                list.add(new RequestParams("page", ""+page));
                list.add(new RequestParams("isRecommend", "true"));
                list.add(new RequestParams("areaCode", "440100"));
                    OkHttpUtils.doGet(APIContents.HOST+"/api/Member/ListByArea" ,list,mHttpCallBack,1);
            }
        }).start();
    }

        private void initAdapter() {
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    int id = mAccompanyEntityList.getAccompanyPlay().get(position).getMemberNews().getId();
                    String userId = AppData.getString(AppData.Keys.AD_USER_ID);
                    String url = APIContents.HOST + "/WapNews/MemberNewsVoidDetail?" + "id=" + id + "&memberId=" + userId;
                    Log.e("initAdapter",url);
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(WebActivity.KEY_URL, url);
                    startActivity(intent);


//                    Bundle toBundle = new Bundle();
//                    toBundle.putInt(APPContents.E_CURRENT_MEMBER_ID, mAccompanyEntityList.getAccompanyPlay().get(position).getId());
//                    ForwardUtil.getInstance(getActivity()).forward(AccompanyUserInfoActivity.class, toBundle);
                }
            });
        }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (data){
            }else {
                data=false;
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
}
