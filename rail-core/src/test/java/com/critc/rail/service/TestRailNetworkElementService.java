/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.modal.Station;
import com.critc.network.modal.Grid;
import com.critc.network.modal.PointVector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    测试RailElementService. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestRailNetworkElementService {
    @Autowired
    RailNetworkElementService railNetworkService;
    private final String POINT_XY_SPLITTER = RailNetworkElementService.POINT_XY_SPLITTER;
    private final String POINT_SPLITTER = RailNetworkElementService.POINT_SPLITTER;

    @Test
    public void testUpdateGridOfElement() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        String basePointString = Double.toString(basePointX) + POINT_XY_SPLITTER + Double.toString(basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        String anchorPoint1String = Double.toString(anchorPoint1X) + POINT_XY_SPLITTER + Double.toString(anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        String anchorPoint2String = Double.toString(anchorPoint2X) + POINT_XY_SPLITTER + Double.toString(anchorPoint2Y);
        String anchorPointsString = anchorPoint1String + POINT_SPLITTER + anchorPoint2String;

        Station station = new Station();
        station.setId(1234);
        station.setBasePointString(basePointString);
        station.setAnchorPointsString(anchorPointsString);
        railNetworkService.updateGridOfElement(station);
        Grid grid = station.getGrid();

        Assert.assertNotNull(grid);
        Assert.assertEquals(station.getId(), grid.getId());
        Assert.assertNotNull(grid.getBasePointVector());
        Assert.assertEquals(basePointX, grid.getBasePointVector().getPointX(), 0);
        Assert.assertEquals(basePointY, grid.getBasePointVector().getPointY(), 0);

        Assert.assertEquals(2, grid.getAnchorPointVectors().size());
        Assert.assertEquals(anchorPoint1X, grid.getAnchorPointVector(0).getPointX(), 0);
        Assert.assertEquals(anchorPoint1Y, grid.getAnchorPointVector(0).getPointY(), 0);
        Assert.assertEquals(anchorPoint2X, grid.getAnchorPointVector(1).getPointX(), 0);
        Assert.assertEquals(anchorPoint2Y, grid.getAnchorPointVector(1).getPointY(), 0);
    }

    @Test
    public void testUpdateElementByGrid() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        PointVector basePointVector = new PointVector();
        basePointVector.setPoint(basePointX, basePointY);
        String basePointString = Double.toString(basePointX) + POINT_XY_SPLITTER + Double.toString(basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        PointVector anchorPoint1Vector = new PointVector();
        anchorPoint1Vector.setPoint(anchorPoint1X, anchorPoint1Y);
        String anchorPoint1String = Double.toString(anchorPoint1X) + POINT_XY_SPLITTER + Double.toString(anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        PointVector anchorPoint2Vector = new PointVector();
        anchorPoint2Vector.setPoint(anchorPoint2X, anchorPoint2Y);
        String anchorPoint2String = Double.toString(anchorPoint2X) + POINT_XY_SPLITTER + Double.toString(anchorPoint2Y);
        List<PointVector> anchorPointVectors = new ArrayList<>();
        anchorPointVectors.add(anchorPoint1Vector);
        anchorPointVectors.add(anchorPoint2Vector);
        String anchorPointsString = anchorPoint1String + POINT_SPLITTER + anchorPoint2String;

        Grid grid = new Grid();
        grid.setBasePointVector(basePointVector);
        grid.setAnchorPointVectors(anchorPointVectors);

        Station station = new Station();
        station.setGrid(grid);
        railNetworkService.updateElementByGrid(station);
        Assert.assertEquals(basePointString, station.getBasePointString());
        Assert.assertEquals(anchorPointsString, station.getAnchorPointsString());
    }


    @Test
    public void testToVector() {
        double point1X = 1.000506;
        double point1Y = 2.000506;
        String point1String = Double.toString(point1X) + POINT_XY_SPLITTER + Double.toString(point1Y);
        double point2X = 2.302;
        double point2Y = 3.302;
        String point2String = Double.toString(point2X) + POINT_XY_SPLITTER + Double.toString(point2Y);
        String pointsString = point1String + POINT_SPLITTER + point2String;

        PointVector point1Vector = railNetworkService.toPointVector(point1String);
        Assert.assertNotNull(point1Vector);
        Assert.assertEquals(point1X, point1Vector.getPointX(), 0);
        Assert.assertEquals(point1Y, point1Vector.getPointY(), 0);
        List<PointVector> pointVectors = railNetworkService.toPointVectors(pointsString);
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

        String point1String2 = railNetworkService.toString(point1Vector);
        String point2String2 = railNetworkService.toString(point2Vector);
        Assert.assertEquals(point1String1, point1String2);
        Assert.assertEquals(point2String1, point2String2);
        String pointsString2 = railNetworkService.toString(pointVectors);
        Assert.assertEquals(pointsString1, pointsString2);
    }
}