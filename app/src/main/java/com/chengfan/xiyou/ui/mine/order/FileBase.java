package com.chengfan.xiyou.ui.mine.order;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class FileBase {

    /**
     * fileName : png
     * fileExt :
     * fileSize : 0
     * filePath : UploadFiles/AccompanyPlayNews/20190813/2b8d7c8b-782b-4fb6-9890-be5dc5f0fb16
     * fileUrl : http://xy.gx11.cn/UploadFiles/AccompanyPlayNews/20190813/2b8d7c8b-782b-4fb6-9890-be5dc5f0fb16
     * success : true
     * msg : 上传成功
     */

    private String fileName;
    private String fileExt;
    private int fileSize;
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

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
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
