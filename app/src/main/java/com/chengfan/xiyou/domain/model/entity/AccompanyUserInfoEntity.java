package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-15/20:40
 * @Description: 个人详情
 */
public class AccompanyUserInfoEntity implements Serializable {


    /**
     * id : 1
     * gender : 1
     * nickname : test123
     * userName : 13800139000
     * avatarUrl : Img/touxiang.png
     * exterior : null
     * areaCode : 440100
     * areaName : 广东省广州市
     * age : 18
     * weiXin : null
     * job : null
     * isFans : false
     * totalFans : 1
     * accompanyPlay : [{"id":3,"images":"UploadFiles/1499249188416299.jpg","title":"123456","price":10000}]
     * memberNews : [{"id":5,"images":null,"content":"2019-07-09测试","createTime":"2019-07-09T14:32:21.82","totalHours":-193.20805833333333,"totalComment":0,"totalPraise":0,"havePraise":false},{"id":1,"images":"UploadFiles/1499249188416299.jpg|UploadFiles/140000.jpg","content":"测试数据会员1","createTime":"2019-07-04T19:50:56.09","totalHours":-307.8985388888889,"totalComment":0,"totalPraise":0,"havePraise":false}]
     */

    private int id;
    private int gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private String exterior;
    private int areaCode;
    private String areaName;
    private int age;
    private String weiXin;
    private String job;
    private boolean isFans;
    private int totalFans;
    private List<AccompanyPlayBean> accompanyPlay;
    private List<MemberNewsBean> memberNews;

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

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getWeiXin() {
        return weiXin;
    }

    public void setWeiXin(String weiXin) {
        this.weiXin = weiXin;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isIsFans() {
        return isFans;
    }

    public void setIsFans(boolean isFans) {
        this.isFans = isFans;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public List<AccompanyPlayBean> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<AccompanyPlayBean> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public List<MemberNewsBean> getMemberNews() {
        return memberNews;
    }

    public void setMemberNews(List<MemberNewsBean> memberNews) {
        this.memberNews = memberNews;
    }



    public static class AccompanyPlayBean implements Serializable {

        /**
         * id : 30
         * title : 酒店一日游
         * memberId : 1052
         * images : UploadFiles/AccompanyPlay/20190729/Editor/080bc742-c3e0-437d-8413-4a73761d425e.png
         * price : 2.0
         * subjectId : 1
         * subject : {"title":"守望先锋","images":"Img/1.png"}
         */

        private int id;
        private String title;
        private int memberId;
        private String images;
        private double price;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
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
             * title : 守望先锋
             * images : Img/1.png
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

    public static class MemberNewsBean implements Serializable {
        /**
         * id : 5
         * images : null
         * content : 2019-07-09测试
         * createTime : 2019-07-09T14:32:21.82
         * totalHours : -193.20805833333333
         * totalComment : 0
         * totalPraise : 0
         * havePraise : false
         */

        private int id;
        private Object images;
        private String content;
        private String createTime;
        private double totalHours;
        private int totalComment;
        private int totalPraise;
        private boolean havePraise;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }
}
