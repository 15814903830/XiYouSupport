package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/23:40
 * @Description:
 */
public class MineFamilyEntity implements Serializable {

    /**
     * id : 3
     * name : 123456
     * banner : UploadFiles/Member/20190713/Editor/1ce0e388-30c2-47b2-8378-caaa206659ab.png;UploadFiles/Member/20190713/Editor/7dfc11ed-6efd-4bfe-96a8-734c3d1efa28.png
     * memberId : 1013
     * avatarUrl : null
     * describe : Ghhhhjjjk
     * createTime : 2019-07-13T17:27:01.557
     * member : {"id":1013,"userName":"18660180001","nickname":"飞起来","avatarUrl":"UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png"}
     * familyMember : [{"role":1,"familyId":3,"memberId":1013,"createTime":"2019-07-13T17:27:01.557","member":{"id":1013,"userName":"18660180001","nickname":"飞起来","avatarUrl":"UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png"},"isFans":false}]
     */

    private int id;
    private String name;
    private String banner;
    private int memberId;
    private Object avatarUrl;
    private String describe;
    private String createTime;
    private MemberBean member;
    private List<FamilyMemberBean> familyMember;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Object getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(Object avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<FamilyMemberBean> getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(List<FamilyMemberBean> familyMember) {
        this.familyMember = familyMember;
    }

    public static class MemberBean implements Serializable {
        /**
         * id : 1013
         * userName : 18660180001
         * nickname : 飞起来
         * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
         */

        private int id;
        private String userName;
        private String nickname;
        private String avatarUrl;

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

    public static class FamilyMemberBean implements Serializable {
        /**
         * role : 1
         * familyId : 3
         * memberId : 1013
         * createTime : 2019-07-13T17:27:01.557
         * member : {"id":1013,"userName":"18660180001","nickname":"飞起来","avatarUrl":"UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png"}
         * isFans : false
         */

        private int role;
        private int familyId;
        private int memberId;
        private String createTime;
        private MemberBeanX member;
        private boolean isFans;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public MemberBeanX getMember() {
            return member;
        }

        public void setMember(MemberBeanX member) {
            this.member = member;
        }

        public boolean isIsFans() {
            return isFans;
        }

        public void setIsFans(boolean isFans) {
            this.isFans = isFans;
        }

        public static class MemberBeanX {
            /**
             * id : 1013
             * userName : 18660180001
             * nickname : 飞起来
             * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
             */

            private int id;
            private String userName;
            private String nickname;
            private String avatarUrl;

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
    }
}
