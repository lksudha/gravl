
    <script type="text/JavaScript">

  window.onload = function()
  {
      /*
      The new 'validTags' setting is optional and allows
      you to specify other HTML elements that curvyCorners
      can attempt to round.

      The value is comma separated list of html elements
      in lowercase.

      validTags: ["div", "form"]

      The above example would enable curvyCorners on FORM elements.
      */
      settings = {
          tl: { radius: 10 },
          tr: { radius: 10 },
          bl: { radius: 10 },
          br: { radius: 10 },
          antiAlias: true,
          autoPad: true,
          validTags: ["div"]
      }

      /*
      Usage:

      newCornersObj = new curvyCorners(settingsObj, classNameStr);
      newCornersObj = new curvyCorners(settingsObj, divObj1[, divObj2[, divObj3[, . . . [, divObjN]]]]);
      */
      var myBoxObject = new curvyCorners(settings, "niceBox", "curvyCorners");
      myBoxObject.applyCornersToAll();

  }
</script>


                <g:customSidebar blog="${params.blog}" file="template/sidebar.html"/>
	      	
	      		<div class="niceBox">
	      			<div class="niceBoxHd"></div>
	      			<div class="niceBoxBody">
						<ul>
						<li>	      			
		      				<a href="${request.contextPath + '/' + params.blog + "/atom"}" class="feedLink">
			      				<img src="${createLinkTo(dir:'images',file:'feed-icon-16x16.jpg')}" alt="Atom"/>
			      			 Atom </a>
			      		</li>
			      		<li>
		      				<a href="${request.contextPath + '/' + params.blog + "/rss"}" class="feedLink">
			      				<img src="${createLinkTo(dir:'images',file:'feed-icon-16x16.jpg')}" alt="RSS"/>
			      			 RSS </a>
			      		</li>
			      		</ul>
			      		
	      			</div>
	      		</div>

                     <div class="niceBox">
                          <div class="niceBoxHd"></div>
                          <div class="niceBoxBody" style="text-align: center;">
                             <a href="${request.contextPath + '/' + params.blog + "/archive"}" class="feedLink">Archive</a>
                        </div>
                    </div>


                  <g:if test="${session.account}">
	      		
		      		<div class="niceBox">
			      			<div class="niceBoxHd">User Info</div>
			      			<div class="niceBoxBody">
								<p> ${session.account.fullName}  (${session.account.userId}} </p>
                                  <ul>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/stats"/>">Stats</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/drafts"/>">Draft Entries</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/blog/edit"/>">New Blog Entry</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/comments/pending"/>">Pending Comments</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/properties"/>">Blog Properties</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/login/logout"/>">Logout</a></li>
                                  </ul>
                              </div>
			      		
			      	</div>

	      		
	      		</g:if>
	      		<g:else>
		      		<div class="niceBox">
		      			<div class="niceBoxBody">

                           <g:if test="${flash.loginError}">
                                <div class="flash">${flash.loginError}</div>
                           </g:if>

                           <form action="<g:createLinkTo dir="${params.blog}/admin/login/login"/>" method="post" >
                                    <b>User Id:</b><br/>
                                    <input type='text' name='userId'/>
                                    <b>Password:</b><br/>
                                    <input type="password" name='password'/>

				                     <span class="formButton">
				                        <input type="submit" value="Login"/>
				                     </span>
				            </form>

		      			</div>
		      		
		      		</div>
	      		</g:else>
	      		

	      		<div class="niceBox">
	      			<div class="niceBoxBody">
						Download the 
						<a href="http://code.google.com/p/gravl/">complete source code</a>
						to Gravl. Contribute patches and enhancements!
	      			</div>
	      		</div>


               <div class="niceBox">
                    <div class="niceBoxBody">
                        <div class="tagCloud">
                            <g:tagCloud blogId="${params.blog}"/>
                        </div>
                    </div>
                </div>

                <div style="padding-left: 80px;">
	      			<a href="http://www.grails.org/">
	      				<img src="${createLinkTo(dir:'images',file:'grails_button.gif')}" alt="Powered By Grails"/>
	      			</a>
	      		</div>

                <g:customSidebar blog="${params.blog}" file="template/sidebar_bottom.html"/>


