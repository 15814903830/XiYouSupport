package com.chengfan.xiyou.utils;

import com.chengfan.xiyou.domain.model.entity.RongEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.ci.widget.logger.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-25/23:39
 * @Description: 解散
 */
public class RongDismiss {
    public static String group(String userId, String groupId) {
        StringBuffer res = new StringBuffer();
        String url = "http://api-cn.ronghub.com/group/dismiss.json";
        String App_Key = "pwe86ga5p4qc6"; //开发者平台分配的 App Key。
        String App_Secret = "rpqjT7A5mxx";
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳，从 1970 年 1 月 1 日 0 点 0 分 0 秒开始到现在的秒数。
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));//随机数，无长度限制。
        String Signature = sha1(App_Secret + Nonce + Timestamp);//数据签名。
        Logger.e("CreateGroup ====>>" + Signature);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("App-Key", App_Key);
        httpPost.setHeader("Timestamp", Timestamp);
        httpPost.setHeader("Nonce", Nonce);
        httpPost.setHeader("Signature", Signature);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");


        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("groupId", groupId));
        nameValuePair.add(new BasicNameValuePair("userId", userId));

        HttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
            httpResponse = httpClient.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.d("CreateGroup  >>>>  " + res.toString());
        Type type = new TypeToken<RongEntity>() {
        }.getType();
        RongEntity userRespone = new Gson().fromJson(res.toString(), type);
        Logger.e("CreateGroup  >>>>  " + userRespone.getCode() + "");
        return userRespone.getCode();
    }

    private static String sha1(String data) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (int i = 0; i < bits.length; i++) {
                int a = bits[i];
                if (a < 0) a += 256;
                if (a < 16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
        } catch (Exception e) {

        }
        return buf.toString();
    }
}
