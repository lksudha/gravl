<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Simple GSP page</title>
      <g:if test="${print==true}">
        <meta name="layout" content="print"/>
      </g:if>
      <g:else>
        <meta name="layout" content="main"/>
      </g:else>
  </head>
  <body>
      <h1>Blog Stats</h1>
      <g:barChart title='Hits Per Hour' size="${[200,200]}" colors="${['FF0000','00ff00','0000ff']}" type="bvs"
              labels="${labels}" axes="x,y" axesLabels="${[0:hitsPerHour.keySet(),1:hitsPerHour.values()]}" fill="${'bg,s,efefef'}" dataType='simple' data='${values}' />

      <g:pieChart title='Browser Types' colors="${['FF0000','00ff00','0000ff']}"
              labels="${browserTypes.keySet()}" fill="${'bg,s,efefef'}" dataType='simple' data='${browserTypes.values()}' />

      <g:pieChart title='Countries' colors="${['FF0000','00ff00','0000ff']}"
              labels="${countries.keySet()}" fill="${'bg,s,efefef'}" dataType='simple' data='${countries.values()}' />

  </body>
</html>