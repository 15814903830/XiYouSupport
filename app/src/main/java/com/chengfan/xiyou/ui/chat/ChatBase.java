package com.chengfan.xiyou.ui.chat;

import java.util.List;

public class ChatBase {

    /**
     * id : 36
     * name : 哈哈哈
     * banner : UploadFiles/AccompanyPlayNews/20190827/c8cba8d3-bfca-44dc-b7e0-5d29bc5a22d2|UploadFiles/AccompanyPlayNews/20190827/6c6e7b1f-2bcf-4088-ab85-38b26dbd3c3e|UploadFiles/AccompanyPlayNews/20190827/257b2f2f-519f-4c8f-b26d-55eb1f9911b9|
     * memberId : 1083
     * avatarUrl : null
     * describe : 把呵呵红红火火
     * createTime : 2019-08-26T16:00:30.873
     * member : {"id":1083,"userName":"15814903830","nickname":"胡椒","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb"}
     * familyMember : [{"role":1,"familyId":36,"memberId":1083,"createTime":"2019-08-26T16:00:30.873","member":{"id":1083,"userName":"15814903830","nickname":"胡椒","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb"},"isFans":false}]
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

    public static class MemberBean {
        /**
         * id : 1083
         * userName : 15814903830
         * nickname : 胡椒
         * avatarUrl : UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb
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

    public static class FamilyMemberBean {
        /**
         * role : 1
         * familyId : 36
         * memberId : 1083
         * createTime : 2019-08-26T16:00:30.873
         * member : {"id":1083,"userName":"15814903830","nickname":"胡椒","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb"}
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
             * id : 1083
             * userName : 15814903830
             * nickname : 胡椒
             * avatarUrl : UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb
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
