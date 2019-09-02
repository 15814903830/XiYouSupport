package com.chengfan.xiyou.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class RecommendAdapter extends FragmentPagerAdapter {
    List<RecommendFragment> list;
    List<String> mtitlelist;
    public RecommendAdapter(FragmentManager fm, List<RecommendFragment> list,List<String> mtitlelist) {
        super(fm);
        this.list=list;
        this.mtitlelist=mtitlelist;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mtitlelist.get(position);
//    }
}
