<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>${title} Entries - ${entries[0]?.blog?.title}</title>
        <meta name="layout" content="main"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>${title} Blog Entries</p>

        <g:archiveByMonth entries="${entries}" baseUri="${baseUri}"/>

    </body>
</html>