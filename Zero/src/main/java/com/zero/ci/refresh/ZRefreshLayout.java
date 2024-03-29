package com.zero.ci.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.zero.ci.R;
import com.zero.ci.refresh.api.DefaultRefreshFooterCreator;
import com.zero.ci.refresh.api.DefaultRefreshHeaderCreator;
import com.zero.ci.refresh.api.RefreshContent;
import com.zero.ci.refresh.api.RefreshFooter;
import com.zero.ci.refresh.api.RefreshHeader;
import com.zero.ci.refresh.api.RefreshInternal;
import com.zero.ci.refresh.api.RefreshKernel;
import com.zero.ci.refresh.api.RefreshLayout;
import com.zero.ci.refresh.api.ScrollBoundaryDecider;
import com.zero.ci.refresh.api.constant.DimensionStatus;
import com.zero.ci.refresh.api.constant.RefreshState;
import com.zero.ci.refresh.api.constant.SpinnerStyle;
import com.zero.ci.refresh.api.listener.OnLoadMoreListener;
import com.zero.ci.refresh.api.listener.OnMultiPurposeListener;
import com.zero.ci.refresh.api.listener.OnRefreshListener;
import com.zero.ci.refresh.api.listener.OnRefreshLoadMoreListener;
import com.zero.ci.refresh.api.listener.OnStateChangedListener;
import com.zero.ci.refresh.api.listener.SimpleMultiPurposeListener;
import com.zero.ci.refresh.footer.ClassicsFooter;
import com.zero.ci.refresh.header.ClassicsHeader;
import com.zero.ci.refresh.impl.RefreshContentWrapper;
import com.zero.ci.refresh.impl.RefreshFooterWrapper;
import com.zero.ci.refresh.impl.RefreshHeaderWrapper;
import com.zero.ci.refresh.impl.ScrollBoundaryDeciderAdapter;
import com.zero.ci.refresh.utils.DelayedRunnable;
import com.zero.ci.refresh.utils.ScrollBoundaryUtil;
import com.zero.ci.refresh.utils.ViscousFluidInterpolator;
import com.zero.ci.tool.DensityUtil;

import java.util.ArrayList;
import java.util.List;


import static android.view.MotionEvent.obtain;
import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.getSize;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.zero.ci.refresh.utils.ZUtil.fling;
import static com.zero.ci.refresh.utils.ZUtil.getColor;
import static com.zero.ci.refresh.utils.ZUtil.isScrollableView;
import static com.zero.ci.tool.DensityUtil.dp2px;
import static java.lang.System.currentTimeMillis;

/**
 * Author: Zero Yuan
 * Email: zero.yuan.xin@gmail.com
 * Description: 刷新布局控件
 * -------------------------------
 */

public class ZRefreshLayout extends ViewGroup implements RefreshLayout, NestedScrollingParent, NestedScrollingChild {

    //<editor-fold desc="属性变量 property and variable">

    //<editor-fold desc="滑动属性">
    protected int mTouchSlop;
    /**
     * 当前的Spinner
     */
    protected int mSpinner;
    /**
     * 最后的Spinner
     */
    protected int mLastSpinner;
    /**
     * 触摸时候，的Spinner
     */
    protected int mTouchSpinner;
    /**
     * 二级展开时长
     */
    protected int mFloorDuration = 250;
    /**
     * 回弹动画时长
     */
    protected int mReboundDuration = 250;
    /**
     * 屏幕高度
     */
    protected int mScreenHeightPixels;

    protected float mTouchX;
    protected float mTouchY;
    /**
     * 用于实现Header的左右拖动效果
     */
    protected float mLastTouchX;
    /**
     * 用于实现多点触摸
     */
    protected float mLastTouchY;

    protected float mDragRate = .5f;
    /**
     * 拖动的方向 none-n horizontal-h vertical-v
     */
    protected char mDragDirection = 'n';

    /**
     * 是否正在拖动
     */
    protected boolean isBeingDragged;
    /**
     * 父类是否处理触摸事件
     */
    protected boolean isSuperDispatchTouchEvent;

    /**
     * 固定在头部的视图Id
     */
    protected int mFixedHeaderViewId;
    /**
     * 固定在底部的视图Id
     */
    protected int mFixedFooterViewId;
    /**
     * 下拉Header偏移的视图Id
     */
    protected int mHeaderTranslationViewId;
    /**
     * 下拉Footer偏移的视图Id
     */
    protected int mFooterTranslationViewId;

    protected int mMinimumVelocity;
    protected int mMaximumVelocity;
    protected int mCurrentVelocity;

    protected Scroller mScroller;
    protected VelocityTracker mVelocityTracker;
    protected Interpolator mReboundInterpolator;

    //</editor-fold>

    //<editor-fold desc="功能属性">
    protected int[] mPrimaryColors;
    protected boolean isEnableRefresh = true;
    protected boolean isEnableLoadMore = false;
    /**
     * 当 Header FixedBehind 时候是否剪裁遮挡 Header
     */
    protected boolean isEnableClipHeaderWhenFixedBehind = true;
    /**
     * 当 Footer FixedBehind 时候是否剪裁遮挡 Footer
     */
    protected boolean isEnableClipFooterWhenFixedBehind = true;
    /**
     * 是否启用内容视图拖动效果
     */
    protected boolean isEnableHeaderTranslationContent = true;
    /**
     * 是否启用内容视图拖动效果
     */
    protected boolean isEnableFooterTranslationContent = true;
    /**
     * 是否在全部加载结束之后Footer跟随内容
     */
    protected boolean isEnableFooterFollowWhenLoadFinished = false;
    /**
     * 是否在编辑模式下开启预览功能
     */
    protected boolean isEnablePreviewInEditMode = true;
    /**
     * 是否启用越界回弹
     */
    protected boolean mEnableOverScrollBounce = true;
    /**
     * 是否启用越界拖动（仿苹果效果）
     */
    protected boolean isEnableOverScrollDrag = false;
    /**
     * 是否在列表滚动到底部时自动加载更多
     */
    protected boolean isEnableAutoLoadMore = true;
    /**
     * 是否开启纯滚动模式
     */
    protected boolean isEnablePureScrollMode = false;
    /**
     * 是否在加载更多完成之后滚动内容显示新数据
     */
    protected boolean isEnableScrollContentWhenLoaded = true;
    /**
     * 是否在刷新完成之后滚动内容显示新数据
     */
    protected boolean isEnableScrollContentWhenRefreshed = true;
    /**
     * 在内容不满一页的时候，是否可以上拉加载更多
     */
    protected boolean isEnableLoadMoreWhenContentNotFull = true;
    /**
     * 是否开启在刷新时候禁止操作内容视图
     */
    protected boolean isDisableContentWhenRefresh = false;
    /**
     * 是否开启在刷新时候禁止操作内容视图
     */
    protected boolean isDisableContentWhenLoading = false;
    /**
     * 数据是否全部加载完成，如果完成就不能在触发加载事件
     */
    protected boolean isFooterNoMoreData = false;
    /**
     * 是否手动设置过LoadMore，用于智能开启
     */
    protected boolean isManualLoadMore = false;
    /**
     * 是否手动设置过 NestedScrolling，用于智能开启
     */
    protected boolean isManualNestedScrolling = false;
    /**
     * 是否手动设置过内容视图拖动效果
     */
    protected boolean isManualHeaderTranslationContent = false;
    /**
     * 是否手动设置过内容视图拖动效果
     */
    protected boolean isManualFooterTranslationContent = false;
    //</editor-fold>

    //<editor-fold desc="监听属性">
    protected OnRefreshListener mRefreshListener;
    protected OnLoadMoreListener mLoadMoreListener;
    protected OnMultiPurposeListener mOnMultiPurposeListener;
    protected ScrollBoundaryDecider mScrollBoundaryDecider;
    //</editor-fold>

    //<editor-fold desc="嵌套滚动">
    protected int mTotalUnconsumed;
    protected boolean mNestedInProgress;
    protected int[] mParentOffsetInWindow = new int[2];
    protected NestedScrollingChildHelper mNestedChild = new NestedScrollingChildHelper(this);
    protected NestedScrollingParentHelper mNestedParent = new NestedScrollingParentHelper(this);
    //</editor-fold>

    //<editor-fold desc="内部视图">

    protected int mHeaderHeight;        //头部高度 和 头部高度状态
    protected DimensionStatus mHeaderHeightStatus = DimensionStatus.DefaultUnNotify;
    protected int mFooterHeight;        //底部高度 和 底部高度状态
    protected DimensionStatus mFooterHeightStatus = DimensionStatus.DefaultUnNotify;

    protected int mHeaderInsetStart;    // Header 起始位置便宜
    protected int mFooterInsetStart;    // Footer 起始位置便宜

    protected float mHeaderMaxDragRate = 2.5f;  //最大拖动比率(最大高度/Header高度)
    protected float mFooterMaxDragRate = 2.5f;  //最大拖动比率(最大高度/Footer高度)
    protected float mHeaderTriggerRate = 1.0f;  //触发刷新距离 与 HeaderHeight 的比率
    protected float mFooterTriggerRate = 1.0f;  //触发加载距离 与 FooterHeight 的比率

    protected RefreshInternal mRefreshHeader;     //下拉头部视图
    protected RefreshInternal mRefreshFooter;     //上拉底部视图
    protected RefreshContent mRefreshContent;   //显示内容视图
    //</editor-fold>

    protected Paint mPaint;
    protected Handler mHandler;
    protected RefreshKernel mKernel = new RefreshKernelImpl();
    protected List<DelayedRunnable> mListDelayedRunnable;

    protected RefreshState mState = RefreshState.None;          //主状态
    protected RefreshState mViceState = RefreshState.None;      //副状态（主状态刷新时候的滚动状态）

    protected long mLastOpenTime = 0;                           //上一次 刷新或者加载 时间

    protected int mHeaderBackgroundColor = 0;                   //为Header绘制纯色背景
    protected int mFooterBackgroundColor = 0;

    protected boolean isHeaderNeedTouchEventWhenRefreshing;      //为游戏Header提供独立事件
    protected boolean isFooterNeedTouchEventWhenLoading;

    protected boolean isFooterLocked = false;//Footer 正在loading 的时候是否锁住 列表不能向上滚动

    protected static boolean isManualFooterCreator = false;
    protected static DefaultRefreshFooterCreator sFooterCreator = new DefaultRefreshFooterCreator() {
        @NonNull
        @Override
        public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
            return new ClassicsFooter(context);
        }
    };
    protected static DefaultRefreshHeaderCreator sHeaderCreator = new DefaultRefreshHeaderCreator() {
        @NonNull
        @Override
        public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
            return new ClassicsHeader(context);
        }
    };


    protected Runnable animationRunnable;
    protected ValueAnimator reboundAnimator;

    protected boolean isVerticalPermit = false;                  //竖直通信证（用于特殊事件的权限判定）

    /**
     * 滑动判断
     */
    protected MotionEvent mFalsifyEvent = null;
    //</editor-fold dec = "属性变量 property and variable --- end ---">

    //<editor-fold desc="构造方法 construction methods">
    public ZRefreshLayout(Context context) {
        this(context, null);
    }

    public ZRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        super.setClipToPadding(false);

        DensityUtil density = new DensityUtil();
        ViewConfiguration configuration = ViewConfiguration.get(context);

        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mScreenHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        mReboundInterpolator = new ViscousFluidInterpolator();
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ZRefreshLayout);

        setNestedScrollingEnabled(ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableNestedScrolling, false));
        mDragRate = ta.getFloat(R.styleable.ZRefreshLayout_zrlDragRate, mDragRate);
        mHeaderMaxDragRate = ta.getFloat(R.styleable.ZRefreshLayout_zrlHeaderMaxDragRate, mHeaderMaxDragRate);
        mFooterMaxDragRate = ta.getFloat(R.styleable.ZRefreshLayout_zrlFooterMaxDragRate, mFooterMaxDragRate);
        mHeaderTriggerRate = ta.getFloat(R.styleable.ZRefreshLayout_zrlHeaderTriggerRate, mHeaderTriggerRate);
        mFooterTriggerRate = ta.getFloat(R.styleable.ZRefreshLayout_zrlFooterTriggerRate, mFooterTriggerRate);
        isEnableRefresh = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableRefresh, isEnableRefresh);
        mReboundDuration = ta.getInt(R.styleable.ZRefreshLayout_zrlReboundDuration, mReboundDuration);
        isEnableLoadMore = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableLoadMore, isEnableLoadMore);
        mHeaderHeight = ta.getDimensionPixelOffset(R.styleable.ZRefreshLayout_zrlHeaderHeight, density.dip2px(100));
        mFooterHeight = ta.getDimensionPixelOffset(R.styleable.ZRefreshLayout_zrlFooterHeight, density.dip2px(60));
        mHeaderInsetStart = ta.getDimensionPixelOffset(R.styleable.ZRefreshLayout_zrlHeaderInsetStart, 0);
        mFooterInsetStart = ta.getDimensionPixelOffset(R.styleable.ZRefreshLayout_zrlFooterInsetStart, 0);
        isDisableContentWhenRefresh = ta.getBoolean(R.styleable.ZRefreshLayout_zrlDisableContentWhenRefresh, isDisableContentWhenRefresh);
        isDisableContentWhenLoading = ta.getBoolean(R.styleable.ZRefreshLayout_zrlDisableContentWhenLoading, isDisableContentWhenLoading);
        isEnableHeaderTranslationContent = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableHeaderTranslationContent, isEnableHeaderTranslationContent);
        isEnableFooterTranslationContent = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableFooterTranslationContent, isEnableFooterTranslationContent);
        isEnablePreviewInEditMode = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnablePreviewInEditMode, isEnablePreviewInEditMode);
        isEnableAutoLoadMore = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableAutoLoadMore, isEnableAutoLoadMore);
        mEnableOverScrollBounce = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableOverScrollBounce, mEnableOverScrollBounce);
        isEnablePureScrollMode = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnablePureScrollMode, isEnablePureScrollMode);
        isEnableScrollContentWhenLoaded = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableScrollContentWhenLoaded, isEnableScrollContentWhenLoaded);
        isEnableScrollContentWhenRefreshed = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableScrollContentWhenRefreshed, isEnableScrollContentWhenRefreshed);
        isEnableLoadMoreWhenContentNotFull = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableLoadMoreWhenContentNotFull, isEnableLoadMoreWhenContentNotFull);
        isEnableFooterFollowWhenLoadFinished = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableFooterFollowWhenLoadFinished, isEnableFooterFollowWhenLoadFinished);
        isEnableClipHeaderWhenFixedBehind = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableClipHeaderWhenFixedBehind, isEnableClipHeaderWhenFixedBehind);
        isEnableClipFooterWhenFixedBehind = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableClipFooterWhenFixedBehind, isEnableClipFooterWhenFixedBehind);
        isEnableOverScrollDrag = ta.getBoolean(R.styleable.ZRefreshLayout_zrlEnableOverScrollDrag, isEnableOverScrollDrag);
        mFixedHeaderViewId = ta.getResourceId(R.styleable.ZRefreshLayout_zrlFixedHeaderViewId, View.NO_ID);
        mFixedFooterViewId = ta.getResourceId(R.styleable.ZRefreshLayout_zrlFixedFooterViewId, View.NO_ID);
        mHeaderTranslationViewId = ta.getResourceId(R.styleable.ZRefreshLayout_zrlHeaderTranslationViewId, View.NO_ID);
        mFooterTranslationViewId = ta.getResourceId(R.styleable.ZRefreshLayout_zrlFooterTranslationViewId, View.NO_ID);

        if (isEnablePureScrollMode && !ta.hasValue(R.styleable.ZRefreshLayout_zrlEnableOverScrollDrag)) {
            isEnableOverScrollDrag = true;
        }

        isManualLoadMore = ta.hasValue(R.styleable.ZRefreshLayout_zrlEnableLoadMore);
        isManualNestedScrolling = ta.hasValue(R.styleable.ZRefreshLayout_zrlEnableNestedScrolling);
        isManualHeaderTranslationContent = ta.hasValue(R.styleable.ZRefreshLayout_zrlEnableHeaderTranslationContent);
        mHeaderHeightStatus = ta.hasValue(R.styleable.ZRefreshLayout_zrlHeaderHeight) ? DimensionStatus.XmlLayoutUnNotify : mHeaderHeightStatus;
        mFooterHeightStatus = ta.hasValue(R.styleable.ZRefreshLayout_zrlFooterHeight) ? DimensionStatus.XmlLayoutUnNotify : mFooterHeightStatus;

//        mHeaderExtendHeight = (int) Math.max((mHeaderHeight * (mHeaderMaxDragRate - 1)), 0);
//        mFooterExtendHeight = (int) Math.max((mFooterHeight * (mFooterMaxDragRate - 1)), 0);

        int accentColor = ta.getColor(R.styleable.ZRefreshLayout_zrlAccentColor, 0);
        int primaryColor = ta.getColor(R.styleable.ZRefreshLayout_zrlPrimaryColor, 0);
        if (primaryColor != 0) {
            if (accentColor != 0) {
                mPrimaryColors = new int[]{primaryColor, accentColor};
            } else {
                mPrimaryColors = new int[]{primaryColor};
            }
        } else if (accentColor != 0) {
            mPrimaryColors = new int[]{0, accentColor};
        }

        ta.recycle();

    }
    //</editor-fold>

    //<editor-fold desc="生命周期 life cycle">

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int count = super.getChildCount();
        if (count > 3) {
            throw new RuntimeException("最多只支持3个子View，Most only support three sub view");
        }

        int contentLevel = 0;
        int indexContent = -1;
        for (int i = 0; i < count; i++) {
            View view = super.getChildAt(i);
            if (isScrollableView(view) && (contentLevel < 2 || i == 1)) {
                indexContent = i;
                contentLevel = 2;
            } else if (!(view instanceof RefreshInternal) && contentLevel < 1) {
                indexContent = i;
                contentLevel = i > 0 ? 1 : 0;
            }
        }

        int indexHeader = -1;
        int indexFooter = -1;
        if (indexContent >= 0) {
            mRefreshContent = new RefreshContentWrapper(super.getChildAt(indexContent));
            if (indexContent == 1) {
                indexHeader = 0;
                if (count == 3) {
                    indexFooter = 2;
                }
            } else if (count == 2) {
                indexFooter = 1;
            }
        }

        for (int i = 0; i < count; i++) {
            View view = super.getChildAt(i);
            if (i == indexHeader ||
                    (i != indexFooter && indexHeader == -1 && mRefreshHeader == null && view instanceof RefreshHeader)) {
                mRefreshHeader = (view instanceof RefreshHeader) ? (RefreshHeader) view : new RefreshHeaderWrapper(view);
            } else if
                    (i == indexFooter ||
                            (indexFooter == -1 && view instanceof RefreshFooter)) {
                isEnableLoadMore = (isEnableLoadMore || !isManualLoadMore);
                mRefreshFooter = (view instanceof RefreshFooter) ? (RefreshFooter) view : new RefreshFooterWrapper(view);
            }
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final View thisView = this;
        if (!thisView.isInEditMode()) {

            if (mHandler == null) {
                mHandler = new Handler();
            }

            if (mListDelayedRunnable != null) {
                for (DelayedRunnable runnable : mListDelayedRunnable) {
                    mHandler.postDelayed(runnable, runnable.delayMillis);
                }
                mListDelayedRunnable.clear();
                mListDelayedRunnable = null;
            }

            if (mRefreshHeader == null) {
                setRefreshHeader(sHeaderCreator.createRefreshHeader(thisView.getContext(), this));
            }
            if (mRefreshFooter == null) {
                setRefreshFooter(sFooterCreator.createRefreshFooter(thisView.getContext(), this));
            } else {
                isEnableLoadMore = isEnableLoadMore || !isManualLoadMore;
            }

            if (mRefreshContent == null) {
                for (int i = 0, len = getChildCount(); i < len; i++) {
                    View view = getChildAt(i);
                    if ((mRefreshHeader == null || view != mRefreshHeader.getView()) &&
                            (mRefreshFooter == null || view != mRefreshFooter.getView())) {
                        mRefreshContent = new RefreshContentWrapper(view);
                    }
                }
            }
            if (mRefreshContent == null) {
                final int padding = DensityUtil.dp2px(20);
                final TextView errorView = new TextView(thisView.getContext());
                errorView.setTextColor(0xffff6600);
                errorView.setGravity(Gravity.CENTER);
                errorView.setTextSize(20);
                errorView.setText(R.string.zrl_content_empty);
                super.addView(errorView, MATCH_PARENT, MATCH_PARENT);
                mRefreshContent = new RefreshContentWrapper(errorView);
                mRefreshContent.getView().setPadding(padding, padding, padding, padding);
            }

            View fixedHeaderView = mFixedHeaderViewId > 0 ? thisView.findViewById(mFixedHeaderViewId) : null;
            View fixedFooterView = mFixedFooterViewId > 0 ? thisView.findViewById(mFixedFooterViewId) : null;

            mRefreshContent.setScrollBoundaryDecider(mScrollBoundaryDecider);
            mRefreshContent.setEnableLoadMoreWhenContentNotFull(isEnableLoadMoreWhenContentNotFull);
            mRefreshContent.setUpComponent(mKernel, fixedHeaderView, fixedFooterView);

            if (mSpinner != 0) {
                notifyStateChanged(RefreshState.None);
                mRefreshContent.moveSpinner(mSpinner = 0, mHeaderTranslationViewId, mFooterTranslationViewId);
            }

            if (!isManualNestedScrolling && !isNestedScrollingEnabled()) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        final View thisView = ZRefreshLayout.this;
                        for (ViewParent parent = thisView.getParent(); parent != null; ) {
                            if (parent instanceof NestedScrollingParent) {
                                View target = ZRefreshLayout.this;
                                //noinspection RedundantCast
                                if (((NestedScrollingParent) parent).onStartNestedScroll(target, target, ViewCompat.SCROLL_AXIS_VERTICAL)) {
                                    setNestedScrollingEnabled(true);
                                    isManualNestedScrolling = false;
                                    break;
                                }
                            }
                            if (parent instanceof View) {
                                View thisParent = (View) parent;
                                parent = thisParent.getParent();
                            } else {
                                break;
                            }
                        }
                    }
                });
            }
        }

        if (mPrimaryColors != null) {
            if (mRefreshHeader != null) {
                mRefreshHeader.setPrimaryColors(mPrimaryColors);
            }
            if (mRefreshFooter != null) {
                mRefreshFooter.setPrimaryColors(mPrimaryColors);
            }
        }

        //重新排序
        if (mRefreshContent != null) {
            super.bringChildToFront(mRefreshContent.getView());
        }
        if (mRefreshHeader != null && mRefreshHeader.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
            super.bringChildToFront(mRefreshHeader.getView());
        }
        if (mRefreshFooter != null && mRefreshFooter.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
            super.bringChildToFront(mRefreshFooter.getView());
        }

    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int minimumHeight = 0;
        final View thisView = this;
        final boolean isInEditMode = thisView.isInEditMode() && isEnablePreviewInEditMode;

        for (int i = 0, len = super.getChildCount(); i < len; i++) {
            View child = super.getChildAt(i);

            if (mRefreshHeader != null && mRefreshHeader.getView() == child) {
                final View headerView = mRefreshHeader.getView();
                final LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                final int widthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, lp.leftMargin + lp.rightMargin, lp.width);
                int heightSpec = heightMeasureSpec;

                if (mHeaderHeightStatus.gteReplaceWith(DimensionStatus.XmlLayoutUnNotify)) {
                    heightSpec = makeMeasureSpec(Math.max(mHeaderHeight - lp.bottomMargin - lp.topMargin, 0), EXACTLY);
                    headerView.measure(widthSpec, heightSpec);
                } else if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                    int headerHeight = 0;
                    if (!mHeaderHeightStatus.notified) {
                        super.measureChild(headerView, widthSpec, makeMeasureSpec(Math.max(getSize(heightSpec) - lp.bottomMargin - lp.topMargin, 0), AT_MOST));
                        headerHeight = headerView.getMeasuredHeight();
                    }
                    headerView.measure(widthSpec, makeMeasureSpec(Math.max(getSize(heightSpec) - lp.bottomMargin - lp.topMargin, 0), EXACTLY));
                    if (headerHeight > 0 && headerHeight != headerView.getMeasuredHeight()) {
                        mHeaderHeight = headerHeight + lp.bottomMargin + lp.topMargin;
                    }
                } else if (lp.height > 0) {
                    if (mHeaderHeightStatus.canReplaceWith(DimensionStatus.XmlExactUnNotify)) {
                        mHeaderHeight = lp.height + lp.bottomMargin + lp.topMargin;
                        mHeaderHeightStatus = DimensionStatus.XmlExactUnNotify;
                    }
                    heightSpec = makeMeasureSpec(lp.height, EXACTLY);
                    headerView.measure(widthSpec, heightSpec);
                } else if (lp.height == WRAP_CONTENT) {
                    heightSpec = makeMeasureSpec(Math.max(getSize(heightMeasureSpec) - lp.bottomMargin - lp.topMargin, 0), AT_MOST);
                    headerView.measure(widthSpec, heightSpec);
                    int measuredHeight = headerView.getMeasuredHeight();
                    if (measuredHeight > 0 && mHeaderHeightStatus.canReplaceWith(DimensionStatus.XmlWrapUnNotify)) {
                        mHeaderHeightStatus = DimensionStatus.XmlWrapUnNotify;
                        mHeaderHeight = headerView.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;
                    } else if (measuredHeight <= 0) {
                        heightSpec = makeMeasureSpec(Math.max(mHeaderHeight - lp.bottomMargin - lp.topMargin, 0), EXACTLY);
                        headerView.measure(widthSpec, heightSpec);
                    }
                } else if (lp.height == MATCH_PARENT) {
                    heightSpec = makeMeasureSpec(Math.max(mHeaderHeight - lp.bottomMargin - lp.topMargin, 0), EXACTLY);
                    headerView.measure(widthSpec, heightSpec);
                } else {
                    headerView.measure(widthSpec, heightSpec);
                }
                if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale && !isInEditMode) {
                    final int height = Math.max(0, isEnableRefresh() ? mSpinner : 0);
                    heightSpec = makeMeasureSpec(Math.max(height - lp.bottomMargin - lp.topMargin, 0), EXACTLY);
                    headerView.measure(widthSpec, heightSpec);
                }

                if (!mHeaderHeightStatus.notified) {
                    mHeaderHeightStatus = mHeaderHeightStatus.notified();
                    mRefreshHeader.onInitialized(mKernel, mHeaderHeight, (int) (mHeaderMaxDragRate * mHeaderHeight));
//                    mHeaderExtendHeight = (int) Math.max((mHeaderHeight * (mHeaderMaxDragRate - 1)), 0);
//                    mRefreshHeader.onInitialized(mKernel, mHeaderHeight, mHeaderExtendHeight);
                }

                if (isInEditMode && isEnableRefresh()) {
                    minimumHeight += headerView.getMeasuredHeight();
                }
            }

            if (mRefreshFooter != null && mRefreshFooter.getView() == child) {
                final View footerView = mRefreshFooter.getView();
                final LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
                final int widthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, lp.leftMargin + lp.rightMargin, lp.width);
                int heightSpec = heightMeasureSpec;
                if (mFooterHeightStatus.gteReplaceWith(DimensionStatus.XmlLayoutUnNotify)) {
                    heightSpec = makeMeasureSpec(Math.max(mFooterHeight - lp.topMargin - lp.bottomMargin, 0), EXACTLY);
                    footerView.measure(widthSpec, heightSpec);
                } else if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.MatchLayout) {
                    int footerHeight = 0;
                    if (!mFooterHeightStatus.notified) {
                        super.measureChild(footerView, widthSpec, makeMeasureSpec(getSize(heightSpec) - lp.topMargin - lp.bottomMargin, AT_MOST));
                        footerHeight = footerView.getMeasuredHeight();
                    }
                    footerView.measure(widthSpec, makeMeasureSpec(getSize(heightSpec) - lp.topMargin - lp.bottomMargin, EXACTLY));
                    if (footerHeight > 0 && footerHeight != footerView.getMeasuredHeight()) {
                        mHeaderHeight = footerHeight + lp.topMargin + lp.bottomMargin;
                    }
                } else if (lp.height > 0) {
                    if (mFooterHeightStatus.canReplaceWith(DimensionStatus.XmlExactUnNotify)) {
                        mFooterHeight = lp.height + lp.topMargin + lp.bottomMargin;
                        mFooterHeightStatus = DimensionStatus.XmlExactUnNotify;
                    }
                    heightSpec = makeMeasureSpec(lp.height, EXACTLY);
                    footerView.measure(widthSpec, heightSpec);
                } else if (lp.height == WRAP_CONTENT) {
                    heightSpec = makeMeasureSpec(Math.max(getSize(heightMeasureSpec) - lp.topMargin - lp.bottomMargin, 0), AT_MOST);
                    footerView.measure(widthSpec, heightSpec);
                    int measuredHeight = footerView.getMeasuredHeight();
                    if (measuredHeight > 0 && mFooterHeightStatus.canReplaceWith(DimensionStatus.XmlWrapUnNotify)) {
                        mFooterHeightStatus = DimensionStatus.XmlWrapUnNotify;
                        mFooterHeight = footerView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                    } else if (measuredHeight <= 0) {
                        heightSpec = makeMeasureSpec(Math.max(mFooterHeight - lp.topMargin - lp.bottomMargin, 0), EXACTLY);
                        footerView.measure(widthSpec, heightSpec);
                    }
                } else if (lp.height == MATCH_PARENT) {
                    heightSpec = makeMeasureSpec(Math.max(mFooterHeight - lp.topMargin - lp.bottomMargin, 0), EXACTLY);
                    footerView.measure(widthSpec, heightSpec);
                } else {
                    footerView.measure(widthSpec, heightSpec);
                }

                if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale && !isInEditMode) {
                    final int height = Math.max(0, isEnableLoadMore ? -mSpinner : 0);
                    heightSpec = makeMeasureSpec(Math.max(height - lp.topMargin - lp.bottomMargin, 0), EXACTLY);
                    footerView.measure(widthSpec, heightSpec);
                }

                if (!mFooterHeightStatus.notified) {
                    mFooterHeightStatus = mFooterHeightStatus.notified();
                    mRefreshFooter.onInitialized(mKernel, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
//                    mFooterExtendHeight = (int) Math.max((mFooterHeight * (mFooterMaxDragRate - 1)), 0);
//                    mRefreshFooter.onInitialized(mKernel, mFooterHeight, mFooterExtendHeight);

                }

                if (isInEditMode && isEnableLoadMore()) {
                    minimumHeight += footerView.getMeasuredHeight();
                }
            }

            if (mRefreshContent != null && mRefreshContent.getView() == child) {
                final View contentView = mRefreshContent.getView();
                final LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
                final int widthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec,
                        thisView.getPaddingLeft() + thisView.getPaddingRight() +
                                lp.leftMargin + lp.rightMargin, lp.width);
                final int heightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec,
                        thisView.getPaddingTop() + thisView.getPaddingBottom() +
                                lp.topMargin + lp.bottomMargin +
                                ((isInEditMode &&
                                        isEnableRefresh() &&
                                        mRefreshHeader != null &&
                                        (isEnableHeaderTranslationContent || mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind))
                                        ? mHeaderHeight : 0) +
                                ((isInEditMode &&
                                        isEnableLoadMore() &&
                                        mRefreshFooter != null &&
                                        (isEnableFooterTranslationContent || mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind))
                                        ? mFooterHeight : 0),
                        lp.height);
                contentView.measure(widthSpec, heightSpec);
//                mRefreshContent.onInitialHeaderAndFooter(mHeaderHeight, mFooterHeight);
                minimumHeight += contentView.getMeasuredHeight();
            }
        }

        super.setMeasuredDimension(
                View.resolveSize(super.getSuggestedMinimumWidth(), widthMeasureSpec),
                View.resolveSize(minimumHeight, heightMeasureSpec));

        mLastTouchX = thisView.getMeasuredWidth() / 2;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final View thisView = this;
        final int paddingLeft = thisView.getPaddingLeft();
        final int paddingTop = thisView.getPaddingTop();
        final int paddingBottom = thisView.getPaddingBottom();

        for (int i = 0, len = super.getChildCount(); i < len; i++) {
            View child = super.getChildAt(i);

            if (mRefreshContent != null && mRefreshContent.getView() == child) {
                boolean isPreviewMode = thisView.isInEditMode() && isEnablePreviewInEditMode && isEnableRefresh() && mRefreshHeader != null;
                final View contentView = mRefreshContent.getView();
                final LayoutParams lp = (LayoutParams) contentView.getLayoutParams();
                int left = paddingLeft + lp.leftMargin;
                int top = paddingTop + lp.topMargin;
                int right = left + contentView.getMeasuredWidth();
                int bottom = top + contentView.getMeasuredHeight();
                if (isPreviewMode && (isEnableHeaderTranslationContent || mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind)) {
                    top = top + mHeaderHeight;
                    bottom = bottom + mHeaderHeight;
                }

                contentView.layout(left, top, right, bottom);
            }
            if (mRefreshHeader != null && mRefreshHeader.getView() == child) {
                boolean isPreviewMode = thisView.isInEditMode() && isEnablePreviewInEditMode && isEnableRefresh();
                final View headerView = mRefreshHeader.getView();
                final LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                int left = lp.leftMargin;
                int top = lp.topMargin + mHeaderInsetStart;
                int right = left + headerView.getMeasuredWidth();
                int bottom = top + headerView.getMeasuredHeight();
                if (!isPreviewMode) {
                    if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        top = top - mHeaderHeight;
                        bottom = bottom - mHeaderHeight;
                    }
                }
                headerView.layout(left, top, right, bottom);
            }
            if (mRefreshFooter != null && mRefreshFooter.getView() == child) {
                final boolean isPreviewMode = thisView.isInEditMode() && isEnablePreviewInEditMode && isEnableLoadMore();
                final View footerView = mRefreshFooter.getView();
                final LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
                final SpinnerStyle style = mRefreshFooter.getSpinnerStyle();
                int left = lp.leftMargin;
                int top = lp.topMargin + thisView.getMeasuredHeight() - mFooterInsetStart;

                if (isPreviewMode
                        || style == SpinnerStyle.FixedFront
                        || style == SpinnerStyle.FixedBehind) {
                    top = top - mFooterHeight;
                } else if (style == SpinnerStyle.Scale && mSpinner < 0) {
                    top = top - Math.max(isEnableLoadMore() ? -mSpinner : 0, 0);
                }

                int right = left + footerView.getMeasuredWidth();
                int bottom = top + footerView.getMeasuredHeight();
                footerView.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mKernel.moveSpinner(0, true);
        notifyStateChanged(RefreshState.None);
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        isManualLoadMore = true;
        isManualNestedScrolling = true;
        animationRunnable = null;
        if (reboundAnimator != null) {
            reboundAnimator.removeAllListeners();
            reboundAnimator.removeAllUpdateListeners();
            reboundAnimator.cancel();
            reboundAnimator = null;
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final View thisView = this;
        final View contentView = mRefreshContent != null ? mRefreshContent.getView() : null;
        if (mRefreshHeader != null && mRefreshHeader.getView() == child) {
            if (!isEnableRefresh() || (!isEnablePreviewInEditMode && thisView.isInEditMode())) {
                return true;
            }
            if (contentView != null) {
                int bottom = Math.max(contentView.getTop() + contentView.getPaddingTop() + mSpinner, child.getTop());
                if (mHeaderBackgroundColor != 0 && mPaint != null) {
                    mPaint.setColor(mHeaderBackgroundColor);
                    if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale) {
                        bottom = child.getBottom();
                    } else if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                        bottom = child.getBottom() + mSpinner;
                    }
                    canvas.drawRect(child.getLeft(), child.getTop(), child.getRight(), bottom, mPaint);
                }
                if (isEnableClipHeaderWhenFixedBehind && mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), child.getTop(), child.getRight(), bottom);
                    boolean ret = super.drawChild(canvas, child, drawingTime);
                    canvas.restore();
                    return ret;
                }
            }
        }
        if (mRefreshFooter != null && mRefreshFooter.getView() == child) {
            if (!isEnableLoadMore() || (!isEnablePreviewInEditMode && thisView.isInEditMode())) {
                return true;
            }
            if (contentView != null) {
                int top = Math.min(contentView.getBottom() - contentView.getPaddingBottom() + mSpinner, child.getBottom());
                if (mFooterBackgroundColor != 0 && mPaint != null) {
                    mPaint.setColor(mFooterBackgroundColor);
                    if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale) {
                        top = child.getTop();
                    } else if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                        top = child.getTop() + mSpinner;
                    }
                    canvas.drawRect(child.getLeft(), top, child.getRight(), child.getBottom(), mPaint);
                }
                if (isEnableClipFooterWhenFixedBehind && mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                    canvas.save();
                    canvas.clipRect(child.getLeft(), top, child.getRight(), child.getBottom());
                    boolean ret = super.drawChild(canvas, child, drawingTime);
                    canvas.restore();
                    return ret;
                }
            }

        }
        return super.drawChild(canvas, child, drawingTime);
    }

    /**
     * 惯性计算
     */
    @Override
    public void computeScroll() {
        int lastCurY = mScroller.getCurrY();
        if (mScroller.computeScrollOffset()) {
            int finalY = mScroller.getFinalY();
            if ((finalY < 0 && (isEnableOverScrollDrag || isEnableRefresh()) && mRefreshContent.isCanRefresh())
                    || (finalY > 0 && (isEnableOverScrollDrag || isEnableLoadMore()) && mRefreshContent.isCanLoadMore())) {
                if (isVerticalPermit) {
                    float velocity;
                    if (Build.VERSION.SDK_INT >= 14) {
                        velocity = finalY > 0 ? -mScroller.getCurrVelocity() : mScroller.getCurrVelocity();
                    } else {
                        velocity = 1f * (mScroller.getCurrY() - finalY) / Math.max((mScroller.getDuration() - mScroller.timePassed()), 1);
                    }
                    animSpinnerBounce(velocity);
                }
                mScroller.forceFinished(true);
            } else {
                isVerticalPermit = true;//打开竖直通行证
                final View thisView = this;
                thisView.invalidate();
            }
        }
    }

    //</editor-fold> desc="生命周期 life cycle --- end ---">

    //<editor-fold desc="滑动判断 judgement of slide">
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {

        //<editor-fold desc="多点触摸计算代码">
        //---------------------------------------------------------------------------
        //多点触摸计算代码
        //---------------------------------------------------------------------------
        final int action = e.getActionMasked();
        final boolean pointerUp = action == MotionEvent.ACTION_POINTER_UP;
        final int skipIndex = pointerUp ? e.getActionIndex() : -1;

        // Determine focal point
        float sumX = 0, sumY = 0;
        final int count = e.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) continue;
            sumX += e.getX(i);
            sumY += e.getY(i);
        }
        final int div = pointerUp ? count - 1 : count;
        final float touchX = sumX / div;
        final float touchY = sumY / div;
        if ((action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_POINTER_DOWN)
                && isBeingDragged) {
            mTouchY += touchY - mLastTouchY;
        }
        mLastTouchX = touchX;
        mLastTouchY = touchY;
        //---------------------------------------------------------------------------
        //</editor-fold>

        final View thisView = this;
        if (mNestedInProgress) {//嵌套滚动时，补充竖直方向不滚动，但是水平方向滚动，需要通知 onHorizontalDrag
            int totalUnconsumed = mTotalUnconsumed;
            boolean ret = super.dispatchTouchEvent(e);
            //noinspection ConstantConditions
            if (action == MotionEvent.ACTION_MOVE) {
                if (totalUnconsumed == mTotalUnconsumed) {
                    final int offsetX = (int) mLastTouchX;
                    final int offsetMax = thisView.getWidth();
                    final float percentX = mLastTouchX / (offsetMax == 0 ? 1 : offsetMax);
                    if (isEnableRefresh() && mSpinner > 0 && mRefreshHeader != null && mRefreshHeader.isSupportHorizontalDrag()) {
                        mRefreshHeader.onHorizontalDrag(percentX, offsetX, offsetMax);
                    } else if (isEnableLoadMore() && mSpinner < 0 && mRefreshFooter != null && mRefreshFooter.isSupportHorizontalDrag()) {
                        mRefreshFooter.onHorizontalDrag(percentX, offsetX, offsetMax);
                    }
                }
            }
            return ret;
        } else if (!thisView.isEnabled()
                || (!isEnableRefresh() && !isEnableLoadMore() && !isEnableOverScrollDrag)
                || (isHeaderNeedTouchEventWhenRefreshing && ((mState.isOpening || mState.isFinishing) && mState.isHeader))
                || (isFooterNeedTouchEventWhenLoading && ((mState.isOpening || mState.isFinishing) && mState.isFooter))) {
            return super.dispatchTouchEvent(e);
        }

        if (interceptAnimatorByAction(action) || mState.isFinishing
                || (mState == RefreshState.Loading && isDisableContentWhenLoading)
                || (mState == RefreshState.Refreshing && isDisableContentWhenRefresh)) {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                /*----------------------------------------------------*/
                /*                   速度追踪初始化                    */
                /*----------------------------------------------------*/
                mCurrentVelocity = 0;
                mVelocityTracker.addMovement(e);
                mScroller.forceFinished(true);
                /*----------------------------------------------------*/
                /*                   触摸事件初始化                    */
                /*----------------------------------------------------*/
                mTouchX = touchX;
                mTouchY = touchY;
                mLastSpinner = 0;
                mTouchSpinner = mSpinner;
                isBeingDragged = false;
                /*----------------------------------------------------*/
                isSuperDispatchTouchEvent = super.dispatchTouchEvent(e);
                if (mState == RefreshState.TwoLevel && mTouchY < 5 * thisView.getMeasuredHeight() / 6) {
                    mDragDirection = 'h';//二级刷新标记水平滚动来禁止拖动
                    return isSuperDispatchTouchEvent;
                }
                if (mRefreshContent != null) {
                    //为 RefreshContent 传递当前触摸事件的坐标，用于智能判断对应坐标位置View的滚动边界和相关信息
                    mRefreshContent.onActionDown(e);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = touchX - mTouchX;
                float dy = touchY - mTouchY;
                mVelocityTracker.addMovement(e);//速度追踪
                if (!isBeingDragged && mDragDirection != 'h' && mRefreshContent != null) {//没有拖动之前，检测  canRefresh canLoadMore 来开启拖动
                    if (mDragDirection == 'v' || (Math.abs(dy) >= mTouchSlop && Math.abs(dx) < Math.abs(dy))) {//滑动允许最大角度为45度
                        mDragDirection = 'v';
                        if (dy > 0 && (mSpinner < 0 || ((isEnableOverScrollDrag || isEnableRefresh()) && mRefreshContent.isCanRefresh()))) {
                            isBeingDragged = true;
                            mTouchY = touchY - mTouchSlop;//调整 mTouchSlop 偏差
                        } else if (dy < 0 && (mSpinner > 0 || ((isEnableOverScrollDrag || isEnableLoadMore()) && ((mState == RefreshState.Loading && isFooterLocked) || mRefreshContent.isCanLoadMore())))) {
                            isBeingDragged = true;
                            mTouchY = touchY + mTouchSlop;//调整 mTouchSlop 偏差
                        }
                        if (isBeingDragged) {
                            dy = touchY - mTouchY;//调整 mTouchSlop 偏差 重新计算 dy
                            if (isSuperDispatchTouchEvent) {//如果父类拦截了事件，发送一个取消事件通知
                                e.setAction(MotionEvent.ACTION_CANCEL);
                                super.dispatchTouchEvent(e);
                            }
                            if (mSpinner > 0 || (mSpinner == 0 && dy > 0)) {
                                mKernel.setState(RefreshState.PullDownToRefresh);
                            } else {
                                mKernel.setState(RefreshState.PullUpToLoad);
                            }
                            thisView.getParent().requestDisallowInterceptTouchEvent(true);//通知父控件不要拦截事件
                        }
                    } else if (Math.abs(dx) >= mTouchSlop && Math.abs(dx) > Math.abs(dy) && mDragDirection != 'v') {
                        mDragDirection = 'h';//标记为水平拖动，将无法再次触发 下拉刷新 上拉加载
                    }
                }
                if (isBeingDragged) {
                    int spinner = (int) dy + mTouchSpinner;
                    if ((mViceState.isHeader && (spinner < 0 || mLastSpinner < 0)) || (mViceState.isFooter && (spinner > 0 || mLastSpinner > 0))) {
                        mLastSpinner = spinner;
                        long time = e.getEventTime();
                        if (mFalsifyEvent == null) {
                            mFalsifyEvent = obtain(time, time, MotionEvent.ACTION_DOWN, mTouchX + dx, mTouchY, 0);
                            super.dispatchTouchEvent(mFalsifyEvent);
                        }
                        MotionEvent em = obtain(time, time, MotionEvent.ACTION_MOVE, mTouchX + dx, mTouchY + spinner, 0);
                        super.dispatchTouchEvent(em);
                        if (isFooterLocked && dy > mTouchSlop && mSpinner < 0) {
                            isFooterLocked = false;//内容向下滚动时 解锁Footer 的锁定
                        }
                        if (spinner > 0 && ((isEnableOverScrollDrag || isEnableRefresh()) && mRefreshContent.isCanRefresh())) {
                            mTouchY = mLastTouchY = touchY;
                            mTouchSpinner = spinner = 0;
                            mKernel.setState(RefreshState.PullDownToRefresh);
                        } else if (spinner < 0 && ((isEnableOverScrollDrag || isEnableLoadMore()) && mRefreshContent.isCanLoadMore())) {
                            mTouchY = mLastTouchY = touchY;
                            mTouchSpinner = spinner = 0;
                            mKernel.setState(RefreshState.PullUpToLoad);
                        }
                        if ((mViceState.isHeader && spinner < 0) || (mViceState.isFooter && spinner > 0)) {
                            if (mSpinner != 0) {
                                moveSpinnerInfinitely(0);
                            }
                            return true;
                        } else if (mFalsifyEvent != null) {
                            mFalsifyEvent = null;
                            em.setAction(MotionEvent.ACTION_CANCEL);
                            super.dispatchTouchEvent(em);
                        }
                        em.recycle();
                    }
                    moveSpinnerInfinitely(spinner);
                    return true;
                } else if (isFooterLocked && dy > mTouchSlop && mSpinner < 0) {
                    isFooterLocked = false;//内容向下滚动时 解锁Footer 的锁定
                }
                break;
            case MotionEvent.ACTION_UP://向上抬起时处理速度追踪
                mVelocityTracker.addMovement(e);
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                mCurrentVelocity = (int) mVelocityTracker.getYVelocity();
                startFlingIfNeed(null);
            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.clear();//清空速度追踪器
                mDragDirection = 'n';//关闭拖动方向
                if (mFalsifyEvent != null) {
                    mFalsifyEvent.recycle();
                    mFalsifyEvent = null;
                    long time = e.getEventTime();
                    MotionEvent ec = obtain(time, time, action, mTouchX, touchY, 0);
                    super.dispatchTouchEvent(ec);
                    ec.recycle();
                }
                overSpinner();
                if (isBeingDragged) {
                    isBeingDragged = false;//关闭拖动状态
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(e);
    }

    /**
     * @param flingVelocity 速度
     * @return true 可以拦截 嵌套滚动的 Fling
     */
    protected boolean startFlingIfNeed(Float flingVelocity) {
        final float velocity = flingVelocity == null ? mCurrentVelocity : flingVelocity;
        if (Math.abs(velocity) > mMinimumVelocity) {
            if (velocity * mSpinner < 0) {
                if (mState.isOpening) {
                    if (mState != RefreshState.TwoLevel && mState != mViceState) {
                        /*
                         * 解决刷新时，惯性丢失问题
                         * 速度方向匹配并且不能是二楼打开状态
                         * 副操作状态:loading refreshing noMoreData
                         */
                        animationRunnable = new FlingRunnable(velocity).start();
                        return true;
                    }
                } else if (mSpinner > mHeaderHeight * mHeaderTriggerRate || -mSpinner > mFooterHeight * mFooterTriggerRate) {
                    return true;//拦截嵌套滚动时，即将刷新或者加载的 Fling
                }
            }
            if ((velocity < 0 && ((mEnableOverScrollBounce && (isEnableOverScrollDrag || isEnableLoadMore())) || (mState == RefreshState.Loading && mSpinner >= 0) || (isEnableAutoLoadMore && isEnableLoadMore())))
                    || (velocity > 0 && ((mEnableOverScrollBounce && (isEnableOverScrollDrag || isEnableRefresh())) || (mState == RefreshState.Refreshing && mSpinner <= 0)))) {
                isVerticalPermit = false;//关闭竖直通行证
                mScroller.fling(0, 0, 0, (int) -velocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                mScroller.computeScrollOffset();
                final View thisView = this;
                thisView.invalidate();
            }
        }
        return false;
    }

    /*
     * 在动画执行时，触摸屏幕，打断动画，转为拖动状态
     */
    protected boolean interceptAnimatorByAction(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            animationRunnable = null;
            if (reboundAnimator != null) {
                if (mState.isFinishing) {
                    return true;
                }
                if (mState == RefreshState.PullDownCanceled) {
                    mKernel.setState(RefreshState.PullDownToRefresh);
                } else if (mState == RefreshState.PullUpCanceled) {
                    mKernel.setState(RefreshState.PullUpToLoad);
                }
                reboundAnimator.cancel();
                reboundAnimator = null;
            }
        }
        return reboundAnimator != null;
    }
    //</editor-fold dec ="滑动判断 judgement of slide --- end ---">

    //<editor-fold desc="状态更改 state changes">
    protected void notifyStateChanged(RefreshState state) {
        final RefreshState oldState = mState;
        if (oldState != state) {
            mState = state;
            mViceState = state;
            final OnStateChangedListener refreshHeader = mRefreshHeader;
            final OnStateChangedListener refreshFooter = mRefreshFooter;
            final OnStateChangedListener refreshListener = mOnMultiPurposeListener;
            if (refreshHeader != null) {
                refreshHeader.onStateChanged(this, oldState, state);
            }
            if (refreshFooter != null) {
                refreshFooter.onStateChanged(this, oldState, state);
            }
            if (refreshListener != null) {
                refreshListener.onStateChanged(this, oldState, state);
            }
        }
    }

    protected void setStateDirectLoading() {
        if (mState != RefreshState.Loading) {
            mLastOpenTime = currentTimeMillis();
            isFooterLocked = true;
            notifyStateChanged(RefreshState.Loading);
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore(this);
            } else if (mOnMultiPurposeListener == null) {
                finishLoadMore(2000);
            }
            if (mRefreshFooter != null) {
                mRefreshFooter.onStartAnimator(this, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
            }
            if (mOnMultiPurposeListener != null && mRefreshFooter instanceof RefreshFooter) {
                mOnMultiPurposeListener.onLoadMore(this);
                mOnMultiPurposeListener.onFooterStartAnimator((RefreshFooter) mRefreshFooter, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
            }
        }
    }

    protected void setStateLoading() {
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setStateDirectLoading();
            }
        };
        notifyStateChanged(RefreshState.LoadReleased);
        ValueAnimator animator = mKernel.animSpinner(-mFooterHeight);
        if (animator != null) {
            animator.addListener(listener);
        }
        if (mRefreshFooter != null) {
            //onReleased 的执行顺序定在 animSpinner 之后 onAnimationEnd 之前
            // 这样 onReleased 内部 可以做出 对 前面 animSpinner 的覆盖 操作
            mRefreshFooter.onReleased(this, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
        }
        if (mOnMultiPurposeListener != null && mRefreshFooter instanceof RefreshFooter) {
            //同 mRefreshFooter.onReleased 一致
            mOnMultiPurposeListener.onFooterReleased((RefreshFooter) mRefreshFooter, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
        }
        if (animator == null) {
            //onAnimationEnd 会改变状态为 loading 必须在 onReleased 之后调用
            listener.onAnimationEnd(null);
        }
    }

    protected void setStateRefreshing() {
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLastOpenTime = currentTimeMillis();
                notifyStateChanged(RefreshState.Refreshing);
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh(ZRefreshLayout.this);
                } else if (mOnMultiPurposeListener == null) {
                    finishRefresh(3000);
                }
                if (mRefreshHeader != null) {
                    mRefreshHeader.onStartAnimator(ZRefreshLayout.this, mHeaderHeight, (int) (mHeaderMaxDragRate * mHeaderHeight));
                }
                if (mOnMultiPurposeListener != null && mRefreshHeader instanceof RefreshHeader) {
                    mOnMultiPurposeListener.onRefresh(ZRefreshLayout.this);
                    mOnMultiPurposeListener.onHeaderStartAnimator((RefreshHeader) mRefreshHeader, mHeaderHeight, (int) (mHeaderMaxDragRate * mHeaderHeight));
                }
            }
        };
        notifyStateChanged(RefreshState.RefreshReleased);
        ValueAnimator animator = mKernel.animSpinner(mHeaderHeight);
        if (animator != null) {
            animator.addListener(listener);
        }
        if (mRefreshHeader != null) {
            //onReleased 的执行顺序定在 animSpinner 之后 onAnimationEnd 之前
            // 这样 onRefreshReleased内部 可以做出 对 前面 animSpinner 的覆盖 操作
            mRefreshHeader.onReleased(this, mHeaderHeight, (int) (mHeaderMaxDragRate * mHeaderHeight));
        }
        if (mOnMultiPurposeListener != null && mRefreshHeader instanceof RefreshHeader) {
            //同 mRefreshHeader.onReleased 一致
            mOnMultiPurposeListener.onHeaderReleased((RefreshHeader) mRefreshHeader, mHeaderHeight, (int) (mHeaderMaxDragRate * mHeaderHeight));
        }
        if (animator == null) {
            //onAnimationEnd 会改变状态为 Refreshing 必须在 onReleased 之后调用
            listener.onAnimationEnd(null);
        }
    }

    /**
     * 重置状态
     */
    protected void resetStatus() {
        if (mState != RefreshState.None) {
            if (mSpinner == 0) {
                notifyStateChanged(RefreshState.None);
            }
        }
        if (mSpinner != 0) {
            mKernel.animSpinner(0);
        }
    }

    protected void setViceState(RefreshState state) {
        if (mState.isDragging && mState.isHeader != state.isHeader) {
            notifyStateChanged(RefreshState.None);
        }
        if (mViceState != state) {
            mViceState = state;
        }
    }

    //</editor-fold dec ="状态更改 state changes --- end ---">

    //<editor-fold desc="视图位移 displacement">
    /*
     * 执行回弹动画
     */
    protected ValueAnimator animSpinner(int endSpinner, int startDelay, Interpolator interpolator, int duration) {
        if (mSpinner != endSpinner) {
            if (reboundAnimator != null) {
                reboundAnimator.cancel();
            }
            animationRunnable = null;
            reboundAnimator = ValueAnimator.ofInt(mSpinner, endSpinner);
            reboundAnimator.setDuration(duration);
            reboundAnimator.setInterpolator(interpolator);
            reboundAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    reboundAnimator = null;
                    if (mSpinner == 0) {
                        if (mState != RefreshState.None && !mState.isOpening) {
                            notifyStateChanged(RefreshState.None);
                        }
                    } else if (mState != mViceState) {
                        setViceState(mState);
                    }
                }
            });
            reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mKernel.moveSpinner((int) animation.getAnimatedValue(), false);
                }
            });
            reboundAnimator.setStartDelay(startDelay);
//            reboundAnimator.setDuration(20000);
            reboundAnimator.start();
            return reboundAnimator;
        }
        return null;
    }

    /*
     * 越界回弹动画
     */
    protected void animSpinnerBounce(final float velocity) {
        if (reboundAnimator == null) {
            if (velocity > 0 && (mState == RefreshState.Refreshing || mState == RefreshState.TwoLevel)) {
                animationRunnable = new BounceRunnable(velocity, mHeaderHeight);
            } else if (velocity < 0 && (mState == RefreshState.Loading
                    || (isEnableFooterFollowWhenLoadFinished && isFooterNoMoreData && isEnableLoadMore())
                    || (isEnableAutoLoadMore && !isFooterNoMoreData && isEnableLoadMore() && mState != RefreshState.Refreshing))) {
                animationRunnable = new BounceRunnable(velocity, -mFooterHeight);
            } else if (mSpinner == 0 && mEnableOverScrollBounce) {
                animationRunnable = new BounceRunnable(velocity, 0);
            }
        }
    }

    /*
     * 手势拖动结束
     * 开始执行回弹动画
     */
    protected void overSpinner() {
        if (mState == RefreshState.TwoLevel) {
            final View thisView = this;
            if (mCurrentVelocity > -1000 && mSpinner > thisView.getMeasuredHeight() / 2) {
                ValueAnimator animator = mKernel.animSpinner(thisView.getMeasuredHeight());
                if (animator != null) {
                    animator.setDuration(mFloorDuration);
                }
            } else if (isBeingDragged) {
                mKernel.finishTwoLevel();
            }
        } else if (mState == RefreshState.Loading
                || (isEnableFooterFollowWhenLoadFinished && isFooterNoMoreData && mSpinner < 0 && isEnableLoadMore())) {
            if (mSpinner < -mFooterHeight) {
                mKernel.animSpinner(-mFooterHeight);
            } else if (mSpinner > 0) {
                mKernel.animSpinner(0);
            }
        } else if (mState == RefreshState.Refreshing) {
            if (mSpinner > mHeaderHeight) {
                mKernel.animSpinner(mHeaderHeight);
            } else if (mSpinner < 0) {
                mKernel.animSpinner(0);
            }
        } else if (mState == RefreshState.PullDownToRefresh) {
            mKernel.setState(RefreshState.PullDownCanceled);
        } else if (mState == RefreshState.PullUpToLoad) {
            mKernel.setState(RefreshState.PullUpCanceled);
        } else if (mState == RefreshState.ReleaseToRefresh) {
            setStateRefreshing();
        } else if (mState == RefreshState.ReleaseToLoad) {
            setStateLoading();
        } else if (mState == RefreshState.ReleaseToTwoLevel) {
            mKernel.setState(RefreshState.TwoLevelReleased);
        } else if (mSpinner != 0) {
            mKernel.animSpinner(0);
        }
    }

    protected void moveSpinnerInfinitely(float spinner) {
        final View thisView = this;
        if (mState == RefreshState.TwoLevel && spinner > 0) {
            mKernel.moveSpinner(Math.min((int) spinner, thisView.getMeasuredHeight()), true);
        } else if (mState == RefreshState.Refreshing && spinner >= 0) {
            if (spinner < mHeaderHeight) {
                mKernel.moveSpinner((int) spinner, true);
            } else {
                final double M = (mHeaderMaxDragRate - 1) * mHeaderHeight;
                final double H = Math.max(mScreenHeightPixels * 4 / 3, thisView.getHeight()) - mHeaderHeight;
                final double x = Math.max(0, (spinner - mHeaderHeight) * mDragRate);
                final double y = Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
                mKernel.moveSpinner((int) y + mHeaderHeight, true);
            }
        } else if (spinner < 0 && (mState == RefreshState.Loading
                || (isEnableFooterFollowWhenLoadFinished && isFooterNoMoreData && isEnableLoadMore())
                || (isEnableAutoLoadMore && !isFooterNoMoreData && isEnableLoadMore()))) {
            if (spinner > -mFooterHeight) {
                mKernel.moveSpinner((int) spinner, true);
            } else {
                final double M = (mFooterMaxDragRate - 1) * mFooterHeight;
                final double H = Math.max(mScreenHeightPixels * 4 / 3, thisView.getHeight()) - mFooterHeight;
                final double x = -Math.min(0, (spinner + mFooterHeight) * mDragRate);
                final double y = -Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
                mKernel.moveSpinner((int) y - mFooterHeight, true);
            }
        } else if (spinner >= 0) {
            final double M = mHeaderMaxDragRate * mHeaderHeight;
            final double H = Math.max(mScreenHeightPixels / 2, thisView.getHeight());
            final double x = Math.max(0, spinner * mDragRate);
            final double y = Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
            mKernel.moveSpinner((int) y, true);
        } else {
            final double M = mFooterMaxDragRate * mFooterHeight;
            final double H = Math.max(mScreenHeightPixels / 2, thisView.getHeight());
            final double x = -Math.min(0, spinner * mDragRate);
            final double y = -Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
            mKernel.moveSpinner((int) y, true);
        }
        if (isEnableAutoLoadMore && !isFooterNoMoreData && isEnableLoadMore() && spinner < 0
                && mState != RefreshState.Refreshing
                && mState != RefreshState.Loading
                && mState != RefreshState.LoadFinish) {
            setStateDirectLoading();
            if (isDisableContentWhenLoading) {
                animationRunnable = null;
                mKernel.animSpinner(-mFooterHeight);
            }
        }
    }
    //</editor-fold dec ="视图位移 displacement --- end --- >

    //<editor-fold desc="嵌套滚动 NestedScrolling">

    //<editor-fold desc="NestedScrollingParent">

    @Override
    public int getNestedScrollAxes() {
        return mNestedParent.getNestedScrollAxes();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        final View thisView = this;
        boolean accepted = thisView.isEnabled() && isNestedScrollingEnabled() && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        accepted = accepted && (isEnableOverScrollDrag || isEnableRefresh() || isEnableLoadMore());
        return accepted;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        // Reset the counter of how much leftover scroll needs to be consumed.
        mNestedParent.onNestedScrollAccepted(child, target, axes);
        // Dispatch up to the nested parent
        mNestedChild.startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mTotalUnconsumed = mSpinner;//0;
        mNestedInProgress = true;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        // If we are in the middle of consuming, a scroll, then we want to move the spinner back up
        // before allowing the list to scroll
        int consumedY = 0;

        if (dy * mTotalUnconsumed > 0) {
            if (Math.abs(dy) > Math.abs(mTotalUnconsumed)) {
                consumedY = mTotalUnconsumed;
                mTotalUnconsumed = 0;
            } else {
                consumedY = dy;
                mTotalUnconsumed -= dy;
            }
            moveSpinnerInfinitely(mTotalUnconsumed);
            if (mViceState.isOpening || mViceState == RefreshState.None) {
                if (mSpinner > 0) {
                    mKernel.setState(RefreshState.PullDownToRefresh);
                } else {
                    mKernel.setState(RefreshState.PullUpToLoad);
                }
            }
        } else if (dy > 0 && isFooterLocked) {
            consumedY = dy;
            mTotalUnconsumed -= dy;
            moveSpinnerInfinitely(mTotalUnconsumed);
        }

        // Now let our nested parent consume the leftovers
        mNestedChild.dispatchNestedPreScroll(dx, dy - consumedY, consumed, null);
        consumed[1] += consumedY;

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // Dispatch up to the nested parent first
        mNestedChild.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, mParentOffsetInWindow);

        // This is a bit of a hack. Nested scrolling works from the bottom up, and as we are
        // sometimes between two nested scrolling views, we need a way to be able to know when any
        // nested scrolling parent has stopped handling events. We do that by using the
        // 'offset in window 'functionality to see if we have been moved from the event.
        // This is a decent indication of whether we should take over the event stream or not.
        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        if (dy != 0 && (isEnableOverScrollDrag || (dy < 0 && isEnableRefresh()) || (dy > 0 && isEnableLoadMore()))) {
            if (mViceState == RefreshState.None) {
                mKernel.setState(dy > 0 ? RefreshState.PullUpToLoad : RefreshState.PullDownToRefresh);
            }
            moveSpinnerInfinitely(mTotalUnconsumed -= dy);
        }

    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return isFooterLocked && velocityY > 0 || startFlingIfNeed(-velocityY) || mNestedChild.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return mNestedChild.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        mNestedParent.onStopNestedScroll(target);
        mNestedInProgress = false;
        // Finish the spinner for nested scrolling if we ever consumed any
        // unconsumed nested scroll
        mTotalUnconsumed = 0;
        overSpinner();
        // Dispatch up our nested parent
        mNestedChild.stopNestedScroll();
    }

    //</editor-fold dec = "NestedScrollingParent --- end ---">

    //<editor-fold desc="NestedScrollingChild">
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        isManualNestedScrolling = true;
        mNestedChild.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedChild.isNestedScrollingEnabled();
    }

    @Override
    public boolean canScrollVertically(int direction) {
        View target = mRefreshContent.getScrollableView();
        if (direction < 0) {
            return isEnableOverScrollDrag || isEnableRefresh() || ScrollBoundaryUtil.isCanScrollUp(target);
        } else if (direction > 0) {
            return isEnableOverScrollDrag || isEnableLoadMore() || ScrollBoundaryUtil.isCanScrollDown(target);
        }
        return true;
    }

    //</editor-fold dec = "NestedScrollingChild  --- end ---">

    //</editor-fold desc="嵌套滚动 NestedScrolling --- end ---">

    //<editor-fold desc="开放接口 open interface">
    @Override
    public ZRefreshLayout setFooterHeight(float heightDp) {
        if (mFooterHeightStatus.canReplaceWith(DimensionStatus.CodeExact)) {
            mFooterHeight = dp2px(heightDp);
//            mFooterExtendHeight = (int) Math.max((mFooterHeight * (mFooterMaxDragRate - 1)), 0);
            mFooterHeightStatus = DimensionStatus.CodeExactUnNotify;
            if (mRefreshFooter != null) {
                mRefreshFooter.getView().requestLayout();
            }
        }
        return this;

    }


    @Override
    public ZRefreshLayout setHeaderHeight(float heightDp) {
        if (mHeaderHeightStatus.canReplaceWith(DimensionStatus.CodeExact)) {
            mHeaderHeight = dp2px(heightDp);
//            mHeaderExtendHeight = (int) Math.max((mHeaderHeight * (mHeaderMaxDragRate - 1)), 0);
            mHeaderHeightStatus = DimensionStatus.CodeExactUnNotify;
            if (mRefreshHeader != null) {
                mRefreshHeader.getView().requestLayout();
            }
        }
        return this;
    }


    @Override
    public ZRefreshLayout setHeaderInsetStart(float insetDp) {
        mHeaderInsetStart = dp2px(insetDp);
        return this;
    }


    @Override
    public ZRefreshLayout setFooterInsetStart(float insetDp) {
        mFooterInsetStart = dp2px(insetDp);
        return this;
    }

    /**
     * @param rate 显示拖动高度/真实拖动高度 比率
     * @return RefreshLayout
     */
    @Override
    public ZRefreshLayout setDragRate(float rate) {
        this.mDragRate = rate;
        return this;
    }

    /**
     * 设置下拉最大高度和Header高度的比率（将会影响可以下拉的最大高度）
     *
     * @param rate 下拉最大高度和Header高度的比率
     */
    @Override
    public ZRefreshLayout setHeaderMaxDragRate(float rate) {
        this.mHeaderMaxDragRate = rate;
//        this.mHeaderExtendHeight = (int) Math.max((mHeaderHeight * (mHeaderMaxDragRate - 1)), 0);
        if (mRefreshHeader != null && mHandler != null) {
            mRefreshHeader.onInitialized(mKernel, mHeaderHeight, (int) (mHeaderMaxDragRate * mHeaderHeight));
        } else {
            mHeaderHeightStatus = mHeaderHeightStatus.unNotify();
        }
        return this;
    }

    /**
     * 设置上拉最大高度和Footer高度的比率（将会影响可以上拉的最大高度）
     *
     * @param rate 上拉最大高度和Footer高度的比率
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setFooterMaxDragRate(float rate) {
        this.mFooterMaxDragRate = rate;
//        this.mFooterExtendHeight = (int) Math.max((mFooterHeight * (mFooterMaxDragRate - 1)), 0);
        if (mRefreshFooter != null && mHandler != null) {
            mRefreshFooter.onInitialized(mKernel, mFooterHeight, (int) (mFooterHeight * mFooterMaxDragRate));
        } else {
            mFooterHeightStatus = mFooterHeightStatus.unNotify();
        }
        return this;
    }

    /**
     * 设置 触发刷新距离 与 HeaderHeight 的比率
     *
     * @param rate 触发刷新距离 与 HeaderHeight 的比率
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setHeaderTriggerRate(float rate) {
        this.mHeaderTriggerRate = rate;
        return this;
    }

    /**
     * 设置 触发加载距离 与 FooterHeight 的比率
     *
     * @param rate 触发加载距离 与 FooterHeight 的比率
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setFooterTriggerRate(float rate) {
        this.mFooterTriggerRate = rate;
        return this;
    }

    /**
     * 设置回弹显示插值器
     *
     * @param interpolator 动画插值器
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setReboundInterpolator(@NonNull Interpolator interpolator) {
        this.mReboundInterpolator = interpolator;
        return this;
    }

    /**
     * 设置回弹动画时长
     *
     * @param duration 时长
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setReboundDuration(int duration) {
        this.mReboundDuration = duration;
        return this;
    }

    /**
     * 设置是否启用上拉加载更多（默认启用）
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableLoadMore(boolean enabled) {
        this.isManualLoadMore = true;
        this.isEnableLoadMore = enabled;
        return this;
    }

    /**
     * 是否启用下拉刷新（默认启用）
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableRefresh(boolean enabled) {
        this.isEnableRefresh = enabled;
        return this;
    }

    /**
     * 设置是否启用内容视图拖动效果
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableHeaderTranslationContent(boolean enabled) {
        this.isEnableHeaderTranslationContent = enabled;
        this.isManualHeaderTranslationContent = true;
        return this;
    }

    /**
     * 设置是否启用内容视图拖动效果
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableFooterTranslationContent(boolean enabled) {
        this.isEnableFooterTranslationContent = enabled;
        return this;
    }

    /**
     * 设置是否监听列表在滚动到底部时触发加载事件
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableAutoLoadMore(boolean enabled) {
        this.isEnableAutoLoadMore = enabled;
        return this;
    }

    /**
     * 设置是否启用越界回弹
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableOverScrollBounce(boolean enabled) {
        this.mEnableOverScrollBounce = enabled;
        return this;
    }

    /**
     * 设置是否开启纯滚动模式
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnablePureScrollMode(boolean enabled) {
        this.isEnablePureScrollMode = enabled;
        return this;
    }

    /**
     * 设置是否在加载更多完成之后滚动内容显示新数据
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableScrollContentWhenLoaded(boolean enabled) {
        this.isEnableScrollContentWhenLoaded = enabled;
        return this;
    }

    /**
     * 是否在刷新完成之后滚动内容显示新数据
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableScrollContentWhenRefreshed(boolean enabled) {
        this.isEnableScrollContentWhenRefreshed = enabled;
        return this;
    }

    /**
     * 设置在内容不满一页的时候，是否可以上拉加载更多
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableLoadMoreWhenContentNotFull(boolean enabled) {
        this.isEnableLoadMoreWhenContentNotFull = enabled;
        if (mRefreshContent != null) {
            mRefreshContent.setEnableLoadMoreWhenContentNotFull(enabled);
        }
        return this;
    }

    /**
     * 设置是否启用越界拖动（仿苹果效果）
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableOverScrollDrag(boolean enabled) {
        this.isEnableOverScrollDrag = enabled;
        return this;
    }

    /**
     * 设置是否在全部加载结束之后Footer跟随内容
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableFooterFollowWhenLoadFinished(boolean enabled) {
        this.isEnableFooterFollowWhenLoadFinished = enabled;
        return this;
    }

    /**
     * 设置是否 当 Header FixedBehind 时候是否剪裁遮挡 Header
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableClipHeaderWhenFixedBehind(boolean enabled) {
        this.isEnableClipHeaderWhenFixedBehind = enabled;
        return this;
    }

    /**
     * 设置是否 当 Footer FixedBehind 时候是否剪裁遮挡 Footer
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setEnableClipFooterWhenFixedBehind(boolean enabled) {
        this.isEnableClipFooterWhenFixedBehind = enabled;
        return this;
    }

    /**
     * 设置是会否启用嵌套滚动功能（默认关闭+智能开启）
     *
     * @param enabled 是否启用
     * @return ZRefreshLayout
     */
    @Override
    public RefreshLayout setEnableNestedScroll(boolean enabled) {
        setNestedScrollingEnabled(enabled);
        return this;
    }

    /**
     * 设置是否开启在刷新时候禁止操作内容视图
     *
     * @param disable 是否禁止
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setDisableContentWhenRefresh(boolean disable) {
        this.isDisableContentWhenRefresh = disable;
        return this;
    }

    /**
     * 设置是否开启在加载时候禁止操作内容视图
     *
     * @param disable 是否禁止
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setDisableContentWhenLoading(boolean disable) {
        this.isDisableContentWhenLoading = disable;
        return this;
    }

    /**
     * 设置指定的 Header
     *
     * @param header 刷新头
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setRefreshHeader(@NonNull RefreshHeader header) {
        return setRefreshHeader(header, MATCH_PARENT, WRAP_CONTENT);
    }

    /**
     * 设置指定的 Header
     *
     * @param header 刷新头
     * @param width  宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height 高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setRefreshHeader(@NonNull RefreshHeader header, int width, int height) {
        if (mRefreshHeader != null) {
            super.removeView(mRefreshHeader.getView());
        }
        this.mRefreshHeader = header;
        this.mHeaderBackgroundColor = 0;
        this.isHeaderNeedTouchEventWhenRefreshing = false;
        this.mHeaderHeightStatus = mHeaderHeightStatus.unNotify();
        if (header.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.addView(mRefreshHeader.getView(), 0, new LayoutParams(width, height));
        } else {
            super.addView(mRefreshHeader.getView(), width, height);
        }
        return this;
    }

    /**
     * 设置指定的 Footer
     *
     * @param footer 刷新尾巴
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setRefreshFooter(@NonNull RefreshFooter footer) {
        return setRefreshFooter(footer, MATCH_PARENT, WRAP_CONTENT);
    }

    /**
     * 设置指定的 Footer
     *
     * @param footer 刷新尾巴
     * @param width  宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height 高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setRefreshFooter(@NonNull RefreshFooter footer, int width, int height) {
        if (mRefreshFooter != null) {
            super.removeView(mRefreshFooter.getView());
        }
        this.mRefreshFooter = footer;
        this.mFooterBackgroundColor = 0;
        this.isFooterNeedTouchEventWhenLoading = false;
        this.mFooterHeightStatus = mFooterHeightStatus.unNotify();
        this.isEnableLoadMore = !isManualLoadMore || isEnableLoadMore;
        if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.addView(mRefreshFooter.getView(), 0, new LayoutParams(width, height));
        } else {
            super.addView(mRefreshFooter.getView(), width, height);
        }
        return this;
    }

    /**
     * 设置指定的Content
     *
     * @param content 内容视图
     * @return ZRefreshLayout
     */
    @Override
    public RefreshLayout setRefreshContent(@NonNull View content) {
        return setRefreshContent(content, MATCH_PARENT, MATCH_PARENT);
    }

    /**
     * 设置指定的 Content
     *
     * @param content 内容视图
     * @param width   宽度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @param height  高度 可以使用 MATCH_PARENT, WRAP_CONTENT
     * @return ZRefreshLayout
     */
    @Override
    public RefreshLayout setRefreshContent(@NonNull View content, int width, int height) {
        final View thisView = this;
        if (mRefreshContent != null) {
            super.removeView(mRefreshContent.getView());
        }
        super.addView(content, 0, new LayoutParams(width, height));
        if (mRefreshHeader != null && mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.bringChildToFront(content);
            if (mRefreshFooter != null && mRefreshFooter.getSpinnerStyle() != SpinnerStyle.FixedBehind) {
                super.bringChildToFront(mRefreshFooter.getView());
            }
        } else if (mRefreshFooter != null && mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
            super.bringChildToFront(content);
            if (mRefreshHeader != null && mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                super.bringChildToFront(mRefreshHeader.getView());
            }
        }
        mRefreshContent = new RefreshContentWrapper(content);
        if (mHandler != null) {
            View fixedHeaderView = mFixedHeaderViewId > 0 ? thisView.findViewById(mFixedHeaderViewId) : null;
            View fixedFooterView = mFixedFooterViewId > 0 ? thisView.findViewById(mFixedFooterViewId) : null;

            mRefreshContent.setScrollBoundaryDecider(mScrollBoundaryDecider);
            mRefreshContent.setEnableLoadMoreWhenContentNotFull(isEnableLoadMoreWhenContentNotFull);
            mRefreshContent.setUpComponent(mKernel, fixedHeaderView, fixedFooterView);
        }
        return this;
    }

    /**
     * 获取底部上拉组件的实现
     *
     * @return RefreshFooter
     */
    @Nullable
    @Override
    public RefreshFooter getRefreshFooter() {
        return mRefreshFooter instanceof RefreshFooter ? (RefreshFooter) mRefreshFooter : null;
    }

    /**
     * 获取顶部下拉组件的实现
     *
     * @return RefreshHeader
     */
    @Nullable
    @Override
    public RefreshHeader getRefreshHeader() {
        return mRefreshHeader instanceof RefreshHeader ? (RefreshHeader) mRefreshHeader : null;
    }

    /**
     * 获取状态
     *
     * @return RefreshState
     */
    @Override
    public RefreshState getState() {
        return mState;
    }

    /**
     * 获取实体布局视图
     *
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout getLayout() {
        return this;
    }

    /**
     * 单独设置刷新监听器
     *
     * @param listener 刷新监听器
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
        return this;
    }

    /**
     * 同时设置刷新和加载监听器
     *
     * @param listener 加载监听器
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
        this.isEnableLoadMore = isEnableLoadMore || (!isManualLoadMore && listener != null);
        return this;
    }

    /**
     * 单独设置加载监听器
     *
     * @param listener 刷新加载监听器
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        this.mRefreshListener = listener;
        this.mLoadMoreListener = listener;
        this.isEnableLoadMore = isEnableLoadMore || (!isManualLoadMore && listener != null);
        return this;
    }

    /**
     * 设置多功能监听器
     *
     * @param listener 建议使用 {@link SimpleMultiPurposeListener}
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setOnMultiPurposeListener(OnMultiPurposeListener listener) {
        this.mOnMultiPurposeListener = listener;
        return this;
    }

    /**
     * 设置主题颜色
     *
     * @param primaryColors 主题颜色
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setPrimaryColors(@ColorInt int... primaryColors) {
        if (mRefreshHeader != null) {
            mRefreshHeader.setPrimaryColors(primaryColors);
        }
        if (mRefreshFooter != null) {
            mRefreshFooter.setPrimaryColors(primaryColors);
        }
        mPrimaryColors = primaryColors;
        return this;
    }

    /**
     * 设置主题颜色
     *
     * @param primaryColorId 主题颜色ID
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setPrimaryColorsId(@ColorRes int... primaryColorId) {
        final View thisView = this;
        final int[] colors = new int[primaryColorId.length];
        for (int i = 0; i < primaryColorId.length; i++) {
            colors[i] = getColor(thisView.getContext(), primaryColorId[i]);
        }
        setPrimaryColors(colors);
        return this;
    }

    /**
     * 设置滚动边界
     *
     * @param boundary 建议使用 {@link ScrollBoundaryDeciderAdapter}
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setScrollBoundaryDecider(ScrollBoundaryDecider boundary) {
        mScrollBoundaryDecider = boundary;
        if (mRefreshContent != null) {
            mRefreshContent.setScrollBoundaryDecider(boundary);
        }
        return this;
    }

    /**
     * 恢复没有更多数据的原始状态
     *
     * @param noMoreData 是否有更多数据
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout setNoMoreData(boolean noMoreData) {
        isFooterNoMoreData = noMoreData;
        if (mRefreshFooter instanceof RefreshFooter && !((RefreshFooter) mRefreshFooter).setNoMoreData(noMoreData)) {
            System.out.println("Footer:" + mRefreshFooter + " Prompt completion is not supported.(不支持提示完成)");
        }
        return this;
    }

    /**
     * 完成刷新
     *
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishRefresh() {
        long passTime = System.currentTimeMillis() - mLastOpenTime;
        return finishRefresh(Math.max(0, 300 - (int) passTime));//保证刷新动画有300毫秒的时间
    }

    /**
     * 完成加载
     *
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishLoadMore() {
        long passTime = System.currentTimeMillis() - mLastOpenTime;
        return finishLoadMore(Math.max(0, 300 - (int) passTime));//保证加载动画有300毫秒的时间
    }

    /**
     * 完成刷新
     *
     * @param delayed 开始延时
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishRefresh(int delayed) {
        return finishRefresh(delayed, true);
    }

    /**
     * 完成刷新
     *
     * @param success 数据是否成功刷新 （会影响到上次更新时间的改变）
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishRefresh(boolean success) {
        long passTime = System.currentTimeMillis() - mLastOpenTime;
        return finishRefresh(success ? Math.max(0, 300 - (int) passTime) : 0, success);//保证加载动画有300毫秒的时间
    }

    /**
     * 完成刷新
     *
     * @param delayed 开始延时
     * @param success 数据是否成功刷新 （会影响到上次更新时间的改变）
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishRefresh(int delayed, final boolean success) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mState == RefreshState.Refreshing && mRefreshHeader != null && mRefreshContent != null) {
                    notifyStateChanged(RefreshState.RefreshFinish);
                    int startDelay = mRefreshHeader.onFinish(ZRefreshLayout.this, success);
                    if (mOnMultiPurposeListener != null && mRefreshHeader instanceof RefreshHeader) {
                        mOnMultiPurposeListener.onHeaderFinish((RefreshHeader) mRefreshHeader, success);
                    }
                    if (startDelay < Integer.MAX_VALUE) {
                        if (isBeingDragged || mNestedInProgress) {
                            if (isBeingDragged) {
                                mTouchY = mLastTouchY;
                                mTouchSpinner = 0;
                                isBeingDragged = false;
                            }
                            long time = System.currentTimeMillis();
                            ZRefreshLayout.super.dispatchTouchEvent(obtain(time, time, MotionEvent.ACTION_DOWN, mLastTouchX, mLastTouchY + mSpinner - mTouchSlop * 2, 0));
                            ZRefreshLayout.super.dispatchTouchEvent(obtain(time, time, MotionEvent.ACTION_MOVE, mLastTouchX, mLastTouchY + mSpinner, 0));
                            if (mNestedInProgress) {
                                mTotalUnconsumed = 0;
                            }
                        }
                        if (mSpinner > 0) {
                            ValueAnimator.AnimatorUpdateListener updateListener = null;
                            ValueAnimator valueAnimator = animSpinner(0, startDelay, mReboundInterpolator, mReboundDuration);
                            if (isEnableScrollContentWhenRefreshed) {
                                updateListener = mRefreshContent.scrollContentWhenFinished(mSpinner);
                            }
                            if (valueAnimator != null && updateListener != null) {
                                valueAnimator.addUpdateListener(updateListener);
                            }
                        } else if (mSpinner < 0) {
                            animSpinner(0, startDelay, mReboundInterpolator, mReboundDuration);
                        } else {
                            mKernel.moveSpinner(0, false);
                            resetStatus();
                        }
                    }
                }
            }
        }, delayed <= 0 ? 1 : delayed);
        return this;
    }

    /**
     * 完成加载
     *
     * @param delayed 开始延时
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishLoadMore(int delayed) {
        return finishLoadMore(delayed, true, false);
    }

    /**
     * 完成加载
     *
     * @param success 数据是否成功
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishLoadMore(boolean success) {
        long passTime = System.currentTimeMillis() - mLastOpenTime;
        return finishLoadMore(success ? Math.max(0, 300 - (int) passTime) : 0, success, false);
    }

    /**
     * 完成加载
     *
     * @param delayed    开始延时
     * @param success    数据是否成功
     * @param noMoreData 是否有更多数据
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishLoadMore(int delayed, final boolean success, final boolean noMoreData) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mState == RefreshState.Loading && mRefreshFooter != null && mRefreshContent != null) {
                    notifyStateChanged(RefreshState.LoadFinish);
                    final int startDelay = mRefreshFooter.onFinish(ZRefreshLayout.this, success);
                    if (mOnMultiPurposeListener != null && mRefreshFooter instanceof RefreshFooter) {
                        mOnMultiPurposeListener.onFooterFinish((RefreshFooter) mRefreshFooter, success);
                    }
                    if (startDelay < Integer.MAX_VALUE) {
                        //计算布局将要移动的偏移量
                        final boolean needHoldFooter = noMoreData && isEnableFooterFollowWhenLoadFinished && mSpinner < 0 && mRefreshContent.isCanLoadMore();
                        final int offset = mSpinner - (needHoldFooter ? Math.max(mSpinner, -mFooterHeight) : 0);
                        //如果正在拖动的话，偏移初始点击事件
                        if (isBeingDragged || mNestedInProgress) {
                            if (isBeingDragged) {
                                mTouchY = mLastTouchY;
                                isBeingDragged = false;
                                mTouchSpinner = mSpinner - offset;
                            }
                            final long time = System.currentTimeMillis();
                            ZRefreshLayout.super.dispatchTouchEvent(obtain(time, time, MotionEvent.ACTION_DOWN, mLastTouchX, mLastTouchY + offset + mTouchSlop * 2, 0));
                            ZRefreshLayout.super.dispatchTouchEvent(obtain(time, time, MotionEvent.ACTION_MOVE, mLastTouchX, mLastTouchY + offset, 0));
                            if (mNestedInProgress) {
                                mTotalUnconsumed = 0;
                            }
//                        } else if (mTotalUnconsumed != 0) {
//                            mDragDirection = 'h';
//                            mTotalUnconsumed = 0;
//                            final long time = System.currentTimeMillis();
//                            ZRefreshLayout.super.dispatchTouchEvent(obtain(time, time, MotionEvent.ACTION_CANCEL, mLastTouchX, mTouchY + offset, 0));
                        }
                        //准备：偏移并结束状态
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ValueAnimator.AnimatorUpdateListener updateListener = null;
                                if (isEnableScrollContentWhenLoaded && offset < 0) {
                                    updateListener = mRefreshContent.scrollContentWhenFinished(mSpinner);
                                }
                                if (updateListener != null) {
                                    updateListener.onAnimationUpdate(ValueAnimator.ofInt(0, 0));
                                }
                                ValueAnimator animator = null;
                                AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                        super.onAnimationEnd(animation);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        isFooterLocked = false;
                                        if (noMoreData) {
                                            setNoMoreData(true);
                                        }
                                        if (mState == RefreshState.LoadFinish) {
                                            notifyStateChanged(RefreshState.None);
                                        }
                                    }
                                };
                                if (mSpinner > 0) {
                                    animator = mKernel.animSpinner(0);
                                } else if (updateListener != null || mSpinner == 0) {
                                    if (reboundAnimator != null) {
                                        reboundAnimator.cancel();
                                        reboundAnimator = null;
                                    }
                                    mKernel.moveSpinner(0, false);
                                    resetStatus();
                                } else {
                                    if (noMoreData && isEnableFooterFollowWhenLoadFinished) {
                                        if (mSpinner >= -mFooterHeight) {
                                            notifyStateChanged(RefreshState.None);
                                        } else {
                                            animator = mKernel.animSpinner(-mFooterHeight);
                                        }
                                    } else {
                                        animator = mKernel.animSpinner(0);
                                    }
                                }
                                if (animator != null) {
                                    animator.addListener(listenerAdapter);
                                } else {
                                    listenerAdapter.onAnimationEnd(null);
                                }
                            }
                        }, mSpinner < 0 ? startDelay : 0);
                    }
                } else {
                    if (noMoreData) {
                        setNoMoreData(true);
                    }
                }
            }
        }, delayed <= 0 ? 1 : delayed);
        return this;
    }

    /**
     * 完成加载并标记没有更多数据
     *
     * @return ZRefreshLayout
     */
    @Override
    public ZRefreshLayout finishLoadMoreWithNoMoreData() {
        long passTime = System.currentTimeMillis() - mLastOpenTime;
        return finishLoadMore(Math.max(0, 300 - (int) passTime), true, true);
    }

    /**
     * 自动刷新
     *
     * @return 是否成功
     */
    @Override
    public boolean autoRefresh() {
        return autoRefresh(mHandler == null ? 400 : 0, mReboundDuration, 1f * ((mHeaderMaxDragRate / 2 + 0.5f) * mHeaderHeight) / (mHeaderHeight == 0 ? 1 : mHeaderHeight));
    }


    /**
     * 自动刷新
     *
     * @param delayed  开始延时
     * @param duration 拖拽动画持续时间
     * @param dragRate 拉拽的高度比率（要求 ≥ 1 ）
     * @return 是否成功
     */
    @Override
    public boolean autoRefresh(int delayed, final int duration, final float dragRate) {
        if (mState == RefreshState.None && isEnableRefresh()) {
            if (reboundAnimator != null) {
                reboundAnimator.cancel();
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    reboundAnimator = ValueAnimator.ofInt(mSpinner, (int) (mHeaderHeight * dragRate));
                    reboundAnimator.setDuration(duration);
                    reboundAnimator.setInterpolator(new DecelerateInterpolator());
                    reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mKernel.moveSpinner((int) animation.getAnimatedValue(), true);
                        }
                    });
                    reboundAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            final View thisView = ZRefreshLayout.this;
                            mLastTouchX = thisView.getMeasuredWidth() / 2;
                            mKernel.setState(RefreshState.PullDownToRefresh);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            reboundAnimator = null;
                            if (mState != RefreshState.ReleaseToRefresh) {
                                mKernel.setState(RefreshState.ReleaseToRefresh);
                            }
                            overSpinner();
                        }
                    });
                    reboundAnimator.start();
                }
            };
            if (delayed > 0) {
                reboundAnimator = new ValueAnimator();
                postDelayed(runnable, delayed);
            } else {
                runnable.run();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 自动加载
     *
     * @return 是否成功
     */
    @Override
    public boolean autoLoadMore() {
        return autoLoadMore(0, mReboundDuration, 1f * (mFooterHeight * (mFooterMaxDragRate / 2 + 0.5f)) / (mFooterHeight == 0 ? 1 : mFooterHeight));
    }


    /**
     * 自动加载
     *
     * @param delayed  开始延时
     * @param duration 拖拽动画持续时间
     * @param dragRate 拉拽的高度比率（要求 ≥ 1 ）
     * @return 是否成功
     */
    @Override
    public boolean autoLoadMore(int delayed, final int duration, final float dragRate) {
        if (mState == RefreshState.None && (isEnableLoadMore() && !isFooterNoMoreData)) {
            if (reboundAnimator != null) {
                reboundAnimator.cancel();
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    reboundAnimator = ValueAnimator.ofInt(mSpinner, -(int) (mFooterHeight * dragRate));
                    reboundAnimator.setDuration(duration);
                    reboundAnimator.setInterpolator(new DecelerateInterpolator());
                    reboundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mKernel.moveSpinner((int) animation.getAnimatedValue(), true);
                        }
                    });
                    reboundAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            final View thisView = ZRefreshLayout.this;
                            mLastTouchX = thisView.getMeasuredWidth() / 2;
                            mKernel.setState(RefreshState.PullUpToLoad);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            reboundAnimator = null;
                            if (mState != RefreshState.ReleaseToLoad) {
                                mKernel.setState(RefreshState.ReleaseToLoad);
                            }
                            if (isEnableAutoLoadMore) {
                                isEnableAutoLoadMore = false;
                                overSpinner();
                                isEnableAutoLoadMore = true;
                            } else {
                                overSpinner();
                            }
                        }
                    });
                    reboundAnimator.start();
                }
            };
            if (delayed > 0) {
                reboundAnimator = new ValueAnimator();
                postDelayed(runnable, delayed);
            } else {
                runnable.run();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnableRefresh() {
        return isEnableRefresh && !isEnablePureScrollMode;
    }

    @Override
    public boolean isEnableLoadMore() {
        return isEnableLoadMore && !isEnablePureScrollMode;
    }

    /**
     * 设置默认 Header 构建器
     *
     * @param creator Header构建器
     */
    public static void setDefaultRefreshHeaderCreator(@NonNull DefaultRefreshHeaderCreator creator) {
        sHeaderCreator = creator;
    }

    /**
     * 设置默认 Footer 构建器
     *
     * @param creator Footer构建器
     */
    public static void setDefaultRefreshFooterCreator(@NonNull DefaultRefreshFooterCreator creator) {
        sFooterCreator = creator;
        isManualFooterCreator = true;
    }


    //</editor-fold>

    //<editor-fold desc="内存泄漏 postDelayed优化">

    @Override
    public boolean post(@NonNull Runnable action) {
        if (mHandler == null) {
            mListDelayedRunnable = mListDelayedRunnable == null ? new ArrayList<DelayedRunnable>() : mListDelayedRunnable;
            mListDelayedRunnable.add(new DelayedRunnable(action, 0));
            return false;
        }
        return mHandler.post(new DelayedRunnable(action, 0));
    }

    @Override
    public boolean postDelayed(@NonNull Runnable action, long delayMillis) {
        if (delayMillis == 0) {
            new DelayedRunnable(action, 0).run();
            return true;
        }
        if (mHandler == null) {
            mListDelayedRunnable = mListDelayedRunnable == null ? new ArrayList<DelayedRunnable>() : mListDelayedRunnable;
            mListDelayedRunnable.add(new DelayedRunnable(action, delayMillis));
            return false;
        }
        return mHandler.postDelayed(new DelayedRunnable(action, 0), delayMillis);
    }

    //</editor-fold dec="内存泄漏 postDelayed优化 --- end ---">

    //<editor-fold desc="布局参数 LayoutParams">
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        final View thisView = this;
        return new LayoutParams(thisView.getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ZRefreshLayout_Layout);
            backgroundColor = ta.getColor(R.styleable.ZRefreshLayout_Layout_layout_zrlBackgroundColor, backgroundColor);
            if (ta.hasValue(R.styleable.ZRefreshLayout_Layout_layout_zrlSpinnerStyle)) {
                spinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.ZRefreshLayout_Layout_layout_zrlSpinnerStyle, SpinnerStyle.Translate.ordinal())];
            }
            ta.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public int backgroundColor = 0;
        public SpinnerStyle spinnerStyle = null;
    }
    //</editor-fold dec= "布局参数 LayoutParams --- end ---">

    //<editor-fold desc="核心接口 RefreshKernel">
    public class RefreshKernelImpl implements RefreshKernel {

        @NonNull
        @Override
        public RefreshLayout getRefreshLayout() {
            return ZRefreshLayout.this;
        }

        @NonNull
        @Override
        public RefreshContent getRefreshContent() {
            return mRefreshContent;
        }

        @Override
        public RefreshKernel setState(@NonNull RefreshState state) {
            switch (state) {
                case None:
                    resetStatus();
                    break;
                case PullDownToRefresh:
                    if (!mState.isOpening && isEnableRefresh()) {
                        notifyStateChanged(RefreshState.PullDownToRefresh);
                    } else {
                        setViceState(RefreshState.PullDownToRefresh);
                    }
                    break;
                case PullUpToLoad:
                    if (isEnableLoadMore() && !mState.isOpening && !mState.isFinishing && !(isFooterNoMoreData && isEnableFooterFollowWhenLoadFinished)) {
                        notifyStateChanged(RefreshState.PullUpToLoad);
                    } else {
                        setViceState(RefreshState.PullUpToLoad);
                    }
                    break;
                case PullDownCanceled:
                    if (!mState.isOpening && isEnableRefresh()) {
                        notifyStateChanged(RefreshState.PullDownCanceled);
                        resetStatus();
                    } else {
                        setViceState(RefreshState.PullDownCanceled);
                    }
                    break;
                case PullUpCanceled:
                    if (isEnableLoadMore() && !mState.isOpening && !(isFooterNoMoreData && isEnableFooterFollowWhenLoadFinished)) {
                        notifyStateChanged(RefreshState.PullUpCanceled);
                        resetStatus();
                    } else {
                        setViceState(RefreshState.PullUpCanceled);
                    }
                    break;
                case ReleaseToRefresh:
                    if (!mState.isOpening && isEnableRefresh()) {
                        notifyStateChanged(RefreshState.ReleaseToRefresh);
                    } else {
                        setViceState(RefreshState.ReleaseToRefresh);
                    }
                    break;
                case ReleaseToLoad:
                    if (isEnableLoadMore() && !mState.isOpening && !mState.isFinishing && !(isFooterNoMoreData && isEnableFooterFollowWhenLoadFinished)) {
                        notifyStateChanged(RefreshState.ReleaseToLoad);
                    } else {
                        setViceState(RefreshState.ReleaseToLoad);
                    }
                    break;
                case ReleaseToTwoLevel: {
                    if (!mState.isOpening && isEnableRefresh()) {
                        notifyStateChanged(RefreshState.ReleaseToTwoLevel);
                    } else {
                        setViceState(RefreshState.ReleaseToTwoLevel);
                    }
                    break;
                }
                case RefreshReleased: {
                    if (!mState.isOpening && isEnableRefresh()) {
                        notifyStateChanged(RefreshState.RefreshReleased);
                    } else {
                        setViceState(RefreshState.RefreshReleased);
                    }
                    break;
                }
                case LoadReleased: {
                    if (!mState.isOpening && isEnableLoadMore()) {
                        notifyStateChanged(RefreshState.LoadReleased);
                    } else {
                        setViceState(RefreshState.LoadReleased);
                    }
                    break;
                }
                case Refreshing:
                    setStateRefreshing();
                    break;
                case Loading:
                    setStateLoading();
                    break;
                case RefreshFinish: {
                    if (mState == RefreshState.Refreshing) {
                        notifyStateChanged(RefreshState.RefreshFinish);
                    }
                    break;
                }
                case LoadFinish: {
                    if (mState == RefreshState.Loading) {
                        notifyStateChanged(RefreshState.LoadFinish);
                    }
                    break;
                }
                case TwoLevelReleased:
                    notifyStateChanged(RefreshState.TwoLevelReleased);
                    break;
                case TwoLevelFinish:
                    notifyStateChanged(RefreshState.TwoLevelFinish);
                    break;
                case TwoLevel:
                    notifyStateChanged(RefreshState.TwoLevel);
                    break;
            }
            return null;
        }

        @Override
        public RefreshKernel startTwoLevel(boolean open) {
            if (open) {
                AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mKernel.setState(RefreshState.TwoLevel);
                    }
                };
                final View thisView = ZRefreshLayout.this;
                ValueAnimator animator = animSpinner(thisView.getMeasuredHeight());
                if (animator != null && animator == reboundAnimator) {
                    animator.setDuration(mFloorDuration);
                    animator.addListener(listener);
                } else {
                    listener.onAnimationEnd(null);
                }
            } else {
                if (animSpinner(0) == null) {
                    notifyStateChanged(RefreshState.None);
                }
            }
            return this;
        }

        @Override
        public RefreshKernel finishTwoLevel() {
            if (mState == RefreshState.TwoLevel) {
                mKernel.setState(RefreshState.TwoLevelFinish);
                if (mSpinner == 0) {
                    moveSpinner(0, false);
                    notifyStateChanged(RefreshState.None);
                } else {
                    animSpinner(0).setDuration(mFloorDuration);
                }
            }
            return this;
        }


        //<editor-fold desc="视图位移 Spinner">

        /*
         * 移动滚动 Scroll
         * moveSpinner 的取名来自 谷歌官方的 {@link android.support.v4.widget.SwipeRefreshLayout#moveSpinner(float)}
         */
        public RefreshKernel moveSpinner(int spinner, boolean isDragging) {
            if (mSpinner == spinner
                    && (mRefreshHeader == null || !mRefreshHeader.isSupportHorizontalDrag())
                    && (mRefreshFooter == null || !mRefreshFooter.isSupportHorizontalDrag())) {
                return this;
            }
            final View thisView = ZRefreshLayout.this;
            final int oldSpinner = mSpinner;
            mSpinner = spinner;
            if (isDragging && mViceState.isDragging) {
                if (mSpinner > mHeaderHeight * mHeaderTriggerRate) {
                    if (mState != RefreshState.ReleaseToTwoLevel) {
                        mKernel.setState(RefreshState.ReleaseToRefresh);
                    }
                } else if (-mSpinner > mFooterHeight * mFooterTriggerRate && !isFooterNoMoreData) {
                    mKernel.setState(RefreshState.ReleaseToLoad);
                } else if (mSpinner < 0 && !isFooterNoMoreData) {
                    mKernel.setState(RefreshState.PullUpToLoad);
                } else if (mSpinner > 0) {
                    mKernel.setState(RefreshState.PullDownToRefresh);
                }
            }
            if (mRefreshContent != null) {
                Integer tSpinner = null;
                if (spinner >= 0 && mRefreshHeader != null) {
                    if (isEnableHeaderTranslationContent || mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                        tSpinner = spinner;
                    } else if (oldSpinner < 0) {
                        tSpinner = 0;
                    }
                }
                if (spinner <= 0 && mRefreshFooter != null) {
                    if (isEnableFooterTranslationContent || mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind) {
                        tSpinner = spinner;
                    } else if (oldSpinner > 0) {
                        tSpinner = 0;
                    }
                }
                if (tSpinner != null) {
                    mRefreshContent.moveSpinner(tSpinner, mHeaderTranslationViewId, mFooterTranslationViewId);
                    boolean header = isEnableClipHeaderWhenFixedBehind && mRefreshHeader.getSpinnerStyle() == SpinnerStyle.FixedBehind;
                    header = header || mHeaderBackgroundColor != 0;
                    boolean footer = isEnableClipFooterWhenFixedBehind && mRefreshFooter.getSpinnerStyle() == SpinnerStyle.FixedBehind;
                    footer = footer || mFooterBackgroundColor != 0;
                    if ((header && (tSpinner >= 0 || oldSpinner > 0)) || (footer && (tSpinner <= 0 || oldSpinner < 0))) {
                        thisView.invalidate();
                    }
                }
            }
            if ((spinner >= 0 || oldSpinner > 0) && mRefreshHeader != null) {

                final int offset = Math.max(spinner, 0);
                final int headerHeight = mHeaderHeight;
                final int maxDragHeight = (int) (mHeaderHeight * mHeaderMaxDragRate);
                final float percent = 1f * offset / (mHeaderHeight == 0 ? 1 : mHeaderHeight);

                if (isEnableRefresh() || (mState == RefreshState.RefreshFinish && !isDragging)) {
                    if (oldSpinner != mSpinner) {
                        if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Translate) {
                            mRefreshHeader.getView().setTranslationY(mSpinner);
                            if (mHeaderBackgroundColor != 0 && mPaint != null && !isEnableHeaderTranslationContent) {
                                thisView.invalidate();
                            }
                        } else if (mRefreshHeader.getSpinnerStyle() == SpinnerStyle.Scale) {
                            mRefreshHeader.getView().requestLayout();
                        }
                        if (!isDragging) {
                            mRefreshHeader.onMoving(false, percent, offset, headerHeight, maxDragHeight);
                        }
                    }
                    if (isDragging) {
                        if (mRefreshHeader.isSupportHorizontalDrag()) {
                            final int offsetX = (int) mLastTouchX;
                            final int offsetMax = thisView.getWidth();
                            final float percentX = mLastTouchX / (offsetMax == 0 ? 1 : offsetMax);
                            mRefreshHeader.onHorizontalDrag(percentX, offsetX, offsetMax);
                            mRefreshHeader.onMoving(true, percent, offset, headerHeight, maxDragHeight);
                        } else if (oldSpinner != mSpinner) {
                            mRefreshHeader.onMoving(true, percent, offset, headerHeight, maxDragHeight);
                        }
                    }
                }

                if (oldSpinner != mSpinner && mOnMultiPurposeListener != null && mRefreshHeader instanceof RefreshHeader) {
                    mOnMultiPurposeListener.onHeaderMoving((RefreshHeader) mRefreshHeader, isDragging, percent, offset, headerHeight, maxDragHeight);
                }

            }
            if ((spinner <= 0 || oldSpinner < 0) && mRefreshFooter != null) {

                final int offset = -Math.min(spinner, 0);
                final int footerHeight = mFooterHeight;
                final int maxDragHeight = (int) (mFooterHeight * mFooterMaxDragRate);
                final float percent = offset * 1f / (mFooterHeight == 0 ? 1 : mFooterHeight);

                if (isEnableLoadMore() || (mState == RefreshState.LoadFinish && !isDragging)) {
                    if (oldSpinner != mSpinner) {
                        if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Translate) {
                            mRefreshFooter.getView().setTranslationY(mSpinner);
                            if (mFooterBackgroundColor != 0 && mPaint != null && !isEnableFooterTranslationContent) {
                                thisView.invalidate();
                            }
                        } else if (mRefreshFooter.getSpinnerStyle() == SpinnerStyle.Scale) {
                            mRefreshFooter.getView().requestLayout();
                        }
                        if (!isDragging) {
                            mRefreshFooter.onMoving(false, percent, offset, footerHeight, maxDragHeight);
                        }
                    }

                    if (isDragging) {
                        if (mRefreshFooter.isSupportHorizontalDrag()) {
                            final int offsetX = (int) mLastTouchX;
                            final int offsetMax = thisView.getWidth();
                            final float percentX = mLastTouchX / (offsetMax == 0 ? 1 : offsetMax);
                            mRefreshFooter.onHorizontalDrag(percentX, offsetX, offsetMax);
                            mRefreshFooter.onMoving(true, percent, offset, footerHeight, maxDragHeight);
                        } else if (oldSpinner != mSpinner) {
                            mRefreshFooter.onMoving(true, percent, offset, footerHeight, maxDragHeight);
                        }
                    }
                }

                if (oldSpinner != mSpinner && mOnMultiPurposeListener != null && mRefreshFooter instanceof RefreshFooter) {
                    mOnMultiPurposeListener.onFooterMoving((RefreshFooter) mRefreshFooter, isDragging, percent, offset, footerHeight, maxDragHeight);
                }
            }
            return this;
        }

        public ValueAnimator animSpinner(int endSpinner) {
            return ZRefreshLayout.this.animSpinner(endSpinner, 0, mReboundInterpolator, mReboundDuration);
        }

        //</editor-fold dec = "视图位移 Spinner --- end ---" >

        //<editor-fold desc="请求事件">

        @Override
        public RefreshKernel requestDrawBackgroundFor(RefreshInternal internal, int backgroundColor) {
            if (mPaint == null && backgroundColor != 0) {
                mPaint = new Paint();
            }
            if (mRefreshHeader != null && mRefreshHeader.getView() == internal.getView()) {
                mHeaderBackgroundColor = backgroundColor;
            } else if (mRefreshFooter != null && mRefreshFooter.getView() == internal.getView()) {
                mFooterBackgroundColor = backgroundColor;
            }
            return this;
        }


        @Override
        public RefreshKernel requestNeedTouchEventFor(@NonNull RefreshInternal internal, boolean request) {
            if (mRefreshHeader != null && mRefreshHeader.getView() == internal.getView()) {
                isHeaderNeedTouchEventWhenRefreshing = request;
            } else if (mRefreshFooter != null && mRefreshFooter.getView() == internal.getView()) {
                isFooterNeedTouchEventWhenLoading = request;
            }
            return this;
        }


        @Override
        public RefreshKernel requestDefaultTranslationContentFor(@NonNull RefreshInternal internal, boolean translation) {
            if (mRefreshHeader != null && mRefreshHeader.getView() == internal.getView()) {
                if (!isManualHeaderTranslationContent) {
                    isManualHeaderTranslationContent = true;
                    isEnableHeaderTranslationContent = translation;
                }
            } else if (mRefreshFooter != null && mRefreshFooter.getView() == internal.getView()) {
                if (!isManualFooterTranslationContent) {
                    isManualFooterTranslationContent = true;
                    isEnableFooterTranslationContent = translation;
                }
            }
            return this;
        }

        @Override
        public RefreshKernel requestRemeasureHeightFor(@NonNull RefreshInternal internal) {
            if (mRefreshHeader != null && mRefreshHeader.getView() == internal.getView()) {
                if (mHeaderHeightStatus.notified) {
                    mHeaderHeightStatus = mHeaderHeightStatus.unNotify();
                }
            } else if (mRefreshFooter != null && mRefreshFooter.getView() == internal.getView()) {
                if (mFooterHeightStatus.notified) {
                    mFooterHeightStatus = mFooterHeightStatus.unNotify();
                }
            }
            return this;
        }


        @Override
        public RefreshKernel requestFloorDuration(int duration) {
            mFloorDuration = duration;
            return this;
        }
        //</editor-fold dec ="请求事件 --- end ---">
    }

    //</editor-fold desc="核心接口 RefreshKernel  --- end ---">

    //<editor-fold desc="动画监听 Animator Listener">

    protected class FlingRunnable implements Runnable {
        int mOffset;
        int mFrame = 0;
        int mFrameDelay = 10;
        float mVelocity;
        float mDamping = 0.98f;//每帧速度衰减值
        long mStartTime = 0;
        long mLastTime = AnimationUtils.currentAnimationTimeMillis();

        FlingRunnable(float velocity) {
            mVelocity = velocity;
            mOffset = mSpinner;
        }

        public Runnable start() {
            if (mState.isFinishing) {
                return null;
            }
            if (mSpinner != 0 && (!(mState.isOpening || (isFooterNoMoreData && isEnableFooterFollowWhenLoadFinished && isEnableLoadMore()))
                    || ((mState == RefreshState.Loading || (isFooterNoMoreData && isEnableFooterFollowWhenLoadFinished && isEnableLoadMore())) && mSpinner < -mFooterHeight)
                    || (mState == RefreshState.Refreshing && mSpinner > mHeaderHeight))) {
                int frame = 0;
                int offset = mSpinner;
                int spinner = mSpinner;
                float velocity = mVelocity;
                while (spinner * offset > 0) {
                    velocity *= Math.pow(mDamping, (++frame) * mFrameDelay / 10);
                    float velocityFrame = (velocity * (1f * mFrameDelay / 1000));
                    if (Math.abs(velocityFrame) < 1) {
                        if (!mState.isOpening
                                || (mState == RefreshState.Refreshing && offset > mHeaderHeight)
                                || (mState != RefreshState.Refreshing && offset < -mFooterHeight)) {
                            return null;
                        }
                        break;
                    }
                    offset += velocityFrame;
                }
            }
            mStartTime = AnimationUtils.currentAnimationTimeMillis();
            postDelayed(this, mFrameDelay);
            return this;
        }

        @Override
        public void run() {
            if (animationRunnable == this && !mState.isFinishing) {
                long now = AnimationUtils.currentAnimationTimeMillis();
                long span = now - mLastTime;
                mVelocity *= Math.pow(mDamping, (now - mStartTime) / (1000 / mFrameDelay));
                float velocity = (mVelocity * (1f * span / 1000));
                if (Math.abs(velocity) > 1) {
                    mLastTime = now;
                    mOffset += velocity;
                    if (mSpinner * mOffset > 0) {
                        mKernel.moveSpinner(mOffset, true);
                        postDelayed(this, mFrameDelay);
                    } else {
                        animationRunnable = null;
                        mKernel.moveSpinner(0, true);
                        fling(mRefreshContent.getScrollableView(), (int) -mVelocity);
                        if (isFooterLocked && velocity > 0) {
                            isFooterLocked = false;
                        }
                    }
                } else {
                    animationRunnable = null;
                }
            }
        }
    }

    protected class BounceRunnable implements Runnable {
        int mFrame = 0;
        int mFrameDelay = 10;
        int mSmoothDistance;
        long mLastTime;
        float mOffset = 0;
        float mVelocity;

        BounceRunnable(float velocity, int smoothDistance) {
            mVelocity = velocity;
            mSmoothDistance = smoothDistance;
            mLastTime = AnimationUtils.currentAnimationTimeMillis();
            postDelayed(this, mFrameDelay);
        }

        @Override
        public void run() {
            if (animationRunnable == this && !mState.isFinishing) {
                if (Math.abs(mSpinner) >= Math.abs(mSmoothDistance)) {
                    if (mSmoothDistance != 0) {
                        mVelocity *= Math.pow(0.45f, ++mFrame);//刷新、加载时回弹滚动数度衰减
                    } else {
                        mVelocity *= Math.pow(0.85f, ++mFrame);//回弹滚动数度衰减
                    }
                } else {
                    mVelocity *= Math.pow(0.95f, ++mFrame);//平滑滚动数度衰减
                }
                long now = AnimationUtils.currentAnimationTimeMillis();
                float t = 1f * (now - mLastTime) / 1000;
                float velocity = mVelocity * t;
                if (Math.abs(velocity) >= 1) {
                    mLastTime = now;
                    mOffset += velocity;
                    moveSpinnerInfinitely(mOffset);
                    postDelayed(this, mFrameDelay);
                } else {
                    animationRunnable = null;
                    if (Math.abs(mSpinner) >= Math.abs(mSmoothDistance)) {
                        int duration = 10 * Math.min(Math.max((int) DensityUtil.px2dp(Math.abs(mSpinner - mSmoothDistance)), 30), 100);
                        animSpinner(mSmoothDistance, 0, mReboundInterpolator, duration);
                    }
                }
            }
        }
    }
    //</editor-fold desc="动画监听 Animator Listener --- end ---">
}

