

<div class="blogcomments">
    <div id="commentPreview">

    </div>

    <g:form action="preview">

        <table id="newCommentTable">
            <tr>
                <td colspan="2" style="float:left">Comment:</td>

                <td><g:textArea name="body" rows="7" cols="60"/>

                </td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><g:textField name="author"/></td>
            </tr>
            <tr>
                <td>Website: (optional)</td>
                <td><g:textField name="url"/></td>
            </tr>
            <tr>
                <td>Email: (optional, never displayed)</td>
                <td><g:textField name="email"/> <br/>
                    <g:checkBox name="emailUpdates">Email me followup comments (with opt-out)</g:checkBox>
               </td>
            </tr>

        </table>
        <g:hiddenField name="entryId" value="${entryId}"/>
        <g:submitToRemote url="[controller: 'comment', action: 'preview']" update="commentPreview" value="Preview"/>
        <g:submitToRemote url="[controller: 'comment', action: 'addComment']" update="commentPreview" value="Save"/>
        <input type="button" value="Cancel" onclick="document.getElementById('newComment').innerHTML='';" />
    
    </g:form>
</div>

