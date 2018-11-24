<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layui/css/layui.css">
</head>
<body class="layui-container"  >

<div class="layui-row" style="margin-top:10%">
    <div class="layui-col-xs6 layui-col-md-offset2">
        <form class="layui-form" action="<%=request.getContextPath()%>/noauth/login.html" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-block">
                    <input type="text" name="email" lay-verify="email" autocomplete="off" placeholder="请输入邮箱"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off"
                           class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo2">登陆</button>${message}
                </div>
            </div>
        </form>
    </div>
</div>
<script src="<%=request.getContextPath()%>/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('form', function () {
        var form = layui.form;
        //自定义验证规则
        form.verify({
            password: [/(.+){6,12}$/, '密码必须6到12位']
        });
    });

</script>
</body>
</html>
