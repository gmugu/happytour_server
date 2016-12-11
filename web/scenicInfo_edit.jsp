<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-5-27
  Time: 下午8:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>修改景区</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <script type="text/javascript" src="jquery/jquery-1.7.1.js"></script>
  <link href="css/basic_layout.css" rel="stylesheet" type="text/css">
  <link href="css/common_style.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="javaScript/commonAll.js"></script>
  <script type="text/javascript" src="jquery/jquery-1.4.4.min.js"></script>
  <script src="My97DatePicker/WdatePicker.js" type="text/javascript" defer="defer"></script>
  <script type="text/javascript" src="artDialog/artDialog.js?skin=default"></script>
  <script type="text/javascript">
    /** 表单验证  **/
    function validateForm() {
      if ($("#name").val() == "") {
        art.dialog({icon: 'error', title: '友情提示', drag: false, resize: false, content: '请填写景区名称', ok: true,});
        return false;
      }
      return true;
    }
    $(document).ready(function () {
      var data = JSON.parse('<%=request.getParameter("data")%>');
      document.getElementById('id').value = data.id;
      document.getElementById('name').value = data.name;
      document.getElementById('star').value = data.star;
      document.getElementById('num').value = data.num;
      document.getElementById('openTime').value = data.openTime==null?"":data.openTime;
      document.getElementById('describe').value = data.describe==null?"":date.describe;
      document.getElementById('longitude').value = data.longitude==null?"":data.longitude;
      document.getElementById('latitude').value = data.latitude==null?"":data.latitude;


      /*
       * 取消
       */
      $("#cancelbutton").click(function () {
        /**  关闭弹出iframe  **/
//        window.parent.$.fancybox.close();
        window.close();
      });

      var result = 'null';
      if (result == 'success') {
        /**  关闭弹出iframe  **/
        window.parent.$.fancybox.close();
      }
    });


  </script>
</head>
<body>
<form id="submitForm" name="submitForm" action="saveOrUpdateScenicOnWeb.do" method="post" enctype="multipart/form-data">
  <input type="hidden" name="id" id="id"/>

  <div id="container">
    <div id="nav_links">
      当前位置：景区信息&nbsp;>&nbsp;<span style="color: #1A5CC6;">修改景区</span>

      <div id="page_close">
        <a href="javascript:parent.$.fancybox.close();">
          <img src="images/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
        </a>
      </div>
    </div>
    <div class="ui_content">
      <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
        <tr>
          <td class="ui_text_rt" width="80">景区名</td>
          <td class="ui_text_lt">
            <input type="text" id="name" name="name"
                   class="ui_input_txt02"/>（必填）
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">星级</td>
          <td class="ui_text_lt">
            <input type="text" id="star" name="star" value="0"
                   class="ui_input_txt01"/>指数
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">评论人数</td>
          <td class="ui_text_lt">
            <input type="text" id="num" name="num" value="0" class="ui_input_txt01"/>个
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">开放时间</td>
          <td class="ui_text_lt">
            <input type="text" id="openTime" name="openTime" class="ui_input_txt02"/>(YYYY-MM-DD)
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">景区图片</td>
          <td class="ui_text_lt">
            <input type="file" id="image" name="image" class="ui_input_txt02"/>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">景区描述</td>
          <td class="ui_text_lt">
            <textarea id="describe" name="describe"></textarea>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">地理经度</td>
          <td class="ui_text_lt">
            <input type="text" id="longitude" name="longitude" class="ui_input_txt02"/>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">地理纬度</td>
          <td class="ui_text_lt">
            <input type="text" id="latitude" name="latitude" class="ui_input_txt02"/>
          </td>
        </tr>


        <td>&nbsp;</td>
        <td class="ui_text_lt">
          &nbsp;<input id="submitbutton" type="submit" value="提交" on class="ui_input_btn01" onclick="return validateForm()"/>
          &nbsp;<input id="cancelbutton" type="button" value="取消" class="ui_input_btn01" onclick="window.close()"/>
        </td>
        </tr>
      </table>
    </div>
  </div>
</form>
</body>
</html>