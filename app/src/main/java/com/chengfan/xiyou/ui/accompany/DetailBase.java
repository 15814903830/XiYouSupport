package com.chengfan.xiyou.ui.accompany;

import java.io.Serializable;
import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class DetailBase  implements Serializable {

    /**
     * id : 56
     * title : 123456
     * images : UploadFiles/AccompanyPlay/20190816/Editor/4f06a5af-9f9c-444d-ad02-06882e707cfe.mp4
     * price : 0.01
     * remark : 123456
     * weekDay : 1,2,3,4,5,6,0
     * subjectId : 1
     * memberId : 1072
     * areaTitle :
     * audioPath :
     * gradeTitle :
     * status : 0
     * serviceEndTime : 23:59:00
     * serviceStartTime : 00:01:00
     * createTime : 2019-08-16T10:23:03.53
     * gender : 0
     * userName : 15625105974
     * nickname : 国彬测试（女）
     * avatarUrl : UploadFiles/Member/20190814/Editor/b60c08b1-fd11-4431-88c1-a2939afae917.png
     * subject : {"id":1,"images":"Img/1.png","title":"守望先锋","areaTitles":null,"gradeTitles":null,"accompanyPlay":[]}
     * isFans : false
     * commentTotal : 0
     * order : []
     */

    private int id;
    private String title;
    private String images;
    private double price;
    private String remark;
    private String weekDay;
    private int subjectId;
    private int memberId;
    private String areaTitle;
    private String audioPath;
    private String gradeTitle;
    private int status;
    private String serviceEndTime;
    private String serviceStartTime;
    private String createTime;
    private int gender;
    private String userName;
    private String nickname;
    private String avatarUrl;
    private SubjectBean subject;
    private boolean isFans;
    private int commentTotal;
    private List<?> order;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
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

    public String getAreaTitle() {
        return areaTitle;
    }

    public void setAreaTitle(String areaTitle) {
        this.areaTitle = areaTitle;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
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

    public List<?> getOrder() {
        return order;
    }

    public void setOrder(List<?> order) {
        this.order = order;
    }

    public static class SubjectBean  implements Serializable{
        /**
         * id : 1
         * images : Img/1.png
         * title : 守望先锋
         * areaTitles : null
         * gradeTitles : null
         * accompanyPlay : []
         */

        private int id;
        private String images;
        private String title;
        private Object areaTitles;
        private Object gradeTitles;
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

        public Object getAreaTitles() {
            return areaTitles;
        }

        public void setAreaTitles(Object areaTitles) {
            this.areaTitles = areaTitles;
        }

        public Object getGradeTitles() {
            return gradeTitles;
        }

        public void setGradeTitles(Object gradeTitles) {
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
