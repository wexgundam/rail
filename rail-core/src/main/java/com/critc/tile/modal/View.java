package com.critc.tile.modal;

/**
 * View代表视图坐标系，描述了视图的范围，figure显示中心点的视图坐标，缩放等级等内容
 * <p>
 * 视图坐标系横轴X向右为正，纵轴Y向下为正
 * <p>
 * 默认显示范围为视图控件（0，0）到视图控件尺寸范围
 * <p>
 * <p>
 * Created by Administrator on 2016/11/25.
 */
public class View {
    /**
     * 视图宽
     */
    private double width;
    /**
     * 视图高
     */
    private double height;

    public View(Double width, Double height) {
        this.width = width;
        this.height = height;
    }

    public Bounds getBounds() {
        return new Bounds(0, 0, width, height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
