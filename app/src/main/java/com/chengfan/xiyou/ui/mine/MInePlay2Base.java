package com.chengfan.xiyou.ui.mine;

import java.util.List;

public class MInePlay2Base {

    /**
     * id : 111
     * title : 叫姐姐
     * images : UploadFiles/AccompanyPlayNews/20190831/c3f93159-27e9-45b9-aeeb-c6ebabab6919
     * price : 111
     * remark : 不好解决
     * weekDay : 1,2
     * subjectId : 1
     * memberId : 1083
     * areaTitle :
     * audioPath : UploadFiles/AccompanyPlayNews/20190831/cc8da46a-0b35-40fc-b521-4eb25de169b0
     * gradeTitle :
     * status : 0
     * serviceEndTime : 15:12:00
     * serviceStartTime : 15:12:00
     * createTime : 2019-08-31T15:13:12.24
     * gender : 0
     * userName : 15814903830
     * nickname : 胡椒
     * avatarUrl : UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb
     * age : 555
     * areaCode : 130100
     * areaName : 浙江省,杭州市,滨江区
     * lableAudio : null
     * applyLable : null
     * approvalLable : null
     * lableStatus : null
     * totalFans : 3
     * vip : false
     * subject : {"id":1,"images":"Img/1.png","title":"守望先锋","areaTitles":"性别:男,女","gradeTitles":"段位:白金,钻石,大师,宗师,璀璨钻石,500强","sore":0,"accompanyPlay":[]}
     * isFans : false
     * commentTotal : 0
     * order : []
     */

    private int id;
    private String title;
    private String images;
    private int price;
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
    private int age;
    private int areaCode;
    private String areaName;
    private Object lableAudio;
    private Object applyLable;
    private Object approvalLable;
    private Object lableStatus;
    private int totalFans;
    private boolean vip;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public Object getLableAudio() {
        return lableAudio;
    }

    public void setLableAudio(Object lableAudio) {
        this.lableAudio = lableAudio;
    }

    public Object getApplyLable() {
        return applyLable;
    }

    public void setApplyLable(Object applyLable) {
        this.applyLable = applyLable;
    }

    public Object getApprovalLable() {
        return approvalLable;
    }

    public void setApprovalLable(Object approvalLable) {
        this.approvalLable = approvalLable;
    }

    public Object getLableStatus() {
        return lableStatus;
    }

    public void setLableStatus(Object lableStatus) {
        this.lableStatus = lableStatus;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
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

    public static class SubjectBean {
        /**
         * id : 1
         * images : Img/1.png
         * title : 守望先锋
         * areaTitles : 性别:男,女
         * gradeTitles : 段位:白金,钻石,大师,宗师,璀璨钻石,500强
         * sore : 0
         * accompanyPlay : []
         */

        private int id;
        private String images;
        private String title;
        private String areaTitles;
        private String gradeTitles;
        private int sore;
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

        public int getSore() {
            return sore;
        }

        public void setSore(int sore) {
            this.sore = sore;
        }

        public List<?> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<?> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }
    }
}
