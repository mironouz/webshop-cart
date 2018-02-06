<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All products</title>
</head>
<body>
	<h1>All products: </h1>
	<table border="1">
		<c:forEach var="item" items="${it.cart}">
			<tr>
			<td>${item}</td>
			<td><a href="products/${item.id}">show item</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>