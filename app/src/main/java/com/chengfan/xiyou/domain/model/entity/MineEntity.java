package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-18/01:42
 * @Description: 个人中心
 */
public class MineEntity implements Serializable {

    /**
     * id : 1013
     * userName : 18660180001
     * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
     * nickname : 咯五天
     * age : 49988
     * gender : 1
     * areaCode : 440100
     * areaName : 广东省 广州市
     * exterior : 168CM 51KG 贤良淑德
     * weiXin : 1111
     * job : 导游
     * genderVideo : UploadFiles/Member/20190711/Editor/01e1be03-0af1-4c15-91c8-16acf580d719.mp4
     * vip : false
     * totalNews : 2
     * totalCare : 2
     * totalFans : 5
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
    private boolean vip;
    private int totalNews;
    private int totalCare;
    private int totalFans;

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

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getTotalNews() {
        return totalNews;
    }

    public void setTotalNews(int totalNews) {
        this.totalNews = totalNews;
    }

    public int getTotalCare() {
        return totalCare;
    }

    public void setTotalCare(int totalCare) {
        this.totalCare = totalCare;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }
}
