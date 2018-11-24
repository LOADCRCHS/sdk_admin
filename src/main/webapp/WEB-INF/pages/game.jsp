<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="<%=request.getContextPath()%>/game/game.html?act=list" method="post" id="search-game" class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">游戏名称:</label>
            <div class="layui-input-inline">
                <input type="text" name="name"  autocomplete="off" placeholder="请输入名称" class="layui-input">
            </div>
            <label class="layui-form-label">厂商:</label>
            <div class="layui-input-inline">
                <select name="cpId" lay-filter="game-cpId">
                    <option value="">请选择</option>
                </select>
            </div>


            <label class="layui-form-label">状态:</label>
            <div class="layui-input-inline">
                <select name="status" lay-filter="game-status">
                    <option value="">请选择</option>
                    <option value="0">无效</option>
                    <option value="1">有效</option>
                </select>
            </div>
            <button class="layui-btn" lay-submit lay-filter="search-game">搜索</button>
            <button class="layui-btn" type="reset">重置</button>

        </div>
    </div>
</form>
<table class="layui-table" lay-filter="game-table" id="game-table">
</table>
<script type="text/html" id="game-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="goEdit"><i class="layui-icon">&#xe654;</i>添加</button>
    </div>
</script>
<script type="text/html" id="game-row-bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit-game">编辑</a>
    <%--<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-game">删除</a>--%>

</script>
<script type="text/html" id="game-status">
    <input type="checkbox" id="status" disabled name="status" value="{{d.id}}" lay-skin="switch" lay-text="有效|无效"  {{ d.status == 1 ? 'checked' : '' }}>
</script>


<script>
    var table;

    layui.use('table', function () {
        table = layui.table;
        table.render({
            id: "game-table",
            elem: '#game-table'
            ,toolbar:'#game-head-bar'
            , cellMinWidth: 80
            , url: '<%=request.getContextPath()%>/game/game.html?act=list' //数据接口
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: [ 'prev', 'page', 'next', 'count', 'skip','limit'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                ,groups: 5 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页,
                ,limits:[5,10,15,20,25,30]
                ,limit:10

            }
            , cols: [[
                {field: 'id', title: 'ID', sort: true, fixed: 'left',width:70}
                , {field: 'cpId', title: '厂商ID',width:80}
                , {field: 'seqNum', title: '游戏序号',width:90}
                , {field: 'name', title: '名称'}
                , {field: 'description', title: '描述'}
                , {field: 'game_category_id', title: '游戏类别ID'}
                , {field: 'game_state', title: '运营状态' ,templet: function (data) {
                        return (data.game_state === 1) ? '<span class="layui-badge layui-bg-green">有效</span>' : '<span class="layui-badge layui-bg-red" >无效</span>'

                    }
                }
                , {field: 'status', title: '状态' ,templet: '#game-status'}
                , {field: 'update_date', title: '更新时间'}
                , {field: 'created_date', title: '创建时间'}
                , {field: 'repairContent', title: '维护信息'}
                , {field: 'repairStatus', title: '维护状态' ,templet: function (data) {
                        return (data.repairStatus === 1) ? '<span class="layui-badge layui-bg-green">有效</span>' : '<span class="layui-badge layui-bg-red" >无效</span>'

                    }
                }
                , {field: 'version', title: '版本号'}
                , {field: 'download_url', title: '下载地址'}
                , {fixed: 'right', title: '操作', toolbar: '#game-row-bar'}
            ]]
        });
        //头工具栏事件
        table.on('toolbar(game-table)', function (obj) {
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
        table.on('tool(game-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'edit-game': {
                    layui.use('form', function () {
                        openEditGameCpWindow();
                        var form = layui.form;
                        form.val("game-edit-form", {
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
                case 'del-game':{
                    alert(1);
                    $.ajax({
                        url: '<%=request.getContextPath()%>/game/game.html?act=del',
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
            form.on('submit(search-game)', function(data){
                table.reload('game-table', {
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
                content: $('#game-edit-layer').html(),
                title: '编辑角色',
                area: ['400px', '300px'],
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/game/game.html?act=edit',
                        data: $("#game-edit-form").serialize(),
                        method: 'post',
                        success: function (result) {
                            if (result.status) {
                                table.reload('game-table', {});
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

<script type="text/html" id="game-edit-layer">
    <form id="game-edit-form" style="width: 80%" class="layui-form" lay-filter="game-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">游戏名</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" placeholder="请输入真实姓名" autocomplete="off"
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
