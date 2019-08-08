package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/19:19
 * @Description:
 */
public class PublishCommentBean {

    /**
     * memberId : 1013
     * memberNewsId : 2
     * memberNewsCommentId : 2
     * content : sdfdfs
     */

    private String memberId;
    private String memberNewsId;
    private String memberNewsCommentId;
    private String content;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberNewsId() {
        return memberNewsId;
    }

    public void setMemberNewsId(String memberNewsId) {
        this.memberNewsId = memberNewsId;
    }

    public String getMemberNewsCommentId() {
        return memberNewsCommentId;
    }

    public void setMemberNewsCommentId(String memberNewsCommentId) {
        this.memberNewsCommentId = memberNewsCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
