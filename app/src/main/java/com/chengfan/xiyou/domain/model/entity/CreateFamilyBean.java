package com.chengfan.xiyou.domain.model.entity;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/11:48
 * @Description:
 */
public class CreateFamilyBean {

    /**
     * memberId : 创建家族会员Id
     * name : 家族名称
     * avatarUrl : 家族头像
     * familyMember : [{"memberId":"创建族长会员Id","role":"角色1.群主"},{"memberId":"普通成员Id","role":"角色10.普通"},{"memberId":"普通成员Id","role":"角色10.普通"}]
     */

    private String memberId;
    private String name;
    private String avatarUrl;
    private List<FamilyMemberBean> familyMember;

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

    public List<FamilyMemberBean> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<FamilyMemberBean> familyMember) {
        this.familyMember = familyMember;
    }

    public static class FamilyMemberBean {
        /**
         * memberId : 创建族长会员Id
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
