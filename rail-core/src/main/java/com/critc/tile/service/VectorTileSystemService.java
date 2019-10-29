/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.tile.service;

import com.critc.tile.modal.Coordinate;
import com.critc.tile.modal.CoordinateSystem;
import com.critc.tile.modal.Figure;
import com.critc.tile.modal.Projection;
import com.critc.tile.modal.VectorTileSystem;
import com.critc.tile.modal.View;
import org.springframework.stereotype.Service;

/**
 * what:    矢量瓦片工厂. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/29
 */
@Service
public class VectorTileSystemService {


    /**
     * what:    根据给定参数创建矢量瓦片系统. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/10/29
     */
    public VectorTileSystem createVectorTileSystem(Figure figure,
                                                   double viewWidth, double viewHeight,
                                                   int zoomLevel, int previousZoomLevel,
                                                   double figureCenterViewCoordinateDeltaX, double figureCenterViewCoordinateDeltaY,
                                                   double lockedViewCoordinateDeltaX, double lockedViewCoordinateDeltaY) {
        synchronized (this) {
            CoordinateSystem coordinateSystem = new CoordinateSystem();
            ////////根据请求参数构建请求方的显示环境////////
            //创建瓦片矢量系统对象
            VectorTileSystem vectorTileSystem = new VectorTileSystem();
            vectorTileSystem.setFigure(figure);
            vectorTileSystem.setViewCenterAsFigureCenter();

            //根据请求方显示数据，创建显示对象
            View view = new View(viewWidth, viewHeight);
            vectorTileSystem.setView(view);
            //设置显示方的缩放比例
            vectorTileSystem.setZoomLevel(zoomLevel);

            ////////////根据显示方现有参数计算视图中心的实际图坐标////////////
            //显示方当前缩放等级对应的透视图
            Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());
            //计算显示方实际图中心点的视图坐标
            double figureCenterViewCoordinateX = view.getBounds().getCenterCoordinate().getX() + figureCenterViewCoordinateDeltaX;
            double figureCenterViewCoordinateY = view.getBounds().getCenterCoordinate().getY() + figureCenterViewCoordinateDeltaY;
            //显示方前次缩放等级对应的透视图
            Projection previousProjection = vectorTileSystem.getProjection(previousZoomLevel);
            //如果视图中心点实际图坐标为实际图中心点时，计算缩放前显示方实际图中心点的视图坐标对应的实际图坐标
            Coordinate previousFigureCenterFigureCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, previousProjection, figure.getCenterCoordinate(), figureCenterViewCoordinateX, figureCenterViewCoordinateY);
            double previousViewCenterFigureCoordinateX = figure.getCenterCoordinate().getX() - (previousFigureCenterFigureCoordinate.getX() - figure.getCenterCoordinate().getX());
            double previousViewCenterFigureCoordinateY = figure.getCenterCoordinate().getY() - (previousFigureCenterFigureCoordinate.getY() - figure.getCenterCoordinate().getY());
            Coordinate previousViewCenterFigureCoordinate = new Coordinate(previousViewCenterFigureCoordinateX, previousViewCenterFigureCoordinateY);

            //显示方锁定锁定点的视图坐标
            double lockedViewCoordinateX = lockedViewCoordinateDeltaX + viewWidth / 2;
            double lockedViewCoordinateY = lockedViewCoordinateDeltaY + viewHeight / 2;

            //如果视图中心点对应的实际图坐标不变，计算显示方视图锁定点在前次缩放和本次缩放时对应的实际图坐标
            Coordinate previousLockedViewCoordinateFigureGCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, previousProjection, previousViewCenterFigureCoordinate, lockedViewCoordinateX, lockedViewCoordinateY);
            Coordinate lockedViewCoordinateFigureGCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, projection, previousViewCenterFigureCoordinate, lockedViewCoordinateX, lockedViewCoordinateY);

            //如果视图锁定点代表的实际图坐标不变，计算视图中心点对应的视图坐标
            double viewCenterFigureCoordinateX = previousViewCenterFigureCoordinate.getX() + previousLockedViewCoordinateFigureGCoordinate.getX() - lockedViewCoordinateFigureGCoordinate.getX();
            double viewCenterFigureCoordinateY = previousViewCenterFigureCoordinate.getY() + previousLockedViewCoordinateFigureGCoordinate.getY() - lockedViewCoordinateFigureGCoordinate.getY();
            vectorTileSystem.setViewCenterFigureCoordinate(new Coordinate(viewCenterFigureCoordinateX, viewCenterFigureCoordinateY));

            return vectorTileSystem;
        }
    }
}
