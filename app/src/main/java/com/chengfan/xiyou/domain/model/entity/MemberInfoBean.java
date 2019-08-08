package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/11:31
 * @Description:
 */
public class MemberInfoBean {

    /**
     * id : 会员Id（必选）
     * gender : 性别（可选）
     * avatarUrl : 头像（可选）
     * nickname : 昵称（可选）
     * age : 年龄（可选）
     * areaCode : 区域代码（可选）
     * areaName : 区域名称（可选）
     * exterior : 外貌（可选）
     * job : 职业（可选）
     * weiXinUId : 微信Id（可选）
     * genderVideo : 视频验证（可选）
     */

    private String id;
    private String gender;
    private String avatarUrl;
    private String nickname;
    private String age;
    private String areaCode;
    private String areaName;
    private String exterior;
    private String job;
    private String weiXinUId;
    private String genderVideo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWeiXinUId() {
        return weiXinUId;
    }

    public void setWeiXinUId(String weiXinUId) {
        this.weiXinUId = weiXinUId;
    }

    public String getGenderVideo() {
        return genderVideo;
    }

    public void setGenderVideo(String genderVideo) {
        this.genderVideo = genderVideo;
    }
}
