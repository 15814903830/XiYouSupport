package com.zero.ci.refresh.api;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.zero.ci.refresh.api.constant.SpinnerStyle;
import com.zero.ci.refresh.api.listener.OnStateChangedListener;


/**
 * Author: Zero Yuan
 * Email: zero.yuan.xin@gmail.com
 * Description:内部刷新组件
 * -------------------------------
 */

public interface RefreshInternal extends OnStateChangedListener {
    /**
     * 获取实体视图
     *
     * @return 实体视图
     */
    @NonNull
    View getView();

    /**
     * 获取变换方式 {@link SpinnerStyle} 必须返回 非空
     *
     * @return 变换方式
     */
    @NonNull
    SpinnerStyle getSpinnerStyle();

    /**
     * 设置主题颜色
     *
     * @param colors 对应xml的色值
     */
    void setPrimaryColors(@ColorInt int... colors);

    /**
     * 尺寸定义完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     *
     * @param kernel        RefreshKernel
     * @param height        HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight);

    /**
     * 手指拖动下拉（会连续多次调用，添加isDragging并取代之前的onPulling、onReleasing）
     *
     * @param isDragging    true 手指正在拖动 false 回弹动画
     * @param percent       下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+maxDragHeight) / footerHeight )
     * @param offset        下拉的像素偏移量  0 - offset - (footerHeight+maxDragHeight)
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight);

    /**
     * 释放时刻（调用一次，将会触发加载）
     *
     * @param refreshLayout RefreshLayout
     * @param height        高度 HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight);

    /**
     * 开始动画
     *
     * @param refreshLayout RefreshLayout
     * @param height        HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight);

    /**
     * 动画结束
     *
     * @param refreshLayout RefreshLayout
     * @param isSuccess     数据是否成功刷新或加载
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    int onFinish(@NonNull RefreshLayout refreshLayout, boolean isSuccess);

    /**
     * 水平方向的拖动
     *
     * @param percentX  下拉时，手指水平坐标对屏幕的占比（0 - percentX - 1）
     * @param offsetX   下拉时，手指水平坐标对屏幕的偏移（0 - offsetX - LayoutWidth）
     * @param offsetMax 最大的偏移量
     */
    void onHorizontalDrag(float percentX, int offsetX, int offsetMax);

    /**
     * 是否支持水平方向的拖动（将会影响到onHorizontalDrag的调用）
     *
     * @return 水平拖动需要消耗更多的时间和资源，所以如果不支持请返回false
     */
    boolean isSupportHorizontalDrag();
}
