package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-17/19:59
 * @Description: 我的关注
 */
public class FinanceRecordEntity implements Serializable {


    /**
     * id : 5
     * memberId : 1
     * content : 2019-07-09测试
     * images : null
     * createTime : 2019-07-09T14:32:21.82
     * member : {"userName":"13800139000","nickname":"test123","avatarUrl":"Img/touxiang.png","vip":false,"accompanyPlay":[{"id":3,"price":10000,"title":"123456","subject":{"title":"游戏陪玩"}}]}
     * totalComment : 0
     * totalPraise : 0
     * totalSeconds : 719510.917
     * havePraise : false
     */

    private int id;
    private int memberId;
    private String content;
    private String images;
    private String createTime;
    private MemberBean member;
    private int totalComment;
    private int totalPraise;
    private double totalSeconds;
    private boolean havePraise;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalPraise() {
        return totalPraise;
    }

    public void setTotalPraise(int totalPraise) {
        this.totalPraise = totalPraise;
    }

    public double getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(double totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public boolean isHavePraise() {
        return havePraise;
    }

    public void setHavePraise(boolean havePraise) {
        this.havePraise = havePraise;
    }

    public static class MemberBean {
        /**
         * userName : 13800139000
         * nickname : test123
         * avatarUrl : Img/touxiang.png
         * vip : false
         * accompanyPlay : [{"id":3,"price":10000,"title":"123456","subject":{"title":"游戏陪玩"}}]
         */

        private String userName;
        private String nickname;
        private String avatarUrl;
        private boolean vip;
        private List<AccompanyPlayBean> accompanyPlay;

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

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }

        public List<AccompanyPlayBean> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<AccompanyPlayBean> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }

        public static class AccompanyPlayBean {
            /**
             * id : 3
             * price : 10000.0
             * title : 123456
             * subject : {"title":"游戏陪玩"}
             */

            private int id;
            private double price;
            private String title;
            private SubjectBean subject;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
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
        }
    }
}
