<form action="<g:createLink action="fileUpload"/>" id="webflowForm">
    <input id="currentProgress" type="hidden" value="${percentComplete}"/>
    <g:submitButton name="_eventId_update" value="Update"/>
    <g:submitButton name="_eventId_cancel" value="Cancel"/>
    <input type="hidden" name="_flowExecutionKey" value="${request.flowExecutionKey}"/>
</form>
