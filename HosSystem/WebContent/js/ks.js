$(function(){

    // 获取热门科室
    $.post("KsJbServlet", {"op": "getHotKss"}, function(data){
        var hotbox = $("#hotbox").html("");
        $.each(data, function(index, item){
            hotbox.append("<div class=\"box-item\"><a href=\"javascript:search(" + item.kid + ")\" class=\"a-normal text-darkgray\">" + item.kname + "</a> </div>")
        });
    }, "json");

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
        var typebox = $("#typebox").html("");
        var listbox = $("#listbox").html("");
        for(var key in ksdict){
            cnt++;
            typebox.append("                <a href=\"#listbox-part-" + cnt + "\" class=\"a-normal text-darkgray\">\n" +
                "                    <span class=\"type_icon\"></span>&nbsp;" + key + "\n" +
                "                    <span class=\"glyphicon glyphicon-chevron-right pull-right type-arrow text-lightgray\"></span>\n" +
                "                </a>");

            listbox.append("            <div id=\"listbox-part-" + cnt + "\" class=\"listbox-part\">\n" +
                "                <span class=\"text-lightblue part-title\">" + key + "</span>\n" +
                "                <span class=\"dashline\"></span>\n" +
                "                <div class=\"part-content\">\n" +
                "                </div>\n" +
                "            </div>");
            var partcontent = $("#listbox-part-"+cnt+" .part-content");
            var ksdetail = ksdict[key];
            for(var i = 0; i < ksdetail.length; i++){
                partcontent.append("<span class=\"content-item\"><a href=\"javascript:search("+ ksdetail[i]["kid"] +")\" class=\"a-normal text-gray\">" + ksdetail[i]["kname"] + "</a></span>")
            }
        }
        $("#typebox .type_icon").each(function(index, item){
            $(item).css("background", "url(\"images/kstype_icon.jpg\") 0px " + index*(-22) + "px no-repeat");
        })
    }, "json")

    // 初始化样式
    $("#navi .navi-item").eq(1).addClass("bg-darkblue");
})