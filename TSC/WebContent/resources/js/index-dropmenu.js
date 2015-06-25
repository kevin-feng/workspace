/**
 * Created by LING on 2014/12/5.
 */
$(function(){
    $(".dropdown").hover(function(){
            $(this).find(".dropdown-menu").stop(true, true).slideDown();
        },function(){
            $(this).find(".dropdown-menu").stop(true, true).slideUp();
        }
    );
});