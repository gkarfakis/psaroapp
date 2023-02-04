<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Edit Form</title>
</head>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>
    Psaroapp</h3></a>

<h1>Edit Fisherman</h1>
<form action="http://localhost:1684/fisherman" method="put">
    <input type="hidden" name="fishermanId" value=${fisherman.id}>
    <table>
        <tr>
            <td>Club <strong style="color: red">(Not auto-selected!)</strong> :</td>
            <td>
                <select name="clubId">
                    <c:forEach items="${clubs}" var="club">
                        <option value="${club.id}" selected=${fisherman.club.clubName}>
                                ${club.clubName}
                        </option>
                    </c:forEach>
                </select>
        <tr>
            <td>Name:</td>
            <td>
                <input type="text" name="fishermanName" value=${fisherman.fishermanName}></td>
        </tr>
        <tr>
            <td>Surname:</td>
            <td>
                <input type="text" name="fishermanSurName" value=${fisherman.fishermanSurName}></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Save"/></td>
        </tr>
    </table>
</form>

</body>
</html>