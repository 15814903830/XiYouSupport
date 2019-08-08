package com.chengfan.xiyou.utils;

import com.chengfan.xiyou.domain.model.entity.LoginEntity;
import com.chengfan.xiyou.domain.model.response.LoginResponse;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-06-21/13:09
 * @Description:
 */
public class UserStorage {
    private String versionCode;
    private LoginEntity mLoginBean;


    public static UserStorage instance;

    public static UserStorage getInstance() {
        synchronized (UserStorage.class) {
            if (instance == null) {
                instance = new UserStorage();
            }
        }
        return instance;
    }

    public UserStorage() {
        initUserInfo();
    }

    public void initUserInfo() {
        if (mLoginBean == null) {
            mLoginBean = AppData.getObject(AppData.Keys.AD_LOGIN_OBJECT, LoginEntity.class);
        }
        if (mLoginBean != null)
            saveLoginInfo(mLoginBean);
    }

    public LoginEntity getLogin() {
        return mLoginBean;
    }



    /**
     * 保存登录信息
     *
     * @param loginBean 登录信息
     */
    public void saveLoginInfo(LoginEntity loginBean) {
        this.mLoginBean = loginBean;
        AppData.putObject(AppData.Keys.AD_LOGIN_OBJECT, loginBean);
    }

    /**
     * 判断登录状态
     *
     * @return 登录状态
     */
    public boolean isLogin() {
        if (mLoginBean == null)
            return false;
        else
            return true;
    }




    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }


}
