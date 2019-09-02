package com.chengfan.xiyou.ui.accompany;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class MoreBase {

    /**
     * id : 90
     * title : LOL大神
     * price : 1
     * status : 0
     * images : UploadFiles/AccompanyPlayNews/20190822/b40a69a1-68a8-4de1-aea6-8d9cac7800fc
     * memberId : 1083
     * weekDay : 1,2,3,4,5,6,0
     * remark : 英雄联盟大神在这里
     * areaTitle : 微信
     * audioPath : null
     * gradeTitle : 荣耀黄金
     * subjectTitle : LOL
     * subjectImages : Img/5.png
     * subject : {"id":5,"images":"Img/5.png","title":"LOL","areaTitles":"性别:男,女","gradeTitles":"段位:华贵铂金,璀璨钻石,超凡大师,傲视宗师,最强王者","accompanyPlay":[]}
     * total : 0
     */

    private int id;
    private String title;
    private int price;
    private int status;
    private String images;
    private int memberId;
    private String weekDay;
    private String remark;
    private String areaTitle;
    private Object audioPath;
    private String gradeTitle;
    private String subjectTitle;
    private String subjectImages;
    private SubjectBean subject;
    private int total;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAreaTitle() {
        return areaTitle;
    }

    public void setAreaTitle(String areaTitle) {
        this.areaTitle = areaTitle;
    }

    public Object getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(Object audioPath) {
        this.audioPath = audioPath;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
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

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class SubjectBean {
        /**
         * id : 5
         * images : Img/5.png
         * title : LOL
         * areaTitles : 性别:男,女
         * gradeTitles : 段位:华贵铂金,璀璨钻石,超凡大师,傲视宗师,最强王者
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
}
