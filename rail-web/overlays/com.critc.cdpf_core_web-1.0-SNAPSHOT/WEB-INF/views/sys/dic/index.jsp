<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<head>
    <title>字典管理</title>
    <critc-css>
        <link href="${staticServer}/assets/treetable/treeTable.min.css?version=${versionNo}" rel="stylesheet"
              type="text/css"/>
    </critc-css>
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
            >字典管理
        </li>
    </ul>
</div>
<div class="portlet box blue">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-cogs"></i>操作面板
        </div>
        <div class="tools">
            <a href="javascript:;" class="collapse"> </a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-responsive">
            <table class="searchField" style="margin: 4px; padding: 4px;">
				<tr>
					<td>
                        <button class="btn btn-primary" id="btnRefresh">
                            <i class="fa fa-search"></i> 刷新
                        </button>
                        <c:if test="${critc:isP('SysDicToAddCategory')}">
                            <button type="button" class="btn btn-success" id="btnAddCategory">
                                <i class=" fa fa-plus bigger-110"></i> 新增类别
                            </button>
                        </c:if>
                    </td>
				</tr>
            </table>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-10">
        <table id="treeTable" class="table table-striped table-bordered table-hover">
            <thead>
				<tr>
					<th width=120>字典名称</th>
					<th width=120>字典代码</th>
					<th width="160">操作</th>
				</tr>
			</thead>
            <tbody>
                 <c:forEach items="${list}" var="sysDic" varStatus="st">
                 <c:choose>  
                    <c:when test="${sysDic.pid eq 0 }">
						<tr id="${sysDic.id}" pId="${sysDic.pid}">
								<td >${sysDic.name }</td>
								<td>${sysDic.code }</td>
								<td><c:if test="${critc:isP('SysDicToUpdateCategory')}">
										<a href="toMaintenance.htm?category=${sysDic.code }&backUrl=${backUrl}"> 字典明细 </a>
									</c:if> <c:if test="${critc:isP('SysDicDelete')}">
										<a href="javascript:delSysDic(${sysDic.id},'${sysDic.code}');"> 删除 </a>
									</c:if></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr id="${sysDic.id}" pId="${sysDic.pid}">
								<td >${sysDic.name }</td>
								<td>${sysDic.code }</td>
						</tr>
					</c:otherwise>
				  </c:choose>
			    </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col-md-12">${ pageNavigate.pageModel}</div>
</div>
</body>
<critc-script>
    <script src="${staticServer }/assets/treetable/jquery.treeTable.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            $("#btnRefresh").bind('click', refresh);
            $("#btnAdd").bind('click', addDic);
            $("#btnAddCategory").bind("click",addCategory);
            $("#treeTable").treeTable({
            });
        })

        // 刷新
        var refresh = function () {
            var url = "index.htm?";
            window.location = encodeURI(url);
        }
        
        /**
                              删除 字典信息
            id: 字典的id                  
            category: 类别,用于判断类别下面是否存在字典，
                              如果类别下面存在字典则不允许删除
        */
        var delSysDic = function (id,category) {
        	var flag = false;
        	if(typeof(category)!="undefined" && category!=null){
        		$.ajax({
        			   url: "checkCateoryExistDic.htm",
        			   data:{"category": category},
        			   success: function(json){
        				   var data = JSON.parse(json);
        				   if(data.result){
        					   bootbox.confirm("该分类下面还有字典存在，您确定要删除吗?", function (result) {
        		                    if (result) {
        		                    	window.location = "delete.htm?category=" + category + "&backUrl=${backUrl}";
        		                    } 
        		               }) 
           			      }else{
           			    	bootbox.confirm("您确定要删除该类别的字典信息吗?", function (result) {
    		                    if (result) {
    		                    	window.location = "delete.htm?category=" + category + "&backUrl=${backUrl}";
    		                    } 
    		                }) 
           			      }
        			   }
        		});
        		
        		
        	} else{
        		bootbox.confirm("你确定要删除该字典信息吗？", function (result) {
                    if (result) {
                    	window.location = "delete.htm?id=" + id + "&backUrl=${backUrl}";
                    } 
               })
        	} 
        }
        //新增
        var addDic = function () {
            window.location = 'toAdd.htm?backUrl=${backUrl }';
        }
        
        //新增类别
        var addCategory = function(){
        	window.location = 'toAddCategory.htm?backUrl=${backUrl }';
        }
        
        //清空缓存
        var clearCache = function () {
            window.location = 'clearCache.htm?backUrl=${backUrl }';
        }
    </script>
</critc-script>