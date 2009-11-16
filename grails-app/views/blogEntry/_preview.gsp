<div class="blogentry">
     <div class="blogRolodex"><g:blogDateRolodex date="${entry.created}"/></div>
     <div class="blogtitle"><a href="#">${entry.title}</a></div>
     <div class="blogdate">By <jsec:principal/> at <g:niceDate date="${entry.created}"/></div>

     <div class="blogbody">${entry.toMarkup()}</div>
     <div class="blogtags">
         <p>
         Tags:
             <g:each var="tag" in="${entry.getAllTags()}">
                 <a href="<g:createLinkTo dir="${params.blog}/archive" file="${tag}"/>">${tag}</a>
             </g:each>
         </p>
     </div>
 </div>
