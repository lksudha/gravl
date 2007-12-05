  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit BlogEntry</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">BlogEntry List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New BlogEntry</g:link></span>
        </div>
        <div class="body">
            <h1>Edit BlogEntry</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${blogEntry}">
            <div class="errors">
                <g:renderErrors bean="${blogEntry}" as="list" />
            </div>
            </g:hasErrors>
            <g:form controller="blogEntry" method="post" >
                <input type="hidden" name="id" value="${blogEntry?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='title'>Title:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'title','errors')}'>
                                    <input type="text" maxlength='128' id='title' name='title' value="${fieldValue(bean:blogEntry,field:'title')}"/>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='body'>Body:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'body','errors')}'>
                                    <textarea rows='5' cols='40' name='body'>${blogEntry?.body?.encodeAsHTML()}</textarea>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='markup'>Markup:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'markup','errors')}'>
                                    <g:select id='markup' name='markup' from='${blogEntry.constraints.markup.inList.collect{it.encodeAsHTML()}}' value="${fieldValue(bean:blogEntry,field:'markup')}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='allowComments'>Allow Comments:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'allowComments','errors')}'>
                                    <g:checkBox name='allowComments' value="${blogEntry?.allowComments}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='blog'>Blog:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'blog','errors')}'>
                                    <g:select optionKey="id" from="${Blog.list()}" name='blog.id' value="${blogEntry?.blog?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='comments'>Comments:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'comments','errors')}'>
                                    
<ul>
<g:each var='c' in='${blogEntry?.comments?}'>
    <li><g:link controller='comment' action='show' id='${c.id}'>${c}</g:link></li>
</g:each>
</ul>
<g:link controller='comment' params='["blogEntry.id":blogEntry?.id]' action='create'>Add Comment</g:link>

                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='created'>Created:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'created','errors')}'>
                                    <g:datePicker name='created' value="${blogEntry?.created}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='published'>Published:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'published','errors')}'>
                                    <g:checkBox name='published' value="${blogEntry?.published}" ></g:checkBox>
                                </td>
                            </tr> 
                        
                            <tr class='prop'>
                                <td valign='top' class='name'>
                                    <label for='tags'>Tags:</label>
                                </td>
                                <td valign='top' class='value ${hasErrors(bean:blogEntry,field:'tags','errors')}'>
                                    
<ul>
<g:each var='t' in='${blogEntry?.tags?}'>
    <li><g:link controller='tag' action='show' id='${t.id}'>${t}</g:link></li>
</g:each>
</ul>
<g:link controller='tag' params='["blogEntry.id":blogEntry?.id]' action='create'>Add Tag</g:link>

                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
