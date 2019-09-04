<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>

<head>
    <title>用户管理</title>
</head>
<critc-css>
    <link rel="stylesheet"
          href="${staticServer}/assets/cropper3.0/cropper.min.css"/>
    <link rel="stylesheet" href="${staticServer}/assets/cropper3.0/main.css"/>
    <link href="${staticServer}/assets/metronic4.7/pages/css/profile.min.css" rel="stylesheet" type="text/css"/>
    <link href="${staticServer }/assets/zTree3.5/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
</critc-css>
<body class="no-skin">

<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a>
        </li>

        <li>>
            系统管理
        </li>
        <li>
            >用户管理
        </li>


    </ul>
</div>


<h1 class="page-title inline"> 用户管理

    <small>>>查看详情</small>


</h1>
<div class="row col-md-10 col-md-offset-1">
    <form id="userForm" name="userForm" class="form-horizontal "
          action="update.htm" method="post">
        <div class="tabbable-custom  nav-justified">
            <ul class="nav nav-tabs ">
                <li class="active" id="pt">
                    <a href="#tab_1_1" data-toggle="tab"> 基本信息 </a>
                </li>
                <li class="" id="yx">
                    <a href="#tab_1_2" data-toggle="tab"> 更多信息 </a>
                </li>
            </ul>
            <input type="hidden" name="tab" value="1" id="tab2">
            <div class="tab-content ">

                <div class="tab-pane fade active in " id="tab_1_1">
                    <div class="row ">
                        <div class="col-md-7">
                            <input type="hidden" name="backUrl" value="${backUrl }">
                            <input type="hidden" name="id" id="id" value="${sysUser.id}">
                            <div class="form-group">
                                <label class="col-md-3 control-label">账号：</label>
                                <div class="col-md-9">
                                    <input id="username" name="username" type="text"
                                           class="form-control input-inline input-medium"
                                           placeholder="" readonly="readonly"
                                           style="border: hidden;background: none" value="${sysUser.username}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">真实姓名：</label>
                                <div class="col-md-9">
                                    <input id="realName" type="text" name="realName"
                                           style="border: hidden;background: none" readonly
                                           class="form-control input-inline input-medium "
                                           placeholder="" value="${sysUser.realName}"><label
                                        id="realNameTip"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">姓名拼音：</label>
                                <div class="col-md-9">
                                    <input id="pinyin" type="text" name="pinyin"
                                           style="border: hidden;background: none" readonly
                                           class="form-control input-inline input-medium"
                                           placeholder="" value="${sysUser.pinyin}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">性别：</label>
                                <div class="col-md-9 ">
                                    <input id="gender" type="text" name="gender"
                                           class="form-control input-inline input-medium "
                                           placeholder="" value="${sysUser.gender}"
                                           style="border: hidden;background: none" readonly>

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">手机：</label>
                                <div class="col-md-9">
                                    <input id="mobile" type="text" name="mobile"
                                           style="border: hidden;background: none" readonly
                                           class="form-control input-inline input-medium"
                                           placeholder="" value="${sysUser.mobile}"/><label
                                        id="mobileTip"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">部门：</label>
                                <div class="col-md-9">

                                    <input id="departmentName" type="text" name="departmentName"
                                           class="form-control input-inline input-medium "
                                           placeholder="" value="${sysUser.departmentName}"
                                           style="border: hidden;background: none" readonly>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">角色：</label>
                                <div class="col-md-9">
                                    <input id="roleName" type="text" name="roleName"
                                           class="form-control input-inline input-medium "
                                           placeholder="" value="${sysUser.roleName}"
                                           style="border: hidden;background: none" readonly>
                                </div>
                            </div>
                        </div>

                        <div class="profile-sidebar col-md-3">
                            <!-- PORTLET MAIN -->
                            <div class="portlet light profile-sidebar-portlet  bg-inverse"
                                 style="width:70%;height:60%;margin:0 auto">
                                <!-- SIDEBAR USERPIC -->
                                <div lass="" style="width:70%;height:60%;margin:0 auto">
                                    <img id="imgAvatar" src="${imageServer}${sysUser.avatar}"
                                         class="img-responsive"   onerror="this.onerror=null;this.src =
                                            '${imageServer}/sys/user/avatar/male.jpg'" alt="">
                                </div>
                                <!-- END SIDEBAR USERPIC -->
                                <br/>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="tab-pane fade active in" id="tab_1_2">
                <input type="hidden" name="backUrl" value="${backUrl }"/>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">邮箱：</label>
                        <div class="col-md-8">

                            <input id="email" type="text" name="email" style="border: hidden;background: none"
                                   readonly
                                   class="form-control input-inline " style="width:90%"
                                   placeholder="无" value="${sysUser.email}"><label
                                id="emailTip"></label>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">职务：</label>
                        <div class="col-md-8 ">
                            <input id="jobTitle" type="text" name="jobTitle"
                                   class="form-control input-inline input-medium "
                                   placeholder="无" value="${sysUser.jobTitle}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">职称：</label>
                        <div class="col-md-8">
                            <input id="post" type="text" name="post"
                                   class="form-control input-inline input-medium "
                                   placeholder="无" value="${sysUser.post}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">身份证号：</label>
                        <div class="col-md-8">
                            <input id="idcard" type="text" name="idcard"
                                   class="form-control input-inline "
                                   placeholder="无" value="${sysUser.idcard}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">政治面貌：</label>
                        <div class="col-md-8">

                            <input id="political" type="text" name="political"
                                   class="form-control input-inline input-medium "
                                   placeholder="无" value="${sysUser.political}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">民族：</label>
                        <div class="col-md-8">

                            <input id="nation" type="text" name="nation"
                                   class="form-control input-inline input-medium "
                                   placeholder="无" value="${sysUser.nation}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">教育程度：</label>
                        <div class="col-md-8">


                            <input id="education" type="text" name="education"
                                   class="form-control input-inline input-medium "
                                   placeholder="无" value="${sysUser.education}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>

                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">毕业院校：</label>
                        <div class="col-md-8">
                            <input id="graduatedSchool" type="text" name="graduatedSchool"
                                   class="form-control input-inline "
                                   placeholder="无" value="${sysUser.graduatedSchool}"
                                   style="border: hidden;background: none" readonly/><label
                                id="graduatedSchoolTip"></label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">座机：</label>
                        <div class="col-md-8">
                            <input id="telephone" type="text" name="telephone"
                                   class="form-control input-inline "
                                   placeholder="无" value="${sysUser.telephone}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">专业：</label>
                        <div class="col-md-8">
                            <input id="major" type="text" name="major"
                                   class="form-control input-inline "
                                   placeholder="无" value="${sysUser.major}"
                                   style="border: hidden;background: none" readonly><label
                                id="majorTip"></label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-6">
                        <label class="col-md-4 control-label">排序：</label>
                        <div class="col-md-8">
                            <input id="displayOrder" type="text" name="displayOrder"
                                   class="form-control input-inline "
                                   placeholder="无" value="${sysUser.displayOrder}"
                                   style="border: hidden;background: none" readonly>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
        <div style="text-align:left;">
            <button type="button" class="btn btn-primary" id="btnLoginHis"
                    onclick="viewLoginHis('${sysUser.id}','${sysUser.username}')">登录记录
            </button>
            <button type="button" class="btn btn-primary" id="btnOperHis"
                    onclick="viewOperHis('${sysUser.id}','${sysUser.username}')">操作记录
            </button>
            <button type="button" class="btn default" onclick="history.back(-1)"><i
                    class="fa fa-undo"/></i> 返回
            </button>
        </div>
</form>

</div>
<div class="modal fade" id="dialog-viewOperHis" tabindex="-1"
     role="dialog">
    <div class="modal-dialog" role="document"
         style="width: 1000px; height: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">Ã</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">操作日志</h4>
            </div>
            <div class="modal-body">
                <table id="operHis" class="display" cellspacing="0" width="800">
                    <thead>
                    <tr>
                        <th width=40>#</th>
                        <th width="100">用户姓名</th>
                        <th width="100">账号</th>
                        <th width="160">操作时间</th>
                        <th width="160">操作IP</th>
                        <th width="120">模块名称</th>
                        <th width="120">操作名称</th>
                        <th>操作url</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeoperHisTable" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="dialog-viewLogin" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width:1000px;height:500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="">登录历史</h4>
            </div>
            <div class="modal-body">
                <table id="loginHis" class="display" cellspacing="0" width="800">
                    <thead>
                    <tr>
                        <th width="40">#</th>
                        <th width="140">登录时间</th>
                        <th width="120">登录IP</th>
                        <th>终端</th>
                        <th>浏览器类型</th>
                        <th>浏览器版本</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" id="closeLoginHisTable" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>

<critc-script>
    <script
            src="${staticServer}/assets/cropper3.0/cropper.min.js"></script>
    <script
            src="${staticServer}/assets/cropper3.0/main.js"></script>
    <script src="${staticServer }/assets/zTree3.5/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {

            if (${sysUser.avatar eq null}) {

                if (${sysUser.gender eq '女'}) {
                    document.getElementById('imgAvatar').src = "${imageServer}/sys/user/avatar/female.jpg";
                } else {
                    document.getElementById('imgAvatar').src = "${imageServer}/sys/user/avatar/male.jpg";
                }

            }
init();
        });
        function init() {
            $("#tab_1_1").addClass("active in");
            $("#tab_1_2").removeClass("active in");
            $("#pt").addClass("active");
            $("#yx").removeClass("active");

        }
        //操作历史
        var viewOperHis = function (id, title) {
            $('#dialog-viewOperHis').modal('show')
            $('#operHis').DataTable().ajax.reload();
        }
        //操作历史表格
        $(function () {
            //提示信息
            var lan = {
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
            var table = $("#operHis").dataTable({
                scrollY: '50vh',
                scrollCollapse: true,
                language: lan,  //提示信息
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
                    param.userId = ${sysUser.id};
                    param.username = '${sysUser.username}';
                    $.ajax({
                        type: "GET",
                        url: "${dynamicServer}/sys/log/searchUserOper.htm",
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
                    {"data": "operaDate"},
                    {"data": "operaIp"},
                    {"data": "moduleName"},
                    {"data": "operaName"},
                    {"data": "operaUrl"}
                ]
            }).api();
            //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象
        });
        //登录记录
        var viewLoginHis = function (id, title) {
            $('#dialog-viewLogin').modal('show')
            $('#loginHis').DataTable().ajax.reload();
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
            var table = $("#loginHis").dataTable({
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
                    param.userId = ${sysUser.id};
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
        });

    </script>
</critc-script>