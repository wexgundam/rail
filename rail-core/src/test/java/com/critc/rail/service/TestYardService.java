/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.dao.YardDao;
import com.critc.rail.modal.AdjoinStationYard;
import com.critc.rail.modal.AdjoinYards;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Link;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.modal.Yard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
public class TestYardService {
    @Autowired
    private BureauService bureauService;
    @Autowired
    private TrainlineDeportService trainlineDeportService;
    @Autowired
    private StationService stationService;
    @Autowired
    private YardService yardService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private YardDao yardDao;

    @Test
    @Transactional
    @Rollback
    public void testStationYardAdjoin() {
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
        yardB.setAnchorPointsString("30@30");

        Link link = new Link();
        link.setAnchorPointsString("0@0;100@100");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("20@20;30@30");
        linkService.addOne(link);
        link = new Link();
        link.setAnchorPointsString("200@200;300@300;400@400;23@53");
        linkService.addOne(link);


        //车站A、yardA邻接
        boolean result = yardService.adjoin(stationA, yardA);
        Assert.assertTrue(result);

        //车站A、yardB不邻接
        result = yardService.adjoin(stationA, yardB);
        Assert.assertFalse(result);

        //车站B、yardA不邻接
        result = yardService.adjoin(stationB, yardA);
        Assert.assertFalse(result);

        //车站B、yardB邻接
        result = yardService.adjoin(stationB, yardB);
        Assert.assertTrue(result);
    }

    @Test
    @Transactional
    @Rollback
    public void testYardsAdjoin() {
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


        //车场A、B邻接
        boolean result = yardService.adjoin(yardA, yardB);
        Assert.assertTrue(result);

        //车场A、C不邻接
        result = yardService.adjoin(yardA, yardC);
        Assert.assertFalse(result);
    }

    @Test
    public void testGetMany() {
        //委托Dao
    }

    @Test
    public void testGetOne() {
        //委托Dao
    }

    @Test
    public void testGetAll() {
        //委托Dao
    }

    @Test
    public void testGetManyYard() {
        Bureau bureauA = new Bureau();
        bureauA.setAnchorPointsString("00@0;0@200;200@200;200@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setAnchorPointsString("200@200;200@400;400@400;400@200");
        bureauService.addOne(bureauB);

        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("00@0;0@100;100@100;100@0");
        trainlineDeportService.setJurisdiction(trainlineDeportA);
        trainlineDeportService.addOne(trainlineDeportA);
        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("200@200;200@300;300@300;300@200");
        trainlineDeportService.setJurisdiction(trainlineDeportB);
        trainlineDeportService.addOne(trainlineDeportB);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("10@10;10@20;20@20;20@10");
        stationService.setJurisdiction(stationA);
        stationService.addOne(stationA);

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("210@210;210@220;220@220;220@210");
        stationService.setJurisdiction(stationB);
        stationService.addOne(stationB);

        Yard yardA = new Yard();
        yardA.setAnchorPointsString("15@15");
        yardService.setJurisdiction(yardA);
        yardService.addOne(yardA);

        Yard yardB = new Yard();
        yardB.setAnchorPointsString("215@215");
        yardService.setJurisdiction(yardB);
        yardService.addOne(yardB);
        try {

            List<Yard> yards = yardService.getMany(stationA);
            Assert.assertFalse(yards.isEmpty());
            Assert.assertEquals(1, yards.size());
            Assert.assertEquals(yardA, yards.get(0));

            yards = yardService.getMany(stationB);
            Assert.assertFalse(yards.isEmpty());
            Assert.assertEquals(1, yards.size());
            Assert.assertEquals(yardB, yards.get(0));

            yards = yardService.getMany(trainlineDeportA);
            Assert.assertFalse(yards.isEmpty());
            Assert.assertEquals(1, yards.size());
            Assert.assertEquals(yardA, yards.get(0));

            yards = yardService.getMany(trainlineDeportB);
            Assert.assertFalse(yards.isEmpty());
            Assert.assertEquals(1, yards.size());
            Assert.assertEquals(yardB, yards.get(0));

            yards = yardService.getMany(bureauA);
            Assert.assertFalse(yards.isEmpty());
            Assert.assertEquals(1, yards.size());
            Assert.assertEquals(yardA, yards.get(0));

            yards = yardService.getMany(bureauB);
            Assert.assertFalse(yards.isEmpty());
            Assert.assertEquals(1, yards.size());
            Assert.assertEquals(yardB, yards.get(0));
        } finally {
            yardService.deleteOne(yardA);
            yardService.deleteOne(yardB);
            stationService.deleteOne(stationA);
            stationService.deleteOne(stationB);
            trainlineDeportService.deleteOne(trainlineDeportA);
            trainlineDeportService.deleteOne(trainlineDeportB);
            bureauService.deleteOne(bureauA);
            bureauService.deleteOne(bureauB);
        }

    }

    @Test
    @Transactional
    @Rollback
    public void testGetAdjoins() {
        //点形车站
        Station stationA = new Station();
        stationA.setAnchorPointsString("0@0");
        stationService.addOne(stationA);
        //多边形车站
        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("100@100;100@120;120@120;120@100");
        stationService.addOne(stationB);

        Yard yardA = new Yard();
        yardA.setAnchorPointsString("30@30");
        yardService.addOne(yardA);
        Yard yardB = new Yard();
        yardB.setAnchorPointsString("80@80");
        yardService.addOne(yardB);
        Yard yardC = new Yard();
        yardC.setAnchorPointsString("50@50");
        yardService.addOne(yardC);

        Link linkA = new Link();
        linkA.setAnchorPointsString("0@0;30@30");
        linkService.addOne(linkA);
        Link linkB = new Link();
        linkB.setAnchorPointsString("30@30;80@80");
        linkService.addOne(linkB);
        Link linkC = new Link();
        linkC.setAnchorPointsString("80@80;100@100");
        linkService.addOne(linkC);
        Link linkD = new Link();
        linkD.setAnchorPointsString("30@30;50@50");
        linkService.addOne(linkD);

        AdjoinYards adjoinYards = yardService.getAdjoinYards(linkA);
        Assert.assertNotNull(adjoinYards);
        Assert.assertEquals(linkA, adjoinYards.getLink());
        Assert.assertEquals(yardA, adjoinYards.getYardA());

        adjoinYards = yardService.getAdjoinYards(linkB);
        Assert.assertNotNull(adjoinYards);
        Assert.assertEquals(linkB, adjoinYards.getLink());
        if (yardA.equals(adjoinYards.getYardA())) {
            Assert.assertEquals(yardB, adjoinYards.getYardB());
        } else if (yardA.equals(adjoinYards.getYardB())) {
            Assert.assertEquals(yardB, adjoinYards.getYardA());
        } else {
            Assert.fail("AdjointYards yardA and yardB are null.");
        }

        adjoinYards = yardService.getAdjoinYards(linkC);
        Assert.assertNotNull(adjoinYards);
        Assert.assertEquals(linkC, adjoinYards.getLink());
        Assert.assertEquals(yardB, adjoinYards.getYardA());

        List<AdjoinYards> adjoinYardses = yardService.getAdjoinYards(yardA);
        Assert.assertFalse(adjoinYardses.isEmpty());
        Assert.assertEquals(2, adjoinYardses.size());
        if (linkB.equals(adjoinYardses.get(0).getLink())) {
            Assert.assertEquals(yardA, adjoinYardses.get(0).getYardA());
            Assert.assertEquals(yardB, adjoinYardses.get(0).getYardB());
        } else if (linkD.equals(adjoinYardses.get(0).getLink())) {
            Assert.assertEquals(yardA, adjoinYardses.get(0).getYardA());
            Assert.assertEquals(yardC, adjoinYardses.get(0).getYardB());
        } else {
            Assert.fail("wrong result.");
        }

        adjoinYardses = yardService.getAdjoinYards(yardB);
        Assert.assertFalse(adjoinYardses.isEmpty());
        Assert.assertEquals(1, adjoinYardses.size());
        Assert.assertEquals(yardB, adjoinYardses.get(0).getYardA());
        Assert.assertEquals(yardA, adjoinYardses.get(0).getYardB());
        Assert.assertEquals(linkB, adjoinYardses.get(0).getLink());

        List<AdjoinStationYard> adjoinStationYards = yardService.getAdjoinStationYards(stationA);
        Assert.assertFalse(adjoinYardses.isEmpty());
        Assert.assertEquals(1, adjoinYardses.size());
        Assert.assertEquals(stationA, adjoinStationYards.get(0).getStation());
        Assert.assertEquals(yardA, adjoinStationYards.get(0).getYard());
        Assert.assertEquals(linkA, adjoinStationYards.get(0).getLink());

        adjoinStationYards = yardService.getAdjoinStationYards(stationB);
        Assert.assertFalse(adjoinYardses.isEmpty());
        Assert.assertEquals(1, adjoinYardses.size());
        Assert.assertEquals(stationB, adjoinStationYards.get(0).getStation());
        Assert.assertEquals(yardB, adjoinStationYards.get(0).getYard());
        Assert.assertEquals(linkC, adjoinStationYards.get(0).getLink());
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
