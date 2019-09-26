/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.dao.LinkDao;
import com.critc.rail.modal.AdjoinTrainlineDeports;
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
 * @author 靳磊 created on 2019/9/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestTrainlineDeportService {
    @Autowired
    private BureauService bureauService;
    @Autowired
    private TrainlineDeportService trainlineDeportService;
    @Autowired
    private StationService stationService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private LinkDao linkDao;

    @Test
    @Transactional
    @Rollback
    public void testAdjoin() {
        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        trainlineDeportService.addOne(trainlineDeportA);
        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        trainlineDeportService.addOne(trainlineDeportB);
        TrainlineDeport trainlineDeportC = new TrainlineDeport();
        trainlineDeportC.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        trainlineDeportService.addOne(trainlineDeportC);

        boolean result = trainlineDeportService.adjoin(trainlineDeportA, trainlineDeportB);
        Assert.assertTrue(result);
        result = trainlineDeportService.adjoin(trainlineDeportA, trainlineDeportC);
        Assert.assertTrue(result);
        result = trainlineDeportService.adjoin(trainlineDeportB, trainlineDeportC);
        Assert.assertFalse(result);
    }

    @Test
    @Transactional
    @Rollback
    public void testJurisdiction() {
        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        trainlineDeportService.addOne(trainlineDeportA);
        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        trainlineDeportService.addOne(trainlineDeportB);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("10@10;10@20;20@20;20@10");

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("70@70;70@80;80@80;80@70");
        stationB.setBureauParting(true);

        Station stationC = new Station();
        stationC.setAnchorPointsString("110@110");

        boolean result = trainlineDeportService.jurisdiction(trainlineDeportA, stationA);
        Assert.assertTrue(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportA, stationB);
        Assert.assertTrue(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportA, stationC);
        Assert.assertFalse(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, stationA);
        Assert.assertFalse(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, stationB);
        Assert.assertFalse(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, stationC);
        Assert.assertTrue(result);

        Link linkA = new Link();
        linkA.setAnchorPointsString("0@0;100@100");
        Link linkB = new Link();
        linkB.setAnchorPointsString("20@20;30@30");
        Link linkC = new Link();
        linkC.setAnchorPointsString("200@200;300@300;400@400;23@53");

        result = trainlineDeportService.jurisdiction(trainlineDeportA, linkA);
        Assert.assertTrue(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportA, linkB);
        Assert.assertTrue(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportA, linkC);
        Assert.assertFalse(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, linkA);
        Assert.assertFalse(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, linkB);
        Assert.assertFalse(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, linkC);
        Assert.assertFalse(result);

        Yard yard = new Yard();
        yard.setAnchorPointsString("5@5");

        result = trainlineDeportService.jurisdiction(trainlineDeportA, yard);
        Assert.assertTrue(result);
        result = trainlineDeportService.jurisdiction(trainlineDeportB, yard);
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
    public void testGetAdjoinTrainlineDeportses() {
        Bureau bureauA = new Bureau();
        bureauA.setId(1);
        bureauA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setId(2);
        bureauB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        bureauService.addOne(bureauB);

        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("80@80;80@100;100@100;100@80");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportA);
        trainlineDeportService.addOne(trainlineDeportA);
        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("100@100;100@150;150@150;150@100");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportB);
        trainlineDeportService.addOne(trainlineDeportB);
        TrainlineDeport trainlineDeportC = new TrainlineDeport();
        trainlineDeportC.setAnchorPointsString("40@40;40@80;80@80;80@40");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportC);
        trainlineDeportService.addOne(trainlineDeportC);
        TrainlineDeport trainlineDeportD = new TrainlineDeport();
        trainlineDeportD.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportD);
        trainlineDeportService.addOne(trainlineDeportD);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("90@90;90@95;95@95;95@90");
        stationService.setJurisdiction(stationA);
        stationService.addOne(stationA);

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("130@130;130@140;140@140;140@130");
        stationService.setJurisdiction(stationB);
        stationService.addOne(stationB);

        Station stationC = new Station();
        stationC.setAnchorPointsString("50@50");
        stationService.setJurisdiction(stationC);
        stationService.addOne(stationC);

        Link linkA = new Link();
        linkA.setAnchorPointsString("95@95;130@130");
        linkService.addOne(linkA);
        Link linkB = new Link();
        linkB.setAnchorPointsString("20@20;30@30");
        linkService.addOne(linkB);
        Link linkC = new Link();
        linkC.setAnchorPointsString("50@50;90@90");
        linkService.addOne(linkC);

        try {
            List<AdjoinTrainlineDeports> adjoinTrainlineDeportses = trainlineDeportService.getAdjoinTrainlineDeportses(bureauA, bureauB);
            Assert.assertNotNull(adjoinTrainlineDeportses);
            Assert.assertEquals(1, adjoinTrainlineDeportses.size());
            Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(0).getTrainlineDeportA());
            Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(0).getStationA());
            Assert.assertEquals(linkA, adjoinTrainlineDeportses.get(0).getLink());
            Assert.assertEquals(stationB, adjoinTrainlineDeportses.get(0).getStationB());
            Assert.assertEquals(trainlineDeportB, adjoinTrainlineDeportses.get(0).getTrainlineDeportB());

            adjoinTrainlineDeportses = trainlineDeportService.getAdjoinTrainlineDeportses(trainlineDeportA);
            Assert.assertNotNull(adjoinTrainlineDeportses);
            Assert.assertEquals(2, adjoinTrainlineDeportses.size());
            if (linkA.equals(adjoinTrainlineDeportses.get(0).getLink())) {
                Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(0).getTrainlineDeportA());
                Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(0).getStationA());
                Assert.assertEquals(linkA, adjoinTrainlineDeportses.get(0).getLink());
                Assert.assertEquals(stationB, adjoinTrainlineDeportses.get(0).getStationB());
                Assert.assertEquals(trainlineDeportB, adjoinTrainlineDeportses.get(0).getTrainlineDeportB());

                Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(1).getTrainlineDeportA());
                Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(1).getStationA());
                Assert.assertEquals(linkC, adjoinTrainlineDeportses.get(1).getLink());
                Assert.assertEquals(stationC, adjoinTrainlineDeportses.get(1).getStationB());
                Assert.assertEquals(trainlineDeportC, adjoinTrainlineDeportses.get(1).getTrainlineDeportB());
            } else if (linkA.equals(adjoinTrainlineDeportses.get(1).getLink())) {
                Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(0).getTrainlineDeportA());
                Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(0).getStationA());
                Assert.assertEquals(linkC, adjoinTrainlineDeportses.get(0).getLink());
                Assert.assertEquals(stationC, adjoinTrainlineDeportses.get(0).getStationB());
                Assert.assertEquals(trainlineDeportC, adjoinTrainlineDeportses.get(0).getTrainlineDeportB());

                Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(1).getTrainlineDeportA());
                Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(1).getStationA());
                Assert.assertEquals(linkA, adjoinTrainlineDeportses.get(1).getLink());
                Assert.assertEquals(stationB, adjoinTrainlineDeportses.get(1).getStationB());
                Assert.assertEquals(trainlineDeportB, adjoinTrainlineDeportses.get(1).getTrainlineDeportB());
            }

            adjoinTrainlineDeportses = trainlineDeportService.getAdjoinTrainlineDeportses(trainlineDeportB);
            Assert.assertNotNull(adjoinTrainlineDeportses);
            Assert.assertEquals(1, adjoinTrainlineDeportses.size());
            Assert.assertEquals(stationB, adjoinTrainlineDeportses.get(0).getStationA());
            Assert.assertEquals(trainlineDeportB, adjoinTrainlineDeportses.get(0).getTrainlineDeportA());
            Assert.assertEquals(linkA, adjoinTrainlineDeportses.get(0).getLink());
            Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(0).getTrainlineDeportB());
            Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(0).getStationB());

            adjoinTrainlineDeportses = trainlineDeportService.getAdjoinTrainlineDeportses(trainlineDeportC);
            Assert.assertNotNull(adjoinTrainlineDeportses);
            Assert.assertEquals(1, adjoinTrainlineDeportses.size());
            Assert.assertEquals(stationA, adjoinTrainlineDeportses.get(0).getStationB());
            Assert.assertEquals(trainlineDeportA, adjoinTrainlineDeportses.get(0).getTrainlineDeportB());
            Assert.assertEquals(linkC, adjoinTrainlineDeportses.get(0).getLink());
            Assert.assertEquals(trainlineDeportC, adjoinTrainlineDeportses.get(0).getTrainlineDeportA());
            Assert.assertEquals(stationC, adjoinTrainlineDeportses.get(0).getStationA());
        } finally {
            linkDao.clear();
            stationService.deleteOne(stationA);
            stationService.deleteOne(stationB);
            stationService.deleteOne(stationC);
            trainlineDeportService.deleteOne(trainlineDeportA);
            trainlineDeportService.deleteOne(trainlineDeportB);
            trainlineDeportService.deleteOne(trainlineDeportC);
            trainlineDeportService.deleteOne(trainlineDeportD);
            bureauService.deleteOne(bureauA);
            bureauService.deleteOne(bureauB);
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testGetJurisdiction() {
        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("80@80;80@100;100@100;100@80");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportA);
        trainlineDeportService.addOne(trainlineDeportA);
        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("100@100;100@150;150@150;150@100");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportB);
        trainlineDeportService.addOne(trainlineDeportB);
        TrainlineDeport trainlineDeportC = new TrainlineDeport();
        trainlineDeportC.setAnchorPointsString("40@40;40@80;80@80;80@40");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportC);
        trainlineDeportService.addOne(trainlineDeportC);
        TrainlineDeport trainlineDeportD = new TrainlineDeport();
        trainlineDeportD.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportD);
        trainlineDeportService.addOne(trainlineDeportD);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("90@90;90@95;95@95;95@90");
        stationService.setJurisdiction(stationA);
        stationService.addOne(stationA);

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("130@130;130@140;140@140;140@130");
        stationService.setJurisdiction(stationB);
        stationService.addOne(stationB);

        Station stationC = new Station();
        stationC.setAnchorPointsString("-500@-500");
        stationService.setJurisdiction(stationC);
        stationService.addOne(stationC);

        TrainlineDeport jurisdiction = trainlineDeportService.getJurisdiction(stationA);
        Assert.assertNotNull(jurisdiction);
        Assert.assertEquals(trainlineDeportA, jurisdiction);
        jurisdiction = trainlineDeportService.getJurisdiction(stationC);
        Assert.assertNull(jurisdiction);
    }

    @Test
    @Transactional
    @Rollback
    public void testSetJurisdiction() {
        Bureau bureauA = new Bureau();
        bureauA.setId(1);
        bureauA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setId(2);
        bureauB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        bureauService.addOne(bureauB);

        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("80@80;80@100;100@100;100@80");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportA);
        Assert.assertEquals(bureauA.getId(), trainlineDeportA.getJurisdictionBureauId());
        trainlineDeportService.addOne(trainlineDeportA);
        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("100@100;100@150;150@150;150@100");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportB);
        Assert.assertEquals(bureauB.getId(), trainlineDeportB.getJurisdictionBureauId());
        TrainlineDeport trainlineDeportC = new TrainlineDeport();
        trainlineDeportC.setAnchorPointsString("40@40;40@80;80@80;80@40");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportC);
        Assert.assertEquals(bureauA.getId(), trainlineDeportC.getJurisdictionBureauId());
        TrainlineDeport trainlineDeportD = new TrainlineDeport();
        trainlineDeportD.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        trainlineDeportService.setJurisdictionBureau(trainlineDeportD);
        Assert.assertNotEquals(bureauA.getId(), trainlineDeportD.getJurisdictionBureauId());
        Assert.assertNotEquals(bureauB.getId(), trainlineDeportD.getJurisdictionBureauId());
    }
}
