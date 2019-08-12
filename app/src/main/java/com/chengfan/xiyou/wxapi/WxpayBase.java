package com.chengfan.xiyou.wxapi;

import com.google.gson.annotations.SerializedName;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class WxpayBase {

    /**
     * data : {"appid":"wx9d039eb839d60758","noncestr":"4190160447","package":"Sign=WXPay","partnerid":"1543012411","prepayid":"wx07171626495707af681665721658062100","sign":"BE8DEDB44F3281B0FF6F26124B7A4A23","timestamp":"1565169387"}
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
         * noncestr : 4190160447
         * package : Sign=WXPay
         * partnerid : 1543012411
         * prepayid : wx07171626495707af681665721658062100
         * sign : BE8DEDB44F3281B0FF6F26124B7A4A23
         * timestamp : 1565169387
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
