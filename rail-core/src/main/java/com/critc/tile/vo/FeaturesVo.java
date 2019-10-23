package com.critc.tile.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    返回图形图像集合. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/11
 */
public class FeaturesVo {
    /**
     * 缩放等级
     */
    private int zoomLevel;
    /**
     * 最小缩放等级
     */
    private int minZoomLevel;
    /**
     * 最大缩放等级
     */
    private int maxZoomLevel;
    /**
     * 实际图中心坐标点的视图坐标在显示时的偏移量
     */
    private double figureCenterViewCoordinateDeltaX;
    /**
     * 实际图中心坐标点的视图坐标在显示时的偏移量
     */
    private double figureCenterViewCoordinateDeltaY;
    /**
     * 点类图形集合
     */
    private List<PointVo> points;
    /**
     * 文本类图形集合
     */
    private List<TextVo> texts;
    /**
     * 线串类图形集合
     */
    private List<LineStringVo> lineStrings;
    /**
     * 多边形类图形集合
     */
    private List<PolygonVo> polygonVos;

    public int getMinZoomLevel() {
        return minZoomLevel;
    }

    public void setMinZoomLevel(int minZoomLevel) {
        this.minZoomLevel = minZoomLevel;
    }

    public int getMaxZoomLevel() {
        return maxZoomLevel;
    }

    public void setMaxZoomLevel(int maxZoomLevel) {
        this.maxZoomLevel = maxZoomLevel;
    }

    public double getFigureCenterViewCoordinateDeltaX() {
        return figureCenterViewCoordinateDeltaX;
    }

    public void setFigureCenterViewCoordinateDeltaX(double figureCenterViewCoordinateDeltaX) {
        this.figureCenterViewCoordinateDeltaX = figureCenterViewCoordinateDeltaX;
    }

    public double getFigureCenterViewCoordinateDeltaY() {
        return figureCenterViewCoordinateDeltaY;
    }

    public void setFigureCenterViewCoordinateDeltaY(double figureCenterViewCoordinateDeltaY) {
        this.figureCenterViewCoordinateDeltaY = figureCenterViewCoordinateDeltaY;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public List<PointVo> getPoints() {
        points = points == null ? new ArrayList<>() : points;
        return points;
    }

    public void setPoints(List<PointVo> points) {
        this.points = points;
    }

    public List<LineStringVo> getLineStrings() {
        lineStrings = lineStrings == null ? new ArrayList<>() : lineStrings;
        return lineStrings;
    }

    public void setLineStrings(List<LineStringVo> lineStrings) {
        this.lineStrings = lineStrings;
    }

    public List<TextVo> getTexts() {
        texts = texts == null ? new ArrayList<>() : texts;
        return texts;
    }

    public void setTexts(List<TextVo> texts) {
        this.texts = texts;
    }

    public List<PolygonVo> getPolygonVos() {
        polygonVos = polygonVos == null ? new ArrayList<>() : polygonVos;
        return polygonVos;
    }

    public void setPolygonVos(List<PolygonVo> polygonVos) {
        this.polygonVos = polygonVos;
    }
}