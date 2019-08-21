package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/19:44
 * @Description:
 */
public class SearchGameEntity implements Serializable {

    /**
     * id : 62
     * images : UploadFiles/AccompanyPlay/20190817/Editor/ea994c9b-5698-4cde-921b-b30936da1ef8.mp4
     * title : 甾醇香啊！，。，你们家是不是真的喜欢你没道理都懂得感恩师兄你们家是不是真的
     * price : 0.1
     * status : 0
     * memberId : 1053
     * areaTitle :
     * audioPath :
     * gradeTitle :
     */

    private int id;
    private String images;
    private String title;
    private double price;
    private int status;
    private int memberId;
    private String areaTitle;
    private String audioPath;
    private String gradeTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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
}
