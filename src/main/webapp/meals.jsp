<%--
  Created by IntelliJ IDEA.
  User: Blynchik
  Date: 09.10.2022
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>Meals</title>
</head>
<body>
<table border="1">
    <caption>Meals</caption>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
        <c:forEach items="${meals}" var="mealTo">
    <tr style="color:${mealTo.excess ? 'red' : 'green'}">
        <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" var="dateTime"/>
        <td><c:out value="${dateTime}"/><br/></td>
        <td><c:out value="${mealTo.description}"/><br/></td>
        <td><c:out value="${mealTo.calories}"/><br/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
