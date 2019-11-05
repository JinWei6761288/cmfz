<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>

    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

    <script>
        var goEasy = new GoEasy({
            //接收的appkey
            appkey: "BS-40afb701f7634e8da5740e5cd7858da8"
        });
        goEasy.subscribe({
            //当前的channel名称
            channel: "my_channel",
            onMessage: function (message) {
                alert("Channel:" + message.channel + " content:" + message.content);

            }
        });
    </script>
</head>
<body>

</body>
</html>