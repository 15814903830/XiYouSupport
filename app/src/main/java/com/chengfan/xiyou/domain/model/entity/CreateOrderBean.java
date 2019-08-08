package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-17/19:21
 * @Description: 下单
 */
public class CreateOrderBean implements Serializable {


    /**
     * uniformOrderId : 0
     * accompanyPlayId : 0
     * memberId : 0
     * startTime : 2019-07-16T15:53:32.105Z
     * endTime : 2019-07-16T15:53:32.105Z
     * finishTime : 2019-07-16T15:53:32.105Z
     * comment : string
     * commentTime : 2019-07-16T15:53:32.105Z
     * unitPrice : 0
     * totalPrice : 0
     * payPrice : 0
     * hour : 0
     * status : 0
     * remark : string
     * refuseRemark : string
     * createTime : 2019-07-16T15:53:32.105Z
     * accompanyPlay : {"id":0,"subjectId":0,"memberId":0,"title":"string","images":"string","weekDay":"string","serviceStartTime":"string","serviceEndTime":"string","price":0,"remark":"string","status":0,"recommendTime":"2019-07-16T15:53:32.105Z","createTime":"2019-07-16T15:53:32.105Z","member":{"id":0,"reference":0,"balance":0,"userName":"string","password":"string","weiXinUid":"string","gender":0,"avatarUrl":"string","nickname":"string","age":0,"areaCode":0,"areaName":"string","exterior":"string","weiXin":"string","job":"string","verificationGenderStatus":0,"genderVideo":"string","vipEffectiveDate":"2019-07-16T15:53:32.105Z","recommendTime":"2019-07-16T15:53:32.105Z","createTime":"2019-07-16T15:53:32.105Z","accompanyPlay":[null],"accompanyPlayOrder":[null],"addressBookFriend":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.105Z"}],"addressBookMember":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.105Z"}],"family":[{"id":0,"memberId":0,"avatarUrl":"string","banner":"string","name":"string","describe":"string","createTime":"2019-07-16T15:53:32.105Z","familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}]}],"familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}],"financeRecord":[{"id":0,"memberId":0,"receiptor":0,"quota":0,"balance":0,"currencyType":0,"transactionType":0,"status":0,"remarks":"string","createTime":"2019-07-16T15:53:32.105Z"}],"memberNews":[{"id":0,"memberId":0,"content":"string","images":"string","createTime":"2019-07-16T15:53:32.105Z","memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.105Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]}],"memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"memberShipFriend":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"memberShipMember":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"team":[{"id":0,"memberId":0,"name":"string","avatarUrl":"string","createTime":"2019-07-16T15:53:32.106Z","teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}],"teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]},"subject":{"id":0,"images":"string","title":"string","accompanyPlay":[null]},"accompanyPlayOrder":[null]}
     * member : {"id":0,"reference":0,"balance":0,"userName":"string","password":"string","weiXinUid":"string","gender":0,"avatarUrl":"string","nickname":"string","age":0,"areaCode":0,"areaName":"string","exterior":"string","weiXin":"string","job":"string","verificationGenderStatus":0,"genderVideo":"string","vipEffectiveDate":"2019-07-16T15:53:32.106Z","recommendTime":"2019-07-16T15:53:32.106Z","createTime":"2019-07-16T15:53:32.106Z","accompanyPlay":[null],"accompanyPlayOrder":[null],"addressBookFriend":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"addressBookMember":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"family":[{"id":0,"memberId":0,"avatarUrl":"string","banner":"string","name":"string","describe":"string","createTime":"2019-07-16T15:53:32.106Z","familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.106Z"}]}],"familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.106Z"}],"financeRecord":[{"id":0,"memberId":0,"receiptor":0,"quota":0,"balance":0,"currencyType":0,"transactionType":0,"status":0,"remarks":"string","createTime":"2019-07-16T15:53:32.106Z"}],"memberNews":[{"id":0,"memberId":0,"content":"string","images":"string","createTime":"2019-07-16T15:53:32.106Z","memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]}],"memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"memberShipFriend":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"memberShipMember":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"team":[{"id":0,"memberId":0,"name":"string","avatarUrl":"string","createTime":"2019-07-16T15:53:32.106Z","teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}],"teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}
     * uniformOrder : {"id":0,"paymentChannel":0,"transactionType":0,"createTime":"2019-07-16T15:53:32.106Z"}
     */

    private int uniformOrderId;
    private int accompanyPlayId;
    private int memberId;
    private String startTime;
    private String endTime;
    private String finishTime;
    private String comment;
    private String commentTime;
    private int unitPrice;
    private int totalPrice;
    private int payPrice;
    private int hour;
    private int status;
    private String remark;
    private String refuseRemark;
    private String createTime;
    private AccompanyPlayBean accompanyPlay;
    private MemberBeanX member;
    private UniformOrderBean uniformOrder;

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

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(int payPrice) {
        this.payPrice = payPrice;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public AccompanyPlayBean getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(AccompanyPlayBean accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }

    public MemberBeanX getMember() {
        return member;
    }

    public void setMember(MemberBeanX member) {
        this.member = member;
    }

    public UniformOrderBean getUniformOrder() {
        return uniformOrder;
    }

    public void setUniformOrder(UniformOrderBean uniformOrder) {
        this.uniformOrder = uniformOrder;
    }

    public static class AccompanyPlayBean {
        /**
         * id : 0
         * subjectId : 0
         * memberId : 0
         * title : string
         * images : string
         * weekDay : string
         * serviceStartTime : string
         * serviceEndTime : string
         * price : 0
         * remark : string
         * status : 0
         * recommendTime : 2019-07-16T15:53:32.105Z
         * createTime : 2019-07-16T15:53:32.105Z
         * member : {"id":0,"reference":0,"balance":0,"userName":"string","password":"string","weiXinUid":"string","gender":0,"avatarUrl":"string","nickname":"string","age":0,"areaCode":0,"areaName":"string","exterior":"string","weiXin":"string","job":"string","verificationGenderStatus":0,"genderVideo":"string","vipEffectiveDate":"2019-07-16T15:53:32.105Z","recommendTime":"2019-07-16T15:53:32.105Z","createTime":"2019-07-16T15:53:32.105Z","accompanyPlay":[null],"accompanyPlayOrder":[null],"addressBookFriend":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.105Z"}],"addressBookMember":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.105Z"}],"family":[{"id":0,"memberId":0,"avatarUrl":"string","banner":"string","name":"string","describe":"string","createTime":"2019-07-16T15:53:32.105Z","familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}]}],"familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}],"financeRecord":[{"id":0,"memberId":0,"receiptor":0,"quota":0,"balance":0,"currencyType":0,"transactionType":0,"status":0,"remarks":"string","createTime":"2019-07-16T15:53:32.105Z"}],"memberNews":[{"id":0,"memberId":0,"content":"string","images":"string","createTime":"2019-07-16T15:53:32.105Z","memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.105Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]}],"memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"memberShipFriend":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"memberShipMember":[{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}],"team":[{"id":0,"memberId":0,"name":"string","avatarUrl":"string","createTime":"2019-07-16T15:53:32.106Z","teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}],"teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}
         * subject : {"id":0,"images":"string","title":"string","accompanyPlay":[null]}
         * accompanyPlayOrder : [null]
         */

        private int id;
        private int subjectId;
        private int memberId;
        private String title;
        private String images;
        private String weekDay;
        private String serviceStartTime;
        private String serviceEndTime;
        private int price;
        private String remark;
        private int status;
        private String recommendTime;
        private String createTime;
        private MemberBean member;
        private SubjectBean subject;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
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

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public String getServiceStartTime() {
            return serviceStartTime;
        }

        public void setServiceStartTime(String serviceStartTime) {
            this.serviceStartTime = serviceStartTime;
        }

        public String getServiceEndTime() {
            return serviceEndTime;
        }

        public void setServiceEndTime(String serviceEndTime) {
            this.serviceEndTime = serviceEndTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRecommendTime() {
            return recommendTime;
        }

        public void setRecommendTime(String recommendTime) {
            this.recommendTime = recommendTime;
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

        public SubjectBean getSubject() {
            return subject;
        }

        public void setSubject(SubjectBean subject) {
            this.subject = subject;
        }


        public static class MemberBean {
            /**
             * id : 0
             * reference : 0
             * balance : 0
             * userName : string
             * password : string
             * weiXinUid : string
             * gender : 0
             * avatarUrl : string
             * nickname : string
             * age : 0
             * areaCode : 0
             * areaName : string
             * exterior : string
             * weiXin : string
             * job : string
             * verificationGenderStatus : 0
             * genderVideo : string
             * vipEffectiveDate : 2019-07-16T15:53:32.105Z
             * recommendTime : 2019-07-16T15:53:32.105Z
             * createTime : 2019-07-16T15:53:32.105Z
             * accompanyPlay : [null]
             * accompanyPlayOrder : [null]
             * addressBookFriend : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.105Z"}]
             * addressBookMember : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.105Z"}]
             * family : [{"id":0,"memberId":0,"avatarUrl":"string","banner":"string","name":"string","describe":"string","createTime":"2019-07-16T15:53:32.105Z","familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}]}]
             * familyMember : [{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}]
             * financeRecord : [{"id":0,"memberId":0,"receiptor":0,"quota":0,"balance":0,"currencyType":0,"transactionType":0,"status":0,"remarks":"string","createTime":"2019-07-16T15:53:32.105Z"}]
             * memberNews : [{"id":0,"memberId":0,"content":"string","images":"string","createTime":"2019-07-16T15:53:32.105Z","memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.105Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]}]
             * memberNewsComment : [{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}]
             * memberNewsNotice : [{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}]
             * memberNewsPraise : [{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
             * memberShipFriend : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
             * memberShipMember : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
             * team : [{"id":0,"memberId":0,"name":"string","avatarUrl":"string","createTime":"2019-07-16T15:53:32.106Z","teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}]
             * teamMember : [{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]
             */

            private int id;
            private int reference;
            private int balance;
            private String userName;
            private String password;
            private String weiXinUid;
            private int gender;
            private String avatarUrl;
            private String nickname;
            private int age;
            private int areaCode;
            private String areaName;
            private String exterior;
            private String weiXin;
            private String job;
            private int verificationGenderStatus;
            private String genderVideo;
            private String vipEffectiveDate;
            private String recommendTime;
            private String createTime;
            private List<AddressBookFriendBean> addressBookFriend;
            private List<AddressBookMemberBean> addressBookMember;
            private List<FamilyBean> family;
            private List<FamilyMemberBeanX> familyMember;
            private List<FinanceRecordBean> financeRecord;
            private List<MemberNewsBean> memberNews;
            private List<MemberNewsCommentBeanX> memberNewsComment;
            private List<MemberNewsNoticeBeanX> memberNewsNotice;
            private List<MemberNewsPraiseBeanX> memberNewsPraise;
            private List<MemberShipFriendBean> memberShipFriend;
            private List<MemberShipMemberBean> memberShipMember;
            private List<TeamBean> team;
            private List<TeamMemberBeanX> teamMember;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getReference() {
                return reference;
            }

            public void setReference(int reference) {
                this.reference = reference;
            }

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getWeiXinUid() {
                return weiXinUid;
            }

            public void setWeiXinUid(String weiXinUid) {
                this.weiXinUid = weiXinUid;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(int areaCode) {
                this.areaCode = areaCode;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getExterior() {
                return exterior;
            }

            public void setExterior(String exterior) {
                this.exterior = exterior;
            }

            public String getWeiXin() {
                return weiXin;
            }

            public void setWeiXin(String weiXin) {
                this.weiXin = weiXin;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public int getVerificationGenderStatus() {
                return verificationGenderStatus;
            }

            public void setVerificationGenderStatus(int verificationGenderStatus) {
                this.verificationGenderStatus = verificationGenderStatus;
            }

            public String getGenderVideo() {
                return genderVideo;
            }

            public void setGenderVideo(String genderVideo) {
                this.genderVideo = genderVideo;
            }

            public String getVipEffectiveDate() {
                return vipEffectiveDate;
            }

            public void setVipEffectiveDate(String vipEffectiveDate) {
                this.vipEffectiveDate = vipEffectiveDate;
            }

            public String getRecommendTime() {
                return recommendTime;
            }

            public void setRecommendTime(String recommendTime) {
                this.recommendTime = recommendTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public List<AddressBookFriendBean> getAddressBookFriend() {
                return addressBookFriend;
            }

            public void setAddressBookFriend(List<AddressBookFriendBean> addressBookFriend) {
                this.addressBookFriend = addressBookFriend;
            }

            public List<AddressBookMemberBean> getAddressBookMember() {
                return addressBookMember;
            }

            public void setAddressBookMember(List<AddressBookMemberBean> addressBookMember) {
                this.addressBookMember = addressBookMember;
            }

            public List<FamilyBean> getFamily() {
                return family;
            }

            public void setFamily(List<FamilyBean> family) {
                this.family = family;
            }

            public List<FamilyMemberBeanX> getFamilyMember() {
                return familyMember;
            }

            public void setFamilyMember(List<FamilyMemberBeanX> familyMember) {
                this.familyMember = familyMember;
            }

            public List<FinanceRecordBean> getFinanceRecord() {
                return financeRecord;
            }

            public void setFinanceRecord(List<FinanceRecordBean> financeRecord) {
                this.financeRecord = financeRecord;
            }

            public List<MemberNewsBean> getMemberNews() {
                return memberNews;
            }

            public void setMemberNews(List<MemberNewsBean> memberNews) {
                this.memberNews = memberNews;
            }

            public List<MemberNewsCommentBeanX> getMemberNewsComment() {
                return memberNewsComment;
            }

            public void setMemberNewsComment(List<MemberNewsCommentBeanX> memberNewsComment) {
                this.memberNewsComment = memberNewsComment;
            }

            public List<MemberNewsNoticeBeanX> getMemberNewsNotice() {
                return memberNewsNotice;
            }

            public void setMemberNewsNotice(List<MemberNewsNoticeBeanX> memberNewsNotice) {
                this.memberNewsNotice = memberNewsNotice;
            }

            public List<MemberNewsPraiseBeanX> getMemberNewsPraise() {
                return memberNewsPraise;
            }

            public void setMemberNewsPraise(List<MemberNewsPraiseBeanX> memberNewsPraise) {
                this.memberNewsPraise = memberNewsPraise;
            }

            public List<MemberShipFriendBean> getMemberShipFriend() {
                return memberShipFriend;
            }

            public void setMemberShipFriend(List<MemberShipFriendBean> memberShipFriend) {
                this.memberShipFriend = memberShipFriend;
            }

            public List<MemberShipMemberBean> getMemberShipMember() {
                return memberShipMember;
            }

            public void setMemberShipMember(List<MemberShipMemberBean> memberShipMember) {
                this.memberShipMember = memberShipMember;
            }

            public List<TeamBean> getTeam() {
                return team;
            }

            public void setTeam(List<TeamBean> team) {
                this.team = team;
            }

            public List<TeamMemberBeanX> getTeamMember() {
                return teamMember;
            }

            public void setTeamMember(List<TeamMemberBeanX> teamMember) {
                this.teamMember = teamMember;
            }

            public static class AddressBookFriendBean {
                /**
                 * memberId : 0
                 * friendId : 0
                 * createTime : 2019-07-16T15:53:32.105Z
                 */

                private int memberId;
                private int friendId;
                private String createTime;

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getFriendId() {
                    return friendId;
                }

                public void setFriendId(int friendId) {
                    this.friendId = friendId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class AddressBookMemberBean {
                /**
                 * memberId : 0
                 * friendId : 0
                 * createTime : 2019-07-16T15:53:32.105Z
                 */

                private int memberId;
                private int friendId;
                private String createTime;

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getFriendId() {
                    return friendId;
                }

                public void setFriendId(int friendId) {
                    this.friendId = friendId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class FamilyBean {
                /**
                 * id : 0
                 * memberId : 0
                 * avatarUrl : string
                 * banner : string
                 * name : string
                 * describe : string
                 * createTime : 2019-07-16T15:53:32.105Z
                 * familyMember : [{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.105Z"}]
                 */

                private int id;
                private int memberId;
                private String avatarUrl;
                private String banner;
                private String name;
                private String describe;
                private String createTime;
                private List<FamilyMemberBean> familyMember;

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

                public String getAvatarUrl() {
                    return avatarUrl;
                }

                public void setAvatarUrl(String avatarUrl) {
                    this.avatarUrl = avatarUrl;
                }

                public String getBanner() {
                    return banner;
                }

                public void setBanner(String banner) {
                    this.banner = banner;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
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

                public List<FamilyMemberBean> getFamilyMember() {
                    return familyMember;
                }

                public void setFamilyMember(List<FamilyMemberBean> familyMember) {
                    this.familyMember = familyMember;
                }

                public static class FamilyMemberBean {
                    /**
                     * familyId : 0
                     * memberId : 0
                     * role : 0
                     * name : string
                     * createTime : 2019-07-16T15:53:32.105Z
                     */

                    private int familyId;
                    private int memberId;
                    private int role;
                    private String name;
                    private String createTime;

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

                    public int getRole() {
                        return role;
                    }

                    public void setRole(int role) {
                        this.role = role;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }
                }
            }

            public static class FamilyMemberBeanX {
                /**
                 * familyId : 0
                 * memberId : 0
                 * role : 0
                 * name : string
                 * createTime : 2019-07-16T15:53:32.105Z
                 */

                private int familyId;
                private int memberId;
                private int role;
                private String name;
                private String createTime;

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

                public int getRole() {
                    return role;
                }

                public void setRole(int role) {
                    this.role = role;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class FinanceRecordBean {
                /**
                 * id : 0
                 * memberId : 0
                 * receiptor : 0
                 * quota : 0
                 * balance : 0
                 * currencyType : 0
                 * transactionType : 0
                 * status : 0
                 * remarks : string
                 * createTime : 2019-07-16T15:53:32.105Z
                 */

                private int id;
                private int memberId;
                private int receiptor;
                private int quota;
                private int balance;
                private int currencyType;
                private int transactionType;
                private int status;
                private String remarks;
                private String createTime;

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

                public int getReceiptor() {
                    return receiptor;
                }

                public void setReceiptor(int receiptor) {
                    this.receiptor = receiptor;
                }

                public int getQuota() {
                    return quota;
                }

                public void setQuota(int quota) {
                    this.quota = quota;
                }

                public int getBalance() {
                    return balance;
                }

                public void setBalance(int balance) {
                    this.balance = balance;
                }

                public int getCurrencyType() {
                    return currencyType;
                }

                public void setCurrencyType(int currencyType) {
                    this.currencyType = currencyType;
                }

                public int getTransactionType() {
                    return transactionType;
                }

                public void setTransactionType(int transactionType) {
                    this.transactionType = transactionType;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class MemberNewsBean {
                /**
                 * id : 0
                 * memberId : 0
                 * content : string
                 * images : string
                 * createTime : 2019-07-16T15:53:32.105Z
                 * memberNewsComment : [{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.105Z"}]
                 * memberNewsNotice : [{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}]
                 * memberNewsPraise : [{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
                 */

                private int id;
                private int memberId;
                private String content;
                private String images;
                private String createTime;
                private List<MemberNewsCommentBean> memberNewsComment;
                private List<MemberNewsNoticeBean> memberNewsNotice;
                private List<MemberNewsPraiseBean> memberNewsPraise;

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

                public List<MemberNewsCommentBean> getMemberNewsComment() {
                    return memberNewsComment;
                }

                public void setMemberNewsComment(List<MemberNewsCommentBean> memberNewsComment) {
                    this.memberNewsComment = memberNewsComment;
                }

                public List<MemberNewsNoticeBean> getMemberNewsNotice() {
                    return memberNewsNotice;
                }

                public void setMemberNewsNotice(List<MemberNewsNoticeBean> memberNewsNotice) {
                    this.memberNewsNotice = memberNewsNotice;
                }

                public List<MemberNewsPraiseBean> getMemberNewsPraise() {
                    return memberNewsPraise;
                }

                public void setMemberNewsPraise(List<MemberNewsPraiseBean> memberNewsPraise) {
                    this.memberNewsPraise = memberNewsPraise;
                }

                public static class MemberNewsCommentBean {
                    /**
                     * id : 0
                     * memberId : 0
                     * memberNewsId : 0
                     * content : string
                     * createTime : 2019-07-16T15:53:32.105Z
                     */

                    private int id;
                    private int memberId;
                    private int memberNewsId;
                    private String content;
                    private String createTime;

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

                    public int getMemberNewsId() {
                        return memberNewsId;
                    }

                    public void setMemberNewsId(int memberNewsId) {
                        this.memberNewsId = memberNewsId;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }
                }

                public static class MemberNewsNoticeBean {
                    /**
                     * id : string
                     * memberId : 0
                     * memberNewsId : 0
                     * type : 0
                     * comtent : string
                     * status : true
                     * createTime : 2019-07-16T15:53:32.106Z
                     */

                    private String id;
                    private int memberId;
                    private int memberNewsId;
                    private int type;
                    private String comtent;
                    private boolean status;
                    private String createTime;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public int getMemberId() {
                        return memberId;
                    }

                    public void setMemberId(int memberId) {
                        this.memberId = memberId;
                    }

                    public int getMemberNewsId() {
                        return memberNewsId;
                    }

                    public void setMemberNewsId(int memberNewsId) {
                        this.memberNewsId = memberNewsId;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public String getComtent() {
                        return comtent;
                    }

                    public void setComtent(String comtent) {
                        this.comtent = comtent;
                    }

                    public boolean isStatus() {
                        return status;
                    }

                    public void setStatus(boolean status) {
                        this.status = status;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }
                }

                public static class MemberNewsPraiseBean {
                    /**
                     * memberNewsId : 0
                     * memberId : 0
                     * createTime : 2019-07-16T15:53:32.106Z
                     */

                    private int memberNewsId;
                    private int memberId;
                    private String createTime;

                    public int getMemberNewsId() {
                        return memberNewsId;
                    }

                    public void setMemberNewsId(int memberNewsId) {
                        this.memberNewsId = memberNewsId;
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
                }
            }

            public static class MemberNewsCommentBeanX {
                /**
                 * id : 0
                 * memberId : 0
                 * memberNewsId : 0
                 * content : string
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int id;
                private int memberId;
                private int memberNewsId;
                private String content;
                private String createTime;

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

                public int getMemberNewsId() {
                    return memberNewsId;
                }

                public void setMemberNewsId(int memberNewsId) {
                    this.memberNewsId = memberNewsId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class MemberNewsNoticeBeanX {
                /**
                 * id : string
                 * memberId : 0
                 * memberNewsId : 0
                 * type : 0
                 * comtent : string
                 * status : true
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private String id;
                private int memberId;
                private int memberNewsId;
                private int type;
                private String comtent;
                private boolean status;
                private String createTime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getMemberNewsId() {
                    return memberNewsId;
                }

                public void setMemberNewsId(int memberNewsId) {
                    this.memberNewsId = memberNewsId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getComtent() {
                    return comtent;
                }

                public void setComtent(String comtent) {
                    this.comtent = comtent;
                }

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class MemberNewsPraiseBeanX {
                /**
                 * memberNewsId : 0
                 * memberId : 0
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int memberNewsId;
                private int memberId;
                private String createTime;

                public int getMemberNewsId() {
                    return memberNewsId;
                }

                public void setMemberNewsId(int memberNewsId) {
                    this.memberNewsId = memberNewsId;
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
            }

            public static class MemberShipFriendBean {
                /**
                 * memberId : 0
                 * friendId : 0
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int memberId;
                private int friendId;
                private String createTime;

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getFriendId() {
                    return friendId;
                }

                public void setFriendId(int friendId) {
                    this.friendId = friendId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class MemberShipMemberBean {
                /**
                 * memberId : 0
                 * friendId : 0
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int memberId;
                private int friendId;
                private String createTime;

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getFriendId() {
                    return friendId;
                }

                public void setFriendId(int friendId) {
                    this.friendId = friendId;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class TeamBean {
                /**
                 * id : 0
                 * memberId : 0
                 * name : string
                 * avatarUrl : string
                 * createTime : 2019-07-16T15:53:32.106Z
                 * teamMember : [{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]
                 */

                private int id;
                private int memberId;
                private String name;
                private String avatarUrl;
                private String createTime;
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

                public List<TeamMemberBean> getTeamMember() {
                    return teamMember;
                }

                public void setTeamMember(List<TeamMemberBean> teamMember) {
                    this.teamMember = teamMember;
                }

                public static class TeamMemberBean {
                    /**
                     * teamId : 0
                     * memberId : 0
                     * role : 0
                     * notDisturb : true
                     * createTime : 2019-07-16T15:53:32.106Z
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

            public static class TeamMemberBeanX {
                /**
                 * teamId : 0
                 * memberId : 0
                 * role : 0
                 * notDisturb : true
                 * createTime : 2019-07-16T15:53:32.106Z
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

        public static class SubjectBean {
            /**
             * id : 0
             * images : string
             * title : string
             * accompanyPlay : [null]
             */

            private int id;
            private String images;
            private String title;

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
        }
    }

    public static class MemberBeanX {
        /**
         * id : 0
         * reference : 0
         * balance : 0
         * userName : string
         * password : string
         * weiXinUid : string
         * gender : 0
         * avatarUrl : string
         * nickname : string
         * age : 0
         * areaCode : 0
         * areaName : string
         * exterior : string
         * weiXin : string
         * job : string
         * verificationGenderStatus : 0
         * genderVideo : string
         * vipEffectiveDate : 2019-07-16T15:53:32.106Z
         * recommendTime : 2019-07-16T15:53:32.106Z
         * createTime : 2019-07-16T15:53:32.106Z
         * accompanyPlay : [null]
         * accompanyPlayOrder : [null]
         * addressBookFriend : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
         * addressBookMember : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
         * family : [{"id":0,"memberId":0,"avatarUrl":"string","banner":"string","name":"string","describe":"string","createTime":"2019-07-16T15:53:32.106Z","familyMember":[{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.106Z"}]}]
         * familyMember : [{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.106Z"}]
         * financeRecord : [{"id":0,"memberId":0,"receiptor":0,"quota":0,"balance":0,"currencyType":0,"transactionType":0,"status":0,"remarks":"string","createTime":"2019-07-16T15:53:32.106Z"}]
         * memberNews : [{"id":0,"memberId":0,"content":"string","images":"string","createTime":"2019-07-16T15:53:32.106Z","memberNewsComment":[{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsNotice":[{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}],"memberNewsPraise":[{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]}]
         * memberNewsComment : [{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}]
         * memberNewsNotice : [{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}]
         * memberNewsPraise : [{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
         * memberShipFriend : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
         * memberShipMember : [{"memberId":0,"friendId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
         * team : [{"id":0,"memberId":0,"name":"string","avatarUrl":"string","createTime":"2019-07-16T15:53:32.106Z","teamMember":[{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]}]
         * teamMember : [{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]
         */

        private int id;
        private int reference;
        private int balance;
        private String userName;
        private String password;
        private String weiXinUid;
        private int gender;
        private String avatarUrl;
        private String nickname;
        private int age;
        private int areaCode;
        private String areaName;
        private String exterior;
        private String weiXin;
        private String job;
        private int verificationGenderStatus;
        private String genderVideo;
        private String vipEffectiveDate;
        private String recommendTime;
        private String createTime;
        private List<AddressBookFriendBeanX> addressBookFriend;
        private List<AddressBookMemberBeanX> addressBookMember;
        private List<FamilyBeanX> family;
        private List<FamilyMemberBeanXXX> familyMember;
        private List<FinanceRecordBeanX> financeRecord;
        private List<MemberNewsBeanX> memberNews;
        private List<MemberNewsCommentBeanXXX> memberNewsComment;
        private List<MemberNewsNoticeBeanXXX> memberNewsNotice;
        private List<MemberNewsPraiseBeanXXX> memberNewsPraise;
        private List<MemberShipFriendBeanX> memberShipFriend;
        private List<MemberShipMemberBeanX> memberShipMember;
        private List<TeamBeanX> team;
        private List<TeamMemberBeanXXX> teamMember;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getReference() {
            return reference;
        }

        public void setReference(int reference) {
            this.reference = reference;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getWeiXinUid() {
            return weiXinUid;
        }

        public void setWeiXinUid(String weiXinUid) {
            this.weiXinUid = weiXinUid;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(int areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getExterior() {
            return exterior;
        }

        public void setExterior(String exterior) {
            this.exterior = exterior;
        }

        public String getWeiXin() {
            return weiXin;
        }

        public void setWeiXin(String weiXin) {
            this.weiXin = weiXin;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getVerificationGenderStatus() {
            return verificationGenderStatus;
        }

        public void setVerificationGenderStatus(int verificationGenderStatus) {
            this.verificationGenderStatus = verificationGenderStatus;
        }

        public String getGenderVideo() {
            return genderVideo;
        }

        public void setGenderVideo(String genderVideo) {
            this.genderVideo = genderVideo;
        }

        public String getVipEffectiveDate() {
            return vipEffectiveDate;
        }

        public void setVipEffectiveDate(String vipEffectiveDate) {
            this.vipEffectiveDate = vipEffectiveDate;
        }

        public String getRecommendTime() {
            return recommendTime;
        }

        public void setRecommendTime(String recommendTime) {
            this.recommendTime = recommendTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }


        public List<AddressBookFriendBeanX> getAddressBookFriend() {
            return addressBookFriend;
        }

        public void setAddressBookFriend(List<AddressBookFriendBeanX> addressBookFriend) {
            this.addressBookFriend = addressBookFriend;
        }

        public List<AddressBookMemberBeanX> getAddressBookMember() {
            return addressBookMember;
        }

        public void setAddressBookMember(List<AddressBookMemberBeanX> addressBookMember) {
            this.addressBookMember = addressBookMember;
        }

        public List<FamilyBeanX> getFamily() {
            return family;
        }

        public void setFamily(List<FamilyBeanX> family) {
            this.family = family;
        }

        public List<FamilyMemberBeanXXX> getFamilyMember() {
            return familyMember;
        }

        public void setFamilyMember(List<FamilyMemberBeanXXX> familyMember) {
            this.familyMember = familyMember;
        }

        public List<FinanceRecordBeanX> getFinanceRecord() {
            return financeRecord;
        }

        public void setFinanceRecord(List<FinanceRecordBeanX> financeRecord) {
            this.financeRecord = financeRecord;
        }

        public List<MemberNewsBeanX> getMemberNews() {
            return memberNews;
        }

        public void setMemberNews(List<MemberNewsBeanX> memberNews) {
            this.memberNews = memberNews;
        }

        public List<MemberNewsCommentBeanXXX> getMemberNewsComment() {
            return memberNewsComment;
        }

        public void setMemberNewsComment(List<MemberNewsCommentBeanXXX> memberNewsComment) {
            this.memberNewsComment = memberNewsComment;
        }

        public List<MemberNewsNoticeBeanXXX> getMemberNewsNotice() {
            return memberNewsNotice;
        }

        public void setMemberNewsNotice(List<MemberNewsNoticeBeanXXX> memberNewsNotice) {
            this.memberNewsNotice = memberNewsNotice;
        }

        public List<MemberNewsPraiseBeanXXX> getMemberNewsPraise() {
            return memberNewsPraise;
        }

        public void setMemberNewsPraise(List<MemberNewsPraiseBeanXXX> memberNewsPraise) {
            this.memberNewsPraise = memberNewsPraise;
        }

        public List<MemberShipFriendBeanX> getMemberShipFriend() {
            return memberShipFriend;
        }

        public void setMemberShipFriend(List<MemberShipFriendBeanX> memberShipFriend) {
            this.memberShipFriend = memberShipFriend;
        }

        public List<MemberShipMemberBeanX> getMemberShipMember() {
            return memberShipMember;
        }

        public void setMemberShipMember(List<MemberShipMemberBeanX> memberShipMember) {
            this.memberShipMember = memberShipMember;
        }

        public List<TeamBeanX> getTeam() {
            return team;
        }

        public void setTeam(List<TeamBeanX> team) {
            this.team = team;
        }

        public List<TeamMemberBeanXXX> getTeamMember() {
            return teamMember;
        }

        public void setTeamMember(List<TeamMemberBeanXXX> teamMember) {
            this.teamMember = teamMember;
        }

        public static class AddressBookFriendBeanX {
            /**
             * memberId : 0
             * friendId : 0
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int memberId;
            private int friendId;
            private String createTime;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getFriendId() {
                return friendId;
            }

            public void setFriendId(int friendId) {
                this.friendId = friendId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class AddressBookMemberBeanX {
            /**
             * memberId : 0
             * friendId : 0
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int memberId;
            private int friendId;
            private String createTime;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getFriendId() {
                return friendId;
            }

            public void setFriendId(int friendId) {
                this.friendId = friendId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class FamilyBeanX {
            /**
             * id : 0
             * memberId : 0
             * avatarUrl : string
             * banner : string
             * name : string
             * describe : string
             * createTime : 2019-07-16T15:53:32.106Z
             * familyMember : [{"familyId":0,"memberId":0,"role":0,"name":"string","createTime":"2019-07-16T15:53:32.106Z"}]
             */

            private int id;
            private int memberId;
            private String avatarUrl;
            private String banner;
            private String name;
            private String describe;
            private String createTime;
            private List<FamilyMemberBeanXX> familyMember;

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

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public List<FamilyMemberBeanXX> getFamilyMember() {
                return familyMember;
            }

            public void setFamilyMember(List<FamilyMemberBeanXX> familyMember) {
                this.familyMember = familyMember;
            }

            public static class FamilyMemberBeanXX {
                /**
                 * familyId : 0
                 * memberId : 0
                 * role : 0
                 * name : string
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int familyId;
                private int memberId;
                private int role;
                private String name;
                private String createTime;

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

                public int getRole() {
                    return role;
                }

                public void setRole(int role) {
                    this.role = role;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }
        }

        public static class FamilyMemberBeanXXX {
            /**
             * familyId : 0
             * memberId : 0
             * role : 0
             * name : string
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int familyId;
            private int memberId;
            private int role;
            private String name;
            private String createTime;

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

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class FinanceRecordBeanX {
            /**
             * id : 0
             * memberId : 0
             * receiptor : 0
             * quota : 0
             * balance : 0
             * currencyType : 0
             * transactionType : 0
             * status : 0
             * remarks : string
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int id;
            private int memberId;
            private int receiptor;
            private int quota;
            private int balance;
            private int currencyType;
            private int transactionType;
            private int status;
            private String remarks;
            private String createTime;

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

            public int getReceiptor() {
                return receiptor;
            }

            public void setReceiptor(int receiptor) {
                this.receiptor = receiptor;
            }

            public int getQuota() {
                return quota;
            }

            public void setQuota(int quota) {
                this.quota = quota;
            }

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public int getCurrencyType() {
                return currencyType;
            }

            public void setCurrencyType(int currencyType) {
                this.currencyType = currencyType;
            }

            public int getTransactionType() {
                return transactionType;
            }

            public void setTransactionType(int transactionType) {
                this.transactionType = transactionType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class MemberNewsBeanX {
            /**
             * id : 0
             * memberId : 0
             * content : string
             * images : string
             * createTime : 2019-07-16T15:53:32.106Z
             * memberNewsComment : [{"id":0,"memberId":0,"memberNewsId":0,"content":"string","createTime":"2019-07-16T15:53:32.106Z"}]
             * memberNewsNotice : [{"id":"string","memberId":0,"memberNewsId":0,"type":0,"comtent":"string","status":true,"createTime":"2019-07-16T15:53:32.106Z"}]
             * memberNewsPraise : [{"memberNewsId":0,"memberId":0,"createTime":"2019-07-16T15:53:32.106Z"}]
             */

            private int id;
            private int memberId;
            private String content;
            private String images;
            private String createTime;
            private List<MemberNewsCommentBeanXX> memberNewsComment;
            private List<MemberNewsNoticeBeanXX> memberNewsNotice;
            private List<MemberNewsPraiseBeanXX> memberNewsPraise;

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

            public List<MemberNewsCommentBeanXX> getMemberNewsComment() {
                return memberNewsComment;
            }

            public void setMemberNewsComment(List<MemberNewsCommentBeanXX> memberNewsComment) {
                this.memberNewsComment = memberNewsComment;
            }

            public List<MemberNewsNoticeBeanXX> getMemberNewsNotice() {
                return memberNewsNotice;
            }

            public void setMemberNewsNotice(List<MemberNewsNoticeBeanXX> memberNewsNotice) {
                this.memberNewsNotice = memberNewsNotice;
            }

            public List<MemberNewsPraiseBeanXX> getMemberNewsPraise() {
                return memberNewsPraise;
            }

            public void setMemberNewsPraise(List<MemberNewsPraiseBeanXX> memberNewsPraise) {
                this.memberNewsPraise = memberNewsPraise;
            }

            public static class MemberNewsCommentBeanXX {
                /**
                 * id : 0
                 * memberId : 0
                 * memberNewsId : 0
                 * content : string
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int id;
                private int memberId;
                private int memberNewsId;
                private String content;
                private String createTime;

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

                public int getMemberNewsId() {
                    return memberNewsId;
                }

                public void setMemberNewsId(int memberNewsId) {
                    this.memberNewsId = memberNewsId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class MemberNewsNoticeBeanXX {
                /**
                 * id : string
                 * memberId : 0
                 * memberNewsId : 0
                 * type : 0
                 * comtent : string
                 * status : true
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private String id;
                private int memberId;
                private int memberNewsId;
                private int type;
                private String comtent;
                private boolean status;
                private String createTime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public int getMemberNewsId() {
                    return memberNewsId;
                }

                public void setMemberNewsId(int memberNewsId) {
                    this.memberNewsId = memberNewsId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getComtent() {
                    return comtent;
                }

                public void setComtent(String comtent) {
                    this.comtent = comtent;
                }

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }

            public static class MemberNewsPraiseBeanXX {
                /**
                 * memberNewsId : 0
                 * memberId : 0
                 * createTime : 2019-07-16T15:53:32.106Z
                 */

                private int memberNewsId;
                private int memberId;
                private String createTime;

                public int getMemberNewsId() {
                    return memberNewsId;
                }

                public void setMemberNewsId(int memberNewsId) {
                    this.memberNewsId = memberNewsId;
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
            }
        }

        public static class MemberNewsCommentBeanXXX {
            /**
             * id : 0
             * memberId : 0
             * memberNewsId : 0
             * content : string
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int id;
            private int memberId;
            private int memberNewsId;
            private String content;
            private String createTime;

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

            public int getMemberNewsId() {
                return memberNewsId;
            }

            public void setMemberNewsId(int memberNewsId) {
                this.memberNewsId = memberNewsId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class MemberNewsNoticeBeanXXX {
            /**
             * id : string
             * memberId : 0
             * memberNewsId : 0
             * type : 0
             * comtent : string
             * status : true
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private String id;
            private int memberId;
            private int memberNewsId;
            private int type;
            private String comtent;
            private boolean status;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getMemberNewsId() {
                return memberNewsId;
            }

            public void setMemberNewsId(int memberNewsId) {
                this.memberNewsId = memberNewsId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getComtent() {
                return comtent;
            }

            public void setComtent(String comtent) {
                this.comtent = comtent;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class MemberNewsPraiseBeanXXX {
            /**
             * memberNewsId : 0
             * memberId : 0
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int memberNewsId;
            private int memberId;
            private String createTime;

            public int getMemberNewsId() {
                return memberNewsId;
            }

            public void setMemberNewsId(int memberNewsId) {
                this.memberNewsId = memberNewsId;
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
        }

        public static class MemberShipFriendBeanX {
            /**
             * memberId : 0
             * friendId : 0
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int memberId;
            private int friendId;
            private String createTime;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getFriendId() {
                return friendId;
            }

            public void setFriendId(int friendId) {
                this.friendId = friendId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class MemberShipMemberBeanX {
            /**
             * memberId : 0
             * friendId : 0
             * createTime : 2019-07-16T15:53:32.106Z
             */

            private int memberId;
            private int friendId;
            private String createTime;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getFriendId() {
                return friendId;
            }

            public void setFriendId(int friendId) {
                this.friendId = friendId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }

        public static class TeamBeanX {
            /**
             * id : 0
             * memberId : 0
             * name : string
             * avatarUrl : string
             * createTime : 2019-07-16T15:53:32.106Z
             * teamMember : [{"teamId":0,"memberId":0,"role":0,"notDisturb":true,"createTime":"2019-07-16T15:53:32.106Z"}]
             */

            private int id;
            private int memberId;
            private String name;
            private String avatarUrl;
            private String createTime;
            private List<TeamMemberBeanXX> teamMember;

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

            public List<TeamMemberBeanXX> getTeamMember() {
                return teamMember;
            }

            public void setTeamMember(List<TeamMemberBeanXX> teamMember) {
                this.teamMember = teamMember;
            }

            public static class TeamMemberBeanXX {
                /**
                 * teamId : 0
                 * memberId : 0
                 * role : 0
                 * notDisturb : true
                 * createTime : 2019-07-16T15:53:32.106Z
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

        public static class TeamMemberBeanXXX {
            /**
             * teamId : 0
             * memberId : 0
             * role : 0
             * notDisturb : true
             * createTime : 2019-07-16T15:53:32.106Z
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

    public static class UniformOrderBean {
        /**
         * id : 0
         * paymentChannel : 0
         * transactionType : 0
         * createTime : 2019-07-16T15:53:32.106Z
         */

        private int id;
        private int paymentChannel;
        private int transactionType;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPaymentChannel() {
            return paymentChannel;
        }

        public void setPaymentChannel(int paymentChannel) {
            this.paymentChannel = paymentChannel;
        }

        public int getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(int transactionType) {
            this.transactionType = transactionType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
