/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:
 */
package com.critc.rail.service;

import com.critc.rail.dao.BureauDao;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.vo.BureauSearchVo;
import com.critc.rail.vo.StationSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    调度视角的铁路局服务. <br/>
 * # 检测两个路局是否邻接. <br/>
 * # 检测给定路局是否是管辖给定行车调度台. <br/>
 * # 检测给定路局是否是管辖给定车站. <br/>
 * # 按条件查询一组行车调度台. <br/>
 * # 按条件查询一个行车调度台. <br/>
 * # 按Id查询一个路局. <br/>
 * # 获取给定路局的邻接路局. <br/>
 * # 获取给定行车调度台的管辖局. <br/>
 * # 获取给定车站的管辖局. <br/>
 * # 获取数量. <br/>
 * # 新增路局. <br/>
 * # 更新路局. <br/>
 * # 删除路局. <br/>
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
     * 路局数据获取对象
     */
    @Autowired
    private BureauDao bureauDao;

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
     * what:    检测给定路局是否管辖给定行车调度台. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean jurisdiction(Bureau bureau, TrainlineDeport trainlineDeport) {
        return railNetworkElementService.jurisdiction(bureau, trainlineDeport);
    }

    /**
     * what:    检测给定路局是否管辖给定车站. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean jurisdiction(Bureau bureau, Station station) {
        return railNetworkElementService.jurisdiction(bureau, station);
    }

    /**
     * what:    根据查询条件查询一组路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Bureau> getMany(BureauSearchVo bureauSearchVo) {
        return bureauDao.getMany(bureauSearchVo);
    }

    /**
     * what:    根据查询条件查询一个路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau getOne(BureauSearchVo bureauSearchVo) {
        return bureauDao.getOne(bureauSearchVo);
    }

    /**
     * what:    根据Id查询路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau getOne(int id) {
        BureauSearchVo bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setIdEqual(id);
        return getOne(bureauSearchVo);
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
        BureauSearchVo bureauSearchVo = new BureauSearchVo();
        return getMany(bureauSearchVo);
    }

    /**
     * what:    获取给定路局的邻接路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Bureau> getAdjoinBureaus(Bureau targetBureau) {
        // 邻接路局集合
        List<Bureau> adjoinBureaus = new ArrayList<>();
        // 遍历路局
        for (Bureau bureau : getAll()) {
            // 如果与目标路局邻接，加入邻接路局集合
            if (adjoin(targetBureau, bureau)) {
                adjoinBureaus.add(bureau);
            }
        }
        return adjoinBureaus;
    }

    /**
     * what:    获取给定行车调度台的管辖局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau getJurisdiction(TrainlineDeport trainlineDeport) {
        for (Bureau bureau : getAll()) {
            if (jurisdiction(bureau, trainlineDeport)) {
                return bureau;
            }
        }
        return null;
    }

    /**
     * what:    获取给定车站的管辖局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau getJurisdiction(Station staiton) {
        for (Bureau bureau : getAll()) {
            if (jurisdiction(bureau, staiton)) {
                return bureau;
            }
        }
        return null;
    }


    /**
     * what:    获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(BureauSearchVo bureauSearchVo) {
        return bureauDao.getCount(bureauSearchVo);
    }


    /**
     * what:    新增路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void addOne(Bureau bureau) {
        int id = bureauDao.addOne(bureau);
        bureau.setId(id);
    }

    /**
     * what:    更新路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Bureau bureau) {
        bureauDao.updateOne(bureau);
    }

    /**
     * what:    删除路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Bureau bureau) {
        bureauDao.deleteOne(bureau);
    }
}
