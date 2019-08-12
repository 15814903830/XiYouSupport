package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-26/02:43
 * @Description:
 */
public class AppManagerEntity implements Serializable {

    /**
     * id : 1
     * androidAppVersion : 1.0.2
     * androidDownloadUrl : https://wa.wawa89.com/app/liugang.apk
     * androidForceUpdate : true
     * androidDescription : 仅作测试更新提示是否弹出，实际不需要更新
     * iosVersion : 1.1.0
     * iosDownloadUrl : https://itunes.apple.com/cn/app/wechat/id382201985
     * iosForceUpdate : false
     * iosDescription : 仅作测试更新提示是否弹出，实际不需要更新
     */

    private int id;
    private String androidAppVersion;
    private String androidDownloadUrl;
    private boolean androidForceUpdate;
    private String androidDescription;
    private String iosVersion;
    private String iosDownloadUrl;
    private boolean iosForceUpdate;
    private String iosDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }

    public String getAndroidDownloadUrl() {
        return androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(String androidDownloadUrl) {
        this.androidDownloadUrl = androidDownloadUrl;
    }

    public boolean isAndroidForceUpdate() {
        return androidForceUpdate;
    }

    public void setAndroidForceUpdate(boolean androidForceUpdate) {
        this.androidForceUpdate = androidForceUpdate;
    }

    public String getAndroidDescription() {
        return androidDescription;
    }

    public void setAndroidDescription(String androidDescription) {
        this.androidDescription = androidDescription;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getIosDownloadUrl() {
        return iosDownloadUrl;
    }

    public void setIosDownloadUrl(String iosDownloadUrl) {
        this.iosDownloadUrl = iosDownloadUrl;
    }

    public boolean isIosForceUpdate() {
        return iosForceUpdate;
    }

    public void setIosForceUpdate(boolean iosForceUpdate) {
        this.iosForceUpdate = iosForceUpdate;
    }

    public String getIosDescription() {
        return iosDescription;
    }

    public void setIosDescription(String iosDescription) {
        this.iosDescription = iosDescription;
    }
}
