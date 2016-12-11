<%@ page import="java.util.Enumeration" %>
<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-5-27
  Time: 下午8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>修改用户</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <script type="text/javascript" src="jquery/jquery-1.7.1.js"></script>
  <link href="css/basic_layout.css" rel="stylesheet" type="text/css">
  <link href="css/common_style.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="javaScript/commonAll.js"></script>
  <script type="text/javascript" src="jquery/jquery-1.4.4.min.js"></script>
  <script src="My97DatePicker/WdatePicker.js" type="text/javascript" defer="defer"></script>
  <script type="text/javascript" src="artDialog/artDialog.js?skin=default"></script>
  <script type="text/javascript">
    $(document).ready(function () {

      /*
       * 取消
       */
      $("#cancelbutton").click(function () {
        /**  关闭弹出iframe  **/
        window.parent.$.fancybox.close();
      });

      var result = 'null';
      if (result == 'success') {
        /**  关闭弹出iframe  **/
        window.parent.$.fancybox.close();
      }

      document.getElementById("id").value=getId('<%=request.getParameter("value")%>');
      document.getElementById("nickname").value=getNickName('<%=request.getParameter("value")%>');
      document.getElementById("email").value=getEmail('<%=request.getParameter("value")%>');

    });

    function getId(str){
      str=str.toString().split("|")[0];
      if(str==undefined){
        str=''
      }
      return str;
    }
    function getNickName(str){
      var str = str.toString().split("|")[1];
      if(str==undefined){
        str=''
      }
      return str;
    }
    function getEmail(str){
      var str = str.toString().split("|")[2];
      if(str==undefined){
        str=''
      }
      return str;
    }
    function getUserType(str){
      var str = str.toString().split("|")[3];
      if(str==undefined){
        str=''
      }
      return str;
    }

  </script>
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
<form id="submitForm" name="submitForm" action="updateUserTypeOnWeb.do" method="get">
  <input type="hidden" name="id" id="id"/>

  <div id="container">
    <div id="nav_links">
      当前位置：用户信息&nbsp;>&nbsp;<span style="color: #1A5CC6;">修改用户类型</span>

      <div id="page_close">
        <a href="javascript:parent.$.fancybox.close();">
          <img src="images/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
        </a>
      </div>
    </div>
    <div class="ui_content">
      <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
        <tr>
          <td class="ui_text_rt" width="80">用户名字</td>
          <td class="ui_text_lt">
            <input type="text" id="nickname"
                   class="ui_input_txt02" disabled="true"/>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">邮箱</td>
          <td class="ui_text_lt">
            <input type="text" id="email"
                   class="ui_input_txt02" disabled="true"/>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">用户类型</td>
          <td class="ui_text_lt">
            <select id="userType" class="ui_select01" name="userType"
                    onchange="getFyDhListByFyXqCode();">
              <option value="PASSENGER" selected="selected" >游客</option>
              <option value="GUIDE">导游</option>
            </select>
          </td>

        </tr>

        <td>&nbsp;</td>
        <td class="ui_text_lt">
          &nbsp;<input id="submitbutton" type="submit" value="提交" class="ui_input_btn01"/>
          &nbsp;<input id="cancelbutton" type="button" value="取消" class="ui_input_btn01"/>
        </td>
      </table>
    </div>
  </div>
</form>
</body>
</html>