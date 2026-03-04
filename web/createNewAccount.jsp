<%-- 
    Document   : createNewAccount
    Created on : Mar 2, 2026, 3:39:24 PM
    Author     : Quoc Thai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create</title>
    </head>
    <body>
        <div>Create New Account</div>
        <form action="MainController" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERRORS}"/>
            Username* <input type="text" name="txtUsername" 
                             value="${param.txtUsername}" />(e.g from 6 to 20 chars)<br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                ${errors.usernameLengthErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red">
                ${errors.usernameIsExisted}
                </font><br/>
            </c:if>    
            Password* <input type="password" name="txtPassword" value="" />(e.g from 6 to 30 chars)<br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}
                </font><br/>
            </c:if>
            Confirm* <input type="password" name="txtConfirm" value="" /><br/>
            <c:if test="${not empty errors.confirmNoMatchErr}">
                <font color="red">
                ${errors.confirmNoMatchErr}
                </font><br/>
            </c:if>
                Full name* <input type="text" name="txtFullname" value="${param.txtFullname}" />(e.g from 6 to 50 chars)<br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                ${errors.fullnameLengthErr}
                </font><br/>
            </c:if>
            <input type="submit" value="Create New Account" name="action" />
            <input type="reset" value="Reset" />
        </form>
    </body>
</html>
