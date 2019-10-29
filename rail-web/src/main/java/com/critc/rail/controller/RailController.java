/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.controller;

import com.critc.rail.service.StationService;
import com.critc.rail.vo.LineView;
import com.critc.rail.vo.LineViewTiles;
import com.critc.rail.vo.NodeView;
import com.critc.rail.vo.NodeViewTiles;
import com.critc.rail.vo.TileViews;
import com.critc.tile.modal.Bounds;
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
import com.critc.util.cache.EhCacheUtil;
import com.critc.util.json.JsonUtil;
import com.critc.util.web.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

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
    /**
     * 定义日志输出位置
     */
    private Logger logger = LoggerFactory.getLogger("serviceLog");
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
    public void getNodeViewFeatures(@RequestParam(value = "zoom-level") Integer zoomLevel,
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

        FeaturesVo featuresVo = createFeaturesVo(vectorTileSystem);

        ModelMap modelMap = new ModelMap();
        modelMap.put("success", true);
        modelMap.put("data", featuresVo);
        String json = JsonUtil.toStr(modelMap);
        WebUtil.out(response, json);
    }

    private FeaturesVo createFeaturesVo(VectorTileSystem vectorTileSystem) {
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

        Map<String, TileViews> tiles = EhCacheUtil.get("railTileCache", "tileViewsMap");
        if (tiles == null) {
            init();
        }

        TileBounds tileBounds = coordinateSystem.viewBoundsToTileBounds(figure, view, projection, viewCenterFigureCoordinate, view.getBounds());
        for (Tile tile : tileBounds.getTiles()) {
            if (!tiles.containsKey(tile.getId())) {
                continue;
            }
            TileViews tileViews = tiles.get(tile.getId());
            for (NodeView nodeView : tileViews.getNodeViews()) {
                Callable<FeaturesVo> callable = () -> createFeature(vectorTileSystem, tileBounds, nodeView);
                // 创建FutureTask
                FutureTask<FeaturesVo> futureTask = new FutureTask<>(callable);
                // 添加到任务集合
                futureTasks.add(futureTask);
                // 将任务提交到线程池
                executorService.submit(futureTask);
            }
            for (LineView lineView : tileViews.getLineViews()) {
                Callable<FeaturesVo> callable = () -> createFeature(vectorTileSystem, tileBounds, lineView);
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

    private FeaturesVo createFeature(VectorTileSystem vectorTileSystem, TileBounds tileBounds, NodeView nodeView) {
        Figure figure = vectorTileSystem.getFigure();
        View view = vectorTileSystem.getView();
        int zoomLevel = vectorTileSystem.getZoomLevel();
        Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();
        Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());

        FeaturesVo featuresVo = new FeaturesVo();
        TileBounds nodeViewTileBounds = getTileBounds(figure, projection, nodeView);
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

    private TileBounds getTileBounds(Figure figure, Projection projection, NodeView nodeView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        double size = 10;
        Bounds bounds = new Bounds(nodeView.getX() - size, nodeView.getY() + size, nodeView.getX() + size, nodeView.getY() - size);

        return coordinateSystem.figureBoundsToTileBounds(figure, projection, bounds);
    }


    private FeaturesVo createFeature(VectorTileSystem vectorTileSystem, TileBounds tileBounds, LineView lineView) {
        Figure figure = vectorTileSystem.getFigure();
        View view = vectorTileSystem.getView();
        Coordinate viewCenterFigureCoordinate = vectorTileSystem.getViewCenterFigureCoordinate();
        Projection projection = vectorTileSystem.getProjection(vectorTileSystem.getZoomLevel());

        FeaturesVo featuresVo = new FeaturesVo();
        TileBounds lineViewTileBounds = getTileBounds(figure, projection, lineView);
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

    private TileBounds getTileBounds(Figure figure, Projection projection, LineView lineView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        Bounds bounds = new Bounds(Math.min(lineView.getSourceX(), lineView.getTargetX()),
                Math.max(lineView.getSourceY(), lineView.getTargetY()),
                Math.max(lineView.getSourceX(), lineView.getTargetX()),
                Math.min(lineView.getSourceY(), lineView.getTargetY()));

        return coordinateSystem.figureBoundsToTileBounds(figure, projection, bounds);
    }

    @PostConstruct
    public void init() {
        EhCacheUtil.put("railTileCache", "tileViewsMap", new HashMap<String, TileViews>());
        EhCacheUtil.put("railTileCache", "nodeViewsMap", new HashMap<String, NodeViewTiles>());
        EhCacheUtil.put("railTileCache", "lineViewsMap", new HashMap<String, LineViewTiles>());
        initNodeViewTiles();
        initLineViewTiles();
    }

    public void initNodeViewTiles() {
        VectorTileSystem vectorTileSystem = new VectorTileSystem();
        vectorTileSystem.setFigure(getFigure());

        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<NodeViewTiles>> futureTasks = new ArrayList<>();

        for (NodeView nodeView : getNodeViews()) {
            for (int zoomLevel = vectorTileSystem.getMinZoomLevel(); zoomLevel <= vectorTileSystem.getMaxZoomLevel(); zoomLevel++) {
                Projection projection = vectorTileSystem.getProjection(zoomLevel);
                // 声明任务
                // 生成nodeView对应features
                Callable<NodeViewTiles> callable = () -> {
                    NodeViewTiles nodeViewTiles = new NodeViewTiles();
                    nodeViewTiles.setNodeView(nodeView);
                    TileBounds nodeViewTileBounds = getTileBounds(figure, projection, nodeView);
                    nodeViewTiles.getTiles().addAll(nodeViewTileBounds.getTiles());
                    return nodeViewTiles;
                };
                // 创建FutureTask
                FutureTask<NodeViewTiles> futureTask = new FutureTask<>(callable);
                // 添加到任务集合
                futureTasks.add(futureTask);
                // 将任务提交到线程池
                executorService.submit(futureTask);
            }
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<NodeViewTiles> task : futureTasks) {
            try {
                NodeViewTiles nodeViewTiles = task.get();
                Map<String, NodeViewTiles> nodeViewMap = EhCacheUtil.get("railTileCache", "nodeViewsMap");
                nodeViewMap.put(nodeViewTiles.getNodeView().getId(), nodeViewTiles);
                for (Tile tile : nodeViewTiles.getTiles()) {
                    Map<String, TileViews> tileViewsMap = EhCacheUtil.get("railTileCache", "tileViewsMap");
                    TileViews tileViews = tileViewsMap.get(tile.getId());
                    if (tileViews == null) {
                        tileViews = new TileViews();
                        tileViews.setTile(tile);
                        tileViewsMap.put(tile.getId(), tileViews);
                    }
                    tileViews.getNodeViews().add(nodeViewTiles.getNodeView());
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: initNodeViewTiles");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: initNodeViewTiles");
                logger.error("异常信息: " + e.getMessage());
            }
        }
    }

    public void initLineViewTiles() {
        VectorTileSystem vectorTileSystem = new VectorTileSystem();
        vectorTileSystem.setFigure(getFigure());

        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<LineViewTiles>> futureTasks = new ArrayList<>();

        for (LineView lineView : getLineViews()) {
            for (int zoomLevel = vectorTileSystem.getMinZoomLevel(); zoomLevel <= vectorTileSystem.getMaxZoomLevel(); zoomLevel++) {
                Projection projection = vectorTileSystem.getProjection(zoomLevel);
                // 声明任务
                // 生成lineView对应features
                Callable<LineViewTiles> callable = () -> {
                    LineViewTiles lineViewTiles = new LineViewTiles();
                    lineViewTiles.setLineView(lineView);
                    TileBounds lineViewTileBounds = getTileBounds(figure, projection, lineView);
                    lineViewTiles.getTiles().addAll(lineViewTileBounds.getTiles());
                    return lineViewTiles;
                };
                // 创建FutureTask
                FutureTask<LineViewTiles> futureTask = new FutureTask<>(callable);
                // 添加到任务集合
                futureTasks.add(futureTask);
                // 将任务提交到线程池
                executorService.submit(futureTask);
            }
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<LineViewTiles> task : futureTasks) {
            try {
                LineViewTiles lineViewTiles = task.get();
                Map<String, LineViewTiles> lineViewMap = EhCacheUtil.get("railTileCache", "lineViewsMap");
                lineViewMap.put(lineViewTiles.getLineView().getId(), lineViewTiles);
                for (Tile tile : lineViewTiles.getTiles()) {
                    Map<String, TileViews> tileViewsMap = EhCacheUtil.get("railTileCache", "tileViewsMap");
                    TileViews tileViews = tileViewsMap.get(tile.getId());
                    if (tileViews == null) {
                        tileViews = new TileViews();
                        tileViews.setTile(tile);
                        tileViewsMap.put(tile.getId(), tileViews);
                    }
                    tileViews.getLineViews().add(lineViewTiles.getLineView());
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: initLineViewTiles");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: initLineViewTiles");
                logger.error("异常信息: " + e.getMessage());
            }
        }
    }

}
