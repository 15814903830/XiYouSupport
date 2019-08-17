package com.chengfan.xiyou.utils;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.zero.ci.widget.lightkv.ConfuseEncoder;
import com.zero.ci.widget.lightkv.DataType;
import com.zero.ci.widget.lightkv.GlobalConfig;
import com.zero.ci.widget.lightkv.LightKV;
import com.zero.ci.widget.lightkv.SyncKV;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 19/4/9/上午10:55
 * @Description: 数据保存
 */
public class AppData {
    private static final SyncKV DATA =
            new LightKV.Builder(GlobalConfig.getAppContext(), "app_data")
                    .executor(AsyncTask.THREAD_POOL_EXECUTOR)
                    .encoder(new ConfuseEncoder())
                    .sync();


    public interface Keys {
        int SHOW_COUNT = 1 | DataType.INT;
        int ACCOUNT = 2 | DataType.STRING | DataType.ENCODE;
        int TOKEN = 3 | DataType.STRING | DataType.ENCODE;
        int AD_LOGIN_OBJECT = 4 | DataType.STRING;
        int AD_FAMILY_OBJECT = 5 | DataType.STRING;
        int AD_SELECT_SEX = 6 | DataType.STRING;
        int AD_IS_SHOW = 9 | DataType.BOOLEAN;
        int AD_IS_PLACE_ORDER = 10 | DataType.BOOLEAN;
        int AD_USER_ID = 11 | DataType.STRING;
        int AD_SHOW_SAY_HI = 16 | DataType.BOOLEAN;
        int AD_USER_IMAGE = 11 | DataType.STRING;
    }

    public static SyncKV data() {
        return DATA;
    }

    public static String getString(int key) {
        return DATA.getString(key);
    }

    public static void putString(int key, String value) {
        DATA.putString(key, value);
        DATA.commit();
    }

    public static boolean getBoole(int key) {
        return DATA.getBoolean(key);
    }

    public static void putBoolen(int key, boolean value) {
        DATA.putBoolean(key, value);
        DATA.commit();
    }

    public static void putObject(int key, Object object) {
        putString(key, new Gson().toJson(object));
    }

    public static <T> T getObject(int key, Class<T> tClass) {
        String jsonStr = getString(key);
        if (jsonStr == null) {
            return null;
        } else {
            return new Gson().fromJson(jsonStr, tClass);
        }
    }

    public static void putList(int key, List list) {
        Gson gson = new Gson();
        putString(key, gson.toJson(list));
    }


    public static void remove(int key) {
        DATA.remove(key);
    }


}
