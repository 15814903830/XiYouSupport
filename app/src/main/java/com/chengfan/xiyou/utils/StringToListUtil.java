package com.chengfan.xiyou.utils;

import com.chengfan.xiyou.domain.model.entity.UploadEntity;

import java.util.List;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-29/20:26
 * @Description:
 */
public class StringToListUtil {
    public static String listToString(List<UploadEntity> mList) {
        final String SEPARATOR = "|";
        // mList = Arrays.asList("AAA", "BBB", "CCC");
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            for (UploadEntity item : mList) {
                sb.append(item.getFilePath());
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length()
                    - SEPARATOR.length());
            return convertedListStr;
        } else return "List is null!!!";
    }
}
