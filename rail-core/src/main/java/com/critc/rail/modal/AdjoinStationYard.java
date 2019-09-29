/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

/**
 * what:    邻接车站与车场，非实体. <br/>
 * 主要用于表示一个车站和一个车场的邻接关系，包含邻接的车场及它们之间的节点间. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/12
 */
public class AdjoinStationYard {
    /**
     * 邻接车站
     */
    private Station station;
    /**
     * 邻接车场
     */
    private Yard yard;
    /**
     * 使得车站和车场具备邻接关系的节点间
     */
    private Link link;

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Yard getYard() {
        return yard;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
