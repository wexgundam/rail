/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.core.dao.BaseDao;
import com.critc.rail.modal.Bureau;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.TrainlineDeport;
import com.critc.rail.vo.BureauSearchVo;
import com.critc.rail.vo.BureauSearchVo;
import com.critc.util.string.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * what:    路局数据获取对象. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/16
 */
@Repository
public class BureauDao extends BaseDao<Bureau, BureauSearchVo> {
    /**
     * what:    根据查询条件查询一组路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Bureau> getMany(BureauSearchVo bureauSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" SHORT_NAME,");
        sql.append(" CODE,");
        sql.append(" TELEGRAPH_CODE,");
        sql.append(" CREATOR_ID,");
        sql.append(" CREATOR_REAL_NAME,");
        sql.append(" CREATED_AT,");
        sql.append(" LAST_EDITOR_ID,");
        sql.append(" LAST_EDITOR_REAL_NAME,");
        sql.append(" LAST_EDITED_AT");
        sql.append(" from ");
        sql.append("T_BUREAU");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(bureauSearchVo));
        return list(sql.substring(0), bureauSearchVo);
    }

    /**
     * what:    根据查询条件查询一个路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Bureau getOne(BureauSearchVo bureauSearchVo) {
        List<Bureau> many = getMany(bureauSearchVo);
        if (many.size() > 0) {
            return many.get(0);
        } else {
            return null;
        }
    }

    /**
     * what:    获取全路路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Bureau> getAll() {
        BureauSearchVo bureauSearchVo = new BureauSearchVo();
        return getMany(bureauSearchVo);
    }

    /**
     * what:    按条件获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(BureauSearchVo bureauSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" count(ID) ");
        sql.append(" from ");
        sql.append("T_BUREAU");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(bureauSearchVo));
        return count(sql.substring(0), bureauSearchVo);
    }

    /**
     * what:    设置查询条件. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/20
     */
    private String createSearchSql(BureauSearchVo bureauSearchVo) {
        String sql = "";
        if (bureauSearchVo.getIdEqual() != null) {
            sql += " and ID=:idEqual";
        }
        if (!StringUtil.isNullOrEmpty(bureauSearchVo.getNameEqual())) {
            sql += " and NAME=:nameEqual";
        }
        if (!StringUtil.isNullOrEmpty(bureauSearchVo.getNameLike())) {
            sql += " and NAME like :nameLike";
        }
        if (!StringUtil.isNullOrEmpty(bureauSearchVo.getShortNameEqual())) {
            sql += " and SHORT_NAME=:shortNameEqual";
        }
        if (bureauSearchVo.getCodeEqual() != null) {
            sql += " and CODE=:codeEqual";
        }
        if (!StringUtil.isNullOrEmpty(bureauSearchVo.getTelegraphCodeEqual())) {
            sql += " and TELEGRAPH_CODE=:telegraphCodeEqual";
        }

        return sql;
    }

    /**
     * what:    新增路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public int addOne(Bureau bureau) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append("T_BUREAU");
        sql.append(" (");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" SHORT_NAME,");
        sql.append(" CODE,");
        sql.append(" TELEGRAPH_CODE,");
        sql.append(" CREATOR_ID,");
        sql.append(" CREATOR_REAL_NAME,");
        sql.append(" CREATED_AT,");
        sql.append(" LAST_EDITOR_ID,");
        sql.append(" LAST_EDITOR_REAL_NAME,");
        sql.append(" LAST_EDITED_AT");
        sql.append(") values (");
        sql.append(" seq_t_bureau.nextval,");
        sql.append(" :gridGeometryType,");
        sql.append(" :basePointString,");
        sql.append(" :anchorPointsString,");
        sql.append(" :name,");
        sql.append(" :shortName,");
        sql.append(" :code,");
        sql.append(" :telegraphCode,");
        sql.append(" :creatorId,");
        sql.append(" :creatorRealName,");
        sql.append(" sysdate,");
        sql.append(" :lastEditorId,");
        sql.append(" :lastEditorRealName,");
        sql.append(" sysdate");
        sql.append(")");

        return insertForId(sql.substring(0), bureau, "id");
    }

    /**
     * what:    更新路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Bureau bureau) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append("T_BUREAU");
        sql.append(" set ");
        sql.append(" GRID_GEOMETRY_TYPE=:gridGeometryType, ");
        sql.append(" BASE_POINT_STRING=:basePointString, ");
        sql.append(" ANCHOR_POINTS_STRING=:anchorPointsString, ");
        sql.append(" NAME=:name, ");
        sql.append(" SHORT_NAME=:shortName, ");
        sql.append(" CODE=:code, ");
        sql.append(" TELEGRAPH_CODE=:telegraphCode, ");
        sql.append(" LAST_EDITOR_ID=:lastEditorId, ");
        sql.append(" LAST_EDITOR_REAL_NAME=:lastEditorRealName, ");
        sql.append(" LAST_EDITED_AT=sysdate ");
        sql.append(" where ID=:id");

        update(sql.substring(0), bureau);
    }

    /**
     * what:    删除路局. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Bureau bureau) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("T_BUREAU");
        sql.append(" where ID=:id");

        delete(sql.substring(0), bureau.getId());
    }
}
