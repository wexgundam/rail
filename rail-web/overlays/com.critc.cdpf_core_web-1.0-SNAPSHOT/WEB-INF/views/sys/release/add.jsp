<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<head>
    <title>系统说明</title>
    <script type="text/javascript" charset="utf-8">
        window.UEDITOR_HOME_URL = "${staticServer}/"; 
    </script>
    <script type="text/javascript" charset="utf-8" src="${staticServer}/assets/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${staticServer}/assets/ueditor/ueditor.all.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${staticServer}/assets/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>

<body>
<!-- BEGIN PAGE BAR -->
<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a>
        </li>
        <li>
            >系统管理
        </li>
        <li>
            >系统说明
        </li>
    </ul>
</div>
<h1 class="page-title"> 系统说明
    <small>>>新增系统说明</small>
</h1>
<div class="row">
    <div class="col-md-12">
        <form role="form" id="sysReleaseForm" name="categoryForm" class="form-horizontal"
              action="add.htm" method="post">
            <input type="hidden" name="backUrl" value="${backUrl }">
            <div class="form-body">
                <div class="form-group">
                    <label class="col-md-3 control-label">系统名称：</label>
                    <div class="col-md-9">
                        <input id="name" maxlength="25" name="name" type="text" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""> <label id="nameTip"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">版本号：</label>
                    <div class="col-md-9">
                        <input id="version" maxlength="10" name="version" type="text" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""> <label id="versionTip"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">发布日期：</label>
                    <div class="col-md-9">
                        <input id="releaseDate" maxlength="10" name="releaseDate" type="text" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""> <label id="releaseDateTip"></label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-md-3 control-label">发布人：</label>
                    <div class="col-md-9">
                        <input id="releaseBy" maxlength="10" type="text" name="releaseBy" class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""><label id="releaseByTip"></label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">发布内容：</label>
                    <div class="col-md-9">
                        <input type="hidden" id="content" name="content" value=""/>
                         
                        <script id="editor" type="text/plain" style="height:260px"></script>
                        
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="submit" class="btn btn-primary"><i class="fa fa-save"/></i> 保存</button>
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

<critc-script>
    <script type="text/javascript"> 
    
       
   		
        /**
                             重置按钮        
        */
	    var reset = function(){
        	$("#name").val("");
        	$("#version").val("");
        	$("#releaseDate").val("");
        	$("#releaseBy").val("");
		}
        
       
        
        $(document).ready(function () {
        	$("#reset").bind('click', reset);
        	
        	$('#releaseDate').datetimepicker({
                lang: 'ch',
                timepicker: false,
                format: 'Y-m-d'
            });
        	
        	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        	
        	var ue = UE.getEditor('editor',{
   			   	 autoHeightEnabled: false,    //显示滚动条
   			 	 enableAutoSave: false,       //取消自动保存
    			 autoFloatEnabled: false,      //是否保持toolbar的位置不动,默认true
    			 elementPathEnabled:false ,    //显示元素路径
    			 initialFrameWidth: null,
    			 wordCount:false
   	        });
        	
        	
        	UE.Editor.prototype.getActionUrl = function(action) { 
	            if (action == 'ueditorUploadImage' || action == 'uploadscrawl' || action == 'uploadimage') {
			          //这里调用后端我们写的图片上传接口
			          return '${dynamicServer}/ueditorUploadImage.htm?basePath=/sys/release/';
	            }else{
	                 return this._bkGetActionUrl.call(this, action);
	            }
	         }
        	
        	
        	ue.ready(function() {//编辑器初始化完成再赋值  
        		
        		$.ajax({
	  				  url: "readFile.htm",
	  				  data: {path:"template.txt"},
	  				  success: function(data){
	  					  ue.setContent(data);
	  				  } 
  			    });
        		
        	     
             });  
        	
        	
            $("#sysReleaseForm").validate({
                debug: true,
                errorElement: "label",
                errorClass: "valiError",
                errorPlacement: function (error, element) {
                    error.appendTo($("#" + element.attr('id') + "Tip"));
                },
                rules: {
                	name: {
                        required: true,
                        maxlength: 25
                    },
                    
                    version: {
                        required: true,
                        maxlength:10,
                        remote: {
                            url: "checkVersionExist.htm", //后台处理程序
                            type: "post", //数据发送方式
                            data: { //要传递的数据
                            	    name: function () {
                                       return $("#name").val();
                                    },
                            	    version: function () {
                                       return $("#version").val();
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
                     
                    releaseDate: {
                        required: true,
                        maxlength: 10
                       
                    }, 
                	
                	
                    releaseBy: {
                    	required: true,
                        maxlength: 10
                    },
                    
                    
                },
                messages: {
                	version: {
                        remote: "该版本号已存在！"
                    },
                },
                submitHandler: function (form) {
                	$("#content").val(ue.getContent());
                    form.submit();
                }
            });
        });

    </script>
</critc-script>

</body>