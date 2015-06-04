/**
 * Created by LING on 2014/11/24.
 */
$(function(){
    var linkurl;
    var $links = $('.main-side-nav-child');
    for(var i=0; i<$links.size(); i++){
        linkurl = $links.eq(i).attr("href");
        if(window.location.href.indexOf(linkurl) != -1){
            $links.eq(i).addClass("choose");
        }
    }

   $('.main-side-nav-child').parent().hover(function(){
            $(this).find(".ul-sub-side").stop(true, true).slideDown();
        },function(){
             $(this).find(".ul-sub-side").stop(true, true).slideUp();
        }
    );

   	$(".dropdown_li .ul-sub-side .sub-side-nav").click(function(event) {
   		/* Act on the event */
   		$(".right-content .tab").eq($(this).parent().index()).show().siblings().hide();
//   		return false;
   	});
})
