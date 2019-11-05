<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>

<html lang="en">
<head>
    <title>Document</title>
    <script src="${path}/user/echarts.min.js"></script>
    <script src="${path}/statics/boot/js/jquery-3.3.1.min.js"></script>


</head>
<body>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <script>
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        $.post("${path}/user/UserEcharts",function(result) {
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '今年用户注册趋势'
                },
                tooltip: {},
                legend: {
                    data: ['男', '女']
                },
                xAxis: {
                    data: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    axisLabel: {
                        interval: 0,
                        rotate: 40,
                    }
                },
                yAxis: {},
                series: [{
                    name: '男',
                    type: 'bar',//bar:柱状图
                    data: result.man
                }, {
                    name: '女',
                    type: 'bar',//bar:柱状图
                    data: result.nv
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },"json")


    </script>

</body>
</html>