<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layui/css/layui.css">

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/easyUI/themes/default/easyui.css"/>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">layui 后台布局</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    ${sessionScope.SESSION_USER.email}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="logout.html">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <c:forEach items="${sessionScope.SESSION_MENU}" var="menu">
                    <li class="layui-nav-item">
                        <c:choose>
                            <c:when test="${empty menu.children}">
                                <a class="" href="javascript:;">${menu.text}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="" href="javascript:;">${menu.text}</a>
                                <dl class="layui-nav-child">
                                    <c:forEach items="${menu.children}" var="child">
                                        <c:if test="${empty child.children}">
                                            <dd>
                                                <a href="javascript:;"
                                                   onclick="$('#main-content').load('<%=request.getContextPath()%>${child.url}')">
                                                    &nbsp;&nbsp;${child.text}
                                                </a>
                                            </dd>
                                        </c:if>
                                        <c:if test="${not empty child.children}">
                                            <dd>
                                                <a href="javascript:;">&nbsp;&nbsp;${child.text}</a>
                                                <dl class="layui-nav-child">
                                                    <c:forEach items="${child.children}" var="sumChild">
                                                        <dd>
                                                            <a href="javascript:;"
                                                               onclick="$('#main-content').load('${pageContext.request.contextPath}${sumChild.url}')">&nbsp;&nbsp;&nbsp;&nbsp;${sumChild.text}</a>
                                                        </dd>
                                                    </c:forEach>
                                                </dl>
                                            </dd>
                                        </c:if>
                                    </c:forEach>
                                </dl>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;" id="main-content">内容主体区域</div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © layui.com - 底部固定区域
    </div>
</div>
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
</script>
</body>
</html>