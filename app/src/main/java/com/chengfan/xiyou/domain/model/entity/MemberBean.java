package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-15/15:00
 * @Description:
 */
public class MemberBean {
    /**
     * id : 1
     * age : 18
     * gender : 1
     * nickname : test123
     * userName : 13800139000
     * avatarUrl : Img/touxiang.png
     * genderVideo : null
     * areaCode : 440100
     * areaName : 广东省广州市
     * vip : false
     * totalFans : 1
     * accompanyPlay : {"id":3,"price":10000,"status":0,"title":"游戏陪玩"}
     * memberNews : {"images":"UploadFiles/Member/20190711/Editor/620ca3af-39cf-43ca-8ac6-3903ba62f8f8.png;UploadFiles/Member/20190711/Editor/dbc3dfba-d7bc-4f08-a468-02ee1f4f4cf5.png","createTime":"2019-07-11T22:29:18.51"}
     */

    private int id;
    private int age;
    private int gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private Object genderVideo;
    private int areaCode;
    private String areaName;
    private boolean vip;
    private int totalFans;
    private AccompanyPlayBean accompanyPlay;
    private MemberNewsBean memberNews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public Object getGenderVideo() {
        return genderVideo;
    }

    public void setGenderVideo(Object genderVideo) {
        this.genderVideo = genderVideo;
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

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public AccompanyPlayBean getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(AccompanyPlayBean accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public MemberNewsBean getMemberNews() {
        return memberNews;
    }

    public void setMemberNews(MemberNewsBean memberNews) {
        this.memberNews = memberNews;
    }

    public static class AccompanyPlayBean {
        /**
         * id : 3
         * price : 10000.0
         * status : 0
         * title : 游戏陪玩
         */

        private int id;
        private double price;
        private int status;
        private String title;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class MemberNewsBean {
        /**
         * images : UploadFiles/Member/20190711/Editor/620ca3af-39cf-43ca-8ac6-3903ba62f8f8.png;UploadFiles/Member/20190711/Editor/dbc3dfba-d7bc-4f08-a468-02ee1f4f4cf5.png
         * createTime : 2019-07-11T22:29:18.51
         */

        private String images;
        private String createTime;

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
