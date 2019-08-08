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
     * id : 7
     * images : UploadFiles/AccompanyPlay/20190711/Editor/b70f0363-4c7f-4c06-bb5b-8870c104609d.png
     * title : 123123
     * price : 23
     * memberId : 1010
     */

    private int id;
    private String images;
    private String title;
    private int price;
    private int memberId;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
