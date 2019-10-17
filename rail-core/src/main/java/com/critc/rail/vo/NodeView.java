package com.critc.rail.vo;

/**
 * Created by Administrator on 2016/11/29.
 */
public class NodeView {
    private String id;
    private String name;
    private int smid;
    private double x;
    private double y;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String newDescription) {

    }

    public String getDescription() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSmid() {
        return smid;
    }

    public void setSmid(int smid) {
        this.smid = smid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
