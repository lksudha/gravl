<div class="blogcomment">
    <div class="bubble">
        <blockquote>
            <p>${comment.body}</p>
        </blockquote>
        <cite>
            <strong>
                <g:if test="${comment.url}"><a href="${comment.url}"></g:if>
                    ${comment.author}<g:if test="${comment.url}"></a></g:if>
            </strong> on
            <g:niceDate date="${comment.created}"/>
        </cite>
    </div>
</div>