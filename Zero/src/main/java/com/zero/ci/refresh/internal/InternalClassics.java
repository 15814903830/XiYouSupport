package com.zero.ci.refresh.internal;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zero.ci.refresh.api.RefreshInternal;
import com.zero.ci.refresh.api.RefreshKernel;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.constant.SpinnerStyle;
import com.zero.ci.tool.DensityUtil;


import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.zero.ci.refresh.utils.ZUtil.getColor;

/**
 * Author: Zero Yuan
 * Email: zero.yuan.xin@gmail.com
 * Description:经典组件
 * -------------------------------
 */

public abstract class InternalClassics<T extends InternalClassics> extends InternalAbstract implements RefreshInternal {
    public static final byte ID_TEXT_TITLE = 1;
    public static final byte ID_IMAGE_ARROW = 2;
    public static final byte ID_IMAGE_PROGRESS = 3;

    protected TextView mTitleTv;
    protected ImageView mArrowIv;
    protected ImageView mProgressIv;
    protected LinearLayout mCenterLayout;
    protected RefreshKernel mRefreshKernel;
    protected ArrowDrawable mArrowDrawable;
    protected ProgressDrawable mProgressDrawable;

    protected Integer mAccentColor;
    protected Integer mPrimaryColor;

    protected int mBackgroundColor;
    protected int mFinishDuration = 500;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;

    public InternalClassics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSpinnerStyle = SpinnerStyle.Translate;
        mArrowIv = new ImageView(context);
        mProgressIv = new ImageView(context);
        mTitleTv = new TextView(context);
        mTitleTv.setTextColor(0xff666666);
        mCenterLayout = new LinearLayout(context);
        mCenterLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mCenterLayout.setOrientation(LinearLayout.VERTICAL);

        final View thisView = this;
        final ViewGroup thisGroup = this;
        final View arrowView = mArrowIv;
        final View titleView = mTitleTv;
        final View progressView = mProgressIv;
        final ViewGroup centerLayout = mCenterLayout;
        final DensityUtil density = new DensityUtil();

        titleView.setId(ID_TEXT_TITLE);
        arrowView.setId(ID_IMAGE_ARROW);
        progressView.setId(ID_IMAGE_PROGRESS);
        centerLayout.setId(android.R.id.widget_frame);

        LinearLayout.LayoutParams lpHeaderText = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        centerLayout.addView(titleView, lpHeaderText);

        LayoutParams lpHeaderLayout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        thisGroup.addView(centerLayout, lpHeaderLayout);

        LayoutParams lpArrow = new LayoutParams(density.dip2px(20), density.dip2px(20));
        lpArrow.addRule(CENTER_VERTICAL);
        lpArrow.addRule(LEFT_OF, android.R.id.widget_frame);
        thisGroup.addView(arrowView, lpArrow);

        LayoutParams lpProgress = new LayoutParams((ViewGroup.LayoutParams) lpArrow);
        lpProgress.addRule(CENTER_VERTICAL);
        lpProgress.addRule(LEFT_OF, android.R.id.widget_frame);
        progressView.animate().setInterpolator(new LinearInterpolator());
        thisGroup.addView(progressView, lpProgress);

        if (thisView.getPaddingTop() == 0) {
            if (thisView.getPaddingBottom() == 0) {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = density.dip2px(20), thisView.getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = density.dip2px(20), thisView.getPaddingRight(), mPaddingBottom = thisView.getPaddingBottom());
            }
        } else {
            if (thisView.getPaddingBottom() == 0) {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = thisView.getPaddingTop(), thisView.getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                mPaddingTop = thisView.getPaddingTop();
                mPaddingBottom = thisView.getPaddingBottom();
            }
        }

        if (thisView.isInEditMode()) {
            arrowView.setVisibility(GONE);
        } else {
            progressView.setVisibility(GONE);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final View thisView = this;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            thisView.setPadding(thisView.getPaddingLeft(), 0, thisView.getPaddingRight(), 0);
        } else {
            thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop, thisView.getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            final View arrowView = mArrowIv;
            final View progressView = mProgressIv;
            arrowView.animate().cancel();
            progressView.animate().cancel();
        }
        final Drawable drawable = mProgressIv.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        }
    }

    protected T self() {
        return (T) this;
    }


    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        final View progressView = mProgressIv;
        if (progressView.getVisibility() != VISIBLE) {
            progressView.setVisibility(VISIBLE);
            Drawable drawable = mProgressIv.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            } else {
                progressView.animate().rotation(36000).setDuration(100000);
            }
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        onStartAnimator(refreshLayout, height, maxDragHeight);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        final View progressView = mProgressIv;
        Drawable drawable = mProgressIv.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        } else {
            progressView.animate().rotation(0).setDuration(0);
        }
        progressView.setVisibility(GONE);
        return mFinishDuration;//延迟500毫秒之后再弹回
    }

    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (colors.length > 0) {
            final View thisView = this;
            if (!(thisView.getBackground() instanceof BitmapDrawable) && mPrimaryColor == null) {
                setPrimaryColor(colors[0]);
                mPrimaryColor = null;
            }
            if (mAccentColor == null) {
                if (colors.length > 1) {
                    setAccentColor(colors[1]);
                } else {
                    setAccentColor(colors[0] == 0xffffffff ? 0xff666666 : 0xffffffff);
                }
                mAccentColor = null;
            }
        }
    }


    public T setProgressDrawable(Drawable drawable) {
        mProgressDrawable = null;
        mProgressIv.setImageDrawable(drawable);
        return self();
    }

    public T setProgressResource(@DrawableRes int resId) {
        mProgressDrawable = null;
        mProgressIv.setImageResource(resId);
        return self();
    }

    public T setArrowDrawable(Drawable drawable) {
        mArrowDrawable = null;
        mArrowIv.setImageDrawable(drawable);
        return self();
    }

    public T setArrowResource(@DrawableRes int resId) {
        mArrowDrawable = null;
        mArrowIv.setImageResource(resId);
        return self();
    }

    public T setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return self();
    }

    public T setPrimaryColor(@ColorInt int primaryColor) {
        mBackgroundColor = mPrimaryColor = primaryColor;
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundFor(this, mPrimaryColor);

        }
        return self();
    }

    public T setAccentColor(@ColorInt int accentColor) {
        mAccentColor = accentColor;
        mTitleTv.setTextColor(accentColor);
        if (mArrowDrawable != null) {
            mArrowDrawable.setColor(accentColor);
        }
        if (mProgressDrawable != null) {
            mProgressDrawable.setColor(accentColor);
        }
        return self();
    }

    public T setPrimaryColorId(@ColorRes int colorId) {
        final View thisView = this;
        setPrimaryColor(getColor(thisView.getContext(), colorId));
        return self();
    }

    public T setAccentColorId(@ColorRes int colorId) {
        final View thisView = this;
        setAccentColor(getColor(thisView.getContext(), colorId));
        return self();
    }

    public T setFinishDuration(int delay) {
        mFinishDuration = delay;
        return self();
    }

    public T setTextSizeTitle(float size) {
        mTitleTv.setTextSize(size);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return self();
    }


    public T setDrawableMarginRight(float dp) {
        final View arrowView = mArrowIv;
        final View progressView = mProgressIv;
        MarginLayoutParams lpArrow = (MarginLayoutParams) arrowView.getLayoutParams();
        MarginLayoutParams lpProgress = (MarginLayoutParams) progressView.getLayoutParams();
        lpArrow.rightMargin = lpProgress.rightMargin = DensityUtil.dp2px(dp);
        arrowView.setLayoutParams(lpArrow);
        progressView.setLayoutParams(lpProgress);
        return self();
    }


    public T setDrawableSize(float dp) {
        final View arrowView = mArrowIv;
        final View progressView = mProgressIv;
        ViewGroup.LayoutParams lpArrow = arrowView.getLayoutParams();
        ViewGroup.LayoutParams lpProgress = progressView.getLayoutParams();
        lpArrow.width = lpProgress.width = DensityUtil.dp2px(dp);
        lpArrow.height = lpProgress.height = DensityUtil.dp2px(dp);
        arrowView.setLayoutParams(lpArrow);
        progressView.setLayoutParams(lpProgress);
        return self();
    }


    public T setDrawableArrowSize(float dp) {
        final View arrowView = mArrowIv;
        ViewGroup.LayoutParams lpArrow = arrowView.getLayoutParams();
        lpArrow.height = lpArrow.width = DensityUtil.dp2px(dp);
        arrowView.setLayoutParams(lpArrow);
        return self();
    }

    public T setDrawableProgressSize(float dp) {
        final View progressView = mProgressIv;
        ViewGroup.LayoutParams lpProgress = progressView.getLayoutParams();
        lpProgress.height = lpProgress.width = DensityUtil.dp2px(dp);
        progressView.setLayoutParams(lpProgress);
        return self();
    }


}
