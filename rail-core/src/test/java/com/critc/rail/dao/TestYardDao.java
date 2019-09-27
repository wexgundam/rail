/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.rail.modal.Station;
import com.critc.rail.modal.Yard;
import com.critc.rail.vo.YardSearchVo;
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
public class TestYardDao {
    @Autowired
    private YardDao yardDao;
    @Autowired
    private StationDao staitonDao;
    private Yard yard;

    @Before
    public void before() {
        yard = new Yard();
        yard.setBasePointString("b");
        yard.setAnchorPointsString("a");
        yard.setJurisdictionStationId(2);
        yard.setName("n");
        yard.setNameInitialPinyin("nip");
        yard.setNamePinyin("np");
        yard.setTelegraphCode("tc");
        yard.setCreatorId(2);
        yard.setCreatorRealName("crn");
        yard.setLastEditorId(3);
        yard.setLastEditorRealName("lrn");
    }

    @Test
    @Transactional
    @Rollback
    public void testCRUD() {
        Station station = new Station();
        station.setName("s");
        int stationId = staitonDao.addOne(station);
        yard.setJurisdictionStationId(stationId);
        yard.setJurisdictionStationName(station.getName());

        int id = yardDao.addOne(yard);
        yard.setId(id);
        YardSearchVo yardSearchVo = new YardSearchVo();
        yardSearchVo.setIdEqual(id);
        Yard getOne = yardDao.getOne(yardSearchVo);
        Assert.assertNotNull(getOne);
        Assert.assertEquals(yard.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(yard.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(yard.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(yard.getName(), getOne.getName());
        Assert.assertEquals(yard.getJurisdictionStationId(), getOne.getJurisdictionStationId());
        Assert.assertEquals(yard.getJurisdictionStationName(), getOne.getJurisdictionStationName());
        Assert.assertEquals(yard.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(yard.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(yard.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(yard.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(yard.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(yard.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(yard.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setIdEqual(id);
        getOne = yardDao.getOne(yardSearchVo);
        Assert.assertTrue(yard.equals(getOne));

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setNameEqual(yard.getName());
        getOne = yardDao.getOne(yardSearchVo);
        Assert.assertTrue(yard.equals(getOne));

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setNameLike(yard.getName());
        List<Yard> getMany = yardDao.getMany(yardSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        getOne = null;
        for (Yard one : getMany) {
            if (one.getId() == id) {
                getOne = one;
                break;
            }
        }
        Assert.assertNotNull(getOne);
        Assert.assertEquals(yard.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(yard.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(yard.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(yard.getName(), getOne.getName());
        Assert.assertEquals(yard.getJurisdictionStationId(), getOne.getJurisdictionStationId());
        Assert.assertEquals(yard.getJurisdictionStationName(), getOne.getJurisdictionStationName());
        Assert.assertEquals(yard.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(yard.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(yard.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(yard.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(yard.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(yard.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(yard.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setJurisdictionStationIdEqual(yard.getJurisdictionStationId());
        getMany = yardDao.getMany(yardSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setTelegraphCodeLike(yard.getTelegraphCode());
        getMany = yardDao.getMany(yardSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setTelegraphCodeEqual(yard.getTelegraphCode());
        getOne = yardDao.getOne(yardSearchVo);
        Assert.assertTrue(yard.equals(getOne));

        yardSearchVo = new YardSearchVo();
        yardSearchVo.setPinyinLike(yard.getNamePinyin());
        getMany = yardDao.getMany(yardSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        station = new Station();
        station.setId(yard.getJurisdictionStationId());
        getMany = yardDao.getMany(station);
        Assert.assertTrue(getMany.size() > 0);

        yardDao.updateOne(yard);
        yardSearchVo = new YardSearchVo();
        yardSearchVo.setIdEqual(id);
        getOne = yardDao.getOne(yardSearchVo);
        Assert.assertNotNull(getOne);
        Assert.assertEquals(yard.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(yard.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(yard.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(yard.getName(), getOne.getName());
        Assert.assertEquals(yard.getJurisdictionStationId(), getOne.getJurisdictionStationId());
        Assert.assertEquals(yard.getJurisdictionStationName(), getOne.getJurisdictionStationName());
        Assert.assertEquals(yard.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(yard.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(yard.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(yard.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(yard.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(yard.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(yard.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        yardDao.deleteOne(yard);
        yardSearchVo = new YardSearchVo();
        yardSearchVo.setIdEqual(id);
        getOne = yardDao.getOne(yardSearchVo);
        Assert.assertNull(getOne);
    }
}
