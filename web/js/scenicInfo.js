/**
 * Created by mengqiumin-ext on 2016/5/25.
 */
$(document).ready(function() {
    $('#scenicInfoList').datagrid({
        url : '',
        method : 'post',
        loadMsg : "数据装载中....",
        nowrap : false,
        fitColumns : true,
        pagination : true,
        singleSelect : false,
        rownumbers : true,
        height:'auto',
        striped : true,
        contentType : "application/x-www-form-urlencoded; charset=utf-8", // 解决中文乱码
        columns : [ [ {
            field : 'crudOperation',
            title : '操作',
            width : 50,
            align : 'center',
            sortable : true,
            formatter:function(value,rec){
//                 return "<a href='Javascript:void(0)'>查看</a>";
//                 return "<a href='Javascript:void(0)'>查看</a>";
                var operate = '<a href="Javascript:void(0);" onclick="detail(\''+rec.billCode+'\');">查看</a>';
                return operate;
            }
        }, {
            field : 'id',
            title : 'id',
            width : 150,
            align : 'center',
            hidden : true
        }, {
            field : 'billType',
            title : '单据类型',
            width : 150,
            align : 'center',
            sortable : true,
            formatter:function(val,row) {
                if(row.billType==1){
                    return '年度预算单';
                }
                if(row.billType==2){
                    return 'TB预算金额单';
                }
            }
        }, {
            field : 'createTime',
            title : '单据日期',
            width : 150,
            align : 'center',
            sortable : true,
            formatter:function(value){
                return new Date(value.time).format('yyyy-MM-dd');
            }
        }, {
            field : 'billCode',
            title : '单据编号',
            width : 150,
            align : 'center',
            sortable : true
        },
            {
                field : 'accountYear',
                title : '预算年度',
                width : 150,
                align : 'center',
                sortable : true,
                hidden:true
            },

            {
                field : 'budgetSubjectName',
                title : '预算科目',
                width : 150,
                align : 'center',
                sortable : true,
                hidden:true
            },

            {
                field : 'remark',
                title : '备注',
                width : 150,
                align : 'center',
                sortable : true
            }, {
                field : 'updateOperator',
                title : '最近维护人',
                width : 150,
                align : 'center',
                sortable : true
            }, {
                field : 'updateTime',
                title : '最近维护日期',
                width : 150,
                align : 'center',
                sortable : true
            }] ]

    });

    $('#scenicInfoList').datagrid('getPager').pagination({
        pageSize : 20,// 每页显示的记录条数，默认为10
        pageList : [ 30, 80, 100 ],// 可以设置每页记录条数的列表
        pageNumber : 1,
        beforePageText : '第',// 页数文本框前显示的汉字
        afterPageText : '页    共 {pages} 页',
        displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'

    });
    $('#scenicInfoList').datagrid('options').url = '**.action';
    $('#scenicInfoList').datagrid('reload');


    //grid页面最大化 样式 自适应
    $(window).resize(function(){
        $('#scenicInfoList').datagrid('resize',{
            width:function(){return $(window).width()-10;}
        });
    });
});