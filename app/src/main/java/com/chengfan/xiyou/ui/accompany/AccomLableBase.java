package com.chengfan.xiyou.ui.accompany;

import java.util.List;

public class AccomLableBase {

    /**
     * model : {"id":237,"images":"","content":"管理规范","memberId":1127,"createTime":"2019-08-31T13:26:00.193","totalSeconds":-378452.99,"totalComment":0,"totalPraise":0,"havePraise":false,"member":{"gender":1,"nickname":"蜗牛超车","userName":"13800139000","avatarUrl":"UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png","lableAudio":"0d104a48-6ec6-4306-a7a7-49169e251eb0.aac","applyLable":"1,2,3,4","approvalLable":"6,7","lableStatus":1,"isFans":true,"vip":false},"accompanyPlay":[{"id":69,"title":"吃鸡啦 欢迎来撩","images":"UploadFiles/AccompanyPlay/20190821/Editor/08747209-3ca3-4a7a-bba9-cb8462fa9e70.mp4","price":5,"memberId":1127,"areaTitle":"微信","audioPath":"UploadFiles/Member/20190821/Editor/13b44ecf-cfbd-4540-af86-c7c6b9285cc3.aac","gradeTitle":"荣耀皇冠","subjectId":2,"subject":{"id":2,"images":"Img/2.png","title":"和平精英","areaTitles":"大区:微信,QQ","gradeTitles":"段位:坚韧白金,不朽星钻,荣耀皇冠,超级王牌,无敌战神","sore":0,"pid":32,"sort":2,"accompanyPlay":[]}}],"memberNewsComment":[]}
     * applyLableList : [{"id":6,"icon":"img/lable/biaoqian06.png","text":"声优认证","needApproval":true,"enabled":false,"sore":0},{"id":7,"icon":"img/lable/biaoqian07.png","text":"语音连麦","needApproval":true,"enabled":false,"sore":0}]
     * approvalLableList : [{"id":1,"icon":"img/lable/biaoqian01.png","text":"萝莉音","needApproval":false,"enabled":false,"sore":0},{"id":2,"icon":"img/lable/biaoqian02.png","text":"御姐音","needApproval":false,"enabled":false,"sore":0},{"id":3,"icon":"img/lable/biaoqian03.png","text":"大叔音","needApproval":false,"enabled":false,"sore":0},{"id":4,"icon":"img/lable/biaoqian04.png","text":"双音线","needApproval":false,"enabled":false,"sore":0}]
     */

    private ModelBean model;
    private List<ApplyLableListBean> applyLableList;
    private List<ApprovalLableListBean> approvalLableList;

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public List<ApplyLableListBean> getApplyLableList() {
        return applyLableList;
    }

    public void setApplyLableList(List<ApplyLableListBean> applyLableList) {
        this.applyLableList = applyLableList;
    }

    public List<ApprovalLableListBean> getApprovalLableList() {
        return approvalLableList;
    }

    public void setApprovalLableList(List<ApprovalLableListBean> approvalLableList) {
        this.approvalLableList = approvalLableList;
    }

    public static class ModelBean {
        /**
         * id : 237
         * images :
         * content : 管理规范
         * memberId : 1127
         * createTime : 2019-08-31T13:26:00.193
         * totalSeconds : -378452.99
         * totalComment : 0
         * totalPraise : 0
         * havePraise : false
         * member : {"gender":1,"nickname":"蜗牛超车","userName":"13800139000","avatarUrl":"UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png","lableAudio":"0d104a48-6ec6-4306-a7a7-49169e251eb0.aac","applyLable":"1,2,3,4","approvalLable":"6,7","lableStatus":1,"isFans":true,"vip":false}
         * accompanyPlay : [{"id":69,"title":"吃鸡啦 欢迎来撩","images":"UploadFiles/AccompanyPlay/20190821/Editor/08747209-3ca3-4a7a-bba9-cb8462fa9e70.mp4","price":5,"memberId":1127,"areaTitle":"微信","audioPath":"UploadFiles/Member/20190821/Editor/13b44ecf-cfbd-4540-af86-c7c6b9285cc3.aac","gradeTitle":"荣耀皇冠","subjectId":2,"subject":{"id":2,"images":"Img/2.png","title":"和平精英","areaTitles":"大区:微信,QQ","gradeTitles":"段位:坚韧白金,不朽星钻,荣耀皇冠,超级王牌,无敌战神","sore":0,"pid":32,"sort":2,"accompanyPlay":[]}}]
         * memberNewsComment : []
         */

        private int id;
        private String images;
        private String content;
        private int memberId;
        private String createTime;
        private double totalSeconds;
        private int totalComment;
        private int totalPraise;
        private boolean havePraise;
        private MemberBean member;
        private List<AccompanyPlayBean> accompanyPlay;
        private List<?> memberNewsComment;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public double getTotalSeconds() {
            return totalSeconds;
        }

        public void setTotalSeconds(double totalSeconds) {
            this.totalSeconds = totalSeconds;
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

        public boolean isHavePraise() {
            return havePraise;
        }

        public void setHavePraise(boolean havePraise) {
            this.havePraise = havePraise;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public List<AccompanyPlayBean> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<AccompanyPlayBean> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }

        public List<?> getMemberNewsComment() {
            return memberNewsComment;
        }

        public void setMemberNewsComment(List<?> memberNewsComment) {
            this.memberNewsComment = memberNewsComment;
        }

        public static class MemberBean {
            /**
             * gender : 1
             * nickname : 蜗牛超车
             * userName : 13800139000
             * avatarUrl : UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png
             * lableAudio : 0d104a48-6ec6-4306-a7a7-49169e251eb0.aac
             * applyLable : 1,2,3,4
             * approvalLable : 6,7
             * lableStatus : 1
             * isFans : true
             * vip : false
             */

            private int gender;
            private String nickname;
            private String userName;
            private String avatarUrl;
            private String lableAudio;
            private String applyLable;
            private String approvalLable;
            private int lableStatus;
            private boolean isFans;
            private boolean vip;

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
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

            public String getLableAudio() {
                return lableAudio;
            }

            public void setLableAudio(String lableAudio) {
                this.lableAudio = lableAudio;
            }

            public String getApplyLable() {
                return applyLable;
            }

            public void setApplyLable(String applyLable) {
                this.applyLable = applyLable;
            }

            public String getApprovalLable() {
                return approvalLable;
            }

            public void setApprovalLable(String approvalLable) {
                this.approvalLable = approvalLable;
            }

            public int getLableStatus() {
                return lableStatus;
            }

            public void setLableStatus(int lableStatus) {
                this.lableStatus = lableStatus;
            }

            public boolean isIsFans() {
                return isFans;
            }

            public void setIsFans(boolean isFans) {
                this.isFans = isFans;
            }

            public boolean isVip() {
                return vip;
            }

            public void setVip(boolean vip) {
                this.vip = vip;
            }
        }

        public static class AccompanyPlayBean {
            /**
             * id : 69
             * title : 吃鸡啦 欢迎来撩
             * images : UploadFiles/AccompanyPlay/20190821/Editor/08747209-3ca3-4a7a-bba9-cb8462fa9e70.mp4
             * price : 5
             * memberId : 1127
             * areaTitle : 微信
             * audioPath : UploadFiles/Member/20190821/Editor/13b44ecf-cfbd-4540-af86-c7c6b9285cc3.aac
             * gradeTitle : 荣耀皇冠
             * subjectId : 2
             * subject : {"id":2,"images":"Img/2.png","title":"和平精英","areaTitles":"大区:微信,QQ","gradeTitles":"段位:坚韧白金,不朽星钻,荣耀皇冠,超级王牌,无敌战神","sore":0,"pid":32,"sort":2,"accompanyPlay":[]}
             */

            private int id;
            private String title;
            private String images;
            private int price;
            private int memberId;
            private String areaTitle;
            private String audioPath;
            private String gradeTitle;
            private int subjectId;
            private SubjectBean subject;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
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

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }

            public SubjectBean getSubject() {
                return subject;
            }

            public void setSubject(SubjectBean subject) {
                this.subject = subject;
            }

            public static class SubjectBean {
                /**
                 * id : 2
                 * images : Img/2.png
                 * title : 和平精英
                 * areaTitles : 大区:微信,QQ
                 * gradeTitles : 段位:坚韧白金,不朽星钻,荣耀皇冠,超级王牌,无敌战神
                 * sore : 0
                 * pid : 32
                 * sort : 2
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

    public static class ApplyLableListBean {
        @Override
        public String toString() {
            return "ApplyLableListBean{" +
                    "id=" + id +
                    ", icon='" + icon + '\'' +
                    ", text='" + text + '\'' +
                    ", needApproval=" + needApproval +
                    ", enabled=" + enabled +
                    ", sore=" + sore +
                    '}';
        }

        /**
         * id : 6
         * icon : img/lable/biaoqian06.png
         * text : 声优认证
         * needApproval : true
         * enabled : false
         * sore : 0
         */

        private int id;
        private String icon;
        private String text;
        private boolean needApproval;
        private boolean enabled;
        private int sore;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isNeedApproval() {
            return needApproval;
        }

        public void setNeedApproval(boolean needApproval) {
            this.needApproval = needApproval;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getSore() {
            return sore;
        }

        public void setSore(int sore) {
            this.sore = sore;
        }
    }

    public static class ApprovalLableListBean {
        /**
         * id : 1
         * icon : img/lable/biaoqian01.png
         * text : 萝莉音
         * needApproval : false
         * enabled : false
         * sore : 0
         */

        private int id;
        private String icon;
        private String text;
        private boolean needApproval;
        private boolean enabled;
        private int sore;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isNeedApproval() {
            return needApproval;
        }

        public void setNeedApproval(boolean needApproval) {
            this.needApproval = needApproval;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getSore() {
            return sore;
        }

        public void setSore(int sore) {
            this.sore = sore;
        }
    }
}
