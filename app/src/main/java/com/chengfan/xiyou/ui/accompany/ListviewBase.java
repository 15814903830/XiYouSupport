package com.chengfan.xiyou.ui.accompany;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ListviewBase {

    @Override
    public String toString() {
        return "ListviewBase{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", images='" + images + '\'' +
                ", memberId=" + memberId +
                ", subjectId=" + subjectId +
                ", areaTitle='" + areaTitle + '\'' +
                ", audioPath='" + audioPath + '\'' +
                ", gradeTitle='" + gradeTitle + '\'' +
                ", recommendTime=" + recommendTime +
                ", age=" + age +
                ", gender=" + gender +
                ", nickname='" + nickname + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", areaCode=" + areaCode +
                ", areaName='" + areaName + '\'' +
                ", totalFans=" + totalFans +
                ", vip=" + vip +
                ", total=" + total +
                '}';
    }

    /**
     * id : 56
     * title : 123456
     * price : 0.01
     * status : 0
     * images : UploadFiles/AccompanyPlay/20190816/Editor/4f06a5af-9f9c-444d-ad02-06882e707cfe.mp4
     * memberId : 1072
     * subjectId : 1
     * areaTitle :
     * audioPath :
     * gradeTitle :
     * recommendTime : null
     * age : 22
     * gender : 0
     * nickname : 国彬测试（女）
     * userName : 15625105974
     * avatarUrl : UploadFiles/Member/20190814/Editor/b60c08b1-fd11-4431-88c1-a2939afae917.png
     * areaCode : 440100
     * areaName : 广东省 广州市
     * totalFans : 4
     * vip : true
     * total : 0
     */

    private int id;
    private String title;
    private double price;
    private int status;
    private String images;
    private int memberId;
    private int subjectId;
    private String areaTitle;
    private String audioPath;
    private String gradeTitle;
    private Object recommendTime;
    private int age;
    private int gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private int areaCode;
    private String areaName;
    private int totalFans;
    private boolean vip;
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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

    public Object getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Object recommendTime) {
        this.recommendTime = recommendTime;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
