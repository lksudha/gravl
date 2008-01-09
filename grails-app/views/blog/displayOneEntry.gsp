<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>gravl</title>
        <g:if test="${print==true}">
            <meta name="layout" content="print"/>
        </g:if>
        <g:else>
            <meta name="layout" content="main"/>
            <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
            <g:javascript library="scriptaculous"/>
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
                        <a href="${request.forwardURI}?print=true"><img src="<g:createLinkTo dir="images" file="print_button.png"/>" alt="Printer Friendly Version"/></a>
                        <g:link controller="pdf" action="show" params="[url: entry.toPermalink()]">
                            <img src="<g:createLinkTo dir="images" file="pdf_button.png"/>" alt="Download PDF of this Entry"/>
                        </g:link>
                    </div>
                </g:if>
                <div class="blogdate">By ${entry.account?.fullName} at <g:niceDate date="${entry.created}"/></div>

                <div class="blogbody">${entry.toMarkup()}</div>
                <div class="blogtags">
                    <p>
                    Tags: <g:each var="tag" in="${entry.tags}">${tag.name}</g:each>
                    </p>
                    <p>
                    ${ entry.comments.size() } Comments:
                    </p>
                </div>
                <g:if test="${entries.size == 1 && print==false}">

                    <a name="comments"/>
                    <div class="blogcomments" id="allcomments">

                        <g:each var="comment" in="${entry.comments}" status="counter">

                            <g:if test="${comment.status == 'approved' || session.account || comment.ipaddress == request.getRemoteAddr() }">
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
    </body>
</html>