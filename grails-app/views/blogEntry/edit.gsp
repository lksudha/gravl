<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Edit BlogEntry</title>
    <style type="text/css">
        input  {
            font-size: x-large;
        }

        .button input {
            font-size: medium;
        }
        
    </style>
</head>
<body>
<div class="body">
<p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>Edit Blog</p>
<g:form controller="${params.blog}" method="admin/blog/save">
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
        <span class="button"><g:actionSubmit class="save" value="Save"/></span>
        <span class="button"><g:actionSubmit class="preview" value="Preview"/></span>
        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete"/></span>
    </div>
</g:form>
</body>
</html>
