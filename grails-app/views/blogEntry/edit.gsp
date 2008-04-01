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
    <resource:richTextEditor />
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

                    <%--
                    <button type="button" id="toggleEditor">Toggle Editor</button><br/>
                    <g:javascript>
                    (function() {
    var Dom = YAHOO.util.Dom,
        Event = YAHOO.util.Event;

    YAHOO.log('Create Button Control (#toggleEditor)', 'info', 'example');
    var _button = new YAHOO.widget.Button('toggleEditor');
    _button.addClass('toggleEditor');

    var myConfig = {
        height: '300px',
        width: '530px',
        animate: true,
        dompath: true,
        focusAtStart: true
    };

    var state = 'on';
    YAHOO.log('Set state to on..', 'info', 'example');

    YAHOO.log('Create the Editor..', 'info', 'example');
    var myEditor = new YAHOO.widget.Editor('body', myConfig);
    myEditor.render();

    _button.on('click', function(ev) {
        Event.stopEvent(ev);
        if (state == 'on') {
            YAHOO.log('state is on, so turn off', 'info', 'example');
            state = 'off';
            myEditor.saveHTML();
            YAHOO.log('Save the Editors HTML', 'info', 'example');
            var stripHTML = /<\S[^><]*>/g;
            myEditor.get('textarea').value = myEditor.get('textarea');
            YAHOO.log('Strip the HTML markup from the string.', 'info', 'example');
            YAHOO.log('Set Editor container to position: absolute, top: -9999px, left: -9999px. Set textarea visible', 'info', 'example');
            Dom.setStyle(myEditor.get('element_cont').get('firstChild'), 'position', 'absolute');
            Dom.setStyle(myEditor.get('element_cont').get('firstChild'), 'top', '-9999px');
            Dom.setStyle(myEditor.get('element_cont').get('firstChild'), 'left', '-9999px');
            myEditor.get('element_cont').removeClass('yui-editor-container');
            Dom.setStyle(myEditor.get('element'), 'visibility', 'visible');
            Dom.setStyle(myEditor.get('element'), 'top', '');
            Dom.setStyle(myEditor.get('element'), 'left', '');
            Dom.setStyle(myEditor.get('element'), 'position', 'static');
        } else {
            YAHOO.log('state is off, so turn on', 'info', 'example');
            state = 'on';
            YAHOO.log('Set Editor container to position: static, top: 0, left: 0. Set textarea to hidden', 'info', 'example');
            Dom.setStyle(myEditor.get('element_cont').get('firstChild'), 'position', 'static');
            Dom.setStyle(myEditor.get('element_cont').get('firstChild'), 'top', '0');
            Dom.setStyle(myEditor.get('element_cont').get('firstChild'), 'left', '0');
            Dom.setStyle(myEditor.get('element'), 'visibility', 'hidden');
            Dom.setStyle(myEditor.get('element'), 'top', '-9999px');
            Dom.setStyle(myEditor.get('element'), 'left', '-9999px');
            Dom.setStyle(myEditor.get('element'), 'position', 'absolute');
            myEditor.get('element_cont').addClass('yui-editor-container');
            YAHOO.log('Reset designMode on the Editor', 'info', 'example');
            myEditor._setDesignMode('on');
            YAHOO.log('Inject the HTML from the textarea into the editor', 'info', 'example');
            myEditor.setEditorHTML(myEditor.get('textarea').value);
        }
    });
})();

                    </g:javascript>




                    <richui:richTextEditor width="600" height="400" name="body" value="${blogEntry.body}"/>
                    --%>
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
