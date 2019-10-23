/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.controller;

import com.critc.rail.service.StationService;
import com.critc.rail.vo.LineView;
import com.critc.rail.vo.NodeView;
import com.critc.tile.modal.Bounds;
import com.critc.tile.modal.Coordinate;
import com.critc.tile.modal.CoordinateSystem;
import com.critc.tile.modal.Figure;
import com.critc.tile.modal.Projection;
import com.critc.tile.modal.TileBounds;
import com.critc.tile.modal.VectorTileSystem;
import com.critc.tile.modal.View;
import com.critc.tile.vo.FeaturesVo;
import com.critc.tile.vo.LineStringVo;
import com.critc.tile.vo.PointVo;
import com.critc.tile.vo.TextVo;
import com.critc.util.json.JsonUtil;
import com.critc.util.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/11
 */
@RequestMapping("/rail")
@Controller
public class RailController {
    @Autowired
    private StationService stationService;
    CoordinateSystem coordinateSystem = new CoordinateSystem();
    NodeView[] nodeViews;
    LineView[] lineViews;
    Figure figure;

    private NodeView[] getNodeViews() {
        if (nodeViews == null) {
            try {
                InputStream inputStream = NodeView.class.getResourceAsStream("nodeViews.json");
                byte[] bytes = new byte[0];
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String json = new String(bytes, "utf-8");
                nodeViews = JsonUtil.toObject(json, NodeView[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodeViews;
    }

    private LineView[] getLineViews() {
        if (lineViews == null) {
            try {
                InputStream inputStream = LineView.class.getResourceAsStream("lineViews.json");
                byte[] bytes = new byte[0];
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String json = new String(bytes);
                lineViews = JsonUtil.toObject(json, LineView[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineViews;
    }

    private Figure getFigure() {
        if (figure == null) {
            figure = new Figure();
        }
        return figure;
    }

    /**
     * what:    根据用户请求，返回对应的图形对象. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/10/23
     */
    @RequestMapping(value = "/features")
    public void getFeatures(@RequestParam(value = "zoom-level") Integer zoomLevel,
                            @RequestParam(value = "view-width") Double viewWidth,
                            @RequestParam(value = "view-height") Double viewHeight,
                            @RequestParam(value = "figure-center-view-coordinate-delta-x") Double figureCenterViewCoordinateDeltaX,
                            @RequestParam(value = "figure-center-view-coordinate-delta-y") Double figureCenterViewCoordinateDeltaY,
                            @RequestParam(value = "locked-view-coordinate-delta-x") Double lockedViewCoordinateDeltaX,
                            @RequestParam(value = "locked-view-coordinate-delta-y") Double lockedViewCoordinateDeltaY,
                            @RequestParam(value = "previous-zoom-level", required = false) Integer previousZoomLevel,
                            HttpServletResponse response) {

        ////////根据请求参数构建请求方的显示环境////////

        //创建瓦片矢量系统对象
        VectorTileSystem vectorTileSystem = new VectorTileSystem();
        vectorTileSystem.setFigure(getFigure());
        vectorTileSystem.setViewCenterAsFigureCenter();

        //根据请求方显示数据，创建显示对象
        View view = new View(viewWidth, viewHeight);
        vectorTileSystem.setView(view);
        //设置显示方的缩放比例
        vectorTileSystem.setZoomLevel(zoomLevel);
        Figure figure = vectorTileSystem.getFigure();

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

        //计算实际图中心点的视图图坐标
        Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();
        Coordinate figureCenterViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, figure.getCenterCoordinate());

        //生成返回的数据包
        FeaturesVo featuresVo = new FeaturesVo();
        featuresVo.setZoomLevel(vectorTileSystem.getZoomLevel());
        featuresVo.setMinZoomLevel(vectorTileSystem.getMinZoomLevel());
        featuresVo.setMaxZoomLevel(vectorTileSystem.getMaxZoomLevel());
        featuresVo.setFigureCenterViewCoordinateDeltaX(figureCenterViewCoordinate.getX() - view.getBounds().getCenterCoordinate().getX());
        featuresVo.setFigureCenterViewCoordinateDeltaY(figureCenterViewCoordinate.getY() - view.getBounds().getCenterCoordinate().getY());

        //计算当前视图的瓦片范围
        TileBounds tileBounds = coordinateSystem.viewBoundsToTileBounds(figure, view, projection, viewCenterFigureCoordinate, view.getBounds());

        for (NodeView nodeView : getNodeViews()) {
            TileBounds nodeViewTileBounds = getTileBounds(figure, projection, nodeView);
            if (nodeViewTileBounds.getTopLeftTile().getRow() > tileBounds.getBottomRightTile().getRow()
                    || nodeViewTileBounds.getBottomRightTile().getRow() < tileBounds.getTopLeftTile().getRow()
                    || nodeViewTileBounds.getTopLeftTile().getColumn() > tileBounds.getBottomRightTile().getColumn()
                    || nodeViewTileBounds.getBottomRightTile().getColumn() < tileBounds.getTopLeftTile().getColumn()
                    ) {
                continue;
            }
            Coordinate viewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, nodeView.getX(), nodeView.getY());
            PointVo pointVo = new PointVo();
            pointVo.setX(viewCoordinate.getX());
            pointVo.setY(viewCoordinate.getY());
            featuresVo.getPoints().add(pointVo);

            TextVo textVo = new TextVo();
            textVo.setText(nodeView.getName());
            textVo.setX(viewCoordinate.getX() + 5);
            textVo.setY(viewCoordinate.getY());
            featuresVo.getTexts().add(textVo);
        }

        PointVo pointVo = new PointVo();
        Coordinate centerCoordinate = view.getBounds().getCenterCoordinate();
        pointVo.setX(centerCoordinate.getX());
        pointVo.setY(centerCoordinate.getY());
        featuresVo.getPoints().add(pointVo);
        TextVo textVo = new TextVo();
        textVo.setText("视图中心");
        textVo.setX(pointVo.getX() + 5);
        textVo.setY(pointVo.getY());
        featuresVo.getTexts().add(textVo);

        pointVo = new PointVo();
        pointVo.setX(449);
        pointVo.setY(427);
        featuresVo.getPoints().add(pointVo);
        textVo = new TextVo();
        textVo.setText("锁定");
        textVo.setX(pointVo.getX() + 5);
        textVo.setY(pointVo.getY());
        featuresVo.getTexts().add(textVo);

        for (LineView lineView : getLineViews()) {
            TileBounds lineViewTileBounds = getTileBounds(figure, projection, lineView);
            if (lineViewTileBounds.getTopLeftTile().getRow() > tileBounds.getBottomRightTile().getRow()
                    || lineViewTileBounds.getBottomRightTile().getRow() < tileBounds.getTopLeftTile().getRow()
                    || lineViewTileBounds.getTopLeftTile().getColumn() > tileBounds.getBottomRightTile().getColumn()
                    || lineViewTileBounds.getBottomRightTile().getColumn() < tileBounds.getTopLeftTile().getColumn()
                    ) {
                continue;
            }

            LineStringVo lineStringVo = new LineStringVo();
            Coordinate sourceViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, lineView.getSourceX(), lineView.getSourceY());
            Coordinate targetViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, lineView.getTargetX(), lineView.getTargetY());
            double[] xys = new double[]{sourceViewCoordinate.getX(), sourceViewCoordinate.getY(), targetViewCoordinate.getX(), targetViewCoordinate.getY()};
            lineStringVo.setXys(xys);
            featuresVo.getLineStrings().add(lineStringVo);
        }

        ModelMap modelMap = new ModelMap();
        modelMap.put("success", true);
        modelMap.put("data", featuresVo);
        String json = JsonUtil.toStr(modelMap);
        WebUtil.out(response, json);
    }

    public TileBounds getTileBounds(Figure figure, Projection projection, NodeView nodeView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        double size = 10;
        Bounds bounds = new Bounds(nodeView.getX() - size, nodeView.getY() + size, nodeView.getX() + size, nodeView.getY() - size);

        return coordinateSystem.figureBoundsToTileBounds(figure, projection, bounds);
    }

    public TileBounds getTileBounds(Figure figure, Projection projection, LineView lineView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        Bounds bounds = new Bounds(Math.min(lineView.getSourceX(), lineView.getTargetX()),
                Math.max(lineView.getSourceY(), lineView.getTargetY()),
                Math.max(lineView.getSourceX(), lineView.getTargetX()),
                Math.min(lineView.getSourceY(), lineView.getTargetY()));

        return coordinateSystem.figureBoundsToTileBounds(figure, projection, bounds);
    }
}
