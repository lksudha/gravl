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
            <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'prettify.css')}"/>
            <g:javascript library="prettify" />
        </g:else>
    </head>
    <body>
        <g:each var="entry" in="${entries}">

            <div class="blogentry">
                <div class="blogRolodex"><g:blogDateRolodex date="${entry.created}"/></div>
                <div class="blogtitle"><a href="${baseUri + entry.toPermalink()}">${entry.title}</a></div>
                <g:if test="${print!=true}">
                    <div class="printIcons">
                        <g:if test="${session.account}">
                            <a href="<g:createLinkTo dir="${params.blog}/admin/blog/edit" file="${entry.id}"/>">Edit</a>
                        </g:if>
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
                    <p>
                    Tags:
                        <g:each var="tag" in="${entry.tags}">
                            <a href="<g:createLinkTo dir="${params.blog}/archive" file="${tag.name}"/>">${tag.name}</a>
                        </g:each>
                    </p>
                </div>
            </div>

        </g:each>
    </body>
</html>