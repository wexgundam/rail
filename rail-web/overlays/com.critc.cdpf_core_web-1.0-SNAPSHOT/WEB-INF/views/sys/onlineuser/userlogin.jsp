<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<body>
<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a
				href="${dynamicServer}/index.htm">首页</a></li>
			<li>>系统管理</li>
			<li>>在线用户</li>
		</ul>
	</div>

<br/>
 <div class="row">
    <input type="hidden" value="${id }" id="uId"/>
    <div class="col-md-12">
        <table id="simple-table" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th width=30>#</th>
                <th width=100>登录时间</th>
                <th width="100">登录IP</th>
                <th width="100">终端</th>
                <th width="120">浏览器类型</th>                                        
                <th width="80">浏览器版本</th>
            </tr>
            </thead>
            <tbody>
             <c:forEach items="${list }" var="sysLog" varStatus="st">
                <tr>
                    <td>${st.index+1 }</td>
                    <td width=40><fmt:formatDate value="${sysLog.loginDate }" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${sysLog.loginIp }</td>
                   
                    <td>${sysLog.terminal }</td>
                    <td>${sysLog.explorerType }</td>
                    <td>${sysLog.explorerVersion }</td>
                  
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="row">
		<div class="col-xs-12">${pageNavigate.pageModel}</div>
	</div>
</body>
<!-- <critc-script>
<script>
//登录记录


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
    var table = $("#simple-table").dataTable({
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
            param.userId = $("#uId").val();
            $.ajax({
                type: "GET",
                url: "${dynamicServer}/sys/userlogin/searchUserLogin.htm",
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
            {"data": "loginDate"},
            {"data": "loginIp"},
            {"data": "terminal"},
            {"data": "explorerType"},
            {"data": "explorerVersion"}
        ]
    }).api();
    //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
});
</script></critc-script> -->