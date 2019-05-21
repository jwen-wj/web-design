$(function () {
    // 跳转地图
    // $("#hosinfo-btn").click(function(){
    //     location = "map/map.html";
    // });

    // 获取科室
    $.post("KsJbServlet", {"op": "getKs"}, function(data){
        var ksdict = new Array();
        for(var i = 0; i < data.length; i++){
            var kid = data[i]["kid"];
            var kname = data[i]["kname"];
            var ktype = data[i]["ktype"];
            if(ktype == "null"){
                ktype = "其他科室";
            }
            if(ksdict[ktype] == null){
                ksdict[ktype] = new Array();
            }
            ksdict[ktype].push({"kid": kid, "kname": kname});
        }
        var cnt = 0;
        for(var key in ksdict){
            $("#type-detail").before("<div class=\"type-item\"><span class=\"glyphicon glyphicon-unchecked\"></span> " + key + "</div>");
            cnt++;
            if(cnt >= 9) break;
        }
        $(".type-item").mouseenter(function () {
            $("#type-detail").css("display", "block");
        });
        $("#type-list").mouseleave(function () {
            $("#type-detail").css("display", "none");
        });
        // 更新详细面板
        $(".type-item").mouseover(function () {
            var key = $.trim($(this).text());
            $("#type-detail .text-detail-title").eq(0).text(key);
            $("#type-detail .text-detail-title").eq(1).text(key + "常见疾病");
            var ksdetail = ksdict[key];
            $("#kslist").html("");
            var kids = new Array();
            for(var i = 0; i < ksdetail.length; i++){
                $("#kslist").append("<a href=\"javascript:search("+ ksdetail[i]["kid"] +")\" class=\"a-normal text-detail\">" + ksdetail[i]["kname"] + "</a><span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>");
                kids.push(ksdetail[i]["kid"]);
            }
            // 获取常见疾病
            $.post("KsJbServlet", {"op": "getJbByKid", "kid": kids.join(",")}, function(data){
                $("#jblist").html("");
                for(var i = 0; i < Math.min(12, data.length); i++){
                    $("#jblist").append("<a href=\"javascript:search("+ data[i]["kid"] +")\" class=\"a-normal text-detail\">" + data[i]["jname"] + "</a><span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>");
                }
            }, "json");
        });
    }, "json")

    // 获取热门专家
    $.post("DoctorServlet", {"op": "getHotDoctors"}, function(data){
        var len = data.length;
        var doctors = $("#doctors").html("");
        for(var i = 0; i < len; i++){
            updateDoctor(data[i], len);
        }
    }, "json");

    // 初始化样式
    $("#navi .navi-item").eq(0).addClass("bg-darkblue");

})


function updateDoctor(doctor, len){
    $.post("KsJbServlet", {"op": "getKsByKid", "kid": doctor.kid}, function(data){
        var kname = data.kname;
        $("#doctors").append("            <div class=\"doctor-item\">\n" +
            "                <div class=\"doctor-img-box\">\n" +
            "                    <img src=\"images/" + doctor.pic + "\" alt=\"医生头像\" class=\"img-circle\" />\n" +
            "                </div>\n" +
            "                <div id=\"doctor_name\"><h4>" + doctor.dname + "</h4></div>\n" +
            "                <div id=\"doctor_major\">" + kname + "</div>\n" +
            "                <div class=\"doctor-item-bottom text-center\">\n" +
            "                    <div class=\"doctor-special\" class='text-gray'>擅长：" + doctor.msg + "</div>\n" +
            "                </div>\n" +
            "                <input class=\"doctor-did\" type=\"hidden\" value=\"" + doctor.did + "\" />\n" +
            "            </div>");
        // 添加跳转事件
        $(".doctor-img-box").click(function(){
            var did = $(this).parent().find(".doctor-did").val();
            location = "doctor.html?did=" + did;
        });
        var res = 4-len%4;
        if(res < 4){
            for(var i = 0; i < res; i++){
                $("#doctors").append("<div class='doctor-item' style='border: 0'></div>");
            }
        }
    }, "json")

}












