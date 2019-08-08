package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-12/18:17
 * @Description: 退出群
 */
public class RemoveTeamMemberBean implements Serializable {


    /**
     * teamId : 群Id
     * memberId : 会员Id
     */

    private String teamId;
    private String memberId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
