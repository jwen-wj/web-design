$(function () {
    layui.use(['table'], function(){
        var table = layui.table;
        table.render({
            elem: '#table-doc',
            url: '../DoctorServlet?op=getDoctorsByPage',
            page: true,
            cols: [[
                {field: 'did', title: 'ID'},
                {field: 'kid', title: '科室ID'},
                {field: 'dname', title: '姓名'},
                {field: 'msg', title: '擅长'},
                {field: 'pic', title: '图片路径'}
            ]],
            done: function(res, curr, count){
                console.info(res);
                console.info(curr);
                console.info(count);
            }
        });
    })
})