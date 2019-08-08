package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/12:49
 * @Description:
 */
public class SayHiEntity {
    /**
     * id : 1
     * gender : 1
     * nickname : test123
     * avatarUrl : Img/touxiang.png
     * vipEffectiveDate : null
     */

    private int id;
    private int gender;
    private String nickname;
    private String avatarUrl;
    private Object vipEffectiveDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Object getVipEffectiveDate() {
        return vipEffectiveDate;
    }

    public void setVipEffectiveDate(Object vipEffectiveDate) {
        this.vipEffectiveDate = vipEffectiveDate;
    }
}
