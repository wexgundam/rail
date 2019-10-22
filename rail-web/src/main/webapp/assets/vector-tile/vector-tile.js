/**
 * Created by Administrator on 2017/1/13.
 */
(function ($) {
    $(function () {
        /**
         * 界面参数
         * @type {Element}
         */
            //初始化canvas尺寸
            //必须通过这个方法设置canvas画板尺寸，其他方式只是设置css显示尺寸
        var canvas = document.getElementById("vectorTileCanvas");
        var pageContentParents = $(canvas).parents(".page-content");
        pageContentParents.css("padding-top", "0px");
        pageContentParents.css("padding-bottom", "0px");
        pageContentParents.css("padding-left", "0px");
        pageContentParents.css("padding-right", "0px");
        pageContentParents.css("background-color", "#000000");
        var actions = pageContentParents.find("#actions");
        canvas.width = pageContentParents.width();
        canvas.height = pageContentParents.height() - actions.outerHeight() - 4;

        /**
         * 设置绘图环境
         */
        var stage = new createjs.Stage("vectorTileCanvas");
        stage.enableMouseOver(20);

        /**
         * 矢量瓦片系统的视图对象
         * @type {Object}
         */
        var view = new Object();
        //关联stage
        view.stage = stage;
        //视图范围宽
        view.width = canvas.width;
        //视图范围高
        view.height = canvas.height;
        //当前缩放等级0
        view.currentZoomLevel = 5;
        //缩放等级变化量
        view.zoomLevelDelta = 0;
        //最小缩放等级0
        view.minZoomLevel = 0;
        //最大缩放等级10
        view.maxZoomLevel = 10;
        //实际图中心点的视图坐标的横向偏移量
        view.viewCenterViewCoordinateDeltaX = 0;
        //实际图中心点的视图坐标的纵向偏移量
        view.viewCenterViewCoordinateDeltaY = 0;
        //是否是缩放锁定
        view.lockZoom = false;
        //缩放锁定缩放等级增减幅度
        view.lockZoomZoomDelta = 0;
        //缩放锁定目标缩放等级0
        view.lockZoomPreviousZoomLevel = view.currentZoomLevel;
        //缩放时锁定的视图坐标的横坐标，默认为视图中心点
        view.lockZoomLockedViewCoordinateX = view.width / 2;
        //缩放时锁定的视图坐标的纵坐标
        view.lockZoomLockedViewCoordinateY = view.height / 2;
        //视图加载类型：0：不加载；1：初始化加载；2：平移加载；3：无锁缩放加载；4有锁缩放加载。默认初始化加载
        view.loadType = 1;
        //清空图形
        view.clearShapes = function () {
            //移除所有图形
            this.stage.removeAllChildren();
        };
        //绘制图形
        view.addShape = function (shape) {
            this.stage.addChild(shape);
        };
        //绘图视图
        view.paint = function () {
            this.stage.update();
        };
        //加载视图
        view.load = function () {
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
        view.doLoad = function (view) {
        };


        /**
         * 定义界面事件与监听器
         */
        var ActionHandler = function (view) {
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

        var actionHandler = new ActionHandler(view);
        canvas.addEventListener("mousedown", function (e) {
            actionHandler.mouseDown(e);
        });
        canvas.addEventListener("mousemove", function (e) {
            actionHandler.mouseMove(e);
        });
        canvas.addEventListener("mouseup", function (e) {
            actionHandler.mouseUp(e);
        });
        canvas.addEventListener("mousewheel", function (e) {
            actionHandler.mouseWheel(e);
        });

        $("#zoomIn").on("click", function (e) {
            actionHandler.zoomIn(e);
        });
        $("#zoomOut").on("click", function (e) {
            actionHandler.zoomOut(e);
        });
        $("#moveUp").on("click", function (e) {
            actionHandler.moveUp(e);
        });
        $("#moveDown").on("click", function (e) {
            actionHandler.moveDown(e);
        });
        $("#moveLeft").on("click", function (e) {
            actionHandler.moveLeft(e);
        });
        $("#moveRight").on("click", function (e) {
            actionHandler.moveRight(e);
        });
        $("#moveCenter").on("click", function (e) {
            actionHandler.moveCenter(e);
        });
        $("#load").on("click", function (e) {
            actionHandler.moveCenter(e);
        });

        //创建点类图形
        var createPointShape = function (point) {
            var radios = 3;
            var pointShape = new createjs.Shape();
            pointShape.graphics.beginFill("#FFFFFF").drawCircle(point.x, point.y, radios).endFill();
            pointShape.cursor = "pointer";
            pointShape.orignalX = point.x;
            pointShape.orignalY = point.y;

            function handleInteraction(event) {
                var originalX = event.target.orignalX;
                var originalY = event.target.orignalY;
                var graphics = event.target.graphics;
                if (event.type == "mouseover") {
                    graphics.clear().beginFill("red").drawCircle(originalX, originalY, radios);
                } else {
                    graphics.clear().beginFill("#FFFFFF").drawCircle(originalX, originalY, radios);
                }
            }

            pointShape.on("mouseover", handleInteraction);
            pointShape.on("mouseout", handleInteraction);

            return pointShape;
        };

        //创建文本类图形
        var createTextShape = function (text) {
            var textShape = new createjs.Text(text.text, "10px Arial", "#FFFFFF");
            textShape.x = text.x;
            textShape.y = text.y;
            textShape.textBaseline = "middle";
            textShape.cursor = "pointer";

            var hitArea = new createjs.Shape();
            hitArea.graphics.beginFill("#000").drawRect(0, -textShape.getMeasuredHeight() / 2,
                textShape.getMeasuredWidth(), textShape.getMeasuredHeight());
            textShape.hitArea = hitArea;

            function handleInteraction(event) {
                event.target.color = (event.type == "mouseover") ? "red" : "#FFFFFF";
            }

            textShape.on("mouseover", handleInteraction);
            textShape.on("mouseout", handleInteraction);

            return textShape;
        };

        //创建线串类图形
        var createLineStringShape = function (lineString) {
            var lineStringShape = new createjs.Shape();
            var graphics = lineStringShape.graphics;
            var xys = lineString.xys;
            graphics.beginStroke("#FFFFFF").setStrokeStyle(1).moveTo(xys[0], xys[1]);
            for (var xysIndex = 2; xysIndex < xys.length; xysIndex += 2) {
                graphics.lineTo(xys[xysIndex], xys[xysIndex + 1]);
            }
            graphics.endStroke();
            lineStringShape.cursor = "pointer";
            lineStringShape.xys = xys;

            function handleInteraction(event) {
                if (event.type == "mouseover") {
                    var xys = event.target.xys;
                    var graphics = event.target.graphics;
                    graphics.clear().beginStroke("red").setStrokeStyle(3).moveTo(xys[0], xys[1]);
                    for (var xysIndex = 2; xysIndex < xys.length; xysIndex += 2) {
                        graphics.lineTo(xys[xysIndex], xys[xysIndex + 1]);
                    }
                    graphics.endStroke();
                    this.stage.update();
                } else {
                    var xys = event.target.xys;
                    var graphics = event.target.graphics;
                    graphics.clear().beginStroke("#FFFFFF").setStrokeStyle(1).moveTo(xys[0], xys[1]);
                    for (var xysIndex = 2; xysIndex < xys.length; xysIndex += 2) {
                        graphics.lineTo(xys[xysIndex], xys[xysIndex + 1]);
                    }
                    graphics.endStroke();
                    this.stage.update();
                }
            }

            lineStringShape.on("mouseover", handleInteraction);
            lineStringShape.on("mouseout", handleInteraction);

            return lineStringShape;
        };

        view.doLoad = function (view) {
            var url = 'http://localhost:8092/rail/features.htm?';
            url += 'current-zoom-level=' + view.currentZoomLevel;
            url += '&view-width=' + view.width;
            url += '&view-height=' + view.height;
            url += '&view-center-view-coordinate-delta-x=' + view.viewCenterViewCoordinateDeltaX;
            url += '&view-center-view-coordinate-delta-y=' + view.viewCenterViewCoordinateDeltaY;
            url += '&lock-zoom=' + view.lockZoom;
            if (view.lockZoom) {
                url += '&lock-zoom-previous-zoom-level=' + view.lockZoomPreviousZoomLevel;
                url += '&lock-zoom-zoom-delta=' + view.lockZoomZoomDelta;
                url += '&lock-zoom-locked-view-coordinate-x=' + view.lockZoomLockedViewCoordinateX;
                url += '&lock-zoom-locked-view-coordinate-y=' + view.lockZoomLockedViewCoordinateY;
            }

            $.ajax({
                type: 'GET',
                url: url,
                dataType: 'json',
                success: function (result) {
                    if (result["success"]) {
                        view.currentZoomLevel = result.data.zoomLevel;
                        view.viewCenterViewCoordinateDeltaX = result.data.viewCenterViewCoordinateDeltaX;
                        view.viewCenterViewCoordinateDeltaY = result.data.viewCenterViewCoordinateDeltaY;
                        view.lockZoom = false;

                        view.clearShapes();

                        for (var index = 0; index < result.data.points.length; index++) {
                            var point = result.data.points[index];
                            view.addShape(createPointShape(point));
                        }
                        for (var index = 0; index < result.data.texts.length; index++) {
                            var text = result.data.texts[index];
                            view.addShape(createTextShape(text));
                        }
                        for (var index = 0; index < result.data.lineStrings.length; index++) {
                            var lineString = result.data.lineStrings[index];
                            view.addShape(createLineStringShape(lineString));
                        }
                    }
                }
            });
        };

        //启动定时器，定时刷新
        // these are equivalent, 1000ms / 40fps = 25ms
        createjs.Ticker.interval = 1000;
        createjs.Ticker.framerate = 2;
        createjs.Ticker.addEventListener("tick", function (event) {
            view.load();
            view.paint();
        });
    });

})
(jQuery);