function pageClick(k) {
	$(k).parent().find("div").removeClass("active");
	$(k).addClass("active");
    $("#flTitle").text($(k).text());

    $("#content_iframe").attr("src",$(k).attr("src"));
}
