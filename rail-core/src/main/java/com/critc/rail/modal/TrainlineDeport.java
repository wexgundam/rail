/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:
 */
package com.critc.rail.modal;

import com.critc.network.modal.Grid;

import java.util.Date;

/**
 * what:    调度视角的行车调度台. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/11
 */
public class TrainlineDeport implements IRailNetworkElement {
    /**
     * 主键，唯一标识
     */
    private int id;
    /**
     * 网格空间几何类型，默认是点类型，可调整为多边形类型
     */
    private int gridGeometryType = Grid.GEOMETRY_TYPE_POLYGON;
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
     * 名称
     */
    private String name;
    /**
     * 管辖局Id
     */
    private int jurisdictionBureauId;
    /**
     * 管辖局名称
     */
    private String jurisdictionBureauName;
    /**
     * 名称全拼音码
     */
    private String namePinyin;
    /**
     * 名称首字母拼音码
     */
    private String nameInitialPinyin;
    /**
     * 电报码
     */
    private String telegraphCode;
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

        TrainlineDeport that = (TrainlineDeport) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "TrainlineDeport{" +
                "id=" + id +
                ", gridGeometryType=" + gridGeometryType +
                ", basePointString='" + basePointString + '\'' +
                ", anchorPointsString='" + anchorPointsString + '\'' +
                ", grid=" + grid +
                ", name='" + name + '\'' +
                ", jurisdictionBureauId=" + jurisdictionBureauId +
                ", jurisdictionBureauName='" + jurisdictionBureauName + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", nameInitialPinyin='" + nameInitialPinyin + '\'' +
                ", telegraphCode='" + telegraphCode + '\'' +
                ", creatorId=" + creatorId +
                ", creatorRealName='" + creatorRealName + '\'' +
                ", createdAt=" + createdAt +
                ", lastEditorId=" + lastEditorId +
                ", lastEditorRealName='" + lastEditorRealName + '\'' +
                ", lastEditedAt=" + lastEditedAt +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getGridGeometryType() {
        return gridGeometryType;
    }

    @Override
    public void setGridGeometryType(int gridGeometryType) {
        this.gridGeometryType = gridGeometryType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJurisdictionBureauId() {
        return jurisdictionBureauId;
    }

    public void setJurisdictionBureauId(int jurisdictionBureauId) {
        this.jurisdictionBureauId = jurisdictionBureauId;
    }

    public String getJurisdictionBureauName() {
        return jurisdictionBureauName;
    }

    public void setJurisdictionBureauName(String jurisdictionBureauName) {
        this.jurisdictionBureauName = jurisdictionBureauName;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getNameInitialPinyin() {
        return nameInitialPinyin;
    }

    public void setNameInitialPinyin(String nameInitialPinyin) {
        this.nameInitialPinyin = nameInitialPinyin;
    }

    public String getTelegraphCode() {
        return telegraphCode;
    }

    public void setTelegraphCode(String telegraphCode) {
        this.telegraphCode = telegraphCode;
    }

    @Override
    public int getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getCreatorRealName() {
        return creatorRealName;
    }

    @Override
    public void setCreatorRealName(String creatorRealName) {
        this.creatorRealName = creatorRealName;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int getLastEditorId() {
        return lastEditorId;
    }

    @Override
    public void setLastEditorId(int lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    @Override
    public String getLastEditorRealName() {
        return lastEditorRealName;
    }

    @Override
    public void setLastEditorRealName(String lastEditorRealName) {
        this.lastEditorRealName = lastEditorRealName;
    }

    @Override
    public Date getLastEditedAt() {
        return lastEditedAt;
    }

    @Override
    public void setLastEditedAt(Date lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }
}
