<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<%=request.getContextPath()%>/game/game_cp.html?act=list" method="post" id="search-game-cp" class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">名称:</label>
            <div class="layui-input-inline">
                <input type="text" name="name"  autocomplete="off" placeholder="请输入名称" class="layui-input">
            </div>

            <label class="layui-form-label">状态:</label>
            <div class="layui-input-inline">
                <select name="status" lay-filter="game-cp-status">
                    <option value="">请选择</option>
                    <option value="0">无效</option>
                    <option value="1">有效</option>
                </select>
            </div>
            <button class="layui-btn" lay-submit lay-filter="search-game-cp">搜索</button>
            <button class="layui-btn" type="reset">重置</button>

        </div>
    </div>
</form>
<table class="layui-table" lay-filter="game-cp-table" id="game-cp-table">
</table>
<script type="text/html" id="game-cp-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="goEdit"><i class="layui-icon">&#xe654;</i>添加</button>
    </div>
</script>
<script type="text/html" id="game-cp-row-bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit-game-cp">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-game-cp">删除</a>

</script>
<script type="text/html" id="game-cp-status">
    <input type="checkbox" id="status" disabled name="status" value="{{d.id}}" lay-skin="switch" lay-text="有效|无效" lay-filter="status-filter" {{ d.status == 1 ? 'checked' : '' }}>
</script>



<script>
    var table;

    layui.use('table', function () {
        table = layui.table;
        table.render({
            id: "game-cp-table",
            elem: '#game-cp-table'
            ,toolbar:'#game-cp-head-bar'
            , cellMinWidth: 80
            , url: '<%=request.getContextPath()%>/game/game_cp.html?act=list' //数据接口
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: [ 'prev', 'page', 'next', 'count', 'skip','limit'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                ,groups: 5 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页,
                ,limits:[5,10,15,20,25,30]
                ,limit:5

            }
            , cols: [[
                {field: 'id', title: 'ID', sort: true, fixed: 'left',width:80}
                , {field: 'name', title: '厂商名'}

                , {field: 'secretKey', title: '游戏密钥'}
                , {field: 'noticeKey', title: '支付密钥'}
                , {field: 'password', title: '密码'}
                , {field: 'createdDate', title: '创建时间'}
                , {
                    field: 'status', title: '状态' ,templet: '#game-cp-status'
                }
                , {fixed: 'right', title: '操作', toolbar: '#game-cp-row-bar'}
            ]]
        });
        //头工具栏事件
        table.on('toolbar(game-cp-table)', function (obj) {
            switch (obj.event) {
                case 'goEdit':
                    layui.use('form', function () {
                        openEditGameCpWindow();
                        var form = layui.form;
                        form.render('select');
                    });
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(game-cp-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'edit-game-cp': {
                    layui.use('form', function () {
                        openEditGameCpWindow();
                        var form = layui.form;
                        form.val("game-cp-edit-form", {
                            "id": data.id,
                            "name": data.name,
                            "password":data.password,
                            "noticeKey": data.noticeKey,
                            "secretKey":data.secretKey,
                            "status": data.status
                        });
                        form.render('select');
                    });
                    break;
                }
                case 'del-game-cp':{
                    alert(1);
                    $.ajax({
                        url: '<%=request.getContextPath()%>/game/game_cp.html?act=del',
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
                        }
                    });
                    break;
                }
            }
        });
        layui.use('form',function(){
            var form = layui.form;
            form.render();
            form.on('submit(search-game-cp)', function(data){
                table.reload('game-cp-table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: data.field
                });
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        });
        function openEditGameCpWindow() {
            layer.open({
                type: 1,
                content: $('#game-cp-edit-layer').html(),
                title: '编辑角色',
                area: ['400px', '300px'],
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/game/game_cp.html?act=edit',
                        data: $("#game-cp-edit-form").serialize(),
                        method: 'post',
                        success: function (result) {
                            if (result.status) {
                                table.reload('game-cp-table', {});
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

<script type="text/html" id="game-cp-edit-layer">
    <form id="game-cp-edit-form" style="width: 80%" class="layui-form" lay-filter="game-cp-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">厂商名</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" placeholder="请输入真实姓名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <%--<div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password" required lay-verify="required" placeholder="密钥自动生成,无需输入" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">游戏密钥</label>
            <div class="layui-input-block">
                <input type="password" name="secretKey" required lay-verify="required" placeholder="密钥自动生成,无需输入" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">支付密钥</label>
            <div class="layui-input-block">
                <input type="password" name="noticeKey" required lay-verify="required" placeholder="密钥自动生成,无需输入" autocomplete="off"
                       class="layui-input">
            </div>
        </div>--%>
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
