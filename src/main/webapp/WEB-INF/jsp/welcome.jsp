<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Fishing</title>
</head>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>Psaroapp</h3>
</a>

<a href="http://localhost:1684/new">Create new contest</a><br>

<c:forEach items="${contests}" var="contest">
    <tr>
        <td>
            <a href='contest/${contest.id}'>${contest.contestDate}</a>

        </td>
    </tr>
    <br>
</c:forEach>
</body>
</html>