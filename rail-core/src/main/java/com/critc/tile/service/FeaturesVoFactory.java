/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.tile.service;

import com.critc.rail.vo.LineView;
import com.critc.rail.vo.NodeView;
import com.critc.rail.vo.TileViews;
import com.critc.tile.modal.Coordinate;
import com.critc.tile.modal.CoordinateSystem;
import com.critc.tile.modal.Figure;
import com.critc.tile.modal.Projection;
import com.critc.tile.modal.Tile;
import com.critc.tile.modal.TileBounds;
import com.critc.tile.modal.VectorTileSystem;
import com.critc.tile.modal.View;
import com.critc.tile.vo.FeaturesVo;
import com.critc.tile.vo.LineStringVo;
import com.critc.tile.vo.PointVo;
import com.critc.tile.vo.TextVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * what:    根据NodeView、LineView创建对应的Feature. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/29
 */
public class FeaturesVoFactory {
    /**
     * 定义日志输出位置
     */
    private Logger logger = LoggerFactory.getLogger("serviceLog");

    public FeaturesVo createFeaturesVo(VectorTileSystem vectorTileSystem, ViewService viewService) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        View view = vectorTileSystem.getView();
        Figure figure = vectorTileSystem.getFigure();
        Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());
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


        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<FeaturesVo>> futureTasks = new ArrayList<>();

        TileBounds tileBounds = coordinateSystem.viewBoundsToTileBounds(figure, view, projection, viewCenterFigureCoordinate, view.getBounds());
        for (Tile tile : tileBounds.getTiles()) {
            Map<String, TileViews> tileViewsMap = viewService.getTileViewsMap();
            if (!tileViewsMap.containsKey(tile.getId())) {
                continue;
            }
            TileViews tileViews = tileViewsMap.get(tile.getId());
            for (NodeView nodeView : tileViews.getNodeViews()) {
                Callable<FeaturesVo> callable = () -> createFeature(vectorTileSystem, viewService, tileBounds, nodeView);
                // 创建FutureTask
                FutureTask<FeaturesVo> futureTask = new FutureTask<>(callable);
                // 添加到任务集合
                futureTasks.add(futureTask);
                // 将任务提交到线程池
                executorService.submit(futureTask);
            }
            for (LineView lineView : tileViews.getLineViews()) {
                Callable<FeaturesVo> callable = () -> createFeature(vectorTileSystem, viewService, tileBounds, lineView);
                // 创建FutureTask
                FutureTask<FeaturesVo> futureTask = new FutureTask<>(callable);
                // 添加到任务集合
                futureTasks.add(futureTask);
                // 将任务提交到线程池
                executorService.submit(futureTask);
            }
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<FeaturesVo> task : futureTasks) {
            try {
                featuresVo.merge(task.get());
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: createFeaturesVo");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: createFeaturesVo");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return featuresVo;
    }

    /**
     * 创建NodeView对应的Feature
     *
     * @param vectorTileSystem
     * @param tileBounds
     * @param nodeView
     *
     * @return
     */
    private FeaturesVo createFeature(VectorTileSystem vectorTileSystem, ViewService viewService, TileBounds tileBounds, NodeView nodeView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        Figure figure = vectorTileSystem.getFigure();
        View view = vectorTileSystem.getView();
        int zoomLevel = vectorTileSystem.getZoomLevel();
        Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();
        Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());

        FeaturesVo featuresVo = new FeaturesVo();
        TileBounds nodeViewTileBounds = viewService.getTileBounds(figure, projection, nodeView);
        if (nodeViewTileBounds.intersect(tileBounds)) {
            if (zoomLevel > 2) {
                Coordinate viewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, nodeView.getX(), nodeView.getY());
                PointVo pointVo = new PointVo();
                pointVo.setX(viewCoordinate.getX());
                pointVo.setY(viewCoordinate.getY());
                featuresVo.getPoints().add(pointVo);
                if (zoomLevel > 5) {
                    TextVo textVo = new TextVo();
                    textVo.setText(nodeView.getName());
                    textVo.setX(viewCoordinate.getX() + 5);
                    textVo.setY(viewCoordinate.getY());
                    featuresVo.getTexts().add(textVo);
                }
            }
        }

        return featuresVo;
    }

    /**
     * 创建LineView对应的Feature
     *
     * @param vectorTileSystem
     * @param viewService
     * @param tileBounds
     * @param lineView
     *
     * @return
     */
    private FeaturesVo createFeature(VectorTileSystem vectorTileSystem, ViewService viewService, TileBounds tileBounds, LineView lineView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        Figure figure = vectorTileSystem.getFigure();
        View view = vectorTileSystem.getView();
        Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();
        Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());

        FeaturesVo featuresVo = new FeaturesVo();
        TileBounds lineViewTileBounds = viewService.getTileBounds(figure, projection, lineView);
        if (lineViewTileBounds.intersect(tileBounds)) {
            LineStringVo lineStringVo = new LineStringVo();
            Coordinate sourceViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, lineView.getSourceX(), lineView.getSourceY());
            Coordinate targetViewCoordinate = coordinateSystem.figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, lineView.getTargetX(), lineView.getTargetY());
            double[] xys = new double[]{sourceViewCoordinate.getX(), sourceViewCoordinate.getY(), targetViewCoordinate.getX(), targetViewCoordinate.getY()};
            lineStringVo.setXys(xys);
            featuresVo.getLineStrings().add(lineStringVo);
        }
        return featuresVo;
    }


}
