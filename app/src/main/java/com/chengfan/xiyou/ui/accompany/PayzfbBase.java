package com.chengfan.xiyou.ui.accompany;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class PayzfbBase {

    /**
     * data : app_id=2019072966075097&biz_content=%7b%22body%22%3a%22LOL%22%2c%22out_trade_no%22%3a%22950662%22%2c%22product_code%22%3a%22play_48_U_1071%22%2c%22subject%22%3a%22LOL%22%2c%22timeout_express%22%3a%2260m%22%2c%22total_amount%22%3a%220.01%22%7d&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fxy.gx11.cn%2fAPI%2fOrder%2fNotifyAccompanyPlayOrder%2fAliPay&sign_type=RSA2&timestamp=2019-08-13+11%3a01%3a13&version=1.0&sign=U%2fuRIXbGSSdgbR%2b8srrxCHp5QP6spazO2pEgv7xX1MyGsl21b4HybdqRsk6Y808hdYsI0W9hL8xX4FqlIqd0U7oxQHUyGE2zaWuzNhGhy5eGgBlwJFzPTfe2uwonFYhQpECEoPNUib%2fuaVBaGKa54L8CzvSH1bk1IVRzvEzy0XBN%2bOlv3szE4H6Q9HQ%2f3ZDKGZk5483OeMN9jTSubACu8fSSxFXrrY8%2bgIdOd5J8Ze8HFo9nh1I1tmVVoDzibGYOB%2bpTGHEaVvfPUxcec7CDpl1WoDCNox4sp%2b1IXxNqtdQYA3K6knT5VJiVjvnFAHMnXlmLjDhKUB4kyYRlmAoZgw%3d%3d
     * suc : true
     * msg : 创建订单成功，请支付
     */

    private String data;
    private boolean suc;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
