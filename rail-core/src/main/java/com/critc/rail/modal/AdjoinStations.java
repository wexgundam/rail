/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

/**
 * what:    邻接车站，非实体. <br/>
 * 主要用于表示两个车站的邻接关系，包含邻接的车站及这两个车站间的节点间. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/12
 */
public class AdjoinStations {
    /**
     * 邻接车站之一
     */
    private Station stationA;
    /**
     * 邻接车站之二
     */
    private Station stationB;
    /**
     * 使得两个车站具备邻接关系的节点间
     */
    private Link link;

    public Station getStationA() {
        return stationA;
    }

    public void setStationA(Station stationA) {
        this.stationA = stationA;
    }

    public Station getStationB() {
        return stationB;
    }

    public void setStationB(Station stationB) {
        this.stationB = stationB;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
