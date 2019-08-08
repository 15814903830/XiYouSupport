package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-13/13:00
 * @Description: 创建群组
 */
public class ChatGroupCreateBean implements Serializable {

    /**
     * memberId : 创建群组会员Id
     * name : 群组名称
     * avatarUrl : 群组头像
     * teamMember : [{"memberId":"创建群主会员Id","role":"角色1.群主"},{"memberId":"普通成员Id","role":"角色10.普通"},{"memberId":"普通成员Id","role":"角色10.普通"}]
     */

    private String memberId;
    private String name;
    private String avatarUrl;
    private List<TeamMemberBean> teamMember;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<TeamMemberBean> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(List<TeamMemberBean> teamMember) {
        this.teamMember = teamMember;
    }

    public static class TeamMemberBean {
        /**
         * memberId : 创建群主会员Id
         * role : 角色1.群主
         */

        private String memberId;
        private String role;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
