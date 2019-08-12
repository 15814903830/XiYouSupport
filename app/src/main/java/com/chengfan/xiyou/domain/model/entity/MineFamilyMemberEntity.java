package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/00:02
 * @Description: 家族成员
 */
public class MineFamilyMemberEntity  implements Serializable {

    /**
     * role : 1
     * createTime : 2019-07-12T15:30:51.66
     * id : 1010
     * userName : 18660181698
     * nickname : 小鱼钓花猫
     * avatarUrl : UploadFiles/Member/20190717/Editor/4d99ece7-f671-40ca-8708-f71f5015e1ee.png
     */

    private int role;
    private String createTime;
    private int id;
    private String userName;
    private String nickname;
    private String avatarUrl;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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
}
