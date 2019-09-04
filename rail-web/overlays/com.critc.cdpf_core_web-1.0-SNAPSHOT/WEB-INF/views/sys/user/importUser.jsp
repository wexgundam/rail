<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>

<head>
<title>用户管理</title>
</head>

<body>

	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a></li>
			<li>>系统管理</li>
			<li>>用户管理</li>
		</ul>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div id="danger" class="alert alert-danger" style="display: none; margin-top: 10px;">
				<strong>错误!</strong> 文件不正确,无法上传!
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
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
							<tbody>
								<tr>
									<td style="font-size: 16px;">选择文件：</td>
									<td><div class="col-md-3">
											<input style="width: 200px;" type="file" id="file" name="file1" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
										</div></td>
									<td>
										<button class="btn btn-primary" id="btnImport">
											<i class="fa fa-cloud-upload"></i> 导入
										</button>
									</td>
									<td>
										<button class="btn default" id="btnExportTemplate">
											<i class="fa fa-file-excel-o"></i> 下载模板
										</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" style="text-align: left;">
		<span style="font-size: 18px" id="span"></span>
	</div>
	<div class="row">
		<div class="col-md-12">
			<table id="simple-table" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th width=20>行号</th>
						<th width="50">校验结果</th>
						<th width="400">问题描述</th>
						<th width="20">入库</th>
					</tr>
				</thead>
				<tbody id="tbody">

				</tbody>
			</table>
		</div>
	</div>

</body>
<critc-script> <script type="text/javascript">
	var waring = "";
	var sumData = "";
	var listJson = "";
	$(function() {
		//为导入按钮绑定弹出框事件
		$("#btnImport").bind("click", function() {
			if ($("#file").val() == "") {
				bootbox.alert({
					buttons : {
						ok : {
							label : '确定'
						}
					},
					message : '请选择文件'
				});
				return;
			}
			if (sumData == 0) {
				return;
			} else {
				if (waring == 0) {
					bootbox.confirm("你确定要导入吗？", function(result) {
						if (result) {
							//无待完善信息时导入
							sendFile("${dynamicServer}/sys/user/importUser.htm", "import");
						}
					});
				} else if (waring > 0) {
					bootbox.confirm("存在待完善信息，你确定要导入吗？", function(result) {
						if (result) {
							//有待完善信息时导入
							sendFile("${dynamicServer}/sys/user/importUser.htm", "import");
						}
					});
				} else {
					return;
				}
			}
		});
		//向后台发送文件，URL为发送的地址，para为发送方式，para="import"时为导入，其余为验证文件格式
		var sendFile = function(url, para) {
			if ($("#file").val() == "") {
				bootbox.alert({
					buttons : {
						ok : {
							label : '确定'
						}
					},
					message : '请选择文件'
				});
				return;
			}
			var formData = new FormData();
			formData.append("file1", document.getElementById("file").files[0]);
			if (para == "import") {
				formData.append("listJson", listJson);
				$("#file").val("");
			}
			$("#span").html("");
			$.ajax({
				url : url,
				type : "POST",
				data : formData,
				dataType : 'json',
				//必须false才会自动加上正确的Content-Type
				contentType : false,
				//必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理
				processData : false,
				success : function(data) {
					if (!data.status) {
						bootbox.alert({
							buttons : {
								ok : {
									label : '确定'
								}
							},
							message : data.msg
						});
						return;
					}
					var htmlValue = "";
					waring = "" + data.sumWaringLine;
					sumData = "" + data.sumDataLine;
					listJson = "" + data.listJson;
					if (para == "import") {
						htmlValue += "&nbsp;&nbsp;&nbsp;总记录：" + data.sumDataLine + "条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlValue += "<i class='fa fa-check' style='color:green'></i>已导入：" + data.successSum + "条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlValue += "<i class='fa fa-close' style='color:red;'></i>未导入：" + data.failSum + "条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlValue += "<i class='fa fa-exclamation' style='color:#e8c30a;'></i>待完善：" + data.waringSum + "条";
						$("#span").html(htmlValue);
					} else {
						htmlValue += "&nbsp;&nbsp;&nbsp;总记录：" + data.sumDataLine + "条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlValue += "<i class='fa fa-times-circle' style='color:red;'></i>不可导入：" + data.sumErrorLine + "条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						htmlValue += "<i class='fa fa-warning' style='color:#e8c30a;'></i>待完善：" + data.sumWaringLine + "条";
						$("#span").html(htmlValue);
					}
					htmlValue = "";
					$.each(data.list, function(i, v) {
						var str = v.split("-");
						if (i == 0) {
							if (str[0] == "0") {
								//提示文件级错误
								$("#danger").show();
								$("#span").html("");
								$("#file").val("");
								return;
							}
						}
						htmlValue += "<tr>";
						htmlValue += "<td>" + str[0] + "</td>";
						htmlValue += "<td>" + (str[1] == '0' ? '<i class="fa fa-times-circle" style="color:red;"></i>不可导入' : '<i class="fa fa-warning" style="color:#e8c30a;"></i>可导入待完善') + "</td>";
						htmlValue += "<td>" + str[2] + "</td> ";
						if (para == "import") {
							htmlValue += "<td>" + (str[1] == '0' ? '<i class="fa fa-close" style="color:red;"></i>' : '<i class="fa fa-check" style="color:green"></i>') + "</td> ";//0失败1成功
						} else {
							htmlValue += "<td></td> ";
						}
						htmlValue += "</tr>";
					});
					$("#tbody").html(htmlValue);
					htmlValue = "";
				},
				error : function() {
					bootbox.alert({
						buttons : {
							ok : {
								label : '确定'
							}
						},
						message : '上传失败'
					});
				}
			});
		}
		//当文件上传按妞获取焦点的时候，清空内容
		$("#file").bind("focus", function() {
			$("#file").val("");
			$("#danger").hide();
			$("#span").html("");
			$("#tbody").html("");
		});
		//绑定当文件上传按钮内容发生改变时触发，发送异步请求进行后台数据验证
		$("#file").bind("change", function() {
			var fileName = $("#file").val();
			if (fileName.lastIndexOf(".xls") < 0 && fileName.lastIndexOf(".xlsx") < 0) {
				//提示文件级错误
				$("#danger").show();
				$("#span").html("");
				$("#file").val("");
				return;
			}
			sendFile("${dynamicServer}/sys/user/validationImportUser.htm", "");
		});
		//导出模板
		$("#btnExportTemplate").bind("click", function() {
			window.location.href = "${staticServer}/template/sys/user/sys_userExcelTemplate.xlsx";
		});
	});
	function getObjectURL(file) {
		var url = null;
		if (window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != undefined) { // webkit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}
</script> </critc-script>