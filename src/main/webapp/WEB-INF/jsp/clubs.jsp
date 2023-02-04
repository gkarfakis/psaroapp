<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clubs</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        #clubs {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
        }

        #clubs td, #clubs th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #clubs tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #clubs tr:hover {
            background-color: #ddd;
        }

        #clubs th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<a class="navbar-brand" href="http://localhost:1684/home" style="color:orange; font-family: 'Barriecito', cursive;"><h3>
    Psaroapp</h3>
</a>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-8"><h2>Clubs</h2></div>
                <div class="col-sm-4" style="border-style: groove">
                    <form action="http://localhost:1684/club" method="post">
                        <label>Name:</label>
                        <input type="text" name="clubName" required><br>
                        <input type="submit" value="Add club">
                    </form>
                </div>
            </div>
        </div>

        <div style="height:80%; overflow:auto">

            <table class="table table-bordered" id="clubs">
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${clubs}" var="club">
                    <tr>
                        <td>${club.id}</td>
                        <td>${club.clubName}</td>
                        <td>
                            <form action="http://localhost:1684/editClub" method="get">
                                <button type="submit">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <input type="hidden" name="clubId" value=${club.id}>
                            </form>
                        </td>
                        <td>
                            <form action="http://localhost:1684/deleteClub" method="post">
                                <button type="submit">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <input type="hidden" name="clubId" value=${club.id}>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <h2 style="color: red">${message}</h2>
            </table>

        </div>
    </div>
</div>
</body>
</html>