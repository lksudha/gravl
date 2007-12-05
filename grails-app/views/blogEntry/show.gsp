  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show BlogEntry</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">BlogEntry List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New BlogEntry</g:link></span>
        </div>
        <div class="body">
            <h1>Show BlogEntry</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${blogEntry.id}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Title:</td>
                            
                            <td valign="top" class="value">${blogEntry.title}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Body:</td>
                            
                            <td valign="top" class="value">${blogEntry.body}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Markup:</td>
                            
                            <td valign="top" class="value">${blogEntry.markup}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Allow Comments:</td>
                            
                            <td valign="top" class="value">${blogEntry.allowComments}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Blog:</td>
                            
                            <td valign="top" class="value"><g:link controller="blog" action="show" id="${blogEntry?.blog?.id}">${blogEntry?.blog}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Comments:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="c" in="${blogEntry.comments}">
                                    <li><g:link controller="comment" action="show" id="${c.id}">${c}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Created:</td>
                            
                            <td valign="top" class="value">${blogEntry.created}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Published:</td>
                            
                            <td valign="top" class="value">${blogEntry.published}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Tags:</td>
                            
                            <td valign="top" class="value">${blogEntry.tags}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form controller="blogEntry">
                    <input type="hidden" name="id" value="${blogEntry?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
