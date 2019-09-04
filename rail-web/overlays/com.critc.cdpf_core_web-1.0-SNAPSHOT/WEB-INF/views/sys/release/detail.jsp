<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<head>
    <title>系统说明</title>
    <critc-css>
        <style>
        	.release{
        		position: relative;
        		top:8px;
        	}
        </style>
    </critc-css>
    
    <script type="text/javascript" charset="utf-8" src="${staticServer}/assets/example/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${staticServer}/assets/example/ueditor/ueditor.all.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${staticServer}/assets/example/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>

<body>
<!-- BEGIN PAGE BAR -->
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a>
        </li>
        <li>
            >系统管理
        </li>
        <li>
            >系统说明
        </li>
    </ul>
</div>
<h1 class="page-title"> 系统说明
    <small>>>查看系统说明</small>
</h1>
<div class="row">
    <div class="col-md-12">
        <form id="sysReleaseForm" name="sysReleaseForm" class="form-horizontal" action="#" method="post">
            <div class="form-body">
                <div class="form-group">
                    <label class="col-md-3 control-label">系统名称：</label>
                    <div  class="col-md-9 release">
                         ${sysRelease.name}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">版本号：</label>
                    <div  class="col-md-9 release">
                        ${sysRelease.version}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">发布日期：</label>
                    <div class="col-md-9 release">
                        ${sysRelease.releaseDate}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">发布人：</label>
                    <div class="col-md-9 release">
                        ${sysRelease.releaseBy}
                    </div>
                </div>
               
                <div class="form-group">
                    <label class="col-md-3 control-label">发布内容：</label>
                    <input type="hidden" id="releaseContent" value="${sysRelease.releaseContent}" >
                    <div style="position: relative;top:-10px;" id="releaseContentDiv" class="col-md-9">
                    </div>
                    
                </div>
            </div>
            <div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="button" class="btn default" onclick="history.back(-1)"><i
                                class="fa fa-undo"/></i>  返回
                        </button>
                    </div>
                </div>
        </div>

        </form>

    </div>
</div>

<critc-script>
    <script type="text/javascript">
	    $(document).ready(function () {
	    	$.ajax({
				  url: "readFile.htm",
				  data: {path:$("#releaseContent").val()},
				  success: function(data){
					  $("#releaseContentDiv").html(data);
				  } 
			});
	    });
      </script>
 </critc-script>
</body>