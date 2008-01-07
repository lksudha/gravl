<g:if test="${newlySaved}"><div id="newlySaved"></g:if>

<div class="blogcomment" id="comment${comment.properties.id}" >
    <g:hasErrors>
        <div class="error" style="background-color: pink; border: 1px solid black; margin: 1em; padding: 1em;">
            <g:renderErrors/>
        </div>
    </g:hasErrors>
    <div class="bubble"><a name="comment${comment.properties.id}"/>
        <blockquote>
            <p>${comment.body}</p>
        </blockquote>

            <g:if test="${session.account}">

                <div class="deleteLink">
                <a href="<g:createLink controller="comment" action="delete" id="${comment.properties.id}"/>" onclick="
                new Ajax.Request('<g:createLink controller="comment" action="delete" id="${comment.properties.id}"/>',
                    {
                        asynchronous:true,
                        evalScripts:true,
                        onSuccess:function(result,JSON){
                            deleteComment(JSON.successful, JSON.commentId, JSON.message)
                        }
                    });
                return false;">
                Delete</a>
                </div>

                <g:if test="${comment.properties.status && comment.properties.status != 'approved'}">
                    <div id="approval${comment.properties.id}" class="approvalLink">
                    <a href="<g:createLink controller="comment" action="approve" id="${comment.properties.id}"/>" onclick="
                    new Ajax.Request('<g:createLink controller="comment" action="approve" id="${comment.properties.id}"/>',
                        {
                            asynchronous:true,
                            evalScripts:true,
                            onSuccess:function(result,JSON){
                                approveComment(JSON.successful, JSON.commentId, JSON.message)
                            }
                        });
                    return false;">
                    Approve</a>
                    </div>
                </g:if>
            </g:if>

        <cite>
            <strong>
                <g:if test="${comment.url}"><a href="${comment.url}"></g:if>
                    ${comment.author}<g:if test="${comment.url}"></a></g:if>
            </strong> on
            <g:niceDate date="${comment.created}"/>

        </cite>
    </div>
</div>
 <g:if test="${newlySaved}"></div></g:if>