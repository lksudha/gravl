<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Archive - ${params.tagName ? params.tagName + " Category - " : ""} ${entries[0]?.blog?.title} </title>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <g:if test="${params.tagName}">
            <div style="float: right">
                <a href="${request.contextPath}/${params.blog}/categories/${params.tagName}/atom.xml">
                    <img src="${createLinkTo(dir:'images',file:'feed-icon-16x16.jpg')}" alt="Atom Feed for category ${params.tagName}"/>
                </a>
            </div>
            <p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>Archive for Category ${params.tagName}</p>
        </g:if>

        <g:archiveByMonth entries="${entries}" baseUri="${baseUri}"/>

        <div class="archivePaginate">
            <g:paginate controller="${params.blog}" action="archive" total="${totalArchiveSize}" max="20" params="[ tagName : params.tagName ]"/>
        </div>

    </body>
</html>