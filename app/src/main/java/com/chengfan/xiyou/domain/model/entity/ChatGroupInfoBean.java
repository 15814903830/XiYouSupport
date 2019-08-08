package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/18:43
 * @Description: 消息免打扰
 */
public class ChatGroupInfoBean implements Serializable {
    private int teamId;
    private int memberId;
    private int role;
    private boolean notDisturb;
    private String createTime;


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isNotDisturb() {
        return notDisturb;
    }

    public void setNotDisturb(boolean notDisturb) {
        this.notDisturb = notDisturb;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
