<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<head>
<title>主题切换</title>
<critc-css>
<link href="${staticServer}/assets/treetable/treeTable.min.css?version=${versionNo}" rel="stylesheet" type="text/css" />
<!-- BEGIN THEME LAYOUT STYLES --> </critc-css>
</head>

<body>
	<!-- BEGIN PAGE BAR -->
	<div class="page-bar">
		<ul class="page-breadcrumb">
			<li><i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a></li>
			<li>>系统管理</li>
			<li>>系统界面</li>
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
				<table class="searchField" style="margin: 4px; padding: 4px;">
					<tr>
						<td>
							<button class="btn btn-success" id="horizontalMenu">
								<i class="fa fa-arrows-h"></i> 横向菜单主题
							</button>
							<button type="button" class="btn btn-success" id="verticalMenu">
								<i class=" fa fa-arrows-v"></i> 纵向菜单主题
							</button>
						</td>
					</tr>

				</table>

			</div>

		</div>

	</div>
	<c:if test="${themeStyle == 1}">
		<div class="row">
			<div class="col-md-12">
				<div class="portlet light portlet-fit portlet-form bordered">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-eye"></i>改变页面风格
						</div>
						<div class="tools">
							<a href="javascript:;" class="collapse"> </a>
						</div>
					</div>
					<div class="portlet-body">
						<!-- BEGIN THEME PANEL -->
						<div class="row">
							<div class="theme-panel" style="float: none; margin: 0 auto; position: static;">
								<div class="theme-options" style="display: inline; background: #ffffff; position: static;">
									<div class="theme-option theme-colors clearfix">
										<span style="color: black;">主题颜色 </span>
										<ul>
											<li class="color-default current tooltips" data-style="default" data-container="body" data-original-title="Default"></li>
											<li class="color-darkblue tooltips" data-style="darkblue" data-container="body" data-original-title="Dark Blue"></li>
											<li class="color-blue tooltips" data-style="blue" data-container="body" data-original-title="Blue"></li>
											<li class="color-grey tooltips" data-style="grey" data-container="body" data-original-title="Grey"></li>
											<li class="color-light tooltips" data-style="light" data-container="body" data-original-title="Light"></li>
											<li class="color-light2 tooltips" data-style="light2" data-container="body" data-html="true" data-original-title="Light 2"></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">页面布局:</label>
							<div class="col-md-2">
								<select id="layout-option" class=" form-control input-sm">
									<option value="fluid" selected="selected">整体布局</option>
									<option value="boxed">盒式布局</option>
								</select>
							</div>
						</div>
						<div class="row" style="padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">页眉布局:</label>
							<div class="col-md-2">
								<select id="page-header-option" class=" form-control input-sm">
									<option value="fixed" selected="selected">浮动</option>
									<option value="default">默认</option>
								</select>
							</div>
						</div>
						<div class="row" style="padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">菜单栏下拉框样式:</label>
							<div class="col-md-2">
								<select id="page-header-top-dropdown-style-option" class=" form-control input-sm">
									<option value="light" selected="selected">亮</option>
									<option value="dark">不亮</option>
								</select>
							</div>
						</div>
						<div class="row" style="padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">左侧菜单栏样式:</label>
							<div class="col-md-2">
								<select id="sidebar-option" class=" form-control input-sm">
									<option value="fixed">浮动</option>
									<option value="default" selected="selected">默认</option>
								</select>
							</div>
						</div>
						<div class="row" style="padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">左侧菜单栏选项样式:</label>
							<div class="col-md-2">
								<select id="sidebar-menu-option" class=" form-control input-sm">
									<option value="accordion" selected="selected">可折叠的</option>
									<option value="hover">悬停边栏</option>
								</select>
							</div>
						</div>
						<div class="row" style="padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">左侧菜单栏选择时高亮状态:</label>
							<div class="col-md-2">
								<select id="sidebar-style-option" class=" form-control input-sm">
									<option value="default" selected="selected">默认</option>
									<option value="light">左边高亮</option>
								</select>
							</div>
						</div>
						<div class="row" style="display: none; padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">菜单栏位置:</label>
							<div class="col-md-2">
								<select id="sidebar-pos-option" class="form-control input-sm">
									<option value="left" selected="selected">左</option>
									<option value="right">右</option>
								</select>
							</div>
						</div>
						<div class="row" style="display: none; padding-bottom: 20px;">
							<label class="col-md-5" style="text-align: right;">脚注浮动状态 :</label>
							<div class="col-md-2">
								<select id="page-footer-option" class="form-control input-sm">
									<option value="fixed">浮动</option>
									<option value="default" selected="selected">默认</option>
								</select>
							</div>
						</div>
						<!-- END THEME PANEL -->
					</div>
				</div>
			</div>
		</div>
	</c:if>
</body>
<critc-script> <!-- BEGIN THEME LAYOUT SCRIPTS --> <!-- END THEME LAYOUT SCRIPTS --> </script> <script>
	//Handle Theme Settings
	var handleTheme = function() {

		var panel = $('.theme-panel');

		if ($('body').hasClass('page-boxed') === false) {
			$('#layout-option', panel).val("fluid");
		}

		$('#sidebar-option', panel).val("default");
		$('#page-header-option', panel).val("fixed");
		$('#page-footer-option', panel).val("default");
		if ($('#sidebar-pos-option').attr("disabled") === false) {
			$('#sidebar-pos-option', panel).val(App.isRTL() ? 'right' : 'left');
		}

		//handle theme layout
		var resetLayout = function() {

			$("body").removeClass("page-boxed").removeClass("page-footer-fixed").removeClass("page-sidebar-fixed").removeClass("page-header-fixed").removeClass("page-sidebar-reversed");

			$('.page-header > .page-header-inner').removeClass("container");

			if ($('.page-container').parent(".container").size() === 1) {
				$('.page-container').insertAfter('body >.page-wrapper > .clearfix');
			}

			if ($('.page-footer > .container').size() === 1) {
				$('.page-footer').html($('.page-footer > .container').html());
			} else if ($('.page-footer').parent(".container").size() === 1) {
				$('.page-footer').insertAfter('.page-container');
				$('.scroll-to-top').insertAfter('.page-footer');
			}

			$(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");

			$('body > .page-wrapper > .container').remove();
		};

		var lastSelectedLayout = '';

		var setLayout = function() {

			var layoutOption = $('#layout-option').val();
			var sidebarOption = $('#sidebar-option').val();
			var headerOption = $('#page-header-option').val();
			var footerOption = $('#page-footer-option').val();
			var sidebarPosOption = $('#sidebar-pos-option').val();
			var sidebarStyleOption = $('#sidebar-style-option').val();
			var sidebarMenuOption = $('#sidebar-menu-option').val();
			var headerTopDropdownStyle = $('#page-header-top-dropdown-style-option').val();
			if (sidebarOption == "fixed" && headerOption == "default") {
				alert('不支持浮动边栏选项的默认页眉。用浮动的侧边栏浮动标题。');
				$('#page-header-option').val("fixed");
				$('#sidebar-option').val("fixed");
				sidebarOption = 'fixed';
				headerOption = 'fixed';
			}

			resetLayout(); // reset layout to default state

			if (layoutOption === "boxed") {
				$("body").addClass("page-boxed");

				// set header
				$('.page-header > .page-header-inner').addClass("container");
				var cont = $('body > .page-wrapper > .clearfix').after('<div class="container"></div>');

				// set content
				$('.page-container').appendTo('body > .page-wrapper > .container');

				// set footer
				if (footerOption === 'fixed') {
					$('.page-footer').html('<div class="container">' + $('.page-footer').html() + '</div>');
				} else {
					$('.page-footer').appendTo('body > .page-wrapper > .container');
				}
			}

			if (lastSelectedLayout != layoutOption) {
				//layout changed, run responsive handler: 
				App.runResizeHandlers();
			}
			lastSelectedLayout = layoutOption;

			//header
			if (headerOption === 'fixed') {
				$("body").addClass("page-header-fixed");
				$(".page-header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
			} else {
				$("body").removeClass("page-header-fixed");
				$(".page-header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
			}

			//sidebar
			if ($('body').hasClass('page-full-width') === false) {
				if (sidebarOption === 'fixed') {
					$("body").addClass("page-sidebar-fixed");
					$("page-sidebar-menu").addClass("page-sidebar-menu-fixed");
					$("page-sidebar-menu").removeClass("page-sidebar-menu-default");
					Layout.initFixedSidebarHoverEffect();
				} else {
					$("body").removeClass("page-sidebar-fixed");
					$("page-sidebar-menu").addClass("page-sidebar-menu-default");
					$("page-sidebar-menu").removeClass("page-sidebar-menu-fixed");
					$('.page-sidebar-menu').unbind('mouseenter').unbind('mouseleave');
				}
			}

			// top dropdown style
			if (headerTopDropdownStyle === 'dark') {
				$(".top-menu > .navbar-nav > li.dropdown").addClass("dropdown-dark");
			} else {
				$(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");
			}

			//footer 
			if (footerOption === 'fixed') {
				$("body").addClass("page-footer-fixed");
			} else {
				$("body").removeClass("page-footer-fixed");
			}

			//sidebar style
			if (sidebarStyleOption === 'light') {
				$(".page-sidebar-menu").addClass("page-sidebar-menu-light");
			} else {
				$(".page-sidebar-menu").removeClass("page-sidebar-menu-light");
			}

			//sidebar menu 
			if (sidebarMenuOption === 'hover') {
				if (sidebarOption == 'fixed') {
					$('#sidebar-menu-option').val("accordion");
					alert("悬停边栏菜单与浮动侧栏模式不兼容。选择默认侧栏模式。");
				} else {
					$(".page-sidebar-menu").addClass("page-sidebar-menu-hover-submenu");
				}
			} else {
				$(".page-sidebar-menu").removeClass("page-sidebar-menu-hover-submenu");
			}

			//sidebar position
			if (App.isRTL()) {
				if (sidebarPosOption === 'left') {
					$("body").addClass("page-sidebar-reversed");
					$('#frontend-link').tooltip('destroy').tooltip({
						placement : 'right'
					});
				} else {
					$("body").removeClass("page-sidebar-reversed");
					$('#frontend-link').tooltip('destroy').tooltip({
						placement : 'left'
					});
				}
			} else {
				if (sidebarPosOption === 'right') {
					$("body").addClass("page-sidebar-reversed");
					$('#frontend-link').tooltip('destroy').tooltip({
						placement : 'left'
					});
				} else {
					$("body").removeClass("page-sidebar-reversed");
					$('#frontend-link').tooltip('destroy').tooltip({
						placement : 'right'
					});
				}
			}

			Layout.fixContentHeight(); // fix content height            
			Layout.initFixedSidebar(); // reinitialize fixed sidebar

		};

		// handle theme colors modify by Sunc
		var setColor = function(color) {
			var color_ = (App.isRTL() ? color + '-rtl' : color);
			$('#style_color').attr("href", "${staticServer}/" + Layout.getLayoutCssPath() + 'themes/' + color_ + ".min.css");
		};

		$('.theme-colors > ul > li', panel).click(function() {
			var color = $(this).attr("data-style");
			setColor(color);
			$('ul > li', panel).removeClass("current");
			$(this).addClass("current");
		});

		// set default theme options:

		if ($("body").hasClass("page-boxed")) {
			$('#layout-option').val("boxed");
		}

		if ($("body").hasClass("page-sidebar-fixed")) {
			$('#sidebar-option').val("fixed");
		}

		if ($("body").hasClass("page-header-fixed")) {
			$('#page-header-option').val("fixed");
		}

		if ($("body").hasClass("page-footer-fixed")) {
			$('#page-footer-option').val("fixed");
		}

		if ($("body").hasClass("page-sidebar-reversed")) {
			$('#sidebar-pos-option').val("right");
		}

		if ($(".page-sidebar-menu").hasClass("page-sidebar-menu-light")) {
			$('#sidebar-style-option').val("light");
		}

		if ($(".page-sidebar-menu").hasClass("page-sidebar-menu-hover-submenu")) {
			$('#sidebar-menu-option').val("hover");
		}
		$('#layout-option, #page-header-option, #page-header-top-dropdown-style-option, #sidebar-option, #page-footer-option, #sidebar-pos-option, #sidebar-style-option, #sidebar-menu-option')
				.change(setLayout);
	};
	if ("${themeStyle}" === "1") {
		handleTheme();

	}
	$(function() {
		//切换为横向菜单
		$("#horizontalMenu").click(function() {
			window.location.href = "${dynamicServer}/themeOfHorizontal.htm";
		});

		//切换为纵向菜单
		$("#verticalMenu").click(function() {
			window.location.href = "${dynamicServer}/themeOfVertical.htm";
		});
	});
</script></critc-script>