/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.network.modal;

import com.critc.network.modal.PointVector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * what:    测试PointVector. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestPointVector {

    @Test
    public void testSet() {
        double basePoint1X = 1.000506;
        double basePoint1Y = 2.000506;
        double basePoint2X = 3.000506;
        double basePoint2Y = 4.000506;

        PointVector pointVector = new PointVector();
        pointVector.setPoint(basePoint1X, basePoint1Y);
        Assert.assertNotNull(pointVector.getVector());
        Assert.assertEquals(basePoint1X, pointVector.getPointX(), 0);
        Assert.assertEquals(basePoint1Y, pointVector.getPointY(), 0);
        pointVector.setPointX(basePoint2X);
        Assert.assertEquals(basePoint2X, pointVector.getPointX(), 0);
        pointVector.setPointY(basePoint2Y);
        Assert.assertEquals(basePoint2Y, pointVector.getPointY(), 0);
    }
}