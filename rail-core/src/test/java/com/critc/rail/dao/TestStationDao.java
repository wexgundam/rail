/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.network.modal.Grid;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.vo.StationSearchVo;
import org.junit.Assert;
import org.junit.Before;
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
 * @author 靳磊 created on 2019/9/20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext-database.xml")
public class TestStationDao {
    @Autowired
    private StationDao stationDao;
    private Station station;

    @Before
    public void before() {
        station = new Station();
        station.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        station.setBasePointString("b");
        station.setAnchorPointsString("a");
        station.setJurisdictionBureauId(2);
        station.setJurisdictionTdId(1);
        station.setBureauParting(true);
        station.setName("n");
        station.setNameInitialPinyin("nip");
        station.setNamePinyin("np");
        station.setTelegraphCode("tc");
        station.setCreatorId(2);
        station.setCreatorRealName("crn");
        station.setLastEditorId(3);
        station.setLastEditorRealName("lrn");
    }

    @Test
    @Transactional
    @Rollback
    public void testCRUD() {
        int id = stationDao.addOne(station);
        station.setId(id);
        Station getOne = stationDao.getOne(station.getId());
        Assert.assertNotNull(getOne);
        Assert.assertEquals(station.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(station.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(station.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(station.getName(), getOne.getName());
        Assert.assertEquals(station.getJurisdictionBureauId(), getOne.getJurisdictionBureauId());
        Assert.assertEquals(station.getJurisdictionBureauName(), getOne.getJurisdictionBureauName());
        Assert.assertEquals(station.getJurisdictionTdId(), getOne.getJurisdictionTdId());
        Assert.assertEquals(station.getJurisdictionTrainlineDeportName(), getOne.getJurisdictionTrainlineDeportName());
        Assert.assertEquals(station.isBureauParting(), getOne.isBureauParting());
        Assert.assertEquals(station.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(station.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(station.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(station.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(station.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(station.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(station.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setIdEqual(id);
        getOne = stationDao.getOne(stationSearchVo);
        Assert.assertTrue(station.equals(getOne));

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setNameEqual(station.getName());
        getOne = stationDao.getOne(stationSearchVo);
        Assert.assertTrue(station.equals(getOne));

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setNameLike(station.getName());
        List<Station> getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setTelegraphCodeEqual(station.getTelegraphCode());
        getOne = stationDao.getOne(stationSearchVo);
        Assert.assertTrue(station.equals(getOne));

        getOne = null;
        for (Station one : getMany) {
            if (one.getId() == id) {
                getOne = one;
                break;
            }
        }
        Assert.assertNotNull(getOne);
        Assert.assertEquals(station.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(station.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(station.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(station.getName(), getOne.getName());
        Assert.assertEquals(station.getJurisdictionBureauId(), getOne.getJurisdictionBureauId());
        Assert.assertEquals(station.getJurisdictionBureauName(), getOne.getJurisdictionBureauName());
        Assert.assertEquals(station.getJurisdictionTdId(), getOne.getJurisdictionTdId());
        Assert.assertEquals(station.getJurisdictionTrainlineDeportName(), getOne.getJurisdictionTrainlineDeportName());
        Assert.assertEquals(station.isBureauParting(), getOne.isBureauParting());
        Assert.assertEquals(station.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(station.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(station.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(station.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(station.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(station.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(station.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionBureauIdEqual(station.getJurisdictionBureauId());
        getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionTdIdEqual(station.getJurisdictionTdId());
        getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setBureauPartingEqual(station.isBureauParting());
        getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setTelegraphCodeLike(station.getTelegraphCode());
        getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setPinyinLike(station.getNamePinyin());
        getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        stationSearchVo.setBureauPartingEqual(true);
        getMany = stationDao.getMany(stationSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        stationSearchVo = new StationSearchVo();
        Assert.assertEquals(1, stationDao.getCount(stationSearchVo));
        stationSearchVo = new StationSearchVo();
        stationSearchVo.setBureauPartingEqual(true);
        Assert.assertEquals(1, stationDao.getCount(stationSearchVo));
        stationSearchVo = new StationSearchVo();
        stationSearchVo.setIdEqual(-1);
        Assert.assertEquals(0, stationDao.getCount(stationSearchVo));

        Bureau bureau = new Bureau();
        bureau.setId(station.getJurisdictionBureauId());
        getMany = stationDao.getMany(bureau);
        Assert.assertTrue(getMany.size() > 0);

        TrainlineDeport trainlineDeport = new TrainlineDeport();
        trainlineDeport.setId(station.getJurisdictionTdId());
        getMany = stationDao.getMany(trainlineDeport);
        Assert.assertTrue(getMany.size() > 0);

        stationDao.updateOne(station);
        getOne = stationDao.getOne(station.getId());
        Assert.assertNotNull(getOne);
        Assert.assertEquals(station.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(station.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(station.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(station.getName(), getOne.getName());
        Assert.assertEquals(station.getJurisdictionBureauId(), getOne.getJurisdictionBureauId());
        Assert.assertEquals(station.getJurisdictionBureauName(), getOne.getJurisdictionBureauName());
        Assert.assertEquals(station.getJurisdictionTdId(), getOne.getJurisdictionTdId());
        Assert.assertEquals(station.getJurisdictionTrainlineDeportName(), getOne.getJurisdictionTrainlineDeportName());
        Assert.assertEquals(station.isBureauParting(), getOne.isBureauParting());
        Assert.assertEquals(station.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(station.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(station.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(station.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(station.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());
        Assert.assertEquals(station.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(station.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        stationDao.deleteOne(station);
        getOne = stationDao.getOne(station.getId());
        Assert.assertNull(getOne);
    }
}
