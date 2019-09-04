<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<title>在线用户统计</title>
 <link rel="stylesheet"
	href="${staticServer}/assets/example/autocomplete/css/jquery.autocomplete.css" />
</head>
<body>

	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a
				href="${dynamicServer}/index.htm">首页</a></li>
			<li>>系统管理</li>
			<li>>在线用户</li>
		</ul>
	</div>
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-cogs"></i>当前&nbsp;${onlineNum }&nbsp;人在线
			</div>
		</div>
		<div class="portlet-body">
			<div class="table-responsive">
				<table class="searchTable">
					<tr>
						<td>账号：</td>
						<td><input type="text" id="txtUsername"
							class="form-control input-small" placeholder=""
							value="<c:if test='${not empty paramMap.userName   }'>${paramMap.userName }</c:if>"></td>
						<td>用户姓名：</td>
						<td><input type="text" id="txtRealname"
							class="form-control input-middle" placeholder=""
							value="<c:if test='${not empty paramMap.realName }'>${paramMap.realName }</c:if>" /></td>

						<td>角色：</td>
						<input type="hidden" value=" ${paramMap.roleName}" id="sd" />
						<td><form:select path="sysUserSearchVO.roleId"
								class="form-control" id="cmbRoleId">

								<form:option value="" label="--全部--" />
								<form:options items="${listRole}" itemValue="value"
									itemLabel="content" />

							</form:select></td>
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
			<table id="simple-table"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th width=60>#</th>
						<th >账号</th>
						<th >用户姓名</th>
						<th >角色</th>
						<th width="120">登录IP</th>
						<th >登录记录</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userList }" var="sysUser" varStatus="st">
						 <%-- <c:if
							test="${st.index+1 <= pageIndex*pageSize && st.index+1>=(pageIndex-1)*pageSize+1}">  --%>
							<tr>
								<td>${st.index+1 }</td>
								<td>${sysUser.username }</td>
								<td>${sysUser.realName }</td>
								<td>${sysUser.roleName }</td>
								<td>${sysUser.userIp }</td>
								<td ><a
									href="javascript:viewLoginHis('${sysUser.userId}','${sysUser.username}')">查看
								</a></td>
							</tr>
						<%--  </c:if>  --%>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<%-- <div class="row">
		<div class="col-xs-12">${pageNavigate.pageModel}</div>
	</div>
 --%>

	<critc-script>
	
	<script
	src="${staticServer}/assets/example/autocomplete/js/jquery.autocomplete.js"></script>
	 <script type="text/javascript">
		 function viewLoginHis(id, title) {
			window.location = "${dynamicServer}/sys/onlineuser/toLoginHistory.htm?id="+ id;
		};
		$(function() {
			$("#btnSearch").bind('click', searchUser);
			sel();
		})
		// 查询方法
		var searchUser = function() {
			var url = "index.htm?";
			if ($("#txtUsername").val() != "")
				url += "username=" + $("#txtUsername").val();
			if ($("#txtRealname").val() != "") {
				url += "&realName=" + $("#txtRealname").val();
			}

			if ($("#cmbRoleId").val() != "")
				url += "&rolename=" + $("#cmbRoleId").val();
			window.location = encodeURI(url);
		}

		//设置查询条件下拉框选中
		function sel() {
			var roleName = $("#sd").val();
			var length = $("#cmbRoleId option").length;
			for (var i = 0; i < length; i++) {
				if ($("#cmbRoleId").get(0).options[i].value == $.trim(roleName)) {
					$("#cmbRoleId").get(0).options[i].selected = true;
				}
			}

		}
		  var names ; //定义数据  
		 $(document).ready(function(){  
	          $.ajax({  
	            type:'POST',  
	            contentType: "application/json",  
	            url: "${dynamicServer}/sys/onlineuser/getAutoComplteList.htm",  
	            dataType: "json",
	            success:function(data){  
	              names = data;  
	              autocompleteFn(data);  
	              autocompleteFn2(data);
	            }  
	          });  
	        });  
	        
		
		function autocompleteFn(names){  
		      $("#txtUsername").autocomplete(names,{  
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
		function autocompleteFn2(names){  
		      $("#txtRealname").autocomplete(names,{  
		        minChars:0,  
		        max: 10,  
		        dataType:"json",  
		        autoFill: true,  
		        mustMatch: false,  
		        matchContains: true,  
		        scrollHeight: 220,  
		        formatItem: function(data, i, total) {  
		          return "<font style='color:black;font-size:14px'>"+data.realName+"</font>";
		        },  
		        formatMatch: function(data, i, total) {  
		          return data.realName;
		        },  
		        formatResult: function(data) {  
		          return data.realName;
		        }  
		      });  
		    }  
	</script> </critc-script>
</body>
</html>