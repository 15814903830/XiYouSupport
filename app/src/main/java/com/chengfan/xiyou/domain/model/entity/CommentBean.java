package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;
import java.util.List;


public class CommentBean  implements Serializable {
    private int code;
    private String message;
    private Data data;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public class Data {

        private int total;
        private List<CommentDetailBean> list;
        public void setTotal(int total) {
            this.total = total;
        }
        public int getTotal() {
            return total;
        }

        public void setList(List<CommentDetailBean> list) {
            this.list = list;
        }
        public List<CommentDetailBean> getList() {
            return list;
        }

    }

}
