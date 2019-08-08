package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/22:14
 * @Description:
 */
public class MemberIdEntity {

    /**
     * id : 8
     * title : 好玩吧
     * price : 20
     * images : UploadFiles/AccompanyPlay/20190711/Editor/cd46ab23-7c07-4f09-b17e-b38f4ac38db9.png
     * memberId : 1013
     * subjectTitle : 游戏陪玩
     * subjectImages : UploadFiles/1499249188416299.jpg
     */

    private int id;
    private String title;
    private int price;
    private String images;
    private int memberId;
    private String subjectTitle;
    private String subjectImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectImages() {
        return subjectImages;
    }

    public void setSubjectImages(String subjectImages) {
        this.subjectImages = subjectImages;
    }
}
