package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-04/15:41
 * @Description:
 */
public class XiYouBean  implements Serializable {

    private int showDrawable;
    private String title;
    private String subjectId;

    public XiYouBean(int showDrawable, String title, String subjectId) {
        this.showDrawable = showDrawable;
        this.title = title;
        this.subjectId = subjectId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }


    public int getShowDrawable() {
        return showDrawable;
    }

    public void setShowDrawable(int showDrawable) {
        this.showDrawable = showDrawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
