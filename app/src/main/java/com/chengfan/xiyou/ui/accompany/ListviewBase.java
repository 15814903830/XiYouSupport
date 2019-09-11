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
     * id : 69
     * title : 吃鸡啦 欢迎来撩
     * price : 5
     * status : 0
     * images : UploadFiles/AccompanyPlay/20190821/Editor/08747209-3ca3-4a7a-bba9-cb8462fa9e70.mp4
     * memberId : 1127
     * subjectId : 2
     * areaTitle : 微信
     * audioPath : UploadFiles/Member/20190821/Editor/13b44ecf-cfbd-4540-af86-c7c6b9285cc3.aac
     * gradeTitle : 荣耀皇冠
     * recommendTime : null
     * age : 20
     * gender : 1
     * nickname : 蜗牛超车
     * userName : 13800139000
     * avatarUrl : UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png
     * areaCode : 440100
     * areaName : 广东省 广州市
     * totalFans : 1
     * vip : false
     * member : {"age":20,"gender":1,"nickname":"蜗牛超车","userName":"13800139000","avatarUrl":"UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png","areaCode":440100,"areaName":"广东省 广州市","lableAudio":"0d104a48-6ec6-4306-a7a7-49169e251eb0.aac","applyLable":"1,2,3,4","approvalLable":"6,7","totalFans":1,"vip":false}
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
    private Object recommendTime;
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

    public void setRecommendTime(Object recommendTime) {
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
         * age : 20
         * gender : 1
         * nickname : 蜗牛超车
         * userName : 13800139000
         * avatarUrl : UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png
         * areaCode : 440100
         * areaName : 广东省 广州市
         * lableAudio : 0d104a48-6ec6-4306-a7a7-49169e251eb0.aac
         * applyLable : 1,2,3,4
         * approvalLable : 6,7
         * totalFans : 1
         * vip : false
         */

        private int age;
        private int gender;
        private String nickname;
        private String userName;
        private String avatarUrl;
        private int areaCode;
        private String areaName;
        private String lableAudio;
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
