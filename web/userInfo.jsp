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
  <title>用户信息</title>
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
    Date.prototype.Format = function(fmt)
    { //author: meizz
      var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate()-1,                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      return fmt;
    };
    /** 新增   **/
    function add() {
        $("#submitForm").attr("action", "**.action").submit();
    }

    /** 删除 **/
    function del(id) {
        // 非空判断
        if (id == '') return;
        if (confirm("您确定要删除吗？")) {
            $.ajax({
              url:'deleteUserOnWeb.do',
              type:'post',
              data:{id:id},
              dataType: "json",
              cache: false,
              success: function (data) {
                if(data.msg=="删除成功") {
                  $('#userInfoList').datagrid('reload');
                }
                alert(data.msg);
              }

            });

        }
    }
    function loadData() {
      $('#userInfoList').datagrid({
        url: 'getUserInfoListOnWeb.do',
        method: 'post',
        loadMsg: "数据装载中....",
        nowrap: false,
        fitColumns: true,
        pagination: true,
        singleSelect: false,
        rownumbers: true,
        height: 'auto',
        striped: true,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", // 解决中文乱码
        columns: [[{
          field: 'columnsrudOperation',
          title: '操作',
          width: 150,
          align: 'center',
          sortable: true,
          formatter: function (value, rec) {
            id=value.toString().split("|")[0];
            var operate = "<a href='userInfo_edit.jsp?value="+value+"' class='edit'>修改</a>" + "  " + "<a href='javaScript:del("+id+");'>删除</a>";
            return operate;
          }
        }, {
          field: 'userId',
          title: '编号',
          width: 150,
          align: 'center'
        }, {
          field: 'portrait',
          title: '头像',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'portraitCheckCode',
          title: '头像验证码',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'nickname',
          title: '名字',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'email',
          title: '邮箱',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'password',
          title: '密码',
          width: 150,
          align: 'center',
          sortable: true,
          hidden: true
        }, {
          field: 'gender',
          title: '性别',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'height',
          title: '身高',
          width: 150,
          align: 'center',
          sortable: true
        }, {
          field: 'weight',
          title: '体重',
          width: 150,
          align: 'center',
          sortable: true
        }, {
            field: 'birthday',
            title: '生日',
            width: 150,
            align: 'center',
            sortable: true,
            formatter: function (value) {
                if(value==null){
                    return "";
                }
                return new Date(value).Format('yyyy-MM-dd');
            }
          }
          , {
            field: 'city',
            title: '城市',
            width: 150,
            align: 'center',
            sortable: true
          }
          , {
            field: 'phone',
            title: '电话号码',
            width: 150,
            align: 'center',
            sortable: true
          }
          , {
            field: 'userType',
            title: '用户类型',
            width: 150,
            align: 'center',
            sortable: true
          }
        ]]

      });

      $('#userInfoList').datagrid('getPager').pagination({
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
    $(document).ready(function () {
      /** 新增   **/
      $("#addBtn").fancybox({
        'href': 'scenicInfo_edit.jsp',
        'width': 733,
        'height': 530,
        'type': 'iframe',
        'hideOnOverlayClick': false,
        'showCloseButton': false,
        'onClosed': function () {
          window.location.href = 'scenicInfo.jsp';
        }
      });
      /**编辑   **/
      $("a.edit").fancybox({
        'width': 733,
        'height': 530,
        'type': 'iframe',
        'hideOnOverlayClick': false,
        'showCloseButton': false,
        'onClosed': function () {
          window.location.href = 'scenicInfo.jsp';
        }
      });
    });

    function searchUser(){
      var inp = document.getElementById("nickName");
      var name=inp.value;
      $.ajax({
        url:'findUserOnWeb.do',
        type:'post',
        data:{nickName:name},
        dataType: "json",
        cache: false,
        success: function (data) {
          $('#userInfoList').datagrid('loadData',data);
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
              <div id="box_top">用户信息操作</div>
              <div id="box_center">
                &nbsp;&nbsp;
                用户名字

                &nbsp;&nbsp;<input type="text" id="nickName" name="nickName"
                                   class="ui_input_txt02"/>
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                &nbsp;&nbsp;
                <input type="button" value="查询" id="search_button" onclick="searchUser()"/>

                <%--<!--&nbsp;&nbsp;-->--%>
                <%--<!--<input type="button" value="修改" onclick="" hidden="true"/>-->--%>
                <%--<!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->--%>
                <%--<!--<input type="button"  value="删除" onclick="del(123)" hidden="true"/>-->--%>
                <%--<!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->--%>
                <%--<!--<input type="button"  value="查看" onclick="" hidden="true"/>-->--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <input type="button" id="reflash_button" value="刷新" onclick="loadData()"/>

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
        <table id="userInfoList"></table>
      </div>
    </div>
  </div>
</div>
</div>


</body>
</html>