package com.critc.tile.modal;

/**
 * 瓦片边界，边界总是一个矩形，瓦片边界代表这个矩形内的所有瓦片
 * <p>
 * Created by Administrator on 2016/11/25.
 */
public class TileBounds {
    /**
     * 边界左上角瓦片
     */
    private Tile topLeftTile;
    /**
     * 边界右下角瓦片
     */
    private Tile bottomRightTile;
    /**
     * 缩放等级
     */
    private int zoomLevel;

    public TileBounds(int zoomLevel, Tile topLeftTile, Tile bottomRightTile) {
        this.topLeftTile = topLeftTile;
        this.bottomRightTile = bottomRightTile;
        this.zoomLevel = zoomLevel;
    }

    public Tile getTopLeftTile() {
        return topLeftTile;
    }

    public Tile getBottomRightTile() {
        return bottomRightTile;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }
}
