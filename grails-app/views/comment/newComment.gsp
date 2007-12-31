

<div class="blogcomments">
    <div id="commentPreview">

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
                <g:textField name="author"/></td>
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
        <g:submitToRemote url="[controller: 'comment', action: 'preview']" update="commentPreview" value="Preview"/>
        <g:submitToRemote url="[controller: 'comment', action: 'save']" update="commentPreview" value="Save"/>
        <input type="button" value="Cancel" onclick="document.getElementById('newComment').innerHTML='';" />
    
    </g:form>
</div>

