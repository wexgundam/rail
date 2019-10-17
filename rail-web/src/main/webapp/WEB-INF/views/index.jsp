<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/11
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>

    <critc-script>
        <script src="${staticServer}/assets/vector-tile/easeljs.min.js" type="text/javascript"></script>
        <script src="${staticServer}/assets/vector-tile/vector-tile.js" type="text/javascript"></script>
        <script src="${staticServer}/assets/index.js" type="text/javascript"></script>
    </critc-script>
</head>
<body>
    <div>
        <div>
            <div id="actions" style="padding-top:5px;padding-bottom:5px;background-color: #FFFFFF">
                <a href="#" id="load" class="easyui-linkbutton">加载</a>
                <a href="#" id="zoomIn" class="easyui-linkbutton">放大</a>
                <a href="#" id="zoomOut" class="easyui-linkbutton">缩小</a>
                <a href="#" id="moveUp" class="easyui-linkbutton">上移</a>
                <a href="#" id="moveDown" class="easyui-linkbutton">下移</a>
                <a href="#" id="moveLeft" class="easyui-linkbutton">左移</a>
                <a href="#" id="moveRight" class="easyui-linkbutton">右移</a>
                <a href="#" id="moveCenter" class="easyui-linkbutton">居中</a>
            </div>
        </div>
        <div>
            <div id="vectorTileCanvasDiv">
                <canvas id="vectorTileCanvas" style="background-color: #000000;"></canvas>
            </div>
        </div>
    </div>
</body>
</html>