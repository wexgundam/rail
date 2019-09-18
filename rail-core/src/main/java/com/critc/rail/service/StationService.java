/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:rail
 */
package com.critc.rail.service;

import com.critc.rail.modal.AdjoinStations;
import com.critc.rail.modal.Bureau;
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
import java.util.Vector;

/**
 * what:    调度视角的车站服务. <br/>
 * # 检测两个车站是否邻接. <br/>
 * # 检测给定车站是否管辖给定车场. <br/>
 * # 检测给定车站是否邻接给定节点间. <br/
 * # 按条件查询一组车站. <br/>
 * # 按条件查询一个车站. <br/>
 * # 获取全路车站. <br/>
 * # 获取全路的路局分界口车站. <br/>
 * # 获取两个路局的邻接车站. <br/>
 * # 获取两个路局的路局分界口车站. <br/>
 * # 获取给定路局所辖车站. <br/>
 * # 获取给定路局所辖路局分界口车站. <br/>
 * # 获取给定行车调度台所辖车站. <br/>
 * # 获取给定车站的邻接车站. <br/>
 * # 获得给定节点间的邻接车站. <br/>
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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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
     * 返回值为List，元素为Vector. <br/>
     * Vector[0]为管辖局. <br/>
     * Vector[1]为邻接局. <br/>
     * Vector[2]为分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Vector<Object>> getBureauPartings() {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setBureauPartingStation(true);
//        getList(stationSearchVo);

        throw new UnsupportedOperationException();
//        return ;
    }

    /**
     * what:    获取两个路局邻接车站. <br/>
     * 返回值为为Vector. <br/>
     * Vector[0]为bureauA管辖的车站集合. <br/>
     * Vector[1]为bureauB管辖的车站集合. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Vector<List<Station>> getAdjoins(Bureau bureauA, Bureau bureauB) {
        // 获取bureauA管辖的车站
        List<Station> bureaAStations = this.getMany(bureauA);
        // 获取bureauB管辖的车站
        List<Station> bureaBStations = this.getMany(bureauB);

        // bureauA车站与BureauB邻接
        // bureauB车站与BureauA邻接

        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取两个路局的路局分界口车站. <br/>
     * 返回值为List，元素为Vector. <br/>
     * Vector[0]为管辖局. <br/>
     * Vector[1]为邻接局. <br/>
     * Vector[2]为分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Vector<Object>> getBureauPartings(Bureau bureauA, Bureau bureauB) {
        // 两局分界口集合
        List<Vector<Object>> vectors = new ArrayList<>();
        // 获得两局邻接车站集合
        Vector<List<Station>> adjoinStations = getAdjoins(bureauA, bureauB);
        // 遍历bureauA管辖的车站
        // 如果车站为分界口，加入到集合中
        for (Station station : adjoinStations.get(0)) {
            if (station.isBureauParting()) {
                Vector<Object> vector = new Vector<>(3);
                vector.set(0, bureauA);
                vector.set(1, bureauB);
                vector.set(2, station);
            }
        }
        // 遍历bureauB管辖的车站
        // 如果车站为分界口，加入到集合中
        for (Station station : adjoinStations.get(1)) {
            if (station.isBureauParting()) {
                Vector<Object> vector = new Vector<>(3);
                vector.set(0, bureauB);
                vector.set(1, bureauA);
                vector.set(2, station);
            }
        }

        return vectors;
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
     * 返回值为List，元素为Vector. <br/>
     * Vector[0]为管辖局. <br/>
     * Vector[1]为邻接局. <br/>
     * Vector[2]为分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Vector<Object>> getBureauPartings(Bureau bureau) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setBureauPartingStation(true);
        stationSearchVo.setJurisdictionBureauIdEqual(bureau.getId());
        List<Station> stations = getMany(stationSearchVo);

        throw new UnsupportedOperationException();
//        return ;
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
        stationSearchVo.setJurisdictionTrainlineDeportIdEqual(trainlineDeport.getId());
        return getMany(stationSearchVo);
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
    public List<AdjoinStations> getAdjoins(Station station) {
        List<Station> stations;
        List<Link> links;
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
    public AdjoinStations getAdjoins(Link link) {
        List<Station> stations;
        throw new UnsupportedOperationException();
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
        List<Bureau> bureaus;
        List<TrainlineDeport> trainlineDeports;
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
    public void addOne(Station station) {
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
    public void updateOne(Station station) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
}
