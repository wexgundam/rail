/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

/**
 * what:    路局分界口车站，非实体. <br/>
 * 主要用于表示路局分界口车站、其归属局与邻接局. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/12
 */
public class BureauPartingStation {
    /**
     * 路局分界口车站
     */
    private Station bureauPartingStation;
    /**
     * 路局分界口管辖局
     */
    private Bureau jurisdictionBureau;
    /**
     * 邻接节点间
     */
    private Link adjoinLink;
    /**
     * 路局分界口邻接局
     */
    private Bureau adjoinBureau;
    /**
     * 邻接局的邻接站
     */
    private Station adjoinStation;

    public Link getAdjoinLink() {
        return adjoinLink;
    }

    public void setAdjoinLink(Link adjoinLink) {
        this.adjoinLink = adjoinLink;
    }

    public Station getAdjoinStation() {
        return adjoinStation;
    }

    public void setAdjoinStation(Station adjoinStation) {
        this.adjoinStation = adjoinStation;
    }

    public Station getBureauPartingStation() {
        return bureauPartingStation;
    }

    public void setBureauPartingStation(Station bureauPartingStation) {
        this.bureauPartingStation = bureauPartingStation;
    }

    public Bureau getJurisdictionBureau() {
        return jurisdictionBureau;
    }

    public void setJurisdictionBureau(Bureau jurisdictionBureau) {
        this.jurisdictionBureau = jurisdictionBureau;
    }

    public Bureau getAdjoinBureau() {
        return adjoinBureau;
    }

    public void setAdjoinBureau(Bureau adjoinBureau) {
        this.adjoinBureau = adjoinBureau;
    }
}
