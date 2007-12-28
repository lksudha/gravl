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
        </g:else>
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
                <div class="blogdate">By [glen] at <g:niceDate date="${entry.created}"/></div>
  
                <div class="blogbody">${entry.body}</div>
                <div class="blogtags">
                    Tags: <g:each var="tag" in="${entry.tags}"> ${tag.name} </g:each>
                </div>
                <g:if test="${entries.size == 1 && print==false}">
                    <g:each var="comment" in="${entry.comments}" status="counter">

                       <div class="blogcomment">
                           <div class="bubble">
                            <blockquote>
                                <p>${comment.body}</p>
                            </blockquote>
                            <cite><strong>${comment.author}</strong> on <g:niceDate date="${comment.created}"/></cite>

                        </div>
                           <!--
                        <div class="${ counter % 2 ? "blogcommentEven" : "blogcommentOdd"}">
                            <div class="commentAuthor">
                                ${comment.author} at <g:niceDate date="${comment.created}"/>
                            </div>
                            <div class="commentBody">
                                ${comment.body}
                            </div>

                        </div>
                        -->
                       </div>
                    </g:each>
                </g:if>
            </div>

        </g:each>
    </body>
</html>