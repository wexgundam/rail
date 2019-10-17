/**
 * Copyright 2019 靳磊. All rights reserved
 * Project Name:rail
 * Module Name:TODO:Module
 */
package com.critc.tile.vo;

/**
 * what:    线串类图像Vo. <br/>
 * when:    (这里描述这个类的适用时机 – 可选).<br/>
 * how:     (这里描述这个类的使用方法 – 可选).<br/>
 * warning: (这里描述这个类的注意事项 – 可选).<br/>
 *
 * @author 靳磊 created on 2019/10/11
 */
public class LineStringVo {
    /**
     * 线串关键点坐标集合:{x1,y1,x2,y2,x3,y3......}
     */
    private double[] xys;

    public double[] getXys() {
        return xys;
    }

    public void setXys(double[] xys) {
        this.xys = xys;
    }
}
