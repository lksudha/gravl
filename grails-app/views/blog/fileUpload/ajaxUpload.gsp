<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Upload blog</title>
        <meta name="layout" content="main"/>
        <g:javascript library="prototype"/>
        <g:javascript src="jsProgressBarHandler.js"/>
        <link rel="stylesheet" media="screen" type="text/css" href="${createLinkTo(dir: 'css', file: 'jsProgressBarHandler.css')}"/>
    </head>
    <body>

        <!-- our hidden form holding webflow flowIds etc, which gets updated on each request -->
        <div id="webflowFormDiv" style="display: block;">
            <g:render template="/blog/fileUpload/webflowForm"/>
        </div>

        <span class="progressBar percentImage3" id="myProgressBar">0%</span>

        <g:javascript>

            new Ajax.PeriodicalUpdater('webflowFormDiv', '<g:createLink action="fileUpload"/>' ,
              {
                method: 'get',
                parameters: $('webflowForm').serialize(true) ,
                frequency: 5,
                decay: 2,
                onSuccess: function() {
                    myJsProgressBarHandler.setPercentage('myProgressBar', $('currentProgress').getValue())
                }
              });



        </g:javascript>

    </body>
</html>