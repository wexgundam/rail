/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

import com.critc.network.modal.Grid;

/**
 * what:    车站. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/4
 */
public class Station implements IRailNetworkElement {
    /**
     * 主键，唯一标识
     */
    private int id;
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

        Station station = (Station) o;

        return id == station.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public String getBasePointString() {
        return basePointString;
    }

    public void setBasePointString(String basePointString) {
        this.basePointString = basePointString;
    }

    public String getAnchorPointsString() {
        return anchorPointsString;
    }

    public void setAnchorPointsString(String anchorPointsString) {
        this.anchorPointsString = anchorPointsString;
    }
}
