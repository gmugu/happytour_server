<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-4-27
  Time: 下午12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>找回密码</title>
    <script type="text/javascript">
        function cheakEqual() {
            var n = document.getElementById('passwd').value;
            var o = document.getElementById('sureInput').value;
            if (n === o) {
                return true;
            }
            return false;
        }

        function onEnsure() {
            if (!cheakEqual()) {
                alert('两次密码不一致');

            }
        }
        function onSubmit() {
            if (!cheakEqual()) {
                alert('两次密码不一致');
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<form action="updatePasswd.do" method="post" id="form" onsubmit="return onSubmit();">
    新密码:<input type="password" id="passwd" name="newPasswd"><br/>
    确 认:<input type="password" id='sureInput' onblur="onEnsure()"><br/>
    <input type="hidden" name="token" value='<%=request.getParameter("token")%>'>
    <input type="submit" value="修改">
</form>

</body>
</html>
