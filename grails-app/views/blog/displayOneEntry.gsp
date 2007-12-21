<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>gravl</title>
      <g:if test="${print==true}">
        <meta name="layout" content="print"/>
      </g:if>
      <g:else>
        <meta name="layout" content="main"/>
      </g:else>
  </head>
  <body>
      <g:each var="entry" in="${entries}">

          <div class="blogentry">
              <div class="blogtitle"><a href="${baseUri + entry.toPermalink()}">${entry.title}</a></div>
              <div class="blogdate"><g:niceDate date="${entry.created}"/></div>
              <g:if test="${print!=true}">
                 <g:link controller="pdf" action="show" params="[url: entry.toPermalink()]">PDF</g:link>
              </g:if>
              <div class="blogbody">${entry.body}</div>
          </div>

      </g:each>
  </body>
</html>