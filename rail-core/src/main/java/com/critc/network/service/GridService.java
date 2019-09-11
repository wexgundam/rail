/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.network.service;

import com.critc.network.modal.Grid;
import com.critc.network.modal.PointVector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * what:    网格服务. <br/>
 * 基于JTS提供的spatial提供以下服务：<br/>
 * 1. 检测网格A是否包含网格B.<br/>
 * 2. 检测网格A是否与网格B相邻.<br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/6
 */
@Service
public class GridService {
    /**
     * 定义日志输出位置
     */
    private static Logger logger = LoggerFactory.getLogger("serviceLog");

    /**
     * 声明JTS的GeometryFactory
     */
    private GeometryFactory geometryFactory;

    public GridService() {
        //初始化JTS的GeometryFactory
        geometryFactory = new GeometryFactory();
    }

    /**
     * what: 根据网格锚点坐标生成对应的几何图形. <br/>
     * 1.生成的图形为JTS的Polygon.<br/>
     * 2.使用坐标数组方法生成Polygon.<br/>
     * 3.Polygon要求至少包含4个坐标点，且第一个点和最后一个点坐标必须相同.<br/>
     * 4.如果锚点数为0，创建empty polygon
     * 5.如果锚点数为1个或2个，不够的坐标数用第一锚点补足
     * 6.如果锚点数超过2个，第一个锚点要作为第一个和作为最后一个坐标
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/9
     */
    private Geometry createAnchorGeometry(Grid grid) {
        // 获取锚点向量个数
        int anchorPointVectorsSize = grid.getAnchorPointVectors().size();

        if (anchorPointVectorsSize == 0) {
            // 如果锚点数为0，创建empty polygon
            return geometryFactory.createPolygon();
        } else if (anchorPointVectorsSize == 1 || anchorPointVectorsSize == 2) {
            //如果锚点数为1个或2个，不够的坐标数用第一锚点补足

            // 构建包含4个元素的坐标数组
            Coordinate[] coordinates = new Coordinate[4];

            //遍历锚点生产数组坐标
            for (int index = 0; index < 4; index++) {
                if (index < anchorPointVectorsSize) {
                    PointVector pointVector = grid.getAnchorPointVector(index);
                    Coordinate coordinate = new Coordinate(pointVector.getPointX(), pointVector.getPointY());
                    coordinates[index] = coordinate;
                } else {
                    //不够的坐标数用第一锚点补足
                    PointVector pointVector = grid.getAnchorPointVector(0);
                    Coordinate coordinate = new Coordinate(pointVector.getPointX(), pointVector.getPointY());
                    coordinates[index] = coordinate;
                }
            }

            Polygon polygon = geometryFactory.createPolygon(coordinates);
            return polygon;
        } else {
            // 构建坐标点数组，数组元素较锚点元素多一个，最后一个坐标为第一个锚点的坐标
            Coordinate[] coordinates = new Coordinate[anchorPointVectorsSize + 1];
            for (int index = 0; index < anchorPointVectorsSize; index++) {
                PointVector pointVector = grid.getAnchorPointVector(index);
                Coordinate coordinate = new Coordinate(pointVector.getPointX(), pointVector.getPointY());
                coordinates[index] = coordinate;
            }

            // 最后一个坐标为第一个锚点的坐标
            PointVector pointVector = grid.getAnchorPointVector(0);
            Coordinate coordinate = new Coordinate(pointVector.getPointX(), pointVector.getPointY());
            coordinates[anchorPointVectorsSize] = coordinate;

            Polygon polygon = geometryFactory.createPolygon(coordinates);
            return polygon;
        }
    }

    /**
     * what:    测试网格A是否包含网格B. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/9
     */
    public boolean contains(Grid gridA, Grid gridB) {
        Geometry gridAAnchorGeometry = createAnchorGeometry(gridA);
        Geometry gridBAnchorGeometry = createAnchorGeometry(gridB);
        return gridAAnchorGeometry.contains(gridBAnchorGeometry);
    }

    /**
     * what:    测试网格A是否与网格B相邻. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/9
     */
    public boolean touches(Grid gridA, Grid gridB) {
        Geometry gridAAnchorGeometry = createAnchorGeometry(gridA);
        Geometry gridBAnchorGeometry = createAnchorGeometry(gridB);
        return gridAAnchorGeometry.touches(gridBAnchorGeometry);
    }

    /**
     * what:    遍历网格集合，返回第一个包含目标网格的网格. <br/>
     * 如果没有这样的网格，则返回Null<br/>
     * 遍历方式采用线程池<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/9
     */
    public Grid getGridContainsTargetGrid(List<Grid> grids, Grid targetGrid) {
        return executorService(grids, targetGrid, new GridContainsTaskFacatory());
    }

    /**
     * what:    遍历网格集合，返回第一个与目标网格相切的网格. <br/>
     * 如果没有这样的网格，则返回Null<br/>
     * 遍历方式采用线程池<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/9
     */
    public Grid getGridTouchesTargetGrid(List<Grid> grids, Grid targetGrid) {
        return executorService(grids, targetGrid, new GridTouchesTaskFacatory());
    }

    /**
     * what:    遍历网格集合，返回第一个与目标网格“相运算”的网格. <br/>
     * 如果没有这样的网格，则返回Null<br/>
     * 遍历方式采用线程池<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/9
     */
    private Grid executorService(List<Grid> grids, Grid targetGrid, IGridServiceTaskFactory gridServiceTaskFactory) {
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<Grid>> futureTasks = new ArrayList<>();

        for (Grid grid : grids) {
            // 声明任务
            Callable<Grid> callable = gridServiceTaskFactory.createTask(grid, targetGrid);
            // 创建FutureTask
            FutureTask<Grid> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<Grid> task : futureTasks) {
            try {
                Grid grid = task.get();
                if (grid != null) {
                    return grid;
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("GridService.getGridContainsTargetGrid()异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("GridService.getGridContainsTargetGrid()异常信息: " + e.getMessage());
            }
        }

        return null;
    }

    /**
     * 两个网格运算TaskFactory，用于生成线程池的任务
     */
    interface IGridServiceTaskFactory {
        Callable<Grid> createTask(Grid gridA, Grid gridB);
    }

    /**
     * 两个网格包含运算TaskFactory
     */
    class GridContainsTaskFacatory implements IGridServiceTaskFactory {

        @Override
        public Callable<Grid> createTask(Grid gridA, Grid gridB) {
            return new Callable<Grid>() {
                @Override
                public Grid call() throws Exception {
                    // Grid对应的Geometry
                    Geometry gridAGeometry = createAnchorGeometry(gridA);
                    // Target Grid对应的Geometry
                    Geometry gridBGeometry = createAnchorGeometry(gridB);
                    // 如果包含，返回Grid；否则返回null；
                    return gridAGeometry.contains(gridBGeometry) ? gridA : null;
                }
            };
        }
    }

    /**
     * 两个网格相切运算TaskFactory
     */
    class GridTouchesTaskFacatory implements IGridServiceTaskFactory {
        @Override
        public Callable<Grid> createTask(Grid gridA, Grid gridB) {
            return new Callable<Grid>() {
                @Override
                public Grid call() throws Exception {
                    // Grid对应的Geometry
                    Geometry gridAGeometry = createAnchorGeometry(gridA);
                    // Target Grid对应的Geometry
                    Geometry gridBGeometry = createAnchorGeometry(gridB);
                    // 如果相切，返回Grid；否则返回null；
                    return gridAGeometry.touches(gridBGeometry) ? gridA : null;
                }
            };
        }
    }
}
