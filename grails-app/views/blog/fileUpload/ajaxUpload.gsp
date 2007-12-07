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

        <h1>Importing Blog...</h1>

        <span class="progressBar percentImage3" id="myProgressBar">0%</span>

        <g:javascript>

            new Ajax.PeriodicalUpdater('webflowFormDiv', '<g:createLink action="fileUpload"/>' ,
              {
                method: 'get',
                parameters: $('webflowForm').serialize(true) ,
                frequency: 10,
                decay: 2,
                onSuccess: function() {
                    // hackery to delay update till the webflowFormDiv is refreshed... I wish onComplete() worked...
                    setTimeout("myJsProgressBarHandler.setPercentage('myProgressBar', $('currentProgress').getValue())",500)
                    if ($('currentProgress').getValue() == '100') {
                        window.location = '<g:createLink action="fileUpload"/>?_flowExecutionKey=' + $('flowExecutionKey').getValue() + '&_eventId_complete=true'    
                    }
                }
              });



        </g:javascript>

    </body>
</html>