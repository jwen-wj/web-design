$(function(){

    // 获取热门疾病
    $.post("KsJbServlet", {"op": "getHotJbs"}, function(data){
        var hotbox = $("#hotbox").html("");
        $.each(data, function(index, item){
            hotbox.append("<div class=\"box-item\"><a href=\"javascript:search(" + item.kid + ")\" class=\"a-normal text-darkgray\">" + item.jname + "</a> </div>")
        });
    }, "json");

    // 获取疾病
    $.post("KsJbServlet", {"op": "getJb"}, function(data){
        var jbdict = new Array();
        for(var i = 0; i < data.length; i++){
            var btype = data[i]["btype"];
            var stype = data[i]["stype"];
            if(btype == null){
                btype = "其他";
            }
            if(stype == null){
                stype = "其他";
            }
            if(jbdict[btype] == null){
                jbdict[btype] = new Array();
            }
            if(jbdict[btype][stype] == null){
                jbdict[btype][stype] = new Array();
            }
            jbdict[btype][stype].push({"kid": data[i]["kid"], "jname": data[i]["jname"]});
        }

        // 更新页面
        var typebox = $("#typebox").html("");
        var listbox = $("#listbox").html("");
        var cnt = 0, cnt2 = 0;
        for(var btype in jbdict){
            if(btype == "其他"){
                var others = btype;
                continue;
            }
            cnt++;
            typebox.append("                <a href=\"#list-title-" + cnt + "\" class=\"a-normal text-darkgray\">\n" +
                "                    <span class=\"glyphicon glyphicon-unchecked type_icon\"></span>&nbsp;" + btype + "\n" +
                "                    <span class=\"glyphicon glyphicon-chevron-right pull-right type-arrow text-lightgray\"></span>\n" +
                "                </a>");
            listbox.append("<div id=\"list-title-" + cnt + "\" class=\"list-title text-darkgray\"><h4>" + btype + "</h4></div>");
            for(var stype in jbdict[btype]){
                cnt2++;
                listbox.append("<div class=\"listbox-part\">\n" +
                    "    <div class=\"part-container\">\n" +
                    "        <div class=\"part-title\">\n" +
                    "            <div><img src=\"images/body/" + stype + ".jpg\"></div>\n" +
                    "            <span class=\"text-lightblue\">" + stype + "</span>\n" +
                    "        </div>\n" +
                    "        <div id=\"part-content-" + cnt2 + "\" class=\"part-content\">\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <span class=\"dashline\"></span>\n" +
                    "</div>");
                var list = $("#part-content-"+cnt2);
                for(var i = 0; i < jbdict[btype][stype].length; i++){
                    list.append("<span class=\"content-item\"><a href=\"javascript:search("+ jbdict[btype][stype][i].kid + ")\" class=\"a-normal text-gray\">" + jbdict[btype][stype][i].jname + "</a></span>");
                }
            }
        }

        var btype = others;
        cnt++;
        typebox.append("                <a href=\"#list-title-" + cnt + "\" class=\"a-normal text-darkgray\">\n" +
            "                    <span class=\"glyphicon glyphicon-unchecked type_icon\"></span>&nbsp;" + btype + "\n" +
            "                    <span class=\"glyphicon glyphicon-chevron-right pull-right type-arrow text-lightgray\"></span>\n" +
            "                </a>");
        listbox.append("<div id=\"list-title-" + cnt + "\" class=\"list-title text-darkgray\"><h4>" + btype + "</h4></div>");
        for(var stype in jbdict[btype]){
            cnt2++;
            listbox.append("<div class=\"listbox-part\">\n" +
                "    <div class=\"part-container\">\n" +
                "        <div id=\"part-content-" + cnt2 + "\" class=\"part-content\">\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <span class=\"dashline\"></span>\n" +
                "</div>");
            var list = $("#part-content-"+cnt2);
            for(var i = 0; i < jbdict[btype][stype].length; i++){
                list.append("<span class=\"content-item\"><a href=\"javascript:search("+ jbdict[btype][stype][i].kid + ")\" class=\"a-normal text-gray\">" + jbdict[btype][stype][i].jname + "</a></span>");
            }
        }
    }, "json");

    $("#navi .navi-item").eq(2).addClass("bg-darkblue");



    $.goup({
        containerColor: "#60BFF2",
        // bottomOffset: 100,       //距底部偏移量
        // locationOffset: 200      //距右部偏移量
        // title: '返回顶部',
        // titleAsText: true        //是否显示title
    });
})








