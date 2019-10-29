/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.dao.LinkDao;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.Yard;
import com.critc.rail.vo.LinkSearchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
 * # 检测是否存在与给定车站和给定车场邻接的节点间. <br/>
 * # 检测是否存在与给定的两个车场均邻接的节点间. <br/>
 * # 检测给定车站是否邻接给定节点间. <br/>
 * # 检测给定车场是否邻接给定节点间. <br/>
 * # 按条件查询一组节点间. <br/>
 * # 按条件查询一个节点间. <br/>
 * # 按Id查询一个节点间. <br/>
 * # 获得所有节点间. <br/>
 * # 获得与两个给定车站邻接的节点间. <br/>
 * # 获得与给定车站和给定车场邻接的节点间. <br/>
 * # 获得与两个给定车场邻接的节点间. <br/>
 * # 获得与给定车站邻接的节点间. <br/>
 * # 获得与给定车场邻接的节点间. <br/>
 * # 获取数量. <br/>
 * # 新增车场. <br/>
 * # 更新车场. <br/>
 * # 删除车场. <br/>
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
        //获得车站A的邻接节点间
        for (Link link : getMany(stationA)) {
            //如果某节点间与车站B邻接，返回true
            if (adjoin(stationB, link)) {
                return true;
            }
        }
        return false;
    }

    /**
     * what:    检测是否存在与给定车站和给定车场邻接的节点间. <br/>
     * 只要存在一个节点间连接给定的车站和车场，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Station station, Yard yard) {
        //获得车站A的邻接节点间
        for (Link link : getMany(station)) {
            //如果某节点间与车场邻接，返回true
            if (adjoin(yard, link)) {
                return true;
            }
        }
        return false;
    }


    /**
     * what:    检测是否存在与给定的两个车场均邻接的节点间. <br/>
     * 只要存在一个节点间连接给定的两个车场，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Yard yardA, Yard yardB) {
        //获得车站A的邻接节点间
        for (Link link : getMany(yardA)) {
            //如果某节点间与车场B邻接，返回true
            if (adjoin(yardB, link)) {
                return true;
            }
        }
        return false;
    }

    /**
     * what:    检测给定车站是否邻接给定节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/16
     */
    public boolean adjoin(Station station, Link link) {
        return railNetworkElementService.adjoin(station, link);
    }

    /**
     * what:    检测给定车场是否邻接给定节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Yard yard, Link link) {
        return railNetworkElementService.adjoin(yard, link);
    }

    /**
     * what:    根据查询条件查询一组节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(LinkSearchVo linkSearchVo) {
        return linkDao.getMany(linkSearchVo);
    }

    /**
     * what:    根据查询条件查询一个节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Link getOne(LinkSearchVo linkSearchVo) {
        return linkDao.getOne(linkSearchVo);
    }

    /**
     * what:    根据id查询一个节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Link getOne(int id) {
        LinkSearchVo linkSearchVo = new LinkSearchVo();
        linkSearchVo.setIdEqual(id);
        return getOne(linkSearchVo);
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
        LinkSearchVo linkSearchVo = new LinkSearchVo();
        return getMany(linkSearchVo);
    }

    /**
     * what:    获得与两个给定车站邻接的节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(Station stationA, Station stationB) {
        // 与两个车站均邻接的节点间集合
        List<Link> links = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        //获得与车站A邻接的节点间
        for (Link link : getMany(stationA)) {
            // 声明任务
            Callable<Link> callable = () -> {
                //检测车站B是否与节点间邻接，如果邻接，返回节点间
                return adjoin(stationB, link) ? link : null;
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
                    links.add(link);
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

        return links;
    }

    /**
     * what:    获得与给定车站和给定车场邻接的节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(Station station, Yard yard) {
        // 与车站车场均邻接的节点间集合
        List<Link> links = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        //获得与车站邻接的节点间
        for (Link link : getMany(station)) {
            // 声明任务
            Callable<Link> callable = () -> {
                //检测车场是否与节点间邻接，如果邻接，返回节点间
                return adjoin(yard, link) ? link : null;
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
                    links.add(link);
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法:  getMany(Station station, Yard yard)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法:  getMany(Station station, Yard yard)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return links;
    }

    /**
     * what:    获得与两个给定车场邻接的节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(Yard yardA, Yard yardB) {
        // 与两个车场均邻接的节点间集合
        List<Link> links = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        //获得与车场A邻接的节点间
        for (Link link : getMany(yardA)) {
            // 声明任务
            Callable<Link> callable = () -> {
                //检测车场B是否与节点间邻接，如果邻接，返回节点间
                return adjoin(yardB, link) ? link : null;
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
                    links.add(link);
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法:  getMany(Yard yardA, Yard yardB)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法:  getMany(Yard yardA, Yard yardB)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return links;
    }

    /**
     * what:    获得与给定车站邻接的节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(Station station) {
        //与给定车站邻接的节点间集合
        List<Link> links = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        for (Link link : getAll()) {
            // 声明任务
            Callable<Link> callable = () -> adjoin(station, link) ? link : null;
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
                logger.error("任务方法: getMany(Station station)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getMany(Station station)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return links;
    }

    /**
     * what:    获得与给定车场邻接的节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(Yard yard) {
        //与给定车场邻接的节点间集合
        List<Link> links = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        // 任务集合
        List<FutureTask<Link>> futureTasks = new ArrayList<>();

        for (Link link : getAll()) {
            // 声明任务
            Callable<Link> callable = () -> adjoin(yard, link) ? link : null;
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
                logger.error("任务方法: getMany(Yard yard)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getMany(Yard yard)");
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
        int id = linkDao.addOne(link);
        link.setId(id);
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
        linkDao.updateOne(link);
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
        linkDao.deleteOne(link);
    }
}
