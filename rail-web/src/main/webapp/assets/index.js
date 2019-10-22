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
         * 矢量瓦片系统参数
         * @type {VectorTile.VectorTileSystem}
         */
            //设置矢量瓦片系统的初始参数
        var view = new Object();
        //视图范围宽
        view.width = canvas.width;
        //视图范围高
        view.height = canvas.height;
        //当前缩放等级0
        view.currentZoomLevel = 5;
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
        //是否重新加载视图
        view.toLoad = false;

        /**
         * 设置绘图环境
         */
        var stage = new createjs.Stage("vectorTileCanvas");
        stage.enableMouseOver(20);

        // these are equivalent, 1000ms / 40fps = 25ms
        createjs.Ticker.interval = 1000;
        createjs.Ticker.framerate = 2;
        createjs.Ticker.addEventListener("tick", handleTick);

        function handleTick(event) {
            if (view.toLoad) {

                if (view.lockZoom) {
                    view.lockZoomPreviousZoomLevel = view.currentZoomLevel;
                    view.currentZoomLevel += view.lockZoomZoomDelta;
                }

                load(stage, view);
            }

            stage.update();
        }

        function Point(x, y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 定义界面事件与监听器
         */
        var StageListener = function (view) {
            this.view = view;
            this.dragging = false;
            this.dragCoordinate = null;
        };
        StageListener.prototype.mouseDown = function (e) {
            this.dragging = true;
            this.dragCoordinate = new Point(e.offsetX, e.offsetY);
        };
        StageListener.prototype.mouseUp = function (e) {
            this.dragging = false;
            this.view.viewCenterViewCoordinateDeltaX += e.offsetX - this.dragCoordinate.x;
            this.view.viewCenterViewCoordinateDeltaY += e.offsetY - this.dragCoordinate.y;
            this.dragCoordinate = new Point(e.offsetX, e.offsetY);
            this.view.toLoad = true;
        };
        StageListener.prototype.mouseMove = function (e) {
            if (!this.dragging) {
                return;
            }
        };
        StageListener.prototype.mouseWheel = function (e) {
            this.view.lockZoomZoomDelta = e.wheelDelta < 0 ? -1 : 1;
            this.view.lockZoomLockedViewCoordinateX = e.offsetX;
            this.view.lockZoomLockedViewCoordinateY = e.offsetY;
            this.view.lockZoom = true;
            this.view.toLoad = true;
        };

        var stageListener = new StageListener(view);
        canvas.addEventListener("mousedown", function (e) {
            stageListener.mouseDown(e);
        });
        canvas.addEventListener("mousemove", function (e) {
            stageListener.mouseMove(e);
        });
        canvas.addEventListener("mouseup", function (e) {
            stageListener.mouseUp(e);
        });
        canvas.addEventListener("mousewheel", function (e) {
            stageListener.mouseWheel(e);
        });

        $("#zoomIn").on("click", function (e) {
            newZoomLevel = 1;
            view.toLoad = true;
        });
        $("#zoomOut").on("click", function (e) {
            newZoomLevel = -1;
            view.toLoad = true;
        });
        $("#moveUp").on("click", function (e) {
            view.viewCenterViewCoordinateDeltaY += -256;
            view.toLoad = true;
        });
        $("#moveDown").on("click", function (e) {
            view.viewCenterViewCoordinateDeltaY += 256;
            view.toLoad = true;
        });
        $("#moveLeft").on("click", function (e) {
            view.viewCenterViewCoordinateDeltaX += -256;
            view.toLoad = true;
        });
        $("#moveRight").on("click", function (e) {
            view.viewCenterViewCoordinateDeltaX += 256;
            view.toLoad = true;
        });
        $("#moveCenter").on("click", function (e) {
            view.viewCenterViewCoordinateDeltaX = 0;
            view.viewCenterViewCoordinateDeltaY = 0;
            view.toLoad = true;
        });
        $("#load").on("click", function (e) {
            view.toLoad = true;
        });

        function load(stage, view) {
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
                        stage.removeAllChildren();

                        view.currentZoomLevel = result.data.zoomLevel;
                        view.viewCenterViewCoordinateDeltaX = result.data.viewCenterViewCoordinateDeltaX;
                        view.viewCenterViewCoordinateDeltaY = result.data.viewCenterViewCoordinateDeltaY;
                        view.lockZoom = false;

                        paint(stage, result);

                        view.toLoad = false;
                    }
                }
            });
        }

        function paint(stage, result) {
            var radios = 3;
            for (var index = 0; index < result.data.points.length; index++) {
                var nodePointShape = new createjs.Shape();
                var point = result.data.points[index];
                nodePointShape.graphics.beginFill("#FFFFFF").drawCircle(point.x, point.y, radios).endFill();
                nodePointShape.cursor = "pointer";
                nodePointShape.orignalX = point.x;
                nodePointShape.orignalY = point.y;

                function handleInteraction(event) {
                    var orignalX = event.target.orignalX;
                    var orignalY = event.target.orignalY;
                    var graphics = event.target.graphics;
                    if (event.type == "mouseover") {
                        graphics.clear().beginFill("red").drawCircle(orignalX, orignalY, radios);
                    } else {
                        graphics.clear().beginFill("#FFFFFF").drawCircle(orignalX, orignalY, radios);
                    }
                }

                nodePointShape.on("mouseover", handleInteraction);
                nodePointShape.on("mouseout", handleInteraction);

                stage.addChild(nodePointShape);
            }

            for (var index = 0; index < result.data.texts.length; index++) {
                var text = result.data.texts[index];
                var nodeTextShape = new createjs.Text(text.text, "10px Arial", "#FFFFFF");
                nodeTextShape.x = text.x;
                nodeTextShape.y = text.y;
                nodeTextShape.textBaseline = "middle";
                nodeTextShape.cursor = "pointer";

                var hitArea = new createjs.Shape();
                hitArea.graphics.beginFill("#000").drawRect(0, -nodeTextShape.getMeasuredHeight() / 2,
                    nodeTextShape.getMeasuredWidth(), nodeTextShape.getMeasuredHeight());
                nodeTextShape.hitArea = hitArea;

                function handleInteraction(event) {
                    event.target.color = (event.type == "mouseover") ? "red" : "#FFFFFF";
                }

                nodeTextShape.on("mouseover", handleInteraction);
                nodeTextShape.on("mouseout", handleInteraction);

                stage.addChild(nodeTextShape);
            }

            for (var index = 0; index < result.data.lineStrings.length; index++) {
                var lineString = result.data.lineStrings[index];
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
                        stage.update();
                    } else {
                        var xys = event.target.xys;
                        var graphics = event.target.graphics;
                        graphics.clear().beginStroke("#FFFFFF").setStrokeStyle(1).moveTo(xys[0], xys[1]);
                        for (var xysIndex = 2; xysIndex < xys.length; xysIndex += 2) {
                            graphics.lineTo(xys[xysIndex], xys[xysIndex + 1]);
                        }
                        graphics.endStroke();
                        stage.update();
                    }
                }

                lineStringShape.on("mouseover", handleInteraction);
                lineStringShape.on("mouseout", handleInteraction);
                stage.addChild(lineStringShape);

            }
        }

        view.toLoad = true;
    });
})(jQuery);