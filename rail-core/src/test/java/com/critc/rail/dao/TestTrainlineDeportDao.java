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
import com.critc.rail.service.TrainlineDeportService;
import com.critc.rail.vo.TrainlineDeportSearchVo;
import com.critc.rail.vo.TrainlineDeportSearchVo;
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
public class TestTrainlineDeportDao {
    @Autowired
    private TrainlineDeportDao trainlineDeportDao;
    private TrainlineDeport trainlineDeport;

    @Before
    public void before() {
        trainlineDeport = new TrainlineDeport();
        trainlineDeport.setGridGeometryType(Grid.GEOMETRY_TYPE_POLYGON);
        trainlineDeport.setBasePointString("b");
        trainlineDeport.setAnchorPointsString("a");
        trainlineDeport.setJurisdictionBureauId(2);
        trainlineDeport.setName("n");
        trainlineDeport.setNameInitialPinyin("nip");
        trainlineDeport.setNamePinyin("np");
        trainlineDeport.setTelegraphCode("tc");
        trainlineDeport.setCreatorId(2);
        trainlineDeport.setCreatorRealName("crn");
        trainlineDeport.setLastEditorId(3);
        trainlineDeport.setLastEditorRealName("lrn");
    }

    @Test
    @Transactional
    @Rollback
    public void testCRUD() {
        int id = trainlineDeportDao.addOne(trainlineDeport);
        trainlineDeport.setId(id);
        TrainlineDeportSearchVo trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setIdEqual(id);
        TrainlineDeport getOne = trainlineDeportDao.getOne(trainlineDeportSearchVo);
        Assert.assertNotNull(getOne);
        Assert.assertEquals(trainlineDeport.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(trainlineDeport.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(trainlineDeport.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(trainlineDeport.getName(), getOne.getName());
        Assert.assertEquals(trainlineDeport.getJurisdictionBureauId(), getOne.getJurisdictionBureauId());
        Assert.assertEquals(trainlineDeport.getJurisdictionBureauName(), getOne.getJurisdictionBureauName());
        Assert.assertEquals(trainlineDeport.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(trainlineDeport.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(trainlineDeport.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(trainlineDeport.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(trainlineDeport.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(trainlineDeport.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(trainlineDeport.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setIdEqual(id);
        getOne = trainlineDeportDao.getOne(trainlineDeportSearchVo);
        Assert.assertTrue(trainlineDeport.equals(getOne));

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setNameEqual(trainlineDeport.getName());
        getOne = trainlineDeportDao.getOne(trainlineDeportSearchVo);
        Assert.assertTrue(trainlineDeport.equals(getOne));

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setNameLike(trainlineDeport.getName());
        List<TrainlineDeport> getMany = trainlineDeportDao.getMany(trainlineDeportSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setTelegraphCodeEqual(trainlineDeport.getTelegraphCode());
        getOne = trainlineDeportDao.getOne(trainlineDeportSearchVo);
        Assert.assertTrue(trainlineDeport.equals(getOne));

        getOne = null;
        for (TrainlineDeport one : getMany) {
            if (one.getId() == id) {
                getOne = one;
                break;
            }
        }
        Assert.assertNotNull(getOne);
        Assert.assertEquals(trainlineDeport.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(trainlineDeport.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(trainlineDeport.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(trainlineDeport.getName(), getOne.getName());
        Assert.assertEquals(trainlineDeport.getJurisdictionBureauId(), getOne.getJurisdictionBureauId());
        Assert.assertEquals(trainlineDeport.getJurisdictionBureauName(), getOne.getJurisdictionBureauName());
        Assert.assertEquals(trainlineDeport.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(trainlineDeport.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(trainlineDeport.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(trainlineDeport.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(trainlineDeport.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(trainlineDeport.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(trainlineDeport.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setJurisdictionBureauIdEqual(trainlineDeport.getJurisdictionBureauId());
        getMany = trainlineDeportDao.getMany(trainlineDeportSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setTelegraphCodeLike(trainlineDeport.getTelegraphCode());
        getMany = trainlineDeportDao.getMany(trainlineDeportSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setPinyinLike(trainlineDeport.getNamePinyin());
        getMany = trainlineDeportDao.getMany(trainlineDeportSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        Assert.assertEquals(1, trainlineDeportDao.getCount(trainlineDeportSearchVo));
        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setIdEqual(-1);
        Assert.assertEquals(0, trainlineDeportDao.getCount(trainlineDeportSearchVo));

        Bureau bureau = new Bureau();
        bureau.setId(trainlineDeport.getJurisdictionBureauId());
        getMany = trainlineDeportDao.getMany(bureau);
        Assert.assertTrue(getMany.size() > 0);

        trainlineDeportDao.updateOne(this.trainlineDeport);
        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setIdEqual(id);
        getOne = trainlineDeportDao.getOne(trainlineDeportSearchVo);
        Assert.assertNotNull(getOne);
        Assert.assertEquals(this.trainlineDeport.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(this.trainlineDeport.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(this.trainlineDeport.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(this.trainlineDeport.getName(), getOne.getName());
        Assert.assertEquals(this.trainlineDeport.getJurisdictionBureauId(), getOne.getJurisdictionBureauId());
        Assert.assertEquals(this.trainlineDeport.getJurisdictionBureauName(), getOne.getJurisdictionBureauName());
        Assert.assertEquals(this.trainlineDeport.getNamePinyin(), getOne.getNamePinyin());
        Assert.assertEquals(this.trainlineDeport.getNameInitialPinyin(), getOne.getNameInitialPinyin());
        Assert.assertEquals(this.trainlineDeport.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(this.trainlineDeport.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(this.trainlineDeport.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());
        Assert.assertEquals(this.trainlineDeport.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(this.trainlineDeport.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        trainlineDeportDao.deleteOne(this.trainlineDeport);
        trainlineDeportSearchVo = new TrainlineDeportSearchVo();
        trainlineDeportSearchVo.setIdEqual(id);
        getOne = trainlineDeportDao.getOne(trainlineDeportSearchVo);
        Assert.assertNull(getOne);
    }
}
