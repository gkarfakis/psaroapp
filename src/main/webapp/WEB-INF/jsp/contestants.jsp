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
    <title>Contestants</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        #contestants {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
        }

        #contestants td, #contestants th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #contestants tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #contestants tr:hover {
            background-color: #ddd;
        }

        #contestants th {
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
                <div class="col-sm-4" style="border-style: groove">
                    <form action="http://localhost:1684/contestant" method="post">
                        <input type="hidden" name="contestId" value=${contest.id}>
                        <label>Club:</label>
                        <select name="clubId">
                            <c:forEach items="${clubs}" var="club">
                                <option value="${club.id}">
                                        ${club.clubName}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Add Club">
                    </form>
                </div>
            </div>
        </div>
        <div class="table-title">
            <div class="row">
                <div class="col-sm-4" style="border-style: groove">
                    <form action="http://localhost:1684/contestant" method="post">
                        <input type="hidden" name="contestId" value=${contest.id}>
                        <label>Name:</label>
                        <select name="fishermanId">
                            <c:forEach items="${fishermen}" var="fisherman">
                                <option value="${fisherman.id}">
                                        ${fisherman.fishermanSurName} ${fisherman.fishermanName}
                                </option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Add Contestant">
                    </form>
                </div>
            </div>
        </div>
        <h2>${totalNum} Contestants</h2>
        <div style="height:80%; overflow:auto">

            <table class="table table-bordered" id="contestants">
                <tr>
                    <th>Id</th>
                    <th>Club</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Seniority</th>
                    <th>Position</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${contestants}" var="contestant">
                    <tr>
                        <td>${contestant.id}</td>
                        <td>${contestant.club.clubName}</td>
                        <td>${contestant.fisherman.fishermanName}</td>
                        <td>${contestant.fisherman.fishermanSurName}</td>
                        <td>${contestant.seniority}</td>
                        <td>${contestant.position}</td>
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
    <form action="http://localhost:1684/sectorize" method="post">
        <input type="hidden" name="contestId" value=${contest.id}>
        <label for="positionsNum">Contestants number per sector:</label><br>
        <input type="text" id="positionsNum" name="positionsNum"><br>
        <input type="submit" value="Calculate sectors">
    </form>
    <div class="col-sm-8"><h2>${sectorsNum} sectors of ${positionsNum} positions</h2></div>
    <div class="col-sm-8"><h2>and one technical sector of ${smallSectorPositions} positions</h2></div>


    <form action="http://localhost:1684/lottery" method="post">
        <input type="hidden" name="sectorsNum" value=${sectorsNum}>
        <input type="hidden" name="positionsNum" value=${positionsNum}>
        <input type="hidden" name="contestId" value=${contest.id}>

        <input type="submit" value="Arrange positions">
    </form>
    <div class="col-sm-8"><h2>${sectorsNum} sectors of ${positionsNum} positions</h2></div>
    <div class="col-sm-8"><h2>and one sector of ${smallSectorPositions} positions</h2></div>

</div>
</body>
</html>