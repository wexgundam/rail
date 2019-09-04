<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<head>
    <title>字典管理</title>
        <script src="${staticServer }/assets/metronic4.7/pages/scripts/table-datatables-editable.js" type="text/javascript"></script>
        <link href="${staticServer }/assets/metronic4.7/global/css/plugins.min.css" rel="stylesheet" type="text/css" /> 
</head>
<body>
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a>
        </li>
        <li>
            >字典管理
        </li>
        <li>
            >功能维护
        </li>
    </ul>
</div>  
 <div class="page-content" style="position: relative; left:-200px;">
  <form role="form" id="categoryForm" name="categoryForm" class="form-horizontal">
    <div class="row">
	    <div class="col-md-12">
	            <input type="hidden" name="backUrl" value="${backUrl }">
	            <div class="form-body">
	                <input type="hidden" name="id" id="categoryId" value=""/>
	                <input type="hidden" name="backUrl" value="${backUrl } ">
	                <div class="form-group">
	                    <label class="col-md-2 control-label">分类：</label>
	                    <div class="col-md-6">
	                        <input id="categoryParent" name="category" maxlength="40" type="text" param="{oldname:'123'}" class="form-control input-inline  input-xlarge"
	                               placeholder=""
	                               value=""> 
	                        
	                    </div>
	                </div>
	                
	                <div class="form-group">
	                    <label class="col-md-2 control-label">分类名称：</label>
	                    <div class="col-md-6">
	                        <input id="categoryNameParent" type="text" maxlength="20" name="categoryName" class="form-control input-inline  input-xlarge"
	                               placeholder=""
	                               value="">
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-md-2 control-label">排序：</label>
	                    <div class="col-md-6">
	                        <input id="displayOrderParent" type="text" maxlength="6" name="displayOrder"
	                               class="form-control input-inline  input-xlarge"
	                               placeholder=""
	                               value="">
	                    </div>
	                </div>
	                
	                 <div class="form-group">
	                       <label class="col-md-2 control-label">字典明细：</label>
                           <div class="col-md-10" >
			                    <table class="table table-striped table-hover table-bordered" id="sample_editable_1" style="text-align: center;">
			                        <thead>
			                            <tr>
			                                <th style="text-align: center;">字典代码</th>
			                                <th style="text-align: center;">字典名称 </th>
			                                <th style="text-align: center;"> 排序 </th>
			                                <th style="text-align: center;"> 编辑 </th>
			                                <th style="text-align: center;">删除 </th>
			                            </tr>
			                        </thead>
			                        <tbody>
			                             <c:forEach items="${sysDicList}" var="sysDic" varStatus="st">
			                                   
							                   <c:if test="${sysDic.code != null }">
							                       
													<tr role="row">
													    <input type="hidden" name="id" value="${sysDic.id}"/>
														<td>${sysDic.code }</td>
														<td>${sysDic.name }</td>
														<td>${sysDic.displayOrder }</td>
														<td>
														     <c:if test="${critc:isP('SysDicUpdate')}">
												                 <a class="edit" href="#"> 修改 </a>
											                 </c:if> 
											             </td>
											             <td>
											                 <c:if test="${critc:isP('SysDicDelete')}">
												                 <a class="delete" href="#"> 删除 </a>
											                </c:if>
											             </td>
													</tr>
												</c:if>
						                    </c:forEach>
			                        </tbody>
			                    </table>
                                <br/>
			                    <div id="submitBtn" class="form-actions" style="position: relative; left:-175px;" >
					                <div class="row">
					                    <div class="col-md-offset-3 col-md-9">
					                        
					                        <button type="button" class="btn btn-success" id="sample_editable_1_new">
				                                <i class="ace-icon fa fa-plus bigger-110"></i>新增
				                            </button>
				                            <button type="submit" id="submitSysDicList" class="btn btn-primary"><i class="fa fa-save"/></i> 保存</button>
				                        	<button type="button" class="btn default" onclick="history.back(-1)"><i class="fa fa-undo"/></i>
				                                                                            取消
				                            </button>
					                    </div>
					                </div>
			                  </div>
                         </div>
	                 </div>
	           </div>
	    </div>
    </div>
   
    </form>
 </div>
 <critc-script>
 <script type="text/javascript"> 
     var checkSysDic = null; 
     var delArray = new Array();   //要删除的sysDic的id
     var array = new Array();
     var sysDicCategory = null;
     var checkSysDic = null;
     var categoryId = null;
     var JqValidate = null;
     var updateArray = new Array();   //要更新的sysDic的id
     var categoryParent = null;
	 var categoryNameParent = null;
	 var displayOrderParent = null;
	 var newSysDicArray = new Array();
	 function init(){
	 	<c:forEach items="${sysDicList}" var="sysDic">
		 	var sysDic = {
		 			id: "${sysDic.id}",
			    	category: '${sysDic.category}',
			    	categoryName:'${sysDic.categoryName}',
			    	code:'${sysDic.code}',
			    	name:'${sysDic.name}',
			    	displayOrder:'${sysDic.displayOrder}'
			}
	 	    if(sysDic.code== ''){
	 	    	sysDicCategory = sysDic;
	 	    	array.push(sysDic); 
	 	    }
	 	    else{
	 	    	array.push(sysDic); 
	 	    }
			</c:forEach>
			$("#categoryParent").val(sysDicCategory.category);
			$("#categoryNameParent").val(sysDicCategory.categoryName);
			$("#displayOrderParent").val(sysDicCategory.displayOrder);
			
			
			//用于多个相同的name,校验是使用
		    if ($.validator) {
	          $.validator.prototype.elements = function () {
	             var validator = this,
	               rulesCache = {};
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
		 
	 init();  
	 
	 $(function(){
		 
		 
		//为每个td添加prev属性，用于校验
		$("table tr").find("td").each(function(){
	        if(!($(this).text().trim()=='修改' || $(this).text().trim()=='删除')){
				$(this).attr("prev",$(this).text().trim());
			} 
		 }); 
		 
	    $("#categoryForm").validate({
             debug: true,
             errorClass: "valiError",
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
                         	    prev: sysDicCategory.category,
                                value: function () {
                                    return $("#categoryParent").val();
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
                         	prev: sysDicCategory.categoryName,
                         	value: function () {
                                 return $("#categoryNameParent").val();
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
                 
                 code: {
                     required: true,
                     maxlength: 20,
                     checkDicExist: ["code"],
                 },
                 
                 name: {
                     required: true,
                     maxlength: 20,
                     checkDicExist: ["name"],
                 }, 
                 displayOrder: {
                     required: true,
                     digits:true,
                     maxlength: 6,
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
                 formSubmit(form);
             }
         });
	 });
	 
	 
	 //一个汉字占2个字节
	 jQuery.validator.addMethod('byteRangeLength', function(value, element, param) {
		 var length = value.length;
		 console.log(length);
		 for(var i = 0; i < value.length; i++){
			 if(/^[\u4e00-\u9fa5]+$/.test(value.charAt(i))){
				 console.log("fff");
				 length++;
			 } 
			 
		 }
		 
		 return this.optional(element) || ( length >= param[0] && length <= param[1] );
	 }, $.validator.format('最多可以输入{1}个字符!'));
	 
	 
	 //字符和数字的组合，以字符开头
     jQuery.validator.addMethod('charNums', function(value, element){
    	return this.optional(element) ||/^[a-zA-Z]+[a-zA-Z0-9_]*$/.test(value);
     }, $.validator.format('只能包括英文字母,数字和下划线,且以英文开头!'));
	 
  	  //校验name,code是否唯一
     jQuery.validator.addMethod('checkDicExist', function(value, element,param){
    	  var prev = null,flag,params = null,code = null,name = null;
    	  var codeArray = new Array();
    	  var nameArray = new Array();
    	  if($(element).attr("prev")!='undefined'){
    		  prev =  $(element).attr("prev");
    	  }
    	  
    	  //用于验证与最新数据是否唯一
    	  $("tbody tr").each(function(){
  			  if($(this).find("input").hasClass("input-small")){
      			 code = $(this).children("td:eq(0)").find("input").val();
      			 name = $(this).children("td:eq(1)").find("input").val();
      			 console.log("1:"+code+","+name);
      		  }else{
      			 code = $(this).children("td:eq(0)").text();
   				 name = $(this).children("td:eq(1)").text();
   				 console.log("2:"+code+","+name);
      		  }
  			  
  			  if(code!= null && typeof(code)!='undefined'){
  				  codeArray.push(code);
  			  }
  			  
  			  if(name!=null && typeof(name)!='undefined'){
  				  nameArray.push(name);
  			  }
     	  })
     	  
     	  console.log(codeArray);
    	  console.log(nameArray);
    	  
    	  
    	  if(param[0] == 'code'){
    		  flag = check(value,codeArray);
    	  }else{
    	  	  params = {"param":param[0],"prev":prev, "value":value};
	    	  $.ajax({
	  	   		  type: "post",
	  	   		  async : false,
	  	   		  url: "checkDicExist.htm",
	  	   		  data: params,
	  	   		  success: function(json){
	  	   			 var data = JSON.parse(json);
	               	 if(data.result == 0){  //与修改前相同
	               		flag = true;
	               	 }else if(data.result == 1){  //数据库中不存在
	               		flag = true;
	               	 }else{  //数据库中存在  
	               		flag = false;
	               	 }
	  	   		  }
	     	  });
	    	  
	    	  if(flag){
	    		  flag = check(value,nameArray);
	    	  }
	      }
    	  
    	  if(!flag){
    		  $(element).parent().attr("validate",false);
    	  }else{
    		  $(element).parent().attr("validate",true);
    	  }
    	  
    	  return flag;
    	
     }, $.validator.format('字典信息已存在')); 
	 
	 
	 //提交信息
     formSubmit = function (){
    	 var newSysDic = null,originalSysDic = null,code1 = null,
    	 displayOrder1 = null,displayOrder1 = null; 
    	 categoryId = $("#categoryId").val();
    	 categoryParent = $("#categoryParent").val();
    	 categoryNameParent = $("#categoryNameParent").val();
    	 displayOrderParent = $("#displayOrderParent").val();
    	//1、判断字典类别是否改变
    	 if(!checkCategory()){
    		 updateArray.push(sysDicCategory);
    	 } 
    	 
    	 //2、原始的字典信息是否修改
    	 $("tbody tr").each(function(){
    		 if($(this).find(":input:first").attr("name")=="id"){
    			 var id = $(this).find(":input:first").val();
    			 if($(this).children("td:eq(0)").find("input").hasClass("input-small")){
        			 code1 = $(this).children("td:eq(0)").find("input").val();
        			 name1 = $(this).children("td:eq(1)").find("input").val();
        			 displayOrder1 = $(this).children("td:eq(2)").find("input").val(); 
        		 }else{
        			 code1 = $(this).children("td:eq(0)").text();
     				 name1 = $(this).children("td:eq(1)").text();
     				 displayOrder1 = $(this).children("td:eq(2)").text();
        		 }
        		 var index = searchCategory(id);
        		 var newSysDic = {
                        id: id,		 
        				category:categoryParent,
        				categoryName:categoryNameParent,
        				code:  code1,
        				name:name1,
        				displayOrder:displayOrder1
        		 }
        		 
        		 //console.log(newSysDic);
        		 if(code1!=null){
        			 if(!checkSysDic(array[index],newSysDic)){
            			 updateArray.push(newSysDic);
            		 }
        		 } 
    		 }
    	 })
    	 
    	 
    	 //3、添加新增的字典
    	 $("tbody tr").each(function(){
    		  var newSysDic = new Object();
    		  if($(this).hasClass("newRow")){
    			   newSysDic.id=0;
      			   newSysDic.category = categoryParent;
      			   newSysDic.categoryName = categoryNameParent;
    			   if($(this).find(":input:first").attr("name")!="id"){
    				   if($(this).children("td:eq(0)").find("input").hasClass("input-small")){
    					   newSysDic.code = $(this).children("td:eq(0)").find("input").val();
    	       			   newSysDic.name = $(this).children("td:eq(1)").find("input").val();
    	       			   newSysDic.displayOrder =$(this).children("td:eq(2)").find("input").val();
    				   }else{
    					   newSysDic.code = $(this).children("td:eq(0)").text();
     	       			   newSysDic.name = $(this).children("td:eq(1)").text();
     	       			   newSysDic.displayOrder = $(this).children("td:eq(2)").text();
    				   }
    			   	   newSysDicArray.push(newSysDic);
    			   }
    		   }
    		
    	 }); 
    	 
    	 
    	 var param = new Array();
    	 param.push(newSysDicArray);
    	 param.push(updateArray);
    	 param.push(delArray);
    	 $.ajax({
    		  type: "post",
    		  url: "maintenanceSysDic.htm",
    		  dataType:"json",
    		  contentType : 'application/json;charset=utf-8', 
    		  data: JSON.stringify(param),
    		  success: function(data){
    			  var backUrl = "${backUrl}";
    			  if(data.message=='10003'){
    			      window.location = "${dynamicServer}/success.htm?resultCode="+data.message+"&backUrl="+backUrl;
    			  }else{
    				  window.location = "${dynamicServer}/error.htm?resultCode="+data.message+"&backUrl="+backUrl;
    			  }
    		  }
    	 });  
    	 
    	 
    	 
    	
     }
	
	 //根据id在数组中查找相应数据的索引
     function searchCategory(id){
       	if(id!=null && typeof(id)!="undefined"){
       		for(i = 0;i<array.length;i++){
            		if(array[i].id == id){
            			return i;
            		}
            }
       	}
     }
     
     //用于判断原始的字典信息是否被修改
     checkSysDic = function(sysDic1,sysDic2){
　                      var flag = sysDic1.code == sysDic2.code 
           && sysDic1.name== sysDic2.name
           && sysDic1.category == sysDic2.category
           &&sysDic1.displayOrder == sysDic2.displayOrder
           &&sysDic1.categoryName ==sysDic2.categoryName;
　                   //console.log(sysDic1.category+ "," + sysDic2.category+","+flag);
    	 if(flag) {
    		 return true;
    	 }else{
    		 return false;
    	 }
     }
     
     
     //用于检查字典类别是否被修改
     function checkCategory(){
    	 if(sysDicCategory.category == categoryParent && sysDicCategory.categoryName == categoryNameParent && sysDicCategory.displayOrder == displayOrderParent){
    		 return true;
    	 }else{
    		 sysDicCategory.category = categoryParent;
    		 sysDicCategory.categoryName = categoryNameParent;
    		 sysDicCategory.displayOrder = displayOrderParent;
    		 return false;
    	 }
     }
     
     
     //表格中查找字典代码，名称是否唯一
     function check(value,array){
    	var count = 0;
        for(var i = 0;i<array.length;i++){
        	if(array[i] == value){
        		count++;
        	}
        }
        
        if(count>1){
        	return false;
        }else {
        	return true;
        }
     }
 </script>
</critc-script>
</body>