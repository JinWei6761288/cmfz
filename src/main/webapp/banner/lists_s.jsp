<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>

<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>轮播图管理</h1>
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
            url: "${path}/banner/findAll",//请求数据
            datatype: "json",//指定请求数据格式 json格式
            colNames: ["编号", "姓名", "封面", "描述", "状态", "上传时间"],//用来指定显示表格列
            pager: "#pager",//指定分页工具栏
            rowNum: 2,//每页展示2条
            rowList: [2, 4, 6, 8, 10],//下拉列表
            viewrecords: true,//显示总条数
            height: "320px",
            caption: "轮播图列表",
            sortname: 'id',
            editurl: "${path}/banner/edit",//编辑时url(保存 删除 和 修改)
            colModel: [
                {name: "id", hidden: true, editable: false, align: "center"},
                {name: "title", editable: true, align: "center"},
                /*{name:'cover',align:'center',editable: true,formatter:showPicture,edittype:'file',
                    editoptions:{enctype:"multipart/form-data"}},*/
                {
                    name: 'cover',
                    editable: true,
                    edittype: "file",
                    align: "center",
                    formatter: function (value, option, rows) {
                        return "<img style='width:150px;height:140px;' src='${pageContext.request.contextPath}/banner/img/" + rows.cover + "'>";
                    }
                },

                {name: "description", editable: true, align: "center"},
                /* {name:"status",editable:true,align:"center"},*/
                {
                    name: 'status',
                    align: "center",
                    editable: true,
                    edittype: 'select',
                    editoptions: {value: "1:正常;0:冻结"},
                    formatter: function (value, options, row) {
                        if (value == "0") {
                            return "冻结";
                        } else {
                            return "正常";
                        }
                    }
                },

                {name: "createTime", align: "center"}
            ],//用来对当前列进行配置
        }).navGrid('#pager', {edit: true, add: true, del: true, search: false}, {
            //控制修改操作
            closeAfterEdit: true, //修改后关闭框
            afterSubmit: function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        type: "post",
                        fileElementId: "cover",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }

        }, {
            //控制添加
            closeAfterAdd: true,
            afterSubmit: function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${path}/banner/upload",
                        type: "post",
                        fileElementId: "cover",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }

        });
    })
</script>


<!--创建表格-->
<table id="bannerTable"></table>

<!--分页-->
<div id="pager"></div>
