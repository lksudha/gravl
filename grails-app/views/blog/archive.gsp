<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>archive</title>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <g:if test="${params.tagName}">
            <p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>Archive for Category ${params.tagName}</p>
        </g:if>

        <g:archiveByMonth entries="${entries}" baseUri="${baseUri}"/>

        <div class="archivePaginate">
            <g:paginate controller="${params.blog}" action="archive" total="${totalArchiveSize}" max="20" params="[ tagName : params.tagName ]"/>
        </div>

    </body>
</html>