<%-- 
    Document   : promotion
    Created on : Aug 23, 2020, 4:46:59 PM
    Author     : tuyet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promotion List</title>
    </head>
    <body>
        <font color="red">
        <h1>Welcome, ${sessionScope.USER.fullname}</h1>
        </font>
        <c:if test="${empty sessionScope.USER or sessionScope.USER.role.name ne 'admin'}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <form action="DispatcherController" method="POST">
            <input type="submit" value="Logout" name="btnAction" />
        </form><br/>

        <c:if test="${ not empty requestScope.MSG_ERROR}">
            <font color="red">
            ${requestScope.MSG_ERROR}
            </font>
        </c:if>
        <c:if test="${ not empty requestScope.MSG_SUCCESS}">
            <font color="green">
            ${requestScope.MSG_SUCCESS}
            </font>
        </c:if>    
        <c:if test="${not empty sessionScope.CART.cart}">
            <table border='1'>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>UserID</th>
                        <th>Rank</th>
                        <th>Update</th>
                        <th>Delete</th>
                        <th>Confirm</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${sessionScope.CART.cart}" varStatus="Counter">
                        <tr>
                    <form action="DispatcherController" method="POST">
                        <td>
                            ${Counter.count}
                        </td>
                        <td>
                            ${dto.value.user.id}
                            <input type="hidden" name="txtUserID" value="${dto.value.user.id}" />
                        </td>
                        <td>
                            <input type="number" min="1" max="10" name="txtRank" value="${dto.value.rank}" />
                        </td>
                        <td>
                            <input type="submit" value="Update Rank" name="btnAction" />
                        </td>
                    </form>
                    <td>
                        <form action="DispatcherController" method="POST">
                            <input type="hidden" name="txtUserID" value="${dto.value.user.id}" />
                            <input type="submit" value="Delete Rank" name="btnAction" />
                        </form>
                    </td>
                    <td>
                        <form action="DispatcherController" method="POST">
                            <input type="hidden" name="txtUserID" value="${dto.value.user.id}" />
                            <input type="hidden" min="1" max="10" name="txtRank" value="${dto.value.rank}" />
                            <input type="submit" value="Confirm" name="btnAction" />

                        </form>
                    </td>
                </tr>  
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty sessionScope.CART.cart}">
    <h2>No Result!!</h2>
</c:if>
<form action="DispatcherController" method="POST">
    <input type="submit" value="Back" name="btnAction" />
</form>
</body>

</html>
