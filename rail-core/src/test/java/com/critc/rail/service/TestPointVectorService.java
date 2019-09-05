/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.modal.Grid;
import com.critc.rail.modal.PointVector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    测试PointVectorService. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestPointVectorService {
    public static final String POINT_XY_SPLITTER = "@";
    public static final String POINT_SPLITTER = ";";
    @Autowired
    PointVectorService pointVectorService;

    @Test
    public void testToVector() {
        double point1X = 1.000506;
        double point1Y = 2.000506;
        String point1String = Double.toString(point1X) + POINT_XY_SPLITTER + Double.toString(point1Y);
        double point2X = 2.302;
        double point2Y = 3.302;
        String point2String = Double.toString(point2X) + POINT_XY_SPLITTER + Double.toString(point2Y);
        String pointsString = point1String + POINT_SPLITTER + point2String;

        PointVector point1Vector = pointVectorService.toPointVector(point1String);
        Assert.assertNotNull(point1Vector);
        Assert.assertEquals(point1X, point1Vector.getPointX(), 0);
        Assert.assertEquals(point1Y, point1Vector.getPointY(), 0);
        List<PointVector> pointVectors = pointVectorService.toPointVectors(pointsString);
        Assert.assertNotNull(pointVectors);
        Assert.assertEquals(2, pointVectors.size());
        Assert.assertEquals(point1X, pointVectors.get(0).getPointX(), 0);
        Assert.assertEquals(point1Y, pointVectors.get(0).getPointY(), 0);
        Assert.assertEquals(point2X, pointVectors.get(1).getPointX(), 0);
        Assert.assertEquals(point2Y, pointVectors.get(1).getPointY(), 0);
    }

    @Test
    public void testToString() {
        double point1X = 1.000506;
        double point1Y = 2.000506;
        PointVector point1Vector = new PointVector();
        point1Vector.setPoint(point1X, point1Y);
        String point1String1 = Double.toString(point1X) + POINT_XY_SPLITTER + Double.toString(point1Y);
        double point2X = 2.302;
        double point2Y = 3.302;
        PointVector point2Vector = new PointVector();
        point2Vector.setPoint(point2X, point2Y);
        String point2String1 = Double.toString(point2X) + POINT_XY_SPLITTER + Double.toString(point2Y);
        List<PointVector> pointVectors = new ArrayList<>();
        pointVectors.add(point1Vector);
        pointVectors.add(point2Vector);
        String pointsString1 = point1String1 + POINT_SPLITTER + point2String1;

        String point1String2 = pointVectorService.toString(point1Vector);
        String point2String2 = pointVectorService.toString(point2Vector);
        Assert.assertEquals(point1String1, point1String2);
        Assert.assertEquals(point2String1, point2String2);
        String pointsString2 = pointVectorService.toString(pointVectors);
        Assert.assertEquals(pointsString1, pointsString2);
    }
}