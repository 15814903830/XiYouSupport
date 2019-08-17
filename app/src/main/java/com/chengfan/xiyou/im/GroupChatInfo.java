package com.chengfan.xiyou.im;

import org.litepal.crud.LitePalSupport;

/**
 * 群组信息
 */
public class GroupChatInfo extends LitePalSupport {

    private String targetId;
    private String name;
    private String image;

    public GroupChatInfo() {
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
