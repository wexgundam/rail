package com.critc.tile.modal;

/**
 * 二维坐标，内容简单，一旦创建不允许修改
 * <p>
 * 坐标值可为Null
 * <p>
 * Created by Administrator on 2016/11/25.
 */
public class Coordinate implements Cloneable {
    private double x;
    private double y;

    public Coordinate() {
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(double[] coordinates) {
        this(coordinates[0], coordinates[1]);
    }

    public Coordinate(Double[] coordinates) {
        this(coordinates[0], coordinates[1]);
    }

    public Coordinate(Object[] figureCoordinates) {
        this(Double.valueOf(figureCoordinates[0].toString()), Double.valueOf(figureCoordinates[1].toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.getX(), getX()) != 0) return false;
        return Double.compare(that.getY(), getY()) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getX());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    protected Coordinate clone() {
        Coordinate coordinate = new Coordinate(this.x, this.y);
        return coordinate;
    }

    /**
     * 坐标转为数组
     *
     * @return
     */
    public Object[] toArray() {
        return new Object[]{x, y};
    }

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }
}
