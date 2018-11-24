<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<button class="layui-btn layui-btn-sm" id="menu-add-btn"><i class="layui-icon">&#xe654;</i>添加</button>
<button class="layui-btn layui-btn-sm layui-btn-danger" id="menu-del-btn"><i class="layui-icon">&#xe640;</i>删除</button>
<ul id="menu-tree">
</ul>

<script type="text/javascript">
    $(function () {
        $("#menu-tree").tree({
            url: "<%=request.getContextPath()%>/menu.html?act=tree",
            checkbox: true,
            cascadeCheck: false,
            onClick: function (node) {
                openEditWindow(node.id)
            }
        });
        $("#menu-add-btn").click(function () {
            openEditWindow("")
        });
        $("#menu-del-btn").click(function () {
            var nodes = $('#menu-tree').tree('getChecked');
            if (nodes.length > 0) {
                if(confirm("确认要删除吗？")){
                    var param="";
                    $.each(nodes,function(i,obj){
                        param+="&ids="+obj.id
                    });
                    $.ajax({
                        url: '<%=request.getContextPath()%>/menu.html?act=delete',
                        method: "post",
                        data: param,
                        success: function (result) {
                            if (result.status) {
                                $("#menu-tree").tree("reload");
                                layer.close(index);
                            } else {
                                alert(result.message)
                            }
                        }
                    })
                }
            }
        })
    });


    function openEditWindow(id) {

        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                id: "menu-edit-window",
                type: 2,
                content: '${pageContext.request.contextPath}/menu.html?act=go_edit&id=' + id,
                area: ['500px', '300px'],
                btn: ['提交', '取消'],  //可以无限个按钮
                yes: function (index, layero) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/menu.html?act=edit',
                        method: "post",
                        data: $("#menu-edit-window iframe").contents().find("#menu-edit-form").serialize(),
                        success: function (result) {
                            if (result.status) {
                                $("#menu-tree").tree("reload");
                                layer.close(index);
                            } else {
                                alert(result.message)
                            }
                        }
                    })
                }
            });
        });
    }

</script>
