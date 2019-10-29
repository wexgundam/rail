package com.critc.tile.modal;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 判断两个瓦片边界是否有交叉
     *
     * @param tileBounds
     *
     * @return
     */
    public boolean intersect(TileBounds tileBounds) {
        return !(this.getTopLeftTile().getRow() > tileBounds.getBottomRightTile().getRow()
                || this.getBottomRightTile().getRow() < tileBounds.getTopLeftTile().getRow()
                || this.getTopLeftTile().getColumn() > tileBounds.getBottomRightTile().getColumn()
                || this.getBottomRightTile().getColumn() < tileBounds.getTopLeftTile().getColumn());
    }

    /**
     * 获得瓦片边界包含的瓦片集合
     *
     * @return
     */
    public List<Tile> getTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (int column = topLeftTile.getColumn(); column < bottomRightTile.getColumn() + 1; column++) {
            for (int row = topLeftTile.getRow(); row < bottomRightTile.getRow() + 1; row++) {
                tiles.add(new Tile(zoomLevel, column, row));
            }
        }
        return tiles;
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
