<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>

<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>用户管理</h1>
</div>
<%--jqgrid--%>
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
        vertical-align:middle;
        padding-top:2px;
    }
</style>
<script>
    $(function () {
        //创建jqgrid
        $("#bannerTable").jqGrid({
            styleUI: "Bootstrap",//使用bootstrap样式
            autowidth: true,//自动适应父容器
            url: "${path}/user/findAll",//请求数据
            datatype: "json",//指定请求数据格式 json格式
            colNames: ['编号', '用户名', '昵称', '头像', '电话', '性别', '地址', '签名','状态'],//用来指定显示表格列
            pager: "#pager",//指定分页工具栏
            rowNum: 3,//每页展示2条
            rowList: [3, 6, 9],//下拉列表
            viewrecords: true,//显示总条数
            height: "320px",
            caption: "用户列表",
            sortname: 'id',
            editurl: "${path}/user/edit",//编辑时url(保存 删除 和 修改)
            colModel: [
                {name: "id", hidden: true, editable: false},
                {name: "dharmaname"},
                {name: "realname"},
                {
                    name: 'photo', formatter: function (value, option, rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/user/img/" + rows.photo + "'>";
                    }
                },
                {name: "phonenum"},
                {
                    name: 'sex',
                    editable: true,
                    edittype: 'select',
                    editoptions: {value: "男:男;女:女"},
                },
                {
                    name: "privoince",
                    formatter: function (value, options, rData) {
                        return value + "-" + rData['city']
                    }
                },
                {name: "sign"},
                {
                    name: "status",
                    editable: true,
                    edittype: 'select',
                    editoptions: {value: "y:正常;n:冻结"},
                    formatter: function (value, options, row) {
                        if (value == "n") {
                            return "冻结";
                        } else {
                            return "正常";
                        }
                    }
                }
            ],
        }).navGrid('#pager', {edit: true, add: false, del: false, search: false}, {
            //控制修改操作
            closeAfterEdit: true, //修改后关闭框
        });

    })
</script>

<%--标签页--%>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${pageContext.request.contextPath}/user/export">导出用户信息</a></li>
</ul>

<!--创建表格-->
<table id="bannerTable"></table>

<!--分页-->
<div id="pager"></div>
