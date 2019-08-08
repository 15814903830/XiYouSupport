package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-17/13:27
 * @Description: 检查是否能发送私信
 */
public class CheckLetterEntity implements Serializable {

    /**
     * suc : false
     * msg : 您暂未开通会员，只能与10位用户聊天
     */

    private boolean suc;
    private String msg;

    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
