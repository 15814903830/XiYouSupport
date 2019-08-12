package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/10:08
 * @Description:
 */
public class AccompanyEntity implements Serializable {

    /**
     * id : 21
     * title : 黄口
     * price : 46
     * images : 20190426_132522915_fc3a2a746d93dd869a166b819e540234.jpg
     * memberId : 1013
     * subjectId : 1
     * recommendTime : null
     * age : 20
     * gender : 0
     * nickname : 淋浴飞起来
     * userName : 18660180001
     * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
     * areaCode : 440100
     * areaName : 广东省 广州市
     * totalFans : 0
     * vip : false
     */

    private String id;
    private String title;
    private String price;
    private String images;
    private int memberId;
    private String subjectId;
    private String recommendTime;
    private String age;
    private String gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private String areaCode;
    private String areaName;
    private String totalFans;
    private boolean vip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(String totalFans) {
        this.totalFans = totalFans;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
