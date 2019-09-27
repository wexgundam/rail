/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.service;

import com.critc.network.modal.Grid;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
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
public class TestBureauService {
    @Autowired
    private BureauService bureauService;

    @Test
    @Transactional
    @Rollback
    public void testAdjoin() {
        Bureau bureauA = new Bureau();
        bureauA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        bureauService.addOne(bureauB);
        Bureau bureauC = new Bureau();
        bureauC.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        bureauService.addOne(bureauC);

        boolean result = bureauService.adjoin(bureauA, bureauB);
        Assert.assertTrue(result);
        result = bureauService.adjoin(bureauA, bureauC);
        Assert.assertTrue(result);
        result = bureauService.adjoin(bureauB, bureauC);
        Assert.assertFalse(result);
    }

    @Test
    @Transactional
    @Rollback
    public void testJurisdiction() {
        Bureau bureauA = new Bureau();
        bureauA.setAnchorPointsString("0@0;0@100;100@100;100@0");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setAnchorPointsString("100@100;100@200;200@200;200@100");
        bureauService.addOne(bureauB);

        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("10@10;10@20;20@20;20@10");

        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("70@70;70@80;80@80;80@70");

        boolean result = bureauService.jurisdiction(bureauA, trainlineDeportA);
        Assert.assertTrue(result);
        result = bureauService.jurisdiction(bureauA, trainlineDeportB);
        Assert.assertTrue(result);
        result = bureauService.jurisdiction(bureauB, trainlineDeportA);
        Assert.assertFalse(result);
        result = bureauService.jurisdiction(bureauB, trainlineDeportB);
        Assert.assertFalse(result);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("10@10;10@20;20@20;20@10");

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("70@70;70@80;80@80;80@70");
        stationB.setBureauParting(true);

        Station stationC = new Station();
        stationC.setAnchorPointsString("110@110");

        result = bureauService.jurisdiction(bureauA, stationA);
        Assert.assertTrue(result);
        result = bureauService.jurisdiction(bureauA, stationB);
        Assert.assertTrue(result);
        result = bureauService.jurisdiction(bureauA, stationC);
        Assert.assertFalse(result);
        result = bureauService.jurisdiction(bureauB, stationA);
        Assert.assertFalse(result);
        result = bureauService.jurisdiction(bureauB, stationB);
        Assert.assertFalse(result);
        result = bureauService.jurisdiction(bureauB, stationC);
        Assert.assertTrue(result);
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
    @Transactional
    @Rollback
    public void testGetAdjoinBureauses() {
        Bureau bureauA = new Bureau();
        bureauA.setAnchorPointsString("80@80;80@100;100@100;100@80");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setAnchorPointsString("100@100;100@150;150@150;150@100");
        bureauService.addOne(bureauB);
        Bureau bureauC = new Bureau();
        bureauC.setAnchorPointsString("40@40;40@80;80@80;80@40");
        bureauService.addOne(bureauC);
        Bureau bureauD = new Bureau();
        bureauD.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        bureauService.addOne(bureauD);

        List<Bureau> adjoinBureaus = bureauService.getAdjoinBureaus(bureauA);
        Assert.assertEquals(2, adjoinBureaus.size());
        if (bureauB.equals(adjoinBureaus.get(0))) {
            Assert.assertEquals(bureauC, adjoinBureaus.get(1));
        } else {
            Assert.assertEquals(bureauC, adjoinBureaus.get(0));
        }

        adjoinBureaus = bureauService.getAdjoinBureaus(bureauB);
        Assert.assertEquals(1, adjoinBureaus.size());
        Assert.assertEquals(bureauA, adjoinBureaus.get(0));

        adjoinBureaus = bureauService.getAdjoinBureaus(bureauC);
        Assert.assertEquals(1, adjoinBureaus.size());
        Assert.assertEquals(bureauA, adjoinBureaus.get(0));

        adjoinBureaus = bureauService.getAdjoinBureaus(bureauD);
        Assert.assertTrue(adjoinBureaus.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetJurisdiction() {
        Bureau bureauA = new Bureau();
        bureauA.setAnchorPointsString("80@80;80@100;100@100;100@80");
        bureauService.addOne(bureauA);
        Bureau bureauB = new Bureau();
        bureauB.setAnchorPointsString("100@100;100@150;150@150;150@100");
        bureauService.addOne(bureauB);
        Bureau bureauC = new Bureau();
        bureauC.setAnchorPointsString("40@40;40@80;80@80;80@40");
        bureauService.addOne(bureauC);
        Bureau bureauD = new Bureau();
        bureauD.setAnchorPointsString("-100@-100;-100@0;0@0;0@-100");
        bureauService.addOne(bureauD);

        TrainlineDeport trainlineDeportA = new TrainlineDeport();
        trainlineDeportA.setAnchorPointsString("90@90;90@95;95@95;95@90");
        Bureau jurisdiction = bureauService.getJurisdiction(trainlineDeportA);
        Assert.assertNotNull(jurisdiction);
        Assert.assertEquals(bureauA, jurisdiction);

        TrainlineDeport trainlineDeportB = new TrainlineDeport();
        trainlineDeportB.setAnchorPointsString("130@130;130@140;140@140;140@130");
        jurisdiction = bureauService.getJurisdiction(trainlineDeportB);
        Assert.assertNotNull(jurisdiction);
        Assert.assertEquals(bureauB, jurisdiction);

        TrainlineDeport trainlineDeportC = new TrainlineDeport();
        trainlineDeportC.setAnchorPointsString("-500@-500;-400@-500;-400@-400;-500@-400");
        jurisdiction = bureauService.getJurisdiction(trainlineDeportC);
        Assert.assertNull(jurisdiction);

        Station stationA = new Station();
        stationA.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationA.setAnchorPointsString("90@90;90@95;95@95;95@90");
        jurisdiction = bureauService.getJurisdiction(stationA);
        Assert.assertNotNull(jurisdiction);
        Assert.assertEquals(bureauA, jurisdiction);

        Station stationB = new Station();
        stationB.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        stationB.setAnchorPointsString("130@130;130@140;140@140;140@130");
        jurisdiction = bureauService.getJurisdiction(stationB);
        Assert.assertNotNull(jurisdiction);
        Assert.assertEquals(bureauB, jurisdiction);

        Station stationC = new Station();
        stationC.setAnchorPointsString("-500@-500");
        jurisdiction = bureauService.getJurisdiction(stationC);
        Assert.assertNull(jurisdiction);
    }

    @Test
    public void testGetCount() {
        //委托Dao
    }
}
