package com.critc.tile.modal;

import java.awt.image.BufferedImage;

/**
 * 示意图
 * <p>
 * 通过painter绘制features
 * <p>
 * view是对视图的描述
 */
public class VectorTileSystem {
    /**
     * 实际图片
     */
    private Figure figure;
    /**
     * 视图
     */
    private View view;
    /**
     * 缩放等级，默认为0
     */
    private Integer zoomLevel = 0;
    /**
     * 最小缩放等级，默认为0
     */
    private Integer minZoomLevel = 0;
    /**
     * 最大缩放等级，默认为10
     */
    private Integer maxZoomLevel = 10;
    /**
     * 瓦片尺寸，默认为256×256像素
     */
    private double tileSize = 256;
    /**
     * 视图中心对应的实际图坐标
     * <p>
     * 默认视图中心坐标为实际图显示中心坐标
     */
    private Coordinate viewCenterFigureCoordinate;

    /**
     * 根据缩放等级获得都应的透视图
     *
     * @param zoomLevel
     *
     * @return
     */
    public Projection getProjection(int zoomLevel) {
        Projection projection = new Projection(this.figure.getBounds(), zoomLevel, tileSize);
        return projection;
    }

    /**
     * 锁定视图中心，放大一级
     */
    public void zoomIn() {
        zoom(this.zoomLevel + 1);
    }

    /**
     * 锁定视图中心，缩小一级
     */
    public void zoomOut() {
        zoom(this.zoomLevel - 1);
    }

    /**
     * 锁定视图中心，缩放到指定等级
     *
     * @param zoomLevel
     */
    public void zoom(int zoomLevel) {
        zoom(zoomLevel, view.getBounds().getCenterCoordinate());
    }

    /**
     * 缩放到给定的zoomLevel，并且给定的视图坐标在缩放前后代表同一实际图坐标
     *
     * @param zoomLevel
     * @param viewCooridnate
     */
    public void zoom(int zoomLevel, Coordinate viewCooridnate) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        //获得缩放前投影图
        Projection beforeZoomProjection = getProjection(zoomLevel);
        //获得视图坐标对应的实际图坐标
        Coordinate lockedFigureCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view,
                beforeZoomProjection, viewCenterFigureCoordinate, viewCooridnate);

        //设置缩放等级
        setZoomLevel(zoomLevel);

        // 获得缩放后的投影图
        Projection afterZoomProjection = getProjection(zoomLevel);

        // 计算视图中心对应的实际图坐标
        double afterZoomProjectionResolution = afterZoomProjection.getResolution();
        Coordinate viewCenterCoordinate = view.getBounds().getCenterCoordinate();
        double viewCenterFigureCoordinateX = lockedFigureCoordinate.getX() - viewCooridnate.getX() * afterZoomProjectionResolution + viewCenterCoordinate.getX() * afterZoomProjectionResolution;
        double viewCenterFigureCoordinateY = lockedFigureCoordinate.getY() + viewCooridnate.getY() * afterZoomProjectionResolution - viewCenterCoordinate.getY() * afterZoomProjectionResolution;
        this.viewCenterFigureCoordinate = new Coordinate(viewCenterFigureCoordinateX, viewCenterFigureCoordinateY);
    }


    /**
     * 缩放到给定的zoomLevel，并且给定的视图坐标在缩放前后代表同一实际图坐标
     *
     * @param zoomLevel
     * @param viewCooridnate
     */
    public void zoom(int previousZoomLevel, int zoomLevel, Coordinate viewCooridnate) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        //获得缩放前投影图
        Projection beforeZoomProjection = getProjection(previousZoomLevel);
        //获得视图坐标对应的实际图坐标
        Coordinate lockedFigureCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view,
                beforeZoomProjection, viewCenterFigureCoordinate, viewCooridnate);

        //设置缩放等级
        setZoomLevel(zoomLevel);

        // 获得缩放后的投影图
        Projection afterZoomProjection = getProjection(zoomLevel);

        // 计算视图中心对应的实际图坐标
        double afterZoomProjectionResolution = afterZoomProjection.getResolution();
        Coordinate viewCenterCoordinate = view.getBounds().getCenterCoordinate();
        double viewCenterFigureCoordinateX = lockedFigureCoordinate.getX() - viewCooridnate.getX() * afterZoomProjectionResolution + viewCenterCoordinate.getX() * afterZoomProjectionResolution;
        double viewCenterFigureCoordinateY = lockedFigureCoordinate.getY() + viewCooridnate.getY() * afterZoomProjectionResolution - viewCenterCoordinate.getY() * afterZoomProjectionResolution;
        this.viewCenterFigureCoordinate = new Coordinate(viewCenterFigureCoordinateX, viewCenterFigureCoordinateY);
    }

    /**
     * 缩放到给定的zoomLevel，并且给定的视图坐标在缩放前后代表同一实际图坐标
     *
     * @param zoomLevel
     * @param viewCooridnate
     */
    public void lock(int zoomLevel, Coordinate viewCooridnate) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        //获得缩放前投影图
        Projection beforeZoomProjection = getProjection(this.zoomLevel);
        //获得视图坐标对应的实际图坐标
        Coordinate lockedFigureCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view,
                beforeZoomProjection, viewCenterFigureCoordinate, viewCooridnate);

        //设置缩放等级
        setZoomLevel(zoomLevel);

        // 获得缩放后的投影图
        Projection afterZoomProjection = getProjection(this.zoomLevel);

        // 计算视图中心对应的实际图坐标
        double afterZoomProjectionResolution = afterZoomProjection.getResolution();
        Coordinate viewCenterCoordinate = view.getBounds().getCenterCoordinate();
        double viewCenterFigureCoordinateX = lockedFigureCoordinate.getX() - viewCooridnate.getX() * afterZoomProjectionResolution + viewCenterCoordinate.getX() * afterZoomProjectionResolution;
        double viewCenterFigureCoordinateY = lockedFigureCoordinate.getY() + viewCooridnate.getY() * afterZoomProjectionResolution - viewCenterCoordinate.getY() * afterZoomProjectionResolution;
        this.viewCenterFigureCoordinate = new Coordinate(viewCenterFigureCoordinateX, viewCenterFigureCoordinateY);
    }


    /**
     * 移动视图坐标当前中心坐标，使该坐标相对于移动前变更了（deltaX,deltaY）增量值
     *
     * @param deltaX
     * @param deltaY
     */
    public void moveViewCenterCoordinateDeltaViewCoordinate(double deltaX, double deltaY) {
        Projection projection = getProjection(this.zoomLevel);
        Coordinate viewCenterCoordinate = view.getBounds().getCenterCoordinate();

        CoordinateSystem coordinateSystem = new CoordinateSystem();
        Coordinate beforeMoveViewCenterProjectionCoordinate = coordinateSystem.viewCoordinateToProjectionCoordinate(
                figure, view, projection, viewCenterFigureCoordinate, viewCenterCoordinate);

        double x = beforeMoveViewCenterProjectionCoordinate.getX() - deltaX;
        double y = beforeMoveViewCenterProjectionCoordinate.getY() - deltaY;

        Coordinate afterMoveViewCenterProjectionCoordinate = new Coordinate(x, y);
        Coordinate afterMoveViewCenterFigureCoordinate = coordinateSystem.projectionCoordinateToFigureCoordinate(figure,
                projection, afterMoveViewCenterProjectionCoordinate);

        setViewCenterFigureCoordinate(afterMoveViewCenterFigureCoordinate);
    }

    /**
     * 设置视图中心为实际图显示中心
     */
    public void setViewCenterAsFigureCenter() {
        this.viewCenterFigureCoordinate = figure.getCenterCoordinate();
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Integer getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(Integer zoomLevel) {
        this.zoomLevel = Math.max(Math.min(maxZoomLevel, zoomLevel), minZoomLevel);
    }

    public Integer getMinZoomLevel() {
        return minZoomLevel;
    }

    public void setMinZoomLevel(Integer minZoomLevel) {
        this.minZoomLevel = minZoomLevel;
    }

    public Integer getMaxZoomLevel() {
        return maxZoomLevel;
    }

    public void setMaxZoomLevel(Integer maxZoomLevel) {
        this.maxZoomLevel = maxZoomLevel;
    }

    public double getTileSize() {
        return tileSize;
    }

    public void setTileSize(double tileSize) {
        this.tileSize = tileSize;
    }

    public Coordinate getViewCenterFigureCoordinate() {
        if (viewCenterFigureCoordinate == null) {
            setViewCenterAsFigureCenter();
        }
        return viewCenterFigureCoordinate;
    }

    public void setViewCenterFigureCoordinate(Coordinate viewCenterFigureCoordinate) {
        this.viewCenterFigureCoordinate = viewCenterFigureCoordinate;
    }
}
