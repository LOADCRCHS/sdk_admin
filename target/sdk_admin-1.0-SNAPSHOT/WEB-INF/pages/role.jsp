<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="layui-form-item">
    <div class="layui-inline">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="search-role-name" autocomplete="off" placeholder="请输入名称"
                   class="layui-input">
        </div>
        <button class="layui-btn" onclick="searchRole()">搜索</button>
    </div>
</div>
<script type="text/html" id="role-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="goEdit"><i class="layui-icon">&#xe654;</i>添加</button>
    </div>
</script>
<script type="text/html" id="role-role-bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit-role">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-role">删除</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="assign-menu">授权</a>
</script>

<table class="layui-table" lay-filter="role-table" id="role-table">
</table>

<script>
    var table;
    layui.use('table', function () {
        table = layui.table;
        table.render({
            id: "role-table",
            elem: '#role-table'
            , toolbar: '#role-head-bar'
            , cellMinWidth: 80
            , url: '<%=request.getContextPath()%>/system/role.html?act=list' //数据接口
            , page: false //开启分页
            , cols: [[ //表头
                {field: 'id', title: 'ID', sort: true, fixed: 'left'}
                , {field: 'name', title: '角色名'}
                , {field: 'remark', title: '备注'}
                , {
                    field: 'status', title: '状态', templet: function (data) {
                        return (data.status === 1) ? '<span class="layui-badge layui-bg-green">有效</span>' : '<span class="layui-badge layui-bg-red" >无效</span>'
                    }
                }, {fixed: 'right', title: '操作', toolbar: '#role-role-bar', width: 180}
            ]]
        });
        //头工具栏事件
        table.on('toolbar(role-table)', function (obj) {
            switch (obj.event) {
                case 'goEdit':
                    layui.use('form', function () {
                        openEditWindow();
                        var form = layui.form;
                        form.render('select');
                    });
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(role-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'del-role': {
                    layer.confirm('删除?', function (index) {
                        $.ajax({
                            url: '<%=request.getContextPath()%>/system/role.html?act=del',
                            data: "id=" + data.id,
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    obj.update({
                                        status: 0
                                    })
                                } else {
                                    alert(result.message)
                                }
                                layer.close(index);
                            }
                        })
                    });
                    break;
                }
                case 'edit-role': {
                    layui.use('form', function () {
                        openEditWindow();
                        var form = layui.form;
                        form.val("role-edit-form", {
                            "id": data.id,
                            "name": data.name,
                            "remark": data.remark,
                            "status": data.status
                        });
                    });
                    break;
                }
                case 'assign-menu': {
                    layer.open({
                        type: 1,
                        content: $('#menu-tree-layer').html(),
                        title: '角色授权'
                        , area: ['400px', '600px'],
                        btn: ['提交', '取消'] //可以无限个按钮
                        , yes: function (index, layero) {
                            var roleId = data.id;
                            var nodes = $('#menu-tree').tree('getChecked', ['checked', 'indeterminate']);
                            var menuIds = "";
                            $.each(nodes, function (i, obj) {
                                menuIds += "&menuIds=" + obj.id
                            });
                            $.ajax({
                                url: '<%=request.getContextPath()%>/system/role.html?act=assign',
                                data: "roleId=" + roleId + "&" + menuIds,
                                method: 'post',
                                success: function (result) {
                                    if (result.status) {
                                        layer.close(index);
                                    } else {
                                        alert(result.message)
                                    }
                                }
                            })
                        }
                    });
                    $.ajax({
                        url: '<%=request.getContextPath()%>/system/role.html?act=role_menu',
                        data: "roleId=" + data.id,
                        method: 'post',
                        success: function (result) {
                            $("#menu-tree").tree({
                                url: "<%=request.getContextPath()%>/system/role.html?act=menu_tree",
                                checkbox: true,
                                onLoadSuccess: function (node, data) {
                                    $.each(result, function (i, obj) {
                                        var node = $('#menu-tree').tree('find', obj);
                                        if ($('#menu-tree').tree('isLeaf', node.target)) {
                                            $('#menu-tree').tree('check', node.target);
                                        }
                                    });
                                }
                            });
                        }
                    });
                    break;
                }
            }
        });

    });

    function openEditWindow() {
        layer.open({
            type: 1,
            content: $('#role-edit-layer').html(),
            title: '编辑角色',
            area: ['400px', '300px'],
            btn: ['提交', '取消'],
            yes: function (index, layero) {
                $.ajax({
                    url: '<%=request.getContextPath()%>/system/role.html?act=edit',
                    data: $("#role-edit-form").serialize(),
                    method: 'post',
                    success: function (result) {
                        if (result.status) {
                            table.reload('role-table', {});
                            layer.close(index);
                        } else {
                            alert(result.message)
                        }
                    }
                })
            }
        });
    }

</script>

<script type="text/html" id="role-edit-layer">
    <form id="role-edit-form" style="width: 80%" class="layui-form" lay-filter="role-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" placeholder="请输入角色名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <input type="text" name="remark" required lay-verify="required" placeholder="请输入备注" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">启用状态</label>
            <div class="layui-input-block">
                <select name="status" lay-verify="required">
                    <option value="" selected>请选择</option>
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </div>
    </form>
</script>

<script type="text/html" id="menu-tree-layer">
    <ul id="menu-tree">
    </ul>
</script>
