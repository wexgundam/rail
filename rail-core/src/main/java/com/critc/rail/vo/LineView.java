package com.critc.rail.vo;

/**
 * Created by Administrator on 2016/11/29.
 */
public class LineView {
    private String id;
    private String name;
    private int smid;
    private double sourceX;
    private double sourceY;
    private double targetX;
    private double targetY;

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

    public double getSourceX() {
        return sourceX;
    }

    public void setSourceX(double sourceX) {
        this.sourceX = sourceX;
    }

    public double getSourceY() {
        return sourceY;
    }

    public void setSourceY(double sourceY) {
        this.sourceY = sourceY;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }
}
