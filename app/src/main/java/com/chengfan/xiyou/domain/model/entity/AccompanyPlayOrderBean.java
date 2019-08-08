package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/23:43
 * @Description:
 */
public class AccompanyPlayOrderBean {
    int UniformOrderId;
    int AccompanyPlayId;
    int MemberId;

    public int getUniformOrderId() {
        return UniformOrderId;
    }

    public void setUniformOrderId(int uniformOrderId) {
        UniformOrderId = uniformOrderId;
    }

    public int getAccompanyPlayId() {
        return AccompanyPlayId;
    }

    public void setAccompanyPlayId(int accompanyPlayId) {
        AccompanyPlayId = accompanyPlayId;
    }

    public int getMemberId() {
        return MemberId;
    }

    public void setMemberId(int memberId) {
        MemberId = memberId;
    }
}
