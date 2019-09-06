/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.network.modal;

import java.util.Vector;

/**
 * what:    坐标的向量表示. <br/>
 * 用向量Vector表示坐标，用容积为2，增长率0，初始为2的的Vector存储坐标的值，vector[0]代表X，vector[1]代表Y。 <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/5
 */
public class PointVector {
    /**
     * 存储坐标值得vector
     */
    private Vector<Double> vector;

    public PointVector() {
        this.vector = new Vector<>(2);
        this.vector.setSize(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointVector that = (PointVector) o;

        return vector.equals(that.vector);
    }

    @Override
    public int hashCode() {
        return vector.hashCode();
    }

    @Override
    public String toString() {
        return "PointVector{" +
                "vector=" + vector +
                '}';
    }

    /**
     * what:    设置坐标值.<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void setPoint(double x, double y) {
        this.vector.set(0, x);
        this.vector.set(1, y);
    }

    /**
     * what:  设置X坐标值.<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void setPointX(double x) {
        this.vector.set(0, x);
    }

    /**
     * what:    返回x轴坐标值. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/5
     */
    public double getPointX() {
        return this.vector.get(0);
    }

    /**
     * what:  设置Y坐标值.<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void setPointY(double y) {
        this.vector.set(1, y);
    }

    /**
     * what:    返回y轴坐标值. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/5
     */
    public double getPointY() {
        return this.vector.get(1);
    }


    public Vector<Double> getVector() {
        return vector;
    }

    public void setVector(Vector<Double> vector) {
        this.vector = vector;
    }
}
