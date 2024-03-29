<html>
    <head>
        <title><g:layoutTitle default="Gravl Welcome"/></title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'reset-fonts-grids.css')}"/>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"/>
        <link rel="shortcut icon" href="${resource(file: 'favicon.ico')}"/>

        <g:layoutHead/>
        <g:javascript library="application"/>
        <g:javascript library="rounded_corners_lite.inc"/>

        <meta name="description" content="gravel is a grails based blog engine"/>
        <meta name="keywords" content="groovy,grails,blogs"/>
        <meta name="robots" content="index,follow"/>

    </head>
    <body>
        <div id="doc3" class="yui-t5">
            <div id="hd">
                <NOimg id="logo" src="${resource(dir: 'images', file: 'headerlogo.png')}" alt="gravel logo"/>

                <div id="hdtitle"><a href="<g:resource dir="${params.blog}/"/>"><g:blogTitle blogid="${params.blog}"/></a></div>

                <div style="float: right; position: relative; margin-right: 7px; font-size: medium; ">
                    <g:searchBox noCombo="true" query="${params.query}" fields="title,body" controller="${params.blog}" action="search"/>
                </div>
                

                <div id="hdsubtitle"><g:blogByline blogid="${params.blog}"/></div>



            </div>
            <div id="bd"><!-- start body -->

                <div id="yui-main">
                    <div class="yui-b">
                        <g:if test="${flash.message}">
                            <div class="flash">
                                ${flash.message}
                            </div>
                        </g:if>

                        <g:layoutBody/>
                    </div>
                </div>
                <div class="yui-b">

                    <g:render template="/sidebar"/>

                </div>

            </div>  <!-- end body -->
            <div id="ft">
                <div id="footerText">
                Gravl <g:meta name="app.version"/> on Grails <g:meta name="app.grails.version"/> by <a href="http://blogs.bytecode.com.au/glen">Glen Smith</a>.
                </div>
            </div>
        </div>

    </body>

</html>