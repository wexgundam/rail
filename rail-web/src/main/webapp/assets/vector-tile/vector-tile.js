/**
 * Created by Administrator on 2017/1/13.
 */

/**
 * 矢量瓦片系统的视图对象
 * @type {Object}
 */
function View() {
//关联stage
    this.stage = null;
//视图范围宽
    this.width = 0;
//视图范围高
    this.height = 0;
//当前缩放等级0
    this.currentZoomLevel = 0;
//缩放等级变化量
    this.zoomLevelDelta = 0;
//最小缩放等级0
    this.minZoomLevel = 0;
//最大缩放等级10
    this.maxZoomLevel = 0;
//实际图中心点的视图坐标的横向偏移量
    this.viewCenterViewCoordinateDeltaX = 0;
//实际图中心点的视图坐标的纵向偏移量
    this.viewCenterViewCoordinateDeltaY = 0;
//是否是缩放锁定
    this.lockZoom = false;
//缩放锁定缩放等级增减幅度
    this.lockZoomZoomDelta = 0;
//缩放锁定目标缩放等级0
    this.lockZoomPreviousZoomLevel = 0;
//缩放时锁定的视图坐标的横坐标，默认为视图中心点
    this.lockZoomLockedViewCoordinateX = 0;
//缩放时锁定的视图坐标的纵坐标
    this.lockZoomLockedViewCoordinateY = 0;
//视图加载类型：0：不加载；1：初始化加载；2：平移加载；3：无锁缩放加载；4有锁缩放加载。默认初始化加载
    this.loadType = 0;
};

//清空图形
View.prototype.clearShapes = function () {
    //移除所有图形
    this.stage.removeAllChildren();
};
//绘制图形
View.prototype.addShape = function (shape) {
    this.stage.addChild(shape);
};
//绘图视图
View.prototype.paint = function () {
    this.stage.update();
};
//加载视图
View.prototype.load = function () {
    var toLoad = false;
    if (1 == this.loadType) {//初始化
        toLoad = true;
    } else if (2 == this.loadType) {//非缩放
        toLoad = true;
    } else if (3 == this.loadType) {//无锁缩放
        var newZoomLevel = Math.min(
            Math.max(this.minZoomLevel, this.currentZoomLevel + this.zoomLevelDelta), this.maxZoomLevel);
        if (this.currentZoomLevel != newZoomLevel) {
            this.lockZoomPreviousZoomLevel = this.currentZoomLevel;
            this.currentZoomLevel = newZoomLevel;
            toLoad = true;
        }
    } else if (4 == this.loadType) {//有锁缩放
        var newZoomLevel = Math.min(
            Math.max(this.minZoomLevel, this.currentZoomLevel + this.lockZoomZoomDelta), this.maxZoomLevel);
        if (this.currentZoomLevel != newZoomLevel) {
            this.lockZoomPreviousZoomLevel = this.currentZoomLevel;
            this.currentZoomLevel = newZoomLevel;
            toLoad = true;
        }
    }

    if (toLoad) {
        this.doLoad(this);
        this.loadType = 0;
    }
};
//具体加载方法
View.prototype.doLoad = function (view) {
};

/**
 * 定义界面事件与监听器
 */
function ActionHandler(view) {
    this.view = view;
    this.dragging = false;
    this.dragCoordinateX;
    this.dragCoordinateY;
};
ActionHandler.prototype.mouseDown = function (e) {
    this.dragging = true;
    this.dragCoordinateX = e.offsetX;
    this.dragCoordinateY = e.offsetY;
};
ActionHandler.prototype.mouseUp = function (e) {
    this.dragging = false;
    this.view.viewCenterViewCoordinateDeltaX += e.offsetX - this.dragCoordinateX;
    this.view.viewCenterViewCoordinateDeltaY += e.offsetY - this.dragCoordinateY;
    this.dragCoordinateX = e.offsetX;
    this.dragCoordinateY = e.offsetY;
    this.view.loadType = 2;
};
ActionHandler.prototype.mouseMove = function (e) {
    if (!this.dragging) {
        return;
    }
};
ActionHandler.prototype.mouseWheel = function (e) {
    this.view.lockZoomZoomDelta = e.wheelDelta < 0 ? -1 : 1;
    this.view.lockZoomLockedViewCoordinateX = e.offsetX;
    this.view.lockZoomLockedViewCoordinateY = e.offsetY;
    this.view.lockZoom = true;
    this.view.loadType = 4;
};
ActionHandler.prototype.zoomIn = function (e) {
    this.view.zoomLevelDelta = 1;
    this.view.loadType = 3;
};
ActionHandler.prototype.zoomOut = function (e) {
    this.view.zoomLevelDelta = -1;
    this.view.loadType = 3;
};
ActionHandler.prototype.moveUp = function (e) {
    this.view.viewCenterViewCoordinateDeltaY += -256;
    this.view.loadType = 2;
};
ActionHandler.prototype.moveDown = function (e) {
    this.view.viewCenterViewCoordinateDeltaY += 256;
    this.view.loadType = 2;
};
ActionHandler.prototype.moveLeft = function (e) {
    this.view.viewCenterViewCoordinateDeltaX += -256;
    this.view.loadType = 2;
};
ActionHandler.prototype.moveRight = function (e) {
    this.view.viewCenterViewCoordinateDeltaX += 256;
    this.view.loadType = 2;
};
ActionHandler.prototype.moveCenter = function (e) {
    this.view.viewCenterViewCoordinateDeltaX = 0;
    this.view.viewCenterViewCoordinateDeltaY = 0;
    this.view.loadType = 2;
};
ActionHandler.prototype.load = function (e) {
    this.view.loadType = 2;
};

/**
 * 矢量瓦片系统
 * @constructor
 */
function VectorTile() {
    this.canvasId = null;
    this.canvasWidth = 0;
    this.canvasHeight = 0;
    this.stage = null;
    this.view = null;
    this.actionHandler = null;
};
/**
 * 矢量瓦片系统初始化
 */
VectorTile.prototype.initialize = function () {
    /**
     * 设置绘图环境
     */
    this.stage = new createjs.Stage(this.canvasId);
    this.stage.enableMouseOver(20);

    /**
     * 矢量瓦片系统的视图对象
     * @type {Object}
     */
    this.view = new View();
    //关联stage
    this.view.stage = this.stage;
    //视图范围宽
    this.view.width = this.canvasWidth;
    //视图范围高
    this.view.height = this.canvasHeight;
    //当前缩放等级0
    this.view.currentZoomLevel = 5;
    //缩放等级变化量
    this.view.zoomLevelDelta = 0;
    //最小缩放等级0
    this.view.minZoomLevel = 0;
    //最大缩放等级10
    this.view.maxZoomLevel = 10;
    //实际图中心点的视图坐标的横向偏移量
    this.view.viewCenterViewCoordinateDeltaX = 0;
    //实际图中心点的视图坐标的纵向偏移量
    this.view.viewCenterViewCoordinateDeltaY = 0;
    //是否是缩放锁定
    this.view.lockZoom = false;
    //缩放锁定缩放等级增减幅度
    this.view.lockZoomZoomDelta = 0;
    //缩放锁定目标缩放等级0
    this.view.lockZoomPreviousZoomLevel = this.view.currentZoomLevel;
    //缩放时锁定的视图坐标的横坐标，默认为视图中心点
    this.view.lockZoomLockedViewCoordinateX = this.view.width / 2;
    //缩放时锁定的视图坐标的纵坐标
    this.view.lockZoomLockedViewCoordinateY = this.view.height / 2;
    //视图加载类型：0：不加载；1：初始化加载；2：平移加载；3：无锁缩放加载；4有锁缩放加载。默认初始化加载
    this.view.loadType = 1;

    this.actionHandler = new ActionHandler(this.view);
};
/**
 * 矢量瓦片系统启动
 */
VectorTile.prototype.startUp = function () {
    var vectorTile = this;
    vectorTile.loadType = 1;

    //启动定时器，定时刷新
    // these are equivalent, 1000ms / 40fps = 25ms
    createjs.Ticker.interval = 1000;
    createjs.Ticker.framerate = 2;
    createjs.Ticker.addEventListener("tick", function (event) {
        vectorTile.view.load();
        vectorTile.view.paint();
    });
};