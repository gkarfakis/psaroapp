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

<h1>Edit Contest</h1>
<form action="http://localhost:1684/contest" method="put">
    <input type="hidden" name="contestId" value=${contest.id}>
    <table>
        <td>Date:</td>
        <td>
            <input type="text" name="date" value=${date}></td>
        </tr>
        <td>Description:</td>
        <td>
            <input type="text" name="description" value=${contest.description}></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Save"/></td>
        </tr>
    </table>
</form>

</body>
</html>