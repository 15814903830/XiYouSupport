package com.chengfan.xiyou.ui.accompany;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ListviewBase {

    /**
     * id : 123
     * title : uuu
     * price : 11
     * status : 0
     * images : UploadFiles/AccompanyPlayNews/20190916/d6463880-171c-4110-9cd4-6670f7f6b444
     * memberId : 10000
     * subjectId : 8
     * areaTitle : 男
     * audioPath : UploadFiles/AccompanyPlayNews/20190916/4e53f36f-6a50-4e46-aa61-140b80931e48
     * gradeTitle : 情绪
     * recommendTime : null
     * age : 18
     * gender : 0
     * nickname : 尴尬
     * userName : 15814903830
     * avatarUrl : UploadFiles/AccompanyPlayNews/20190916/f2fc2322-b318-41ed-9fc6-fef546cdff8c
     * areaCode : 440100
     * areaName : 广州市
     * totalFans : 0
     * vip : true
     * member : {"age":18,"gender":0,"nickname":"尴尬","userName":"15814903830","avatarUrl":"UploadFiles/AccompanyPlayNews/20190916/f2fc2322-b318-41ed-9fc6-fef546cdff8c","areaCode":440100,"areaName":"广州市","lableAudio":"UploadFiles/AccompanyPlayNews/20190916/4d740d6b-4d1d-49f4-844c-df64946ab752|UploadFiles/AccompanyPlayNews/20190916/4e72ed61-884f-4dcb-89c7-9f2346c9cac2|UploadFiles/AccompanyPlayNews/20190916/19c16c25-0b19-4b67-b702-e83c4714a5c3|","lableStatus":0,"applyLable":"9,13,5,2","approvalLable":null,"totalFans":0,"vip":true}
     * total : 0
     */

    private int id;
    private String title;
    private int price;
    private int status;
    private String images;
    private int memberId;
    private int subjectId;
    private String areaTitle;
    private String audioPath;
    private String gradeTitle;
    private String recommendTime;
    private int age;
    private int gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private int areaCode;
    private String areaName;
    private int totalFans;
    private boolean vip;
    private MemberBean member;
    private int total;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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

    public Object getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

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

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class MemberBean {
        /**
         * age : 18
         * gender : 0
         * nickname : 尴尬
         * userName : 15814903830
         * avatarUrl : UploadFiles/AccompanyPlayNews/20190916/f2fc2322-b318-41ed-9fc6-fef546cdff8c
         * areaCode : 440100
         * areaName : 广州市
         * lableAudio : UploadFiles/AccompanyPlayNews/20190916/4d740d6b-4d1d-49f4-844c-df64946ab752|UploadFiles/AccompanyPlayNews/20190916/4e72ed61-884f-4dcb-89c7-9f2346c9cac2|UploadFiles/AccompanyPlayNews/20190916/19c16c25-0b19-4b67-b702-e83c4714a5c3|
         * lableStatus : 0
         * applyLable : 9,13,5,2
         * approvalLable : null
         * totalFans : 0
         * vip : true
         */

        private int age;
        private int gender;
        private String nickname;
        private String userName;
        private String avatarUrl;
        private int areaCode;
        private String areaName;
        private String lableAudio;
        private int lableStatus;
        private String applyLable;
        private String approvalLable;
        private int totalFans;
        private boolean vip;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

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

        public String getLableAudio() {
            return lableAudio;
        }

        public void setLableAudio(String lableAudio) {
            this.lableAudio = lableAudio;
        }

        public int getLableStatus() {
            return lableStatus;
        }

        public void setLableStatus(int lableStatus) {
            this.lableStatus = lableStatus;
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

        public int getTotalFans() {
            return totalFans;
        }

        public void setTotalFans(int totalFans) {
            this.totalFans = totalFans;
        }

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }
    }
}
