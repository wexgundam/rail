package com.critc.tile.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 边界对像，描述一个二维矩形边界，topLeftX、y表示矩形的最小横纵坐标，width、height表示矩形的宽和高
 * <p>
 * 一旦创建不可修改
 * <p>
 * Created by Administrator on 2016/11/25.
 */
public class Bounds implements Cloneable {
    /**
     * 边界左上角坐标
     */
    private Coordinate topLeftCoordinate;
    /**
     * 边界右上角坐标
     */
    private Coordinate bottomRightCoordinate;

    public Bounds() {
    }

    public Bounds(double[] bounds) {
        this(bounds[0], bounds[1], bounds[2], bounds[3]);
    }

    public Bounds(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
        this(new Coordinate(topLeftX, topLeftY), new Coordinate(bottomRightX, bottomRightY));
    }

    public Bounds(Coordinate topLeftCoordinate, Coordinate bottomRightCoordinate) {
        this.topLeftCoordinate = topLeftCoordinate;
        this.bottomRightCoordinate = bottomRightCoordinate;
    }

    public Bounds(Double[] bounds) {
        this(bounds[0], bounds[1], bounds[2], bounds[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bounds bounds = (Bounds) o;

        if (!getTopLeftCoordinate().equals(bounds.getTopLeftCoordinate())) return false;
        return getBottomRightCoordinate().equals(bounds.getBottomRightCoordinate());

    }

    @Override
    public int hashCode() {
        int result = getTopLeftCoordinate().hashCode();
        result = 31 * result + getBottomRightCoordinate().hashCode();
        return result;
    }

    @Override
    protected Bounds clone() {
        Bounds bounds = new Bounds(this.getTopLeftCoordinate(), this.getBottomRightCoordinate());
        return bounds;
    }

    /**
     * 获取边界最大横向坐标
     *
     * @return
     */
    @JsonIgnore
    public double getMaxX() {
        return Math.max(getTopLeftCoordinate().getX(), getBottomRightCoordinate().getX());
    }

    /**
     * 获取边界最大纵向坐标
     *
     * @return
     */
    @JsonIgnore
    public double getMaxY() {
        return Math.max(getTopLeftCoordinate().getY(), getBottomRightCoordinate().getY());
    }

    /**
     * 获取边界最小横向坐标
     *
     * @return
     */
    @JsonIgnore
    public double getMinX() {
        return Math.min(getTopLeftCoordinate().getX(), getBottomRightCoordinate().getX());
    }

    /**
     * 获取边界最小纵向坐标
     *
     * @return
     */
    @JsonIgnore
    public double getMinY() {
        return Math.min(getTopLeftCoordinate().getY(), getBottomRightCoordinate().getY());
    }

    /**
     * 获得边界中心坐标
     *
     * @return
     */
    @JsonIgnore
    public Coordinate getCenterCoordinate() {
        return new Coordinate(getTopLeftCoordinate().getX() + getWidth() / 2,
                getTopLeftCoordinate().getY() + getHeight() / 2);
    }

    /**
     * 边界宽度
     *
     * @return
     */
    @JsonIgnore
    public double getWidth() {
        return getMaxX() - getMinX();
    }

    /**
     * 边界高度
     *
     * @return
     */
    @JsonIgnore
    public double getHeight() {
        return getMaxY() - getMinY();
    }

    /**
     * 获得边界的最大尺寸，即max(width,height)
     *
     * @return
     */
    @JsonIgnore
    public double getSize() {
        return Math.max(getWidth(), getHeight());
    }

    public Coordinate getTopLeftCoordinate() {
        topLeftCoordinate = topLeftCoordinate == null ? new Coordinate() : topLeftCoordinate;
        return topLeftCoordinate;
    }

    public void setTopLeftCoordinate(Coordinate topLeftCoordinate) {
        this.topLeftCoordinate = topLeftCoordinate;
    }

    public Coordinate getBottomRightCoordinate() {
        bottomRightCoordinate = bottomRightCoordinate == null ? new Coordinate() : bottomRightCoordinate;
        return bottomRightCoordinate;
    }

    public void setBottomRightCoordinate(Coordinate bottomRightCoordinate) {
        this.bottomRightCoordinate = bottomRightCoordinate;
    }
}
