<form action="<g:createLink action="fileUpload"/>" id="webflowForm" style="display: none;">
    <input id="currentProgress" value="${percentComplete}"/>
    <g:submitButton name="update" value="Update"/>
    <input id="flowExecutionKey" name="_flowExecutionKey" value="${request.flowExecutionKey}" size="100"/>
</form>