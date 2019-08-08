package com.chengfan.xiyou.utils;

import com.zero.ci.network.http.rest.binary.BasicBinary;
import com.zero.ci.network.http.rest.binary.FileBinary;
import com.zero.ci.network.http.rest.binary.InputStreamBinary;
import com.zero.ci.network.zrequest.response.UploadFile;
import com.zero.ci.widget.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-20/21:26
 * @Description:
 */
public class FileToBase64 {
    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String encode64 = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);

        return encode64;
    }


    public static String best64(UploadFile file) {
        BasicBinary basicBinary = null;
        Logger.d("FileToBase64  : " + file.getMode());

        if (file.getMode() instanceof File) {
            basicBinary = new FileBinary((File) file.getMode(), file.getKey());
        } else if (file.getMode() instanceof FileInputStream) {
            basicBinary = new InputStreamBinary((FileInputStream) file.getMode(), file.getKey());
        }
        //request.add(file.getKey(), basicBinary);

        byte[] buffer = new byte[(int) basicBinary.getLength()];
        String encode64 = android.util.Base64.encodeToString(buffer, android.util.Base64.DEFAULT);
        return encode64;
    }


}
