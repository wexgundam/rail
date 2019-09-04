/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

import com.critc.rail.util.Global.GridGlobal;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * what:    网格。铁路路网所有元素的基本特征，每一元素都可视为一个网格。<br/>
 * 网格仅含有与网络有关的信息，不含任何业务信息。 <br/>
 * 用向量Vector表示坐标，用容积为2，增长率0的Vector存储basePoint的值，vector[0]代表X，vector[1]代表Y。 <br/>
 * 基点：在小比例尺时，网格较小，可视为点，基点是网格作为点时的坐标。 <br/>
 * 锚点：网格控制点的集合。控制点能够描述网格的位置、范围。 <br/>
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
     * 基点坐标的字符串表示，表示方式：x@y，"@"为分隔符
     */
    private String basePointString;
    /**
     * 基点坐标的向量表示
     * 该值根据basePoint计算而得，也可将其转换为basePoint。
     * basePointValue.get(0)代表X轴坐标。
     * basePointValue.get(1)代表Y轴坐标。
     */
    private Vector<Double> basePointVector;
    /**
     * 锚点坐标的字符串表示，表示方式：x1@y1;x2@y2，“；”为两个锚点的分隔符
     */
    private String anchorPointsString;
    /**
     * 锚点坐标的向量表示，即每个锚点的向量坐标的集合。
     */
    private List<Vector<Double>> anchorPointVectors;
    /**
     * 源对象，网格代表的实物对象
     */
    private Object origin;

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
     * what:    根据数据对象的值，生成Modal属性. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void toModal() {
        //将字符串形式的基点坐标转为向量形式
        basePointVector = toVector(basePointString);

        //解析anchorPoints，转为vector集合
        anchorPointVectors = toVectors(anchorPointsString);
    }


    /**
     * what:    根据Modal属性，生成数据对象的值. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void toData() {
        //将向量形式的基点坐标转为字符形式
        basePointString = toString(basePointVector);
        //将向量形式的锚点坐标的集合转为字符串形式
        anchorPointsString = toString(getAnchorPoints());
    }

    /**
     * what:    将向量形式的坐标转为字符串形式. <br/>
     * 字符串形式为"X@Y";<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    private String toString(Vector<Double> pointVector) {
        if (pointVector == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(pointVector.get(0));
        stringBuffer.append(GridGlobal.POINT_XY_SPLITTER);
        stringBuffer.append(pointVector.get(1));
        return stringBuffer.substring(0);
    }

    /**
     * what:    将向量形式的坐标集合转为字符串形式. <br/>
     * 字符串形式为"X1@Y1;X2@Y2";<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    private String toString(List<Vector<Double>> pointVectors) {
        if (pointVectors == null || pointVectors.isEmpty()) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Vector<Double> pointVector : pointVectors) {
            stringBuffer.append(toString(pointVector));
            stringBuffer.append(GridGlobal.POINT_SPLITTER);
        }
        // 移除最后添加的POINT_SPLITTER
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    /**
     * what:    将给定字符串形式的坐标，转为向量形式的坐标。 <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @param pointString 字符串形式的坐标
     *
     * @return 向量形式的坐标
     *
     * @author 靳磊 created on 2019/9/3
     */
    private Vector<Double> toVector(String pointString) {
        String[] pointStringValueArray = pointString.split(GridGlobal.POINT_XY_SPLITTER);
        Vector<Double> pointVector = createPointVector();
        setPoint(pointVector, Double.parseDouble(pointStringValueArray[0]), Double.parseDouble(pointStringValueArray[1]));
        return pointVector;
    }

    /**
     * what:    将给定字符串形式的若干坐标，转为向量形式的若干坐标的集合。 <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @param pointsString 字符串形式的若干坐标
     *
     * @return 向量形式的若干坐标的集合
     *
     * @author 靳磊 created on 2019/9/3
     */
    private List<Vector<Double>> toVectors(String pointsString) {
        String[] pointStringArray = pointsString.split(GridGlobal.POINT_SPLITTER);
        List<Vector<Double>> pointVectors = new ArrayList<>();
        for (String pointString : pointStringArray) {
            pointVectors.add(toVector(pointString));
        }
        return pointVectors;
    }

    /**
     * what:    为给定的向量形式的坐标设置坐标值. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    private void setPoint(Vector<Double> pointVector, double x, double y) {
        pointVector.set(0, x);
        pointVector.set(1, y);
    }

    /**
     * what:    添加一个锚点，新加入的锚点放在锚点集合的最后位置. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void addAnchorPoint(double x, double y) {
        Vector<Double> pointVector = createPointVector();
        setPoint(pointVector, x, y);
        getAnchorPoints().add(pointVector);
    }

    /**
     * what:    创建向量，容积为2，增长为0，初始元素为2. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    private Vector<Double> createPointVector() {
        Vector<Double> pointVector = new Vector<>(2);
        pointVector.setSize(2);
        return pointVector;
    }

    /**
     * what:    添加一个锚点，新加入的锚点放在锚点集合的最后位置. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void addAnchorPoint(int index, double x, double y) {
        Vector<Double> pointVector = createPointVector();
        setPoint(pointVector, x, y);
        getAnchorPoints().add(index, pointVector);
    }

    /**
     * what:    更新一个锚点的坐标值. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void updateAnchorPoint(int index, double x, double y) {
        Vector<Double> pointVector = getAnchorPoints().get(index);
        setPoint(pointVector, x, y);
    }

    /**
     * what:    移除制定索引的锚点. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void removeAnchorPoint(int index) {
        getAnchorPoints().remove(index);
    }

    /**
     * what:    移除所有锚点. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void removeAll() {
        getAnchorPoints().clear();
    }

    public Vector<Double> getBasePoint() {
        return basePointVector;
    }

    public void setBasePoint(double x, double y) {
        this.basePointVector = createPointVector();
        setPoint(basePointVector, x, y);
    }

    /**
     * what:    获得所有向量形式的锚点的集合. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public List<Vector<Double>> getAnchorPoints() {
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
    public Vector<Double> getAnchorPoint(int index) {
        return getAnchorPoints().get(index);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }
}
