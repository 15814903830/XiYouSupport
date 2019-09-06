package com.chengfan.xiyou.ui.accompany;

import java.util.List;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class ListduanweiBase {

    /**
     * id : 31
     * images : null
     * title : 租号中心
     * areaTitles : 大区:微信区,QQ区
     * gradeTitles : 游戏:王者荣耀,和平精英,英雄联盟,QQ飞车,绝地求生,全军出击,CSGO
     * sore : 0
     * pid : 32
     * sort : 31
     * accompanyPlay : []
     */

    private int id;
    private Object images;
    private String title;
    private String areaTitles;
    private String gradeTitles;
    private int sore;
    private int pid;
    private int sort;
    private List<?> accompanyPlay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAreaTitles() {
        return areaTitles;
    }

    public void setAreaTitles(String areaTitles) {
        this.areaTitles = areaTitles;
    }

    public String getGradeTitles() {
        return gradeTitles;
    }

    public void setGradeTitles(String gradeTitles) {
        this.gradeTitles = gradeTitles;
    }

    public int getSore() {
        return sore;
    }

    public void setSore(int sore) {
        this.sore = sore;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<?> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<?> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }
}
