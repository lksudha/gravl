<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Timeline - ${blogObj.title} </title>
        <meta name="layout" content="main"/>
        <resource:timeline />
    </head>
    <body>

        <richui:timeline style="height: 500px; border: 1px solid #aaa" startDate="${startDate}" datasource="${createLinkTo(dir: params.blog+ '/timelineData')}" /> 
        <g:javascript>
            initTimeline();
        </g:javascript>
    </body>
</html>