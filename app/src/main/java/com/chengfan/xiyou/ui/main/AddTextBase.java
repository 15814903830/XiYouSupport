package com.chengfan.xiyou.ui.main;

public class AddTextBase {
    /**
     * id : e8ccc900-3071-4269-ab12-06859eb2eb72
     * memberId : 1083
     * memberNewsId : 224
     * type : 1
     * comtent : 胡椒给您的动态点赞！
     * status : false
     * createTime : 2019-09-03T15:03:58.0313313
     * member : null
     * memberNews : null
     */
    private String id;
    private int memberId;
    private int memberNewsId;
    private int type;
    private String comtent;
    private boolean status;
    private String createTime;
    private Object member;
    private Object memberNews;

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

    public Object getMember() {
        return member;
    }

    public void setMember(Object member) {
        this.member = member;
    }

    public Object getMemberNews() {
        return memberNews;
    }

    public void setMemberNews(Object memberNews) {
        this.memberNews = memberNews;
    }
}
