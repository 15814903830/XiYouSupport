package com.chengfan.xiyou.ui.main;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class VersionBase {

    /**
     * id : 1
     * androidAppVersion : null
     * androidDownloadUrl : https://wa.wawa89.com/app/liugang.apk
     * androidForceUpdate : true
     * androidDescription : 仅作测试更新提示是否弹出，实际不需要更新
     * iosVersion : 1.0.0
     * iosDownloadUrl : https://www.pgyer.com/1Zzp
     * iosForceUpdate : true
     * iosDescription : 优化了用户体验，请及时更新
     */

    private int id;
    private Object androidAppVersion;
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

    public Object getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(Object androidAppVersion) {
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
