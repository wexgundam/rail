/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.dao.YardDao;
import com.critc.rail.modal.AdjoinStationYard;
import com.critc.rail.modal.AdjoinYards;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.modal.Yard;
import com.critc.rail.vo.YardSearchVo;
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
 * what:    车场服务. <br/>
 * # 检测给定车场是否邻接给定车站. <br/>
 * # 检测给定车场是否邻接给定车场. <br/>
 * # 按条件查询一组车站. <br/>
 * # 按条件查询一个车站. <br/>
 * # 获取全路车场. <br/>
 * # 获取给定路局管辖的车场. <br/>
 * # 获取给定行车调度台管辖的车场. <br/>
 * # 获取给定车站管辖的车场. <br/>
 * # 获取给定车站的邻接车场. <br/>
 * # 获取给定节点间的邻接车场. <br/>
 * # 获取给定车场的邻接车场. <br/>
 * # 获取数量. <br/>
 * # 设置给定车场的管辖单位. <br/>
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
public class YardService {
    /**
     * 定义日志输出位置
     */
    private Logger logger = LoggerFactory.getLogger("serviceLog");
    /**
     * 车站服务
     */
    @Autowired
    private StationService stationService;
    /**
     * 节点间服务
     */
    @Autowired
    private LinkService linkService;
    /**
     * 车场数据获取对象
     */
    @Autowired
    private YardDao yardDao;

    /**
     * what:    检测给定车场是否邻接给定车站. <br/>
     * 只要存在一个节点间连接给定的两个车站，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Station station, Yard yard) {
        return linkService.adjoin(station, yard);
    }

    /**
     * what:    检测给定车场是否邻接给定车场. <br/>
     * 只要存在一个节点间连接给定的两个车场，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Yard yardA, Yard yardB) {
        // 检测是否存在与给定的两个车站均邻接的节点间
        return linkService.adjoin(yardA, yardB);
    }

    /**
     * what:    根据查询条件查询一组车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getMany(YardSearchVo yardSearchVo) {
        return yardDao.getMany(yardSearchVo);
    }

    /**
     * what:    根据查询条件查询一个车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Yard getOne(YardSearchVo yardSearchVo) {
        return yardDao.getOne(yardSearchVo);
    }

    /**
     * what:    根据id查询一个车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Yard getOne(int id) {
        YardSearchVo yardSearchVo = new YardSearchVo();
        yardSearchVo.setIdEqual(id);
        return getOne(yardSearchVo);
    }

    /**
     * what:    获取全路车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getAll() {
        YardSearchVo yardSearchVo = new YardSearchVo();
        return getMany(yardSearchVo);
    }

    /**
     * what:    获取给定路局管辖的车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getMany(Bureau bureau) {
        return getMany(stationService.getMany(bureau));

    }

    /**
     * what:    获取给定行车调度台管辖的车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getMany(TrainlineDeport trainlineDeport) {
        return getMany(stationService.getMany(trainlineDeport));
    }

    /**
     * what:    遍历给定车站集合，获得每个车站管辖的车场集合. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/27
     */
    private List<Yard> getMany(List<Station> stations) {
        //行车调度台管辖车场集合
        List<Yard> yards = new ArrayList<>();

        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<List<Yard>>> futureTasks = new ArrayList<>();

        //遍历集合
        for (Station station : stations) {
            // 声明任务
            Callable<List<Yard>> callable = () -> {
                //查询给定车站管辖的车场
                return getMany(station);
            };
            // 创建FutureTask
            FutureTask<List<Yard>> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<List<Yard>> task : futureTasks) {
            try {
                yards.addAll(task.get());
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getMany(List<Station> stations)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getMany(List<Station> stations)");
                logger.error("异常信息: " + e.getMessage());
            }
        }
        return yards;
    }

    /**
     * what:    获取给定车站管辖的车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getMany(Station station) {
        YardSearchVo yardSearchVo = new YardSearchVo();
        yardSearchVo.setJurisdictionStationIdEqual(station.getId());
        return getMany(yardSearchVo);
    }

    /**
     * what:    获得给定车场的邻接车场. <br/>
     * 根据与给定车场邻接的节点间，查询邻接车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<AdjoinStationYard> getAdjoinStationYards(Station station) {
        //邻接车站车场集合
        List<AdjoinStationYard> adjoinStationYards = new ArrayList<>();
        //获得给定车站邻接的节点间
        for (Link link : linkService.getMany(station)) {
            //获得与节点间邻接的车场
            AdjoinYards adjoinYards = getAdjoinYards(link);
            //生成邻接车站车场信息
            AdjoinStationYard adjoinStationYard = new AdjoinStationYard();
            adjoinStationYard.setStation(station);
            adjoinStationYard.setLink(link);
            adjoinStationYard.setYard(adjoinYards.getYardA());
            adjoinStationYards.add(adjoinStationYard);
        }

        return adjoinStationYards;
    }

    /**
     * what:    获取给定节点间的邻接车场. <br/>
     * 通过gridService实现. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public AdjoinYards getAdjoinYards(Link link) {
        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<Yard>> futureTasks = new ArrayList<>();

        for (Yard yard : getAll()) {
            // 声明任务
            Callable<Yard> callable = () -> {
                //查询给定路局的分界口
                return linkService.adjoin(yard, link) ? yard : null;
            };
            // 创建FutureTask
            FutureTask<Yard> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        //节点间邻接的车场A与车场B
        Yard yardA = null;
        Yard yardB = null;
        for (FutureTask<Yard> task : futureTasks) {
            try {
                Yard yard = task.get();
                if (yard != null) {
                    if (yardA == null) {
                        yardA = yard;
                        continue;
                    }
                    if (yardB == null) {
                        yardB = yard;
                        break;
                    }
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinYards(Link link)");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getAdjoinYards(Link link)");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        // 没有邻接车场时返回null
        if (yardA == null && yardB == null) {
            return null;
        } else {
            AdjoinYards adjoinYards = new AdjoinYards();
            adjoinYards.setLink(link);
            adjoinYards.setYardA(yardA);
            adjoinYards.setYardB(yardB);
            return adjoinYards;
        }
    }

    /**
     * what:    获取给定车场的邻接车场. <br/>
     * 获得yard邻接的节点间. <br/>
     * 获得每个节点间邻接的车场. <br/>
     * 返回结果. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<AdjoinYards> getAdjoinYards(Yard yard) {
        List<AdjoinYards> adjoinYardses = new ArrayList<>();
        //获得yard邻接的节点间
        for (Link link : linkService.getMany(yard)) {
            //获得每个节点间邻接的车场
            AdjoinYards adjoinYards = getAdjoinYards(link);
            //调换yardA和yardB的位置，使得yardA为给定的yard
            if (!yard.equals(adjoinYards.getYardA())) {
                adjoinYards.setYardB(adjoinYards.getYardA());
                adjoinYards.setYardA(yard);
            }
            adjoinYardses.add(adjoinYards);
        }
        return adjoinYardses;
    }

    /**
     * what:    获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(YardSearchVo yardSearchVo) {
        return yardDao.getCount(yardSearchVo);
    }

    /**
     * what:    设置给定车场的管辖单位. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void setJurisdiction(Yard yard) {
        //获取管辖站
        Station jurisdictionStation = stationService.getJurisdiction(yard);
        if (jurisdictionStation != null) {
            yard.setJurisdictionStationId(jurisdictionStation.getId());
        }
    }

    /**
     * what:    新增车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void addOne(Yard yard) {
        int id = yardDao.addOne(yard);
        yard.setId(id);
    }

    /**
     * what:    更新车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Yard yard) {
        yardDao.updateOne(yard);
    }

    /**
     * what:    删除车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Yard yard) {
        yardDao.deleteOne(yard);
    }
}
