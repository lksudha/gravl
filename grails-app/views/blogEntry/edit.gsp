<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Edit BlogEntry</title>
    <style type="text/css">
        input  {
            font-size: x-large;
        }

        .buttons input {
            font-size: medium;
        }
        
    </style>
    <g:javascript library="scriptaculous"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'prettify.css')}"/>
    <g:javascript library="prettify" />
</head>
<body>
<div class="body">
<p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>Edit Blog</p>
<g:form controller="${params.blog}" action="admin/blog/save">
    <input type="hidden" name="id" value="${blogEntry?.id}"/>
    <table>
        <tbody>

            <tr>
                <td>
                    Title:
                </td>
                <td>
                    <g:textField name='title' value="${blogEntry.title}" style="width: 25em;"/>
                </td>
            </tr>

            <tr>
                <td>
                    Body:
                </td>
                <td>
                    <textarea rows='20' cols='91' name='body'>${blogEntry?.body}</textarea>
                </td>
            </tr>

            <tr>
                <td>
                    Markup:
                </td>
                <td>
                    <g:select name='markup' from='["wiki", "html"]' value="${blogEntry?.markup}"></g:select>
                </td>
            </tr>

            <tr>
                <td>
                    Status:
                </td>
                <td>
                    <g:select name='status' from='["published", "unpublished"]' value="${blogEntry?.status}"></g:select>
                </td>
            </tr>

            <tr>
                <td>
                    Comments:
                </td>
                <td>
                    <g:checkBox name='allowComments' value="${blogEntry?.allowComments}"></g:checkBox>
                </td>
            </tr>


            <tr>
                <td>
                    Created:
                </td>
                <td>
                    <g:datePicker name='created' value="${blogEntry?.created}"></g:datePicker>
                </td>
            </tr>


            <tr>
                <td>
                    Tags:
                </td>
                <td>
                    <g:textField name="tagList" value="${blogEntry?.tagList}"/>
                </td>
            </tr>

        </tbody>
    </table>
    </div>
    <div class="buttons">
        <g:actionSubmit class="save" value="Save"/>
        <g:submitToRemote url="[controller: 'blogEntry', action: 'preview']" update="entryPreview" value="Preview"/>
        <g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/>
    </div>
</g:form>
<div id="entryPreview">
    
</div>
</body>
</html>
