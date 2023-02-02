
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>New Contest Form</title>
</head>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>Psaroapp</h3></a>
<h2>Insert date in the format of 31/01/2022</h2>
<form action="http://localhost:1684/contest" method="post">
    <table style="with: 50%">
        <tr>
            <td>Date</td>
            <td><input type="text" name="date"  placeholder="dd/MM/yyyy" required/></td>

        </tr>
    </table>
    <input type="submit" value="Create" /></form>
</body>
</html>