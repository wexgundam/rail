package com.critc.tile.modal;

/**
 * 坐标系统，工具类
 * <p>
 * 提供基于实际图、投影图、瓦片、瓦片图与视图间的坐标计算
 * <p>
 * Created by Administrator on 2016/12/10.
 */
public class CoordinateSystem {
    /**
     * 计算实际坐标在给定投影图中的投影图坐标
     *
     * @param figure
     * @param projection
     * @param figureCoordinate
     * @return
     */
    public Coordinate figureCoordinateToProjectionCoordinate(Figure figure, Projection projection,
                                                             Coordinate figureCoordinate) {
        return figureCoordinateToProjectionCoordinate(figure, projection, figureCoordinate.getX(),
                figureCoordinate.getY());
    }

    /**
     * 计算实际坐标在给定投影图中的投影图坐标
     *
     * @param figure
     * @param projection
     * @param figureCoordinateX
     * @param figureCoordinateY
     * @return
     */
    public Coordinate figureCoordinateToProjectionCoordinate(Figure figure, Projection projection,
                                                             double figureCoordinateX, double figureCoordinateY) {
        double resolution = projection.getResolution();
        Coordinate figureTopLeftCoordinate = figure.getBounds().getTopLeftCoordinate();
        double x = (figureCoordinateX - figureTopLeftCoordinate.getX()) / resolution;
        double y = (figureTopLeftCoordinate.getY() - figureCoordinateY) / resolution;
        return new Coordinate(x, y);
    }

    /**
     * 计算给定投影图的投影图坐标在的实际坐标
     *
     * @param figure
     * @param projection
     * @param projectionCoordinate
     * @return
     */
    public Coordinate projectionCoordinateToFigureCoordinate(Figure figure, Projection projection,
                                                             Coordinate projectionCoordinate) {
        return projectionCoordinateToFigureCoordinate(figure, projection, projectionCoordinate.getX(),
                projectionCoordinate.getY());
    }

    /**
     * 计算给定投影图的投影图坐标在的实际坐标
     *
     * @param figure
     * @param projection
     * @param projectionCoordinateX
     * @param projectionCoordinateY
     * @return
     */
    public Coordinate projectionCoordinateToFigureCoordinate(Figure figure, Projection projection,
                                                             double projectionCoordinateX,
                                                             double projectionCoordinateY) {
        Coordinate figureTopLeftCoordinate = figure.getBounds().getTopLeftCoordinate();
        double resolution = projection.getResolution();
        double x = figureTopLeftCoordinate.getX() + projectionCoordinateX * resolution;
        double y = figureTopLeftCoordinate.getY() - projectionCoordinateY * resolution;
        return new Coordinate(x, y);
    }

    /**
     * 计算实际图坐标在给定投影图的瓦片
     *
     * @param figure
     * @param projection
     * @param figureCoordinate
     * @return
     */
    public Tile figureCoordinateToTile(Figure figure, Projection projection, Coordinate figureCoordinate) {
        return figureCoordinateToTile(figure, projection, figureCoordinate.getX(), figureCoordinate.getY());
    }


    /**
     * 计算实际图坐标在给定投影图的瓦片
     *
     * @param figure
     * @param projection
     * @param figureCoordinateX
     * @param figureCoordinateY
     * @return
     */
    public Tile figureCoordinateToTile(Figure figure, Projection projection, double figureCoordinateX,
                                       double figureCoordinateY) {
        Bounds bounds = figure.getBounds();
        // 计算实际坐标据实际图左上角坐标的距离，除以分辨率和瓦片size，获得瓦片
        double resolution = projection.getResolution();
        double tileSize = projection.getTileSize();
        int column = new Double(
                Math.floor(
                        (figureCoordinateX - bounds.getTopLeftCoordinate().getX()) / resolution / tileSize)).intValue();
        int row = new Double(Math.floor((bounds.getMaxY() - figureCoordinateY) / resolution / tileSize)).intValue();

        Tile tile = new Tile(column, row, projection.getZoomLevel());
        return tile;
    }

    /**
     * 计算投影图坐标在给定投影图的瓦片
     *
     * @param projection
     * @param projectionCoordinate
     * @return
     */
    public Tile projectionCoordinateToTile(Projection projection, Coordinate projectionCoordinate) {
        return projectionCoordinateToTile(projection, projectionCoordinate.getX(), projectionCoordinate.getY());
    }


    /**
     * 计算投影图坐标在给定投影图的瓦片
     *
     * @param projection
     * @param projectionCoordinateX
     * @param projectionCoordinateY
     * @return
     */
    public Tile projectionCoordinateToTile(Projection projection, double projectionCoordinateX,
                                           double projectionCoordinateY) {
        double tileSize = projection.getTileSize();
        Bounds projectionBounds = projection.getBounds();

        int column;
        int row;
        if (projectionCoordinateX <= projectionBounds.getMinX()) {
            column = 0;
        } else if (projectionCoordinateX >= projectionBounds.getMaxX()) {
            column = (int) Math.pow(2, projection.getZoomLevel()) - 1;
        } else {
            column = (int) (projectionCoordinateX / tileSize);
        }
        if (projectionCoordinateY <= projectionBounds.getMinY()) {
            row = 0;
        } else if (projectionCoordinateY >= projectionBounds.getMaxY()) {
            row = (int) Math.pow(2, projection.getZoomLevel()) - 1;
        } else {
            row = (int) (projectionCoordinateY / tileSize);

        }
        Tile tile = new Tile(projection.getZoomLevel(), column, row);
        return tile;
    }

    /**
     * 计算实际坐标在给定投影图的瓦片图的坐标
     *
     * @param figure
     * @param projection
     * @param figureCoordinate
     * @return
     */
    public Coordinate figureCoordinateToTileImageCoordinate(Figure figure, Projection projection, Tile tile,
                                                            Coordinate figureCoordinate) {
        return figureCoordinateToTileImageCoordinate(figure, projection, tile, figureCoordinate.getX(),
                figureCoordinate.getY());
    }

    /**
     * 计算实际坐标在给定投影图的瓦片图的坐标
     *
     * @param figure
     * @param projection
     * @param tile
     * @param figureCoordinateX
     * @param figureCoordinateY
     * @return
     */
    public Coordinate figureCoordinateToTileImageCoordinate(Figure figure, Projection projection, Tile tile,
                                                            double figureCoordinateX, double figureCoordinateY) {
        Coordinate projectionCoordinate = figureCoordinateToProjectionCoordinate(figure, projection, figureCoordinateX,
                figureCoordinateY);
        double tileSize = projection.getTileSize();
        double x = projectionCoordinate.getX() - tile.getColumn() * tileSize;
        double y = projectionCoordinate.getY() - tile.getRow() * tileSize;
        return new Coordinate(x, y);
    }

    /**
     * 根据瓦片计算瓦片的实际范围
     *
     * @param figure
     * @param projection
     * @param tile
     * @return
     */
    public Bounds tileToFigureBounds(Figure figure, Projection projection, Tile tile) {
        Coordinate tileTopLeftFigureCoordinate = tileToTileTopLeftFigureCoordinate(figure, projection, tile);
        double resolution = projection.getResolution();
        double tileSize = projection.getTileSize();
        double tileBottomRightFigureCoordinateX = tileTopLeftFigureCoordinate.getX() + tileSize * resolution;
        double tileBottomRightFigureCoordinateY = tileTopLeftFigureCoordinate.getY() - tileSize * resolution;
        Coordinate tileBottomRightFigureCoordinate = new Coordinate(tileBottomRightFigureCoordinateX,
                tileBottomRightFigureCoordinateY);

        return new Bounds(tileTopLeftFigureCoordinate, tileBottomRightFigureCoordinate);
    }

    /**
     * 根据瓦片计算瓦片图左上角的实际图坐标
     *
     * @param figure
     * @param projection
     * @param tile
     * @return
     */
    public Coordinate tileToTileTopLeftFigureCoordinate(Figure figure, Projection projection, Tile tile) {
        Coordinate tileTopLeftProjectionCoordinate = tileToTileTopLeftProjectionCoordinate(projection, tile);
        return projectionCoordinateToFigureCoordinate(figure, projection, tileTopLeftProjectionCoordinate);
    }

    /**
     * 根据瓦片计算瓦片图左上角的投影图坐标
     *
     * @param projection
     * @param tile
     * @return
     */
    public Coordinate tileToTileTopLeftProjectionCoordinate(Projection projection, Tile tile) {
        double tileSize = projection.getTileSize();
        double x = tile.getColumn() * tileSize;
        double y = tile.getRow() * tileSize;
        return new Coordinate(x, y);
    }


    /**
     * 根据投影图范围计算实际图范围
     *
     * @param figure
     * @param projection
     * @param projectionBounds
     * @return
     */
    public Bounds projectionBoundsToFigureBounds(Figure figure, Projection projection, Bounds projectionBounds) {
        Coordinate topLeftFigureCoordinate = projectionCoordinateToFigureCoordinate(figure, projection,
                projectionBounds.getTopLeftCoordinate());
        Coordinate bottomRightFigureCoordinate = projectionCoordinateToFigureCoordinate(figure, projection,
                projectionBounds.getBottomRightCoordinate());
        return new Bounds(topLeftFigureCoordinate, bottomRightFigureCoordinate);
    }

    /**
     * 根据实际图范围计算投影图范围
     *
     * @param figure
     * @param projection
     * @param figureBounds
     * @return
     */
    public Bounds figureBoundsToProjectionBounds(Figure figure, Projection projection, Bounds figureBounds) {
        Coordinate projectionTopLeftCoordinate = figureCoordinateToProjectionCoordinate(figure, projection,
                figureBounds.getTopLeftCoordinate());
        Coordinate projectionBottomRightCoordinate = figureCoordinateToProjectionCoordinate(figure, projection,
                figureBounds.getBottomRightCoordinate());
        return new Bounds(projectionTopLeftCoordinate, projectionBottomRightCoordinate);
    }

    /**
     * 根据实际图范围计算瓦片范围
     *
     * @param projection
     * @param figure
     * @param figureBounds
     * @return
     */
    public TileBounds figureBoundsToTileBounds(Figure figure, Projection projection, Bounds figureBounds) {
        Bounds projectionBounds = figureBoundsToProjectionBounds(figure, projection, figureBounds);
        return projectionBoundsToTileBounds(projection, projectionBounds);
    }

    /**
     * 根据投影图范围计算瓦片范围
     *
     * @param projection
     * @param projectionBounds
     * @return
     */
    public TileBounds projectionBoundsToTileBounds(Projection projection, Bounds projectionBounds) {
        Tile topLeftTile = projectionCoordinateToTile(projection, projectionBounds.getTopLeftCoordinate());
        Tile bottomRightTile = projectionCoordinateToTile(projection, projectionBounds.getBottomRightCoordinate());
        return new TileBounds(projection.getZoomLevel(), topLeftTile, bottomRightTile);
    }

    /**
     * 计算实际坐标在给定投影图中的视图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param figureCoordinate
     * @return
     */
    public Coordinate figureCoordinateToViewCoordinate(Figure figure, View view, Projection projection,
                                                       Coordinate viewCenterFigureCoordinate,
                                                       Coordinate figureCoordinate) {
        return figureCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate,
                figureCoordinate.getX(), figureCoordinate.getY());
    }

    /**
     * 计算实际坐标在给定投影图中的视图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param figureCoordinateX
     * @param figureCoordinateY
     * @return
     */
    public Coordinate figureCoordinateToViewCoordinate(Figure figure, View view, Projection projection,
                                                       Coordinate viewCenterFigureCoordinate, double figureCoordinateX,
                                                       double figureCoordinateY) {
        Coordinate projectionCoordinate = figureCoordinateToProjectionCoordinate(figure, projection, figureCoordinateX,
                figureCoordinateY);
        return projectionCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate,
                projectionCoordinate);
    }

    /**
     * 计算投影图坐标在给定投影图中的视图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param projectionCoordinate
     * @return
     */
    public Coordinate projectionCoordinateToViewCoordinate(Figure figure, View view, Projection projection,
                                                           Coordinate viewCenterFigureCoordinate,
                                                           Coordinate projectionCoordinate) {
        return projectionCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate,
                projectionCoordinate.getX(), projectionCoordinate.getY());
    }

    /**
     * 计算投影图坐标在给定投影图中的投影图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param projectionCoordinateX
     * @param projectionCoordinateY
     * @return
     */
    public Coordinate projectionCoordinateToViewCoordinate(Figure figure, View view, Projection projection,
                                                           Coordinate viewCenterFigureCoordinate,
                                                           double projectionCoordinateX, double projectionCoordinateY) {
        Coordinate viewCenterProjectionCoordinate = figureCoordinateToProjectionCoordinate(figure, projection,
                viewCenterFigureCoordinate);
        Coordinate viewCenterCoordinate = view.getBounds().getCenterCoordinate();
        double viewCoordinateX = projectionCoordinateX - (viewCenterProjectionCoordinate.getX() - viewCenterCoordinate.getX());
        double viewCoordinateY = projectionCoordinateY - (viewCenterProjectionCoordinate.getY() - viewCenterCoordinate.getY());
        Coordinate viewCoordinate = new Coordinate(viewCoordinateX, viewCoordinateY);
        return viewCoordinate;
    }


    /**
     * 根据瓦片计算瓦片图左上角的实际图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param tile
     * @return
     */
    public Coordinate tileToTileTopLeftViewCoordinate(Figure figure, View view, Projection projection,
                                                      Coordinate viewCenterFigureCoordinate, Tile tile) {
        return tileToTileTopLeftViewCoordinate(figure, view, projection, viewCenterFigureCoordinate, tile.getColumn(),
                tile.getRow());
    }

    /**
     * 根据瓦片计算瓦片图左上角的实际图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param tileColumn
     * @param tileRow
     * @return
     */
    public Coordinate tileToTileTopLeftViewCoordinate(Figure figure, View view, Projection projection,
                                                      Coordinate viewCenterFigureCoordinate, int tileColumn,
                                                      int tileRow) {
        Coordinate tileToTileTopLeftProjectionCoordinate = tileToTileTopLeftProjectionCoordinate(projection,
                new Tile(projection.getZoomLevel(), tileColumn, tileRow));
        return projectionCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate,
                tileToTileTopLeftProjectionCoordinate);
    }

    /**
     * 计算视图坐标在给定投影图中的实际坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewCoordinate
     * @return
     */
    public Coordinate viewCoordinateToFigureCoordinate(Figure figure, View view, Projection projection,
                                                       Coordinate viewCenterFigureCoordinate,
                                                       Coordinate viewCoordinate) {
        return viewCoordinateToFigureCoordinate(figure, view, projection, viewCenterFigureCoordinate,
                viewCoordinate.getX(), viewCoordinate.getY());
    }

    /**
     * 计算视图坐标在给定投影图中的实际图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewCoordinateX
     * @param viewCoordinateY
     * @return
     */
    public Coordinate viewCoordinateToFigureCoordinate(Figure figure, View view, Projection projection,
                                                       Coordinate viewCenterFigureCoordinate,
                                                       double viewCoordinateX,
                                                       double viewCoordinateY) {
        Coordinate projectionCoordinate = viewCoordinateToProjectionCoordinate(figure, view, projection,
                viewCenterFigureCoordinate, viewCoordinateX, viewCoordinateY);
        return projectionCoordinateToFigureCoordinate(figure, projection, projectionCoordinate);
    }

    /**
     * 计算视图坐标在给定投影图中的投影图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewCoordinate
     * @return
     */
    public Coordinate viewCoordinateToProjectionCoordinate(Figure figure, View view, Projection projection,
                                                           Coordinate viewCenterFigureCoordinate,
                                                           Coordinate viewCoordinate) {
        return viewCoordinateToProjectionCoordinate(figure, view, projection, viewCenterFigureCoordinate,
                viewCoordinate.getX(), viewCoordinate.getY());
    }

    /**
     * 计算视图坐标在给定投影图中的投影图坐标
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewCoordinateX
     * @param viewCoordinateY
     * @return
     */
    public Coordinate viewCoordinateToProjectionCoordinate(Figure figure, View view, Projection projection,
                                                           Coordinate viewCenterFigureCoordinate,
                                                           double viewCoordinateX, double viewCoordinateY) {
        Coordinate viewCenterProjectionCoordinate = figureCoordinateToProjectionCoordinate(figure, projection,
                viewCenterFigureCoordinate);
        Coordinate viewCenterCoordinate = view.getBounds().getCenterCoordinate();
        double projectionCoordinateX = viewCoordinateX + (viewCenterProjectionCoordinate.getX() - viewCenterCoordinate.getX());
        double projectionCoordinateY = viewCoordinateY + (viewCenterProjectionCoordinate.getY() - viewCenterCoordinate.getY());
        return new Coordinate(projectionCoordinateX, projectionCoordinateY);
    }

    /**
     * 根据视图坐标计算瓦片
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewCoordinate
     * @return
     */
    public Tile viewCoordinateToTile(Figure figure, View view, Projection projection,
                                     Coordinate viewCenterFigureCoordinate,
                                     Coordinate viewCoordinate) {
        return viewCoordinateToTile(figure, view, projection, viewCenterFigureCoordinate, viewCoordinate.getX(),
                viewCoordinate.getY());
    }

    /**
     * 根据视图坐标计算瓦片
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewCoordinateX
     * @param viewCoordinateY
     * @return
     */
    private Tile viewCoordinateToTile(Figure figure, View view, Projection projection,
                                      Coordinate viewCenterFigureCoordinate, double viewCoordinateX,
                                      double viewCoordinateY) {
        Coordinate projectionCoordinate = viewCoordinateToProjectionCoordinate(figure, view, projection,
                viewCenterFigureCoordinate, viewCoordinateX, viewCoordinateY);
        return projectionCoordinateToTile(projection, projectionCoordinate);
    }

    /**
     * 根据视图范围计算实际图范围
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewBounds
     * @return
     */
    public Bounds viewBoundsToFigureBounds(Figure figure, View view, Projection projection,
                                           Coordinate viewCenterFigureCoordinate,
                                           Bounds viewBounds) {
        Coordinate figureTopLeftCoordinate = viewCoordinateToFigureCoordinate(figure, view, projection,
                viewCenterFigureCoordinate, viewBounds.getTopLeftCoordinate());
        Coordinate figureBottomRightCoordinate = viewCoordinateToFigureCoordinate(figure, view, projection,
                viewCenterFigureCoordinate, viewBounds.getBottomRightCoordinate());
        return new Bounds(figureTopLeftCoordinate, figureBottomRightCoordinate);
    }

    /**
     * 根据视图范围计算投影图范围
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewBounds
     * @return
     */
    public Bounds viewBoundsToProjectionBounds(Figure figure, View view, Projection projection,
                                               Coordinate viewCenterFigureCoordinate, Bounds viewBounds) {
        Coordinate projectionTopLeftCoordinate = viewCoordinateToProjectionCoordinate(figure, view, projection,
                viewCenterFigureCoordinate, viewBounds.getTopLeftCoordinate());
        Coordinate projectionBottomRightCoordinate = viewCoordinateToProjectionCoordinate(figure, view, projection,
                viewCenterFigureCoordinate, viewBounds.getBottomRightCoordinate());
        return new Bounds(projectionTopLeftCoordinate, projectionBottomRightCoordinate);
    }

    /**
     * 根据视图范围计算瓦片范围
     *
     * @param figure
     * @param view
     * @param projection
     * @param viewCenterFigureCoordinate
     * @param viewBounds
     * @return
     */
    public TileBounds viewBoundsToTileBounds(Figure figure, View view, Projection projection,
                                             Coordinate viewCenterFigureCoordinate, Bounds viewBounds) {
        Bounds figureBounds = viewBoundsToFigureBounds(figure, view, projection, viewCenterFigureCoordinate,
                viewBounds);
        return figureBoundsToTileBounds(figure, projection, figureBounds);
    }
}
