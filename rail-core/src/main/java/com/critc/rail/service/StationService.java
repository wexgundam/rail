/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.modal.Grid;
import com.critc.rail.modal.PointVector;
import com.critc.rail.modal.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * what:    车站服务. <br/>
 * 1. 生成车站对应的网格
 * 2. 根据网格生成车站对应的坐标属性
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/5
 */
@Service
public class StationService {
    /**
     * 向量坐标服务
     */
    @Autowired
    private PointVectorService pointVectorService;

    /**
     * what:    根据车站信息生成网格，并关联车站与网格. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/5
     */
    public void updateGridOfStation(Station station) {
        //创建网格
        Grid grid = new Grid();
        //设置标识
        grid.setId(station.getId());
        //将车站基点坐标的字符串形式转为向量形式
        grid.setBasePointVector(pointVectorService.toPointVector(station.getBasePointString()));
        //将车站锚点坐标的字符串形式转为向量形式
        grid.setAnchorPointVectors(pointVectorService.toPointVectors(station.getAnchorPointsString()));
        //设置源对象
        grid.setOrigin(station);
        //关联车站和网格
        station.setGrid(grid);
    }

    /**
     * what:    根据网格信息更新车站基点与锚点信息. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void updateStationByGrid(Station station) {
        //将基点坐标的向量形式转为字符串形式
        station.setBasePointString(pointVectorService.toString(station.getGrid().getBasePointVector()));
        //将锚点坐标的向量形式转为字符串形式
        station.setAnchorPointsString(pointVectorService.toString(station.getGrid().getAnchorPointVectors()));
    }
}
