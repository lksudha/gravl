<html>
<head>
    <title>Manage Files</title>
    <meta name="layout" content="main"/>
    <g:javascript library="scriptaculous"/>
    <g:javascript src="lightbox.js"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'lightbox.css')}"/>
    
</head>
<body>
<table width="100%">
    <g:if test="${dir}">
        <tr>
            <td>
                <g:link controller="${params.blog}/files" action="list" params="[dir: (dir.substring(0, dir.lastIndexOf('/')))]">..</g:link>
            </td>
            <td>

            </td>
        </tr>

    </g:if>

    <g:each var="file" in="${files}">
        <tr>
            <td>
                <g:if test="${file.isDirectory()}">
                    <g:link controller="${params.blog}/files" action="list" params="[dir: (dir + '/' + file.name)]">${file.name}</g:link>
                </g:if>
                <g:else>
                    <div id="file-${file.name}">${file.name}</div>
                </g:else>
                <g:javascript>
                     new Ajax.InPlaceEditor( 'file-${file.name}', '<g:createLink controller="${params.blog}/files" action="rename"/>?dir=${dir}&filename=${file.name}');
                </g:javascript>
            </td>
            <td>

                <g:if test="${file.isFile()}">

                    <a href="<g:createLink controller="${params.blog + dir}" action="${file.name}"/>" rel="lightbox" title="${file.name}">
                        <img src="<g:createLinkTo dir='images' file="zoom.png" alt="preview image"/>"/>
                    </a>
                    <a href="<g:createLink controller="${params.blog + dir}" action="${file.name}"/>">
                         <img src="<g:createLinkTo dir='images' file="photo.png" alt="show image"/>"/>
                    </a>
                    <g:link controller="${params.blog}/files" action="delete" params="[ dir: dir, filename: file.name]">
                        <img src="<g:createLinkTo dir='images' file="delete.png" alt="delete image"/>"/>
                    </g:link>
                    
                </g:if>


            </td>

        </tr>

    </g:each>
</table>

<div style="padding: 1em; border: 1px dotted black; background-color: lightyellow;">
    <g:form controller="${params.blog}/files" action="upload" method="post" enctype="multipart/form-data">
        Filename:
        <input name="filename"/>
        <input type="hidden" name="dir" value="${dir}"/>
        <input type="file" name="myFile"/>
        <input type="submit"/>
    </g:form>
</div>

</body>
</html>