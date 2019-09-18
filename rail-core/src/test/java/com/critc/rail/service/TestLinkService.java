/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
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
    public void testAdjoinStationsWithLinks() {
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

        List<Link> links = new ArrayList<>();
        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        links.add(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@30");
        links.add(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        links.add(link);
        for (int index = 0; index < 1000; index++) {
            link = new Link();
            link.setAnchorPointsString("200@200;300@300;400@400;23@53");
            links.add(link);
        }

        //车站A、B邻接
        System.out.println(DateUtil.getSystemTime());
        boolean result = linkService.adjoin(links, stationA, stationB);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、D邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.adjoin(links, stationA, stationD);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、C不邻接
        System.out.println(DateUtil.getSystemTime());
        result = linkService.adjoin(links, stationA, stationC);
        Assert.assertFalse(result);
        System.out.println(DateUtil.getSystemTime());
    }
}
