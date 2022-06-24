<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login via OAuth2</h2>
    <form action="<c:url value="/login"/>" method="post">
        <div>
            <label for="username">Username</label><input type="text" id="username" name="username">
        </div>
        <div>
            <label for="password">Password</label><input type="password" id="password" name="password">
        </div>
        <input type="submit" id="btn-login" value="Login" />
    </form>
</body>
</html>
