<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>

<head>
    <title>用户管理</title>
</head>
<critc-css>
    <link href="${staticServer }/assets/zTree3.5/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />

</critc-css>
<body>

    <div class="page-bar">
        <ul class="page-breadcrumb">
            <li>
                <i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a>
            </li>
            <li>
                >系统管理
            </li>
            <li>
                >用户管理
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
                        <td>账号：</td>
                        <td><input type="text" id="txtUsername" class="form-control "
                                   placeholder=""
                                   value="${sysUserSearchVO.username }"></td>
                        <td>姓名：</td>
                        <td><input type="text" id="txtRealName" class="form-control "
                                   placeholder=""
                                   value="${sysUserSearchVO.realName }"></td>
                        <td>状态：</td>
                        <td><form:select path="sysUserSearchVO.status" class="form-control " style=" width: 260px"
                                         id="cmbStatus">
                            <form:option value="" label="--全部--" />
                            <form:option value="1">正常</form:option>
                            <form:option value="2">已锁定</form:option>
                        </form:select></td>
                    <tr>


                        <td>角色：</td>
                        <td>
                            <form:select path="sysUserSearchVO.roleId" class="form-control input-medium"
                                         id="cmbRoleId">
                                <form:option value="" label="--全部--" />
                                <form:options items="${listRole}" itemValue="value" itemLabel="content" />
                            </form:select></td>
                        <td>完整度：</td>
                        <td><form:select path="sysUserSearchVO.isCompletion" class="form-control input-medium"
                                         id="cmbCompletion">
                            <form:option value="" label="--全部--" />
                            <form:option value="1">完整</form:option>
                            <form:option value="2">不完整</form:option>
                        </form:select></td>

                        <td>部门:</td>
                        <td>
                            <div class="input-group " style=" width: 260px">
                                <input id="departmentId" type="hidden" name="departmentId"
                                       value="${sysUserSearchVO.departmentId}">
                                <div id="nodeDiv" type="text" name="departmentName" readonly="readonly"
                                     class="form-control" value="${sysUserSearchVO.departmentName}">
                                    <span id="departmentName">${sysUserSearchVO.departmentName}</span>
                                </div>
                                <span class="input-group-btn" />
                                <button class="btn btn-default" onclick="javascript:showSelTree()"
                                        type="button"><i
                                        class="fa fa-search" /></i>选择
                                </button>
                                </span>
                            </div>
                        </td>
                    </tr>

                </table>
                <hr class="divider-vertical" />
                <div>
                    <button class="btn btn-primary" id="btnSearch">
                        <i class="ace-icon fa fa-search"></i> 查询
                    </button>
                    <c:if test="${critc:isP('SysUserAdd')}">
                        <button type="button" class="btn btn-success" id="btnAdd">
                            <i class="ace-icon fa fa-plus bigger-100"></i>新增
                        </button>
                    </c:if>

                    <button class="btn btn-file" id="importUser">
                        <i class="fa fa-cloud-upload"></i> 导入
                    </button>
                    <button class="btn btn-file" id="outportUser">
                        <i class="fa fa-cloud-download"></i> 导出
                    </button>
                </div>
            </div>

        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table id="simple-table" class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th width=40>#</th>
                        <th width=120>账号</th>
                        <th width="100">姓名</th>
                        <th width="60">性别</th>
                        <th width="120">角色</th>
                        <th width="120">部门</th>
                        <th width="140">手机号</th>
                        <th width="80">状态</th>
                        <th width="80">详情</th>
                        <th width="160">操作</th>
                    </tr>
                </thead>
                <tbody
                <c:forEach items="${list }" var="sysUser" varStatus="st">
                    <tr>
                        <td>${st.index+1 }</td>
                        <td>${sysUser.username } </td>
                        <td>${sysUser.realName }</td>
                        <td>
                            <c:if test="${sysUser.gender eq '女'}">
                                <img alt="" class="img" style="width: 20px;height: 20px"
                                     src="${imageServer}/sys/user/avatar/f.jpg">
                            </c:if>
                            <c:if test="${sysUser.gender eq '男'}">
                                <img alt="" class="img" style="width: 20px;height: 20px"
                                     src="${imageServer}/sys/user/avatar/m.jpg">
                            </c:if>
                        </td>
                        <td>${sysUser.roleName }</td>
                        <td>${sysUser.departmentName }</td>
                        <td>${sysUser.mobile }</td>
                        <td>${critc:getUserStatus(sysUser.status)}</td>
                        <td>
                            <a href="detail.htm?id=${sysUser.id }&backUrl=${backUrl}&pageType=1">查看 </a>
                        </td>
                        <td><c:if test="${critc:isP('SysUserUpdate')}">
                            <a href="toUpdate.htm?id=${sysUser.id }&backUrl=${backUrl}&pageType=0"> 修改 </a>
                        </c:if>
                            <c:if test="${critc:isP('SysUserDelete')&&sysUser.deletable==1}">
                                <a href="javascript:delUser(${sysUser.id });"> 删除 </a>
                            </c:if>
                            <c:if test="${critc:isP('SysUserLock')&&sysUser.status==1}">
                                <a href="javascript:lock(${sysUser.id });">锁定 </a>
                            </c:if>
                            <c:if test="${critc:isP('SysUserUnlock')&&sysUser.status==2}">
                                <a href="javascript:unlock(${sysUser.id });">解锁 </a>
                            </c:if>
                            <c:if test="${critc:isP('SysUserResetPass')}">
                                <a href="javascript:resetPass(${sysUser.id });">重置密码 </a>
                            </c:if></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">${ pageNavigate.pageModel}</div>
    </div>

    <div class="modal fade" id="departmentList" tabindex="-1" role="basic" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">选择部门</h4>
                </div>
                <div class="modal-body">
                    <ul id="tree" class="ztree" style="width: 560px; overflow: auto;"></ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="javascript:getSelected();">确认</button>
                    <button type="button" class="btn " data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

</body>
<critc-script>

    <script src="${staticServer }/assets/zTree3.5/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            if (${sysUserSearchVO.departmentName ne null}) {
                $("#clearNode").remove();
                $("#departmentName").append(
                    "<i id='clearNode'  class=' fa fa-remove  pull-right' style='margin-top:2px' onclick='clearDepartment()'>&nbsp;</i>");
            }

        });
        function getZtree(data) {
            var zTree = data;
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "pId",
                        rootPId: ""
                    }
                }
            };
            var zNodes = [${ztree}];
            jQuery(document).ready(function () {
                var t = $("#tree");
                t = $.fn.zTree.init(t, setting, zNodes);
                var zTree = $.fn.zTree.getZTreeObj("tree");
            });
        }

        function showSelTree() {

            $.ajax({
                type: 'POST',
                url: 'departmentTree.htm',
                dataType: 'json',
                success: function (result) {
                    if (result["success"]) {
                        ztree = result.ztree;
                        getZtree(ztree);
                        $('#departmentList').modal('show');
                    }
                }

            });


        }
        function getSelected() {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getSelectedNodes();
            if (nodes.length > 0) {
                $("#departmentId").val(nodes[0].id);
                $("#departmentName").text(nodes[0].name);
                $('#departmentList').modal('hide');
                $("#clearNode").remove();
                //设置叉号在上级节点有值才出现
                $("#departmentName").append(
                    "<i id='clearNode'  class=' fa fa-remove  pull-right' style='margin-top:2px' onclick='clearDepartment()'>&nbsp;</i>");

            }
            else return;
        }
        $(function () {
            $("#btnSearch").bind('click', searchUser);
            $("#btnAdd").bind('click', addUser);
            $("#importUser").bind('click', importUser);
            $("#outportUser").bind('click', outportUser);
        })

        // 查询方法
        var searchUser = function () {
            var url = "index.htm?";
            if ($("#txtUsername").val() != '')
                url += "username=" + $("#txtUsername").val();
            if ($("#txtRealName").val() != '')
                url += "&realName=" + $("#txtRealname").val();
            if ($("#cmbStatus").val() != '')
                url += "&status=" + $("#cmbStatus").val();
            if ($("#cmbRoleId").val() != '')
                url += "&roleId=" + $("#cmbRoleId").val();
            if ($("#departmentId").val() != '')
                url += "&departmentId=" + $("#departmentId").val();
            if ($("#departmentName").text() != '')
                url += "&departmentName=" + $("#departmentName").text();
            if ($("#cmbCompletion").val() != '')
                url += "&isCompletion=" + $("#cmbCompletion").val();
            window.location = encodeURI(url);
        }
        // 删除
        var delUser = function (id) {
            bootbox.confirm("你确定要删除该用户吗？", function (result) {
                if (result) {
                    window.location = "delete.htm?id=" + id + "&backUrl=${backUrl}";
                }
            })
        }
        // 锁定
        var lock = function (id) {
            bootbox.confirm("你确定要锁定该用户吗？", function (result) {
                if (result) {
                    window.location = "saveLock.htm?id=" + id + "&backUrl=${backUrl}";
                }
            })
        }
        // 解锁
        var unlock = function (id) {
            bootbox.confirm("你确定要解锁该用户吗？", function (result) {
                if (result) {
                    window.location = "saveUnlock.htm?id=" + id + "&backUrl=${backUrl}";
                }
            })
        }
        // 重置密码
        var resetPass = function (id) {
            bootbox.confirm("你确定要给该用户重置密码吗？", function (result) {
                if (result) {
                    window.location = "saveResetPass.htm?id=" + id + "&backUrl=${backUrl}";
                }
            })
        }

        //新增
        var addUser = function (id) {
            window.location = 'toAdd.htm?backUrl=${backUrl }';
        }
        //导入
        var importUser = function () {
            window.location = 'toImportUser.htm?backUrl=${backUrl }';
        }
        //导出
        var outportUser = function () {
            var url = "exportUserExcel.htm?";
            if ($("#txtUsername").val() != '')
                url += "username=" + $("#txtUsername").val();
            if ($("#txtRealName").val() != '')
                url += "&realName=" + $("#txtRealname").val();
            if ($("#cmbStatus").val() != '')
                url += "&status=" + $("#cmbStatus").val();
            if ($("#cmbRoleId").val() != '')
                url += "&role_id=" + $("#cmbRoleId").val();
            if ($("#departmentId").val() != '')
                url += "&departmentId=" + $("#departmentId").val();
            if ($("#departmentName").text() != '')
                url += "&departmentName=" + $("#departmentName").text();
            window.location = encodeURI(url);
        }
        //清空选择部门
        var clearDepartment = function () {
            $("#departmentName").text("");
            $("#departmentId").val("");
            $("#clearNode").remove();
        };

    </script>
</critc-script>