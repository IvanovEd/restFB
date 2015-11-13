<%--
  Created by IntelliJ IDEA.
  User: Ivanov
  Date: 04.11.15
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<head>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
    <script type="text/javascript">
        google.load("visualization", "1", {packages: ["corechart"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var POLITIC, MUSIC, OTHER;
            <c:if test="${!empty valuesForPio}">
            POLITIC = ${valuesForPio["POLITIC"]},
                    MUSIC = ${valuesForPio["MUSIC"]},
                    OTHER = ${valuesForPio["OTHER"]}
            </c:if>;
            var data = google.visualization.arrayToDataTable([
                ['Task', 'Hours per Day'],
                ['POLITIC', POLITIC],
                ['MUSIC', MUSIC],
                ['OTHER', OTHER]
            ]);

            var options = {
                title: 'Statistics'
            };

            var chart = new google.visualization.PieChart(document.getElementById('chart_div_pio'));

            chart.draw(data, options);
        }
    </script>
    <script type='text/javascript'>
        google.load('visualization', '1', {'packages': ['geochart']});
        google.setOnLoadCallback(drawRegionsMap);


        function drawRegionsMap() {
            var kyivska, krym, zakarpatska, odesska, vinnitska, lvivska, ivanofrankivska, zhitomerska, harkiv, sumska, donetska, luhanska, mikolaivska, volinska, chernigivska, cherkaska, zakarpatska, zaporizhska, kirovogradska, ternopilska, hmelnitska, dnipropetrovska, poltavska, rivnenska, hersonska, chernivetska;
            <c:if test="${!empty values}">
            kyivska = ${values["kuyivska"]},
                    odesska = ${values["odesska"]},
                    vinnitska = ${values["vinnitska"]},
                    lvivska = ${values["lvivska"]},
                    ivanofrankivska = ${values["ivanofrankivska"]},
                    zhitomerska = ${values["zhitomerska"]},
                    harkiv = ${values["harkiv"]},
                    sumska = ${values["sumska"]},
                    donetska = ${values["donetska"]},
                    luhanska = ${values["luhanska"]},
                    mikolaivska = ${values["mikolaivska"]},
                    volinska = ${values["volinska"]},
                    chernigivska = ${values["chernigivska"]},
                    cherkaska = ${values["cherkaska"]},
                    zakarpatska = ${values["zakarpatska"]},
                    zaporizhska = ${values["zaporizhska"]},
                    kirovogradska = ${values["kirovogradska"]},
                    ternopilska = ${values["ternopilska"]},
                    hmelnitska = ${values["hmelnitska"]},
                    dnipropetrovska = ${values["dnipropetrovska"]},
                    poltavska = ${values["poltavska"]},
                    rivnenska = ${values["rivnenska"]},
                    hersonska = ${values["hersonska"]},
                    krym = ${values["krym"]},
                    chernivetska = ${values["chernivetska"]};
            </c:if>
            var data = google.visualization.arrayToDataTable([
                ['City', 'Site'],
                ['khersons\'ka oblast\'', hersonska],
                ['zakarpats\'ka oblast\'', zakarpatska],
                ['respublika krym', krym],
                ['odes \'ka oblast\'', odesska],
                ['sums \'ka oblast\'', sumska],
                ['rivnens\'ka oblast\'', rivnenska],
                ['khmel\'nyts\'ka oblast\'', hmelnitska],
                ['ivano-frankivs\'ka oblast\'', ivanofrankivska],
                ['poltavs\'ka oblast\'', poltavska],
                ['kharkivs\'ka oblast\'', harkiv],
                ['zhytomyrs\'ka oblast\'', zhitomerska],
                ['ternopil\'s\'ka oblast\'', ternopilska],
                ['cherkas\'ka oblast\'', cherkaska],
                ['zaporiz\'ka oblast\'', zaporizhska],
                ['luhans\'ka oblast\'', luhanska],
                ['vinnyts\'ka oblast\'', vinnitska],
                ['donets\'ka oblast\'', donetska],
                ['l\'vivs\'ka oblast\'', lvivska],
                ['volyns\'ka oblast\'', volinska],
                ['dnipropetrovs\'ka oblast\'', dnipropetrovska],
                ['mykola\u00efvs\'ka oblast\'', mikolaivska],
                ['kirovohrads\'ka oblast\'', kirovogradska],
                ['ky\u00efvs\'ka oblast\'', kyivska],
                ['chernivets\'ka oblast\'', chernivetska],
                ['chernihivs\'ka oblast\'', chernigivska],
                ['ky\u00efv', 0]
            ]);
            var options = {region: 'UA', displayMode: 'region', resolution: 'provinces', showZoomOut: true, colors: ['#0066ff'], showTip: true, magnifyingGlass: {enable: true, zoomFactor: 7.5}};

            var chart = new google.visualization.GeoChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>

</head>
<body>
<div style="color: teal; font-size: 30px"> Hello
    <c:if test="${!empty values}">

        <c:forEach items="${values}" var="val">

            <c:out value="${val.key}"/>
        </c:forEach>
    </c:if>

    <c:if test="${!empty valuesForPio}">
        <c:forEach items="${valuesForPio}" var="val">

            <c:out value="${val.value}"/>
        </c:forEach>
    </c:if>
    :)
</div>
<div id="chart_div_pio" style="width: 400px; height: 240px;"></div>
<div id="chart_div" style="width: 600px; height: 400px;"></div>
</body>
</html>