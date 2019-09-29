/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.dao;

import com.critc.core.dao.BaseDao;
import com.critc.rail.modal.Link;
import com.critc.rail.vo.LinkSearchVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * what:    节点间数据获取对象. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/16
 */
@Repository
public class LinkDao extends BaseDao<Link, LinkSearchVo> {
    /**
     * what:    根据查询条件查询一组节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getMany(LinkSearchVo linkSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" CREATOR_ID,");
        sql.append(" CREATOR_REAL_NAME,");
        sql.append(" CREATED_AT,");
        sql.append(" LAST_EDITOR_ID,");
        sql.append(" LAST_EDITOR_REAL_NAME,");
        sql.append(" LAST_EDITED_AT");
        sql.append(" from ");
        sql.append("T_LINK");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(linkSearchVo));
        return list(sql.substring(0), linkSearchVo);
    }

    /**
     * what:    根据查询条件查询一个节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public Link getOne(LinkSearchVo linkSearchVo) {
        List<Link> many = getMany(linkSearchVo);
        if (many.size() > 0) {
            return many.get(0);
        } else {
            return null;
        }
    }

    /**
     * what:    获取全路节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public List<Link> getAll() {
        LinkSearchVo linkSearchVo = new LinkSearchVo();
        return getMany(linkSearchVo);
    }

    /**
     * what:    按条件获取数量. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/25
     */
    public int getCount(LinkSearchVo linkSearchVo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(" count(ID) ");
        sql.append(" from ");
        sql.append("T_LINK");
        sql.append(" where 1=1 ");

        sql.append(createSearchSql(linkSearchVo));
        return count(sql.substring(0), linkSearchVo);
    }

    /**
     * what:    设置查询条件. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/20
     */
    private String createSearchSql(LinkSearchVo linkSearchVo) {
        String sql = "";
        if (linkSearchVo.getIdEqual() != null) {
            sql += " and ID=:idEqual";
        }

        return sql;
    }

    /**
     * what:    新增节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public int addOne(Link link) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append("T_LINK");
        sql.append(" (");
        sql.append(" ID,");
        sql.append(" GRID_GEOMETRY_TYPE,");
        sql.append(" BASE_POINT_STRING,");
        sql.append(" ANCHOR_POINTS_STRING,");
        sql.append(" CREATOR_ID,");
        sql.append(" CREATOR_REAL_NAME,");
        sql.append(" CREATED_AT,");
        sql.append(" LAST_EDITOR_ID,");
        sql.append(" LAST_EDITOR_REAL_NAME,");
        sql.append(" LAST_EDITED_AT");
        sql.append(") values (");
        sql.append(" seq_t_link.nextval,");
        sql.append(" :gridGeometryType,");
        sql.append(" :basePointString,");
        sql.append(" :anchorPointsString,");
        sql.append(" :creatorId,");
        sql.append(" :creatorRealName,");
        sql.append(" sysdate,");
        sql.append(" :lastEditorId,");
        sql.append(" :lastEditorRealName,");
        sql.append(" sysdate");
        sql.append(")");

        return insertForId(sql.substring(0), link, "id");
    }

    /**
     * what:    更新节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void updateOne(Link link) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append("T_LINK");
        sql.append(" set ");
        sql.append(" GRID_GEOMETRY_TYPE=:gridGeometryType, ");
        sql.append(" BASE_POINT_STRING=:basePointString, ");
        sql.append(" ANCHOR_POINTS_STRING=:anchorPointsString, ");
        sql.append(" LAST_EDITOR_ID=:lastEditorId, ");
        sql.append(" LAST_EDITOR_REAL_NAME=:lastEditorRealName, ");
        sql.append(" LAST_EDITED_AT=sysdate ");
        sql.append(" where ID=:id");

        update(sql.substring(0), link);
    }

    /**
     * what:    删除节点间. <br/>
     * when:    (这里描述这个类的适用时机 – 可选).<br/>
     * how:     (这里描述这个类的使用方法 – 可选).<br/>
     * warning: (这里描述这个类的注意事项 – 可选).<br/>
     *
     * @author 靳磊 created on 2019/9/11
     */
    public void deleteOne(Link link) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("T_LINK");
        sql.append(" where ID=:id");

        delete(sql.substring(0), link.getId());
    }

}
