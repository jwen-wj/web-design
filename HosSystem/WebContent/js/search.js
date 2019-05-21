var date = new Date();
date.setDate(date.getDate()+1);

$(function(){
    var endDate = new Date();
    endDate.setDate(endDate.getDate()+3*30);
    $('#orderdate').datetimepicker({
        language:  'zh-CN',
        format: "yyyy-MM-dd",
        autoclose: 1,
        minView: 2,
        startDate: date,
        endDate: endDate,
        todayBtn:true,
        todayHighlight: true
    });

    // 清空按钮
    $("#dateclear").click(function () {
        $("#orderdate").val("不限");
    });

    //初始化日期
    var month = date.getMonth() + 1 + "";
    if(month.length == 1) month = "0" + month;
    var day = date.getDate() + "";
    if(day.length == 1) day = "0" + day;
    $("#orderdate").val(date.getFullYear()+"-"+month+"-"+day);

    // 显示日期按钮
    $("#dateshow").click(function () {
        $('#orderdate').datetimepicker('show');
    });

    // 日期或时间筛选更新
    $("#orderdate").change(function(){
        refreshList();
    })
    $("#ordertime").change(function () {
        refreshList();
    })
    refreshList();

    // 预约下一步
    $("#nextbtn").click(function () {
        if(pageNo == 1){
            $("#step_1").fadeOut("normal", function(){
                $("#appoint_price").text("应付金额： ￥"+price);
                $("#step_2").fadeIn();
                $("#appointtitle").text("支付");
                pageNo++;
            });
        }
        else if(pageNo == 2){
            $.post("OrderServlet", {"op": "addOrder", "sid": sid}, function(data){
                data = parseInt($.trim(data));
                if(data == 1){
                    $("#result_ok").css("display", "");
                } else{
                    $("#result_no").css("display", "");
                }
                $("#appointcancel").fadeOut();
                $("#step_2").fadeOut("normal", function(){
                    $("#step_3").fadeIn();
                    $("#appointtitle").text("预约结果");
                    $("#nextbtn").html("完成");
                    pageNo++;
                })
            }, "text");
        }
        else if(pageNo == 3){
            $("#appointmodal").modal("hide");
        }
    });
})

// 预约
var price;
var pageNo = 1;
var sid;
function appoint(did, dname, kname){
    if(login_state != 1){
        $("#loginform")[0].reset();
        $("#loginModal").modal('show');
        return false;
    }
    pageNo = 1;
    $("#step_1").css("display", "");
    $("#step_2").css("display", "none");
    $("#step_3").css("display", "none");
    $("#appointcancel").css("display", "");
    $("#appointtitle").text("确认信息");
    $("#nextbtn").text("下一步");
    $("#nextbtn").attr("disabled", "disabled");
    $("#result_ok").css("display", "none");
    $("#result_no").css("display", "none");
    var sdate = $("#orderdate").val();
    var timetype = $("#ordertime").val();
    $("#appoint_kname").text(kname);
    $("#appoint_dname").text(dname);
    $("#appoint_date").text(sdate);
    $("#appoint_timetype").text(timetype);
    $.post("ScheduServlet", {
        "op": "getSchedu",
        "did": did,
        "sdate": sdate,
        "timetype": timetype
    }, function(data){
        sid = data.sid;
        var rescount = data.rescount;
        price = data.price;
        $.post("OrderServlet", {"op": "getCount", "sid": sid}, function(data){  //rescount为可预约数，usedcnt为已预约数
            var usedcnt = parseInt($.trim(data))
            var leftcnt = rescount - usedcnt;
            if(leftcnt > 0){
                $("#nextbtn").removeAttr("disabled");
                $("#appoint_cnt").text(leftcnt);
            } else{
                $("#appoint_cnt").text("无");
            }
        }, "text")
    }, "json");
    $("#appointmodal").modal('show');
}

// 更新医生列表
function refreshList() {
    var reg = /^.*kid=(.*?)(&|$)/
    if(reg.exec(location)){
        var kid = RegExp.$1;
    }
    var sdate = $("#orderdate").val();
    if(sdate == "不限"){
        sdate = "";
    }
    var timetype = $("#ordertime").val();
    if(timetype == "不限"){
        timetype = "";
    }
    $.post("DoctorServlet", {
        "op": "getDoctors",
        "kid": kid,
        "sdate": sdate,
        "timetype": timetype
    }, function(data){
        $.post("KsJbServlet", {"op": "getKsByKid", "kid": kid}, function(ks){
            var kname = ks.kname;
            var len = data.length;
            var doctors = $("#docters").html("");
            for(var i = 0; i < len; i++){
                doctors.append("            <div class=\"doctor-item\">\n" +
                    "                <div class=\"doctor-img-box\">\n" +
                    "                    <img src=\"images/" + data[i].pic + "\" alt=\"医生头像\" class=\"img-circle\" />\n" +
                    "                </div>\n" +
                    "                <div id=\"doctor_name\"><h4>" + data[i].dname + "</h4></div>\n" +
                    "                <div id=\"doctor_major\">" + kname + "</div>\n" +
                    "                <div class=\"doctor-item-bottom text-center\">\n" +
                    "                    <div class=\"doctor-special\" class='text-gray'>擅长：" + data[i].msg + "</div>\n" +
                    "                    <a class=\"btn btn-sm bg-lightblue text-white\" href=\"javascript:appoint(" + data[i].did + ", '" + data[i].dname + "','" + kname + "')\"><span class=\"glyphicon glyphicon-plus-sign\"></span> 一键预约</a>\n" +
                    "                </div>\n" +
                    "                <input class=\"doctor-did\" type=\"hidden\" value=\"" + data[i].did + "\" />\n" +
                    "            </div>")
            }
            // 添加跳转事件
            $(".doctor-img-box").click(function(){
                var did = $(this).parent().find(".doctor-did").val();
                location = "doctor.html?did=" + did;
            });
            var res = 4-len%4;
            if(res < 4){
                for(var i = 0; i < res; i++){
                    doctors.append("<div class='doctor-item' style='border: 0'></div>");
                }
            }
        }, "json");
    }, "json");
}




















