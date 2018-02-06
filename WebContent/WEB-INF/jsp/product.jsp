<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product ${it.id} info</title>
</head>
<body>
	<c:choose>
		<c:when test="${it == null}">
			<h1>The item was not found</h1>
		</c:when>
		<c:otherwise>
			<h1>Product ${it.id}:</h1>
			<table border="1">
				<tr>
					<td>name</td><td>${it.name}</td>
				</tr>
				<tr>
					<td>price</td><td>${it.price}</td>
				</tr>
				<tr>
					<td>status</td>
					<td>
						<c:if test="${it.in_stock == false}">
							not
						</c:if>
						in stock
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>