<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>properties</title>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <g:form action="updateProperties">
            <g:hiddenField name="id" value="${cmd.id}"/>
            <table>

                <tr>
                    <td>Blog Name: </td>
                    <td><g:textField name="title" value="${cmd.title}"/></td>
                </tr>

                <tr>
                    <td>Byline: </td>
                    <td><g:textField name="byline" value="${cmd.byline}"/></td>
                </tr>

                <tr>
                    <td>Url: (go easy, tiger)</td>
                    <td>${request.contextPath}/<g:textField name="byline" value="${cmd.blogId}"/>/</td>
                </tr>

                <tr>
                    <td colspan="2"><g:checkBox name="allowComments" value="${cmd.allowComments}"/>Allow Comments: </td>
                </tr>

                <tr>
                    <td><g:checkBox name="emailNotify" value="${cmd.emailNotify}"/>Email on new comments to: (comma separated) </td>
                    <td><g:textField name="emailAddresses" value="${cmd.emailAddresses}"/></td>
                </tr>

                <tr>
                    <td><g:checkBox name="gtalkNotify" value="${cmd.gtalkNotify}"/>GTail notify on new comments to: (comma separated) </td>
                    <td><g:textField name="gtalkAddresses" value="${cmd.gtalkAddresses}"/></td>
                </tr>

                <tr>
                    <td><g:checkBox name="smsNotify" value="${cmd.smsNotify}"/>SMS on new comments to: (comma separated) </td>
                    <td><g:textField name="smsAddresses" value="${cmd.smsAddresses}"/></td>
                </tr>


            </table>

        </g:form>

    </body>
</html>