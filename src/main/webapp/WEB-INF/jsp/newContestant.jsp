<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>New Contest Form</title>
</head>
<style>
    #contestants  {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    #contestants td, #contestants th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    #contestants tr:nth-child(even) {
        background-color: #dddddd;
    }
</style>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>Psaroapp</h3></a>

<h1>${contest.contestDate}</h1>

<form action="http://localhost:1684/contestant" method="post">
    <input type="hidden"  name="contestId" value=${contest.id}>
    <table>
        <tr>
            <td>Club</td>
            <td>
                <select name="clubId">
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
                <select name="fishermanId">
                    <c:forEach items="${fishermen}" var="fisherman">
                        <option value="${fisherman.id}">
                                ${fisherman.fishermanName}  ${fisherman.fishermanSurName}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>

    </table>
    <input type="submit" value="Add contestant"/></form>

<table id="contestants">
    <tr>
        <th>Contest</th>
        <th>Club</th>
        <th>Fisherman</th>
    </tr>
    <c:forEach items="${contestants}" var="contestant">
        <tr>
            <td>${contestant.contest.contestDate}</td>
            <td>${contestant.club.clubName}</td>
            <td>${contestant.fisherman.fishermanName}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>