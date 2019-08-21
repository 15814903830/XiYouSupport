package com.chengfan.xiyou.domain.model.entity;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class LikeBase {
    private String MemberId;
    private String MemberNewsId;

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getMemberNewsId() {
        return MemberNewsId;
    }

    public void setMemberNewsId(String memberNewsId) {
        MemberNewsId = memberNewsId;
    }
}
