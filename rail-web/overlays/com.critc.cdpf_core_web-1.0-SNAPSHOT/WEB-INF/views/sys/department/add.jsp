<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../../common/taglib.jsp"%>

<head>
<title>组织机构</title>
<critc-css>
<link href="${staticServer }/assets/zTree3.5/css/zTreeStyle/metro.css"
	rel="stylesheet" type="text/css" />
</critc-css>
</head>

<body>
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a
				href="${dynamicServer}/index.htm">首页</a></li>
			<li>>系统管理</li>
			<li>>组织机构</li>
		</ul>
	</div>
	<h1 class="page-title">
		组织机构 <small>>>新增部门</small>
	</h1>

	<div class="row">
		<div class="col-md-10">
			<form role="form" id="departmentForm" name="departmentForm"
				class="form-horizontal" action="add.htm" method="post">
				<input type="hidden" name="backUrl" value="${backUrl }">
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">名称：</label>
						<div class="col-md-9">
							<input id="name" name="name" type="text"
								class="form-control input-inline  input-xlarge" placeholder=""
								value="" maxlength="20"> <label id="nameTip"></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">上级部门：</label>
						<div class="col-md-9">
							<div class="input-group input-xlarge">
								 <input id="parentId" type="hidden" name="parentId"> 
								<div id="nodeDiv" type="text" name="parentName" readonly="readonly"
									class="form-control  ">
									<span id="parentName"></span> 
								</div>
								<span class="input-group-btn">
									<button type="button" class="btn btn-primary"
										onclick="javascript:showSelTree()">
										<i class="fa fa-search" /></i> 选择
									</button> <label id="parentIdTip"></label>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">部门类型：</label>
						<div class="col-md-9">
							<form:select path="sysDepartment.type"
								class="form-control input-inline  input-xlarge" name="type"
								id="type">
								<option value="">请选择部门类型</option>
								<form:options items="${listType}" itemValue="code"
									itemLabel="name" />
							</form:select>
							<label id="typeTip"></label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 control-label">部门编码：</label>
						<div class="col-md-9">
							<input id="code" type="text" name="code"
								class="form-control input-inline  input-xlarge" placeholder=""
								value="" maxlength="20"><label id="codeTip"></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">排序：</label>
						<div class="col-md-9">
							<input id="displayOrder" type="text" name="displayOrder"
								class="form-control input-inline  input-xlarge" placeholder=""
								value="" maxlength="6"><label id="displayOrderTip"></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">备注：</label>
						<div class="col-md-9">
							<textarea rows="5" cols="60" id="note" name="note"
								class="form-control input-inline  input-xlarge" maxlength="100"></textarea>
							<label id="noteTip"></label>
						</div>
					</div>
				</div>
				<div class="form-actions">
					<div class="row">
						<div class="col-md-offset-3 col-md-9">
							<button type="submit" class="btn btn-primary">
								<i class="fa fa-save" /></i> 保存
							</button>
							<button type="button" class="btn default"
								onclick="history.back(-1)">
								<i class="fa fa-undo" /></i> 取消
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>


	<div class="modal fade" id="basic" tabindex="-1" role="basic"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">选择上级部门</h4>
				</div>
				<div class="modal-body">
					<ul id="tree" class="ztree" style="width: 560px; overflow: auto;"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="javascript:getSelected();">确认</button>
					<button type="button" class="btn " data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

</body>
<critc-script> <script
	src="${staticServer }/assets/zTree3.5/js/jquery.ztree.all-3.5.min.js"
	type="text/javascript"></script> <script type="text/javascript">

        var zTree;
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

        function getSelected() {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getSelectedNodes();
            if (nodes.length > 0) {
                $("#parentId").val(nodes[0].id);
                $("#parentName").text(nodes[0].name);
                $("#clearNode").remove();
                //设置叉号在上级节点有值才出现
                $("#nodeDiv").append("<i id='clearNode'  class=' fa fa-remove  pull-right' style='margin-top:2px' onclick='clearDepartment()'>&nbsp;</i>");
                $('#basic').modal('hide');
            }
            else return;

        }

        function showSelTree() {
            $('#basic').modal('show');
        }
        $(document).ready(function () {
            $("#departmentForm").validate({
                debug: true,
                errorElement: "label",
                errorClass: "valiError",
                errorPlacement: function (error, element) {
                    error.appendTo($("#" + element.attr('id') + "Tip"));
                },
                rules: {
                    name: {
                        required: true,
                        maxlength: 20
                    },
                    code: {
                    	maxlength: 20,
                    	checkCode: true,
                    	 required: true
                    },              
                    displayOrder: {
                        required: true,
                        number: true,
                        maxlength: 6
                    },
                    type: {
                    	required: true
                    },
                    note:{
                    	maxlength: 100
                    } 
                },
                messages: {},
                submitHandler: function (form) {
                    form.submit();
                }
            });
        });
        //部门编码校验 不为空
        jQuery.validator.addMethod("checkCode", function(value, element) {
			return this.optional(element) || /^[a-zA-Z][a-zA-Z0-9]*$/.test(value);
		}, "只能是英文和数值，且以英文开头");   
        //清空选择的部门
         var clearDepartment =function(){
        	$("#parentName").text("");
        	$("#parentId").val("");
        	$("#clearNode").remove();
        };
    </script> </critc-script>