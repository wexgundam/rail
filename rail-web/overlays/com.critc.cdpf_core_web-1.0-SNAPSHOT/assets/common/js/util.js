//设置弹出框为中文
bootbox.setLocale("zh_CN");

//设置菜单选中
$(".sub-menu li").find("a").click(function () {
    //alert($(this).parent().attr("id"));
    //当前选择的下标
    var index = $(this).parent().attr("id");
    //记录下标
    Cookies.set('current', index, {expires: 10, path: '/'});
});
var current = Cookies.get('current');
if (current != null && current != '') {
    $('.nav-item .active').removeClass('active');
    var $current = $("#" + current);
    $current.addClass("active");
    $current.parent().parent().addClass("active open");
   if($current.parent().parent().parent().parent()!=null)
    $current.parent().parent().parent().parent().addClass("active open");
}






