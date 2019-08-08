package com.chengfan.xiyou.domain.model.entity;

import com.chengfan.xiyou.widget.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/20:02
 * @Description:
 */
public class MineAddPlayEntity implements IPickerViewData {

    /**
     * id : 1
     * images : UploadFiles/1499249188416299.jpg
     * title : 游戏陪玩
     * accompanyPlay : []
     */

    private int id;
    private String images;
    private String title;
    private List<?> accompanyPlay;

    @Override
    public String getPickerViewText() {
        return title;
    }

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

    public List<?> getAccompanyPlay() {
        return accompanyPlay;
    }

    public void setAccompanyPlay(List<?> accompanyPlay) {
        this.accompanyPlay = accompanyPlay;
    }
}
