<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>drafts</title>
        <meta name="layout" content="main"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>Draft Blog Entries</p>

        <g:archiveByMonth entries="${entries}" baseUri="${baseUri}"/>

    </body>
</html>