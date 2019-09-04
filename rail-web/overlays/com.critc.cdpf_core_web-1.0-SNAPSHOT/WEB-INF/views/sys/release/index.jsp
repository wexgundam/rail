<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<head>
    <title>系统说明</title>
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
            <table class="searchTable">
                <tr>
                    <td>系统名称：</td>
                    <td><input type="text" id="name" class="form-control input-small"
                               placeholder=""
                               value="${sysReleaseSearchVO.name }"></td>
                    <td>版本号：</td>
                    <td><input type="text" id="version" class="form-control input-middle"
                               placeholder=""
                               value="${sysReleaseSearchVO.version }"></td>
                     
                    <%-- <td>发布日期：</td>
                    <td><input type="text" id="releaseDate" class="form-control input-middle"
                               placeholder=""
                               value="${sysReleaseSearchVO.releaseDate }"></td> --%>
                     
                     <td>发布人：</td>
                     <td><input type="text" id="releaseBy"  class="form-control input-middle"
                               placeholder=""
                               value="${sysReleaseSearchVO.releaseBy }"></td>
                    
                     <td>
                        <button class="btn btn-primary" id="btnSearch">
                            <i class="ace-icon fa fa-search"></i> 查询
                        </button>
                        <c:if test="${critc:isP('SysReleaseAdd')}">
                            <button type="button" class="btn btn-success" id="btnAdd">
                                <i class="ace-icon fa fa-plus bigger-110"></i>新增
                            </button>
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <table id="simple-table" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th width=40>#</th>
                <th width=120>系统名称</th>
                <th width="120">版本号</th>
                <th width="120">发布日期</th>
                <th width="120">发布人</th>
                <th width="140">升级记录</th>
                <th width="160">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list }" var="sysRelease" varStatus="st">
                <tr>
                    <td>${st.index+1 }</td>
                    <td>${sysRelease.name }</td>
                    <td>${sysRelease.version }</td>
                    <td>${sysRelease.releaseDate }</td>
                    <td>${sysRelease.releaseBy }</td>
                    <td><c:if test="${critc:isP('SysReleaseDetail')}">
                        <a href="toDetail.htm?id=${sysRelease.id }&backUrl=${backUrl}"> 查看 </a>
                    </c:if>
                    </td>
                    <td>
                        <c:if test="${critc:isP('SysReleaseUpdate')}">
                         <a href="toUpdate.htm?id=${sysRelease.id }&backUrl=${backUrl}"> 修改 </a>
                    </c:if>
                         <c:if test="${critc:isP('SysReleaseDelete')}">
                        <a href="javascript:delSysRelease(${sysRelease.id });"> 删除 </a>
                    </c:if>
                    
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">${ pageNavigate.pageModel}</div>
</div>
<critc-script>
	<script type="text/javascript">
		 $(function () {
	         $("#btnAdd").bind('click', addSysRelease);
	         $("#btnSearch").bind('click', searchSysRelease);
	     })
	     
	     
	     // 删除
         var delSysRelease = function (id) {
            bootbox.confirm("你确定要删除该系统说明吗？", function (result) {
                if (result) {
                    window.location = "delete.htm?id=" + id + "&backUrl=${backUrl}";
                }
            })
         }
	     
	     var addSysRelease = function () {
	            window.location = 'toAdd.htm?backUrl=${backUrl }';
	     }
	     
	     // 查询方法
         var searchSysRelease = function () {
             var url = "index.htm?";
             if ($("#name").val() != '')
                url += "name=" + $("#name").val();
             if ($("#version").val() != '')
                url += "&version=" + $("#version").val();
             if ($("#releaseBy").val() != '')
                url += "&releaseBy=" + $("#releaseBy").val();
             window.location = encodeURI(url);
         }
	
	</script>
</critc-script>
</body>