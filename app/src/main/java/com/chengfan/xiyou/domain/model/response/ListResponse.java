package com.chengfan.xiyou.domain.model.response;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-11/13:25
 * @Description:
 */
public class ListResponse<T> {
    private List<T> d;

    public List<T> getD() {
        return d;
    }

    public void setD(List<T> d) {
        this.d = d;
    }
}
