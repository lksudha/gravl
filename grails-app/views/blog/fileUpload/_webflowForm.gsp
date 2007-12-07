<form action="<g:createLink action="fileUpload"/>" id="webflowForm">
    <input id="currentProgress" value="${percentComplete}"/>
    <g:submitButton name="update" value="Update"/>
    <input name="_flowExecutionKey" value="${request.flowExecutionKey}" size="100"/>
</form>