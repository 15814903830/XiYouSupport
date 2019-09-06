package com.chengfan.xiyou.ui.mine.order;

public class MyLableBase {

    /**
     * suc : false
     * msg : 选择的标签不能超过4个
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
