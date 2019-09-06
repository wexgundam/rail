/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.network.modal;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    <b>网格:</b>铁路路网所有元素的基本特征，每一元素都可视为一个网格。<br/>
 * 网格仅含有与网络有关的信息，不含任何业务信息。 <br/>
 * 基点：在小比例尺时，网格较小，可视为点，基点是网格作为点时的坐标。 <br/>
 * 锚点：网格控制点的集合。控制点能够描述网格的位置、范围。 <br/>
 * <b>网格间关系：</b>网格与网格之间可以是单点先交、多点相交、相切、包含和分离五种关系.<br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/8/29
 */
public class Grid {
    /**
     * 主键：唯一性标识。
     */
    private int id;
    /**
     * 基点坐标的向量表示
     */
    private PointVector basePointVector;
    /**
     * 锚点坐标的向量表示，即每个锚点的向量坐标的集合。
     */
    private List<PointVector> anchorPointVectors;
    /**
     * 源对象，网格代表的实物对象
     */
    private Object original;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        return id == grid.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * what:    添加一个锚点，新加入的锚点放在锚点集合的最后位置. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void addAnchorPointVector(double x, double y) {
        PointVector pointVector = new PointVector();
        pointVector.setPoint(x, y);
        getAnchorPointVectors().add(pointVector);
    }

    /**
     * what:    添加一个锚点，新加入的锚点放在锚点集合的最后位置. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void addAnchorPointVector(int index, double x, double y) {
        PointVector pointVector = new PointVector();
        pointVector.setPoint(x, y);
        getAnchorPointVectors().add(index, pointVector);
    }

    /**
     * what:    更新一个锚点的坐标值. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void updateAnchorPointVector(int index, double x, double y) {
        PointVector pointVector = getAnchorPointVectors().get(index);
        pointVector.setPoint(x, y);
    }

    /**
     * what:    移除制定索引的锚点. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void removeAnchorPointVector(int index) {
        getAnchorPointVectors().remove(index);
    }

    /**
     * what:    移除所有锚点. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void removeAllAnchorPointVectors() {
        getAnchorPointVectors().clear();
    }

    /**
     * what:    获得所有向量形式的锚点的集合. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public List<PointVector> getAnchorPointVectors() {
        anchorPointVectors = anchorPointVectors == null ? new ArrayList<>() : anchorPointVectors;
        return anchorPointVectors;
    }

    /**
     * what:    获得给定索引位置的锚点. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public PointVector getAnchorPointVector(int index) {
        return getAnchorPointVectors().get(index);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
        this.original = original;
    }

    public PointVector getBasePointVector() {
        return basePointVector;
    }

    public void setBasePointVector(PointVector basePointVector) {
        this.basePointVector = basePointVector;
    }

    public void setAnchorPointVectors(List<PointVector> anchorPointVectors) {
        this.anchorPointVectors = anchorPointVectors;
    }
}
