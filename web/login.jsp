<%-- 
    Document   : login
    Created on : Aug 19, 2020, 3:39:22 PM
    Author     : tuyet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <font color="red">${requestScope.MSG_ERROR}</font>
        <form action="DispatcherController" method="POST">
            Username: <input type="text" name="txtUsername" value="${param.txtUsername}" /><br/>
            Password: <input type="password" name="txtPassword" /><br/>
            <input type="submit" value="Login" name="btnAction" />
        </form>
    </body>
</html>
