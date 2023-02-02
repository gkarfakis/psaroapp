<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Fishing fishermen</title>
</head>
<style>
    #fishermen {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    #fishermen td, #fishermen th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    #fishermen tr:nth-child(even) {
        background-color: #dddddd;
    }
</style>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fishing Fishermen</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        #fishermen {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #fishermen td, #fishermen th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #fishermen tr:nth-child(even){background-color: #f2f2f2;}

        #fishermen tr:hover {background-color: #ddd;}

        #fishermen th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>Psaroapp</h3>
</a>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-8"><h2>Fishermen</h2></div>
                <div class="col-sm-4">
                    <form action="http://localhost:1684/fisherman" method="post">
                        <label>Name:</label><br>
                        <input type="text" name="name" required><br>
                        <label>Surname:</label><br>
                        <input type="text" name="surname" required><br>
                        <input type="submit" value="Add new Fisherman">
                    </form>
                </div>
            </div>
        </div>

        <table class="table table-bordered" id="fishermen">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${fishermen}" var="fisherman">
                <tr>
                    <td>${fisherman.id}</td>
                    <td>${fisherman.fishermanName}</td>
                    <td>${fisherman.fishermanSurName}</td>
                    <td>
                        <form action="http://localhost:1684/deleteFisherman" method="post">
                            <button type="submit">
                                <i class="fa fa-trash"></i>
                            </button>
                            <input type="hidden" name="fishermanId" value=${fisherman.id}>
                        </form>
                    </td>
                </tr>
            </c:forEach>

        </table>

    </div>
</div>
</body>
</html>