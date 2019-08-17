package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/20:12
 * @Description:
 */
public class MineAddBean  implements Serializable {

    /**
     * id : 0
     * subjectId : 0
     * memberId : 0
     * title : string
     * images : string
     * weekDay : string
     * serviceStartTime : string
     * serviceEndTime : string
     * price : 0
     * remark : string
     * status : 0
     */

    private String id;
    private String subjectId;
    private String memberId;
    private String title;
    private String images;
    private String weekDay;
    private String serviceStartTime;
    private String serviceEndTime;
    private String price;
    private String remark;
    private String status;

    @Override
    public String toString() {
        return "MineAddBean{" +
                "id='" + id + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", title='" + title + '\'' +
                ", images='" + images + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", serviceStartTime='" + serviceStartTime + '\'' +
                ", serviceEndTime='" + serviceEndTime + '\'' +
                ", price='" + price + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", areaTitle='" + areaTitle + '\'' +
                ", gradeTitle='" + gradeTitle + '\'' +
                '}';
    }

    private String areaTitle;

    public String getAreaTitle() {
        return areaTitle;
    }

    public void setAreaTitle(String areaTitle) {
        this.areaTitle = areaTitle;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    private String gradeTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public String getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
