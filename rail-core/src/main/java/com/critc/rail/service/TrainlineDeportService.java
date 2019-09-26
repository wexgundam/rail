/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.dao.TrainlineDeportDao;
import com.critc.rail.modal.AdjoinStations;
import com.critc.rail.modal.AdjoinTrainlineDeports;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.modal.Yard;
import com.critc.rail.vo.TrainlineDeportSearchVo;
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
 * what:    调度视角的行车调度台服务. <br/>
 * # 检测两个行车调度台是否邻接. <br/>
 * # 检测给定行车调度台是否管辖给定车站. <br/>
 * # 检测给定行车调度台是否邻接给定节点间. <br/>
 * # 检测给定行车调度台是否管辖给定车场. <br/>
 * # 按条件查询一组行车调度台. <br/>
 * # 按条件查询一个行车调度台. <br/>
 * # 获取全路行车调度台. <br/>
 * # 获取两个路局的邻接行车调度台. <br/>
 * # 获取给定路局所辖行车调度台. <br/>
 * # 获取给定行车调度台的邻接行车调度台. <br/>
 * # 获取给定车站的管辖行车调度台. <br/>
 * # 获取给定节点的管辖行车调度台. <br/>
 * # 获取给定车场的管辖行车调度台. <br/>
 * # 获取数量. <br/>
 * # 设置给定行车调度台的管辖单位. <br/>
 * # 新增行车调度台. <br/>
 * # 更新行车调度台. <br/>
 * # 删除行车调度台. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/12
 */
@Service
public class TrainlineDeportService {
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
     * 路局服务
     */
    @Autowired
    private BureauService bureauService;
    /**
     * 行车调度台数据获取服务
     */
    @Autowired
    private TrainlineDeportDao trainlineDeportDao;
    /**
     * 车站服务
     */
    @Autowired
    private StationService stationService;

    /**
     * what:    检测两个行车调度台是否邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(TrainlineDeport trainlineDeportA, TrainlineDeport trainlineDeportB) {
        return railNetworkElementService.adjoin(trainlineDeportA, trainlineDeportB);
    }

    /**
     * what:    检测给定行车调度台是否管辖给定车站. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean jurisdiction(TrainlineDeport trainlineDeport, Station station) {
        return railNetworkElementService.jurisdiction(trainlineDeport, station);
    }

    /**
     * what:    检测给定行车调度台是否管辖给定节点间. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean jurisdiction(TrainlineDeport trainlineDeport, Link link) {
        return railNetworkElementService.jurisdiction(trainlineDeport, link);
    }

    /**
     * what:    检测给定行车调度台是否管辖给定车场. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean jurisdiction(TrainlineDeport trainlineDeport, Yard yard) {
        return railNetworkElementService.jurisdiction(trainlineDeport, yard);
    }

    /**
     * what:    根据查询条件查询一组车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getMany(TrainlineDeportSearchVo trainlineDeportSearchVo) {
        return trainlineDeportDao.getMany(trainlineDeportSearchVo);
    }

    /**
     * what:    根据查询条件查询一个行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public TrainlineDeport getOne(TrainlineDeportSearchVo trainlineDeportSearchVo) {
        return trainlineDeportDao.getOne(trainlineDeportSearchVo);
    }

    /**
     * what:    根据id查询一个行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public TrainlineDeport getOne(int id) {
        TrainlineDeportSearchVo trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setIdEqual(id);
        return getOne(trainlineDeportSearchVo);
    }


    /**
     * what:    查询所有行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getAll() {
        return trainlineDeportDao.getAll();
    }

    /**
     * what:    获取给定行车调度台的邻接行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<AdjoinTrainlineDeports> getAdjoinTrainlineDeportses(Bureau bureauA, Bureau bureauB) {
        // 获取bureauA管辖的行调台
        List<TrainlineDeport> bureauATrainlineDeports = getMany(bureauA);
        // 获取bureauB管辖的行调台
        List<TrainlineDeport> bureauBTrainlineDeports = getMany(bureauB);

        //台邻接的行车调度台集合
        List<AdjoinTrainlineDeports> adjoinTrainlineDeportses = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<AdjoinTrainlineDeports>> futureTasks = new ArrayList<>();

        for (TrainlineDeport bureauATrainlineDeport : bureauATrainlineDeports) {
            // 声明任务
            Callable<AdjoinTrainlineDeports> callable = () -> {
                for (TrainlineDeport bureauBTrainlineDeport : bureauBTrainlineDeports) {
                    if (adjoin(bureauATrainlineDeport, bureauBTrainlineDeport)) {
                        return getAdjoinTrainlineDeports(bureauATrainlineDeport, bureauBTrainlineDeport);
                    }
                }
                return null;
            };
            // 创建FutureTask
            FutureTask<AdjoinTrainlineDeports> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<AdjoinTrainlineDeports> task : futureTasks) {
            try {
                if (task.get() != null) {
                    adjoinTrainlineDeportses.add(task.get());
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeportses(Bureau bureauA, Bureau bureauB)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeportses(Bureau bureauA, Bureau bureauB)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return adjoinTrainlineDeportses;
    }

    /**
     * what:    获取两个行车调度的邻接信息. <br/>
     * 如果邻接返回邻接对象，否则返回null. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    private AdjoinTrainlineDeports getAdjoinTrainlineDeports(TrainlineDeport trainlineDeportA, TrainlineDeport trainlineDeportB) {
        //生成邻接信息
        AdjoinTrainlineDeports adjoinTrainlineDeports = new AdjoinTrainlineDeports();
        adjoinTrainlineDeports.setTrainlineDeportA(trainlineDeportA);
        adjoinTrainlineDeports.setTrainlineDeportB(trainlineDeportB);

        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask> futureTasks = new ArrayList<>();

        //获取两个行调台管辖的车站集合
        List<Station> trainlineDeportAStations = stationService.getMany(trainlineDeportA);
        List<Station> trainlineDeportBStations = stationService.getMany(trainlineDeportB);

        //遍历行调台A管辖的车站集合
        for (Station trainlineDeportAStation : trainlineDeportAStations) {
            // 声明任务
            Runnable runnable = () -> {
                //遍历给定车站的邻接车站集合
                for (AdjoinStations adjoinStations : stationService.getAdjoinStationses(trainlineDeportAStation)) {
                    //如果行调台B管辖的车站集合包含邻接车站，则补充邻接行调台信息
                    if (trainlineDeportBStations.contains(adjoinStations.getStationB())) {
                        adjoinTrainlineDeports.setStationA(adjoinStations.getStationA());
                        adjoinTrainlineDeports.setLink(adjoinStations.getLink());
                        adjoinTrainlineDeports.setStationB(adjoinStations.getStationB());
                        return;
                    }
                }
            };
            // 创建FutureTask
            FutureTask futureTask = new FutureTask<>(runnable, null);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask task : futureTasks) {
            try {
                task.get();
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeports(TrainlineDeport trainlineDeportA, TrainlineDeport trainlineDeportB)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeports(TrainlineDeport trainlineDeportA, TrainlineDeport trainlineDeportB)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return adjoinTrainlineDeports;
    }

    /**
     * what:    获取给定路局管辖的行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getMany(Bureau bureau) {
        TrainlineDeportSearchVo trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setJurisdictionBureauIdEqual(bureau.getId());
        return getMany(trainlineDeportSearchVo);
    }

    /**
     * what:    获取给定行车调度台的邻接行车调度台. <br/>
     * 遍历所有行车调度台，如果邻接，则记录该行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<AdjoinTrainlineDeports> getAdjoinTrainlineDeportses(TrainlineDeport targetTrainlineDeport) {
        //与给定行车调度台邻接的行车调度台集合
        List<AdjoinTrainlineDeports> adjoinTrainlineDeportses = new ArrayList<>();
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<AdjoinTrainlineDeports>> futureTasks = new ArrayList<>();

        for (TrainlineDeport trainlineDeport : getAll()) {
            // 声明任务
            Callable<AdjoinTrainlineDeports> callable = () -> {
                if (adjoin(targetTrainlineDeport, trainlineDeport)) {
                    return getAdjoinTrainlineDeports(targetTrainlineDeport, trainlineDeport);
                }
                return null;
            };
            // 创建FutureTask
            FutureTask<AdjoinTrainlineDeports> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<AdjoinTrainlineDeports> task : futureTasks) {
            try {
                if (task.get() != null) {
                    adjoinTrainlineDeportses.add(task.get());
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeportses(TrainlineDeport targetTrainlineDeport)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinTrainlineDeportses(TrainlineDeport targetTrainlineDeport)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return adjoinTrainlineDeportses;

    }

    /**
     * what:    获取给定车站的管辖行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public TrainlineDeport getJurisdiction(Station station) {
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<TrainlineDeport>> futureTasks = new ArrayList<>();

        for (TrainlineDeport trainlineDeport : getAll()) {
            // 声明任务
            //查询给定路局的分界口
            Callable<TrainlineDeport> callable = () -> jurisdiction(trainlineDeport, station) ? trainlineDeport : null;
            // 创建FutureTask
            FutureTask<TrainlineDeport> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<TrainlineDeport> task : futureTasks) {
            try {
                TrainlineDeport trainlineDeport = task.get();
                if (trainlineDeport != null) {
                    return trainlineDeport;
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getJurisdiction");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getJurisdiction");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return null;
    }


    /**
     * what:    设置给定行调台的管辖局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void setJurisdictionBureau(TrainlineDeport trainlineDeport) {
        Bureau jurisdictionBureau = bureauService.getJurisdiction(trainlineDeport);
        if (jurisdictionBureau != null) {
            trainlineDeport.setJurisdictionBureauId(jurisdictionBureau.getId());
        }
    }

    /**
     * what:    新增行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public void addOne(TrainlineDeport trainlineDeport) {
        int id = trainlineDeportDao.addOne(trainlineDeport);
        trainlineDeport.setId(id);
    }

    /**
     * what:    更新行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public void updateOne(TrainlineDeport trainlineDeport) {
        trainlineDeportDao.updateOne(trainlineDeport);
    }


    /**
     * what:    删除行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public void deleteOne(TrainlineDeport trainlineDeport) {
        trainlineDeportDao.deleteOne(trainlineDeport);
    }
}
