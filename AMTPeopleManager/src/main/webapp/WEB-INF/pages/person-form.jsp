<%--
  Created by IntelliJ IDEA.
  User: Thibaud Besseau
  Date: 24.09.2017
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>SB Admin - Start Bootstrap Template</title>
  <!-- Bootstrap core CSS-->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">
</head>

<body class="bg-dark">
  <div class="container">
    <div class="card card-register mx-auto mt-5">
      <div class="card-header">Person informations</div>
      <div class="card-body">
        <c:choose>
          <c:when test="${action=='edit'}">
            <form action="person-action?action=${action}&id=${id}" method=POST>
          </c:when>
          <c:otherwise>
            <form action="person-action?action=${action}" method=POST>
          </c:otherwise>
        </c:choose>
          <div class="form-group">
            <label for="genderUser">Gender</label>
              <select class="form-control" name="genderUser" id="Gender" type="text" value="<c:out value="${param.genderUser}"/>" required="true">
                  <option value="${genderList[0]}">${genderList[0]}</option>
                <c:choose>
                <c:when test="${person.gender=='Women'}">
                  <option selected value="${genderList[1]}">${genderList[1]}</option>
                </c:when>
                <c:otherwise>
                  <option value="${genderList[1]}">${genderList[1]}</option>
                </c:otherwise>
                </c:choose>
              </select>
            <span class="error">${form.errors['genderUser']}</span>
              <br>

            <label for="firstNameUser">First Name</label>
            <input class="form-control" name="firstNameUser" id="firstName" type="text" value="<c:out value="${person.firstName}${param.firstNameUser}"/>"  aria-describedby="Number User" placeholder="First Name" required="true">
            <span class="error">${form.errors['firstNameUser']}</span>
              <br>


            <label for="lastNameUser">Last Name</label>
            <input class="form-control" name="lastNameUser" id="lastName" type="text" value="<c:out value="${person.lastName}${param.lastNameUser}"/>"  aria-describedby="Number User" placeholder="Last Name" required="true">
            <span class="error">${form.errors['lastNameUser']}</span>
              <br>

            <label for="birthdayUser">Birthday</label>
            <input class="form-control" name="birthdayUser" id="birthday" type="date" value="<c:out value="${person.birthday}${param.birthdayUser}"/>"  aria-describedby="Number User" placeholder="Birthday" required="true">
            <span class="error">${form.errors['birthdayUser']}</span>
              <br>

            <label for="emailUser">Email</label>
            <input class="form-control" name="emailUser" id="email" type="email" value="<c:out value="${person.email}${param.emailUser}"/>"  aria-describedby="Number User" placeholder="Email" required="true">
            <span class="error">${form.errors['emailUser']}</span>
              <br>

            <label for="phoneUser">Phone</label>
            <input class="form-control" name="phoneUser" id="phone" type="text" value="<c:out value="${person.phone}${param.phoneUser}"/>" aria-describedby="Number User" placeholder="Phone" required="true">
            <span class="error">${form.errors['phoneUser']}</span>
              <br>

          </div>
            <button type="submit"  class="btn btn-primary btn-success btn-block">Save</button>
          <a class="btn btn-primary btn-danger btn-block" href="people-list">Cancel</a>
        </form>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/popper/popper.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
</body>

</html>
