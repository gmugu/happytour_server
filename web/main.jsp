<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-5-27
  Time: 下午1:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>首页</title>
  <link rel="stylesheet" type="text/css" href="css/style.css"/>

  <script type="text/javascript">
    /**退出系统**/
    function logout() {
      if (confirm("您确定要退出本系统吗？")) {
        window.location.href = "index.jsp";
      }
    }
  </script>


</head>
<body>

<div class="main1">
  <div class="header ">
    <div id="top_close">
      <a href="javascript:void(0);" onclick="logout();" target="_parent">
        <img alt="退出系统" title="退出系统" src="./images/close.jpg"
             style="position: relative; top: 0px; left: 650px;">
      </a>
    </div>
  </div>
  <div class="content1">
    <div class="button1" onclick="window.location.href='userInfo.jsp'"></div>
    <div class="button2" onclick="window.location.href='scenicInfo.jsp'"></div>

  </div>
</div>
<script type="text/javascript" src="javaScript/placeholder.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="js/belatedpng.js" ></script>
<script type="text/javascript">
  DD_belatedPNG.fix("*");
</script>
<![endif]-->
</body>
</html>