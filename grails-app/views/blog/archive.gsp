<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>archive</title>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <g:archiveByMonth entries="${entries}" baseUri="${baseUri}"/>

        <div class="archivePaginate">
            <g:paginate total="${totalArchiveSize}" max="20" params="[blog : params.blog]"/>
        </div>

    </body>
</html>