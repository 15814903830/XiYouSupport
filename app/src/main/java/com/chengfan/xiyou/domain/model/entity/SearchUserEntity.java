package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/18:23
 * @Description:
 */
public class SearchUserEntity implements Serializable {

    /**
     * id : 1170
     * gender : 1
     * nickname : 蜗牛超车2
     * userName : 13800139001
     * avatarUrl : UploadFiles/Member/20190823/Editor/0f69aa9c-ab01-40b6-92a0-ef6312993b99.png
     * applyLable : 1,2,3,4
     * approvalLable : 6,7
     * lableStatus : 1
     * totalFans : 1
     * applyLableList : [{"id":1,"icon":"img/lable/biaoqian01.png","text":"萝莉音","needApproval":false,"enabled":false,"sore":0},{"id":2,"icon":"img/lable/biaoqian02.png","text":"御姐音","needApproval":false,"enabled":false,"sore":0},{"id":3,"icon":"img/lable/biaoqian03.png","text":"大叔音","needApproval":false,"enabled":false,"sore":0},{"id":4,"icon":"img/lable/biaoqian04.png","text":"双音线","needApproval":false,"enabled":false,"sore":0}]
     * approvalLableList : [{"id":6,"icon":"img/lable/biaoqian06.png","text":"声优认证","needApproval":true,"enabled":false,"sore":0},{"id":7,"icon":"img/lable/biaoqian07.png","text":"语音连麦","needApproval":true,"enabled":false,"sore":0}]
     */

    private int id;
    private int gender;
    private String nickname;
    private String userName;
    private String avatarUrl;
    private String applyLable;
    private String approvalLable;
    private int lableStatus;
    private int totalFans;
    private List<ApplyLableListBean> applyLableList;
    private List<ApprovalLableListBean> approvalLableList;

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

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
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

    public static class ApplyLableListBean {
        /**
         * id : 1ss
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

    public static class ApprovalLableListBean {
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
}
