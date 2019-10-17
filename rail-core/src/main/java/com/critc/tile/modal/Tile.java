package com.critc.tile.modal;

/**
 * 瓦片坐标系中代表瓦片的索引。
 * <p>
 * 为了看优化figure的显示效率，将figure切分为若干个瓦片，视图控件只绘制但前显示范围内涉及的瓦片，
 * <p>
 * 通过减少数据传输与绘制，提高显示性能。
 * <p>
 * figure的缩放等级采用金字塔形设计，计算公式为tileCount = Math.pow(4,zoomLevel)
 * <p>
 * 瓦片系统主要描述瓦片的索引，figure映射为瓦片系统后，从左上角开始横向向右，纵向向左为正
 * <p>
 * 默认瓦片size为256×256
 * <p>
 * Created by Administrator on 2016/11/25.
 */
public class Tile {
    private static final String SEPARATOR = "/";

    public static String createId(int zoomLevel, int column, int row) {
        return zoomLevel + SEPARATOR + column + SEPARATOR + row;
    }

    /**
     * 根据tileId创建Tile
     *
     * @param tileId
     * @return
     */
    public static Tile createTile(String tileId) {
        String[] split = tileId.split(SEPARATOR);
        return createTile(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    /**
     * 创建Tile
     *
     * @param zoomLevel
     * @param column
     * @param row
     * @return
     */
    public static Tile createTile(int zoomLevel, int column, int row) {
        return new Tile(zoomLevel, column, row);
    }

    /**
     * tile的横向索引
     */
    private int column;
    /**
     * tile的纵向索引
     */
    private int row;
    /**
     * tile的缩放等级
     */
    private int zoomLevel;

    public Tile() {
    }

    public Tile(int zoomLevel, int column, int row) {
        this.column = column;
        this.row = row;
        this.zoomLevel = zoomLevel;
    }

    /**
     * 生成tile id
     *
     * @return
     */
    public String getId() {
        return createId(zoomLevel, column, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (getColumn() != tile.getColumn()) return false;
        if (getRow() != tile.getRow()) return false;
        return getZoomLevel() == tile.getZoomLevel();

    }

    @Override
    public int hashCode() {
        int result = getColumn();
        result = 31 * result + getRow();
        result = 31 * result + getZoomLevel();
        return result;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

}
