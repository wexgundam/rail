/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.service.GridService;
import com.critc.rail.modal.IRailNetworkElement;
import com.critc.network.modal.Grid;
import com.critc.network.modal.PointVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    铁路路网元素服务. <br/>
 * 1. 生成铁路路网元素对应的网格
 * 2. 根据网格生成铁路路网元素对应的坐标属性
 * 3. 判断两个路网元素是否邻接
 * 4. 检测铁路路网元素A是否管辖铁路路网元素B
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/5
 */
@Service
public class RailNetworkElementService {
    /**
     * 字符串形式的坐标，其X和Y的分割标记为"@"
     */
    public static final String POINT_XY_SPLITTER = "@";
    /**
     * 字符串形式的若干坐标，每个字符串形式的坐标的分割标记为";"
     */
    public static final String POINT_SPLITTER = ";";
    /**
     * 网格服务
     */
    @Autowired
    private GridService gridService;

    /**
     * what:    根据铁路路网元素信息生成网格，并关联元素与网格. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/5
     */
    public void updateGridOfElement(IRailNetworkElement element) {
        //创建网格
        Grid grid = new Grid();
        //设置标识
        grid.setId(element.getId());
        //设置空间几何类型
        grid.setGeometryType(element.getGridGeometryType());
        //将元素基点坐标的字符串形式转为向量形式
        grid.setBasePointVector(toPointVector(element.getBasePointString()));
        //将元素锚点坐标的字符串形式转为向量形式
        grid.setAnchorPointVectors(toPointVectors(element.getAnchorPointsString()));
        //设置源对象
        grid.setOriginal(element);
        //关联元素和网格
        element.setGrid(grid);
    }

    /**
     * what:    检测给定元素是否已生成对应网格，否则生成网格. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/17
     */
    private void checkGrid(IRailNetworkElement element) {
        if (element.getGrid() == null) {
            updateGridOfElement(element);
        }
    }

    /**
     * what:    根据网格信息更新路网基点与锚点信息. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public void updateElementByGrid(IRailNetworkElement element) {
        //将基点坐标的向量形式转为字符串形式
        element.setBasePointString(toString(element.getGrid().getBasePointVector()));
        //将锚点坐标的向量形式转为字符串形式
        element.setAnchorPointsString(toString(element.getGrid().getAnchorPointVectors()));
    }

    /**
     * what:    将给定字符串形式的坐标，转为向量形式的坐标。 <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @param pointString 字符串形式的坐标
     *
     * @return 向量形式的坐标
     *
     * @author 靳磊 created on 2019/9/3
     */
    public PointVector toPointVector(String pointString) {
        try {
            String[] pointStringValueArray = pointString.split(POINT_XY_SPLITTER);
            PointVector pointVector = new PointVector();
            pointVector.setPoint(Double.parseDouble(pointStringValueArray[0]), Double.parseDouble(pointStringValueArray[1]));
            return pointVector;
        } catch (Exception e) {//有异常返回null;
            return null;
        }
    }

    /**
     * what:    将给定字符串形式的若干坐标，转为向量形式的若干坐标的集合。 <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @param pointsString 字符串形式的若干坐标
     *
     * @return 向量形式的若干坐标的集合
     *
     * @author 靳磊 created on 2019/9/3
     */
    public List<PointVector> toPointVectors(String pointsString) {
        String[] pointStringArray = pointsString.split(POINT_SPLITTER);
        List<PointVector> pointVectors = new ArrayList<>();
        for (String pointString : pointStringArray) {
            PointVector pointVector = toPointVector(pointString);
            if (pointVector != null) {
                pointVectors.add(pointVector);
            }
        }
        return pointVectors;
    }

    /**
     * what:    将向量形式的坐标转为字符串形式. <br/>
     * 字符串形式为"X@Y";<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public String toString(PointVector pointVector) {
        if (pointVector == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(pointVector.getPointX());
        stringBuffer.append(POINT_XY_SPLITTER);
        stringBuffer.append(pointVector.getPointY());
        return stringBuffer.substring(0);
    }

    /**
     * what:    将向量形式的坐标集合转为字符串形式. <br/>
     * 字符串形式为"X1@Y1;X2@Y2";<br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/3
     */
    public String toString(List<PointVector> pointVectors) {
        if (pointVectors == null || pointVectors.isEmpty()) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (PointVector pointVector : pointVectors) {
            stringBuffer.append(toString(pointVector));
            stringBuffer.append(POINT_SPLITTER);
        }
        // 移除最后添加的POINT_SPLITTER
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    /**
     * what:    检测两个铁路路网元素是否邻接. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean adjoin(IRailNetworkElement elementA, IRailNetworkElement elementB) {
        // 判断elementA是否已生成网格
        checkGrid(elementA);
        // 判断elementB是否已生成网格
        checkGrid(elementB);
        // 通过网格服务判断是否邻接
        return gridService.touches(elementA.getGrid(), elementB.getGrid());
    }

    /**
     * what:    检测铁路路网元素A是否管辖铁路路网元素B. <br/>
     * 管辖全根据网格范围判断，元素B在元素A的范围内，A具有对B的管辖全. <br/>
     * 通过GridService判断网格间关系. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public boolean jurisdiction(IRailNetworkElement elementA, IRailNetworkElement elementB) {
        // 判断elementA是否已生成网格
        checkGrid(elementA);
        // 判断elementB是否已生成网格
        checkGrid(elementB);
        // 通过网格服务判断是否邻接
        return gridService.contains(elementA.getGrid(), elementB.getGrid());
    }
}
