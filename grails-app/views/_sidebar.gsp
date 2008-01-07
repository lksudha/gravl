
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


                  <div class="niceBox">
	      			<div class="niceBoxHd"></div>
	      			<div class="niceBoxBody">
						Gravl was developed by
						<a href="http://blogs.bytecode.com.au/glen">Glen Smith</a>
						 on <a href="http://www.grails.org/">Grails 1.0 RC1</a>
						for the Grails demo app collection.
	      			</div>
	      		
	      		</div>
	      		
	      	
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
								<p><a href="<g:createLink controller='account' action='edit'/>">
									${session.account.userId}
								</a></p>
			      			</div>
			      		
			      	</div>

	      		
	      		</g:if>
	      		<g:else>
		      		<div class="niceBox">
		      			<div class="niceBoxBody">


				           <g:form controller="login" action="login" method="post" >
                                    <b>User Id:</b><br/>
                                    <input type='text' name='userId'/>
                                    <b>Password:</b><br/>
                                    <input type="password" name='password'/>

				                     <span class="formButton">
				                        <input type="submit" value="Login"/>
				                     </span>
				            </g:form>
				            <p>
				            <g:link controller='login' action="forgottenPassword">Forgotten your password?</g:link><p/>

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
                        <g:tagCloud blogId="${params.blog}"/>
                    </div>
                </div>

                <div style="padding-left: 3em;">
	      			<a href="http://www.grails.org/">
	      				<img src="${createLinkTo(dir:'images',file:'grails_button.gif')}" alt="Powered By Grails"/>
	      			</a>
	      		</div>

