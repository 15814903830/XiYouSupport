package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-17/20:36
 * @Description: 我的动态
 */
public class DynamicMineEntity implements Serializable {

    /**
     * id : 30
     * memberId : 1013
     * content : 松榆里
     * images : 20190422_153554644_344df421d75e70e59cf97feb2ec09728.jpg|20190422_153604689_b343425a1ea5b70b4d0aa5cffd94676f.png
     * createTime : 2019-07-21T13:36:27.613
     * member : {"userName":"18660180001","nickname":"红木","avatarUrl":"UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png","vip":false,"accompanyPlay":[{"id":21,"price":46,"title":"黄口","subject":{"title":"游戏陪玩"}}]}
     * totalComment : 0
     * totalPraise : 0
     * totalSeconds : 41750.926999999996
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
         * userName : 18660180001
         * nickname : 红木
         * avatarUrl : UploadFiles/Member/20190711/Editor/b2278344-0253-447d-9bf7-c122051c2666.png
         * vip : false
         * accompanyPlay : [{"id":21,"price":46,"title":"黄口","subject":{"title":"游戏陪玩"}}]
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
             * id : 21
             * price : 46.0
             * title : 黄口
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
