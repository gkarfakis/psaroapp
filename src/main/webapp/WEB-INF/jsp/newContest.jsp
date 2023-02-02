<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>New Contest Form</title>
</head>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>Ψάρεμα</h3></a>

<h1>${contest.contestDate}</h1>
<form action="http://localhost:1684/contestant" method="post">
    <input type="hidden"  name="contestId" value=${contest.id}>
    <table style="with: 50%">
        <tr>
            <td>Club</td>
            <td>
                <select  type="text"name="clubId">
                    <c:forEach items="${clubs}" var="club">
                        <option value="${club.id}">
                                ${club.clubName}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Fisherman</td>
            <td>
                <select  type="text" name="fishermanId">
                    <c:forEach items="${fishermen}" var="fisherman">
                        <option value="${fisherman.id}">
                                ${fisherman.fishermanName}  ${fisherman.fishermanSurName}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>

    </table>
    <input type="submit" value="add"/></form>
</body>
</html>