  
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>BlogEntry List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New BlogEntry</g:link></span>
        </div>
        <div class="body">
            <h1>BlogEntry List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="title" title="Title" />
                        
                   	        <g:sortableColumn property="body" title="Body" />
                        
                   	        <g:sortableColumn property="markup" title="Markup" />
                        
                   	        <g:sortableColumn property="allowComments" title="Allow Comments" />
                        
                   	        <th>Blog</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${blogEntryList}" status="i" var="blogEntry">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${blogEntry.id}">${blogEntry.id?.encodeAsHTML()}</g:link></td>
                        
                            <td>${blogEntry.title?.encodeAsHTML()}</td>
                        
                            <td>${blogEntry.body?.encodeAsHTML()}</td>
                        
                            <td>${blogEntry.markup?.encodeAsHTML()}</td>
                        
                            <td>${blogEntry.allowComments?.encodeAsHTML()}</td>
                        
                            <td>${blogEntry.blog?.encodeAsHTML()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${BlogEntry.count()}" />
            </div>
        </div>
    </body>
</html>
