/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.tile.service;

import com.critc.rail.vo.LineView;
import com.critc.rail.vo.LineViewTiles;
import com.critc.rail.vo.NodeView;
import com.critc.rail.vo.NodeViewTiles;
import com.critc.rail.vo.TileViews;
import com.critc.tile.modal.Bounds;
import com.critc.tile.modal.CoordinateSystem;
import com.critc.tile.modal.Figure;
import com.critc.tile.modal.Projection;
import com.critc.tile.modal.Tile;
import com.critc.tile.modal.TileBounds;
import com.critc.tile.modal.VectorTileSystem;
import com.critc.util.cache.EhCacheUtil;
import com.critc.util.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
 * @author 靳磊 created on 2019/10/29
 */
@Service
public class ViewService {
    /**
     * 定义日志输出位置
     */
    private Logger logger = LoggerFactory.getLogger("serviceLog");

    NodeView[] nodeViews;
    LineView[] lineViews;
    private Figure figure;
    private Object tileViewsMap;

    public Figure getFigure() {
        if (figure == null) {
            figure = new Figure();
        }
        return figure;
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

    public Map<String, TileViews> getTileViewsMap() {
        Map<String, TileViews> tileViewsMap = EhCacheUtil.get("railTileCache", "tileViewsMap");
        if (tileViewsMap == null) {
            init();
        }
        return EhCacheUtil.get("railTileCache", "tileViewsMap");
    }

    /**
     * 计算NodeView在给定透视图的边界
     *
     * @param figure
     * @param projection
     * @param nodeView
     *
     * @return
     */
    public TileBounds getTileBounds(Figure figure, Projection projection, NodeView nodeView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        double size = 10;
        Bounds bounds = new Bounds(nodeView.getX() - size, nodeView.getY() + size, nodeView.getX() + size, nodeView.getY() - size);

        return coordinateSystem.figureBoundsToTileBounds(figure, projection, bounds);
    }

    /**
     * 计算LineView在给定透视图的边界
     *
     * @param figure
     * @param projection
     * @param lineView
     *
     * @return
     */
    public TileBounds getTileBounds(Figure figure, Projection projection, LineView lineView) {
        CoordinateSystem coordinateSystem = new CoordinateSystem();

        Bounds bounds = new Bounds(Math.min(lineView.getSourceX(), lineView.getTargetX()),
                Math.max(lineView.getSourceY(), lineView.getTargetY()),
                Math.max(lineView.getSourceX(), lineView.getTargetX()),
                Math.min(lineView.getSourceY(), lineView.getTargetY()));

        return coordinateSystem.figureBoundsToTileBounds(figure, projection, bounds);
    }
}
