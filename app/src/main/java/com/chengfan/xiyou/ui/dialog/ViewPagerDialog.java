package com.chengfan.xiyou.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengfan.xiyou.R;
import com.chengfan.xiyou.domain.model.entity.ImageEntity;
import com.chengfan.xiyou.ui.adapter.ViewPagerDialogAdapter;
import com.chengfan.xiyou.widget.viewpager.JazzyViewPager;
import com.zero.ci.widget.logger.Logger;

import java.util.List;


public class ViewPagerDialog extends Dialog {
    Context context;
    List<ImageEntity> list;
    View mView;
    Button vp_close_btn;
    TextView vp_dialog_tv;

    private Window window;
    JazzyViewPager _view_pager;
    ViewPagerDialogAdapter viewPagerAdapter;
    int mImageUrlsSize;

    public ViewPagerDialog(Context context, List<ImageEntity> list) {
        super(context, R.style.vpDialog);
        this.list = list;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window = getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);
        window.setWindowAnimations(R.style.dialogWindowAnim);
        setCanceledOnTouchOutside(true);
        mView = getLayoutInflater().inflate(R.layout.dialog_viewpager, null);
        setContentView(mView);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        vp_close_btn = (Button) findViewById(R.id.vp_close_btn);
        vp_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        vp_dialog_tv = (TextView) findViewById(R.id.vp_dialog_tv);
        vp_dialog_tv.setText("1 / " + list.size());
        initImageToViewPager(list);
    }

    /**
     * c初始化viewpager
     */
    private void initImageToViewPager(final List<ImageEntity> list) {
        if (list != null)
            mImageUrlsSize = list.size();
        for (int i = 0; i < mImageUrlsSize; i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(15, 15);
            lp.setMargins(10, 0, 10, 0);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        setViewPager(JazzyViewPager.TransitionEffect.Standard);
    }

    private void setViewPager(JazzyViewPager.TransitionEffect effect) {
        // =============  初始化viewpager =========
        _view_pager = (JazzyViewPager) mView.findViewById(R.id.dialog_jazzy_vp);
        _view_pager.setTransitionEffect(effect);
        Logger.e("setViewPager " + list);
        viewPagerAdapter = new ViewPagerDialogAdapter(context, list, _view_pager);
        _view_pager.setAdapter(viewPagerAdapter);


        _view_pager.setOnPageChangeListener(new MyPageChangeListener());
        _view_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mImageUrlsSize == 0 || mImageUrlsSize == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(final int position) {
            vp_dialog_tv.setText(position + 1 + " / " + list.size());
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }


}
