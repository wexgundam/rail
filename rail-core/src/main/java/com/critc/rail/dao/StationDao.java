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
import com.critc.rail.vo.StationSearchVo;
import com.critc.util.string.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * what:    车站数据获取对象. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/16
 */
@Repository
public class StationDao extends BaseDao<Station, StationSearchVo> {
    /**
     * what:    根据查询条件查询一组车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getMany(StationSearchVo stationSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" JURISDICTION_BUREAU_ID,");
//        sql.append(" (select NAME from T_BUREAU where id=:jurisdictionBureauId),");
        sql.append(" JURISDICTION_TD_ID,");
//        sql.append(" (select NAME from T_TRAINLINE_DEPORT where id=:jurisdictionTdId),");
        sql.append(" BUREAU_PARTING,");
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
        sql.append("T_STATION");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(stationSearchVo));
        return list(sql.substring(0), stationSearchVo);
    }

    /**
     * what:    根据查询条件查询一个车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Station getOne(StationSearchVo stationSearchVo) {
        List<Station> many = getMany(stationSearchVo);
        if (many.size() > 0) {
            return many.get(0);
        } else {
            return null;
        }
    }

    /**
     * what:    根据id查询一个车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Station getOne(int id) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setIdEqual(id);
        return getOne(stationSearchVo);
    }

    /**
     * what:    获取全路车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getAll() {
        StationSearchVo stationSearchVo = new StationSearchVo();
        return getMany(stationSearchVo);
    }

    /**
     * what:    获取给定路局管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getMany(Bureau bureau) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionBureauIdEqual(bureau.getId());
        return getMany(stationSearchVo);
    }

    /**
     * what:    获取给定行车调度台管辖的车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Station> getMany(TrainlineDeport trainlineDeport) {
        StationSearchVo stationSearchVo = new StationSearchVo();
        stationSearchVo.setJurisdictionTdIdEqual(trainlineDeport.getId());
        return getMany(stationSearchVo);
    }

    /**
     * what:    按条件获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(StationSearchVo stationSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" count(ID) ");
        sql.append(" from ");
        sql.append("T_STATION");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(stationSearchVo));
        return count(sql.substring(0), stationSearchVo);
    }

    /**
     * what:    设置查询条件. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/20
     */
    private String createSearchSql(StationSearchVo stationSearchVo) {
        String sql = "";
        if (stationSearchVo.getIdEqual() != null) {
            sql += " and ID=:idEqual";
        }
        if (stationSearchVo.getJurisdictionBureauIdEqual() != null) {
            sql += " and JURISDICTION_BUREAU_ID=:jurisdictionBureauIdEqual";
        }
        if (stationSearchVo.getJurisdictionTdIdEqual() != null) {
            sql += " and JURISDICTION_TD_ID=:jurisdictionTdIdEqual";
        }
        if (!StringUtil.isNullOrEmpty(stationSearchVo.getNameEqual())) {
            sql += " and NAME=:nameEqual";
        }
        if (!StringUtil.isNullOrEmpty(stationSearchVo.getNameLike())) {
            sql += " and NAME like :nameLike";
        }
        if (!StringUtil.isNullOrEmpty(stationSearchVo.getPinyinLike())) {
            sql += " and (NAME_PINYIN like :pinyinLike or NAME_INITIAL_PINYIN like :pinyinLike)";
        }
        if (!StringUtil.isNullOrEmpty(stationSearchVo.getTelegraphCodeLike())) {
            sql += " and TELEGRAPH_CODE like :TelegraphCodeLike";
        }
        if (stationSearchVo.getBureauPartingEqual() != null) {
            sql += " and BUREAU_PARTING like :bureauPartingEqual";
        }

        return sql;
    }

    /**
     * what:    新增车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public int addOne(Station station) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append("T_STATION");
        sql.append(" (");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" NAME,");
        sql.append(" JURISDICTION_BUREAU_ID,");
        sql.append(" JURISDICTION_TD_ID,");
        sql.append(" BUREAU_PARTING,");
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
        sql.append(" seq_t_station.nextval,");
        sql.append(" :gridGeometryType,");
        sql.append(" :basePointString,");
        sql.append(" :anchorPointsString,");
        sql.append(" :name,");
        sql.append(" :jurisdictionBureauId,");
        sql.append(" :jurisdictionTdId,");
        sql.append(" :bureauParting,");
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

        return insertForId(sql.substring(0), station, "id");
    }

    /**
     * what:    更新车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Station station) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append("T_STATION");
        sql.append(" set ");
        sql.append(" GRID_GEOMETRY_TYPE=:gridGeometryType, ");
        sql.append(" BASE_POINT_STRING=:basePointString, ");
        sql.append(" ANCHOR_POINTS_STRING=:anchorPointsString, ");
        sql.append(" NAME=:name, ");
        sql.append(" JURISDICTION_BUREAU_ID=:jurisdictionBureauId, ");
        sql.append(" JURISDICTION_TD_ID=:jurisdictionTdId, ");
        sql.append(" BUREAU_PARTING=:bureauParting, ");
        sql.append(" NAME_PINYIN=:namePinyin, ");
        sql.append(" NAME_INITIAL_PINYIN=:nameInitialPinyin, ");
        sql.append(" TELEGRAPH_CODE=:telegraphCode, ");
        sql.append(" LAST_EDITOR_ID=:lastEditorId, ");
        sql.append(" LAST_EDITOR_REAL_NAME=:lastEditorRealName, ");
        sql.append(" LAST_EDITED_AT=sysdate ");
        sql.append(" where ID=:id");

        update(sql.substring(0), station);
    }

    /**
     * what:    删除车站. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Station station) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("T_STATION");
        sql.append(" where ID=:id");

        delete(sql.substring(0), station.getId());
    }
}
