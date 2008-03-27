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

    <g:if test="${params.dir}">
        <tr>
            <td>
                <a href="<g:createLinkTo dir="${params.blog}/files/list"/>?dir=${(params.dir.substring(0, params.dir.lastIndexOf('/')))}">..</a>
            </td>
            <td>

            </td>
        </tr>

    </g:if>


    <g:each var="file" in="${files}">
        <tr>

            <td>
                <g:if test="${file.isDirectory()}">
                    <a href="<g:createLinkTo dir="${params.blog}/files/list"/>?dir=${dirName}/${file.name}">${file.name}</a>
                </g:if>
                <g:else>
                    <div id="file-${file.name}">${file.name}</div>
                </g:else>
                <g:javascript>
                     new Ajax.InPlaceEditor( 'file-${file.name}', '<g:createLinkTo dir="${params.blog}/files/rename"/>?dir=${dirName}&filename=${file.name}');
                </g:javascript>
            </td>

            <td>

                <g:if test="${file.isFile()}">

                    <a href="<g:createLinkTo dir="${params.blog}"/>${dirName}/${file.name}" rel="lightbox" title="${file.name}">
                        <img src="<g:createLinkTo dir="images" file="zoom.png" alt="preview image"/>"/>
                    </a>

                    <a href="<g:createLinkTo dir="${params.blog}"/>${dirName}/${file.name}">
                         <img src="<g:createLinkTo dir='images' file="photo.png" alt="show image"/>"/>
                    </a>

                    <a href="<g:createLinkTo dir="${params.blog}/files/delete"/>?dir=${dirName}&filename=${file.name}">
                        <img src="<g:createLinkTo dir='images' file="delete.png" alt="delete image"/>"/>
                    </a>
                    
                </g:if>


            </td>



        </tr>

    </g:each>

</table>

<div style="padding: 1em; border: 1px dotted black; background-color: lightyellow;">
    <form action="<g:createLinkTo dir='${params.blog}/files/upload'/>" method="post" enctype="multipart/form-data">
        Filename:
        <input name="filename"/>
        File:
        <input type="hidden" name="dir" value="${dirName}"/>
        <input type="file" name="myFile"/>
        <input type="submit" value="Upload"/>
    </form>
</div>


</body>
</html>