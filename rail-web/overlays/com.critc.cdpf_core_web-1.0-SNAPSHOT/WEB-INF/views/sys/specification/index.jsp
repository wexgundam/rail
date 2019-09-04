<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<head>
<title>研发规范</title>
</head>
<critc-css> </critc-css>
<body>
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a></li>
			<li>>帮助建议</li>
			<li>>研发规范</li>
		</ul>
	</div>
	<div class="row">
		<div class=" col-md-12">
			<div class="tabbable-custom nav-justified">
				<ul class="nav nav-tabs ">
					<li class="active" id=""><a href="#tab_1_1" data-toggle="tab"> 技术选型 </a></li>
					<li class="" id=""><a href="#tab_1_2" data-toggle="tab"> 技术指南 </a></li>
					<li class="" id=""><a href="#tab_1_3" data-toggle="tab"> 数据库设计 </a></li>
					<li class="" id=""><a href="#tab_1_4" data-toggle="tab"> 接口设计 </a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade  active in" id="tab_1_1">
						<div style="width: 100%; overflow: auto;" id="technicalSelectionPage">
							<c:forEach var="temp" begin="0" end="${ technicalSelectionPage-1}">
								<img data-group='test' data-original="${imageServer }/sys/specification/${technicalSelectionPdfName }page_${temp }.jpg" style="width: 100%;" />
								<br />
							</c:forEach>
						</div>
					</div>
					<div class="tab-pane fade" id="tab_1_2">
						<div style="width: 100%; overflow: auto;" id="technicalGuidePage">
							<c:forEach var="temp" begin="0" end="${ technicalGuidePage-1}">
								<img data-group='test' data-original="${imageServer }/sys/specification/${technicalGuidePdfName }page_${temp }.jpg" style="width: 100%;" />
								<br />
							</c:forEach>
						</div>
					</div>
					<div class="tab-pane fade " id="tab_1_3">
						<div style="width: 100%; overflow: auto;" id="databaseDesignPage">
							<c:forEach var="temp" begin="0" end="${ databaseDesignPage-1}">
								<img data-group='test' data-original="${imageServer }/sys/specification/${databaseDesignPdfName }page_${temp }.jpg" style="width: 100%;" />
								<br />
							</c:forEach>
						</div>
					</div>
					<div class="tab-pane fade " id="tab_1_4">
						<div style="width: 100%; overflow: auto;" id="interfaceDesignPage">
							<c:forEach var="temp" begin="0" end="${ interfaceDesignPage-1}">
								<img data-group='test' data-original="${imageServer }/sys/specification/${interfaceDesignPdfName }page_${temp }.jpg" style="width: 100%;" />
								<br />
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="page-footer">
		<div class="scroll-to-top" style="display: block;">
			<i class="icon-arrow-up" style="font-size: 45px"></i>
		</div>
	</div>
</body>
<critc-script> <script type="text/javascript" src="${staticServer }/assets/imagelazyout/jquery.lazyload.js"></script> <script>
	$(function() {
		$("img[data-group='test']").lazyload({
			effect : "fadeIn"
		});
		$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
			$("img[data-group='test']").lazyload({
				effect : "fadeIn"
			});
		})
	});
</script> </critc-script>
