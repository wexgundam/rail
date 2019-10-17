package com.critc.tile.modal;

/**
 * 投影图
 * <p>
 * 实际图按照zoomLevel投影后生成的图
 * <p>
 * 投影图有自己的坐标系统，简称为投影图坐标系
 * <p>
 * 投影坐标系的原点(0,0)在投影图左上角，对应实际图的左上角坐标
 * <p>
 * 投影图坐标系向右、向下为正
 * <p>
 * 投影图==实际图在zoomLevel时的视图全图
 * <p>
 * Created by Administrator on 2016/12/10.
 */
public class Projection {
    /**
     * 实际图边界
     */
    private Bounds figureBounds;
    /**
     * 实际图的缩放等级，决定投影图尺寸的主要元素之一
     */
    private int zoomLevel;
    /**
     * 瓦片图尺寸，决定投影图尺寸的主要元素之一
     */
    private double tileSize;

    public Projection(Bounds figureBounds, int zoomLevel, double tileSize) {
        this.figureBounds = figureBounds;
        this.zoomLevel = zoomLevel;
        this.tileSize = tileSize;
    }

    /**
     * 投影图尺寸
     */
    public double getSize() {
        return tileSize * Math.pow(2, zoomLevel);
    }

    /**
     * 当前分辨率：每像素代表的实际距离
     */
    public double getResolution() {
        return figureBounds.getSize() / getSize();
    }

    /**
     * 投影图的边界
     */
    public Bounds getBounds() {
        double size = getSize();
        return new Bounds(0, 0, size, size);
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public double getTileSize() {
        return tileSize;
    }
}

