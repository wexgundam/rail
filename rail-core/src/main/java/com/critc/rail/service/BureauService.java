/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:
 */
package com.critc.rail.service;

import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.vo.BureauSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * what:    铁路局服务. <br/>
 * 1. 检测两个路局是否邻接. <br/>
 * 2. 获取两个路局邻接行车调度台. <br/>
 * 3. 获取两个路局的邻接口车站. <br/>
 * 3. 获取两个路局的路局分界口车站. <br/>
 * 4. 获取给定路局的邻接路局. <br/>
 * 5. 获取给定路局所辖行车调度台. <br/>
 * 6. 获取给定路局所辖车站. <br/>
 * 7. 获取给定路局所辖路局分界口车站. <br/>
 * 9. 检测给定路局是否是给定行调台的管辖局. <br/>
 * 10. 检测给定路局是否是给定车站的管辖局. <br/>
 * 13. 新增路局. <br/>
 * 14. 更新路局. <br/>
 * 15. 删除路局. <br/>
 * 15. 查询路局. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/11
 */
@Service
public class BureauService {
    /**
     * 铁路路网元素服务
     */
    @Autowired
    private RailNetworkElementService railNetworkElementService;

    /**
     * what:    检测两个路局是否邻接. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(Bureau bureauA, Bureau bureauB) {
        return railNetworkElementService.adjoin(bureauA, bureauB);
    }

    /**
     * what:    获取两个路局邻接行车调度台. <br/>
     * 返回值为List，元素为Vector. <br/>
     * Vector[0]为bureauA管辖的行车调度台. <br/>
     * Vector[1]为bureauB管辖的行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Vector<TrainlineDeport>> getAdjoinTrainlineDeports(Bureau bureauA, Bureau bureauB, List<TrainlineDeport> trainlineDeports) {
        // 获取bureauA管辖的行调台
        List<TrainlineDeport> bureaATrainlineDeports = getTrainlineDeports(bureauA, trainlineDeports);
        // 获取bureauB管辖的行调台
        List<TrainlineDeport> bureaBTrainlineDeports = getTrainlineDeports(bureauB, trainlineDeports);

        // bureauA行车调度台与BureauB邻接
        // bureauB行车调度台与BureauA邻接

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
        List<Station> bureaAStations = getStations(bureauA, stations);
        // 获取bureauB管辖的车站
        List<Station> bureaBStations = getStations(bureauB, stations);

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
    public List<Station> getBureauPartingStations(Bureau bureauA, Bureau bureauB, List<Station> stations) {
        // 获取bureauA管辖的路局分界口车站
        List<Station> bureaABureaPartingStations = getBureauPartingStations(bureauA, stations);
        // 获取bureauB管辖的路局分界口车站
        List<Station> bureaBBureaPartingStations = getBureauPartingStations(bureauB, stations);

        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取给定路局的邻接路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Bureau> getAdjoinBureaus(List<Bureau> bureaus, Bureau targetBureau) {
        // 邻接路局集合
        List<Bureau> adjoinBureaus = new ArrayList<>();
        // 遍历路局
        for (Bureau bureau : bureaus) {
            // 如果与目标路局邻接，加入邻接路局集合
            if (railNetworkElementService.adjoin(targetBureau, bureau)) {
                adjoinBureaus.add(bureau);
            }
        }
        return adjoinBureaus;
    }

    /**
     * what:    获取给定路局管辖的行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getTrainlineDeports(Bureau bureau, List<TrainlineDeport> trainlineDeports) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取给定路局管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getStations(Bureau bureau, List<Station> stations) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取给定路局管辖的路局分界口车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getBureauPartingStations(Bureau bureau, List<Station> stations) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    新增路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void add(Bureau bureau) {

    }

    /**
     * what:    更新路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void update(Bureau bureau) {

    }

    /**
     * what:    删除路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void delete(Bureau bureau) {

    }

    /**
     * what:    根据Id查询路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau get(int id) {
        return null;
    }

    /**
     * what:    根据查询条件查询路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau get(BureauSearchVo bureauSearchVo) {
        return null;
    }

    /**
     * what:    查询所有路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Bureau> getAll() {
        return null;
    }
}
