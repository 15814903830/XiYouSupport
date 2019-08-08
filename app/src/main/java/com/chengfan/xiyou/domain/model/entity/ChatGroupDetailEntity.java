package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/23:30
 * @Description:
 */
public class ChatGroupDetailEntity implements Serializable {

    /**
     * id : 13
     * name : 、1册
     * memberId : 1012
     * avatarUrl : null
     * createTime : 2019-07-22T22:10:01.95
     * teamMemberAvatarUrl : Img/touxiang.png|Img/touxiang.png
     * member : {"id":1012,"avatarUrl":"Img/touxiang.png","userName":"15899365515","nickname":"高冷"}
     * teamMember : [{"teamMember":{"teamId":13,"role":10,"memberId":1003,"createTime":"2019-07-22T22:10:01.95","notDisturb":false,"member":{"id":1003,"nickname":"昵称-1003","userName":"13800138003","avatarUrl":"Img/touxiang.png"}}},{"teamMember":{"teamId":13,"role":1,"memberId":1012,"createTime":"2019-07-22T22:10:01.95","notDisturb":false,"member":{"id":1012,"nickname":"高冷","userName":"15899365515","avatarUrl":"Img/touxiang.png"}}}]
     */

    private int id;
    private String name;
    private int memberId;
    private Object avatarUrl;
    private String createTime;
    private String teamMemberAvatarUrl;
    private MemberBean member;
    private List<TeamMemberBeanX> teamMember;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTeamMemberAvatarUrl() {
        return teamMemberAvatarUrl;
    }

    public void setTeamMemberAvatarUrl(String teamMemberAvatarUrl) {
        this.teamMemberAvatarUrl = teamMemberAvatarUrl;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public List<TeamMemberBeanX> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(List<TeamMemberBeanX> teamMember) {
        this.teamMember = teamMember;
    }

    public static class MemberBean implements Serializable {
        /**
         * id : 1012
         * avatarUrl : Img/touxiang.png
         * userName : 15899365515
         * nickname : 高冷
         */

        private int id;
        private String avatarUrl;
        private String userName;
        private String nickname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
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
    }

    public static class TeamMemberBeanX implements Serializable {
        /**
         * teamMember : {"teamId":13,"role":10,"memberId":1003,"createTime":"2019-07-22T22:10:01.95","notDisturb":false,"member":{"id":1003,"nickname":"昵称-1003","userName":"13800138003","avatarUrl":"Img/touxiang.png"}}
         */

        private TeamMemberBean teamMember;

        public TeamMemberBean getTeamMember() {
            return teamMember;
        }

        public void setTeamMember(TeamMemberBean teamMember) {
            this.teamMember = teamMember;
        }

        public static class TeamMemberBean {
            /**
             * teamId : 13
             * role : 10
             * memberId : 1003
             * createTime : 2019-07-22T22:10:01.95
             * notDisturb : false
             * member : {"id":1003,"nickname":"昵称-1003","userName":"13800138003","avatarUrl":"Img/touxiang.png"}
             */

            private int teamId;
            private int role;
            private int memberId;
            private String createTime;
            private boolean notDisturb;
            private MemberBeanX member;

            public int getTeamId() {
                return teamId;
            }

            public void setTeamId(int teamId) {
                this.teamId = teamId;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
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

            public boolean isNotDisturb() {
                return notDisturb;
            }

            public void setNotDisturb(boolean notDisturb) {
                this.notDisturb = notDisturb;
            }

            public MemberBeanX getMember() {
                return member;
            }

            public void setMember(MemberBeanX member) {
                this.member = member;
            }

            public static class MemberBeanX {
                /**
                 * id : 1003
                 * nickname : 昵称-1003
                 * userName : 13800138003
                 * avatarUrl : Img/touxiang.png
                 */

                private int id;
                private String nickname;
                private String userName;
                private String avatarUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
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
}
