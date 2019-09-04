<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>

<head>
    <title>用户管理</title>
</head>
<critc-css>
    <link rel="stylesheet" href="${staticServer}/assets/cropper3.0/cropper.min.css"/>
    <link rel="stylesheet" href="${staticServer}/assets/cropper3.0/main.css"/>
    <link href="${staticServer }/assets/zTree3.5/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>

</critc-css>
<body class="no-skin">
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li><i class="fa fa-home"></i> <a
                href="${dynamicServer}/index.htm">首页</a></li>
        <li>>系统管理</li>
        <li>>用户管理</li>
    </ul>
</div>
<h1 class="page-title">
    用户管理
    <small>>>新增用户</small>
</h1>


<div class="row  col-md-10 col-md-offset-1">
    <form id="userForm" name="userForm" class="form-horizontal "
          action="add.htm" method="post">

        <div class="tabbable-custom  nav-justified">
            <ul class="nav nav-tabs ">
                <li class="active" id="pt">
                    <a href="#tab_1_1" data-toggle="tab"> 基本信息 </a>
                </li>
                <li class="" id="yx">
                    <a href="#tab_1_2" data-toggle="tab"> 更多信息 </a>
                </li>
                <li class="pull-right m-grid-col m-grid-col-bottom">
                    <a id="completion" > </a>
                </li>
            </ul>
            <input type="hidden" name="tab" value="1" id="tab2">
            <div class="tab-content ">

                <div class="tab-pane fade active in " id="tab_1_1">
                    <div class="row ">
                        <div class="col-md-7">
                            <input type="hidden" name="backUrl" value="${backUrl }">
                            <div class="form-group">
                                <label class="col-md-3 control-label">账号：</label>
                                <div class="col-md-9">
                                    <input id="username" name="username" type="text"
                                           class="form-control input-inline input-medium"
                                           placeholder="" value="${sysUser.username}"> <label
                                        id="usernameTip"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">真实姓名：</label>
                                <div class="col-md-9">
                                    <input id="realName" type="text" name="realName"
                                           class="form-control input-inline input-medium "
                                           placeholder=""><label
                                        id="realNameTip"></label>
                                </div>
                            </div>
                            <div class="form-group" style="margin-bottom:-20px;">
                                <label class="col-md-3 control-label">性别：</label>
                                <div class="col-md-9 ">
                                    <div class="mt-radio-inline">
                                        <label class="mt-radio">
                                            <input type="radio" class="input-inline" name="gender"
                                                   id="male" value="男" checked="checked"/>男
                                            <span></span>
                                        </label>
                                        <label class="mt-radio">
                                            <input type="radio" class="input-inline" name="gender"
                                                   id="female"
                                                   value="女"/>女
                                            <span></span>
                                        </label>
                                    </div>

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">手机：</label>
                                <div class="col-md-9">
                                    <input id="mobile" type="text" name="mobile"
                                           class="form-control input-inline input-medium"
                                           placeholder="" value=""/><label
                                        id="mobileTip"></label>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-md-3 control-label">角色：</label>
                                <div class="col-md-9">
                                    <form:select path="sysUser.roleId" name="roleId"
                                                 class="form-control input-inline input-medium"
                                                 id="roleId">
                                        <option value="">请选择角色</option>
                                        <form:options items="${listRole }" itemValue="value"
                                                      itemLabel="content"/>
                                    </form:select>
                                    <label id="roleIdTip"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">部门：</label>
                                <div class="col-md-9">
                                    <div class="input-group input-medium">
                                        <input type="hidden" id="departmentId" name="departmentId"
                                               class="form-control" value="${sysUser.departmentId}"/>
                                        <input id="departmentName" type="text" name="departmentName"
                                               readonly="readonly"
                                               class="form-control  "
                                               placeholder="" value="${sysUser.departmentName}"/>
                                        <span class="input-group-btn">
                                            <button class="btn btn-primary" id="choice"
                                                    onclick="javascript:showSelTree()"
                                                    type="button"><i class="fa fa-search"/></i>选择
                                        </button>
                                       </span>
                                    </div>
                                    <label id="departmentNameTip"></label>
                                </div>
                            </div>
                        </div>
                        <div class="profile-sidebar col-md-3">
                            <!-- PORTLET MAIN -->
                            <div class="portlet light profile-sidebar-portlet  bg-inverse ">
                                <!-- SIDEBAR USERPIC -->
                                <br/>
                                <div class="" style="width:70%;height:60%;margin:0 auto">
                                    <img id="imgAvatar" src="${imageServer}/sys/user/avatar/male.jpg"
                                         class="img-responsive" alt="">
                                </div>
                                <br/>
                                <!-- END SIDEBAR USERPIC -->

                            </div>
                        </div>
                    </div>

                </div>

                <div class="tab-pane fade active in" id="tab_1_2">
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">邮箱：</label>
                            <div class="col-md-8">
                                <input id="email" type="text" name="email"
                                       class="form-control input-inline " style="width:90%"
                                       placeholder=""><label
                                    id="emailTip"></label>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">职务：</label>
                            <div class="col-md-8 ">
                                <form:select path="sysUser.jobTitle" name="jobTitle"
                                             class="form-control input-inline" style="width:90%"
                                             id="jobTitle">
                                    <option value="">请选择职务</option>
                                    <form:options items="${listJobTitle }" itemValue="name"
                                                  itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">职称：</label>
                            <div class="col-md-8">
                                <form:select path="sysUser.post" name="post"
                                             class="form-control input-inline" style="width:90%"
                                             id="post">
                                    <option value="">请选择职称</option>
                                    <form:options items="${listPost }" itemValue="name"
                                                  itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">身份证号：</label>
                            <div class="col-md-8">
                                <input id="idcard" type="text" name="idcard"
                                       class="form-control input-inline " style="width:90%"
                                       placeholder=""><label
                                    id="idcardTip"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">政治面貌：</label>
                            <div class="col-md-8">
                                <form:select path="sysUser.political" name="political"
                                             class="form-control input-inline " style="width:90%"
                                             id="political">
                                    <option value="">请选择政治面貌</option>
                                    <form:options items="${listPolitical}" itemValue="name"
                                                  itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">民族：</label>
                            <div class="col-md-8">
                                <form:select path="sysUser.nation" name="nation"
                                             class="form-control input-inline " style="width:90%"
                                             id="nation">
                                    <option value="">请选择民族</option>
                                    <form:options items="${listNation }" itemValue="name"
                                                  itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">教育程度：</label>
                            <div class="col-md-8">
                                <form:select path="sysUser.education" name="education "
                                             class="form-control input-inline " style="width:90%"
                                             id="education ">
                                    <option value="">请选择教育程度</option>
                                    <form:options items="${listEducation}" itemValue="name"
                                                  itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">毕业院校：</label>
                            <div class="col-md-8">
                                <input id="graduatedSchool" type="text" name="graduatedSchool"
                                       class="form-control input-inline " style="width:90%"
                                       placeholder=""/><label
                                    id="graduatedSchoolTip"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">座机：</label>
                            <div class="col-md-8">
                                <input id="telephone" type="text" name="telephone"
                                       class="form-control input-inline " style="width:90%"
                                       placeholder=""><label
                                    id="telephoneTip"></label>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">专业：</label>
                            <div class="col-md-8">
                                <input id="major" type="text" name="major"
                                       class="form-control input-inline" style="width:90%"
                                       placeholder=""><label
                                    id="majorTip"></label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label class="col-md-4 control-label">排序：</label>
                            <div class="col-md-8">
                                <input id="displayOrder" type="text" name="displayOrder"
                                       class="form-control input-inline " style="width:90%"
                                       placeholder=""><label
                                    id="displayOrderTip"></label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <div class="row">
                <div class="col-md-offset-4 col-md-6">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-save"/></i> 保存
                    </button>
                    <button type="button" class="btn default" onclick="history.back(-1)"><i
                            class="fa fa-undo"/></i>  取消
                    </button>
                </div>
            </div>
        </div>


    </form>
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

    <script src="${staticServer}/assets/cropper3.0/cropper.min.js"></script>
    <script src="${staticServer}/assets/cropper3.0/main.js"></script>
    <script src="${staticServer }/assets/zTree3.5/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            init();
            $("#btnEditAvatar").bind('click', showAvatar);
            $("input[name='gender']").bind('change', function () {
                    if ($("input[name='gender']:checked").val() == "男"){
                        document.getElementById('imgAvatar').src = "${imageServer}/sys/user/avatar/male.jpg"
                        $('#avatar').val('/sys/user/avatar/male.jpg');
                    }

                    else{
                        document.getElementById('imgAvatar').src = "${imageServer}/sys/user/avatar/female.jpg"
                        $('#avatar').val('/sys/user/avatar/female.jpg');
                }}
            );
            $("input[type='text'],select").bind('change', completionCalculate);
        });
        function init() {
            $("#tab_1_1").addClass("active in");
            $("#tab_1_2").removeClass("active in");
            $("#pt").addClass("active");
            $("#yx").removeClass("active");

        }
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
                $("#departmentName").val(nodes[0].name);
                $('#departmentList').modal('hide');
            }
            else return;

        }
        function showAvatar() {
            $('#avatar-modal').modal('show');
        }
        //固定电话验证
        jQuery.validator.addMethod("isTelphone", function (value, element) {
            var regex = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;
            return this.optional(element) || regex.test(value);
        }, "请正确格式的固定电话");
        //用户名验证
        jQuery.validator.addMethod("isUserName", function (value, element) {
            var regex = /^[a-zA-Z][a-zA-Z0-9_]*$/;
            return this.optional(element) || regex.test(value);
        }, "请输入正确格式的账号：以字母开通，只包含字母、数字、下划线");
        //身份证号验证
        jQuery.validator.addMethod("isIdCardNo", function (value, element) {
            var regex = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
            return this.optional(element) || regex.test(value);
        }, "请正确输入您的身份证号码");
        $(document).ready(function () {
            $("#userForm").validate({
                errorElement: "label",
                errorClass: "valiError",
                errorPlacement: function (error, element) {
                    error.appendTo($("#" + element.attr('id') + "Tip"));
                },
                rules: {
                    username: {
                        required: true,
                        minlength: 4,
                        maxlength: 20,
                        isUserName: true,
                        remote: {
                            url: "checkUserExist.htm", //后台处理程序
                            type: "post", //数据发送方式
                            //dataType: "json", //接受数据格式
                            data: { //要传递的数据
                                username: function () {
                                    return $("#username").val();
                                }
                            }
                        }
                    },
                    realName: {
                        required: true,
                        maxlength: 20
                    },
                    mobile: {
                        mobile: true,
                        required: true,
                        maxlength: 11,
                        remote: {
                            url: "checkMobileExist.htm",
                            type: "post",
                            data: { //要传递的数据
                                mobile: function () {
                                    return $("#mobile").val();
                                }
                            }
                        }
                    },
                    mobile1: {
                        required: true
                    },
                    email: {
                        maxlength: 30,
                        email: true
                    },
                    roleId: {
                        required: true
                    },
                    departmentName: {
                        required: true
                    },
                    idcard: {
                        isIdCardNo: true
                    },
                    displayOrder: {
                        number: true
                    },
                    graduatedSchool: {
                        maxlength: 10
                    },
                    telephone: {
                        maxlength: 15,
                        isTelphone: true
                    },
                    major: {
                        maxlength: 10
                    }
                },
                messages: {
                    username: {
                        remote: "账号已存在！"
                    },
                    mobile: {
                        remote: "此手机号已注册过账号！"
                    },
                    email: {
                        email: "请输入正确的电子邮箱地址"
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });

        });

        //计算完整度
        function completionCalculate(){
            var form = document.getElementById("userForm");
            var data = new FormData(form);
            $.ajax({
                type: 'POST',
                url: 'completionCalculate.htm',
                data: data,
                dataType: 'json',
                processData: false,
                contentType: false,
                success: function (result) {
                    if (result["success"]) {
                        showCompletion(result["completion"]);
                    }
                }

            });
        }
        //显示不用颜色完整度
        function showCompletion(comple){
            if(comple!=null&&comple>=80){
                $("#completion").html('完整度:'+comple+' %' ).css('color','green');
            }else if(comple!=null&&comple>=50&&comple<80){
                $("#completion").html('完整度:'+comple+' %' ).css('color','gold');
            }else{
                $("#completion").html('完整度:'+comple+' %' ).css('color','red');
            }
        }


    </script>
</critc-script>