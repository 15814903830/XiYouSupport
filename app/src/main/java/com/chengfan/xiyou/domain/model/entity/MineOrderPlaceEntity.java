package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/13:07
 * @Description:
 */
public class MineOrderPlaceEntity {
    /**
     * hour : 1
     * status : 5
     * comment : 订单号950014好玩
     * unitPrice : 100
     * payPrice : 100
     * commentTime : 2019-07-21T22:57:52.947
     * createTime : 2019-07-21T22:37:52.947
     * memberId : 1013
     * uniformOrderId : 950014
     * accompanyPlayId : 1
     * finishTime : 2019-07-21T22:55:52.947
     * statusTag : 已完成
     * accompanyPlay : {"id":1,"price":0,"title":"测试陪玩1","subject":{"title":"游戏陪玩"},"member":{"id":1,"gender":1,"userName":"13800139000","nickname":"昵称（可选）","avatarUrl":"Img/touxiang.png"}}
     */

    private int hour;
    private int status;
    private String comment;
    private int unitPrice;
    private int payPrice;
    private String commentTime;
    private String createTime;
    private int memberId;
    private int uniformOrderId;
    private int accompanyPlayId;
    private String finishTime;
    private String statusTag;
    private AccompanyPlayBean accompanyPlay;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(int payPrice) {
        this.payPrice = payPrice;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getUniformOrderId() {
        return uniformOrderId;
    }

    public void setUniformOrderId(int uniformOrderId) {
        this.uniformOrderId = uniformOrderId;
    }

    public int getAccompanyPlayId() {
        return accompanyPlayId;
    }

    public void setAccompanyPlayId(int accompanyPlayId) {
        this.accompanyPlayId = accompanyPlayId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStatusTag() {
        return statusTag;
    }

    public void setStatusTag(String statusTag) {
        this.statusTag = statusTag;
    }

    public AccompanyPlayBean getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(AccompanyPlayBean accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public static class AccompanyPlayBean {
        /**
         * id : 1
         * price : 0
         * title : 测试陪玩1
         * subject : {"title":"游戏陪玩"}
         * member : {"id":1,"gender":1,"userName":"13800139000","nickname":"昵称（可选）","avatarUrl":"Img/touxiang.png"}
         */

        private int id;
        private int price;
        private String title;
        private SubjectBean subject;
        private MemberBean member;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public SubjectBean getSubject() {
            return subject;
        }

        public void setSubject(SubjectBean subject) {
            this.subject = subject;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public static class SubjectBean {
            /**
             * title : 游戏陪玩
             */

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class MemberBean {
            /**
             * id : 1
             * gender : 1
             * userName : 13800139000
             * nickname : 昵称（可选）
             * avatarUrl : Img/touxiang.png
             */

            private int id;
            private int gender;
            private String userName;
            private String nickname;
            private String avatarUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
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
