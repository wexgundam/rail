package com.critc.tile.modal;

/**
 * 图片对象，代表所绘图形实际坐标系统，figure总是正方形
 * <p>
 * 该坐标系为笛卡尔坐标系，原点坐标为（0，0），x向右为正，y向上为正，
 * <p>
 * <p>
 * 瓦片坐标、视图坐标都应转换为图片对象的实际坐标系统进行计算
 * <p>
 * Created by Administrator on 2016/11/25.
 */
public class Figure {
    /**
     * Figure默认大小为40000*40000的图片
     */
    private double size = 40000;

    /**
     * figure的显示中心点，即初次展示figure时，应将figure的显示中心点设置到视图的中心点
     * <p>
     * 所有计算都会围绕着显示中心点开展
     * <p>
     * 默认中心点为原点（0，0）
     */
    private Coordinate centerCoordinate;

    /**
     * 实际图的边界
     */
    private Bounds bounds;


    /**
     * figure的边界
     * <p>
     * 默认为以（0，0）为坐标原点
     * <p>
     * x轴的范围为（-size/2，size/2）
     * <p>
     * y轴的范围为（-size/2，size/2）
     */
    public Bounds getBounds() {
        this.bounds = this.bounds == null ? new Bounds(-size / 2, size / 2, size / 2, -size / 2) : this.bounds;
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public Coordinate getCenterCoordinate() {
        if (centerCoordinate == null) {
            centerCoordinate = new Coordinate(0, 0);
        }
        return centerCoordinate;
    }

    public void setCenterCoordinate(Coordinate centerCoordinate) {
        this.centerCoordinate = centerCoordinate;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
