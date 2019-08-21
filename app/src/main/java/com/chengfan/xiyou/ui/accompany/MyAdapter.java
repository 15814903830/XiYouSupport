package com.chengfan.xiyou.ui.accompany;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chengfan.xiyou.R;

import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;

public class MyAdapter extends BaseAdapter {
    private oncilckitem mOncilckitem;
    public List<String> getListText() {
        return listText;
    }

    public void setListText(List<String> listText) {
        this.listText = listText;
    }

    private List<String> listText;
    private Context context;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public MyAdapter(List<String> listText, Context context,oncilckitem mOncilckitem) {
        this.listText = listText;
        this.context = context;
        this.mOncilckitem=mOncilckitem;
    }

    @Override
    public int getCount() {
        //return返回的是int类型，也就是页面要显示的数量。
        return listText.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //通过一个打气筒 inflate 可以把一个布局转换成一个view对象
            view = View.inflate(context, R.layout.listtiem, null);
        } else {
            view = convertView;//复用历史缓存对象
        }
        //单选按钮的文字
        TextView radioText = (TextView) view.findViewById(R.id.tv_radio_text);
        //单选按钮
        RadioButton radioButton = (RadioButton) view.findViewById(R.id.rb_radio_button);
        radioText.setText(listText.get(position));
        radioText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), true);
                MyAdapter.this.notifyDataSetChanged();
                mOncilckitem.OnClickitem(listText.get(position));
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;
        radioButton.setChecked(res);
        return view;
    }

    interface oncilckitem{
       void OnClickitem(String  name);
    }
}