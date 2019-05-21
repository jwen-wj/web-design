$(function(){
    $.post("OrderServlet", {"op": "getOrderShows"}, function(data){
        $.each(data, function(index, item){
            $("#orderlist").append("            <div class=\"list-group-item flex-horizontal-around\">\n" +
                "                <div>" + item.dname + "</div>\n" +
                "                <div>" + item.kname + "</div>\n" +
                "                <div>" + item.sdate.split(" ")[0] + "</div>\n" +
                "                <div>" + item.timetype + "</div>\n" +
                "                <div>" + item.price + "</div>\n" +
                "            </div>");
        })
    }, "json");
})