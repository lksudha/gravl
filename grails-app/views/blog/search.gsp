
  
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
				<g:if test="${results.totalHitCount > 0}">
					Displaying <b>${1 + results.totalHitsOffset} - ${results.totalHitsOffset + results.returnedHitCount}</b> 
					of <b>${results.totalHitCount}</b> matches 
				</g:if>
				<g:else>
					No matches found. 
				</g:else>	
				(<b>${results.queryTime}</b> ms).  Index contains <b>${results.totalDocsInIndex}</b> documents.
			</div>
			
			<div id="searchBody">
				<g:searchResults results="${results}" titleField="title" bodyField="body"/>
			</div>


            <div class="archivePaginate">
                <g:paginate controller="${params.blog}" action="search" total="${results.totalHitCount}" max="10" params="[ query: params.query, fields: params.fields ]"/>
             </div>

            
		</g:if>
    </body>
</html>
            