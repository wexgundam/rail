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
    <small>>>修改字典</small>
</h1>

<div class="row">
    <div class="col-md-8">
        <form role="form" id="categoryForm" name="categoryForm" class="form-horizontal"
              action="update.htm" method="post">
            <input type="hidden" name="backUrl" value="${backUrl }">
            <input type="hidden" name="id" value="${sysDic.id }" />
            <input type="hidden" name="categoryName" value="${sysDic.categoryName}" />
            <div class="form-body">
                 <div class="form-group">
						<label class="col-md-3 control-label">类别：</label>
						<div class="col-md-9 ">
							<form:select path="sysDic.category" name="category"
								class="form-control input-inline  input-xlarge" id="category">
								<option value="">请选择类别</option>
								<form:options items="${listCategory}" itemValue="value"
									itemLabel="content" />
							</form:select>
							<label id="categoryTip"></label>
						</div>
				</div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">字典代码：</label>
                    <div class="col-md-9">
                        <input id="code" type="text"  name="code" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value="${sysDic.code }"><label id="codeTip"></label>
                    </div>
                </div>
                
               
                
                <div class="form-group">
                    <label class="col-md-3 control-label">字典名称：</label>
                    <div class="col-md-9">
                        <input id="name" type="text" name="name" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value="${sysDic.name}"><label id="nameTip"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">排序：</label>
                    <div class="col-md-9">
                        <input id="displayOrder" type="text" name="displayOrder"
                               class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value="${sysDic.displayOrder}"><label id="displayOrderTip"></label>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="submit" class="btn default"><i class="fa fa-save"/></i> 保存</button>
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
    
       
	    
        
        $(document).ready(function () {
        	$("#category").change(function(){
        		console.log($(this));
        	})
        	
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
                    },
                    
                    code: {
                        required: true,
                        maxlength: 40
                    },
                    
                    name: {
                        required: true,
                        maxlength: 40
                    }, 
                	
                    displayOrder: {
                        required: true,
                        number: true,
                        maxlength: 6
                    },
                    
                    
                },
                messages: {
                	code: {
                        remote: "字典代码已存在！"
                    },
                    name:{
                	  remote:  "字典名称已存在！"
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
        });

    </script>
</critc-script>