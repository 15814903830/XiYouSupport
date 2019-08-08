package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-24/01:05
 * @Description:
 */
public class UpdateOrderStatusBean {
    /**
     * uniformOrderId : 0
     * accompanyPlayId : 0
     * memberId : 0
     * status : 0
     * refuseRemark : refuseRemark
     */

    private int uniformOrderId;
    private int accompanyPlayId;
    private int memberId;
    private int status;
    private String refuseRemark;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }
}
