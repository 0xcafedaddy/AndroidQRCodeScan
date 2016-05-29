<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">


	<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
   <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
   <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

  </head>
  
  
<body>

<table class="table table-striped">
   <thead>
      <tr>
         <th>名称</th>
         <th>信息</th>
      </tr>
   </thead>
   <tbody>
      <tr >
         <td>编号</td>
         <td><c:out value="${sessionScope.product.mid }"></c:out></td>
      </tr>
      <tr >
         <td>名称</td>
         <td><c:out value="${sessionScope.product.nam }"></c:out></td>
      </tr>
      <tr >
         <td>生产日期</td>
         <td><c:out value="${sessionScope.product.pdString }"></c:out></td>
      </tr>
      <tr >
         <td>截止日期</td>
         <td><c:out value="${sessionScope.product.gdString }"></c:out></td>
      </tr>
      <tr >
         <td>近一次维修记录</td>
         <td><c:out value="${sessionScope.product.recordString }"></c:out></td>
      </tr>
      
       <tr >
         <td>维修次数</td>
         <td><c:out value="${sessionScope.product.frequency }"></c:out></td>
      </tr>
      
       <tr >
         <td>公司</td>
         <td><c:out value="${sessionScope.product.company }"></c:out></td>
      </tr>
      
       <tr >
         <td>联系方式</td>
         <td><c:out value="${sessionScope.product.phone }"></c:out></td>
      </tr>
      
       <tr >
         <td>价格</td>
         <td><c:out value="${sessionScope.product.price }"></c:out></td>
      </tr>
      
   </tbody>
</table>
</body>
  
</html>
