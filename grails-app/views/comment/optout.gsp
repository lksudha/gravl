<html>
  <head>
      <title>Comment OptOut</title>
      <meta name="layout" content="print"/>
  </head>
  <body>
        <g:if test="${successful}">
            Successfully opted out of further comment thread posts for email ${email}.
        </g:if>
        <g:else>
            Comment optout unsuccessful for ${email}. Please contact the blog owner.
        </g:else>
  </body>
</html>