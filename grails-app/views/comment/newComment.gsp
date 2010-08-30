

<div class="blogcomments">
    <div id="commentPreview" style="display:none;">

    </div>

    <g:form action="preview">

        <style>
            div#newComment td {
                padding: 5px;
            }

            div#newComment table input {
                font-size: large;
                width: 20em;
            }
        </style>

        <table id="newCommentTable">
            <tr>
                <td>Comment:<br/>

                <g:textArea name="body" rows="7" cols="80"/>

                </td>
            </tr>
            <tr>
                <td>Name:<br/>
                <g:textField name="author" value="${author}"/></td>
            </tr>
            <tr>
                <td>Website: (optional)<br/>
                <g:textField name="url"/></td>
            </tr>
            <tr>
                <td>Email: (optional, never displayed)<br/>
                <g:textField name="email"/> </td>
            </tr>
            <tr>
                <td><g:checkBox name="emailUpdates" style="width: 12px;"/>
                    Email me followup comments (optional, email has opt-out link)
                </td>
            </tr>

        </table>
        
        <g:hiddenField name="entryId" value="${entryId}"/>
        <g:submitToRemote url="[controller: 'comment', action: 'preview']" update="commentPreview" value="Preview" onComplete="Effect.Appear('commentPreview')"/>
        <g:submitToRemote url="[controller: 'comment', action: 'save']" before="this.disabled=true;" update="commentPreview" value="Save" onComplete="refreshIfSuccessful()"/>
        <input type="button" value="Cancel" onclick="removeNewCommentUI();" />
    
    </g:form>
</div>

