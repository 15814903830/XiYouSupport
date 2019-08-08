package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/21:45
 * @Description: 游戏陪玩
 */
public class AccompanyGameEntity implements Serializable {

    /**
     * id : 8
     * title : 好玩吧
     * price : 20
     * images : UploadFiles/AccompanyPlay/20190711/Editor/cd46ab23-7c07-4f09-b17e-b38f4ac38db9.png
     * memberId : 1013
     * subjectId : 1
     * recommendTime : null
     * gender : 0
     * nickname : 飞起来
     * userName : 18660180001
     * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
     * areaCode : 440100
     * totalFans : 0
     */

    private int id;
    private String title;
    private int price;
    private String images;
    private int memberId;
    private int subjectId;
    private Object recommendTime;
    private int gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private int areaCode;
    private int totalFans;

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

    public Object getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Object recommendTime) {
        this.recommendTime = recommendTime;
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

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }
}
