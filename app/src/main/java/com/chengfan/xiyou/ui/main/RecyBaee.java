package com.chengfan.xiyou.ui.main;

import java.util.List;

public class RecyBaee {

    private List<?> news;
    private List<SubjectBean> subject;
    private List<?> accompanyPlay;

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

    public List<?> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<?> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public static class SubjectBean {
        /**
         * id : 1
         * images : Img/1.png
         * grayImages : null
         * title : 守望先锋
         * areaTitles : 性别:男,女
         * gradeTitles : 段位:白金,钻石,大师,宗师,璀璨钻石,500强
         * sore : 0
         * pid : 32
         * sort : 99
         * enabled : false
         * accompanyPlay : []
         */

        private int id;
        private String images;
        private Object grayImages;
        private String title;
        private String areaTitles;
        private String gradeTitles;
        private int sore;
        private int pid;
        private int sort;
        private boolean enabled;
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

        public Object getGrayImages() {
            return grayImages;
        }

        public void setGrayImages(Object grayImages) {
            this.grayImages = grayImages;
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

        public int getSore() {
            return sore;
        }

        public void setSore(int sore) {
            this.sore = sore;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<?> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<?> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }
    }
}
