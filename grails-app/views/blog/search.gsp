
  
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="main" />
         <title>Search Results</title>
    </head>
    <body>

        <style>
            .highlight {
                font-weight: bold;
            }

            .hitEntry {
                margin-top: 5px;
                border-bottom: 1px dotted black;
            }

            .hitTitle {
                font-size: larger;
                margin: 0px;
            }

            .hitTitle a {
                text-decoration: none;
            }

            .hitTitle a:hover {
                text-decoration: underline;
            }


            .hitInfo {
                font-size: smaller;
                color: #6e7d8e;
            }

        </style>

        <g:if test="${results != null}">
		
			<div id="searchCount">
				<g:if test="${results.total > 0}">
					Displaying <b>${1 + results.offset} - ${results.offset + results.max}</b> 
					of <b>${results.total}</b> matches
				</g:if>
				<g:else>
					No matches found. 
				</g:else>	
			</div>


            <g:each var="result" in="${results.results}" status="i">

                <div class='hit'>

                    <div class='hitEntry'>
                        <div class='hitTitle'>
                            <a href="<g:createLinkTo dir="${result.toPermalink().substring(1)}"/>">

                                ${result.title}

                                </a>
                        </div>
                        <div class='hitInfo'>
                            <g:niceDate date="${result.created}"/>
                        </div>
                        <p class='hitBody'>
                            ${ results.highlights[i] ?: "..." }
                        </p>
                    </div>
                </div>

            </g:each>


            <div class="archivePaginate">
                <g:paginate controller="${params.blog}" action="search" total="${results.total}" max="10" params="[ query: params.query]"/>
             </div>

            
		</g:if>
    </body>
</html>
            