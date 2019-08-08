package com.chengfan.xiyou.domain.model.entity;

import java.io.Serializable;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-22/01:04
 * @Description:
 */
public class UpdateEntity implements Serializable {

    /**
     * fileName : vid_20190704_003231.mp4
     * fileExt : .mp4
     * fileSize : 0.0
     * filePath : UploadFiles/Member/20190722/bf3053fd-6f54-42a9-8280-d944c438fe47.mp4
     * fileUrl : http://xy.gx11.cn/UploadFiles/Member/20190722/bf3053fd-6f54-42a9-8280-d944c438fe47.mp4
     * success : true
     * msg : 上传成功
     */

    private String fileName;
    private String fileExt;
    private double fileSize;
    private String filePath;
    private String fileUrl;
    private boolean success;
    private String msg;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
