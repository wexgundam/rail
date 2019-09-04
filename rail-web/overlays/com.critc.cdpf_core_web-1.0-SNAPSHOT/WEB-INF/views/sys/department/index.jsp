<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<head>
<title>组织机构</title>
<critc-css>
<link
	href="${staticServer}/assets/treetable/treeTable.min.css?version=${versionNo}"
	rel="stylesheet" type="text/css" />
</critc-css>
</head>

<body>
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a
				href="${dynamicServer}/index.htm">首页</a></li>
			<li>>系统管理</li>
			<li>>组织机构</li>
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
						<td>
							<button class="btn btn-primary" id="btnSearch">
								<i class="fa fa-search"></i> 刷新
							</button> <c:if test="${critc:isP('SysDepartmentAdd')}">
								<button type="button" class="btn btn-success" id="btnAdd">
									<i class=" fa fa-plus bigger-110"></i> 新增
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
			<table id="treeTable"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th width=200>部门名称</th>
						<th width=100>部门类型</th>
						<th width=120>部门编码</th>
						<th width=80>排序</th>
						<th width=100>备注</th>
						<th width=100>上次修改人</th>
						<th width=160>上次修改时间</th>
						<th width="241">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="department" varStatus="st">
						<tr id="${department.id}" pId="${department.parentId}">
							<td>${department.name}</td>
							<td>${department.typeName}</td>
							<td>${department.code}</td>
							<td>${department.displayOrder}</td>
							<td>${department.note}</td>
							<td>${department.lastEditorRealName}</td>
							<td><fmt:formatDate value="${department.lastEditedAt}"
									pattern="yyyy-MM-dd HH:mm" /></td>
							<td><c:if test="${critc:isP('SysDepartmentUpdate')}">
									<a href="toUpdate.htm?id=${department.id}&backUrl=${backUrl}">
										修改</i>
									</a>
								</c:if> <c:if test="${critc:isP('SysDepartmentDelete')}">
									<a href="javascript:delModule(${department.id });"> 删除 </a>
								</c:if> <a
								href="javascript:viewUser(${department.id },'${department.name}')">系统用户
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">${ pageNavigate.pageModel}</div>
	</div>

	<div class="modal fade" id="dia-viewUser" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document"
			style="width: 1000px; height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body">
					<table id="user" class="display" cellspacing="0" width="800">
						<thead>
							<tr>
								<th width="40">#</th>
								<th width="120">姓名</th>
								<th width="120">账号</th>
								<th width="120">职务</th>
								<th width="120">办公电话</th>
								<th width="120">手机号码</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
<critc-script> <script
	src="${staticServer }/assets/treetable/jquery.treeTable.min.js"
	type="text/javascript"></script> <script type="text/javascript">
    var selDepartmentId = 0;//定义选择的selDepartmentId
        $(function () {
            $("#btnSearch").bind('click', searchModule);
            $("#btnAdd").bind('click', addUser);
            $("#treeTable").treeTable({
                expandLevel: 2
            });
        })

        // 查询方法
        var searchModule = function () {
            var url = "index.htm?";
            window.location = encodeURI(url);
        }
        // 删除
        var delModule = function (id) {
        	//先查询是否存在系统用户属于该部门，存在用户则不允许删除
        	 $.ajax({
                 type: "GET",
                 url: "searchUser.htm",
                 cache: false,  //禁用缓存
                 data: {departmentId: id},  //传入组装的参数
                 dataType: "json",
                 success: function (result) {
                	 if(result.total==0){
                		 bootbox.confirm("你确定要删除该机构吗？", function (result) {
                             if (result) {
                                 window.location = "delete.htm?id=" + id + "&backUrl=${backUrl}";
                             }
                	 })
                	 }else{
                		 bootbox.alert({
         					buttons : {
         						ok : {
         							label : '确定'
         						}
         					},
         					message : '存在系统用户，不能删除该部门！'
         				});
         				return;
                	 }
                 }
                 })
        }
        //新增
        var addUser = function (id) {
            window.location = 'toAdd.htm?backUrl=${backUrl }';
        }
        var viewUser = function (departmentId,departmentIdName) {
            selDepartmentId = departmentId;
            $('#myModalLabel').text(departmentIdName);
            $('#dia-viewUser').modal('show')
            $('#user').DataTable().ajax.reload();

        }

        $(function () {
            //提示信息
            var lang = {
                //"sProcessing": "处理中...",
                "sLengthMenu": "每页 _MENU_ 项",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页",
                    "sJump": "跳转"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            };

            //初始化表格
            var table = $("#user").dataTable({
                scrollY: '50vh',
                scrollCollapse: true,
                language: lang,  //提示信息
                autoWidth: false,  //禁用自动调整列宽
                stripeClasses: ["odd", "even"],  //为奇偶行加上样式，兼容不支持CSS伪类的场合
                processing: true,  //隐藏加载提示,自行处理
                serverSide: true,  //启用服务器端分页
                searching: false,  //禁用原生搜索
                orderMulti: false,  //启用多列排序
                ordering: false,
                bLengthChange: false,   //去掉每页显示多少条数据方法
                pageLength: 20,//每页多少条记录
                order: [],  //取消默认排序查询,否则复选框一列会出现小箭头
                renderer: "bootstrap",  //渲染样式：Bootstrap和jquery-ui
                pagingType: "bootstrap_full_number",  //分页样式：simple,simple_numbers,full,full_numbers
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    var param = {};
                    param.pageSize = 20;//页面显示记录条数，在页面显示每页显示多少项的时候
                    param.start = data.start;//开始的记录序号
                    param.pageIndex = (data.start / data.length) + 1;//当前页码
                    param.departmentId = selDepartmentId;
                    $.ajax({
                        type: "GET",
                        url: "searchUser.htm",
                        cache: false,  //禁用缓存
                        data: param,  //传入组装的参数
                        dataType: "json",
                        success: function (result) {
                            //setTimeout仅为测试延迟效果
                            setTimeout(function () {
                                //封装返回数据
                                var returnData = {};
                                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                                returnData.recordsTotal = result.total;//返回数据全部记录
                                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                                returnData.data = result.data;//返回的数据列表
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(returnData);
                            }, 200);
                        }
                    });
                },
                //列表表头字段
                columns: [
                    {
                        "data": "index", "render": function (data, type, row, meta) {
                        var startIndex = meta.settings._iDisplayStart;
                        return startIndex + meta.row + 1;
                    }
                    },
                    {"data": "realName"},
                    {"data": "username"},
                    {"data": "post"},
                    {"data": "telephone"},
                    {"data": "mobile"}
                ]
            }).api();
            //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
        });
    </script> </critc-script>