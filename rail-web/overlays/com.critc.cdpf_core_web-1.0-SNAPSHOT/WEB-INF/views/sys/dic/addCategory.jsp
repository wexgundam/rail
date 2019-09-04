<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp" %>


<head>
    <title>字典管理</title>
    <critc-css>
        <link href="${staticServer }/assets/zTree3.5/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
    </critc-css>
</head>

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
            >字典管理
        </li>
    </ul>
</div>
<h1 class="page-title"> 字典管理
    <small>>>新增类别</small>
</h1>

<div class="row">
    <div class="col-md-12">
        <form role="form" id="categoryForm" name="categoryForm" class="form-horizontal"
              action="add.htm" method="post">
            <input type="hidden" name="backUrl" value="${backUrl }">
            <div class="form-body">
                <div class="form-group">
                    <label class="col-md-3 control-label">分类：</label>
                    <div class="col-md-9">
                        <input id="category" name="category" type="text" maxlength="40" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""> <label id="categoryTip"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">分类名称：</label>
                    <div class="col-md-9">
                        <input id="categoryName" type="text" name="categoryName" maxlength="20" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""><label id="categoryNameTip"></label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">排序：</label>
                    <div class="col-md-9">
                        <input id="displayOrder" maxlength="6" type="text" name="displayOrder"
                               class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""><label id="displayOrderTip"></label>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="submit" class="btn blue"><i class="fa fa-save"/></i> 保存</button>
                        <button type="button" id="reset" class="btn default"><i class="fa fa-retweet"/></i>重置</button>
                        <button type="button" class="btn default" onclick="history.back(-1)"><i class="fa fa-undo"/></i>
                                                              取消
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
<critc-script>
    <script type="text/javascript">
    
        /**
                             重置按钮        
        */
	    var reset = function(){
        	$("#category").val("");
        	$("#categoryName").val("");
        	$("#displayOrder").val("");
		}
        
        $(document).ready(function () {
        	$("#reset").bind('click', reset);
        	
            $("#categoryForm").validate({
                debug: true,
                errorElement: "label",
                errorClass: "valiError",
                errorPlacement: function (error, element) {
                    error.appendTo($("#" + element.attr('id') + "Tip"));
                },
                rules: {
                	category: {
                        required: true,
                        maxlength: 40,
                        charNums:[],
                        remote: {
                            url: "checkDicExist.htm", //后台处理程序
                            type: "post", //数据发送方式
                            data: { //要传递的数据
                            	    param:"category",
                                    value: function () {
                                       return $("#category").val();
                                    },
                                
                            },
                            dataFilter: function (json,type) {
                            	var data = JSON.parse(json);
                            	if(data.result == 0){  //与修改前相同
                            		return true;
                            	}else if(data.result == 1){  //数据库中不存在
                            		return true;
                            	}else{                  //数据库中存在
                            		return false;
                            	}
                            },
                            
                        }
                    },
                    
                    categoryName: {
                        required: true,
                        maxlength: 20,
                        remote: {
                            url: "checkDicExist.htm", //后台处理程序
                            type: "post", //数据发送方式
                            data: {
                            	param:"categoryName",
                            	value: function () {
                                    return $("#categoryName").val();
                                }
                            },
                            dataFilter: function (json,type) {
                            	var data = JSON.parse(json);
                            	if(data.result == 0){  //与修改前相同
                            		return true;
                            	}else if(data.result == 1){  //数据库中不存在
                            		return true;
                            	}else{                  //数据库中存在
                            		return false;
                            	}
                            },
                        }
                    },
                    
                	
                	
                    displayOrder: {
                        required: true,
                        number: true,
                        maxlength: 6
                    },
                    
                    
                },
                messages: {
                	category: {
                        remote: "该类别已存在！"
                    },
                    categoryName:{
                	  remote:"该类别名称已存在！"
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
        });
        
       
        
         
        //字符和数字的组合，以字符开头
        jQuery.validator.addMethod('charNums', function(value, element){
       	return this.optional(element) ||/^[a-zA-Z]+[a-zA-Z0-9_]*$/.test(value);
        }, $.validator.format('只能包括英文字母,数字和下划线,且以英文开头!'));
        
        

    </script>
</critc-script>