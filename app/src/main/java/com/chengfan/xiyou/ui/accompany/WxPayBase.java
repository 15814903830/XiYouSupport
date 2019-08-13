package com.chengfan.xiyou.ui.accompany;

import com.google.gson.annotations.SerializedName;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class WxPayBase {

    /**
     * data : {"appid":"wx9d039eb839d60758","noncestr":"1296215721","package":"Sign=WXPay","partnerid":"1543012411","prepayid":"wx13110835763147a0839c56cb1714555800","sign":"6EEB74C11880FD585D8DF1FE6F410067","timestamp":"1565665716"}
     * suc : true
     * msg : 生成订单成功，请支付
     */

    private DataBean data;
    private boolean suc;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * appid : wx9d039eb839d60758
         * noncestr : 1296215721
         * package : Sign=WXPay
         * partnerid : 1543012411
         * prepayid : wx13110835763147a0839c56cb1714555800
         * sign : 6EEB74C11880FD585D8DF1FE6F410067
         * timestamp : 1565665716
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
