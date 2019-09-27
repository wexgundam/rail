/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:
 */
package com.critc.rail.vo;

/**
 * what:    路局查询条件Vo. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/11
 */
public class BureauSearchVo {
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
     * 简称等于
     */
    private String shortNameEqual;
    /**
     * 编码等于
     */
    private Integer codeEqual;
    /**
     * 电报码等于
     */
    private String telegraphCodeEqual;

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

    public String getShortNameEqual() {
        return shortNameEqual;
    }

    public void setShortNameEqual(String shortNameEqual) {
        this.shortNameEqual = shortNameEqual;
    }

    public Integer getCodeEqual() {
        return codeEqual;
    }

    public void setCodeEqual(Integer codeEqual) {
        this.codeEqual = codeEqual;
    }

    public String getTelegraphCodeEqual() {
        return telegraphCodeEqual;
    }

    public void setTelegraphCodeEqual(String telegraphCodeEqual) {
        this.telegraphCodeEqual = telegraphCodeEqual;
    }

    public Integer getIdEqual() {
        return idEqual;
    }

    public void setIdEqual(Integer idEqual) {
        this.idEqual = idEqual;
    }
}
