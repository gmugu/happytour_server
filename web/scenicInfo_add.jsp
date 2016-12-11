<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-5-27
  Time: 下午8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>新增景区</title>
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
       * 提交
       */
      $("#submitbutton").click(function () {
        if (validateForm()) {
          checkFyFhSubmit();
        }
      });

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
    });

    /** 检测景区名称是否已经存在并是否提交form  **/
    function checkScenicNameSubmit() {
      // 分别获取景区相关信息
      var id = $('#id').val();
      var name = $("#name").val();
      var star = $("#star").val();
      var num = $("#num").val();
      var openTime = $("#openTime").val();
      var picture = $("#picture").val();
      var describe = $("#describe").val();
      var longitude = $("#longitude").val();
      var laititude = $("#laititude").val();
      if (name != "") {
        // 异步判断该房室是否存在，如果已存在，给用户已提示哦
        $.ajax({
          type: "POST",
          url: "**.action",
          data: {
            "某实体类的.name": name,
            "某实体类的.star": star,
            "某实体类的.num": num,
            "某实体类的.openTime": openTime,
            "某实体类的.picture": picture,
            "某实体类的.describe": describe,
            "某实体类的.longitude": longitude,
            "某实体类的.laititude": laititude
          },
          dataType: "text",
          success: function (data) {
// 					alert(data);
            // 如果返回数据不为空，更改“景区信息”
            if (data == "1") {
              art.dialog({
                icon: 'error',
                title: '友情提示',
                drag: false,
                resize: false,
                content: '该景区在系统中已经存在哦，\n',
                ok: true,
              });

              return false;
            } else {
              $("#submitForm").attr("action", "*****.action").submit();
            }
          }
        });
      }
      return true;
    }

    /** 表单验证  **/
    function validateForm() {
      if ($("#name").val() == "") {
        art.dialog({icon: 'error', title: '友情提示', drag: false, resize: false, content: '请填写景区名称', ok: true,});
        return false;
      }
      return true;
    }
  </script>
</head>
<body>
<form id="submitForm" name="submitForm" action="**.action" method="post">
  <input type="hidden" name="id" value="id的数值" id="id"/>

  <div id="container">
    <div id="nav_links">
      当前位置：景区信息&nbsp;>&nbsp;<span style="color: #1A5CC6;">新增景区</span>

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
            <input type="text" id="name" name="某实体类的.name"
                   class="ui_input_txt02"/>（必填）
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">星级</td>
          <td class="ui_text_lt">
            <input type="text" id="star" name="某实体类的.star" value="0"
                   class="ui_input_txt01"/>指数
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">报团数</td>
          <td class="ui_text_lt">
            <input type="text" id="num" name="某实体类的.num" value="0" class="ui_input_txt01"/>个
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">开放时间</td>
          <td class="ui_text_lt">
            <input type="text" id="openTime" name="某实体类的.openTime" class="ui_input_txt02"/>(YYYY-MM-DD)
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">景区图片</td>
          <td class="ui_text_lt">
            <input type="text" id="picture" name="某实体类的.picture" class="ui_input_txt02"/><input id="uoload"
                                                                                                type="button"
                                                                                                value="上传">
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">景区描述</td>
          <td class="ui_text_lt">
            <textarea id="describe" name="某实体类的.describe"></textarea>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">地理经度</td>
          <td class="ui_text_lt">
            <input type="text" id="longitude" name="某实体类的.longitude" class="ui_input_txt02"/>
          </td>
        </tr>
        <tr>
          <td class="ui_text_rt">地理纬度</td>
          <td class="ui_text_lt">
            <input type="text" id="laititude" name="某实体类的.laititude" class="ui_input_txt02"/>
          </td>
        </tr>

        <td>&nbsp;</td>
        <td class="ui_text_lt">
          &nbsp;<input id="submitbutton" type="button" value="提交" class="ui_input_btn01"/>
          &nbsp;<input id="cancelbutton" type="button" value="取消" class="ui_input_btn01"/>
        </td>
        </tr>
      </table>
    </div>
  </div>
</form>
</body>
</html>