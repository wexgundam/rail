/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:
 */
package com.critc.rail.vo;

/**
 * what:    车站查询条件Vo. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/11
 */
public class StationSearchVo {
    /**
     * id等于
     */
    private Integer idEqual;
    /**
     * 全称等于
     */
    private String nameEqual;
    /**
     * 全称Like
     */
    private String nameLike;
    /**
     * 管辖局Id等于
     */
    private Integer jurisdictionBureauIdEqual;
    /**
     * 管辖行车调度台Id等于
     */
    private Integer jurisdictionTdIdEqual;
    /**
     * 电报码等于
     */
    private String telegraphCodeEqual;
    /**
     * 电报码like
     */
    private String telegraphCodeLike;
    /**
     * 拼音码like
     */
    private String pinyinLike;
    /**
     * 是否为路局分界口
     */
    private Boolean bureauPartingEqual;

    public String getNameEqual() {
        return nameEqual;
    }


    public void setNameEqual(String nameEqual) {
        this.nameEqual = nameEqual;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Integer getJurisdictionBureauIdEqual() {
        return jurisdictionBureauIdEqual;
    }

    public void setJurisdictionBureauIdEqual(Integer jurisdictionBureauIdEqual) {
        this.jurisdictionBureauIdEqual = jurisdictionBureauIdEqual;
    }

    public Integer getJurisdictionTdIdEqual() {
        return jurisdictionTdIdEqual;
    }

    public void setJurisdictionTdIdEqual(Integer jurisdictionTdIdEqual) {
        this.jurisdictionTdIdEqual = jurisdictionTdIdEqual;
    }

    public String getTelegraphCodeLike() {
        return telegraphCodeLike;
    }

    public void setTelegraphCodeLike(String telegraphCodeLike) {
        this.telegraphCodeLike = telegraphCodeLike;
    }

    public String getPinyinLike() {
        return pinyinLike;
    }

    public void setPinyinLike(String pinyinLike) {
        this.pinyinLike = pinyinLike;
    }

    public Integer getIdEqual() {
        return idEqual;
    }

    public void setIdEqual(Integer idEqual) {
        this.idEqual = idEqual;
    }

    public Boolean getBureauPartingEqual() {
        return bureauPartingEqual;
    }

    public void setBureauPartingEqual(Boolean bureauPartingEqual) {
        this.bureauPartingEqual = bureauPartingEqual;
    }

    public String getTelegraphCodeEqual() {
        return telegraphCodeEqual;
    }

    public void setTelegraphCodeEqual(String telegraphCodeEqual) {
        this.telegraphCodeEqual = telegraphCodeEqual;
    }
}
