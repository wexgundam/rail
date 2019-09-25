/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:rail
 */
package com.critc.rail.service;

import com.critc.rail.dao.StationDao;
import com.critc.rail.modal.AdjoinStations;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.BureauPartingStation;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.modal.Yard;
import com.critc.rail.vo.StationSearchVo;
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
 * what:    调度视角的车站服务. <br/>
 * # 检测两个车站是否邻接. <br/>
 * # 检测给定车站是否管辖给定车场. <br/>
 * # 检测给定车站是否邻接给定节点间. <br/
 * # 按条件查询一组车站. <br/>
 * # 按条件查询一个车站. <br/>
 * # 获取全路车站. <br/>
 * # 获取全路的路局分界口车站. <br/>
 * # 获取两个路局的路局分界口车站. <br/>
 * # 获取给定路局所辖车站. <br/>
 * # 获取给定路局所辖路局分界口车站. <br/>
 * # 获取给定行车调度台所辖车站. <br/>
 * # 获取给定车站的邻接车站. <br/>
 * # 获得给定节点间的邻接车站. <br/>
 * # 获得数量. <br/>
 * # 设置给定车站的管辖信息. <br/>
 * # 新增车站. <br/>
 * # 更新车站. <br/>
 * # 删除车站. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/12
 */
@Service
public class StationService {
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
     * 行车调度台服务
     */
    @Autowired
    private TrainlineDeportService trainlineDeportService;
    /**
     * 车站数据获取对象
     */
    @Autowired
    private StationDao stationDao;
    /**
     * 节点间服务
     */
    @Autowired
    private LinkService linkService;

    /**
     * what:    检测两个车站是否邻接. <br/>
     * 只要存在一个节点间连接给定的两个车站，即视为这两个车站邻接. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Station stationA, Station stationB) {
        // 检测是否存在与给定的两个车站均邻接的节点间
        // 只要有link同时邻接两个车站，就认为两个车站邻接
        return linkService.adjoin(stationA, stationB);
    }

    /**
     * what:    检测给定车站是否管辖给定车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/16
     */
    public boolean jurisdiction(Station station, Yard yard) {
        return railNetworkElementService.jurisdiction(station, yard);
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
     * what:    根据查询条件查询一组车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getMany(StationSearchVo stationSearchVo) {
        return stationDao.getMany(stationSearchVo);
    }

    /**
     * what:    根据查询条件查询一个车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Station getOne(StationSearchVo stationSearchVo) {
        return stationDao.getOne(stationSearchVo);
    }

    /**
     * what:    根据id查询一个车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Station getOne(int id) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setIdEqual(id);
        return getOne(stationSearchVo);
    }

    /**
     * what:    获取全路车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getAll() {
        StationSearchVo stationSearchVo = new StationSearchVo();
        return getMany(stationSearchVo);
    }

    /**
     * what:    获取全路的路局分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<BureauPartingStation> getBureauPartingStations() {
        List<BureauPartingStation> bureauPartingStations = new ArrayList<>();

        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<List<BureauPartingStation>>> futureTasks = new ArrayList<>();

        for (Bureau bureau : bureauService.getAll()) {
            // 声明任务
            Callable<List<BureauPartingStation>> callable = () -> {
                //查询给定路局的分界口
                return getBureauPartingStations(bureau);
            };
            // 创建FutureTask
            FutureTask<List<BureauPartingStation>> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        for (FutureTask<List<BureauPartingStation>> task : futureTasks) {
            try {
                bureauPartingStations.addAll(task.get());
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getBureauPartingStations");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getBureauPartingStations");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        return bureauPartingStations;

    }

    /**
     * what:    获取两个路局的路局分界口车站. <br/>
     * 即获取两个路局的邻接车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<BureauPartingStation> getBureauPartingStations(Bureau bureauA, Bureau bureauB) {
        List<BureauPartingStation> bureauPartingStations = new ArrayList<>();
        //遍历bureauA管辖的分界口
        for (BureauPartingStation bureauPartingStation : getBureauPartingStations(bureauA)) {
            //如果邻接局为bureauB，则添加到集合中
            if (bureauPartingStation.getAdjoinBureau().equals(bureauB)) {
                bureauPartingStations.add(bureauPartingStation);
            }
        }
        //遍历bureauB管辖的分界口
        for (BureauPartingStation bureauPartingStation : getBureauPartingStations(bureauB)) {
            //如果邻接局为bureauA，则添加到集合中
            if (bureauPartingStation.getAdjoinBureau().equals(bureauA)) {
                bureauPartingStations.add(bureauPartingStation);
            }
        }

        return bureauPartingStations;
    }

    /**
     * what:    获取给定路局管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getMany(Bureau bureau) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionBureauIdEqual(bureau.getId());
        return getMany(stationSearchVo);
    }

    /**
     * what:    获取给定路局管辖的路局分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<BureauPartingStation> getBureauPartingStations(Bureau bureau) {
        List<BureauPartingStation> bureauPartingStations = new ArrayList<>();

        // 查询Bureau管辖的分界口
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setBureauPartingEqual(true);
        stationSearchVo.setJurisdictionBureauIdEqual(bureau.getId());

        //遍历分界口车站
        for (Station station : stationDao.getMany(stationSearchVo)) {
            // 获取管辖局
            Bureau jurisdictionBureau = bureauService.getOne(station.getJurisdictionBureauId());
            // 获取邻接车站
            for (AdjoinStations adjoinStations : getAdjoinStationses(station)) {
                Bureau adjoinBureau = bureauService.getOne(adjoinStations.getStationB().getJurisdictionBureauId());
                //如果分界口与邻接站管辖局不一致，则记录邻接局信息
                if (!jurisdictionBureau.equals(adjoinBureau)) {
                    BureauPartingStation bureauPartingStation = new BureauPartingStation();
                    bureauPartingStation.setBureauPartingStation(station);
                    bureauPartingStation.setJurisdictionBureau(jurisdictionBureau);
                    bureauPartingStation.setAdjoinLink(adjoinStations.getLink());
                    bureauPartingStation.setAdjoinBureau(adjoinBureau);
                    bureauPartingStation.setAdjoinStation(adjoinStations.getStationB());
                    bureauPartingStations.add(bureauPartingStation);
                }
            }
        }
        return bureauPartingStations;
    }

    /**
     * what:    获取给定行车调度台管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getMany(TrainlineDeport trainlineDeport) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionTdIdEqual(trainlineDeport.getId());
        return getMany(stationSearchVo);
    }

    /**
     * what:    获得给定车站的邻接车站. <br/>
     * 根据与给定车站邻接的节点间，查询邻接车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<AdjoinStations> getAdjoinStationses(Station station) {
        //邻接车站集合
        List<AdjoinStations> adjoinStationses = new ArrayList<>();
        //获得给定车站邻接的节点间
        for (Link link : linkService.getAdjoins(station)) {
            AdjoinStations adjoinStations = getAdjoinStations(link);
            //调换stationA和stationB的位置，使得stationA为给定的station
            if (!adjoinStations.getStationA().equals(station)) {
                adjoinStations.setStationB(adjoinStations.getStationA());
                adjoinStations.setStationA(station);
            }
            adjoinStationses.add(adjoinStations);
        }

        return adjoinStationses;
    }

    /**
     * what:    获得给定节点间的邻接车站. <br/>
     * 通过gridService实现. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public AdjoinStations getAdjoinStations(Link link) {

        // 声明固定线程池。后期根据性能优化
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 任务集合
        List<FutureTask<Station>> futureTasks = new ArrayList<>();

        for (Station station : getAll()) {
            // 声明任务
            Callable<Station> callable = () -> {
                //查询给定路局的分界口
                return adjoin(station, link) ? station : null;
            };
            // 创建FutureTask
            FutureTask<Station> futureTask = new FutureTask<>(callable);
            // 添加到任务集合
            futureTasks.add(futureTask);
            // 将任务提交到线程池
            executorService.submit(futureTask);
        }

        // 软性关闭线程池，开始执行线程
        executorService.shutdown();

        // 返回结果
        //节点间邻接的车站A与车站B
        Station stationA = null;
        Station stationB = null;
        for (FutureTask<Station> task : futureTasks) {
            try {
                Station station = task.get();
                if (station != null) {
                    if (stationA == null) {
                        stationA = station;
                        continue;
                    }
                    if (stationB == null) {
                        stationB = station;
                        break;
                    }
                }
            } catch (InterruptedException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getBureauPartingStations");
                logger.error("异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("任务类: " + getClass().getSimpleName());
                logger.error("任务方法: getBureauPartingStations");
                logger.error("异常信息: " + e.getMessage());
            }
        }

        // 没有邻接车站时返回null
        if (stationA == null && stationB == null) {
            return null;
        } else {
            AdjoinStations adjoinStations = new AdjoinStations();
            adjoinStations.setLink(link);
            adjoinStations.setStationA(stationA);
            adjoinStations.setStationB(stationB);
            return adjoinStations;
        }
    }

    /**
     * what:    获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(StationSearchVo stationSearchVo) {
        return stationDao.getCount(stationSearchVo);
    }

    /**
     * what:    设置给定车站的管辖单位. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void setJurisdiction(Station station) {
        //获取管辖局
        Bureau jurisdictionBureau = bureauService.getJurisdiction(station);
        if (jurisdictionBureau != null) {
            station.setJurisdictionBureauId(jurisdictionBureau.getId());
        }
        //获取行车调度台
        TrainlineDeport trainlineDeport = trainlineDeportService.getJurisdiction(station);
        if (trainlineDeport != null) {
            station.setJurisdictionTdId(trainlineDeport.getId());
        }
    }

    /**
     * what:    新增车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void addOne(Station station) {
        int id = stationDao.addOne(station);
        station.setId(id);
    }

    /**
     * what:    更新车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Station station) {
        stationDao.updateOne(station);
    }

    /**
     * what:    删除车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Station station) {
        stationDao.deleteOne(station);
    }
}
