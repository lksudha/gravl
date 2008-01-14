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

      <g:tableFromMap map="${urlCount}" headings="${['URL', 'Count']}"/>

      <g:tableFromMap map="${hitsPerHour}" headings="${['Hour', 'Hits']}"/>


      <g:barChart title='Hits Per Hour' size="${[400,200]}" colors="${['FF0000','00ff00','0000ff']}" type="bvs"
              labels="${hitsPerHourChart.keySet()}" axes="x,y" axesLabels="${[0:hitsPerHourChart.keySet()]}" fill="${'bg,s,efefef'}" dataType='text' data='${new ArrayList(hitsPerHourChart.values())}' />

      <g:tableFromMap map="${browserTypes}" headings="${['Browser Type', 'Hits']}"/>

      <g:pieChart title='Browser Types' colors="${['FF0000','00ff00','0000ff']}"
              labels="${browserTypes.keySet()}" fill="${'bg,s,efefef'}" dataType='text' data='${new ArrayList(browserTypes.values())}' />
      
      <g:tableFromMap map="${countries}" headings="${['Country', 'Hits']}"/>

      <g:pieChart title='Countries' colors="${['FF0000','00ff00','0000ff']}"
              labels="${countries.keySet()}" fill="${'bg,s,efefef'}" dataType='text' data='${new ArrayList(countries.values())}' />

  </body>
</html>