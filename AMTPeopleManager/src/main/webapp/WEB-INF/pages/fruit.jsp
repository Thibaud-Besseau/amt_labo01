<%--
  Created by IntelliJ IDEA.
  User: Thibaud Besseau
  Date: 24.09.2017
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
    <title>JSP PAGE</title>
</head>
<body>
    <h1>Hello Fruit!</h1>
    ${requestScope.theFruit.name} is ${requestScope.theFruit.color}
</body>
</html>
