package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-21/23:23
 * @Description: {@link com.chengfan.xiyou.ui.accompany.AccompanyConfirmOrderActivity}
 */
public class AccompanyConfirmOrderBean implements Serializable {

    /**
     * accompanyPlayId : 陪玩Id
     * memberId : 下单人会员id
     * hour : 购买时长
     * remark : 备注
     */

    private String accompanyPlayId;
    private String memberId;
    private String hour;
    private String remark;

    public String getAccompanyPlayId() {
        return accompanyPlayId;
    }

    public void setAccompanyPlayId(String accompanyPlayId) {
        this.accompanyPlayId = accompanyPlayId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
