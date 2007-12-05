<%--
  Created by IntelliJ IDEA.
  User: glen
  Date: Dec 4, 2007
  Time: 1:15:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head><title>Upload blog</title>
        <meta name="layout" content="main"/></head>
    <body>
        <g:form action="fileUpload" enctype="multipart/form-data">
            <input type="file" name="zipfile"/>
            <g:submitButton name="upload" value="Upload"/>
            <g:submitButton name="cancel" value="Cancel"/>
        </g:form>
    </body>
</html>