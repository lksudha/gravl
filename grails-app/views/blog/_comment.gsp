<div class="blogcomment">
    <g:hasErrors>
        <div class="error" style="background-color: pink; border: 1px solid black; margin: 1em; padding: 1em;">
            <g:renderErrors/>
        </div>
    </g:hasErrors>
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