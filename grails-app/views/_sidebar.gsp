
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
                              <p>
                                <a href="${request.contextPath + '/' + params.blog + "/archive"}" class="feedLink">Archive</a>
                              </p>
                              <p>
                                <a href="${request.contextPath + '/' + params.blog + "/timeline"}" class="feedLink">Timeline</a>
                              </p>
                        </div>
                    </div>


                 <jsec:user>
	      		
		      		<div class="niceBox">
			      			<div class="niceBoxHd">User Info</div>
			      			<div class="niceBoxBody">
								<p> <jsec:principal/>  (<g:link controller="auth" action="signOut">Logout</g:link>) </p>
                                  <ul>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/stats"/>">Stats</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/drafts"/>">Draft Entries</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/static"/>">Static Entries</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/blog/edit"/>">New Blog Entry</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/comments/pending"/>">Pending Comments</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/files"/>">File Management</a></li>                                 
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/properties"/>">Blog Properties</a></li>
                                  <li><a href="<g:createLinkTo dir="${params.blog}/admin/login/logout"/>">Logout</a></li>
                                  </ul>
                              </div>
			      		
			      	</div>

	      		
	      		</jsec:user>
	      		<jsec:notUser>
		      		<div class="niceBox">
		      			<div class="niceBoxBody">

                           <g:if test="${flash.loginError}">
                                <div class="flash">${flash.loginError}</div>
                           </g:if>

                           <g:form controller="auth" action="signIn" method="post" >
                                    <b>User Id:</b><br/>
                                    <input type='text' name='username'/>
                                    <b>Password:</b><br/>
                                    <input type="password" name='password'/>
									<b>Remember Me:</b>
									<g:checkBox name="rememberMe" /><br/>

				                     <span class="formButton">
				                        <input type="submit" value="Login"/>
				                     </span>
				            </g:form>

		      			</div>
		      		
		      		</div>
	      		</jsec:notUser>
	      		

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
                            <g:tagCloudCustom blogId="${params.blog}"/>
                        </div>
                    </div>
                </div>

                <div style="padding-left: 80px;">
	      			<a href="http://www.grails.org/">
	      				<img src="${createLinkTo(dir:'images',file:'grails_button.gif')}" alt="Powered By Grails"/>
	      			</a>
	      		</div>

                <g:customSidebar blog="${params.blog}" file="template/sidebar_bottom.html"/>


