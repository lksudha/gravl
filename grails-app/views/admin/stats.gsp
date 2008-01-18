<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Stats</title>
      <meta name="layout" content="main"/>
  </head>
  <body>

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

          .prettyTable thead tr th {
            font-weight: bolder;
            padding: 4px;
          }

          .prettyTable tbody tr td {
            padding: 4px;
            font-size: smaller;
          }

          .odd {
            background-color: #f4f4f4;
          }

          .rightcol {
            width: 10%;  
          }

          h2 {
            font-size: xx-large;
            border-bottom: dotted black 1px;
            margin-top: 10px;
          }

          h1 {
            font-size: xx-large;
            font-weight: bold;
            border-bottom: dotted black 1px;
          }

      </style>

      <h1>Blog Stats</h1>


      <h2>Hits</h2>

      <g:tableFromMap map="${urlCount}" headings="${['URL', 'Count']}"/>

      <h2>Referers</h2>

      <g:tableFromMap map="${referers}" headings="${['URL', 'Count']}" url="true"/>

      <h2>Hits Per Hour</h2>

      <g:tableFromMap map="${hitsPerHour}" headings="${['Hour', 'Hits']}"/>

      <g:barChart type="bvs" title="Hits Per Hour" size="${[500,200]}" axes="x,y" axesLabels="${[0:hitsPerHour.keySet(),1:[0,0,hitsPerHour.values().max()]]}" 
            dataType="simple" data="${hitsPerHour.values().asList()}" />

      <h2>Browser Types</h2>

      <g:tableFromMap map="${browserTypes}" headings="${['Browser Type', 'Hits']}"/>

      <g:pieChart type="3d" title='Browser Types' size="${[600,200]}"
               labels="${browserTypes.keySet()}" dataType='simple' data='${browserTypes.values().asList()}' />

      <h2>Hits By Country</h2>

      <g:tableFromMap map="${countries}" headings="${['Country', 'Hits']}"/>

      <g:pieChart type="3d" title='Hits By Country' size="${[600,200]}"
               labels="${countries.keySet()}" dataType='simple' data='${countries.values().asList()}' />





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