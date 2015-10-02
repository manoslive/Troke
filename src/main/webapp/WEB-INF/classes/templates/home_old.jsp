<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">

    <title>Liste d'Usagers</title>
</head>
<body>
<div align="center">
    <h1>Liste d'Usagers</h1>
    <h3><a href="newContact">Nouvel Usager</a></h3>
    <table border="1" style="text-align:center;">
        <th class="ListeID">ID</th>
        <th style="text-align:center;">Prenom</th>
        <th style="text-align:center;">Nom</th>
        <th style="text-align:center;">Photo</th>
        <th style="text-align:center;">Action</th>

        <c:forEach var="user" items="${listContact}" varStatus="status">
            <tr>
                <td class="ListeID">${status.index + 1}</td>
                <td>${user.firstname}</td>
                <td>${user.email}</td>
                <td><img src="${contact.photo}" class="img-thumbnail" style="width:100px; height:100px;"></td>

                <td>
                    <a href="editContact?id=${contact.id}">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="deleteContact?id=${contact.id}">Delete</a>
                </td>

            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>