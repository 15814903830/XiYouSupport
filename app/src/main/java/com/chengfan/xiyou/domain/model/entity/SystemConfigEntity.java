package com.chengfan.xiyou.domain.model.entity;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-19/11:11
 * @Description:
 */
public class SystemConfigEntity {

    /**
     * id : 2
     * title : 外貌配置
     * value : 温柔可人;贤良淑德;娇小萝莉;知性体贴;大方得体;风韵犹存;清新可爱;天真可爱
     * datatype : null
     * allowNull : false
     * groupName : null
     * remarks : null
     */

    private int id;
    private String title;
    private String value;
    private Object datatype;
    private boolean allowNull;
    private Object groupName;
    private Object remarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getDatatype() {
        return datatype;
    }

    public void setDatatype(Object datatype) {
        this.datatype = datatype;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }
}
