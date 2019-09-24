/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.modal.AdjoinStations;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.BureauPartingStation;
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
public class TestStationService {
    @Autowired
    private BureauService bureauService;
    @Autowired
    private StationService stationService;
    @Autowired
    private LinkService linkService;

    @Test
    public void testStationsAdjoin() {
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
        boolean result = stationService.adjoin(stationA, stationB);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、D邻接
        System.out.println(DateUtil.getSystemTime());
        result = stationService.adjoin(stationA, stationD);
        Assert.assertTrue(result);
        System.out.println(DateUtil.getSystemTime());

        //车站A、C不邻接
        System.out.println(DateUtil.getSystemTime());
        result = stationService.adjoin(stationA, stationC);
        Assert.assertFalse(result);
        System.out.println(DateUtil.getSystemTime());
    }

    @Test
    public void testStationJurisdictionYard() {
        //多边形车站
        Station station = new Station();
        station.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        station.setAnchorPointsString("0@0;0@20;20@20;20@0");

        // station管辖车场A
        Yard yardA = new Yard();
        yardA.setAnchorPointsString("5@5");
        Assert.assertTrue(stationService.jurisdiction(station, yardA));

        // station不管辖车场B
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("5@50");
        Assert.assertFalse(stationService.jurisdiction(station, yardB));
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

        Assert.assertTrue(stationService.adjoin(stationA, link));
        Assert.assertTrue(stationService.adjoin(stationB, link));
        Assert.assertFalse(stationService.adjoin(stationC, link));
        Assert.assertTrue(stationService.adjoin(stationD, link));
        Assert.assertFalse(stationService.adjoin(stationE, link));
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
    public void testGetBureauPartingStations() {
        Bureau bureauA = new Bureau();
        bureauA.setId(1);
        bureauA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setId(2);
        bureauB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        bureauService.addOne(bureauB);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("10@10;10@20;20@20;20@10");
        stationService.setJurisdiction(stationA);
        stationService.addOne(stationA);

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("70@70;70@80;80@80;80@70");
        stationB.setBureauParting(true);
        stationService.setJurisdiction(stationB);
        stationService.addOne(stationB);

        Station stationC = new Station();
        stationC.setAnchorPointsString("110@110");
        stationService.setJurisdiction(stationC);
        stationService.addOne(stationC);

        Station stationD = new Station();
        stationD.setId(4);
        stationD.setAnchorPointsString("180@180");
        stationService.setJurisdiction(stationD);
        stationService.addOne(stationD);

        Link linkA = new Link();
        linkA.setId(5);
        linkA.setAnchorPointsString("80@80;90@93;102@105;110@110");
        linkService.addOne(linkA);
        Link linkB = new Link();
        linkB.setId(6);
        linkB.setAnchorPointsString("32@54;123@231");
        linkService.addOne(linkB);

        List<BureauPartingStation> bureauPartingStations = stationService.getBureauPartingStations();
        Assert.assertNotNull(bureauPartingStations);
        Assert.assertEquals(1, bureauPartingStations.size());
        Assert.assertNotNull(bureauPartingStations.get(0).getBureauPartingStation());
        Assert.assertTrue(bureauPartingStations.get(0).getBureauPartingStation().equals(stationB));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinStation());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinStation().equals(stationC));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinLink());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinLink().equals(linkA));
        Assert.assertNotNull(bureauPartingStations.get(0).getJurisdictionBureau());
        Assert.assertTrue(bureauPartingStations.get(0).getJurisdictionBureau().equals(bureauA));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinBureau());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinBureau().equals(bureauB));

        bureauPartingStations = stationService.getBureauPartingStations(bureauA);
        Assert.assertNotNull(bureauPartingStations);
        Assert.assertEquals(1, bureauPartingStations.size());
        Assert.assertNotNull(bureauPartingStations.get(0).getBureauPartingStation());
        Assert.assertTrue(bureauPartingStations.get(0).getBureauPartingStation().equals(stationB));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinStation());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinStation().equals(stationC));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinLink());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinLink().equals(linkA));
        Assert.assertNotNull(bureauPartingStations.get(0).getJurisdictionBureau());
        Assert.assertTrue(bureauPartingStations.get(0).getJurisdictionBureau().equals(bureauA));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinBureau());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinBureau().equals(bureauB));

        bureauPartingStations = stationService.getBureauPartingStations(bureauA, bureauB);
        Assert.assertNotNull(bureauPartingStations);
        Assert.assertEquals(1, bureauPartingStations.size());
        Assert.assertNotNull(bureauPartingStations.get(0).getBureauPartingStation());
        Assert.assertTrue(bureauPartingStations.get(0).getBureauPartingStation().equals(stationB));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinStation());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinStation().equals(stationC));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinLink());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinLink().equals(linkA));
        Assert.assertNotNull(bureauPartingStations.get(0).getJurisdictionBureau());
        Assert.assertTrue(bureauPartingStations.get(0).getJurisdictionBureau().equals(bureauA));
        Assert.assertNotNull(bureauPartingStations.get(0).getAdjoinBureau());
        Assert.assertTrue(bureauPartingStations.get(0).getAdjoinBureau().equals(bureauB));
    }


    @Test
    @Transactional
    @Rollback
    public void testGetAdjoinStationses() {
        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("10@10;10@20;20@20;20@10");
        stationService.addOne(stationA);

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("70@70;70@80;80@80;80@70");
        stationB.setBureauParting(true);
        stationService.addOne(stationB);

        Station stationC = new Station();
        stationC.setAnchorPointsString("110@110");
        stationService.addOne(stationC);

        Station stationD = new Station();
        stationD.setId(4);
        stationD.setAnchorPointsString("180@180");
        stationService.addOne(stationD);

        Link linkA = new Link();
        linkA.setId(5);
        linkA.setAnchorPointsString("80@80;90@93;102@105;110@110");
        linkService.addOne(linkA);
        Link linkB = new Link();
        linkB.setId(6);
        linkB.setAnchorPointsString("32@54;123@231");
        linkService.addOne(linkB);

        List<AdjoinStations> adjoinStationses = stationService.getAdjoinStationses(stationA);
        Assert.assertNotNull(adjoinStationses);
        Assert.assertEquals(0, adjoinStationses.size());

        adjoinStationses = stationService.getAdjoinStationses(stationB);
        Assert.assertNotNull(adjoinStationses);
        Assert.assertEquals(1, adjoinStationses.size());
        Assert.assertNotNull(adjoinStationses.get(0).getStationA());
        Assert.assertTrue(adjoinStationses.get(0).getStationA().equals(stationB));
        Assert.assertNotNull(adjoinStationses.get(0).getLink());
        Assert.assertTrue(adjoinStationses.get(0).getLink().equals(linkA));
        Assert.assertNotNull(adjoinStationses.get(0).getStationB());
        Assert.assertTrue(adjoinStationses.get(0).getStationB().equals(stationC));

        adjoinStationses = stationService.getAdjoinStationses(stationC);
        Assert.assertNotNull(adjoinStationses);
        Assert.assertEquals(1, adjoinStationses.size());
        Assert.assertNotNull(adjoinStationses.get(0).getStationA());
        Assert.assertTrue(adjoinStationses.get(0).getStationA().equals(stationC));
        Assert.assertNotNull(adjoinStationses.get(0).getLink());
        Assert.assertTrue(adjoinStationses.get(0).getLink().equals(linkA));
        Assert.assertNotNull(adjoinStationses.get(0).getStationB());
        Assert.assertTrue(adjoinStationses.get(0).getStationB().equals(stationB));

        adjoinStationses = stationService.getAdjoinStationses(stationD);
        Assert.assertNotNull(adjoinStationses);
        Assert.assertEquals(0, adjoinStationses.size());

        AdjoinStations adjoinStations = stationService.getAdjoinStations(linkA);
        Assert.assertNotNull(adjoinStations);
        Assert.assertNotNull(adjoinStations.getStationA());
        if (stationB.equals(adjoinStations.getStationA())) {
            Assert.assertNotNull(adjoinStations.getStationB());
            Assert.assertTrue(adjoinStations.getStationB().equals(stationC));
        }
        Assert.assertNotNull(adjoinStations.getLink());
        Assert.assertTrue(adjoinStations.getLink().equals(linkA));
        if (stationC.equals(adjoinStations.getStationA())) {
            Assert.assertNotNull(adjoinStations.getStationB());
            Assert.assertTrue(adjoinStations.getStationB().equals(stationB));
        }

        adjoinStations = stationService.getAdjoinStations(linkB);
        Assert.assertNull(adjoinStations);
    }

    @Test
    public void testSetJurisdiction() {
        Bureau bureauA = new Bureau();
        bureauA.setId(1);
        bureauA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setId(2);
        bureauB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        bureauService.addOne(bureauB);

        Station stationA = new Station();
        stationA.setId(3);
        stationA.setAnchorPointsString("10@10");
        stationService.setJurisdiction(stationA);
        Assert.assertEquals(bureauA.getId(), stationA.getJurisdictionBureauId());

        Station stationB = new Station();
        stationB.setId(4);
        stationB.setAnchorPointsString("110@110");
        stationService.setJurisdiction(stationB);
        Assert.assertEquals(bureauB.getId(), stationB.getJurisdictionBureauId());

        Station stationC = new Station();
        stationC.setId(5);
        stationC.setAnchorPointsString("300@300");
        stationService.setJurisdiction(stationC);
        Assert.assertNotEquals(bureauA.getId(), stationC.getJurisdictionBureauId());
        Assert.assertNotEquals(bureauB.getId(), stationC.getJurisdictionBureauId());
    }

    @Test
    public void testAddOne() {
        //委托StationDao
    }

    @Test
    public void testUpdateOne() {
        //委托StationDao
    }

    @Test
    public void testDeleteOne() {
        //委托StationDao
    }
}
