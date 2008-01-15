<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Stats</title>
      <meta name="layout" content="main"/>
  </head>
  <body>
      <h1>Blog Stats</h1>

      <style>
          .prettyTable {
            margin: 1em;
            border: 1px solid lightgray;
            width: 100%;
            font-size: larger;
          }
          
          .prettyTable thead  {
            border: 1px solid lightgray;
          }

          .prettyTable thead tr td {
            font-weight: bolder;
          }

          .prettyTable tbody tr td {
            padding: 4px;
          }

          .rightcol {
            width: 10%;  
          }

      </style>

      <g:tableFromMap map="${urlCount}" headings="${['URL', 'Count']}"/>

      <g:tableFromMap map="${referers}" headings="${['URL', 'Count']}"/>
      
      <g:tableFromMap map="${hitsPerHour}" headings="${['Hour', 'Hits']}"/>

      <g:tableFromMap map="${browserTypes}" headings="${['Browser Type', 'Hits']}"/>

      <g:tableFromMap map="${countries}" headings="${['Country', 'Hits']}"/>

      <%--
      
      <g:barChart title='Hits Per Hour' size="${[400,200]}" colors="${['FF0000','00ff00','0000ff']}" type="bvs"
              labels="${hitsPerHourChart.keySet()}" axes="x,y" axesLabels="${[0:hitsPerHourChart.keySet()]}" fill="${'bg,s,efefef'}" dataType='text' data='${new ArrayList(hitsPerHourChart.values())}' />


      <g:pieChart title='Browser Types' colors="${['FF0000','00ff00','0000ff']}"
              labels="${browserTypes.keySet()}" fill="${'bg,s,efefef'}" dataType='text' data='${new ArrayList(browserTypes.values())}' />
      

      <g:pieChart title='Countries' colors="${['FF0000','00ff00','0000ff']}"
              labels="${countries.keySet()}" fill="${'bg,s,efefef'}" dataType='text' data='${new ArrayList(countries.values())}' />

        --%>

  </body>
</html>