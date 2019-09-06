package com.chengfan.xiyou.domain.model.entity;

public class ApplyLableListBean {
    /**
     * id : 1ss
     * icon : img/lable/biaoqian01.png
     * text : 萝莉音
     * needApproval : false
     * enabled : false
     * sore : 0
     */

    private int id;
    private String icon;
    private String text;
    private boolean needApproval;
    private boolean enabled;
    private int sore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNeedApproval() {
        return needApproval;
    }

    public void setNeedApproval(boolean needApproval) {
        this.needApproval = needApproval;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSore() {
        return sore;
    }

    public void setSore(int sore) {
        this.sore = sore;
    }
}
