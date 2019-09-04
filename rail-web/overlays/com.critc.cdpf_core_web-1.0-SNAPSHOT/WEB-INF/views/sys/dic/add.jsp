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
    <div class="col-md-8">
        <%-- <form role="form" id="categoryForm" name="categoryForm" class="form-horizontal"
              action="add.htm" method="post">
            <input type="hidden" name="backUrl" value="${backUrl }">
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
                               value=""><label id="codeTip"></label>
                    </div> 
                    
                    
                </div>
                
               
                
                <div class="form-group">
                    <label class="col-md-3 control-label">字典名称：</label>
                    <div class="col-md-9">
                        <input id="name" type="text" name="name" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""><label id="nameTip"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">排序：</label>
                    <div class="col-md-9">
                        <input id="displayOrder" type="text" name="displayOrder"
                               class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""><label id="displayOrderTip"></label>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="submit" class="btn default"><i class="fa fa-save"/></i> 保存</button>
                        <button type="button" id="reset" class="btn default"><i class="fa fa-retweet"/></i>重置</button>
                        <button type="button" class="btn default" onclick="history.back(-1)"><i class="fa fa-undo"/></i>
                                                              取消
                        </button>
                    </div>
                </div>
            </div>
        </form> --%>
        
        <form role="form" id="categoryForm" name="categoryForm" class="form-horizontal"
              action="add.htm" method="post">
            <input type="hidden" name="backUrl" value="${backUrl }">
            <div class="row" id="row1">
                <label style="position: relative; left:-350px; top:5px;">类别：</label>
                <div class="col-md-3" style="position: relative; left:50px;">
                    <select class="form-control category" name="category" id="category">
        	            <option value="">请选择类别</option>"
					    <c:forEach items="${listCategory}" var="sysDic">"
					    	<option value="${sysDic.value}">${sysDic.content}</option>"
					    </c:forEach>
					</select>
					<label id="categoryTip"></label> 
                </div>
               
               <label style="position: relative; left:-180px; top:5px;">字典代码：</label>
               <div class="col-md-3" style="position: relative; left:120px;">
                    <input type='text' name="code" class='form-control' id="code" placeholder=''>
                    <input id="categoryName" type="hidden" name="categoryName" />
                    <label id="codeTip"></label>
               </div>
               
               <label style="position: relative; left:0px; top:5px;">字典名称：</label>
               <div class="col-md-3" style="position: relative; left:180px;top:-25px;">
                   <input type='text'  name="name" id="name" class='form-control' placeholder=''> 
                   <label id="nameTip"></label>
               </div>
               
               <label style="position: relative; left:180px; top:5px;">排序：</label>
               <div class="col-md-3" style="position: relative; left:220px;top:-25px;">
                    <input type='text' name="displayOrder" id="displayOrder" class='form-control' placeholder=''>
                    <label id="displayOrderTip"></label> 
               </div>
               <div>
	               <button style="position: relative; left:1000px; top:-80px;" type="button" class="btn btn-primary" id="btnSysDicAdd">
	                   <i  class=" fa fa-plus bigger-110"></i> 新增
	               </button>
               </div>
          </div>
          <div id="submitBtn" class="form-actions" style="position: relative; left:280px;">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="submit" id="submitSysDicList" class="btn default"><i class="fa fa-save"/></i> 保存</button>
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
        var count = 2;
        var array = new Array();
        
        var btnUpCss = {
            position: 'relative',
		    top:'-95px'
        }
        
        init();
        
        /**
                                 重置操作
        */
	    var reset = function(){
        	$("#category").val("");
        	$("#code").val("");
        	$("#name").val("");
        	$("#displayOrder").val("");
        }
        
	    
        
         function init(){
        	<c:forEach items="${listCategory}" var="sysDic">  
     		var sysDic = {
      		    	category: '${sysDic.value}',
      		    	categoryName:'${sysDic.content}',
      		}
     		array.push(sysDic); 
     		</c:forEach>  
     		
     		if ($.validator) {
                $.validator.prototype.elements = function () {
                    var validator = this,
                      rulesCache = {};
      
                    // select all valid inputs inside the form (no submit or reset buttons)
                    return $(this.currentForm)
                    .find("input, select, textarea")
                    .not(":submit, :reset, :image, [disabled]")
                    .not(this.settings.ignore)
                    .filter(function () {
                        if (!this.name && validator.settings.debug && window.console) {
                            console.error("%o has no name assigned", this);
                        }
                       
                        rulesCache[this.name] = true;
                        return true;
                    });
                }
            }
     		
        }
        
         function searchCategory(category){
         	if(category!=null && typeof(category)!="undefined"){
         		for(i = 0;i<array.length;i++){
              		if(array[i].category == category){
              			return i;
              		}
              	}
         	}
         }
         
        
        
        var btnSysDicAdd = function(){
        	var submitBtn = $("#submitBtn");
        	console.log(submitBtn);
        	var str = "<div id='row"+count+"' class=row' style='position: relative; left:0px;'>"
        	        +"<label style='position: relative; left:-350px; top:5px;'>类别：</label>"
        	        +"<div class='col-md-3' style='position: relative; left:35px;'>"
        	        +"<select class='form-control category' id='category"+count+"' name='category'>"
        	        +"<option value=''>请选择类别</option>"
					+"<c:forEach items='${listCategory}' var='sysDic'>"
					+"<option value='${sysDic.value}'>${sysDic.content}</option>"
					+"</c:forEach></select>"
					+"<input type='hidden' name='categoryName' /><label id='category"+count+"Tip'></label> </div>"
                    +"<label style='position: relative; left:-180px; top:5px;'>字典代码：</label>"
                    +"<div class='col-md-3' style='position: relative; left:120px;'>"
                    +""
                    +"<input type='text' class='form-control' id='code"+count+"' name='code' placeholder=''>"
                    +"<label id='code"+count+"Tip'></label></div>"
                    +"<label style='position: relative; left:0px; top:5px;'>字典名称：</label>"
                    +"<div class='col-md-3' style='position: relative; left:180px;top:-25px;'>"
                    +"<input type='text' class='form-control' id='name"+count+"' name='name' placeholder=''>"
                    +"<label id='name"+count+"Tip'></label></div>"
                    +"<label style='position: relative; left:180px; top:5px;'>排序：</label>"
                    +"<div class='col-md-3' style='position: relative; left:220px;top:-25px;'>"
                    +"<input type='text' class='form-control' id='displayOrder"+count+"' name='displayOrder' placeholder=''>"
                    +"<label id='displayOrder"+count+"Tip'></label></div><div id='button"+count+"'>"
                    +"<button style='position: relative; left:985px; top:-80px;' onclick='deleteBtn("+count+")' type='button' class='btn btn-primary deleteBtn'>"
	                + "<i class=' fa fa-remove bigger-110'></i> 删除"
		            + "</button></div>"
        	        +"</div>"
        	       
        	        
        	  submitBtn.before(str);
        	  
        	  
        	  count++;
        	  
      	      $(".category").bind("change",function() {
      	    	  changeCategory($(this));
      	      })
        } 
        
        
        
        var deleteBtn = function(count){
        	$("#row"+count).remove();
        }
        
        
        var changeCategory = function(data){
        	console.log(data);
        	var hiddenCategoryName = null;
        	var category = null;
        	if(data.target){
        		if(data.target.value){
        			category = data.target.value;
        			hiddenCategoryName = $("#categoryName");
        		}
        	}else{
        		category = $(data).val();
        		hiddenCategoryName = $(data).next();
        	}
        	
        	var index = searchCategory(category);
        	var categoryName = array[index].categoryName;
        	hiddenCategoryName.val(categoryName);
        }
        
        
        $(document).ready(function () {
        	$("#reset").bind('click', reset);
        	$("#btnSysDicAdd").bind("click",btnSysDicAdd);
        	$(".category").bind("change",changeCategory);
        	
        	/* $("#submitSysDicList").click(function(){
        		$("#categoryForm").submit();
        	}) */
        	
        	
            $("#categoryForm").validate({
                debug: true,
                errorElement: "label",
                errorClass: "valiError",
                errorPlacement: function (error, element) {
                    error.appendTo($("#" + element.attr('id') + "Tip"));
                },
                rules: {
                	category: {
                        required: true
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
                	
                },
                
                showErrors : function(errorMap, errorList) {
                	/* if($(":button").hasClass("btn-primary")){
                		console.log($(this));
                		$(this).css(btnUpCss);
                	} */
                	$(":button[class*=btn-primary]").each(function(){
                		$(this).css(btnUpCss);
                	})
                	//console.log();
                	//$(":button[class=btn-primary]").css(btnUpCss);
                	this.defaultShowErrors();
                	
                },
                submitHandler: function (form) {
                    form.submit();
                    
                }
            }); 
        });

    </script>
    
    
</critc-script>