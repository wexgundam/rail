/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:rail
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.modal.AdjoinStations;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.BureauPartingStation;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.modal.Yard;
import com.critc.rail.util.global.Global;
import com.critc.rail.vo.StationSearchVo;
import com.critc.util.cache.EhCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * what:    调度视角的车站服务. <br/>
 * # 检测两个车站是否邻接. <br/>
 * # 检测给定车站是否管辖给定车场. <br/>
 * # 检测给定车站是否邻接给定节点间. <br/>
 * # 获取给定路局所辖车站. <br/>
 * # 获取给定路局所辖路局分界口车站. <br/>
 * # 获取两个路局的邻接口车站. <br/>
 * # 获取两个路局的路局分界口车站. <br/>
 * # 获取全路的路局分界口车站. <br/>
 * # 设置给定车站的管辖局. <br/>
 * # 设置给定车站的管辖行车调度台. <br/>
 * # 获得给定节点间的邻接车站. <br/>
 * # 获取给定车还在那的邻接车站. <br/>
 * # 新增车站. <br/>
 * # 更新车站. <br/>
 * # 删除车站. <br/>
 * # 查询车站. <br/>
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
     * 车站缓存的key
     */
    private static final String STATION_CACHE_KEY = "stations";

    @Autowired
    private RailNetworkElementService railNetworkElementService;

    /**
     * what:    检测两个车站是否邻接. <br/>
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
                logger.error("GridService.getGridContainsTargetGrid()任务类: " + getClass().getSimpleName());
                logger.error("GridService.getGridContainsTargetGrid()异常信息: " + e.getMessage());
            } catch (ExecutionException e) {
                // 异常日志
                logger.error("GridService.getGridContainsTargetGrid()任务类: " + getClass().getSimpleName());
                logger.error("GridService.getGridContainsTargetGrid()异常信息: " + e.getMessage());
            }
        }

        //两个车站不邻接
        return false;
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
     * what:    获取给定路局管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getStationsByBureau(List<Station> stations, Bureau bureau) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionBureauIdEqual(bureau.getId());
        return getList(stationSearchVo);
    }

    /**
     * what:    获取给定路局管辖的路局分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<BureauPartingStation> getBureauPartingStations(List<Station> stations, Bureau bureau) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取两个路局邻接车站. <br/>
     * 返回值为List，元素为Vector. <br/>
     * Vector[0]为bureauA管辖的车站. <br/>
     * Vector[1]为bureauB管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Vector<Station>> getAdjoinStations(Bureau bureauA, Bureau bureauB, List<Station> stations) {
        // 获取bureauA管辖的车站
        List<Station> bureaAStations = getStationsByBureau(stations, bureauA);
        // 获取bureauB管辖的车站
        List<Station> bureaBStations = getStationsByBureau(stations, bureauB);

        // bureauA车站与BureauB邻接
        // bureauB车站与BureauA邻接

        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取两个路局的路局分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<BureauPartingStation> getBureauPartingStations(Bureau bureauA, Bureau bureauB, List<Station> stations) {
        // 获取bureauA管辖的路局分界口车站
        List<BureauPartingStation> bureaABureaPartingStations = getBureauPartingStations(stations, bureauA);
        // 获取bureauB管辖的路局分界口车站
        List<BureauPartingStation> bureaBBureaPartingStations = getBureauPartingStations(stations, bureauB);

        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }


    /**
     * what:    设置给定车站的管辖局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void setJurisdictionBureau(List<Bureau> bureaus, Station station) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    设置给定车站的管辖行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void setJurisdictionTrainlineDeport(List<TrainlineDeport> trainlineDeports, Station station) {
        throw new UnsupportedOperationException();
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
    public AdjoinStations getAdjoinStations(List<Station> stations, Link link) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    获得给定车站的邻接车站. <br/>
     * 通过gridService实现. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<AdjoinStations> getAdjoinStations(List<Station> stations, List<Link> links, Station station) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    新增车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void add(Station station) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    更新车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void update(Station station) {

    }

    /**
     * what:    删除车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void delete(Station station) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    根据Id查询车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Station get(int id) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setIdEqual(id);
        return get(stationSearchVo);
    }

    /**
     * what:    根据查询条件查询一个车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Station get(StationSearchVo stationSearchVo) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    根据查询条件查询一组车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getList(StationSearchVo stationSearchVo) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    查询所有车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getAll() {
        //从缓存获取数据
        List<Station> stations = EhCacheUtil.get(Global.CACHE_NAME, STATION_CACHE_KEY);
        if (stations == null) {
            //缓存没有数据，从数据库获取
            stations = new ArrayList<>();
            //缓存数据
            EhCacheUtil.put(Global.CACHE_NAME, STATION_CACHE_KEY, stations);
        }
        return stations;
    }
}
