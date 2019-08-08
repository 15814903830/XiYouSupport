package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-23/12:35
 * @Description:
 */
public class VIPOrderBean {

    /**
     * vipSetMealId : 0
     * uniformOrderId : 0
     * paymentChannel : 0
     * memberId : 0
     * body : string
     * totalPrice : 0
     * payPrice : 0
     * vipDay : 0
     * status : 0
     */

    private String vipSetMealId;
    private int uniformOrderId;
    private int paymentChannel;
    private int memberId;
    private String body;
    private int totalPrice;
    private String payPrice;
    private String vipDay;
    private int status;

    public String getVipSetMealId() {
        return vipSetMealId;
    }

    public void setVipSetMealId(String vipSetMealId) {
        this.vipSetMealId = vipSetMealId;
    }

    public int getUniformOrderId() {
        return uniformOrderId;
    }

    public void setUniformOrderId(int uniformOrderId) {
        this.uniformOrderId = uniformOrderId;
    }

    public int getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(int paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getVipDay() {
        return vipDay;
    }

    public void setVipDay(String vipDay) {
        this.vipDay = vipDay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
