<%@page import="com.critc.sys.service.SysOnlineUserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = "E:\\debug\\zhongtx\\cdpf_v1\\cdpf_example_web\\src\\main\\webapp\\";
	//String rootPath = request.getServletContext().getRealPath("/"); 
    System.out.println(rootPath);
	out.write( new ActionEnter( request, rootPath ).exec() );
	
	//新加的代码
	String action = request.getParameter("action");  
	String result = new ActionEnter( request, rootPath ).exec();  
	if( action!=null &&   
	   (action.equals("listfile") || action.equals("listimage") ) ){  
	    rootPath = rootPath.replace("\\", "/");  
	    result = result.replaceAll(rootPath, "/");  
	}  

	out.write( result );  
	
%>