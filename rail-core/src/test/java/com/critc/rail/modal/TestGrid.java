/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

import com.critc.rail.service.PointVectorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.*;
import java.util.Vector;

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
public class TestGrid {
    @Autowired
    PointVectorService pointVectorService;

    @Test
    public void testGrid() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        PointVector basePointVector = new PointVector();
        basePointVector.setPoint(basePointX, basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        PointVector anchorPointVector1 = new PointVector();
        anchorPointVector1.setPoint(anchorPoint1X, anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        PointVector anchorPointVector2 = new PointVector();
        anchorPointVector2.setPoint(anchorPoint2X, anchorPoint2Y);
        double anchorPoint3X = 6.302;
        double anchorPoint3Y = 7.302;
        PointVector anchorPointVector3 = new PointVector();
        anchorPointVector3.setPoint(anchorPoint3X, anchorPoint3Y);
        double newAnchorPoint1X = 8.302;
        double newAnchorPoint1Y = 9.302;
        PointVector newAnchorPointVector = new PointVector();
        newAnchorPointVector.setPoint(newAnchorPoint1X, newAnchorPoint1Y);

        Grid grid = new Grid();

        grid.setBasePointVector(basePointVector);
        Assert.assertNotNull(grid.getBasePointVector());
        Assert.assertSame(basePointVector, grid.getBasePointVector());

        grid.addAnchorPointVector(anchorPoint1X, anchorPoint1Y);
        Assert.assertNotNull(grid.getBasePointVector());
        Assert.assertSame(basePointVector, grid.getBasePointVector());
        Assert.assertEquals(1, grid.getAnchorPointVectors().size());
        Assert.assertEquals(anchorPoint1X, grid.getAnchorPointVector(0).getPointX(), 0);
        Assert.assertEquals(anchorPoint1Y, grid.getAnchorPointVector(0).getPointY(), 0);

        grid.addAnchorPointVector(anchorPoint2X, anchorPoint2Y);
        Assert.assertNotNull(grid.getBasePointVector());
        Assert.assertSame(basePointVector, grid.getBasePointVector());
        Assert.assertEquals(2, grid.getAnchorPointVectors().size());
        Assert.assertEquals(anchorPoint1X, grid.getAnchorPointVector(0).getPointX(), 0);
        Assert.assertEquals(anchorPoint1Y, grid.getAnchorPointVector(0).getPointY(), 0);
        Assert.assertEquals(anchorPoint2X, grid.getAnchorPointVector(1).getPointX(), 0);
        Assert.assertEquals(anchorPoint2Y, grid.getAnchorPointVector(1).getPointY(), 0);

        grid.addAnchorPointVector(1, anchorPoint3X, anchorPoint3Y);
        Assert.assertEquals(3, grid.getAnchorPointVectors().size());
        Assert.assertEquals(anchorPoint1X, grid.getAnchorPointVector(0).getPointX(), 0);
        Assert.assertEquals(anchorPoint1Y, grid.getAnchorPointVector(0).getPointY(), 0);
        Assert.assertEquals(anchorPoint3X, grid.getAnchorPointVector(1).getPointX(), 0);
        Assert.assertEquals(anchorPoint3Y, grid.getAnchorPointVector(1).getPointY(), 0);
        Assert.assertEquals(anchorPoint2X, grid.getAnchorPointVector(2).getPointX(), 0);
        Assert.assertEquals(anchorPoint2Y, grid.getAnchorPointVector(2).getPointY(), 0);

        grid.updateAnchorPointVector(0, newAnchorPoint1X, newAnchorPoint1Y);
        Assert.assertEquals(3, grid.getAnchorPointVectors().size());
        Assert.assertEquals(newAnchorPoint1X, grid.getAnchorPointVector(0).getPointX(), 0);
        Assert.assertEquals(newAnchorPoint1Y, grid.getAnchorPointVector(0).getPointY(), 0);
        Assert.assertEquals(anchorPoint3X, grid.getAnchorPointVector(1).getPointX(), 0);
        Assert.assertEquals(anchorPoint3Y, grid.getAnchorPointVector(1).getPointY(), 0);
        Assert.assertEquals(anchorPoint2X, grid.getAnchorPointVector(2).getPointX(), 0);
        Assert.assertEquals(anchorPoint2Y, grid.getAnchorPointVector(2).getPointY(), 0);

        grid.removeAnchorPointVector(1);
        Assert.assertEquals(2, grid.getAnchorPointVectors().size());
        Assert.assertEquals(newAnchorPoint1X, grid.getAnchorPointVector(0).getPointX(), 0);
        Assert.assertEquals(newAnchorPoint1Y, grid.getAnchorPointVector(0).getPointY(), 0);
        Assert.assertEquals(anchorPoint2X, grid.getAnchorPointVector(1).getPointX(), 0);
        Assert.assertEquals(anchorPoint2Y, grid.getAnchorPointVector(1).getPointY(), 0);
        grid.removeAllAnchorPointVectors();
        Assert.assertNotNull(grid.getBasePointVector());
        Assert.assertSame(basePointVector, grid.getBasePointVector());
        Assert.assertNull(grid.getAnchorPointVectors());
        Assert.assertEquals(0, grid.getAnchorPointVectors().size());
    }
}