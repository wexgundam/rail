/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

import com.critc.rail.util.Global.GridGlobal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void testToModal() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        String basePointString = Double.toString(basePointX) + GridGlobal.POINT_XY_SPLITTER + Double.toString(basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        String anchorPoint1String = Double.toString(anchorPoint1X) + GridGlobal.POINT_XY_SPLITTER + Double.toString(anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        String anchorPoint2String = Double.toString(anchorPoint2X) + GridGlobal.POINT_XY_SPLITTER + Double.toString(anchorPoint2Y);
        String anchorPointsString = anchorPoint1String + GridGlobal.POINT_SPLITTER + anchorPoint2String;

        Grid grid = new Grid();
        grid.setBasePointString(basePointString);
        grid.setAnchorPointsString(anchorPointsString);
        grid.toModal();
        Assert.assertNotNull(grid.getBasePoint());
        Assert.assertTrue(grid.getBasePoint().get(0).equals(basePointX));
        Assert.assertTrue(grid.getBasePoint().get(1).equals(basePointY));
        Assert.assertEquals(2, grid.getAnchorPoints().size());
        Vector<Double> anchorPoint1 = grid.getAnchorPoint(0);
        Assert.assertNotNull(anchorPoint1);
        Assert.assertTrue(anchorPoint1.get(0).equals(anchorPoint1X));
        Assert.assertTrue(anchorPoint1.get(1).equals(anchorPoint1Y));
        Vector<Double> anchorPoint2 = grid.getAnchorPoint(1);
        Assert.assertNotNull(anchorPoint2);
        Assert.assertTrue(anchorPoint2.get(0).equals(anchorPoint2X));
        Assert.assertTrue(anchorPoint2.get(1).equals(anchorPoint2Y));
    }

    @Test
    public void testToData() {
        double basePointX = 1.000506;
        double basePointY = 2.000506;
        String basePointString = Double.toString(basePointX) + GridGlobal.POINT_XY_SPLITTER + Double.toString(basePointY);
        double anchorPoint1X = 2.302;
        double anchorPoint1Y = 3.302;
        String anchorPoint1String = Double.toString(anchorPoint1X) + GridGlobal.POINT_XY_SPLITTER + Double.toString(anchorPoint1Y);
        double anchorPoint2X = 4.302;
        double anchorPoint2Y = 5.302;
        String anchorPoint2String = Double.toString(anchorPoint2X) + GridGlobal.POINT_XY_SPLITTER + Double.toString(anchorPoint2Y);
        String anchorPointsString12 = anchorPoint1String + GridGlobal.POINT_SPLITTER + anchorPoint2String;
        double anchorPoint3X = 6.302;
        double anchorPoint3Y = 7.302;
        String anchorPoint3String = Double.toString(anchorPoint3X) + GridGlobal.POINT_XY_SPLITTER + Double.toString(anchorPoint3Y);
        String anchorPointsString132 = anchorPoint1String + GridGlobal.POINT_SPLITTER + anchorPoint3String + GridGlobal.POINT_SPLITTER + anchorPoint2String;
        double newAnchorPoint1X = 8.302;
        double newAnchorPoint1Y = 9.302;
        String newAnchorPoint1String = Double.toString(newAnchorPoint1X) + GridGlobal.POINT_XY_SPLITTER + Double.toString(newAnchorPoint1Y);
        String newAnchorPointsString132 = newAnchorPoint1String + GridGlobal.POINT_SPLITTER + anchorPoint3String + GridGlobal.POINT_SPLITTER + anchorPoint2String;
        String newAnchorPointsString12 = newAnchorPoint1String + GridGlobal.POINT_SPLITTER + anchorPoint2String;

        Grid grid = new Grid();
        grid.setBasePoint(basePointX, basePointY);
        grid.addAnchorPoint(anchorPoint1X, anchorPoint1Y);
        grid.addAnchorPoint(anchorPoint2X, anchorPoint2Y);
        grid.toData();
        Assert.assertEquals(basePointString, grid.getBasePointString());
        Assert.assertEquals(anchorPointsString12, grid.getAnchorPointsString());
        grid.addAnchorPoint(1, anchorPoint3X, anchorPoint3Y);
        grid.toData();
        Assert.assertEquals(basePointString, grid.getBasePointString());
        Assert.assertEquals(anchorPointsString132, grid.getAnchorPointsString());
        grid.updateAnchorPoint(0, newAnchorPoint1X, newAnchorPoint1Y);
        grid.toData();
        Assert.assertEquals(basePointString, grid.getBasePointString());
        Assert.assertEquals(newAnchorPointsString132, grid.getAnchorPointsString());
        grid.removeAnchorPoint(1);
        grid.toData();
        Assert.assertEquals(basePointString, grid.getBasePointString());
        Assert.assertEquals(newAnchorPointsString12, grid.getAnchorPointsString());
        grid.removeAll();
        grid.toData();
        Assert.assertEquals(basePointString, grid.getBasePointString());
        Assert.assertNull(grid.getAnchorPointsString());
    }
}