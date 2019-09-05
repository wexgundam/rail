/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.rail.modal.Grid;
import com.critc.rail.modal.PointVector;
import com.critc.rail.modal.Station;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    测试Grid. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestStationService {
    @Autowired
    StationService stationService;

    @Test
    public void testUpdateGridOfStation() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        String basePointString = Double.toString(basePointX) + TestPointVectorService.POINT_XY_SPLITTER + Double.toString(basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        String anchorPoint1String = Double.toString(anchorPoint1X) + TestPointVectorService.POINT_XY_SPLITTER + Double.toString(anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        String anchorPoint2String = Double.toString(anchorPoint2X) + TestPointVectorService.POINT_XY_SPLITTER + Double.toString(anchorPoint2Y);
        String anchorPointsString = anchorPoint1String + TestPointVectorService.POINT_SPLITTER + anchorPoint2String;

        Station station = new Station();
        station.setId(1234);
        station.setBasePointString(basePointString);
        station.setAnchorPointsString(anchorPointsString);
        stationService.updateGridOfStation(station);
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
    public void testUpdateStationByGrid() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        PointVector basePointVector = new PointVector();
        basePointVector.setPoint(basePointX, basePointY);
        String basePointString = Double.toString(basePointX) + TestPointVectorService.POINT_XY_SPLITTER + Double.toString(basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        PointVector anchorPoint1Vector = new PointVector();
        anchorPoint1Vector.setPoint(anchorPoint1X, anchorPoint1Y);
        String anchorPoint1String = Double.toString(anchorPoint1X) + TestPointVectorService.POINT_XY_SPLITTER + Double.toString(anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        PointVector anchorPoint2Vector = new PointVector();
        anchorPoint2Vector.setPoint(anchorPoint2X, anchorPoint2Y);
        String anchorPoint2String = Double.toString(anchorPoint2X) + TestPointVectorService.POINT_XY_SPLITTER + Double.toString(anchorPoint2Y);
        List<PointVector> anchorPointVectors = new ArrayList<>();
        anchorPointVectors.add(anchorPoint1Vector);
        anchorPointVectors.add(anchorPoint2Vector);
        String anchorPointsString = anchorPoint1String + TestPointVectorService.POINT_SPLITTER + anchorPoint2String;

        Grid grid = new Grid();
        grid.setBasePointVector(basePointVector);
        grid.setAnchorPointVectors(anchorPointVectors);

        Station station = new Station();
        station.setGrid(grid);
        stationService.updateStationByGrid(station);
        Assert.assertEquals(basePointString, station.getBasePointString());
        Assert.assertEquals(anchorPointsString, station.getAnchorPointsString());
    }
}