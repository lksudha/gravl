<div class="blogcomment" id="comment${comment.properties.id}" <g:if test="${newlySaved}">id="newlySaved"</g:if> >
    <g:hasErrors>
        <div class="error" style="background-color: pink; border: 1px solid black; margin: 1em; padding: 1em;">
            <g:renderErrors/>
        </div>
    </g:hasErrors>
    <div class="bubble"><a name="comment${comment.properties.id}"/>
        <blockquote>
            <p>${comment.body}</p>
        </blockquote>
        <cite>
            <strong>
                <g:if test="${comment.url}"><a href="${comment.url}"></g:if>
                    ${comment.author}<g:if test="${comment.url}"></a></g:if>
            </strong> on
            <g:niceDate date="${comment.created}"/>
            <g:if test="${session.account}">
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
            </g:if>
        </cite>
    </div>
</div>