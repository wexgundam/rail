/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:Rail
 */
package com.critc.rail.modal;

import com.critc.network.modal.Grid;

/**
 * what:    铁路局. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/11
 */
public class Bureau implements IRailNetworkElement {
    /**
     * 主键
     */
    private int id;
    /**
     * 全称
     */
    private String name;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 编码
     */
    private int code;
    /**
     * 电报码
     */
    private String telegraphCode;
    /**
     * 基点坐标的字符串表示，表示方式：x@y，"@"为分隔符
     */
    private String basePointString;
    /**
     * 锚点坐标的字符串表示，表示方式：x1@y1;x2@y2，“；”为两个锚点的分隔符
     */
    private String anchorPointsString;
    /**
     * 对应的网格
     */
    private Grid grid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bureau bureau = (Bureau) o;

        return id == bureau.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Bureau{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", code=" + code +
                ", telegraphCode='" + telegraphCode + '\'' +
                ", basePointString='" + basePointString + '\'' +
                ", anchorPointsString='" + anchorPointsString + '\'' +
                ", grid=" + grid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTelegraphCode() {
        return telegraphCode;
    }

    public void setTelegraphCode(String telegraphCode) {
        this.telegraphCode = telegraphCode;
    }

    @Override
    public String getBasePointString() {
        return basePointString;
    }

    @Override
    public void setBasePointString(String basePointString) {
        this.basePointString = basePointString;
    }

    @Override
    public String getAnchorPointsString() {
        return anchorPointsString;
    }

    @Override
    public void setAnchorPointsString(String anchorPointsString) {
        this.anchorPointsString = anchorPointsString;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
