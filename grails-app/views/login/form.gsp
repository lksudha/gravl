<%--
  Created by IntelliJ IDEA.
  User: glen
  Date: Nov 16, 2007
  Time: 10:59:26 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Login Form</title>
        <meta name="layout" content="main"/>
    </head>
    <body>

        <form action="<g:createLinkTo dir="${blog}/admin/login/login"/>" method="POST">

            username: <input name="userId"/>
            password: <input name="password" type="password"/>
            <input type="submit" value="login"/>

        </form>
    </body>
</html>