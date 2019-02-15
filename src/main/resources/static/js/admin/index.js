$(function(){
    $("#left_menu").children("div").each(function () {
        if($(this).hasClass("active")){
            pageClick($(this));
        }
    });
    //获取登录信息
    $.ajax({
        url:'/admin/login/currentLoginUser',
        type:'post',
        success:function (response) {
            if(response != null){
                $("#userName").text(response.userName);
            }
        }
    });
    //退出
    $("#logout").click(function(){
        $.ajax({
            url:'/admin/login/logout',
            type:'post',
            success:function (response) {
                window.location.href="/admin/login.html";
            }
        });
    })
})
function pageClick(k) {
	$(k).parent().find("div").removeClass("active");
	$(k).addClass("active");
    $("#flTitle").text($(k).text());
    $("#content_iframe").attr("src",$(k).attr("src"));
}

