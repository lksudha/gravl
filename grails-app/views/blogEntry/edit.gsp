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
            margin-top: 5px;
        }

        .label {
            width: 5em;
            height: 2em;
        }
        
    </style>
    <g:javascript library="scriptaculous"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'prettify.css')}"/>
    <g:javascript library="prettify" />
</head>
<body>
<div class="body">
<p style='font-size: large; border-bottom: 1px dotted black; padding-bottom: 4px;'>Edit Blog</p>
<form action="<g:createLinkTo dir="${params.blog}/admin/blog/save"/>" method="POST">
    <input type="hidden" name="id" value="${blogEntry?.id}"/>
    <table style="width: 100%;">
        <tbody>

            <tr>
                <td colspan="2">
                    <g:textField name='title' value="${blogEntry.title}" style="width: 100%;"/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <textarea rows='20' cols='102' name='body' style="width: 100%; margin-top: 4px; margin-bottom: 4px;">${blogEntry?.body}</textarea>
                </td>
            </tr>

            <tr>
                <td class="label">
                    Markup:
                </td>
                <td>
                    <g:select name='markup' from='["wiki", "html"]' value="${blogEntry?.markup}"></g:select>
                </td>
            </tr>

            <tr>
                <td class="label">
                    Status:
                </td>
                <td>
                    <g:select name='status' from='["published", "unpublished", "static"]' value="${blogEntry?.status}"></g:select>
                </td>
            </tr>

            <tr>
                <td class="label">
                    Comments:
                </td>
                <td>
                    <g:checkBox name='allowComments' value="${blogEntry?.allowComments}"></g:checkBox>
                </td>
            </tr>


            <tr>

                <td class="label">
                    Created:
                </td>
                <td>
                    <g:datePicker name='created' value="${blogEntry?.created}"></g:datePicker>
                </td>
            </tr>


            <tr>
                <td class="label">
                    Tags:
                </td>
                <td>
                    <resource:autoComplete skin="default" />
                    <richui:autoComplete name="tagList" delimChar=" " style="width: 100%"  
                            action="${createLinkTo(dir: params.blog+ '/blog/tagcomplete')}" value="${blogEntry.tagList}"/>
                </td>
            </tr>

        </tbody>
    </table>
    </div>
    <div class="buttons" style="float: left; ">
        <g:submitToRemote url="[controller: 'blogEntry', action: 'preview']" update="entryPreview" value="Preview"/>
        <g:actionSubmit value="Save"/>

    </div>
</form>
    
    <div class="buttons" style="float: right;top: -1em;">
        <g:form controller="${params.blog}/admin/blog" action="delete">
            <input type="hidden" name="id" value="${blogEntry?.id}"/>
            <g:submitButton name="delete" onclick="return confirm('Are you sure?');" value="Delete"/>
        </g:form>
    </div>
<div id="entryPreview" style="margin-top: 3em;">
    
</div>
</body>
</html>
