<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All items</title>
</head>
<body>
	<c:choose>
		<c:when test="${it.cart.size() == 0}">
			<h1>There is no items</h1>
		</c:when>
		<c:otherwise>
			<h1>All items: </h1>
			<p><a href="" onClick="fetch('products', {
					    method: 'delete'
					    })">delete all</a></p>
			<table border="1">
				<c:forEach var="item" items="${it.cart}">
					<tr>
					<td>${item}</td>
					<td><a href="products/${item.id}">show item</a></td>
					<!--  That is not good, just for testing DELETE requet -->
					<!-- It doesn't work in all browsers -->
					<td><a href="" onClick="fetch('products/' + ${item.id}, {
					    method: 'delete'
					    })">delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<p>Create new item:</p>
	<form action="products" method="post">
		name
		<input type="text" name="name" />
		price
		<input type="text" name="price" />
		<input type="submit" value="create" />
	</form>
</body>
</html>