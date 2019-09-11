/**
 * Copyright 2017 弘远技术研发中心. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.network.service;

import com.critc.network.modal.Grid;
import com.critc.util.date.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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
public class TestGridService {
    @Autowired
    GridService gridService;

    @Test
    public void testContainsAndTouches() {
        // 网格B完全内含与网格A，网格A包含网格B
        Grid gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        Grid gridB = new Grid();
        gridB.addAnchorPointVector(10, 10);
        gridB.addAnchorPointVector(10, 20);
        gridB.addAnchorPointVector(20, 20);
        gridB.addAnchorPointVector(20, 10);
        boolean result = gridService.contains(gridA, gridB);
        Assert.assertTrue(result);

        // 网格B内切网格A于1点，网格A包含网格B
        gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        gridB = new Grid();
        gridB.addAnchorPointVector(0, 0);
        gridB.addAnchorPointVector(10, 20);
        gridB.addAnchorPointVector(20, 20);
        result = gridService.contains(gridA, gridB);
        Assert.assertTrue(result);

        // 网格A不包含但外切网格B于1点
        gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        gridB = new Grid();
        gridB.addAnchorPointVector(0, 0);
        gridB.addAnchorPointVector(-10, -20);
        gridB.addAnchorPointVector(-20, -20);
        result = gridService.contains(gridA, gridB);
        Assert.assertFalse(result);
        result = gridService.touches(gridA, gridB);
        Assert.assertTrue(result);

        // 网格A不包含但外切网格B
        gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        gridB = new Grid();
        gridB.addAnchorPointVector(-10, -10);
        gridB.addAnchorPointVector(-10, -20);
        gridB.addAnchorPointVector(-20, -20);
        result = gridService.contains(gridA, gridB);
        Assert.assertFalse(result);
        result = gridService.touches(gridA, gridB);
        Assert.assertFalse(result);

        // 网格A不包含且不外切0个锚点的网格B
        gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        gridB = new Grid();
        result = gridService.contains(gridA, gridB);
        Assert.assertFalse(result);
        result = gridService.touches(gridA, gridB);
        Assert.assertFalse(result);

        // 网格A不包含且不外切1个锚点的网格B
        gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        gridB = new Grid();
        gridB.addAnchorPointVector(-10, -10);
        result = gridService.contains(gridA, gridB);
        Assert.assertFalse(result);
        result = gridService.touches(gridA, gridB);
        Assert.assertFalse(result);

        // 网格A不包含且不外切2个锚点的网格B
        gridA = new Grid();
        gridA.addAnchorPointVector(0, 0);
        gridA.addAnchorPointVector(0, 100);
        gridA.addAnchorPointVector(100, 100);
        gridA.addAnchorPointVector(100, 0);
        gridB = new Grid();
        gridB.addAnchorPointVector(-10, -10);
        gridB.addAnchorPointVector(-20, -20);
        result = gridService.contains(gridA, gridB);
        Assert.assertFalse(result);
        result = gridService.touches(gridA, gridB);
        Assert.assertFalse(result);
    }

    @Test
    public void testGetGridContainsTargetGrid() {
        // 模拟Grid集合
        List<Grid> grids = new ArrayList<>();
        for (int index = 0; index < 1000; index++) {
            // 网格B完全内含与网格A，网格A包含网格B
            Grid grid = new Grid();
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grids.add(grid);
        }
        // 模拟目标Grid，理论存在
        Grid targetGrid = new Grid();
        targetGrid.addAnchorPointVector(10, 10);
        targetGrid.addAnchorPointVector(10, 20);
        targetGrid.addAnchorPointVector(20, 20);
        targetGrid.addAnchorPointVector(20, 10);

        System.out.println(DateUtil.getSystemTime());
        System.out.println(gridService.getGridContainsTargetGrid(grids, targetGrid));
        System.out.println(DateUtil.getSystemTime());

        // 模拟目标Grid，肯定不存在
        targetGrid = new Grid();
        targetGrid.addAnchorPointVector(-10, -10);
        targetGrid.addAnchorPointVector(-10, -20);
        targetGrid.addAnchorPointVector(-20, -20);
        targetGrid.addAnchorPointVector(-20, -10);

        System.out.println(DateUtil.getSystemTime());
        System.out.println(gridService.getGridContainsTargetGrid(grids, targetGrid));
        System.out.println(DateUtil.getSystemTime());
    }

    @Test
    public void testGetGridTouchesTargetGrid() {
        // 模拟Grid集合
        List<Grid> grids = new ArrayList<>();
        for (int index = 0; index < 1000; index++) {
            // 网格B完全内含与网格A，网格A包含网格B
            Grid grid = new Grid();
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grid.addAnchorPointVector(100 * Math.random(), 100 * Math.random());
            grids.add(grid);
        }
        // 与模拟目标Grid相切的Grid
        Grid grid = new Grid();
        grid.addAnchorPointVector(20, 20);
        grid.addAnchorPointVector(20, 30);
        grid.addAnchorPointVector(30, 30);
        grid.addAnchorPointVector(30, 20);
        grids.add(grid);

        // 模拟目标Grid，一定存在
        Grid targetGrid = new Grid();
        targetGrid.addAnchorPointVector(10, 10);
        targetGrid.addAnchorPointVector(10, 20);
        targetGrid.addAnchorPointVector(20, 20);
        targetGrid.addAnchorPointVector(20, 10);

        System.out.println(DateUtil.getSystemTime());
        System.out.println(gridService.getGridTouchesTargetGrid(grids, targetGrid));
        System.out.println(DateUtil.getSystemTime());

        // 模拟目标Grid，肯定不存在
        targetGrid = new Grid();
        targetGrid.addAnchorPointVector(-10, -10);
        targetGrid.addAnchorPointVector(-10, -20);
        targetGrid.addAnchorPointVector(-20, -20);
        targetGrid.addAnchorPointVector(-20, -10);

        System.out.println(DateUtil.getSystemTime());
        System.out.println(gridService.getGridContainsTargetGrid(grids, targetGrid));
        System.out.println(DateUtil.getSystemTime());
    }
}