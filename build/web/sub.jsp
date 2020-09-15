<%-- 
    Document   : sub
    Created on : Aug 19, 2020, 3:06:56 PM
    Author     : tuyet
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <font color="red">
        <h1>Welcome, ${sessionScope.USER.fullname}</h1>
        </font><br/>
        <form action="DispatcherController" method="POST">
            <input type="submit" value="Logout" name="btnAction" />
        </form><br/>
        <c:if test="${empty sessionScope.USER or sessionScope.USER.role.name ne 'sub'}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <h3>${sessionScope.USER.fullname}'s Profile  </h3>
        <c:if test="${not empty requestScope.PROFILE}">
            <form action="DispatcherController" method="POST">
                ID : <input type="text" name="txtID" value="${requestScope.PROFILE.id}" readonly/><br/>
                Password : <input type="password" name="txtPassword" value="${requestScope.PROFILE.password}"/><br/>
                Email : <input type="email" name="txtEmail" value="${requestScope.PROFILE.email}"/><br/>
                Fullname : <input type="text" name="txtFullname" value="${requestScope.PROFILE.fullname}"/><br/>
                Phone : <input type="text" name="txtPhone" value="${requestScope.PROFILE.phone}"/><br/>
                Photo : <input type="text" name="txtPhoto" value="${requestScope.PROFILE.photo}" /><br/>
                Role : <input type="text" name="txtRole" value="${requestScope.PROFILE.role.name}" readonly/><br/>
            </form>
        </c:if>
        <c:if test="${empty requestScope.PROFILE}">
            <h1>Not found</h1>
        </c:if>

    </body>
</html>
