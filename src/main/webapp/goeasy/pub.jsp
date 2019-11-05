<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Document</title>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        var goEasy = new GoEasy({
            //发布的appkey
            appkey: "BC-73401fae92ae4377ad88afbd19a96afb"
        });
        goEasy.publish({
            //当前的channel名称
            channel: "my_channel",
            //发送（发布）的内容
            message: "新店开业,欢迎来玩!"
        });
    </script>
</head>
<body>

</body>
</html>