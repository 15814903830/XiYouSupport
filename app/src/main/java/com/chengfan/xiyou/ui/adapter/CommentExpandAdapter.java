package com.chengfan.xiyou.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengfan.xiyou.R;
import com.chengfan.xiyou.common.APIContents;
import com.chengfan.xiyou.domain.model.entity.DynamicDetailEntity;
import com.chengfan.xiyou.domain.model.entity.ReplyDetailBean;
import com.chengfan.xiyou.utils.DataFormatUtil;
import com.zero.ci.widget.CircleImageView;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;


/**
 * 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<DynamicDetailEntity.MemberNewsCommentBean> commentBeanList;
    private List<ReplyDetailBean> replyBeanList;
    private Context context;
    private int pageIndex = 1;

    public CommentExpandAdapter(Context context, List<DynamicDetailEntity.MemberNewsCommentBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (commentBeanList.get(i).getCommentMemberNewsComment() == null) {
            return 0;
        } else {
            return commentBeanList.get(i).getCommentMemberNewsComment().size() > 0 ? commentBeanList.get(i).getCommentMemberNewsComment().size() : 0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getCommentMemberNewsComment().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(R.drawable.home_nan)
                // .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(groupHolder.logo);
        ImageLoaderManager.getInstance().showImage(groupHolder.logo, APIContents.HOST + "/" + commentBeanList.get(groupPosition).getAvatarUrl());

        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getNickname());
        groupHolder.tv_time.setText(DataFormatUtil.formatDate(commentBeanList.get(groupPosition).getCreateTime()));
        groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getContent());


        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        String replyUser = commentBeanList.get(groupPosition).getCommentMemberNewsComment().get(childPosition).getNickname();
        if (!TextUtils.isEmpty(replyUser)) {
            childHolder.tv_name.setText(replyUser + "：");
        } else {
            childHolder.tv_name.setText("无名：");
        }

        childHolder.tv_content.setText(commentBeanList.get(groupPosition).getCommentMemberNewsComment().get(childPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;

        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
        }
    }

    private class ChildHolder {
        private TextView tv_name, tv_content;

        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
    }


    /**
     * 评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(DynamicDetailEntity.MemberNewsCommentBean commentDetailBean) {
        if (commentDetailBean != null) {

            commentBeanList.add(commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * 回复成功后插入一条数据
     *
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(DynamicDetailEntity.ReplyDetailBean replyDetailBean, int groupPosition) {
        if (replyDetailBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyDetailBean.toString());
            if (commentBeanList.get(groupPosition).getCommentMemberNewsComment() != null) {
                commentBeanList.get(groupPosition).getCommentMemberNewsComment().add(replyDetailBean);
            } else {
                List<DynamicDetailEntity.ReplyDetailBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setCommentMemberNewsComment(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * 添加和展示所有回复
     *
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    private void addReplyList(List<DynamicDetailEntity.ReplyDetailBean> replyBeanList, int groupPosition) {
        if (commentBeanList.get(groupPosition).getCommentMemberNewsComment() != null) {
            commentBeanList.get(groupPosition).getCommentMemberNewsComment().clear();
            commentBeanList.get(groupPosition).getCommentMemberNewsComment().addAll(replyBeanList);
        } else {

            commentBeanList.get(groupPosition).setCommentMemberNewsComment(replyBeanList);
        }

        notifyDataSetChanged();
    }

}
