<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!DOCTYPE HTML>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layui/css/layui.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/easyUI/themes/default/easyui.css"/>
</head>
<body>
<form id="menu-edit-form" style="width: 80%">
    <input type="hidden" name="id" value="${menu.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-block">
            <input type="text" name="text" required lay-verify="required" placeholder="请输入菜单名" autocomplete="off"
                   class="layui-input"  value="${menu.text}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">父节点</label>
        <div class="layui-input-block">
            <input class="easyui-combotree" name="parentId" id="parentId"
                   style="width:220px;"  value="${menu.parentId}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">URL</label>
        <div class="layui-input-block">
            <input type="text" name="url" required lay-verify="required" placeholder="请输入url" autocomplete="off"
                   class="layui-input"  value="${menu.url}">
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function(){
        $.ajax({
            url:'<%=request.getContextPath() %>/menu.html?act=tree',
            method:'post',
            success:function(data){
                data.unshift({"id":1,"text":"根节点"})  ;
                $("#parentId").combotree('loadData',data)
            }
        })
    })
</script>
</body>
</html>
