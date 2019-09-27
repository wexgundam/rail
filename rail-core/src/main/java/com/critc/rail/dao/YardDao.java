/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.core.dao.BaseDao;
import com.critc.rail.modal.Station;
import com.critc.rail.modal.Yard;
import com.critc.rail.vo.YardSearchVo;
import com.critc.util.string.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * what:    车场数据获取对象. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/16
 */
@Repository
public class YardDao extends BaseDao<Yard, YardSearchVo> {
    /**
     * what:    根据查询条件查询一组车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getMany(YardSearchVo yardSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" JURISDICTION_STATION_ID,");
        sql.append(" (select NAME from T_STATION where ID=JURISDICTION_STATION_ID) as jurisdictionStationName,");
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
        sql.append("T_YARD");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(yardSearchVo));
        return list(sql.substring(0), yardSearchVo);
    }

    /**
     * what:    根据查询条件查询一个车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Yard getOne(YardSearchVo yardSearchVo) {
        List<Yard> many = getMany(yardSearchVo);
        if (many.size() > 0) {
            return many.get(0);
        } else {
            return null;
        }
    }

    /**
     * what:    获取全路车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getAll() {
        YardSearchVo yardSearchVo = new YardSearchVo();
        return getMany(yardSearchVo);
    }

    /**
     * what:    获取给定路局管辖的车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Yard> getMany(Station station) {
        YardSearchVo yardSearchVo = new YardSearchVo();
        yardSearchVo.setJurisdictionStationIdEqual(station.getId());
        return getMany(yardSearchVo);
    }

    /**
     * what:    按条件获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(YardSearchVo yardSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" count(ID) ");
        sql.append(" from ");
        sql.append("T_YARD");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(yardSearchVo));
        return count(sql.substring(0), yardSearchVo);
    }

    /**
     * what:    设置查询条件. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/20
     */
    private String createSearchSql(YardSearchVo yardSearchVo) {
        String sql = "";
        if (yardSearchVo.getIdEqual() != null) {
            sql += " and ID=:idEqual";
        }
        if (yardSearchVo.getJurisdictionStationIdEqual() != null) {
            sql += " and JURISDICTION_STATION_ID=:jurisdictionStationIdEqual";
        }
        if (!StringUtil.isNullOrEmpty(yardSearchVo.getNameEqual())) {
            sql += " and NAME=:nameEqual";
        }
        if (!StringUtil.isNullOrEmpty(yardSearchVo.getNameLike())) {
            sql += " and NAME like :nameLike";
        }
        if (!StringUtil.isNullOrEmpty(yardSearchVo.getPinyinLike())) {
            sql += " and (NAME_PINYIN like :pinyinLike or NAME_INITIAL_PINYIN like :pinyinLike)";
        }
        if (!StringUtil.isNullOrEmpty(yardSearchVo.getTelegraphCodeLike())) {
            sql += " and TELEGRAPH_CODE like :TelegraphCodeLike";
        }

        return sql;
    }

    /**
     * what:    新增车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public int addOne(Yard yard) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append("T_YARD");
        sql.append(" (");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" JURISDICTION_STATION_ID,");
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
        sql.append(" seq_t_yard.nextval,");
        sql.append(" :gridGeometryType,");
        sql.append(" :basePointString,");
        sql.append(" :anchorPointsString,");
        sql.append(" :name,");
        sql.append(" :jurisdictionStationId,");
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

        return insertForId(sql.substring(0), yard, "id");
    }

    /**
     * what:    更新车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Yard yard) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append("T_YARD");
        sql.append(" set ");
        sql.append(" GRID_GEOMETRY_TYPE=:gridGeometryType, ");
        sql.append(" BASE_POINT_STRING=:basePointString, ");
        sql.append(" ANCHOR_POINTS_STRING=:anchorPointsString, ");
        sql.append(" NAME=:name, ");
        sql.append(" JURISDICTION_STATION_ID=:jurisdictionStationId, ");
        sql.append(" NAME_PINYIN=:namePinyin, ");
        sql.append(" NAME_INITIAL_PINYIN=:nameInitialPinyin, ");
        sql.append(" TELEGRAPH_CODE=:telegraphCode, ");
        sql.append(" LAST_EDITOR_ID=:lastEditorId, ");
        sql.append(" LAST_EDITOR_REAL_NAME=:lastEditorRealName, ");
        sql.append(" LAST_EDITED_AT=sysdate ");
        sql.append(" where ID=:id");

        update(sql.substring(0), yard);
    }

    /**
     * what:    删除车场. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Yard yard) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("T_YARD");
        sql.append(" where ID=:id");

        delete(sql.substring(0), yard.getId());
    }
}
