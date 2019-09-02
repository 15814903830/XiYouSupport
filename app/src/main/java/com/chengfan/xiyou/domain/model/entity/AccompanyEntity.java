package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/10:08
 * @Description:
 */
public class AccompanyEntity implements Serializable {


    private List<?> news;
    private List<SubjectBean> subject;
    private List<AccompanyPlayBean> accompanyPlay;

    public List<?> getNews() {
        return news;
    }

    public void setNews(List<?> news) {
        this.news = news;
    }

    public List<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectBean> subject) {
        this.subject = subject;
    }

    public List<AccompanyPlayBean> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<AccompanyPlayBean> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public static class SubjectBean {
        /**
         * id : 1
         * images : Img/1.png
         * title : 守望先锋
         * areaTitles : 性别:男,女
         * gradeTitles : 段位:白金,钻石,大师,宗师,璀璨钻石,500强
         * accompanyPlay : []
         */

        private int id;
        private String images;
        private String title;
        private String areaTitles;
        private String gradeTitles;
        private List<?> accompanyPlay;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAreaTitles() {
            return areaTitles;
        }

        public void setAreaTitles(String areaTitles) {
            this.areaTitles = areaTitles;
        }

        public String getGradeTitles() {
            return gradeTitles;
        }

        public void setGradeTitles(String gradeTitles) {
            this.gradeTitles = gradeTitles;
        }

        public List<?> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<?> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }
    }

    public static class AccompanyPlayBean {
        /**
         * id : 1082
         * age : 19
         * gender : 0
         * nickname : 国彬
         * userName : 15625105974
         * avatarUrl : UploadFiles/Member/20190821/Editor/6d87fe79-5bc5-4ce5-9e93-7e039ada2f68.png
         * genderVideo : UploadFiles/Member/20190821/Editor/4e286fdf-da93-408f-9503-7000b4b1e617.mp4
         * areaCode : 440100
         * areaName : 广东省 广州市
         * recommendTime : 2019-08-31T00:00:00
         * vip : true
         * totalFans : 2
         * accompanyPlay : null
         * memberNews : {"id":218,"images":"UploadFiles/AccompanyPlayNews/20190824/0b7ef443-74d0-4fb4-8d14-6daf862aa88e.jpg","content":"76646","createTime":"2019-08-24T18:39:07.62"}
         */

        private int id;
        private int age;
        private int gender;
        private String nickname;
        private String userName;
        private String avatarUrl;
        private String genderVideo;
        private int areaCode;
        private String areaName;
        private String recommendTime;
        private boolean vip;
        private int totalFans;
        private Object accompanyPlay;
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

        public String getGenderVideo() {
            return genderVideo;
        }

        public void setGenderVideo(String genderVideo) {
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

        public String getRecommendTime() {
            return recommendTime;
        }

        public void setRecommendTime(String recommendTime) {
            this.recommendTime = recommendTime;
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

        public Object getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(Object accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }

        public MemberNewsBean getMemberNews() {
            return memberNews;
        }

        public void setMemberNews(MemberNewsBean memberNews) {
            this.memberNews = memberNews;
        }

        public static class MemberNewsBean {
            /**
             * id : 218
             * images : UploadFiles/AccompanyPlayNews/20190824/0b7ef443-74d0-4fb4-8d14-6daf862aa88e.jpg
             * content : 76646
             * createTime : 2019-08-24T18:39:07.62
             */

            private int id;
            private String images;
            private String content;
            private String createTime;

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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
