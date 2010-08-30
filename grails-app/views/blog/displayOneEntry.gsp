<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>
            <g:if test="${entries?.size == 1}">
                ${entries[0].title} - ${entries[0].blog.title}
            </g:if>
            <g:else>
                ${entries[0]?.blog?.title} ${params.year ? " > " + params.year : ""} ${params.month ? " > " + params.month : "" } ${params.day ? " > " + params.day : "" }
            </g:else>
        </title>
        <g:if test="${print==true}">
            <meta name="layout" content="print"/>
        </g:if>
        <g:else>
            <meta name="layout" content="main"/>
            <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
            <g:javascript library="scriptaculous"/>
            <!--
            <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'prettify.css')}"/>
            <g:javascript library="prettify" />
        	-->
        	<link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'shCore.css')}"/>
        	<link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'shThemeDefault.css')}"/>
        	<g:javascript src="shCore.js"/>
        	<g:javascript src="shAutoloader.js"/>
        	
            
        </g:else>


        <g:javascript>
        function removeNewCommentUI() {
            Effect.Fade('newComment')
            // document.getElementById('newComment').innerHTML='';
        }


        function refreshIfSuccessful() {
            if (document.getElementById('newlySaved')) {
                var currComments = document.getElementById('allcomments')
                var newComment = document.getElementById('newlySaved')
                currComments.innerHTML += newComment.innerHTML
                removeNewCommentUI()
            }
        }


        function deleteComment(successful, commentId, message) {

            if (successful) {
                var commentIdDivName = 'comment' + commentId
                var commentIdDiv = document.getElementById(commentIdDivName)
                Effect.Fade(commentIdDivName)
                // commentIdDiv.innerHTML='<div class="flash">' + message + "</div>"
            } else {
                alert(message);
            }

        }

        function approveComment(successful, commentId, message) {

            if (successful) {
                var commentIdDivName = 'approval' + commentId
                var commentIdDiv = document.getElementById(commentIdDivName)
                commentIdDiv.innerHTML='<b>' + message + "</b>"
                Effect.Highlight(commentIdDivName)
            } else {
                alert(message);
            }

        }

    </g:javascript>



    </head>
    <body>
        <g:each var="entry" in="${entries}">

            <div class="blogentry">
                <div class="blogRolodex"><g:blogDateRolodex date="${entry.created}"/></div>
                <div class="blogtitle"><a href="${baseUri + entry.toPermalink()}">${entry.title}</a></div>
                <g:if test="${print!=true}">
                    <div class="printIcons">
                        <jsec:user>
                            <a href="<g:createLinkTo dir="${params.blog}"/>/admin/blog/edit/${entry.id}">Edit</a>
                        </jsec:user>
                        <a href="${entry.toPermalink()}?print=true"><img src="<g:createLinkTo dir="images" file="print_button.png"/>" alt="Printer Friendly Version"/></a>
                        <%--
                        <g:link controller="pdf" action="show" params="[url: entry.toPermalink()]">
                            <img src="<g:createLinkTo dir="images" file="pdf_button.png"/>" alt="Download PDF of this Entry"/>
                        </g:link>
                        --%>
                    </div>

                </g:if>
                <div class="blogdate">By ${entry.account?.fullName} at <g:niceDate date="${entry.created}"/></div>

                <div class="blogbody">${entry.toMarkup()}</div>
                <div class="blogtags">
                    <div style="float: right;">
                        <a href="${baseUri + entry.toPermalink()}#comments">${ entry.comments.size() } Comments.</a>
                    </div>
                    <p>
                    Tags:
                        <g:each var="tag" in="${entry.tags}">
                            <a href="<g:createLinkTo dir="${params.blog}/archive" file="${tag.name}"/>">${tag.name}</a>
                        </g:each>
                    </p>
                </div>
                <%--
                <div class="nextPrevLinks" style="align: center; width: 100%;">
                	<g:writeNextPrevLinks id="${entry.id}"/>
                </div>
                --%>
                <g:if test="${entries.size == 1 && print==false}">

                    <a name="comments"/>
                    <div class="blogcomments" id="allcomments">

                        <g:each var="comment" in="${entry.comments}" status="counter">

                            <g:if test="${comment.status == 'approved' || org.jsecurity.SecurityUtils.subject.authenticated || comment.ipaddress == request.getRemoteAddr() }">
                                <g:render template="comment" model="[comment: comment]"/>
                            </g:if>
                            
                        </g:each>
                    </div>
                    
                    <div id="newComment" style="display: none;">

                    </div>

                    <p style="margin: 1em;">
                        <g:remoteLink controller="comment" action="newComment" id="${entry.id}" update="newComment" onSuccess="Effect.Appear('newComment')">Add Comment</g:remoteLink>
                    </p>

                </g:if>
                <g:else>

                </g:else>
            </div>

        </g:each>
        <g:javascript>
        
			function path() {
			  var args = arguments,
			      result = []
			      ;
			       
			  for(var i = 0; i < args.length; i++)
			      result.push(args[i].replace('@', "${createLinkTo(dir: 'js')}/"));
			       
			  return result
			};
			 
			SyntaxHighlighter.autoloader.apply(null, path(
			  'applescript            @shBrushAppleScript.js',
			  'actionscript3 as3      @shBrushAS3.js',
			  'bash shell             @shBrushBash.js',
			  'coldfusion cf          @shBrushColdFusion.js',
			  'cpp c                  @shBrushCpp.js',
			  'c# c-sharp csharp      @shBrushCSharp.js',
			  'css                    @shBrushCss.js',
			  'delphi pascal          @shBrushDelphi.js',
			  'diff patch pas         @shBrushDiff.js',
			  'erl erlang             @shBrushErlang.js',
			  'groovy                 @shBrushGroovy.js',
			  'java                   @shBrushJava.js',
			  'jfx javafx             @shBrushJavaFX.js',
			  'js jscript javascript  @shBrushJScript.js',
			  'perl pl                @shBrushPerl.js',
			  'php                    @shBrushPhp.js',
			  'text plain             @shBrushPlain.js',
			  'py python              @shBrushPython.js',
			  'ruby rails ror rb      @shBrushRuby.js',
			  'sass scss              @shBrushSass.js',
			  'scala                  @shBrushScala.js',
			  'sql                    @shBrushSql.js',
			  'vb vbnet               @shBrushVb.js',
			  'xml xhtml xslt html    @shBrushXml.js'
			));
			SyntaxHighlighter.all();
        
        	
        </g:javascript>
    </body>
</html>