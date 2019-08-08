package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-11/13:21
 * @Description:
 */
public class ChatGroupEntity  implements Serializable {

    /**
     * team : {"id":1,"memberId":1,"name":"string","avatarUrl":"string","createTime":"2019-07-10T19:05:36.667","member":null,"teamMember":[{"teamId":1,"memberId":1,"role":1,"notDisturb":true,"createTime":"2019-07-10T19:05:36.667"}]}
     * totalMember : 0
     */

    private TeamBean team;
    private int totalMember;

    public TeamBean getTeam() {
        return team;
    }

    public void setTeam(TeamBean team) {
        this.team = team;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(int totalMember) {
        this.totalMember = totalMember;
    }

    public static class TeamBean {
        /**
         * id : 1
         * memberId : 1
         * name : string
         * avatarUrl : string
         * createTime : 2019-07-10T19:05:36.667
         * member : null
         * teamMember : [{"teamId":1,"memberId":1,"role":1,"notDisturb":true,"createTime":"2019-07-10T19:05:36.667"}]
         */

        private int id;
        private int memberId;
        private String name;
        private String avatarUrl;
        private String createTime;
        private Object member;
        private List<TeamMemberBean> teamMember;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getMember() {
            return member;
        }

        public void setMember(Object member) {
            this.member = member;
        }

        public List<TeamMemberBean> getTeamMember() {
            return teamMember;
        }

        public void setTeamMember(List<TeamMemberBean> teamMember) {
            this.teamMember = teamMember;
        }

        public static class TeamMemberBean {
            /**
             * teamId : 1
             * memberId : 1
             * role : 1
             * notDisturb : true
             * createTime : 2019-07-10T19:05:36.667
             */

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
    }
}
