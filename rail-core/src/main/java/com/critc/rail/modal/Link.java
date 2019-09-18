/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:Rail
 */
package com.critc.rail.modal;

import com.critc.network.modal.Grid;

import java.util.Date;

/**
 * what:    调度视角的节点间. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/11
 */
public class Link implements IRailNetworkElement {
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
     * 网格空间几何类型，默认是线类型
     */
    private int gridGeometryType = Grid.GEOMETRY_TYPE_LINE_STRING;
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
    /**
     * 创建人Id，只在保存新增数据时填写，以后不可更改
     */
    private int creatorId;//创建人Id
    /**
     * 创建人实名，只在保存新增数据时填写，以后不可更改
     */
    private String creatorRealName;//创建人Name
    /**
     * 创建时间，只在保存新增数据时填写，以后不可更改
     */
    private Date createdAt;//创建时间
    /**
     * 最后修改人Id，在新增和更新数据时填写，修改后保存时可更改
     */
    private int lastEditorId;//最后修改人Id
    /**
     * 最后修改人实名，在新增和更新数据时填写，修改后保存时可更改
     */
    private String lastEditorRealName;//最后修改人实名
    /**
     * 最后修改时间，在新增和更新数据时填写，修改后保存时可更改
     */
    private Date lastEditedAt;//最后修改时间

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link bureau = (Link) o;

        return id == bureau.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", code=" + code +
                ", telegraphCode='" + telegraphCode + '\'' +
                ", gridGeometryType=" + gridGeometryType +
                ", basePointString='" + basePointString + '\'' +
                ", anchorPointsString='" + anchorPointsString + '\'' +
                ", grid=" + grid +
                ", creatorId=" + creatorId +
                ", creatorRealName='" + creatorRealName + '\'' +
                ", createdAt=" + createdAt +
                ", lastEditorId=" + lastEditorId +
                ", lastEditorRealName='" + lastEditorRealName + '\'' +
                ", lastEditedAt=" + lastEditedAt +
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

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorRealName() {
        return creatorRealName;
    }

    public void setCreatorRealName(String creatorRealName) {
        this.creatorRealName = creatorRealName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getLastEditorId() {
        return lastEditorId;
    }

    public void setLastEditorId(int lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public String getLastEditorRealName() {
        return lastEditorRealName;
    }

    public void setLastEditorRealName(String lastEditorRealName) {
        this.lastEditorRealName = lastEditorRealName;
    }

    public Date getLastEditedAt() {
        return lastEditedAt;
    }

    public void setLastEditedAt(Date lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }

    @Override
    public int getGridGeometryType() {
        return gridGeometryType;
    }

    @Override
    public void setGridGeometryType(int gridGeometryType) {
        this.gridGeometryType = gridGeometryType;
    }
}
