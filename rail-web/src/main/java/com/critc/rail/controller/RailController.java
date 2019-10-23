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

    @RequestMapping(value = "/features")
    public void getFeatures(@RequestParam(value = "zoom-level") Integer zoomLevel,
                            @RequestParam(value = "view-width") Double viewWidth,
                            @RequestParam(value = "view-height") Double viewHeight,
                            @RequestParam(value = "view-center-view-coordinate-delta-x") Double viewCenterViewCoordinateDeltaX,
                            @RequestParam(value = "view-center-view-coordinate-delta-y") Double viewCenterViewCoordinateDeltaY,
                            @RequestParam(value = "locked-view-coordinate-delta-x") Double lockedViewCoordinateDeltaX,
                            @RequestParam(value = "locked-view-coordinate-delta-y") Double lockedViewCoordinateDeltaY,
                            @RequestParam(value = "previous-zoom-level", required = false) Integer previousZoomLevel,
                            HttpServletResponse response) {
        View view = new View(viewWidth, viewHeight);
        VectorTileSystem vectorTileSystem = new VectorTileSystem();
        vectorTileSystem.setFigure(getFigure());
        vectorTileSystem.setView(view);
        vectorTileSystem.setZoomLevel(zoomLevel);
        vectorTileSystem.setViewCenterAsFigureCenter();
        Figure figure = vectorTileSystem.getFigure();
        Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());

        Coordinate coordinate0 = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, projection, figure.getCenterCoordinate(), view.getBounds().getCenterCoordinate().getX() + viewCenterViewCoordinateDeltaX, view.getBounds().getCenterCoordinate().getY() + viewCenterViewCoordinateDeltaY);

        vectorTileSystem.setViewCenterFigureCoordinate(new Coordinate(-coordinate0.getX(), -coordinate0.getY()));
        if (true) {
            double lockedViewCoordinateX = lockedViewCoordinateDeltaX + +viewWidth / 2;
            double lockedViewCoordinateY = lockedViewCoordinateDeltaY + +viewHeight / 2;


            Projection preViousProjection = vectorTileSystem.getProjection(previousZoomLevel);

            Coordinate coordinate01 = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, preViousProjection, figure.getCenterCoordinate(), view.getBounds().getCenterCoordinate().getX() + viewCenterViewCoordinateDeltaX, view.getBounds().getCenterCoordinate().getY() + viewCenterViewCoordinateDeltaY);
            vectorTileSystem.setViewCenterFigureCoordinate(new Coordinate(-coordinate01.getX(), -coordinate01.getY()));
            Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();

            Coordinate coordinate1 = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, preViousProjection, viewCenterFigureCoordinate, lockedViewCoordinateX, lockedViewCoordinateY);
            Coordinate coordinate2 = coordinateSystem.viewCoordinateToFigureCoordinate(figure, view, projection, viewCenterFigureCoordinate, lockedViewCoordinateX, lockedViewCoordinateY);
            vectorTileSystem.setViewCenterFigureCoordinate(new Coordinate(viewCenterFigureCoordinate.getX() + coordinate1.getX() - coordinate2.getX(), viewCenterFigureCoordinate.getY() + coordinate1.getY() - coordinate2.getY()));
        }
        Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();

        Coordinate coordinate5 = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, figure.getCenterCoordinate());

        FeaturesVo featuresVo = new FeaturesVo();
        featuresVo.setZoomLevel(vectorTileSystem.getZoomLevel());
        featuresVo.setViewCenterViewCoordinateDeltaX(coordinate5.getX() - view.getBounds().getCenterCoordinate().getX());
        featuresVo.setViewCenterViewCoordinateDeltaY(coordinate5.getY() - view.getBounds().getCenterCoordinate().getY());

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
//            featuresVo.getTexts().add(textVo);
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

//        for (LineView lineView : getLineViews()) {
//            TileBounds lineViewTileBounds = getTileBounds(figure, projection, lineView);
//            if (lineViewTileBounds.getTopLeftTile().getRow() > tileBounds.getBottomRightTile().getRow()
//                    || lineViewTileBounds.getBottomRightTile().getRow() < tileBounds.getTopLeftTile().getRow()
//                    || lineViewTileBounds.getTopLeftTile().getColumn() > tileBounds.getBottomRightTile().getColumn()
//                    || lineViewTileBounds.getBottomRightTile().getColumn() < tileBounds.getTopLeftTile().getColumn()
//                    ) {
//                continue;
//            }
//
//            LineStringVo lineStringVo = new LineStringVo();
//            Coordinate sourceViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, lineView.getSourceX(), lineView.getSourceY());
//            Coordinate targetViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, lineView.getTargetX(), lineView.getTargetY());
//            double[] xys = new double[]{sourceViewCoordinate.getX(), sourceViewCoordinate.getY(), targetViewCoordinate.getX(), targetViewCoordinate.getY()};
//            lineStringVo.setXys(xys);
//            featuresVo.getLineStrings().add(lineStringVo);
//        }

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

//    private FeaturesVo getFeatures(int zoomLevel) {
//        Projection projection = new Projection(figure.getBounds(), zoomLevel, tileSize);
//        GeometrySystem geometrySystem = new GeometrySystem();
//        TileBounds tileBounds = geometrySystem.getTileBounds(figure, projection, feature.getGeometry());
//        for (int column = tileBounds.getTopLeftTile().getColumn(); column <= tileBounds.getBottomRightTile().getColumn(); column++) {
//            for (int row = tileBounds.getTopLeftTile().getRow(); row <= tileBounds.getBottomRightTile().getRow(); row++) {
//                Tile tile = new Tile(zoomLevel, column, row);
//
//                featurePackage.getFeatureTiles(feature).getTiles().add(tile);
//                featurePackage.getTileFeatures(tile).getFeatures().add(feature);
//            }
//        }
//
//        FeaturesVo featuresVo = new FeaturesVo();
//        for (NodeView lineView : getNodeViews()) {
//            coordinateSystem.fi
//            PointVo pointVo = new PointVo();
//            pointVo.setX();
//        }
//        return null;
//    }


//    @RequestMapping(value = "/features")
//    public void getFeatures(HttpServletRequest request, HttpServletResponse response) {
//        FeaturesVo features = featureService.getFeatures(tileSize, minZoomLevel, maxZoomLevel);
//
//        ModelMap modelMap = new ModelMap();
//        modelMap.put("success", true);
//        modelMap.put("data", features);
//        String json = JsonUtil.toStr(modelMap);
//        WebUtil.out(response, json);
//
//        (function () {
//            $.ajax({
//                    type: 'GET',
//                    url: 'http://localhost:8092/rail/features.htm',
//                    dataType: 'json',
//                    success: function (result) {
//                if (result["success"]) {
//                    console.log(result);
//                }
//            }
//
//        });
//        })();
//    }
}
