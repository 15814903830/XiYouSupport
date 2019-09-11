package com.chengfan.xiyou.ui.login;

import java.io.Serializable;
import java.util.List;

public class LogingBase implements Serializable {


    /**
     * data : {"vip":false,"realNameTag":"已认证","verificationGenderStatusTag":"已认证","referenceMember":null,"referenceUserName":null,"id":1083,"reference":null,"balance":0.02,"userName":"15814903830","password":"6B56B2410397DBA5","weiXinUid":"o1Tco1lET2mxUo8F2_Y6oXeeD7qA","avatarUrl":"UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb","nickname":"胡椒","age":555,"areaCode":440100,"areaName":"广东省 广州市","exterior":"168CM 51KG 贤良淑德","weiXin":"","job":"学生","gender":0,"verificationGenderStatus":1,"verificationGenderRemarks":null,"genderVideo":"UploadFiles/Member/20190821/68a8e3cc-8a4f-4bf8-b04d-11d48c8e413c.mp4","idImages":"UploadFiles/AccompanyPlayNews/20190821/cef6068d-9fa7-494a-935a-e0b1803b6068|UploadFiles/AccompanyPlayNews/20190821/49167460-bdaf-419c-90de-27384a8207ab","fullName":"相声","idNo":"身份证","realName":1,"realNameRemarks":null,"introduction":"5,4,3,1","applyLable":"5,4,3,1","approvalLable":null,"lableAudio":"UploadFiles/AccompanyPlayNews/20190904/9c2e732a-2f4b-45d9-b1f2-4ad6987ee200|","lableVideo":"UploadFiles/AccompanyPlayNews/20190904/22d37359-7914-4263-9f16-64295bd9642a","lableStatus":0,"lableRemarks":null,"vipEffectiveDate":null,"recommendTime":"2019-09-05T00:00:00","createTime":"2019-08-21T16:31:11.963","accompanyPlay":[],"accompanyPlayOrder":[],"addressBookFriend":[],"addressBookMember":[],"family":[],"familyMember":[],"financeRecord":[],"memberNews":[],"memberNewsComment":[],"memberNewsNotice":[],"memberNewsPraise":[],"memberShipFriend":[],"memberShipMember":[],"team":[],"teamMember":[],"vipOrder":[],"withdrawCash":[]}
     * suc : true
     * msg : 授权成功
     */

    private DataBean data;
    private boolean suc;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * vip : false
         * realNameTag : 已认证
         * verificationGenderStatusTag : 已认证
         * referenceMember : null
         * referenceUserName : null
         * id : 1083
         * reference : null
         * balance : 0.02
         * userName : 15814903830
         * password : 6B56B2410397DBA5
         * weiXinUid : o1Tco1lET2mxUo8F2_Y6oXeeD7qA
         * avatarUrl : UploadFiles/AccompanyPlayNews/20190823/f15c9ea3-bd7d-4301-806f-53cb83b0b0eb
         * nickname : 胡椒
         * age : 555
         * areaCode : 440100
         * areaName : 广东省 广州市
         * exterior : 168CM 51KG 贤良淑德
         * weiXin :
         * job : 学生
         * gender : 0
         * verificationGenderStatus : 1
         * verificationGenderRemarks : null
         * genderVideo : UploadFiles/Member/20190821/68a8e3cc-8a4f-4bf8-b04d-11d48c8e413c.mp4
         * idImages : UploadFiles/AccompanyPlayNews/20190821/cef6068d-9fa7-494a-935a-e0b1803b6068|UploadFiles/AccompanyPlayNews/20190821/49167460-bdaf-419c-90de-27384a8207ab
         * fullName : 相声
         * idNo : 身份证
         * realName : 1
         * realNameRemarks : null
         * introduction : 5,4,3,1
         * applyLable : 5,4,3,1
         * approvalLable : null
         * lableAudio : UploadFiles/AccompanyPlayNews/20190904/9c2e732a-2f4b-45d9-b1f2-4ad6987ee200|
         * lableVideo : UploadFiles/AccompanyPlayNews/20190904/22d37359-7914-4263-9f16-64295bd9642a
         * lableStatus : 0
         * lableRemarks : null
         * vipEffectiveDate : null
         * recommendTime : 2019-09-05T00:00:00
         * createTime : 2019-08-21T16:31:11.963
         * accompanyPlay : []
         * accompanyPlayOrder : []
         * addressBookFriend : []
         * addressBookMember : []
         * family : []
         * familyMember : []
         * financeRecord : []
         * memberNews : []
         * memberNewsComment : []
         * memberNewsNotice : []
         * memberNewsPraise : []
         * memberShipFriend : []
         * memberShipMember : []
         * team : []
         * teamMember : []
         * vipOrder : []
         * withdrawCash : []
         */

        private boolean vip;
        private String realNameTag;
        private String verificationGenderStatusTag;
        private Object referenceMember;
        private Object referenceUserName;
        private int id;
        private Object reference;
        private double balance;
        private String userName;
        private String password;
        private String weiXinUid;
        private String avatarUrl;
        private String nickname;
        private int age;
        private int areaCode;
        private String areaName;
        private String exterior;
        private String weiXin;
        private String job;
        private int gender;
        private int verificationGenderStatus;
        private Object verificationGenderRemarks;
        private String genderVideo;
        private String idImages;
        private String fullName;
        private String idNo;
        private int realName;
        private Object realNameRemarks;
        private String introduction;
        private String applyLable;
        private Object approvalLable;
        private String lableAudio;
        private String lableVideo;
        private int lableStatus;
        private Object lableRemarks;
        private Object vipEffectiveDate;
        private String recommendTime;
        private String createTime;
        private List<?> accompanyPlay;
        private List<?> accompanyPlayOrder;
        private List<?> addressBookFriend;
        private List<?> addressBookMember;
        private List<?> family;
        private List<?> familyMember;
        private List<?> financeRecord;
        private List<?> memberNews;
        private List<?> memberNewsComment;
        private List<?> memberNewsNotice;
        private List<?> memberNewsPraise;
        private List<?> memberShipFriend;
        private List<?> memberShipMember;
        private List<?> team;
        private List<?> teamMember;
        private List<?> vipOrder;
        private List<?> withdrawCash;

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }

        public String getRealNameTag() {
            return realNameTag;
        }

        public void setRealNameTag(String realNameTag) {
            this.realNameTag = realNameTag;
        }

        public String getVerificationGenderStatusTag() {
            return verificationGenderStatusTag;
        }

        public void setVerificationGenderStatusTag(String verificationGenderStatusTag) {
            this.verificationGenderStatusTag = verificationGenderStatusTag;
        }

        public Object getReferenceMember() {
            return referenceMember;
        }

        public void setReferenceMember(Object referenceMember) {
            this.referenceMember = referenceMember;
        }

        public Object getReferenceUserName() {
            return referenceUserName;
        }

        public void setReferenceUserName(Object referenceUserName) {
            this.referenceUserName = referenceUserName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getReference() {
            return reference;
        }

        public void setReference(Object reference) {
            this.reference = reference;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getVerificationGenderStatus() {
            return verificationGenderStatus;
        }

        public void setVerificationGenderStatus(int verificationGenderStatus) {
            this.verificationGenderStatus = verificationGenderStatus;
        }

        public Object getVerificationGenderRemarks() {
            return verificationGenderRemarks;
        }

        public void setVerificationGenderRemarks(Object verificationGenderRemarks) {
            this.verificationGenderRemarks = verificationGenderRemarks;
        }

        public String getGenderVideo() {
            return genderVideo;
        }

        public void setGenderVideo(String genderVideo) {
            this.genderVideo = genderVideo;
        }

        public String getIdImages() {
            return idImages;
        }

        public void setIdImages(String idImages) {
            this.idImages = idImages;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public int getRealName() {
            return realName;
        }

        public void setRealName(int realName) {
            this.realName = realName;
        }

        public Object getRealNameRemarks() {
            return realNameRemarks;
        }

        public void setRealNameRemarks(Object realNameRemarks) {
            this.realNameRemarks = realNameRemarks;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getApplyLable() {
            return applyLable;
        }

        public void setApplyLable(String applyLable) {
            this.applyLable = applyLable;
        }

        public Object getApprovalLable() {
            return approvalLable;
        }

        public void setApprovalLable(Object approvalLable) {
            this.approvalLable = approvalLable;
        }

        public String getLableAudio() {
            return lableAudio;
        }

        public void setLableAudio(String lableAudio) {
            this.lableAudio = lableAudio;
        }

        public String getLableVideo() {
            return lableVideo;
        }

        public void setLableVideo(String lableVideo) {
            this.lableVideo = lableVideo;
        }

        public int getLableStatus() {
            return lableStatus;
        }

        public void setLableStatus(int lableStatus) {
            this.lableStatus = lableStatus;
        }

        public Object getLableRemarks() {
            return lableRemarks;
        }

        public void setLableRemarks(Object lableRemarks) {
            this.lableRemarks = lableRemarks;
        }

        public Object getVipEffectiveDate() {
            return vipEffectiveDate;
        }

        public void setVipEffectiveDate(Object vipEffectiveDate) {
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

        public List<?> getAccompanyPlay() {
            return accompanyPlay;
        }

        public void setAccompanyPlay(List<?> accompanyPlay) {
            this.accompanyPlay = accompanyPlay;
        }

        public List<?> getAccompanyPlayOrder() {
            return accompanyPlayOrder;
        }

        public void setAccompanyPlayOrder(List<?> accompanyPlayOrder) {
            this.accompanyPlayOrder = accompanyPlayOrder;
        }

        public List<?> getAddressBookFriend() {
            return addressBookFriend;
        }

        public void setAddressBookFriend(List<?> addressBookFriend) {
            this.addressBookFriend = addressBookFriend;
        }

        public List<?> getAddressBookMember() {
            return addressBookMember;
        }

        public void setAddressBookMember(List<?> addressBookMember) {
            this.addressBookMember = addressBookMember;
        }

        public List<?> getFamily() {
            return family;
        }

        public void setFamily(List<?> family) {
            this.family = family;
        }

        public List<?> getFamilyMember() {
            return familyMember;
        }

        public void setFamilyMember(List<?> familyMember) {
            this.familyMember = familyMember;
        }

        public List<?> getFinanceRecord() {
            return financeRecord;
        }

        public void setFinanceRecord(List<?> financeRecord) {
            this.financeRecord = financeRecord;
        }

        public List<?> getMemberNews() {
            return memberNews;
        }

        public void setMemberNews(List<?> memberNews) {
            this.memberNews = memberNews;
        }

        public List<?> getMemberNewsComment() {
            return memberNewsComment;
        }

        public void setMemberNewsComment(List<?> memberNewsComment) {
            this.memberNewsComment = memberNewsComment;
        }

        public List<?> getMemberNewsNotice() {
            return memberNewsNotice;
        }

        public void setMemberNewsNotice(List<?> memberNewsNotice) {
            this.memberNewsNotice = memberNewsNotice;
        }

        public List<?> getMemberNewsPraise() {
            return memberNewsPraise;
        }

        public void setMemberNewsPraise(List<?> memberNewsPraise) {
            this.memberNewsPraise = memberNewsPraise;
        }

        public List<?> getMemberShipFriend() {
            return memberShipFriend;
        }

        public void setMemberShipFriend(List<?> memberShipFriend) {
            this.memberShipFriend = memberShipFriend;
        }

        public List<?> getMemberShipMember() {
            return memberShipMember;
        }

        public void setMemberShipMember(List<?> memberShipMember) {
            this.memberShipMember = memberShipMember;
        }

        public List<?> getTeam() {
            return team;
        }

        public void setTeam(List<?> team) {
            this.team = team;
        }

        public List<?> getTeamMember() {
            return teamMember;
        }

        public void setTeamMember(List<?> teamMember) {
            this.teamMember = teamMember;
        }

        public List<?> getVipOrder() {
            return vipOrder;
        }

        public void setVipOrder(List<?> vipOrder) {
            this.vipOrder = vipOrder;
        }

        public List<?> getWithdrawCash() {
            return withdrawCash;
        }

        public void setWithdrawCash(List<?> withdrawCash) {
            this.withdrawCash = withdrawCash;
        }
    }
}
