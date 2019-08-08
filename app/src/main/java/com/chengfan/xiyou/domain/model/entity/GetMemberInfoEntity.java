package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/11:18
 * @Description:
 */
public class GetMemberInfoEntity {

    /**
     * id : 1013
     * userName : 18660180001
     * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
     * nickname : 飞起来
     * age : 20
     * gender : 0
     * areaCode : 440100
     * areaName : 广东省 广州市
     * exterior : 172cm  51kg  温柔可人
     * weiXin : null
     * job : 游戏职业玩加
     * genderVideo : UploadFiles/Member/20190711/Editor/01e1be03-0af1-4c15-91c8-16acf580d719.mp4
     * verificationGenderStatus : 0
     */

    private int id;
    private String userName;
    private String avatarUrl;
    private String nickname;
    private int age;
    private int gender;
    private int areaCode;
    private String areaName;
    private String exterior;
    private String weiXin;
    private String job;
    private String genderVideo;
    private int verificationGenderStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
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

    public String getGenderVideo() {
        return genderVideo;
    }

    public void setGenderVideo(String genderVideo) {
        this.genderVideo = genderVideo;
    }

    public int getVerificationGenderStatus() {
        return verificationGenderStatus;
    }

    public void setVerificationGenderStatus(int verificationGenderStatus) {
        this.verificationGenderStatus = verificationGenderStatus;
    }
}
