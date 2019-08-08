package com.chengfan.xiyou.domain.model.entity;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/23:18
 * @Description: 首页广告
 */
public class HomeBannerEntity {

    private List<NewsBean> news;
    private List<MemberBean> member;

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<MemberBean> getMember() {
        return member;
    }

    public void setMember(List<MemberBean> member) {
        this.member = member;
    }

    public static class NewsBean {
        /**
         * id : 3
         * typeCode : 1100
         * title : 首页广告
         * imgUrl : UploadFiles/1499249188416299.jpg
         * imgHref : null
         * pubTime : 2019-05-21T17:41:00
         * enable : true
         */

        private int id;
        private int typeCode;
        private String title;
        private String imgUrl;
        private Object imgHref;
        private String pubTime;
        private boolean enable;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(int typeCode) {
            this.typeCode = typeCode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getImgHref() {
            return imgHref;
        }

        public void setImgHref(Object imgHref) {
            this.imgHref = imgHref;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    @Override
    public String toString() {
        return "HomeBannerEntity{" +
                "news=" + news +
                ", member=" + member +
                '}';
    }
}
