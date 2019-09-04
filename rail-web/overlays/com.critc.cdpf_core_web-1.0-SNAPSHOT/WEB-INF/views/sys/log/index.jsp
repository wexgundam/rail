<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<head>
    <title>操作日志</title>
    <link rel="stylesheet"
	href="${staticServer}/assets/example/autocomplete/css/jquery.autocomplete.css" />
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
            >日志管理
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
						<td><input type="text" id="username"
							class="form-control input-small" placeholder=""
							value="${sysLogSearchVO.username }" /></td>
                    <td>起止日期：</td>
                    <td><input type="text" id="startDate" class="form-control input-small"
                               placeholder="" readonly="readonly" value="${sysLogSearchVO.startDate }">
                    </td>
                    <td>至</td>
                    <td><input type="text" id="endDate" class="form-control input-small"
                               placeholder="" readonly="readonly" value="${sysLogSearchVO.endDate }"></td>
                    <td>
                        <button class="btn btn-primary" id="btnSearch">
                            <i class="ace-icon fa fa-search"></i> 查询
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <table id="simple-table" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th width=40>#</th>
                <th width="100">用户姓名</th>
                <th width="100">账号</th>
                <th width="160">操作时间</th>
                <th width="100">操作IP</th>
                <th width="100">模块名称</th>
                <th width="110">操作名称</th>
                <th>操作url</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${list }" var="sysLog" varStatus="st">
                <tr>
                    <td>${st.index+1 }</td>
                    <td>${sysLog.realName }</td>
                    <td>${sysLog.username }</td>
                    <td><fmt:formatDate value="${sysLog.operaDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${sysLog.operaIp }</td>
                    <td>${sysLog.moduleName }</td>
                    <td>${sysLog.operaName }</td>
                    <td>${sysLog.operaUrl }<a style="float: right;" onclick="param(${sysLog.id})">参数</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- /.span -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-xs-12">${ pageNavigate.pageModel}</div>
</div>
<div class="modal fade table"  id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					请求参数
				</h4>
			</div>
			<div class="modal-body" style="max-height:400px;overflow:auto" >
					<table id="param"  style="table-layout: fixed;" class="table table-striped table-bordered table-hover">
					</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal" >确定
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</body>
<critc-script>
<script
	src="${staticServer}/assets/example/autocomplete/js/jquery.autocomplete.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btnSearch").bind('click', searchUser);

            $('#startDate').datetimepicker({
                lang: 'ch',
                timepicker: false,
                format: 'Y-m-d'
            });
            $('#endDate').datetimepicker({
                lang: 'ch',
                timepicker: false,
                format: 'Y-m-d'
            });
        })
function param(id){
        	$.ajax({
                type: "post",
                url: "operaparams.htm",
                data: "id="+id,
                success: function (data) {
                	cleanRow();
                    var params=new Array();
                    params=data.split("&");
                    params.splice(params.length-1,params.length); 
                    $(params).each(function(i){
                    	insertRows(params[i]);
                    });
                    console.log($(".modal-body"));
                    	$('#myModal').modal("show");
                }
            });
        }
        // 查询方法
        var searchUser = function () {
        	if($("#startDate").val()>$("#endDate").val()){
        		bootbox.alert("结束时间大于起始时间，请重新选择日期！");
        	}else{
        		 var url = "index.htm?";
                 if ($("#username").val() != '')
                     url += "username=" + $("#username").val();
                 if ($("#startDate").val() != '')
                     url += "&startDate=" + $("#startDate").val();
                 if ($("#endDate").val() != '')
                     url += "&endDate=" + $("#endDate").val();
                 window.location = encodeURI(url);
        	}
        }
        
        function insertRows(param) {
            var tb1 = $("#param");
            var tempRow = $("#param tr").size();
            var $tdNum = $("<td style='word-wrap:break-word;word-break;break-all;width:8%;' align='left' >"+param.split("=")[0]+" </td>");
            var $tdName = $("<td style='word-wrap:break-word;word-break;break-all;width:20%;' align='left' ></td>");
            $tdName.html("<lable id='"+tempRow+"'  type='text' size='45' maxlenth='25'>"+param.split("=")[1]+"</lable>");
            var $tr = $("<tr></tr>");
            $tr.append($tdNum);
            $tr.append($tdName);
            tb1.append($tr);
        }
        function cleanRow(){
        	$("#param").empty();
        }
        
        var names ; //定义数据  
        //开始函数  
        $(document).ready(function(){  
          $.ajax({  
            type:'POST',  
            contentType: "application/json",  
            url: "getAutoComplteList.htm",  
            dataType: "json",
            success:function(data){  
              names = data;  
              autocompleteFn(data);  
            }  
          });  
        });  
        
      //自动 补全方法  
        function autocompleteFn(names){  
          $("#username").autocomplete(names,{  
            minChars:0,  
            max: 10,  
            dataType:"json",  
            autoFill: true,  
            mustMatch: false,  
            matchContains: true,  
            scrollHeight: 220,  
            formatItem: function(data, i, total) {  
              return "<font style='color:black;font-size:14px'>"+data.username+"</font>";  
            },  
            formatMatch: function(data, i, total) {  
              return data.username;  
            },  
            formatResult: function(data) {  
              return data.username;  
            }  
          });  
        }  
    </script>
</critc-script>