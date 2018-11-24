<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<%=request.getContextPath()%>/system/adminuser.html?act=list" method="post" id="search-admin" class="layui-form">
<div class="layui-form-item">
    <div class="layui-inline">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="realName"  autocomplete="off" placeholder="请输入名称" class="layui-input">
        </div>
        <label class="layui-form-label">邮箱:</label>
        <div class="layui-input-inline">
            <input type="text" name="email"  autocomplete="off" placeholder="请输入email" class="layui-input" >
        </div>
        <label class="layui-form-label">状态:</label>
        <div class="layui-input-inline">
            <select name="status" lay-filter="user-status">
                <option value="">请选择</option>
                <option value="0">无效</option>
                <option value="1">有效</option>
            </select>
        </div>
        <button class="layui-btn" lay-submit lay-filter="search-admin">搜索</button>
        <button class="layui-btn" type="reset">重置</button>
    </div>
</div>
</form>
<table class="layui-table" lay-filter="adminuser-table" id="adminuser-table">
</table>
<script type="text/html" id="user-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="goEdit"><i class="layui-icon">&#xe654;</i>添加</button>
    </div>
</script>
<script type="text/html" id="user-row-bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit-adminuser">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-adminuser">删除</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="assign-role">授权</a>
</script>
<script type="text/html" id="role-tree-layer">
    <ul id="role-tree">
    </ul>
</script>
<script>
    var table;

    layui.use('table', function () {
        table = layui.table;
        table.render({
            id: "adminuser-table",
            elem: '#adminuser-table'
            ,toolbar:'#user-head-bar'
            , cellMinWidth: 80
            , url: '<%=request.getContextPath()%>/system/adminuser.html?act=list' //数据接口
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: [ 'prev', 'page', 'next', 'count', 'skip','limit'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                ,groups: 5 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页,
                ,limits:[5,10,15,20,25,30]
                ,limit:5

            }
            , cols: [[ //表头
                {field: 'id', title: 'ID', sort: true, fixed: 'left',width:80}
                , {field: 'email', title: '邮箱'}
                , {field: 'realName', title: '姓名'}
                , {
                    field: 'status', title: '状态', templet: function (data) {
                        return (data.status === 1) ? '<span class="layui-badge layui-bg-green">有效</span>' : '<span class="layui-badge layui-bg-red" >无效</span>'
                    }
                }, {fixed: 'right', title: '操作', toolbar: '#user-row-bar'}
            ]]
        });
        //头工具栏事件
        table.on('toolbar(adminuser-table)', function (obj) {
            switch (obj.event) {
                case 'goEdit':
                    layui.use('form', function () {
                        openEditUserWindow();
                        var form = layui.form;
                        form.render('select');
                    });
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(adminuser-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'del-adminuser': {
                    layer.confirm('真的删除行么', function (index) {
                        $.ajax({
                            url: '<%=request.getContextPath()%>/system/adminuser.html?act=del',
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
                case 'edit-adminuser': {
                    layui.use('form', function () {
                        openEditUserWindow();
                        var form = layui.form;
                        form.val("adminuser-edit-form", {
                            "id": data.id,
                            "email": data.email,
                            "realName": data.realName,
                            "status": data.status
                        });
                        form.render('select');
                    });
                    break;
                }
                case 'assign-role': {
                    layer.open({
                        type: 1,
                        content: $('#role-tree-layer').html(),
                        title: '用户授权'
                        , area: ['400px', '600px'],
                        btn: ['提交', '取消'] //可以无限个按钮
                        , yes: function (index, layero) {
                            var userId = data.id;
                            var nodes = $('#role-tree').tree('getChecked', ['checked']);
                            var roleIds = "";
                            $.each(nodes, function (i, obj) {
                                roleIds += "&roleIds=" + obj.id
                            });
                            $.ajax({
                                url: '<%=request.getContextPath()%>/system/adminuser.html?act=assign',
                                data: "userId=" + userId + "&" + roleIds,
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
                        url: '<%=request.getContextPath()%>/system/adminuser.html?act=user_role',
                        data: "userId=" + data.id,
                        method: 'post',
                        success: function (result) {
                            $("#role-tree").tree({
                                url: "<%=request.getContextPath()%>/system/adminuser.html?act=role_list",
                                checkbox: true,
                                formatter:function(node){
                                    return node.name;
                                },
                                onLoadSuccess: function (node, data) {
                                    $.each(result, function (i, obj) {
                                        var node = $('#role-tree').tree('find', obj);
                                        $('#role-tree').tree('check', node.target);
                                    })
                                }
                            });
                        }
                    });
                    break;
                }
            }
        });
        layui.use('form',function(){
            var form = layui.form;
            form.render();
            form.on('submit(search-admin)', function(data){
                table.reload('adminuser-table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: data.field
                });
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        });
        function openEditUserWindow() {
            layer.open({
                type: 1,
                content: $('#adminuser-edit-layer').html(),
                title: '编辑角色',
                area: ['400px', '300px'],
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/system/adminuser.html?act=edit',
                        data: $("#adminuser-edit-form").serialize(),
                        method: 'post',
                        success: function (result) {
                            if (result.status) {
                                table.reload('adminuser-table', {});
                                layer.close(index);
                            } else {
                                alert(result.message)
                            }
                        }
                    })
                }
            });
        }
    })
</script>

<script type="text/html" id="adminuser-edit-layer">
    <form id="adminuser-edit-form" style="width: 80%" class="layui-form" lay-filter="adminuser-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">邮箱</label>
            <div class="layui-input-block">
                <input type="text" name="email" required lay-verify="required" placeholder="请输入邮箱" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">真实姓名</label>
            <div class="layui-input-block">
                <input type="text" name="realName" required lay-verify="required" placeholder="请输入真实姓名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off"
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