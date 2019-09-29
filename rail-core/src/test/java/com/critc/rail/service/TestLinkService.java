/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.Yard;
import com.critc.util.date.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * what:    (这里用一句话描述这个类的作用). <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestLinkService {
    @Autowired
    private LinkService linkService;

    @Test
    @Transactional
    @Rollback
    public void testAdjoinStations() {
        //点形车站
        Station stationA = new Station();
        stationA.setAnchorPointsString("0@0");
        Station stationB = new Station();
        stationB.setAnchorPointsString("100@100");
        Station stationC = new Station();
        stationC.setAnchorPointsString("30@40");
        //多边形车站
        Station stationD = new Station();
        stationD.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationD.setAnchorPointsString("0@0;0@20;10@20");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@30");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);
        for (int index = 0; index < 1000; index++) {
            link = new Link();
            link.setAnchorPointsString("200@200;300@300;400@400;23@53");
            linkService.addOne(link);
        }

        //车站A、B邻接
        System.out.println(DateUtil.getSystemTime());
        boolean result = linkService.adjoin(stationA, stationB);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、D邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.adjoin(stationA, stationD);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、C不邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.adjoin(stationA, stationC);
        Assert.assertFalse(result);
        System.out.println(DateUtil.getSystemTime());
    }

    @Test
    @Transactional
    @Rollback
    public void testAdjoinStationYard() {
        //点形车站
        Station stationA = new Station();
        stationA.setAnchorPointsString("0@0");
        //多边形车站
        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("0@0;0@20;20@20;20@0");

        Yard yardA = new Yard();
        yardA.setAnchorPointsString("100@100");
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("30@40");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@40");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);

        //车站A、yardA邻接
        boolean result = linkService.adjoin(stationA, yardA);
        Assert.assertTrue(result);

        //车站A、yardB不邻接
        result = linkService.adjoin(stationA, yardB);
        Assert.assertFalse(result);

        //车站B、yardA不邻接
        result = linkService.adjoin(stationB, yardA);
        Assert.assertFalse(result);

        //车站B、yardB邻接
        result = linkService.adjoin(stationB, yardB);
        Assert.assertTrue(result);
    }

    @Test
    @Transactional
    @Rollback
    public void testAdjoinYards() {
        //点形车站
        Yard yardA = new Yard();
        yardA.setAnchorPointsString("0@0");
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("100@100");
        Yard yardC = new Yard();
        yardC.setAnchorPointsString("30@40");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@30");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);
        for (int index = 0; index < 1000; index++) {
            link = new Link();
            link.setAnchorPointsString("200@200;300@300;400@400;23@53");
            linkService.addOne(link);
        }

        //车站A、B邻接
        System.out.println(DateUtil.getSystemTime());
        boolean result = linkService.adjoin(yardA, yardB);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、C不邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.adjoin(yardA, yardC);
        Assert.assertFalse(result);
        System.out.println(DateUtil.getSystemTime());
    }

    @Test
    public void testStationLinkAdjoin() {
        //点形车站
        Station stationA = new Station();
        stationA.setAnchorPointsString("0@0");
        Station stationB = new Station();
        stationB.setAnchorPointsString("100@100");
        Station stationC = new Station();
        stationC.setAnchorPointsString("50@30");
        //多边形车站
        Station stationD = new Station();
        stationD.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationD.setAnchorPointsString("0@0;0@20;10@20");
        Station stationE = new Station();
        stationE.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationE.setAnchorPointsString("20@10;20@20;10@20");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");

        Assert.assertTrue(linkService.adjoin(stationA, link));
        Assert.assertTrue(linkService.adjoin(stationB, link));
        Assert.assertFalse(linkService.adjoin(stationC, link));
        Assert.assertTrue(linkService.adjoin(stationD, link));
        Assert.assertFalse(linkService.adjoin(stationE, link));
    }

    @Test
    public void testYardLinkAdjoin() {
        Yard yardA = new Yard();
        yardA.setAnchorPointsString("0@0");
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("100@100");
        Yard yardC = new Yard();
        yardC.setAnchorPointsString("50@30");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");

        Assert.assertTrue(linkService.adjoin(yardA, link));
        Assert.assertTrue(linkService.adjoin(yardB, link));
        Assert.assertFalse(linkService.adjoin(yardC, link));
    }

    @Test
    public void testGetMany() {
        //委托StationDao
    }

    @Test
    public void testGetOne() {
        //委托StationDao
    }

    @Test
    public void testGetAll() {
        //委托StationDao
    }

    @Test
    @Transactional
    @Rollback
    public void testGetManyAdjoinStations() {
        //点形车站
        Station stationA = new Station();
        stationA.setAnchorPointsString("0@0");
        Station stationB = new Station();
        stationB.setAnchorPointsString("100@100");
        Station stationC = new Station();
        stationC.setAnchorPointsString("30@40");
        //多边形车站
        Station stationD = new Station();
        stationD.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationD.setAnchorPointsString("0@0;0@20;10@20");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@30");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);
        for (int index = 0; index < 1000; index++) {
            link = new Link();
            link.setAnchorPointsString("200@200;300@300;400@400;23@53");
            linkService.addOne(link);
        }

        //车站A、B邻接
        System.out.println(DateUtil.getSystemTime());
        List<Link> result = linkService.getMany(stationA, stationB);
        Assert.assertFalse(result.isEmpty());
        System.out.println(DateUtil.getSystemTime());

        //车站A、D邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.getMany(stationA, stationD);
        Assert.assertFalse(result.isEmpty());
        System.out.println(DateUtil.getSystemTime());

        //车站A、C不邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.getMany(stationA, stationC);
        Assert.assertTrue(result.isEmpty());
        System.out.println(DateUtil.getSystemTime());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetManyAdjoinStationYard() {
        //点形车站
        Station stationA = new Station();
        stationA.setAnchorPointsString("0@0");
        //多边形车站
        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("0@0;0@20;20@20;20@0");

        Yard yardA = new Yard();
        yardA.setAnchorPointsString("100@100");
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("30@40");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@40");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);

        //车站A、yardA邻接
        List<Link> result = linkService.getMany(stationA, yardA);
        Assert.assertFalse(result.isEmpty());

        //车站A、yardB不邻接
        result = linkService.getMany(stationA, yardB);
        Assert.assertTrue(result.isEmpty());

        //车站B、yardA不邻接
        result = linkService.getMany(stationB, yardA);
        Assert.assertTrue(result.isEmpty());

        //车站B、yardB邻接
        result = linkService.getMany(stationB, yardB);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetManyAdjoinYards() {
        //点形车站
        Yard yardA = new Yard();
        yardA.setAnchorPointsString("0@0");
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("100@100");
        Yard yardC = new Yard();
        yardC.setAnchorPointsString("30@40");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@30");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);
        for (int index = 0; index < 1000; index++) {
            link = new Link();
            link.setAnchorPointsString("200@200;300@300;400@400;23@53");
            linkService.addOne(link);
        }

        //车站A、B邻接
        System.out.println(DateUtil.getSystemTime());
        List<Link> result = linkService.getMany(yardA, yardA);
        Assert.assertFalse(result.isEmpty());
        System.out.println(DateUtil.getSystemTime());

        //车站A、C不邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.getMany(yardA, yardC);
        Assert.assertTrue(result.isEmpty());
        System.out.println(DateUtil.getSystemTime());
    }

    @Test
    public void testAddOne() {
        //委托Dao
    }

    @Test
    public void testUpdateOne() {
        //委托Dao
    }

    @Test
    public void testDeleteOne() {
        //委托Dao
    }
}
