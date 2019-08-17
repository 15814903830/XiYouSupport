package com.chengfan.xiyou.im;

import org.litepal.crud.LitePalSupport;

/**
 * 即时通讯用户类
 */
public class UserIMInfo extends LitePalSupport {

    private int userId;
    private String nickname;
    private String avatarUrl;

    public UserIMInfo() {
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
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
