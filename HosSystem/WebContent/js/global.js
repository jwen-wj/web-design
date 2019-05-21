var login_state;
$(function(){
    $("#top-login").click(function () {
        $("#loginform")[0].reset();
    });

    // 获取登录状态
    $.post("UserServlet", {"op": "login_state"}, function (data) {
        login_state = parseInt($.trim(data));
        if(login_state == 1){
            showLoginbtn()
        } else{
            showLogoutbtn();
        }
    }, "text");

    function showLoginbtn(){
        $("#top-login").css("display", "none");
        $("#top-register").css("display", "none");
        $("#top-logout").css("display", "block");
        $("#top-order").css("display", "block");
    }
    function showLogoutbtn(){
        $("#top-login").css("display", "block");
        $("#top-register").css("display", "block");
        $("#top-logout").css("display", "none");
        $("#top-order").css("display", "none");
    }

    // 模态框登录
    $("#loginbtn").click(function () {
        var email = $.trim($("#loginemail").val());
        if(email == ""){
            alert("邮箱不能为空！");
            return false;
        }
        var pwd = $.trim($("#loginpwd").val());
        if(pwd == ""){
            alert("密码不能为空！");
            return false;
        }
        $.post("UserServlet", {"op": "login", "email": email, "pwd": pwd}, function (data) {
            data = parseInt($.trim(data));
            if(data == 1){
                login_state = 1;
                alert("登录成功！");
                $('#loginModal').modal('hide');
                showLoginbtn();
            } else{
                alert("邮箱或密码错误！");
            }
        }, "text");
    });

    // 退出登录
    $("#top-logout").click(function () {
        $.post("UserServlet", {"op": "logout"}, function (data) {
            data = parseInt($.trim(data));
            if(data == 1){
                login_state = 0;
                alert("退出登录成功！");
                showLogoutbtn();
            }
        }, "text");
    });

    // 搜索关键词
    $("#search-btn").click(function(){
        var keyword = $.trim($("#top-search").val());
        if(keyword == ""){
            alert("搜索内容不能为空！");
            return false;
        }
        searchKeyword(keyword);
    });
    $("#search-menu-ks").click(function(){
        $("#search-btn-title").text("科室");
    });
    $("#search-menu-jb").click(function(){
        $("#search-btn-title").text("疾病");
    });
})

// 搜索
function search(kid){
    location = "search.html?kid=" + kid;
}
function searchKeyword(keyword){
    var title = $("#search-btn-title").text();
    var op;
    if(title == "科室"){
        op = "SearchKsByKname";
    } else if(title == "疾病"){
        op = "SearchKsByJname";
    }
    $.post("KsJbServlet", {"op": op, "keyword": keyword}, function(data){
        var kid = parseInt($.trim(data));
        if(kid == -1){
            alert("未搜索到对应的"+title+"，请到科室或疾病页面进行查找！");
        } else{
            search(data);
        }
    }, "json")
}




















