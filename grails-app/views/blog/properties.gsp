<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>properties</title>
        <meta name="layout" content="main"/>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'bubbles.css')}"/>
        <g:javascript library="scriptaculous"/>
    </head>
    <body>

        <style>
            td input {
                padding: 3px;
                font-size: larger;

            }
        </style>

        <form action="<g:createLinkTo dir="${params.blog}/admin/updateProperties"/>">
            <g:hiddenField name="id" value="${cmd.id}"/>
            <table>

                <tr>
                    <td>Blog Name: </td>
                    <td><g:textField name="title" value="${cmd.title}" size="60"/></td>
                </tr>

                <tr>
                    <td>Byline: </td>
                    <td><g:textField name="byline" value="${cmd.byline}" size="60"/></td>
                </tr>

                <tr>
                    <td>Url: (go easy, tiger)</td>
                    <td>${request.contextPath}/<g:textField name="blogid" value="${cmd.blogid}"/>/</td>
                </tr>

                <tr>
                    <td colspan="2"><g:checkBox name="allowComments" value="${cmd.allowComments}"/>Allow Comments: </td>
                </tr>

                <tr>
                    <td><g:checkBox name="emailNotify" value="${cmd.emailNotify}"/>Email on new comments to: (comma separated) </td>
                    <td><g:textField name="emailAddresses" value="${cmd.emailAddresses}"  size="60"/></td>
                </tr>

                <tr>
                    <td><g:checkBox name="gtalkNotify" value="${cmd.gtalkNotify}"/>GTail notify on new comments to: (comma separated) </td>
                    <td><g:textField name="gtalkAddresses" value="${cmd.gtalkAddresses}" size="60"/></td>
                </tr>

                <tr>
                    <td><g:checkBox name="useFeedburner" value="${cmd.useFeedburner}"/>Enable Feedburner. Your Feedburner URL:</td>
                    <td><g:textField name="fbAddress" value="${cmd.fbAddress}" size="60"/></td>
                </tr>


            </table>
            <g:submitButton name="save" value="Save"/>

        </form>

    </body>
</html>