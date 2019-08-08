package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/17:30
 * @Description:
 */
public class MineOrderTakingEntity implements Serializable {

    /**
     * hour : 1
     * status : 5
     * comment : 好好玩
     * unitPrice : 100.0
     * payPrice : 100.0
     * commentTime : 2019-07-21T22:57:52.947
     * createTime : 2019-07-21T22:37:52.947
     * memberId : 2
     * uniformOrderId : 950000
     * accompanyPlayId : 1
     * finishTime : 2019-07-21T22:55:52.947
     * statusTag : 已完成
     * member : {"id":2,"gender":1,"userName":"13800138001","nickname":"爱吃蛤蟆肉的天鹅","avatarUrl":"Img/touxiang.png"}
     * accompanyPlay : {"id":1,"price":0,"title":"测试陪玩1","memberId":1,"subject":{"title":"游戏陪玩"}}
     */

    private int hour;
    private int status;
    private String comment;
    private double unitPrice;
    private double payPrice;
    private String commentTime;
    private String createTime;
    private int memberId;
    private int uniformOrderId;
    private int accompanyPlayId;
    private String finishTime;
    private String statusTag;
    private MemberBean member;
    private AccompanyPlayBean accompanyPlay;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getUniformOrderId() {
        return uniformOrderId;
    }

    public void setUniformOrderId(int uniformOrderId) {
        this.uniformOrderId = uniformOrderId;
    }

    public int getAccompanyPlayId() {
        return accompanyPlayId;
    }

    public void setAccompanyPlayId(int accompanyPlayId) {
        this.accompanyPlayId = accompanyPlayId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStatusTag() {
        return statusTag;
    }

    public void setStatusTag(String statusTag) {
        this.statusTag = statusTag;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public AccompanyPlayBean getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(AccompanyPlayBean accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public static class MemberBean {
        /**
         * id : 2
         * gender : 1
         * userName : 13800138001
         * nickname : 爱吃蛤蟆肉的天鹅
         * avatarUrl : Img/touxiang.png
         */

        private int id;
        private int gender;
        private String userName;
        private String nickname;
        private String avatarUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

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

        @Override
        public String toString() {
            return "MemberBean{" +
                    "id=" + id +
                    ", gender=" + gender +
                    ", userName='" + userName + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    '}';
        }
    }

    public static class AccompanyPlayBean {
        /**
         * id : 1
         * price : 0.0
         * title : 测试陪玩1
         * memberId : 1
         * subject : {"title":"游戏陪玩"}
         */

        private int id;
        private double price;
        private String title;
        private int memberId;
        private SubjectBean subject;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public SubjectBean getSubject() {
            return subject;
        }

        public void setSubject(SubjectBean subject) {
            this.subject = subject;
        }

        public static class SubjectBean {
            /**
             * title : 游戏陪玩
             */

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
