/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
    @Autowired
    private RailNetworkElementService railNetworkElementService;

    List<TrainlineDeport> trainlineDeports = new ArrayList<>();

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
    public List<Vector<TrainlineDeport>> getAdjoinTrainlineDeports(List<TrainlineDeport> trainlineDeports, Bureau bureauA, Bureau bureauB) {
        // 获取bureauA管辖的行调台
        List<TrainlineDeport> bureaATrainlineDeports = getTrainlineDeports(trainlineDeports, bureauA);
        // 获取bureauB管辖的行调台
        List<TrainlineDeport> bureaBTrainlineDeports = getTrainlineDeports(trainlineDeports, bureauB);

        // bureauA行车调度台与BureauB邻接
        // bureauB行车调度台与BureauA邻接

        throw new UnsupportedOperationException();
    }

    /**
     * what:    获取给定路局管辖的行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getTrainlineDeports(List<TrainlineDeport> trainlineDeports, Bureau bureau) {
        throw new UnsupportedOperationException();
    }

    /**
     * what:    设置给定行调台的管辖局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void setJurisdictionBureau(List<Bureau> bureaus, TrainlineDeport trainlineDeport) {
        synchronized (trainlineDeport) {
            for (Bureau bureau : bureaus) {
                if (railNetworkElementService.jurisdiction(bureau, trainlineDeport)) {
                    break;
                }
            }
        }
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
        return trainlineDeports;
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
        for (TrainlineDeport trainlineDeport : getAll()) {
            if (jurisdiction(trainlineDeport, station)) {
                return trainlineDeport;
            }
        }
        return null;
    }
}
