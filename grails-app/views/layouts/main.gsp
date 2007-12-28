<html>
    <head>
        <title>gravl - <g:layoutTitle default="Welcome"/></title>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'reset-fonts-grids.css')}"/>
        <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'main.css')}"/>
        <link rel="shortcut icon" href="${createLinkTo(file: 'favicon.ico')}"/>

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
                <NOimg id="logo" src="${createLinkTo(dir: 'images', file: 'headerlogo.png')}" alt="gravel logo"/>

                <div id="hdtitle"><g:blogTitle blogid="${params.blog}"/></div>
                <div id="hdsubtitle"><g:blogByline blogid="${params.blog}"/></div>

                <div id="tabs">

                </div>

            </div>
            <div id="bd"><!-- start body -->

                <div id="yui-main">
                    <div class="yui-b">
                        <g:if test="${flash.message}">
                            <div id="flash">
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
                Gravl <g:meta name="app.version"/> on Grails <g:meta name="app.grails.version"/> by <a href="http://blogs.bytecode.com.au/glen">Glen Smith</a>.
            </div>
        </div>

    </body>

</html>