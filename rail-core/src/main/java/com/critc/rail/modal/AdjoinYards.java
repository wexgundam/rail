/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.rail.modal;

/**
 * what:    邻接车场，非实体. <br/>
 * 主要用于表示两个车场的邻接关系，包含邻接车场及它们之间的节点间. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/9/12
 */
public class AdjoinYards {
    /**
     * 邻接车站之一
     */
    private Yard yardA;
    /**
     * 邻接车站之二
     */
    private Yard yardB;
    /**
     * 使得车场具备邻接关系的节点间
     */
    private Link link;

    public Yard getYardA() {
        return yardA;
    }

    public void setYardA(Yard yardA) {
        this.yardA = yardA;
    }

    public Yard getYardB() {
        return yardB;
    }

    public void setYardB(Yard yardB) {
        this.yardB = yardB;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
