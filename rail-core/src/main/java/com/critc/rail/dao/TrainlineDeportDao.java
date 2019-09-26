/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.core.dao.BaseDao;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.vo.TrainlineDeportSearchVo;
import com.critc.rail.vo.TrainlineDeportSearchVo;
import com.critc.util.string.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * what:    行车调度台数据获取对象. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/16
 */
@Repository
public class TrainlineDeportDao extends BaseDao<TrainlineDeport, TrainlineDeportSearchVo> {
    /**
     * what:    根据查询条件查询一组行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getMany(TrainlineDeportSearchVo TrainlineDeportSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" JURISDICTION_BUREAU_ID,");
//        sql.append(" (select NAME from T_BUREAU where id=JURISDICTION_BUREAU_ID) as jurisdictionBureauName,");
        sql.append(" NAME_PINYIN,");
        sql.append(" NAME_INITIAL_PINYIN,");
        sql.append(" TELEGRAPH_CODE,");
        sql.append(" CREATOR_ID,");
        sql.append(" CREATOR_REAL_NAME,");
        sql.append(" CREATED_AT,");
        sql.append(" LAST_EDITOR_ID,");
        sql.append(" LAST_EDITOR_REAL_NAME,");
        sql.append(" LAST_EDITED_AT");
        sql.append(" from ");
        sql.append("T_TRAINLINE_DEPORT");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(TrainlineDeportSearchVo));
        return list(sql.substring(0), TrainlineDeportSearchVo);
    }

    /**
     * what:    根据查询条件查询一个行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public TrainlineDeport getOne(TrainlineDeportSearchVo TrainlineDeportSearchVo) {
        List<TrainlineDeport> many = getMany(TrainlineDeportSearchVo);
        if (many.size() > 0) {
            return many.get(0);
        } else {
            return null;
        }
    }

    /**
     * what:    根据id查询一个行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public TrainlineDeport getOne(int id) {
        TrainlineDeportSearchVo TrainlineDeportSearchVo = new TrainlineDeportSearchVo();
        TrainlineDeportSearchVo.setIdEqual(id);
        return getOne(TrainlineDeportSearchVo);
    }

    /**
     * what:    获取全路行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getAll() {
        TrainlineDeportSearchVo TrainlineDeportSearchVo = new TrainlineDeportSearchVo();
        return getMany(TrainlineDeportSearchVo);
    }

    /**
     * what:    获取给定路局管辖的行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<TrainlineDeport> getMany(Bureau bureau) {
        TrainlineDeportSearchVo TrainlineDeportSearchVo = new TrainlineDeportSearchVo();
        TrainlineDeportSearchVo.setJurisdictionBureauIdEqual(bureau.getId());
        return getMany(TrainlineDeportSearchVo);
    }

    /**
     * what:    获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(TrainlineDeportSearchVo TrainlineDeportSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" count(ID) ");
        sql.append(" from ");
        sql.append("T_TRAINLINE_DEPORT");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(TrainlineDeportSearchVo));
        return count(sql.substring(0), TrainlineDeportSearchVo);
    }

    /**
     * what:    设置查询条件. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/20
     */
    private String createSearchSql(TrainlineDeportSearchVo TrainlineDeportSearchVo) {
        String sql = "";
        if (TrainlineDeportSearchVo.getIdEqual() != null) {
            sql += " and ID=:idEqual";
        }
        if (TrainlineDeportSearchVo.getJurisdictionBureauIdEqual() != null) {
            sql += " and JURISDICTION_BUREAU_ID=:jurisdictionBureauIdEqual";
        }
        if (!StringUtil.isNullOrEmpty(TrainlineDeportSearchVo.getNameEqual())) {
            sql += " and NAME=:nameEqual";
        }
        if (!StringUtil.isNullOrEmpty(TrainlineDeportSearchVo.getNameLike())) {
            sql += " and NAME like :nameLike";
        }
        if (!StringUtil.isNullOrEmpty(TrainlineDeportSearchVo.getPinyinLike())) {
            sql += " and (NAME_PINYIN like :pinyinLike or NAME_INITIAL_PINYIN like :pinyinLike)";
        }
        if (!StringUtil.isNullOrEmpty(TrainlineDeportSearchVo.getTelegraphCodeLike())) {
            sql += " and TELEGRAPH_CODE like :TelegraphCodeLike";
        }

        return sql;
    }

    /**
     * what:    新增行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public int addOne(TrainlineDeport TrainlineDeport) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append("T_TRAINLINE_DEPORT");
        sql.append(" (");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" JURISDICTION_BUREAU_ID,");
        sql.append(" NAME_PINYIN,");
        sql.append(" NAME_INITIAL_PINYIN,");
        sql.append(" TELEGRAPH_CODE,");
        sql.append(" CREATOR_ID,");
        sql.append(" CREATOR_REAL_NAME,");
        sql.append(" CREATED_AT,");
        sql.append(" LAST_EDITOR_ID,");
        sql.append(" LAST_EDITOR_REAL_NAME,");
        sql.append(" LAST_EDITED_AT");
        sql.append(") values (");
        sql.append(" seq_t_trainline_deport.nextval,");
        sql.append(" :gridGeometryType,");
        sql.append(" :basePointString,");
        sql.append(" :anchorPointsString,");
        sql.append(" :name,");
        sql.append(" :jurisdictionBureauId,");
        sql.append(" :namePinyin,");
        sql.append(" :nameInitialPinyin,");
        sql.append(" :telegraphCode,");
        sql.append(" :creatorId,");
        sql.append(" :creatorRealName,");
        sql.append(" sysdate,");
        sql.append(" :lastEditorId,");
        sql.append(" :lastEditorRealName,");
        sql.append(" sysdate");
        sql.append(")");

        return insertForId(sql.substring(0), TrainlineDeport, "id");
    }

    /**
     * what:    更新行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(TrainlineDeport TrainlineDeport) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append("T_TRAINLINE_DEPORT");
        sql.append(" set ");
        sql.append(" GRID_GEOMETRY_TYPE=:gridGeometryType, ");
        sql.append(" BASE_POINT_STRING=:basePointString, ");
        sql.append(" ANCHOR_POINTS_STRING=:anchorPointsString, ");
        sql.append(" NAME=:name, ");
        sql.append(" JURISDICTION_BUREAU_ID=:jurisdictionBureauId, ");
        sql.append(" NAME_PINYIN=:namePinyin, ");
        sql.append(" NAME_INITIAL_PINYIN=:nameInitialPinyin, ");
        sql.append(" TELEGRAPH_CODE=:telegraphCode, ");
        sql.append(" LAST_EDITOR_ID=:lastEditorId, ");
        sql.append(" LAST_EDITOR_REAL_NAME=:lastEditorRealName, ");
        sql.append(" LAST_EDITED_AT=sysdate ");
        sql.append(" where ID=:id");

        update(sql.substring(0), TrainlineDeport);
    }

    /**
     * what:    删除行车调度台. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(TrainlineDeport TrainlineDeport) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("T_TRAINLINE_DEPORT");
        sql.append(" where ID=:id");

        delete(sql.substring(0), TrainlineDeport.getId());
    }
}
