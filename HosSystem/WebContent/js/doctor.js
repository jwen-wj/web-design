var did, dname, kname, sdate;

var date = new Date();
date.setDate(date.getDate()+1);

$(function () {
    // 获取did
    var reg = /^.*did=(.*?)(&|$)/;
    if(reg.exec(location)){
        did = RegExp.$1;
    }
    // 获取医生信息
    $.post("DoctorServlet", {"op": "getDoctor", "did": did}, function(data){
        dname = data.dname;
        var kid = data.kid;
        var msg = data.msg;
        var pic = data.pic;
        // 获取科室信息
        $.post("KsJbServlet", {"op": "getKsByKid", "kid": kid}, function(data){
            kname = data.kname;
            $("#doctor-img img").attr("src", "images/"+pic);
            $("#doctor-msg").html("                <h4>" + dname + "</h4>\n" +
                "                <p class=\"text-darkgray text-bigger\">" + kname + "</p>\n" +
                "                <p class=\"text-gray\">擅长：" + msg + "</p>");
            $("#title-forth").text(kname + " - 其他专家");
            // 获取其他专家
            $.post("DoctorServlet", {"op": "getDoctorsByKid", "kid": kid}, function(data){
                var len = data.length;
                var doctors = $("#doctors").html("");
                for(var i = 0; i < len; i++){
                    if(data[i].dname == dname) continue;
                    doctors.append("            <div class=\"doctor-item\">\n" +
                        "                <div class=\"doctor-img-box\">\n" +
                        "                    <img src=\"images/" + data[i].pic + "\" alt=\"医生头像\" class=\"img-circle\" />\n" +
                        "                </div>\n" +
                        "                <div id=\"doctor_name\"><h4>" + data[i].dname + "</h4></div>\n" +
                        "                <div id=\"doctor_major\">" + kname + "</div>\n" +
                        "                <div class=\"doctor-item-bottom text-center\">\n" +
                        "                    <div class=\"doctor-special\" class='text-gray'>擅长：" + data[i].msg + "</div>\n" +
                        "                </div>\n" +
                        "                <input class=\"doctor-did\" type=\"hidden\" value=\"" + data[i].did + "\" />\n" +
                        "            </div>")
                }
                // 添加跳转事件
                $(".doctor-img-box").click(function(){
                    var did = $(this).parent().find(".doctor-did").val();
                    location = "doctor.html?did=" + did;
                });
                len--;              // 去掉自己
                var res = 4-len%4;
                if(res < 4){
                    for(var i = 0; i < res; i++){
                        doctors.append("<div class='doctor-item' style='border: 0'></div>");
                    }
                }
            }, "json");
            sdate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
            refreshSchedu();
        }, "json");
    }, "json");

    laydate.render({
        elem: '#schedu-date',
        position: 'static',
        theme: '#60bff2',
        min:1,
        max: 3*30,
        showBottom: false,
        value: date,
        done: function(value, date){
            // 获取排班信息
            $("#schedu-tbody").html("                        <tr id=\"tr-sw\">\n" +
                "                            <td>上午</td>\n" +
                "                            <td colspan=\"3\">此时间段没有排班</td>\n" +
                "                        </tr>\n" +
                "                        <tr id=\"tr-xw\">\n" +
                "                            <td>下午</td>\n" +
                "                            <td colspan=\"3\">此时间段没有排班</td>\n" +
                "                        </tr>\n" +
                "                        <tr id=\"tr-ws\">\n" +
                "                            <td>晚上</td>\n" +
                "                            <td colspan=\"3\">此时间段没有排班</td>\n" +
                "                        </tr>");
            sdate = date.year + "-" + date.month + "-" + date.date;
            refreshSchedu();
        }
    });

    function refreshSchedu(){
        $.post("ScheduServlet", {
            "op": "getSchedus",
            "did": did,
            "sdate": sdate
        }, function(data){
            for(var i = 0; i < data.length; i++){
                updateRow(data[i]);             // 解决for循环的i无法被异步方法正确取到
            }
        }, "json");
    }

    // 预约下一步
    $("#nextbtn").click(function () {
        if(pageNo == 1){
            $("#step_1").fadeOut("normal", function(){
                $("#step_2").fadeIn();
                $("#appointtitle").text("支付");
                pageNo++;
            });
        }
        else if(pageNo == 2){
            var sid = $("#appoint_sid").val();
            $.post("OrderServlet", {"op": "addOrder", "sid": sid}, function(data){
                data = parseInt($.trim(data));
                if(data == 1){
                    $("#result_ok").css("display", "");
                    refreshSchedu();                    // 预约成功刷新数据
                } else{
                    $("#result_no").css("display", "");
                }
                $("#appointcancel").fadeOut();
                $("#step_2").fadeOut("normal", function(){
                    $("#step_3").fadeIn();
                    $("#appointtitle").text("预约结果");
                    $("#nextbtn").html("完成");
                    pageNo++;
                });
            }, "text");
        }
        else if(pageNo == 3){
            $("#appointmodal").modal("hide");

        }
    });
})
var rescount;
function updateRow(row_data){
    var timetype = row_data.timetype;
    rescount = row_data.rescount;
    var price = row_data.price;
    var sid = row_data.sid;
    $.post("OrderServlet", {"op": "getCount", "sid": sid}, function(data){  //rescount为可预约数，usedcnt为已预约数
        var usedcnt = parseInt($.trim(data))
        var leftcnt = rescount - usedcnt;
        if(timetype == "上午"){
            updateTr("#tr-sw", timetype, leftcnt, price, sid);
        } else if(timetype == "下午"){
            updateTr("#tr-xw", timetype, leftcnt, price, sid);
        } else if(timetype == "晚上"){
            updateTr("#tr-ws", timetype, leftcnt, price, sid);
        }
    }, "text");
}

function updateTr(tr_id, timetype, leftcnt, price, sid){
    if(leftcnt == 0){
        leftcnt = "无";
    }
    var tr = $(tr_id).html("                            <td>" + timetype + "</td>\n" +
        "                            <td>" + leftcnt + "</td>\n" +
        "                            <td>￥" + price + "</td>\n" +
        "                            <td><button type=\"button\" class=\"btn btn-sm bg-lightblue text-white\">" +
        "<span class=\"glyphicon glyphicon-plus-sign\"></span> 一键预约</button></td>")
    if(leftcnt == 0){
        $(tr_id+" button").attr("disabled", "disabled");
    }
    // 添加预约事件
    tr.find("button").click(function(){
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
        $("#appoint_kname").text(kname);
        $("#appoint_dname").text(dname);
        $("#appoint_date").text(sdate);
        $("#appoint_timetype").text(timetype);
        $("#appoint_sid").val(sid);
        $("#appoint_price").text("应付金额： ￥"+price);
        if(leftcnt > 0){
            $("#nextbtn").removeAttr("disabled");
            $("#appoint_cnt").text(leftcnt);
        } else{
            $("#appoint_cnt").text("无");
        }
        $("#appointmodal").modal('show');
    })
}