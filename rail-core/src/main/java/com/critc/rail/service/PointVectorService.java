/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.modal.PointVector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    向量坐标服务. <br/>
 * 1. 将一个坐标的字符串形式转为一个向量
 * 2. 将一组坐标的字符串形式转为一组向量
 * 3. 将一个坐标的一个向量转为字符串形式
 * 4. 将一组坐标的一组向量转为字符串形式
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/4
 */
@Service
public class PointVectorService {
    /**
     * 字符串形式的坐标，其X和Y的分割标记为"@"
     */
    private static final String POINT_XY_SPLITTER = "@";
    /**
     * 字符串形式的若干坐标，每个字符串形式的坐标的分割标记为";"
     */
    private static final String POINT_SPLITTER = ";";

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
        String[] pointStringValueArray = pointString.split(POINT_XY_SPLITTER);
        PointVector pointVector = new PointVector();
        pointVector.setPoint(Double.parseDouble(pointStringValueArray[0]), Double.parseDouble(pointStringValueArray[1]));
        return pointVector;
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
            pointVectors.add(toPointVector(pointString));
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
}
