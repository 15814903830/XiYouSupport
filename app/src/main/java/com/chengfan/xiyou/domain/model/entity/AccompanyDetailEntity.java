package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-17/11:50
 * @Description: 陪玩详情
 */
public class AccompanyDetailEntity implements Serializable {


    /**
     * id : 1
     * subjectId : 1
     * memberId : 1
     * title : 测试陪玩1
     * images : UploadFiles/1499249188416299.jpg
     * weekDay : 0,1,2,3,4,5,6
     * serviceEndTime : 23:59:00
     * serviceStartTime : 00:00:00
     * price : 10000.0
     * remark : 描述备注
     * status : 0
     * createTime : 2019-07-05T16:43:40.833
     * gender : 1
     * userName : 13800139000
     * nickname : 昵称（可选）
     * avatarUrl : Img/touxiang.png
     * subjectTitle : 游戏陪玩
     * subjectImages : UploadFiles/1499249188416299.jpg
     * isFans : false
     * commentTotal : 2
     * order : [{"status":5,"comment":"好好玩","commentTime":"2019-07-21T22:57:52.947","gender":1,"userName":"13800138001","nickname":"","avatarUrl":null},{"status":5,"comment":"好好玩涛涛涛涛","commentTime":"2019-07-21T22:57:52.947","gender":1,"userName":"13800138001","nickname":"","avatarUrl":null}]
     */

    private int id;
    private int subjectId;
    private int memberId;
    private String title;
    private String images;
    private String weekDay;
    private String serviceEndTime;
    private String serviceStartTime;
    private double price;
    private String remark;
    private int status;
    private String createTime;
    private int gender;
    private String userName;
    private String nickname;
    private String avatarUrl;
    private String subjectTitle;
    private String subjectImages;
    private boolean isFans;
    private int commentTotal;
    private List<OrderBean> order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectImages() {
        return subjectImages;
    }

    public void setSubjectImages(String subjectImages) {
        this.subjectImages = subjectImages;
    }

    public boolean isIsFans() {
        return isFans;
    }

    public void setIsFans(boolean isFans) {
        this.isFans = isFans;
    }

    public int getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(int commentTotal) {
        this.commentTotal = commentTotal;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public static class OrderBean implements Serializable {
        /**
         * status : 5
         * comment : 好好玩
         * commentTime : 2019-07-21T22:57:52.947
         * gender : 1
         * userName : 13800138001
         * nickname :
         * avatarUrl : null
         */

        private int status;
        private String comment;
        private String commentTime;
        private int gender;
        private String userName;
        private String nickname;
        private Object avatarUrl;

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

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
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

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
