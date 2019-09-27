/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.rail.modal.Bureau;
import com.critc.rail.vo.BureauSearchVo;
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
public class TestBureauDao {
    @Autowired
    private BureauDao bureauDao;
    private Bureau bureau;

    @Before
    public void before() {
        bureau = new Bureau();
        bureau.setBasePointString("b");
        bureau.setAnchorPointsString("a");
        bureau.setName("n");
        bureau.setShortName("sn");
        bureau.setCode(99);
        bureau.setTelegraphCode("tc");
        bureau.setTelegraphCode("tc");
        bureau.setCreatorId(2);
        bureau.setCreatorRealName("crn");
        bureau.setLastEditorId(3);
        bureau.setLastEditorRealName("lrn");
    }

    @Test
    @Transactional
    @Rollback
    public void testCRUD() {
        int id = bureauDao.addOne(bureau);
        bureau.setId(id);
        BureauSearchVo bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setIdEqual(id);
        Bureau getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertNotNull(getOne);
        Assert.assertEquals(bureau.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(bureau.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(bureau.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(bureau.getName(), getOne.getName());
        Assert.assertEquals(bureau.getShortName(), getOne.getShortName());
        Assert.assertEquals(bureau.getCode(), getOne.getCode());
        Assert.assertEquals(bureau.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(bureau.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(bureau.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(bureau.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(bureau.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setIdEqual(id);
        getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertTrue(bureau.equals(getOne));

        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setNameEqual(bureau.getName());
        getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertTrue(bureau.equals(getOne));

        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setNameLike(bureau.getName());
        List<Bureau> getMany = bureauDao.getMany(bureauSearchVo);
        Assert.assertTrue(getMany.size() > 0);

        getOne = null;
        for (Bureau one : getMany) {
            if (one.getId() == id) {
                getOne = one;
                break;
            }
        }
        Assert.assertNotNull(getOne);
        Assert.assertEquals(bureau.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(bureau.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(bureau.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(bureau.getName(), getOne.getName());
        Assert.assertEquals(bureau.getShortName(), getOne.getShortName());
        Assert.assertEquals(bureau.getCode(), getOne.getCode());
        Assert.assertEquals(bureau.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(bureau.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(bureau.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(bureau.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(bureau.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setShortNameEqual(bureau.getShortName());
        getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertTrue(bureau.equals(getOne));

        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setTelegraphCodeEqual(bureau.getTelegraphCode());
        getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertTrue(bureau.equals(getOne));

        bureauDao.updateOne(bureau);
        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setIdEqual(id);
        getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertNotNull(getOne);
        Assert.assertEquals(bureau.getGridGeometryType(), getOne.getGridGeometryType());
        Assert.assertEquals(bureau.getBasePointString(), getOne.getBasePointString());
        Assert.assertEquals(bureau.getAnchorPointsString(), getOne.getAnchorPointsString());
        Assert.assertEquals(bureau.getName(), getOne.getName());
        Assert.assertEquals(bureau.getShortName(), getOne.getShortName());
        Assert.assertEquals(bureau.getCode(), getOne.getCode());
        Assert.assertEquals(bureau.getTelegraphCode(), getOne.getTelegraphCode());
        Assert.assertEquals(bureau.getCreatorId(), getOne.getCreatorId());
        Assert.assertEquals(bureau.getCreatorRealName(), getOne.getCreatorRealName());
        Assert.assertNotNull(getOne.getCreatedAt());
        Assert.assertEquals(bureau.getLastEditorId(), getOne.getLastEditorId());
        Assert.assertEquals(bureau.getLastEditorRealName(), getOne.getLastEditorRealName());
        Assert.assertNotNull(getOne.getLastEditedAt());

        bureauDao.deleteOne(bureau);
        bureauSearchVo = new BureauSearchVo();
        bureauSearchVo.setIdEqual(id);
        getOne = bureauDao.getOne(bureauSearchVo);
        Assert.assertNull(getOne);
    }
}
