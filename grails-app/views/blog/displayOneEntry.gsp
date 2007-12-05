<%--
  Created by IntelliJ IDEA.
  User: glen
  Date: Nov 19, 2007
  Time: 1:24:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Simple GSP page</title>
      <meta name="layout" content="main"/>
  </head>
  <body>
      <g:each var="entry" in="${entries}">

          <div class="blogentry">
              <div class="blogtitle">${entry.title}</div>
              <div class="blogdate">${entry.created}</div>
              <div class="blogbody">${entry.body.encodeAsWiki()}</div>
          </div>

      </g:each>
  </body>
</html>