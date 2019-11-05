<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>


<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
        vertical-align:middle;
        padding-top:2px;
    }

</style>
<%--<pre name="code" class="html"><script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        pickDate: true,
        pickTime: false,
        minView: 'month',
        todayBtn: true,
        todayHighlight: true
    });
</script>
<pre name="code" class="html"><input type="text" id="date1" name="date1"  class="form_datetime" />--%>
<script>
    $(function () {
        $("#star-show-table").jqGrid({
            url: '${pageContext.request.contextPath}/album/findAll',
            datatype: "json",
            height: 300,
            colNames: ['编号', '标题','封面', '作者', '章节数', '得分', '简介', '发行时间'],
            colModel: [
                {name: "id", hidden: true, editable: false, align: "center"},
                {name: 'title', editable: true},
                {
                    name: 'cover',
                    editable: true,
                    edittype: "file",
                    formatter: function (value, option, rows) {
                        return "<img style='width:130px;height:100px' src='${path}/album/img/" + rows.cover + "'>";
                    }
                },
                {
                    name: 'author',
                    index : 'author',
                    editable : true,
                    edittype : "select",
                    editoptions : {
                        value : star()
                    },
                    formatter : 'select',

                },
                {name: 'count', editable: false},
                {name: 'score',editable: true},
                {name: 'brief',editable: true},

                {name: 'createDate',editable: true,edittype: "date"}
            ],
            styleUI: 'Bootstrap',
            autowidth: true,/*自适应*/
            rowNum: 3,
            rowList: [3, 5, 10],
            pager: '#star-page',
            viewrecords: true,
            subGrid: true,
            caption: "所有专辑列表",
            editurl: "${path}/album/edit",//编辑时url(保存 删除 和 修改)
            subGridRowExpanded: function (subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${path}/chapter/findAllChapter?albumId=" + id,
                        datatype: "json",
                        colNames: ['编号', '章节名称', '大小', '时长', '文件名', '创建时间','在线播放'],
                        colModel: [
                            {name: "id", hidden: true, editable: false, align: "center"},
                            {name: "title", editable: true},//editable修改添加可编辑
                            {name: "size"},
                            {name: "duration",},
                            {name: "name",editable: true,edittype:"file"},
                            {name: "createDate"},
                            {name : "operation",width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/album/music/"+rows.name+"' >\n" +
                                        "</audio>";
                                }}
                        ],
                        styleUI: "Bootstrap",
                        rowNum: 2,
                        pager: pager_id,
                        autowidth: true,
                        height: '100%',
                        editurl:"${path}/chapter/edit?albumId="+id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit: false,
                        add: true,
                        del: false,
                        search: false
                    },{},{
                    //控制添加
                        closeAfterAdd:true,
                        afterSubmit:function (response) {
                            var status = response.responseJSON.status;
                            if(status){
                                var cid = response.responseJSON.message;
                                $.ajaxFileUpload({
                                    url:"${path}/chapter/upload",
                                    type:"post",
                                    fileElementId:"name",
                                    data:{id:cid,albumId:id},
                                    success:function () {
                                        $("#"+subgrid_table_id).trigger("reloadGrid");
                                    }
                                })
                            }
                            return "123";
                        }
                    });

            }

        }).navGrid('#star-page', {edit: true, add: true, del: true, search: false}, {
            //控制修改操作
            closeAfterEdit: true, //修改后关闭框
            afterSubmit: function (data) {
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${path}/album/upload",
                        type: "post",
                        fileElementId: "cover",
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
                        url: "${path}/album/upload",
                        type: "post",
                        fileElementId: "cover",
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
    //获取明星下拉列表
    var datajaon1;
    var str1 = "";
    function kechengSelectDatahz() {
        $.ajax({
            url : "${path}/star/findAllStarName",
            type : "post",
            // data: {
            // zid: zid
            // },
            async : false,
            success : function(data) {
                if (data != null) {
                    datajaon1 = data
                }
            },
            error: function () {
                alert('请求失败');
            }
        });
    }
    //获取明星下拉列表
    function star() {
        kechengSelectDatahz();
        var jsonobj = eval(datajaon1.rows);
        var length = jsonobj.length;
        for (var i = 0; i < length; i++) {
            if (i != length - 1) {
                str1 += jsonobj[i].name + ":" + jsonobj[i].name + ";";
            } else {
                str1 += jsonobj[i].name + ":" + jsonobj[i].name;// 这里是option里面的
                // value:label
            }
        }
        return str1;
    }

    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

</script>

<div class="panel page-header">
    <h3>展示所有的明星</h3>
</div>
<table id="star-show-table"></table>
<div id="star-page" style="height: 40px;"></div>