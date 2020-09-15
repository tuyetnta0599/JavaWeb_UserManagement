<%-- 
    Document   : createUser
    Created on : Aug 22, 2020, 2:56:15 PM
    Author     : tuyet
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User Page</title>
    </head>
    <body>
        <h1>Create User</h1>
        <font color="red">
        <h1>Welcome, ${sessionScope.USER.fullname}</h1>
        </font><br/>
        <c:if test="${empty sessionScope.USER or sessionScope.USER.role.name ne 'admin'}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <form action="DispatcherController" method="POST">
            <input type="submit" value="Logout" name="btnAction" />
        </form><br/>

        <c:if test="${not empty requestScope.CREATED_FAILED}">
            <font color="red">
            ${requestScope.CREATED_FAILED}
            </font>
        </c:if>
        <c:if test="${not empty requestScope.CREATED}">
            <font color="green">
            ${requestScope.CREATED}
            </font>
        </c:if>
        <form action="DispatcherController" method="POST">
            <c:if test="${not empty requestScope.MSG_ERROR}">
                <font color="red">
                <p>${requestScope.MSG_ERROR}</p>
                </font>
            </c:if><br/>
            Username : <input type="text" name="txtId" value="${param.txtId}" /><br/>
            Password : <input type="password" name="txtPassword" value="" /><br/>
            Confirm  : <input type="password" name="txtConfirm" value="" /><br/>
            Email    : <input type="email" name="txtEmail" value="${param.txtEmail}" /><br/>
            Fullname : <input type="text" name="txtFullname" value="${param.txtFullname}" /><br/>
            Phone    : <input type="text" name="txtPhone" value="${param.txtPhone}" /><br/>
            Photo    : <input type="text" name="txtPhoto" value="${param.txtPhoto}" /><br/>
            <input type="submit" value="Create" name="btnAction" />
            <input type="submit" value="Back" name="btnAction" /><br/>
        </form>

    </body>
</html>
