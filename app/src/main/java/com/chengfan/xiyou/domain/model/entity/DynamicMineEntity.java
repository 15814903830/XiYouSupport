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
     * id : 195
     * memberId : 1083
     * content : 具体地址
     * images : UploadFiles/AccompanyPlayNews/20190821/b0966e88-8e37-476b-91a0-a2aeb628a60a.jpg|UploadFiles/AccompanyPlayNews/20190821/7f65e7c3-99f9-4b43-b84e-5249212a5cfa.jpg
     * createTime : 2019-08-21T18:25:55.987
     * member : {"userName":"15814903830","nickname":"胡椒","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb","vip":false,"accompanyPlay":[{"id":116,"price":12,"title":"1不后悔不会VBv","areaTitle":"微信","audioPath":"1","gradeTitle":"永恒钻石","subject":{"id":3,"images":"Img/3.png","title":"王者荣耀","areaTitles":"大区:微信,QQ","gradeTitles":"段位:尊贵铂金,永恒钻石,至尊星耀,最强王者,荣耀王者","sore":0,"pid":32,"sort":3,"accompanyPlay":[]}}]}
     * totalComment : 8
     * totalPraise : 2
     * totalSeconds : 1217421.623
     * havePraise : true
     * memberNewsComment : [{"id":228,"memberId":1083,"content":"早睡早起","memberNewsId":195,"createTime":"2019-08-22T14:31:47.5369284","member":{"id":1083,"userName":"15814903830","nickname":"胡椒","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb","gender":0,"vip":false}}]
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
    private List<MemberNewsCommentBean> memberNewsComment;

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

    public List<MemberNewsCommentBean> getMemberNewsComment() {
        return memberNewsComment;
    }

    public void setMemberNewsComment(List<MemberNewsCommentBean> memberNewsComment) {
        this.memberNewsComment = memberNewsComment;
    }

    public static class MemberBean {
        /**
         * userName : 15814903830
         * nickname : 胡椒
         * avatarUrl : UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb
         * vip : false
         * accompanyPlay : [{"id":116,"price":12,"title":"1不后悔不会VBv","areaTitle":"微信","audioPath":"1","gradeTitle":"永恒钻石","subject":{"id":3,"images":"Img/3.png","title":"王者荣耀","areaTitles":"大区:微信,QQ","gradeTitles":"段位:尊贵铂金,永恒钻石,至尊星耀,最强王者,荣耀王者","sore":0,"pid":32,"sort":3,"accompanyPlay":[]}}]
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
             * id : 116
             * price : 12
             * title : 1不后悔不会VBv
             * areaTitle : 微信
             * audioPath : 1
             * gradeTitle : 永恒钻石
             * subject : {"id":3,"images":"Img/3.png","title":"王者荣耀","areaTitles":"大区:微信,QQ","gradeTitles":"段位:尊贵铂金,永恒钻石,至尊星耀,最强王者,荣耀王者","sore":0,"pid":32,"sort":3,"accompanyPlay":[]}
             */

            private int id;
            private int price;
            private String title;
            private String areaTitle;
            private String audioPath;
            private String gradeTitle;
            private SubjectBean subject;

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

            public String getAreaTitle() {
                return areaTitle;
            }

            public void setAreaTitle(String areaTitle) {
                this.areaTitle = areaTitle;
            }

            public String getAudioPath() {
                return audioPath;
            }

            public void setAudioPath(String audioPath) {
                this.audioPath = audioPath;
            }

            public String getGradeTitle() {
                return gradeTitle;
            }

            public void setGradeTitle(String gradeTitle) {
                this.gradeTitle = gradeTitle;
            }

            public SubjectBean getSubject() {
                return subject;
            }

            public void setSubject(SubjectBean subject) {
                this.subject = subject;
            }

            public static class SubjectBean {
                /**
                 * id : 3
                 * images : Img/3.png
                 * title : 王者荣耀
                 * areaTitles : 大区:微信,QQ
                 * gradeTitles : 段位:尊贵铂金,永恒钻石,至尊星耀,最强王者,荣耀王者
                 * sore : 0
                 * pid : 32
                 * sort : 3
                 * accompanyPlay : []
                 */

                private int id;
                private String images;
                private String title;
                private String areaTitles;
                private String gradeTitles;
                private int sore;
                private int pid;
                private int sort;
                private List<?> accompanyPlay;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getImages() {
                    return images;
                }

                public void setImages(String images) {
                    this.images = images;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getAreaTitles() {
                    return areaTitles;
                }

                public void setAreaTitles(String areaTitles) {
                    this.areaTitles = areaTitles;
                }

                public String getGradeTitles() {
                    return gradeTitles;
                }

                public void setGradeTitles(String gradeTitles) {
                    this.gradeTitles = gradeTitles;
                }

                public int getSore() {
                    return sore;
                }

                public void setSore(int sore) {
                    this.sore = sore;
                }

                public int getPid() {
                    return pid;
                }

                public void setPid(int pid) {
                    this.pid = pid;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public List<?> getAccompanyPlay() {
                    return accompanyPlay;
                }

                public void setAccompanyPlay(List<?> accompanyPlay) {
                    this.accompanyPlay = accompanyPlay;
                }
            }
        }
    }

    public static class MemberNewsCommentBean {
        /**
         * id : 228
         * memberId : 1083
         * content : 早睡早起
         * memberNewsId : 195
         * createTime : 2019-08-22T14:31:47.5369284
         * member : {"id":1083,"userName":"15814903830","nickname":"胡椒","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb","gender":0,"vip":false}
         */

        private int id;
        private int memberId;
        private String content;
        private int memberNewsId;
        private String createTime;
        private MemberBeanX member;

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

        public int getMemberNewsId() {
            return memberNewsId;
        }

        public void setMemberNewsId(int memberNewsId) {
            this.memberNewsId = memberNewsId;
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

        public static class MemberBeanX {
            /**
             * id : 1083
             * userName : 15814903830
             * nickname : 胡椒
             * avatarUrl : UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb
             * gender : 0
             * vip : false
             */

            private int id;
            private String userName;
            private String nickname;
            private String avatarUrl;
            private int gender;
            private boolean vip;

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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public boolean isVip() {
                return vip;
            }

            public void setVip(boolean vip) {
                this.vip = vip;
            }
        }
    }
}
