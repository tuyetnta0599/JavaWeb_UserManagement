<%-- 
    Document   : admin
    Created on : Aug 19, 2020, 3:06:50 PM
    Author     : tuyet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
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

        <a href="createUser.jsp" style="text-decoration : none"><h3>Create User</h3></a>

        <form action="DispatcherController" method="POST">
            <input type="submit" value="Promotion" name="btnAction" />
        </form><br/>

        <br/><form action="DispatcherController" method="GET">
            <label for="roles">Choose role:</label>
            <select name="roles" id="roles">
                <option value="all" <c:if test="${param.roles eq 'all'}">selected</c:if>>All</option>
                <option value="admin" <c:if test="${param.roles eq 'admin'}">selected</c:if>>Admin</option>
                <option value="sub" <c:if test="${param.roles eq 'sub'}">selected</c:if>>Sub</option>
                </select><br/> 
                Search: <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" />
            <input type="submit" value="Search" name="btnAction" />
        </form><br/>

        <br/>
        <br/>
        <font color="green">
        ${requestScope.MSG_SUCCESS}
        </font>

        <font color="red">
        ${requestScope.MSG_ERROR}
        </font>

        <c:if test="${not empty requestScope.SEARCH_RESULT}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Email</th>
                        <th>Fullname</th>
                        <th>Phone</th>
                        <th>Photo</th>
                        <th>Role</th>
                        <th>Update</th>
                        <th>Delete</th>
                        <th>Add Rank</th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach var="dto" items="${requestScope.SEARCH_RESULT}" varStatus="Counter">
                        <tr>
                    <form action="DispatcherController" method="POST">
                        <td>
                            ${Counter.count}
                        </td>
                        <td>
                            ${dto.id}
                            <input type="hidden" name="txtId" value="${dto.id}" />
                        </td>
                        <td>
                            <input type="password" name="txtPassword" value="" />
                        </td>
                        <td>
                            <input type="email" name="txtEmail" value="${dto.email}" />
                        </td>
                        <td>
                            <input type="text" name="txtFullname" value="${dto.fullname}" />
                        </td>

                        <td>
                            <input type="text" name="txtPhone" value="${dto.phone}" />
                        </td>

                        <td>
                            <input type="text" name="txtPhoto" value="${dto.photo}" />
                        </td>

                        <td>
                            <input type="checkbox" name="isAdmin" value="ON" 
                                   <c:if test="${dto.role.name eq 'admin'}">
                                       checked="checked"
                                   </c:if>
                                   />
                        </td>  
                        <td>
                            <input type="hidden" name="roles" value="${param.roles}" />
                            <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}" />
                            <input type="submit" value="Update" name="btnAction" />
                        </td>
                    </form>
                    <td>
                        <form action="DispatcherController" method="POST">
                            <input type="hidden" name="txtId" value="${dto.id}" />
                            <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}" />
                            <input type="hidden" name="roles" value="${param.roles}" />
                            <input type="submit" value="Delete" name="btnAction" />
                        </form>
                    </td>
                    <td>
                        <form action="DispatcherController" method="POST">
                            <input type="hidden" name="txtId" value="${dto.id}" />
                            <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}" />
                            <input type="hidden" name="roles" value="${param.roles}" />
                            <input type="hidden" name="txtRole" value="${dto.role.name}" />
                            <input type="submit" value="Add Rank" name="btnAction" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty SEARCH_RESULT}">
    <h2>No Result!!</h2>
</c:if>
</body>
</html>
