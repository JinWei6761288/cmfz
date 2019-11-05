<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
        vertical-align:text-top;
        padding-top:2px;
    }

</style>
<script>
    $(function () {
        $("#star-show-table").jqGrid({
            url: '${pageContext.request.contextPath}/star/findAll',
            datatype: "json",
            height: 300,
            colNames: ['编号', '真名', '艺名', '照片', '性别', '生日'],
            colModel: [
                {name: "id", hidden: true, editable: false, align: "center"},
                {name: 'name', editable: true, align: "center"},
                {name: 'nickname', editable: true, align: "center"},
                {
                    name: 'photo',
                    editable: true,
                    edittype: "file",
                    align: "center",
                    formatter: function (value, option, rows) {
                        return "<img style='width:100px;height:70px' src='${path}/star/img/" + rows.photo + "'>";
                    }
                },
                {
                    name: 'sex',
                    align: "center",
                    editable: true,
                    edittype: 'select',
                    editoptions: {value: "男:男;女:女"},
                },
                {name: 'bir',align: "center"}
            ],
            styleUI: 'Bootstrap',
            autowidth: true,
            rowNum: 3,
            rowList: [3, 5, 10],
            pager: '#star-page',
            viewrecords: true,
            subGrid: true,
            caption: "所有明星列表",
            editurl: "${path}/star/edit",//编辑时url(保存 删除 和 修改)
            subGridRowExpanded: function (subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${path}/user/findAll?starId=" + id,
                        datatype: "json",
                        colNames: ['编号', '用户名', '昵称', '头像', '电话', '性别', '地址', '签名'],
                        colModel: [
                            {name: "id", hidden: true, editable: false, align: "center"},
                            {name: "dharmaname"},//editable修改添加可编辑
                            {name: "realname"},
                            {
                                name: 'photo', formatter: function (value, option, rows) {
                                    return "<img style='width:100px;height:70px' src='${path}/user/img/" + rows.photo + "'>";
                                }
                            },
                            {name: "phonenum",},
                            {name: "sex"},
                            {
                                name: "privoince",
                                formatter: function (value, options, rData) {
                                    return value + "-" + rData['city']
                                }
                            },
                            {name: "sign"}
                        ],
                        styleUI: "Bootstrap",
                        rowNum: 2,
                        pager: pager_id,
                        autowidth: true,
                        height: '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit: true,
                        add: true,
                        del: true,
                        search: true
                    });
            },

        }).navGrid('#star-page', {edit: true, add: true, del: true, search: false}, {
            //控制修改操作
            closeAfterEdit: true, //修改后关闭框
            afterSubmit: function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${path}/star/upload",
                        type: "post",
                        fileElementId: "photo",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#star-show-table").trigger("reloadGrid");
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
                        url: "${path}/star/upload",
                        type: "post",
                        fileElementId: "photo",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#star-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }

        });
    })

</script>

<div class="panel page-header">
    <h3>展示所有的明星</h3>
</div>
<table id="star-show-table"></table>
<div id="star-page" style="height: 40px;"></div>