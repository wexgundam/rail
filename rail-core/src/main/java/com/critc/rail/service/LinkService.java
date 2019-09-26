/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.dao.LinkDao;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * what:    节点间服务. <br/>
 * # 检测是否存在与给定的两个车站均邻接的节点间. <br/>
 * # 检测给定的节点间集合中存在与给定的两个车站均邻接的节点间
 * # 获得所有节点间. <br/>
 * # 获得与给定的两个车站均邻接的节点间. <br/>
 * # 获得与给定的车站邻接的节点间. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/18
 */
@Service
public class LinkService {
    /**
     * 定义日志输出位置
     */
    private Logger logger = LoggerFactory.getLogger("serviceLog");
    /**
     * 路网元素服务
     */
    @Autowired
    private RailNetworkElementService railNetworkElementService;
    /**
     * 数据获取Dao
     */
    @Autowired
    private LinkDao linkDao;

    /**
     * what:    检测是否存在与给定的两个车站均邻接的节点间. <br/>
     * 只要存在一个节点间连接给定的两个车站，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Station stationA, Station stationB) {
        return adjoin(getAll(), stationA, stationB);
    }

    /**
     * what:    检测给定的节点间集合中存在与给定的两个车站均邻接的节点间. <br/>
     * 只要存在一个节点间连接给定的两个车站，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(List<Link> links, Station stationA, Station stationB) {
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Boolean>> futureTasks = new ArrayList<>();

        for (Link link : links) {
            // 声明任务
            Callable<Boolean> callable = () -> {
                //检测车站A是否与节点间邻接
                boolean stationAAdjoinLink = railNetworkElementService.adjoin(stationA, link);
                //检测车站B是否与节点间邻接
                boolean stationBAdjoinLink = railNetworkElementService.adjoin(stationB, link);
                //返回并集结果
                return stationAAdjoinLink && stationBAdjoinLink;
            };
            // 创建FutureTask
            FutureTask<Boolean> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<Boolean> task : futureTasks) {
            try {
                if (task.get()) {
                    // 只要有link邻接两个车站，就认为两个车站邻接
                    return true;
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: adjoin");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: adjoin");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        //两个车站不邻接
        return false;
    }

    /**
     * what:    获得所有节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getAll() {
        return linkDao.getAll();
    }

    /**
     * what:    获得与给定的两个车站均邻接的节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(Station stationA, Station stationB) {
        // 与两个车站均邻接的节点间集合
        List<Link> adjoins = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        for (Link link : getAll()) {
            // 声明任务
            Callable<Link> callable = () -> {
                //检测车站A是否与节点间邻接
                boolean stationAAdjoinLink = railNetworkElementService.adjoin(stationA, link);
                //检测车站B是否与节点间邻接
                boolean stationBAdjoinLink = railNetworkElementService.adjoin(stationB, link);
                //返回并集结果
                return stationAAdjoinLink && stationBAdjoinLink ? link : null;
            };
            // 创建FutureTask
            FutureTask<Link> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<Link> task : futureTasks) {
            try {
                Link link = task.get();
                if (link != null) {
                    adjoins.add(link);
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getMany(Station stationA, Station stationB)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getMany(Station stationA, Station stationB)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return adjoins;
    }

    /**
     * what:    检测给定的节点间集合中存在与给定的两个车站均邻接的节点间. <br/>
     * 只要存在一个节点间连接给定的两个车站，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getAdjoins(Station station) {
        //与给定车站邻接的节点间集合
        List<Link> links = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        for (Link link : getAll()) {
            // 声明任务
            Callable<Link> callable = () -> railNetworkElementService.adjoin(station, link) ? link : null;
            // 创建FutureTask
            FutureTask<Link> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<Link> task : futureTasks) {
            try {
                if (task.get() != null) {
                    links.add(task.get());
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeportses");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeportses");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return links;
    }

    /**
     * what:    新增节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void addOne(Link link) {
        linkDao.addOne(link);
    }

    /**
     * what:    更新节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Link link) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    删除节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Link link) {
        throw new UnsupportedOperationException();
    }
}
