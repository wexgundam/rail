/**
 * Created by Administrator on 2017/1/14.
 */
//定义命名空间
var VectorTile = new Object();
(function ($) {
    /**
     * 定义二维坐标对象
     * @param x
     * @param y
     * @constructor
     */
    $.Coordinate = function (x, y) {
        this.x = x;
        this.y = y;
    };
    //二维坐标复制方法
    $.Coordinate.prototype.clone = function () {
        var coordinate = new VectorTile.Coordinate(this.x, this.y);
        return coordinate;
    };
    //二维坐标转为数组
    $.Coordinate.prototype.toArray = function () {
        return [this.x, this.y];
    };

    /**
     * 定义矩形边界对象
     * @param topLeftCoordinate
     * @param bottomRightCoordinate
     * @constructor
     */
    $.Bounds = function (topLeftCoordinate, bottomRightCoordinate) {
        this.topLeftCoordinate = topLeftCoordinate;
        this.bottomRightCoordinate = bottomRightCoordinate;
    };
    //获得边界最大横坐标
    $.Bounds.prototype.getMaxX = function () {
        return Math.max(this.topLeftCoordinate.x, this.bottomRightCoordinate.x);
    };
    //获得边界最大纵坐标
    $.Bounds.prototype.getMaxY = function () {
        return Math.max(this.topLeftCoordinate.y, this.bottomRightCoordinate.y);
    };
    //获得边界最小横坐标
    $.Bounds.prototype.getMinX = function () {
        return Math.min(this.topLeftCoordinate.x, this.bottomRightCoordinate.x);
    };
    //获得边界最小纵坐标
    $.Bounds.prototype.getMinY = function () {
        return Math.min(this.topLeftCoordinate.y, this.bottomRightCoordinate.y);
    };
    //获得边界宽
    $.Bounds.prototype.getWidth = function () {
        return this.getMaxX() - this.getMinX();
    };
    //获得边界高
    $.Bounds.prototype.getHeight = function () {
        return this.getMaxY() - this.getMinY();
    };
    //获得边界的最大尺寸
    $.Bounds.prototype.getSize = function () {
        return Math.max(this.getWidth(), this.getHeight());
    };
    //获得边界的中心点坐标
    $.Bounds.prototype.getCenterCoordinate = function () {
        var x = (this.topLeftCoordinate.x + this.bottomRightCoordinate.x) / 2;
        var y = (this.topLeftCoordinate.y + this.bottomRightCoordinate.y) / 2;
        return new $.Coordinate(x, y);
    };

    /**
     * 定义实际图对象
     * @param size
     * @constructor
     */
    $.Figure = function (size) {
        this.size = size ? size : 40000;
        this.centerCoordinate = new $.Coordinate(0, 0);
        this.bounds = new $.Bounds(new $.Coordinate(-size / 2, size / 2), new $.Coordinate(size / 2, -size / 2));
    };

    /**
     * 定义视图对象
     * @constructor
     */
    $.View = function () {
        this.bounds = null;
    };

    /**
     * 定义投影图对象
     * @param figureBounds
     * @param zoomLevel
     * @param tileSize
     * @constructor
     */
    $.Projection = function (figureBounds, zoomLevel, tileSize) {
        this.figureBounds = figureBounds;
        this.currentZoomLevel = zoomLevel;
        this.tileSize = tileSize;
    };
    //获得投影图的最大尺寸
    $.Projection.prototype.getSize = function () {
        return this.tileSize * Math.pow(2, this.currentZoomLevel);
    };
    //获得投影图的解析度
    $.Projection.prototype.getResolution = function () {
        return this.figureBounds.getSize() / this.getSize();
    };
    //获得投影图的边界
    $.Projection.prototype.getBounds = function () {
        var size = this.getSize();
        return new $.Bounds(new $.Coordinate(0, 0), new $.Coordinate(size, size));
    };

    /**
     * 定义瓦片对象
     * @param zoomLevel
     * @param column
     * @param row
     * @constructor
     */
    $.Tile = function (zoomLevel, column, row) {
        this.currentZoomLevel = zoomLevel;
        this.column = column;
        this.row = row;
    };
    //创建瓦片Id
    $.Tile.createId = function (zoomLevel, column, row) {
        var separator = "/";
        return zoomLevel + separator + column + separator + row;
    };
    //获得瓦片Id
    $.Tile.prototype.getId = function () {
        return $.Tile.createId(this.currentZoomLevel, this.column, this.row);
    };

    /**
     * 定义瓦片边界
     * @param zoomLevel
     * @param topLeftTile
     * @param bottomRightTile
     * @constructor
     */
    $.TileBounds = function (zoomLevel, topLeftTile, bottomRightTile) {
        this.topLeftTile = topLeftTile;
        this.bottomRightTile = bottomRightTile;
        this.currentZoomLevel = zoomLevel;
    };

    /**
     * 定义可视瓦片状态集
     * @constructor
     */
    $.VisibleTilesState = function () {
        this.renderedTiles = new Array();
        this.unrenderedTiles = new Array();
    };

    /**
     * 定义坐标系统
     * @constructor
     */
    $.CoordinateSystem = function () {
    };
    //计算实际坐标在给定投影图中的投影图坐标
    $.CoordinateSystem.prototype.figureCoordinateToProjectionCoordinate = function (figure,
                                                                                    projection,
                                                                                    figureCoordinateX,
                                                                                    figureCoordinateY) {
        var resolution = projection.getResolution();
        var figureTopLeftCoordinate = figure.bounds.topLeftCoordinate.clone();
        var x = (figureCoordinateX - figureTopLeftCoordinate.x) / resolution;
        var y = (figureTopLeftCoordinate.y - figureCoordinateY) / resolution;
        return new $.Coordinate(x, y);
    };
    /**
     * 计算给定投影图的投影图坐标的实际坐标
     *
     * @param figure
     * @param projection
     * @param projectionCoordinateX
     * @param projectionCoordinateY
     * @return
     */
    $.CoordinateSystem.prototype.projectionCoordinateToFigureCoordinate = function (figure,
                                                                                    projection,
                                                                                    projectionCoordinateX,
                                                                                    projectionCoordinateY) {
        var figureTopLeftCoordinate = figure.bounds.topLeftCoordinate.clone();
        var resolution = projection.getResolution();
        var x = figureTopLeftCoordinate.x + projectionCoordinateX * resolution;
        var y = figureTopLeftCoordinate.y - projectionCoordinateY * resolution;
        return new $.Coordinate(x, y);
    };
    /**
     * 计算实际图坐标在给定投影图的瓦片
     *
     * @param figure
     * @param projection
     * @param figureCoordinateX
     * @param figureCoordinateY
     * @return
     */
    $.CoordinateSystem.prototype.figureCoordinateToTile = function (figure,
                                                                    projection,
                                                                    figureCoordinateX,
                                                                    figureCoordinateY) {
        var bounds = figure.bounds;
        // 计算实际坐标据实际图左上角坐标的距离，除以分辨率和瓦片size，获得瓦片
        var resolution = projection.getResolution();
        var tileSize = projection.tileSize;
        var column = Math.floor((figureCoordinateX - bounds.topLeftCoordinate.x) / resolution / tileSize);
        var row = Math.floor((bounds.getMaxY() - figureCoordinateY) / resolution / tileSize);
        var tile = new $.Tile(column, row, projection.currentZoomLevel);
        return tile;
    };
    /**
     * 计算投影图坐标在给定投影图的瓦片
     *
     * @param projection
     * @param projectionCoordinateX
     * @param projectionCoordinateY
     * @return
     */
    $.CoordinateSystem.prototype.projectionCoordinateToTile = function (projection,
                                                                        projectionCoordinateX,
                                                                        projectionCoordinateY) {
        var tileSize = projection.tileSize;
        var projectionBounds = projection.getBounds();

        var column;
        var row;
        if (projectionCoordinateX <= projectionBounds.getMinX()) {
            column = 0;
        } else if (projectionCoordinateX >= projectionBounds.getMaxX()) {
            column = Math.pow(2, projection.currentZoomLevel) - 1;
        } else {
            column = Math.floor(projectionCoordinateX / tileSize);
        }
        if (projectionCoordinateY <= projectionBounds.getMinY()) {
            row = 0;
        } else if (projectionCoordinateY >= projectionBounds.getMaxY()) {
            row = Math.pow(2, projection.currentZoomLevel) - 1;
        } else {
            row = Math.floor(projectionCoordinateY / tileSize);
        }
        var tile = new $.Tile(projection.currentZoomLevel, column, row);
        return tile;
    };

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
    $.CoordinateSystem.prototype.figureCoordinateToTileImageCoordinate = function (figure,
                                                                                   projection,
                                                                                   tile,
                                                                                   figureCoordinateX,
                                                                                   figureCoordinateY) {
        var projectionCoordinate = this.figureCoordinateToProjectionCoordinate(figure, projection, figureCoordinateX,
            figureCoordinateY);
        var tileSize = projection.tileSize;
        var x = projectionCoordinate.x - tile.column * tileSize;
        var y = projectionCoordinate.y - tile.row * tileSize;
        return new $.Coordinate(x, y);
    };
    /**
     * 根据瓦片计算瓦片图左上角的投影图坐标
     *
     * @param projection
     * @param tile
     * @return
     */
    $.CoordinateSystem.prototype.tileToTileTopLeftProjectionCoordinate = function (projection, tile) {
        var tileSize = projection.tileSize;
        var x = tile.column * tileSize;
        var y = tile.row * tileSize;
        return new $.Coordinate(x, y);
    };
    /**
     * 根据实际图范围计算投影图范围
     *
     * @param figure
     * @param projection
     * @param figureBounds
     * @return
     */
    $.CoordinateSystem.prototype.figureBoundsToProjectionBounds = function (figure, projection, figureBounds) {
        var projectionTopLeftCoordinate = this.figureCoordinateToProjectionCoordinate(figure, projection,
            figureBounds.topLeftCoordinate.x, figureBounds.topLeftCoordinate.y);
        var projectionBottomRightCoordinate = this.figureCoordinateToProjectionCoordinate(figure, projection,
            figureBounds.bottomRightCoordinate.x, figureBounds.bottomRightCoordinate.y);
        return new $.Bounds(projectionTopLeftCoordinate, projectionBottomRightCoordinate);
    };
    /**
     * 根据实际图范围计算瓦片范围
     *
     * @param projection
     * @param figure
     * @param figureBounds
     * @return
     */
    $.CoordinateSystem.prototype.figureBoundsToTileBounds = function (figure, projection, figureBounds) {
        var projectionBounds = this.figureBoundsToProjectionBounds(figure, projection, figureBounds);
        return this.projectionBoundsToTileBounds(projection, projectionBounds);
    };
    /**
     * 根据投影图范围计算瓦片范围
     *
     * @param projection
     * @param projectionBounds
     * @return
     */
    $.CoordinateSystem.prototype.projectionBoundsToTileBounds = function (projection, projectionBounds) {
        var topLeftTile = this.projectionCoordinateToTile(projection, projectionBounds.topLeftCoordinate.x,
            projectionBounds.topLeftCoordinate.y);
        var bottomRightTile = this.projectionCoordinateToTile(projection, projectionBounds.bottomRightCoordinate.x,
            projectionBounds.bottomRightCoordinate.y);
        return new $.TileBounds(projection.currentZoomLevel, topLeftTile, bottomRightTile);
    };
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
    $.CoordinateSystem.prototype.figureCoordinateToViewCoordinate = function (figure,
                                                                              view,
                                                                              projection,
                                                                              viewCenterFigureCoordinate,
                                                                              figureCoordinateX,
                                                                              figureCoordinateY) {
        var projectionCoordinate = this.figureCoordinateToProjectionCoordinate(figure, projection, figureCoordinateX,
            figureCoordinateY);
        return this.projectionCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate,
            projectionCoordinate.x, projectionCoordinate.y);
    };
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
    $.CoordinateSystem.prototype.projectionCoordinateToViewCoordinate = function (figure,
                                                                                  view,
                                                                                  projection,
                                                                                  viewCenterFigureCoordinate,
                                                                                  projectionCoordinateX,
                                                                                  projectionCoordinateY) {
        var viewCenterProjectionCoordinate = this.figureCoordinateToProjectionCoordinate(figure, projection,
            viewCenterFigureCoordinate.x, viewCenterFigureCoordinate.y);
        var viewCenterCoordinate = view.bounds.getCenterCoordinate();
        var viewCoordinateX = projectionCoordinateX - (viewCenterProjectionCoordinate.x - viewCenterCoordinate.x);
        var viewCoordinateY = projectionCoordinateY - (viewCenterProjectionCoordinate.y - viewCenterCoordinate.y);
        var viewCoordinate = new $.Coordinate(viewCoordinateX, viewCoordinateY);
        return viewCoordinate;
    };
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
    $.CoordinateSystem.prototype.tileToTileTopLeftViewCoordinate = function (figure,
                                                                             view,
                                                                             projection,
                                                                             viewCenterFigureCoordinate,
                                                                             tileColumn,
                                                                             tileRow) {
        var tileToTileTopLeftProjectionCoordinate = this.tileToTileTopLeftProjectionCoordinate(projection,
            new $.Tile(projection.currentZoomLevel, tileColumn, tileRow));
        return this.projectionCoordinateToViewCoordinate(figure, view, projection, viewCenterFigureCoordinate,
            tileToTileTopLeftProjectionCoordinate.x, tileToTileTopLeftProjectionCoordinate.y);
    };

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
    $.CoordinateSystem.prototype.viewCoordinateToFigureCoordinate = function (figure,
                                                                              view,
                                                                              projection,
                                                                              viewCenterFigureCoordinate,
                                                                              viewCoordinateX,
                                                                              viewCoordinateY) {
        var projectionCoordinate = this.viewCoordinateToProjectionCoordinate(figure, view, projection,
            viewCenterFigureCoordinate, viewCoordinateX, viewCoordinateY);
        return this.projectionCoordinateToFigureCoordinate(figure, projection, projectionCoordinate.x,
            projectionCoordinate.y);
    };

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
    $.CoordinateSystem.prototype.viewCoordinateToProjectionCoordinate = function (figure,
                                                                                  view,
                                                                                  projection,
                                                                                  viewCenterFigureCoordinate,
                                                                                  viewCoordinateX,
                                                                                  viewCoordinateY) {
        var viewCenterProjectionCoordinate = this.figureCoordinateToProjectionCoordinate(figure, projection,
            viewCenterFigureCoordinate.x, viewCenterFigureCoordinate.y);
        var viewCenterCoordinate = view.bounds.getCenterCoordinate();
        var projectionCoordinateX = viewCoordinateX + (viewCenterProjectionCoordinate.x - viewCenterCoordinate.x);
        var projectionCoordinateY = viewCoordinateY + (viewCenterProjectionCoordinate.y - viewCenterCoordinate.y);
        return new $.Coordinate(projectionCoordinateX, projectionCoordinateY);
    };
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
    $.CoordinateSystem.prototype.viewCoordinateToTile = function (figure,
                                                                  view,
                                                                  projection,
                                                                  viewCenterFigureCoordinate,
                                                                  viewCoordinateX,
                                                                  viewCoordinateY) {
        var projectionCoordinate = this.viewCoordinateToProjectionCoordinate(figure, view, projection,
            viewCenterFigureCoordinate, viewCoordinateX, viewCoordinateY);
        return this.projectionCoordinateToTile(projection, projectionCoordinate.x, projectionCoordinate.y);
    };
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
    $.CoordinateSystem.prototype.viewBoundsToFigureBounds = function (figure,
                                                                      view,
                                                                      projection,
                                                                      viewCenterFigureCoordinate,
                                                                      viewBounds) {
        var figureTopLeftCoordinate = this.viewCoordinateToFigureCoordinate(figure, view, projection,
            viewCenterFigureCoordinate, viewBounds.topLeftCoordinate.x, viewBounds.topLeftCoordinate.y);
        var figureBottomRightCoordinate = this.viewCoordinateToFigureCoordinate(figure, view, projection,
            viewCenterFigureCoordinate, viewBounds.bottomRightCoordinate.x, viewBounds.bottomRightCoordinate.y);
        return new $.Bounds(figureTopLeftCoordinate, figureBottomRightCoordinate);
    };

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
    $.CoordinateSystem.prototype.viewBoundsToTileBounds = function (figure,
                                                                    view,
                                                                    projection,
                                                                    viewCenterFigureCoordinate,
                                                                    viewBounds) {
        var figureBounds = this.viewBoundsToFigureBounds(figure, view, projection, viewCenterFigureCoordinate,
            viewBounds);
        return this.figureBoundsToTileBounds(figure, projection, figureBounds);
    };

    /**
     * 示意图
     * <p>
     * 通过painter绘制features
     * <p>
     * view是对视图的描述
     */
    $.VectorTileSystem = function () {
        /**
         * 实际图片
         */
        this.figure = null;
        /**
         * 视图
         */
        this.view = null;
        /**
         * 缩放等级，默认为0
         */
        this.currentZoomLevel = 0;
        /**
         * 最小缩放等级，默认为0
         */
        this.minZoomLevel = 0;
        /**
         * 最大缩放等级，默认为10
         */
        this.maxZoomLevel = 10;
        /**
         * 瓦片尺寸，默认为256×256像素
         */
        this.tileSize = 256;
        /**
         * 视图中心对应的实际图坐标
         * <p>
         * 默认视图中心坐标为实际图显示中心坐标
         */
        this.viewCenterFigureCoordinate = null;
        /**
         * 缓存管理器
         */
        this.cacheManager = null;
        /**
         * 瓦片图缓存
         */
        this.tileImageCache = new Object();
    };
    $.VectorTileSystem.prototype.setViewCenterAsFigureCenter = function () {
        this.viewCenterFigureCoordinate = this.figure.centerCoordinate.clone();
    };
    $.VectorTileSystem.prototype.setZoomLevel = function (zoomLevel) {
        this.currentZoomLevel = Math.max(Math.min(this.maxZoomLevel, zoomLevel), this.minZoomLevel);
    };
    /**
     * 初始化
     */
    $.VectorTileSystem.prototype.initialize = function () {
    };
    /**
     * 销毁
     */
    $.VectorTileSystem.prototype.dispose = function () {
    };
    /**
     * 获得当前缩放等级对应的透视图
     *
     * @return
     */
    $.VectorTileSystem.prototype.getProjection = function (zoomLevel) {
        var projection = new $.Projection(this.figure.bounds, zoomLevel, this.tileSize);
        return projection;
    };

    /**
     * 锁定视图中心，放大一级
     */
    $.VectorTileSystem.prototype.zoomIn = function () {
        this.zoom(this.currentZoomLevel + 1);
    };

    /**
     * 锁定视图中心，缩小一级
     */
    $.VectorTileSystem.prototype.zoomOut = function () {
        this.zoom(this.currentZoomLevel - 1);
    };
    /**
     * 缩放到给定的zoomLevel，并且制定视图坐标在缩放前后代表同一figureCoordinate
     *
     * viewCoordinate为null/undefined是锁定视图中心
     *
     * @param zoomLevel
     * @param viewCooridnate
     */
    $.VectorTileSystem.prototype.zoom = function (zoomLevel, viewCooridnate) {
        viewCooridnate = viewCooridnate ? viewCooridnate : this.view.bounds.getCenterCoordinate();
        var coordinateSystem = new $.CoordinateSystem();

        //获得缩放前投影图
        var beforeZoomProjection = this.getProjection(this.currentZoomLevel);
        //获得视图坐标对应的实际图坐标
        var lockedFigureCoordinate = coordinateSystem.viewCoordinateToFigureCoordinate(this.figure, this.view,
            beforeZoomProjection, this.viewCenterFigureCoordinate, viewCooridnate.x, viewCooridnate.y);

        //设置缩放等级
        this.setZoomLevel(zoomLevel);

        // 获得缩放后的投影图
        var afterZoomProjection = this.getProjection(this.currentZoomLevel);

        // 计算视图中心对应的实际图坐标
        var afterZoomProjectionResolution = afterZoomProjection.getResolution();
        var viewCenterCoordinate = this.view.bounds.getCenterCoordinate();
        var viewCenterFigureCoordinateX = lockedFigureCoordinate.x - viewCooridnate.x * afterZoomProjectionResolution + viewCenterCoordinate.x * afterZoomProjectionResolution;
        var viewCenterFigureCoordinateY = lockedFigureCoordinate.y + viewCooridnate.y * afterZoomProjectionResolution - viewCenterCoordinate.y * afterZoomProjectionResolution;
        this.viewCenterFigureCoordinate = new $.Coordinate(viewCenterFigureCoordinateX, viewCenterFigureCoordinateY);
    };
    /**
     * 移动视图坐标当前中心坐标，使该坐标相对于移动前变更了（deltaX,deltaY）增量值
     *
     * @param deltaX
     * @param deltaY
     */
    $.VectorTileSystem.prototype.moveViewCenterCoordinateDeltaViewCoordinate = function (deltaX, deltaY) {
        var projection = this.getProjection(this.currentZoomLevel);
        var viewCenterCoordinate = this.view.bounds.getCenterCoordinate();

        var coordinateSystem = new $.CoordinateSystem();
        var beforeMoveViewCenterProjectionCoordinate = coordinateSystem.viewCoordinateToProjectionCoordinate(
            this.figure, this.view, projection, this.viewCenterFigureCoordinate, viewCenterCoordinate.x,
            viewCenterCoordinate.y);

        var x = beforeMoveViewCenterProjectionCoordinate.x - deltaX;
        var y = beforeMoveViewCenterProjectionCoordinate.y - deltaY;

        var afterMoveViewCenterProjectionCoordinate = new $.Coordinate(x, y);
        var afterMoveViewCenterFigureCoordinate = coordinateSystem.projectionCoordinateToFigureCoordinate(this.figure,
            projection, afterMoveViewCenterProjectionCoordinate.x, afterMoveViewCenterProjectionCoordinate.y);

        this.viewCenterFigureCoordinate = afterMoveViewCenterFigureCoordinate;
    };
    /**
     * 获得给定zoomLevel对应的可视瓦片状态
     * <p>
     * 如果没有缓存瓦片图，则创建瓦片图，但不绘制瓦片图
     *
     * @return
     */
    $.VectorTileSystem.prototype.getVisibleTilesState = function (zoomLevel) {
        var coordinateSystem = new $.CoordinateSystem();
        var tileBounds = coordinateSystem.viewBoundsToTileBounds(this.figure, this.view, this.getProjection(zoomLevel),
            this.viewCenterFigureCoordinate, this.view.bounds);

        var visibleTilesState = new $.VisibleTilesState();

        for (var column = tileBounds.topLeftTile.column; column <= tileBounds.bottomRightTile.column; column++) {
            for (var row = tileBounds.topLeftTile.row; row <= tileBounds.bottomRightTile.row; row++) {
                var tile = new $.Tile(tileBounds.currentZoomLevel, column, row);
                if (this.tileImageCache[tile.getId()]) {
                    visibleTilesState.renderedTiles.push(tile);
                } else {
                    var tileImage = new VectorTile.TileImage();
                    tileImage.tile = tile;
                    this.tileImageCache[tile.getId()] = tileImage;
                    visibleTilesState.unrenderedTiles.push(tile);
                }
            }
        }
        return visibleTilesState;
    };
    /**
     * 清空tileImageCache
     */
    $.VectorTileSystem.prototype.clearTileImageCache = function () {
        this.tileImageCache = new Object();
    };

    $.TileImage = function () {
        this.tile = null;
        this.container = new createjs.Container();
    };

    $.RenderSystem = function () {
        this.zoomLevelLayersCache = new Object();
    };
    $.RenderSystem.prototype.initialize = function () {
    };
    $.RenderSystem.prototype.dispose = function () {
    };
    $.RenderSystem.prototype.paintView = function (vectorTileSystem, featureSystem, stage) {
        stage.removeAllChildren();
        var visibleTilesState = vectorTileSystem.getVisibleTilesState(vectorTileSystem.currentZoomLevel);
        this.renderTiles(vectorTileSystem, featureSystem, visibleTilesState.unrenderedTiles, stage);
        this.paintTiles(vectorTileSystem, visibleTilesState.renderedTiles, stage);
    };
    $.RenderSystem.prototype.paintTiles = function (vectorTileSystem, tiles, stage) {
        for (var index = 0; index < tiles.length; index++) {
            var tile = tiles[index];
            this.paintTile(vectorTileSystem, tile, stage);
        }
    };
    $.RenderSystem.prototype.paintTile = function (vectorTileSystem, tile, stage) {
        var tileImage = vectorTileSystem.tileImageCache[tile.getId()];
        if (vectorTileSystem.currentZoomLevel != tile.currentZoomLevel) {
            return;
        }
        var coordinateSystem = new $.CoordinateSystem();
        var tileToTileTopLeftViewCoordinate = coordinateSystem.tileToTileTopLeftViewCoordinate(vectorTileSystem.figure,
            vectorTileSystem.view, vectorTileSystem.getProjection(tile.currentZoomLevel),
            vectorTileSystem.viewCenterFigureCoordinate, tile.column, tile.row);
        tileImage.container.x = tileToTileTopLeftViewCoordinate.x;
        tileImage.container.y = tileToTileTopLeftViewCoordinate.y;
        stage.addChild(tileImage.container);
    };
    $.RenderSystem.prototype.renderTiles = function (vectorTileSystem, featureSystem, tiles, stage) {
        for (var index = 0; index < tiles.length; index++) {
            var tile = tiles[index];
            this.renderTile(vectorTileSystem, featureSystem, tile, this.zoomLevelLayersCache[tile.currentZoomLevel].layers,
                stage);
            this.paintTile(vectorTileSystem, tile, stage);
        }
    };
    $.RenderSystem.prototype.renderTile = function (vectorTileSystem, featureSystem, tile, layers, stage) {
        var tileImage = vectorTileSystem.tileImageCache[tile.getId()];

        function setupBackground() {
            var imageSize = vectorTileSystem.tileSize;
            // easeJs内部问题，绘制矩形时产生间隙
            imageSize += 1;
            var background = new createjs.Shape();
            // background.graphics.beginFill("#000000").drawRect(0, 0, imageSize, imageSize).endFill();
            background.graphics.beginFill(getRandomColor()).drawRect(0, 0, imageSize, imageSize).endFill();
            tileImage.container.addChild(background);
        }

        setupBackground();

        var tileFeatures = featureSystem.getTileFeatures(tile);
        for (var layerIndex = 0; layerIndex < layers.length; layerIndex++) {
            var layer = layers[layerIndex];
            for (var index = 0; index < tileFeatures.features.length; index++) {
                var feature = tileFeatures.features[index];
                if (layer.renderable(feature)) {
                    layer.render(vectorTileSystem, tile, feature, tileImage)
                }
            }
        }
    };

    /**
     * 透视图等级与绘图图层
     * @constructor
     */
    $.ZoomLevelLayers = function () {
        this.currentZoomLevel = null;
        this.layers = new Array();
    };

    $.Layer = function () {
    };
    $.Layer.prototype.renderable = function (feature) {
        return false;
    };
    $.Layer.prototype.render = function (vectorTileSystem, tile, feature, tileImage) {

    };


    $.FeatureSystem = function () {
        /**
         * feature加载器
         */
        this.featureLoader = new VectorTile.FeatureLoader();
        /**
         * feature缓存
         */
        this.featureCache = new Object();
        this.featureTilesCache = new Object();
        this.tileFeaturesCache = new Object();
    };
    $.FeatureSystem.prototype.initialize = function () {
    };
    $.FeatureSystem.prototype.dispose = function () {
    };
    $.FeatureSystem.prototype.loadAll = function (tileSize, minZoomLevel, maxZoomLevel) {
        var featurePackage = this.featureLoader.loadAll(tileSize, minZoomLevel, maxZoomLevel);
        if (!featurePackage) {
            return;
        }

        this.featureCache = featurePackage.featureCache;
        this.featureTilesCache = featurePackage.featureTilesCache;
        this.tileFeaturesCache = featurePackage.tileFeaturesCache;
    };
    $.FeatureSystem.prototype.getTileFeatures = function (tile) {
        var tileId = tile.getId();
        if (!this.tileFeaturesCache[tileId]) {
            this.tileFeaturesCache[tileId] = new VectorTile.TileFeatures();
        }
        return this.tileFeaturesCache[tileId];
    };
    $.FeatureSystem.prototype.getFeatureTiles = function (feature) {
        var featureId = feature.id;
        if (!this.featureTilesCache[featureId]) {
            this.featureTilesCache[featureId] = new VectorTile.FeatureTiles();
        }
        return this.featureTilesCache[featureId];
    };

    $.FeatureLoader = function () {

    };
    $.FeatureLoader.prototype.loadAll = function () {
        return null;
    };

    $.TileFeatures = function () {
        this.tile = null;
        this.features = new Array();
    };
    $.FeatureTiles = function () {
        this.feature = null;
        this.tiles = new Array();
    };
})(VectorTile);