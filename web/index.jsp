<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-4-18
  Time: 上午10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>管理员登陆界面</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <script type="text/javascript">
        window.onload = function () {
            var input1 =
                    document.getElementById("div1");
            var ss = input1.value;
            if (ss != "") {
                alert(ss);
            }
        }
    </script>
</head>

<body>
<input type="hidden"
       name="msg" id="div1" value="${msg}"/>

<div class="main">
    <div class="header hide"> 基于Android的游踪管理系统 Beta 1.0</div>
    <div class="content">
        <div class="title hide">管理登录</div>
        <form name="login" action="loginOnWeb.do" method="post">
            <fieldset>
                <div class="input">
                    <input class="input_all name" name="username" id="name" placeholder="用户名" type="text"
                           onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'"
                           maxLength="24"/>
                </div>
                <div class="input">
                    <input class="input_all password" name="password" id="password" type="password" placeholder="密码"
                           onFocus="this.className='input_all password_now';"
                           onBlur="this.className='input_all password'" maxLength="24"/>
                </div>
                <div class="checkbox">
                    <input type="checkbox" name="remember" id="remember"/><label
                        for="remember"><span>记住密码</span></label>
                </div>
                <div class="enter">
                    <input class="button hide" type="submit" value="登录"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<script type="text/javascript" src="javaScript/placeholder.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="javaScript/belatedpng.js"></script>
<script type="text/javascript">
    DD_belatedPNG.fix("*");
</script>
<![endif]--></body>
</html>
