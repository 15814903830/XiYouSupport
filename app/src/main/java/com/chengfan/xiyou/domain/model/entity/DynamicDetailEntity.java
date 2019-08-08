package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-18/00:55
 * @Description:
 */
public class DynamicDetailEntity implements Serializable {


    /**
     * id : 1
     * images : UploadFiles/1499249188416299.jpg|UploadFiles/140000.jpg
     * content : 测试数据会员1
     * memberId : 1
     * createTime : 2019-07-04T19:50:56.09
     * totalHours : -381.93396027777777
     * totalComment : 4
     * totalPraise : 0
     * havePraise : false
     * member : {"gender":1,"nickname":"test123","userName":"13800139000","avatarUrl":"Img/touxiang.png"}
     * accompanyPlay : [{"id":11,"title":"孔","memberId":1013,"images":"Screenshot_2019-06-11-12-17-30.png","price":20,"subjectId":1,"subject":{"title":"游戏陪玩","images":"UploadFiles/1499249188416299.jpg"}},{"id":9,"title":"尴尬","memberId":1013,"images":"UploadFiles/AccompanyPlay/20190715/Editor/455e95aa-2813-4ba1-8756-f44213ab07b5.png","price":20,"subjectId":1,"subject":{"title":"游戏陪玩","images":"UploadFiles/1499249188416299.jpg"}}]
     * memberNewsComment : [{"id":1,"content":"我来评论","memberId":2,"gender":1,"nickname":"","userName":"13800138001","avatarUrl":null},{"id":2,"content":"我再来评论","memberId":2,"gender":1,"nickname":"","userName":"13800138001","avatarUrl":null},{"id":3,"content":"我来评论会员id为2的评论","memberId":1002,"gender":1,"nickname":"","userName":"13800138002","avatarUrl":null},{"id":4,"content":"我会员id为1003也来评论会员id为2的评论","memberId":1003,"gender":1,"nickname":"","userName":"13800138003","avatarUrl":null}]
     */

    private int id;
    private String images;
    private String content;
    private int memberId;
    private String createTime;
    private double totalHours;
    private int totalComment;
    private int totalPraise;
    private boolean havePraise;
    private MemberBean member;
    private List<AccompanyPlayBean> accompanyPlay;
    private List<MemberNewsCommentBean> memberNewsComment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalPraise() {
        return totalPraise;
    }

    public void setTotalPraise(int totalPraise) {
        this.totalPraise = totalPraise;
    }

    public boolean isHavePraise() {
        return havePraise;
    }

    public void setHavePraise(boolean havePraise) {
        this.havePraise = havePraise;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<AccompanyPlayBean> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<AccompanyPlayBean> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public List<MemberNewsCommentBean> getMemberNewsComment() {
        return memberNewsComment;
    }

    public void setMemberNewsComment(List<MemberNewsCommentBean> memberNewsComment) {
        this.memberNewsComment = memberNewsComment;
    }

    public static class MemberBean {
        /**
         * gender : 1
         * nickname : test123
         * userName : 13800139000
         * avatarUrl : Img/touxiang.png
         */

        private int gender;
        private String nickname;
        private String userName;
        private String avatarUrl;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }

    public static class AccompanyPlayBean {
        /**
         * id : 11
         * title : 孔
         * memberId : 1013
         * images : Screenshot_2019-06-11-12-17-30.png
         * price : 20
         * subjectId : 1
         * subject : {"title":"游戏陪玩","images":"UploadFiles/1499249188416299.jpg"}
         */

        private int id;
        private String title;
        private int memberId;
        private String images;
        private int price;
        private int subjectId;
        private SubjectBean subject;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
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
             * images : UploadFiles/1499249188416299.jpg
             */

            private String title;
            private String images;

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
        }
    }

    public static class MemberNewsCommentBean {
        /**
         * id : 1
         * content : 我来评论
         * memberId : 2
         * gender : 1
         * nickname :
         * userName : 13800138001
         * avatarUrl : null
         */

        private int id;
        private String content;
        private int memberId;
        private int gender;
        private String nickname;
        private String userName;
        private Object avatarUrl;
        private List<ReplyDetailBean> commentMemberNewsComment;

        public List<ReplyDetailBean> getCommentMemberNewsComment() {
            return commentMemberNewsComment;
        }

        public void setCommentMemberNewsComment(List<ReplyDetailBean> commentMemberNewsComment) {
            this.commentMemberNewsComment = commentMemberNewsComment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }

    public static class ReplyDetailBean {
        private int id;
        private String content;
        private int memberId;
        private int gender;
        private String nickname;
        private String userName;
        private Object avatarUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
