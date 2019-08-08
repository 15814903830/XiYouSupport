package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-06/17:25
 * @Description: 我的关注
 */
public class MineAttentionEntity implements Serializable {

    /**
     * memberId : 1010
     * friendId : 1039
     * createTime : 2019-07-28T16:39:28.97
     * member : {"userName":"18660181698","nickname":"小鱼钓花猫","avatarUrl":"UploadFiles/Member/20190722/Editor/bf762bdf-1cd8-4d94-898f-55e9dd94a9a6.png","isFans":false,"vip":false}
     */

    private int memberId;
    private int friendId;
    private String createTime;
    private MemberBean member;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public static class MemberBean implements Serializable {
        /**
         * userName : 18660181698
         * nickname : 小鱼钓花猫
         * avatarUrl : UploadFiles/Member/20190722/Editor/bf762bdf-1cd8-4d94-898f-55e9dd94a9a6.png
         * isFans : false
         * vip : false
         */

        private String userName;
        private String nickname;
        private String avatarUrl;
        private boolean isFans;
        private boolean vip;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public boolean isIsFans() {
            return isFans;
        }

        public void setIsFans(boolean isFans) {
            this.isFans = isFans;
        }

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }
    }
}
