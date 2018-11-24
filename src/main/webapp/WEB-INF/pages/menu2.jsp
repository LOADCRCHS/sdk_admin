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

    });


    function openEditWindow(id) {
        $.ajax({
            url:'<%=request.getContextPath()%>/menu.html?act=go_edit&id='+id,
            method:'get',
            success:function (d) {
                $('input[name="id"]').val(d.menuTO.id);
                $('input[name="text"]').val(d.menuTO.text);
                $('input[name="url"]').val(d.menuTO.url);
                $('input[name="parentId"]').combotree('loadData',d.menuTree);
            }
        });

        layui.use('layer', function(){

            var layer = layui.layer;
            //loadTree();
            layer.open({
                id: "menu-edit-window",
                type: 1,
                content: $('#menu-edit-layer').html(),
                area: ['500px', '300px'],
                btn: ['提交', '取消'],  //可以无限个按钮
                yes: function (index, layero) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/menu.html?act=edit',
                        method: "post",
                        data: $("#menu-edit-form").serialize(),
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

    function loadTree() {
        <%--$.ajax({--%>
            <%--url:'<%=request.getContextPath() %>/menu.html?act=tree',--%>
            <%--method:'post',--%>
            <%--dataType:'json',--%>
            <%--success:function(data){--%>
                <%--alert("11");--%>
                <%--data.unshift({"id":1,"text":"根节点"})  ;--%>
                <%--$("#menu-edit-layer #parentId").combotree('loadData',data);--%>
            <%--}--%>
        <%--});--%>
        alert("1");
        $('#menu-edit-layer #parentId').combotree({
            url: '<%=request.getContextPath() %>/menu.html?act=tree',
            required: true
        });
    }
</script>
<script type="text/html" id="menu-edit-layer">
    <form id="menu-edit-form" style="width: 80%">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-block">
                <input type="text" name="text" required lay-verify="required" placeholder="请输入菜单名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">父节点</label>
            <div class="layui-input-block">
                <input class="easyui-combotree" name="parentId" id="parentId"
                       style="width:220px;">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">URL</label>
            <div class="layui-input-block">
                <input type="text" name="url" required lay-verify="required" placeholder="请输入url" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
    </form>
</script>
