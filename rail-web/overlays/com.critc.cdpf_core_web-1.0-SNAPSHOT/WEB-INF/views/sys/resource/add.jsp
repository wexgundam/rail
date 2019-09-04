<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../../common/taglib.jsp"%>


<head>
	<title>资源管理</title>
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
		<li>>系统模块</li>
	</ul>
</div>
<h1 class="page-title">
	资源管理 <small>>>新增模块</small>
</h1>

<div class="row">
	<div class="col-md-8">
		<form role="form" id="resouceForm" name="resouceForm"
			  class="form-horizontal" action="add.htm" method="post">
			<input type="hidden" name="backUrl" value="${backUrl }">
			<div class="form-body">
				<div class="form-group">
					<label class="col-md-3 control-label">模块名称：</label>
					<div class="col-md-9">
						<input id="name" name="name" type="text"
							   class="form-control input-inline  input-xlarge" placeholder="输入长度限制为20个字符"
							   value="" maxlength="20"> <label id="nameTip"></label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">模块等级：</label>
					<div class="col-md-9">
						<select id="resourceLevel" name="resourceLevel"
								class="form-control input-inline input-xlarge">
							<option id="ptsel" value="1" >平台管理</option>
							<option id="ptsel2" value="0" selected="selected">应用系统</option>
						</select>

					</div>
				</div>

				<input id="type" name="type" type="hidden"
					   class="form-control input-inline  input-xlarge" value="1" readonly />

				<div class="form-group">
					<label class="col-md-3 control-label">上级节点：</label>
					<div class="col-md-9">
						<div class="input-group input-xlarge ">
							<input id="parentId" type="hidden" name="parentId"
								   value="${sysResource.parentId}"/>

							<div  id="nodeDiv"	 type="text" name="parentName" readonly="readonly"	class="form-control  " >

								<span id="parentName"  >${sysResource.parentName}</span>

							</div>
							<span class="input-group-btn">
									<button type="button" class="btn btn-primary"
											onclick="javascript:showSelTree()">
										<i class="fa fa-search" /></i> 选择
									</button>
									<label id="parentIdTip"></label>
								</span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">模块代码：</label>
					<div class="col-md-9">
						<input id="code" type="text" name="code"
							   class="form-control input-inline  input-xlarge" placeholder="请输入非中文字符，长度最大40个字符"
							   value="" maxlength="40"><label id="codeTip"></label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label">链接地址：</label>
					<div class="col-md-9">
						<input id="url" type="text" name="url"
							   class="form-control input-inline  input-xlarge" placeholder="请输入非中文字符，长度最大200个字符"
							   value="" maxlength="200"><label id="urlTip"></label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label">链接目标：</label>
					<div class="col-md-9">
						${critc:createCombo2('{_self:_self,_blank:_blank}',sysResource.target,0,'target','target','form-control input-inline input-xlarge' )}
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label">图标：</label> <input
						id="iconImg" type="hidden" name="iconImg" class="form-control"
						placeholder="" value="${sysResource.iconImg }">
					<div class="col-md-9">
						<div class="input-group input-xlarge" id="icondiv">
							<label class="form-control-static"><i
									class="fa ${sysResource.iconImg }"> ${sysResource.iconImg }</i></label>
							<span class="input-group-btn">
									<button type="button" class="btn btn-primary"
											onclick="javascript:showIcon()">
										<i class="fa fa-search" /></i> 选择
									</button>
								</span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">排序：</label>
					<div class="col-md-9">
						<input id="displayOrder" type="text" name="displayOrder"
							   class="form-control input-inline  input-xlarge" placeholder="只能输入数字，最大长度为6位"
							   value="" maxlength= "6" ><label id="displayOrderTip"></label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">描述：</label>
					<div class="col-md-9">
							<textarea id="description" name="description"
									  class="form-control input-inline  input-xlarge " rows="3 " placeholder="只能输入50个字符"  maxlength= "50"></textarea>
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
	 aria-hidden="true" >
	<div class="modal-dialog" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
				<h4 class="modal-title">选择上级节点</h4>
			</div>
			<div class="modal-body" style="max-height:400px; overflow: auto;">
				<ul id="tree" class="ztree" ></ul>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
						onclick="javascript:getSelected();">确认</button>
				<button type="button" class="btn " data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>
<%@ include file="icon.jsp"%>


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



    jQuery(document).ready(function () {

        initTree(0);

    });


    function getSelected() {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length > 0) {
            $("#parentId").val(nodes[0].id);
            $("#parentName").text(nodes[0].name);
            $("#clearNode").remove();
            //设置叉号在上级节点有值才出现
            $("#nodeDiv").append("<i id='clearNode'  class=' fa fa-remove  pull-right' style='margin-top:2px'>&nbsp;</i>");
			/*  var pId = nodes[0].pId;
			 var id =nodes[0].id;
			 //设置上级节点和模块等级联动
			 if(pId=="2"||id=="2"){
			 $("#ptsel").prop("selected",true);
			 $("#ptsel2").prop("selected",false);

			 }else if(pId=="529"||id=="529"){
			 $("#ptsel1").prop("selected",false);
			 $("#ptsel2").prop("selected",true);

			 }else{
			 if(nodes[0].getParentNode()==null){

			 }else{
			 var pd = nodes[0].getParentNode().pId;
			 if(pd=="2"){
			 $("#ptsel").prop("selected",true);
			 $("#ptsel2").prop("selected",false);
			 }else if(pd=="529"){
			 $("#ptsel1").prop("selected",false);
			 $("#ptsel2").prop("selected",true);
			 }
			 }

			 }*/
            $('#basic').modal('hide');
        }
        else return;

    }

    function showSelTree() {
        $('#basic').modal('show');
    }
    function showIcon() {
        $('#iconModel').modal('show');
    }
    $(document).ready(function () {
        //如果为新增功能，则不显示图标和target
        var type = '${sysResource.type}';
        if (type == 2) {
            $("#divIconImg").hide();
            $("#divTarget").hide();
        }

        $("#resouceForm").validate({
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
                    required: true,
                    maxlength: 40,
                    checkCode: true
                },
                url: {
                    required: true,
                    maxlength: 200,
                    checkCode: true
                },
                displayOrder: {
                    required: true,
                    number: true,
                    maxlength: 6
                },
                description: {
                    maxlength: 50
                }
            },
            messages: {},
            submitHandler: function (form) {
                form.submit();
            }
        });
    });

    jQuery.validator.addMethod("checkCode", function(value, element) {
        return this.optional(element) || /^[^\u4e00-\u9fa5]*$/.test(value);
    }, "不能包含中文");
    var icon;
    $("#iconModel").on("show.bs.modal",function(){
        $("#iconModel a").css("color","black");
    });
    $("#iconModel").on("shown.bs.modal",function(){
        $("#iconModel .tab-pane div div").mouseover(function(){
            $(this).css("background","#D4D4D4");
        });
        $("#iconModel .tab-pane div div").mouseout(function(){
            $(this).css("background","");
        });
        $("#iconModel #web div").mouseover(function(){
            $(this).css("background","#D4D4D4");
        });
        $("#iconModel #web div").mouseout(function(){
            $(this).css("background","");
        });
        $("#iconModel .tab-content a").click(function(){
            icon=$(this).context.firstChild.classList[2];
            $('#iconModel').modal('hide');
            $('#iconImg').val(icon);
        });
    });
    $("#iconModel").on("hidden.bs.modal",function(){
        if(icon==undefined)icon=[${sysResource.iconImg }];
        $("#icondiv").empty();
        $("#icondiv").append("<label class='form-control-static'><i class='fa "+icon+"'>"+"  "+icon+"</i></label><span class='input-group-btn'><button type='button' class='btn btn-primary' onclick='javascript:showIcon()''><i class='fa fa-search'/></i>选择</button></span>");
    });

    $("#nodeDiv").on("click","#clearNode",function(){
        $("#parentName").text("");
        $("#parentId").val("");
        $(this).remove();
    });

    //获取资源等级
    function initTree(resourceLevel){

        $.ajax({
            type:"get",
            url:"${dynamicServer}/sys/resource/initTree.htm",
            data:{"resourceLevel":resourceLevel},
            dataType:"json",
            contentType: 'application/json',
            success:function(data ){

                var t = $("#tree");

                t = $.fn.zTree.init(t, setting, data);
                var zTree = $.fn.zTree.getZTreeObj("tree");
            },
            error:function(data){
                console.log(data);
            }

        });

    }
    //监听模块等级下拉框
    $("#resourceLevel").change(function(){

        //下拉框值
        var selVal = $(this).val();
        var parentId = $("#parentId").val();
        if(!parentId == selVal){
            $("#clearNode").remove();
            $("#parentName").text("");
        }
        initTree(selVal);

    });
</script> </critc-script>