<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--当前页面更好支持移动端-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>


    <%--引入bootstrap的样式--%>
    <link rel="stylesheet" href="${path}/statics/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="${path}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${path}/statics/boot/js/bootstrap.min.js"></script>

    <%--引入jqgrid--%>
    <script src="${path}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${path}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%----%>
    <script src="${path}/statics/jqgrid/js/ajaxfileupload.js"></script>

    <script charset="utf-8" src="kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="kindeditor/lang/zh-CN.js"></script>

    <script>
        $(function () {

            /*$("#btn").click(function(){
               //修改中心布局的内容
                $("#centerLayout").load("./user/lists_s.jsp");//引入一张页面到当前页面中
            });*/

        })

    </script>
    <style>
        .item{
            height: 490px
        }
    </style>
</head>
<body>
<!--导航条-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!--导航标题-->
        <div class="navbar-header">
            <a class="navbar-brand" href="showAll.jsp">明星管理系统 <small>v1.0</small></a>
        </div>

        <!--导航条内容-->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎:<font color="aqua">${sessionScope.admin.name}</font></a></li>
                <li><a href="${path}/exit/exit">退出登录 <span class="glyphicon glyphicon-log-out"></span> </a></li>
            </ul>
        </div>
    </div>
</nav>
<!--页面主体内容-->
<div class="container-fluid">
    <!--row-->
    <div class="row">

        <!--菜单-->
        <div class="col-sm-2">

            <!--手风琴控件-->
            <div class="panel-group" id="accordion" >

                <!--面板-->
                <div class="panel panel-success">
                    <div class="panel-heading" role="tab" id="userPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bannerList" aria-expanded="true" aria-controls="collapseOne">
                               <center>轮播图管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="bannerList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/banner/lists_s.jsp');" id="btn"><center>所有轮播图</center></a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--专辑面板-->
                <div class="panel panel-primary">
                    <div class="panel-heading" role="tab" id="categoryPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#categoryLists" aria-expanded="true" aria-controls="collapseOne">
                                <center>专辑管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="categoryLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/album/showAll.jsp')"><center>所有专辑</center></a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!--面板-->
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="stars">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bookLists" aria-expanded="true" aria-controls="collapseOne">
                                <center>文章管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="bookLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/article/showAll.jsp')"><center>所有文章</center></a></li>
                            </ul>
                        </div>
                    </div>
                </div>


                <!--面板-->
                <div class="panel panel-warning">
                    <div class="panel-heading" role="tab" id="orderPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#orderLists" aria-expanded="true" aria-controls="collapseOne">
                                <center>用户管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="orderLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/user/showAll.jsp')"><center>所有用户</center></a></li><br/>
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/user/userEcharts.jsp')"><center>注册趋势</center></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--面板-->
                <div class="panel panel-danger">
                    <div class="panel-heading" role="tab" id="bookPanel">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#star" aria-expanded="true" aria-controls="collapseOne">
                                <center>明星管理 </center>
                            </a>
                        </h4>
                    </div>
                    <div id="star" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item"><a href="javascript:$('#centerLayout').load('${path}/star/showAll.jsp')"><center>所有明星</center></a></li>
                            </ul>
                        </div>
                    </div>
                </div>


            </div>
        </div>




        <!--中心布局-->
        <div class="col-sm-10" id="centerLayout">

            <!--巨幕-->
            <div class="jumbotron">
                <p><font style="color: #31b0d5">欢迎来到明星后台管理系统! </font></p>
            </div>
            <!--面板-->
             <%--<img src="login/4.jpg" class="img-rounded" alt="圆角图片" width="100%" height="400">--%>
            <div id="myCarousel" class="carousel slide" >
                <div class="carousel-inner">
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to ="0" class="active" ></li>
                        <li data-target="#myCarousel" data-slide-to ="1"></li>
                        <li data-target="#myCarousel" data-slide-to ="2"></li>
                        <li data-target="#myCarousel" data-slide-to ="3"></li>
                        <li data-target="#myCarousel" data-slide-to ="4"></li>
                    </ol>


                    <div class="item active">
                        <a href="#">
                            <img src="login/lbt/4.jpg" alt="第一张" class="img-rounded" width="100%">
                        </a>
                    </div>
                    <div class="item">
                        <a href="#">
                            <img src="login/lbt/2.jpg" alt="第二张" class="img-rounded" width="100%">
                        </a>
                    </div>
                    <div class="item">
                        <a href="#">
                            <img src="login/lbt/3.jpg" alt="第三张" class="img-rounded" width="100%">
                        </a>
                    </div>
                    <div class="item">
                        <a href="#">
                            <img src="login/lbt/1.jpg" alt="第四张" class="img-rounded" width="100%">
                        </a>
                    </div>
                    <div class="item">
                        <a href="#">
                            <img src="login/lbt/5.jpg" alt="第五张" class="img-rounded" width="100%">
                        </a>
                    </div>
                    <a href="#myCarousel" data-slide="prev" class="carousel-control left"><!-- ‹ -->
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </a><!-- 左箭头 -->
                    <a href="#myCarousel" data-slide="next" class="carousel-control right"><!-- › -->
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </a><!-- 右箭头 -->
                </div>


                <script type="text/javascript">
                    $(function(){
                        //轮播图自动播放
                        $('#myCarousel').carousel({
                            interval: 3000,//自动播放4s

                        });

                    });
                </script>
            </div>

        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container">
                <p style="padding-top: 5px"><center style="color: darkorchid">明星后台管理系统@百知教育  2019.10.25</center></p>
            </div>
        </nav>
        </div>
    </div>
</div>
<!--巨幕-->

</body>
</html>