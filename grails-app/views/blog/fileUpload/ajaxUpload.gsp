<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Upload blog</title>
        <meta name="layout" content="main"/>
        <g:javascript library="prototype"/>
        <g:javascript src="jsProgressBarHandler.js"/>
        <link rel="stylesheet" media="screen" type="text/css" href="${createLinkTo(dir: 'css', file: 'jsProgressBarHandler.css')}"/>

        <g:javascript>

            new Ajax.PeriodicalUpdater('progressBarId', '<g:link action="fileUpload" event="refresh" />',
              {
                method: 'get',
                frequency: 60,
              });


        </g:javascript>

    </head>
    <body>

        <div style="padding: 2em; border: black 1px solid; margin: 2em;">
            <span class="progressBar colorClass" id="progressBarId">50%</span>
        </div>
    </body>
</html>