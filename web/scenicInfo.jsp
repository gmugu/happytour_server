<%@ page import="com.gmugu.happyhour.message.UserInfoModel" %>
<%@ page import="com.gmugu.happytour.comment.constant.SessionKeys" %>
<%--
  Created by IntelliJ IDEA.
  User: mugu
  Date: 16-5-27
  Time: 下午7:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  UserInfoModel userInfoModel= (UserInfoModel) request.getSession().getAttribute(SessionKeys.USER_INFO);
  String nickName;
  if (userInfoModel!=null){
    nickName=userInfoModel.getNickname();
  }else {
    nickName="Admin";
  }

%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
  <meta charset="UTF-8">
  <title>景区信息</title>
  <link rel="stylesheet" type="text/css" href="css/main_css.css"/>
  <link href="css/basic_layout.css" rel="stylesheet" type="text/css">
  <link href="css/common_style.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="jquery/jquery-1.7.1.js"></script>
  <script type="text/javascript" src="javaScript/commonAll.js"></script>
  <script src="jquery-easyui-1.4.5/jquery.min.js" type="text/javascript"></script>
  <script src="jquery-easyui-1.4.5/jquery.easyui.min.js" type="text/javascript"></script>
  <link href="jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
  <link href="jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet" type="text/css"/>
  <script src="jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
  <script type="text/javascript" src="fancybox/jquery.fancybox-1.3.4.js"></script>
  <script type="text/javascript" src="fancybox/jquery.fancybox-1.3.4.pack.js"></script>
  <link rel="stylesheet" type="text/css" href="css/jquery.fancybox-1.3.4.css" media="screen"/>
  <script type="text/javascript" src="artDialog/artDialog.js?skin=default"></script>
  <script type="text/javascript">
    /**
     * Created by mengqiumin-ext on 2016/5/25.
     */
    function update(id){

      var data = $('#scenicInfoList').datagrid("getData");
      var rs=data.rows;
      for(var i=0;i<rs.length;++i){
        if(rs[i].id==id){
          var q=JSON.stringify(rs[i]);
          var form=document.createElement("form");
          form.id="form1";
          form.action="scenicInfo_edit.jsp";
          form.method="post";
          var input=document.createElement("input");
          input.type = "hidden";
          input.id="data";
          input.name='data';
          input.value=q;
          form.appendChild(input);
          document.body.appendChild(form);
          form.submit();
          document.body.removeChild(form);
          break;
        }
      }

    }
    function loadData() {
      $('#scenicInfoList').datagrid({
        url: 'getScenicInfoOnWeb.do',
        method: 'post',
        loadMsg: "数据装载中..",
        nowrap: false,
        fitColumns: true,
        pagination: true,
        singleSelect: false,
        rownumbers: true,
        height: 'auto',
        striped: true,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", // 解决中文乱码
        columns: [[{
          field: 'crudOperation',
          title: '操作',
          width: 150,
          align: 'center',
          sortable: true,
          formatter: function (value) {
            var operate = "<a href='javascript:void(0);' class='edit' onclick='update("+value+")'>修改</a>" + "  " + "<a href='javaScript:del("+value+");'>删除</a>";
            return operate;
          }
        }, {
          field: 'id',
          title: '编号',
          width: 150,
          align: 'center'
        }, {
          field: 'name',
          title: '景区名',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'star',
          title: '星级',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'num',
          title: '评论人数',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'openTime',
          title: '开放时间',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'picture',
          title: '景区图片',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'describe',
          title: '景区描述',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'longitude',
          title: '地理经度',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'latitude',
          title: '地理纬度',
          width: 150,
          align: 'center',
          sortable: true
        }
        ]]

      });

      $('#scenicInfoList').datagrid('getPager').pagination({
        pageSize: 10,// 每页显示的记录条数，默认为10
        pageList: [10, 20, 30, 50],// 可以设置每页记录条数的列表
        pageNumber: 1,
        beforePageText: '第',// 页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'

      });

    }
    $(document).ready(loadData);
  </script>
  <script type="text/javascript">
    /**退出系统**/
    function logout() {
      if (confirm("您确定要退出本系统吗？")) {
        window.location.href = "index.jsp";
      }
    }

    /**返回首页**/
    function back_main() {
      if (confirm("你要返回首页么？")) {
        window.location.href = "main.jsp";
      }
    }

    /**获得当前日期**/
    function getDate01() {
      var time = new Date();
      var myYear = time.getFullYear();
      var myMonth = time.getMonth() + 1;
      var myDay = time.getDate();
      if (myMonth < 10) {
        myMonth = "0" + myMonth;
      }
      document.getElementById("yue_fen").innerHTML = myYear + "." + myMonth;
      document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "." + myDay;
    }
  </script>


  <script type="text/javascript">

    /** 新增   **/
    function add() {
      window.open('scenicInfo_edit.jsp','添加');
    }

    /** 删除 **/
    function del(id) {
      // 非空判断
      if (id == '') return;
      if (confirm("您确定要删除吗？")) {
        $.ajax({
          url:'deleteScenicOnWeb.do',
          type:'post',
          data:{id:id},
          dataType: "json",
          cache: false,
          success: function (data) {
            if(data.msg=="删除成功") {
              $('#scenicInfoList').datagrid('reload');
//              window.location='scenicInfo.jsp';
            }
            alert(data.msg);
          }
        })
      }
    }

    function searchScenic(){
      var inp=document.getElementById("scenicName");
      var name=inp.value;
      $.ajax({
        url:'findScenicOnWeb.do',
        type:'post',
        data:{scenicName:name},
        dataType: "json",
        cache: false,
        success: function (data) {
          $('#scenicInfoList').datagrid('loadData',data);
        }
      });
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
<body onload="getDate01()">
<input type="hidden"
       name="msg" id="div1" value="${msg}"/>
<div>
  <div id="top">
    <div id="top_logo">
      <img alt="logo" src="./images/logo.jpg" width="274" height="49" style="vertical-align:middle;">
    </div>
    <div id="top_links">
      <div id="top_op">
        <ul>
          <li>
            <img alt="当前用户" src="./images/user.jpg">：
            <span><%=nickName%></span>
          </li>
          <li>
            <img alt="事务月份" src="./images/month.jpg">：
            <span id="yue_fen"></span>
          </li>
          <li>
            <img alt="今天是" src="./images/date.jpg">：
            <span id="day_day"></span>
          </li>
        </ul>
      </div>
      <div id="top_back">
        <a href="javascript:void(0);" onclick="back_main();" target="_parent">
          <img alt="返回首页" title="返回首页" src="./images/nav_hide.png"
               style="position: relative; top: 10px; left: 25px;">
        </a>
      </div>
    </div>
  </div>

  <div>
    <form id="submitForm" action="" method="post">
      <div id="container">
        <div class="ui_content">
          <div class="ui_text_indent">
            <div id="box_border">
              <div id="box_top">景区信息操作</div>
              <div id="box_center">
                &nbsp;&nbsp;
                <input type="button" id="add_button" value="添加" onclick="add()"/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="reflash_button" value="刷新" onclick="loadData()"/>
                &nbsp;&nbsp;
                <%--<input type="button" value="修改" onclick="" hidden="true"/>--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--<input type="button" value="删除" onclick="del(123)" hidden="true"/>--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--<input type="button" value="查看" onclick="" hidden="true"/>--%>
              </div>
              <div id="box_center1">
                &nbsp;&nbsp;
                景区名
                &nbsp;&nbsp;<input type="text" id="scenicName" name="scenicName"
                                   class="ui_input_txt02"/>
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                <input type="button" value="查询" id="search_button" onclick="searchScenic()"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>

  <div id="container1">
    <div class="ui_content">
      <div class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0"
           style="overflow:hidden">
        <table id="scenicInfoList"></table>
      </div>
    </div>
  </div>
</div>
</div>


</body>
</html>