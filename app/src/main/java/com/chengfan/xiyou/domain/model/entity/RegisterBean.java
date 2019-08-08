package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/15:03
 * @Description:
 */
public class RegisterBean {

    /**
     * entryway : 手机号
     * code : 手机验证码
     * password : 密码
     * reference : 推荐码(可选)
     * nickname : 昵称(可选)
     * avatarUrl : 头像(可选)
     * gender : 性别(可选，1.男，0.女)
     */

    private String entryway;
    private String code;
    private String password;
    private String reference;
    private String nickname;
    private String avatarUrl;
    private String gender;

    public String getEntryway() {
        return entryway;
    }

    public void setEntryway(String entryway) {
        this.entryway = entryway;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
