<%@page import="org.w3c.dom.Document"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<meta charset="utf-8" />
<meta name="description" content="${webTitle}" />
<title><sitemesh:write property='title' /> - ${webTitle}</title>
<!--切换主题  -->
<c:if test="${themeStyle eq 0 }">
	<%@include file="../common/styles-3.jspf"%>
</c:if>
<c:if test="${themeStyle eq 1 }">
	<%@ include file="../common/styles.jspf"%>
</c:if>
<sitemesh:write property='head' />
<sitemesh:write property='critc-css' />
<link rel="shortcut icon" href="${staticServer}/assets/common/images/favicon.ico" />
</head>
</head>
<!--横向菜单主题  -->
<c:if test="${themeStyle eq 0 }">
	<body class="page-container-bg-solid">
		<div class="page-wrapper-row full-height page-wrapper-middle">
			<%@ include file="../common/header-3.jspf"%>

			<!-- BEGIN CONTAINER -->
			<div class="page-container">
				<!-- BEGIN SIDEBAR -->

				<!-- BEGIN CONTENT -->
				<div class="page-content-wrapper">
					<div class="page-content">
						<div class="container">
							<sitemesh:write property='body' />
						</div>
					</div>
				</div>
			</div>
			<!-- END CONTAINER -->

			<%--<%@ include file="../common/footer.jspf" %>--%>
		</div>
		<%@include file="../common/scripts-3.jspf"%>
		<sitemesh:write property='critc-script' />
		<script>
			$(".page-bar > ul").addClass("breadcrumb");
		</script>
	</body>
</c:if>
<!--纵向菜单主题  -->
<c:if test="${themeStyle eq 1 }">
	<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
		<div class="page-wrapper">
			<%@ include file="../common/header.jspf"%>
			<div class="clearfix"></div>

			<!-- BEGIN CONTAINER -->
			<div class="page-container">
				<!-- BEGIN SIDEBAR -->
				<div class="page-sidebar-wrapper">
					<%@ include file="../common/menu.jspf"%>
				</div>
				<!-- BEGIN CONTENT -->
				<div class="page-content-wrapper">
					<div class="page-content">
						<sitemesh:write property='body' />
					</div>
				</div>
			</div>
			<!-- END CONTAINER -->
			<%--<%@ include file="../common/footer.jspf" %>--%>
		</div>
		<%@include file="../common/scripts.jspf"%>
		<sitemesh:write property='critc-script' />
		<script>
			$(".page-bar > ul").removeClass("breadcrumb");
		</script>
	</body>
</c:if>
</html>
