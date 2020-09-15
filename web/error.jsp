<%-- 
    Document   : error
    Created on : Aug 19, 2020, 3:53:33 PM
    Author     : tuyet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <font color="red"> ${requestScope.ERROR}</font>
    </body>
</html>
