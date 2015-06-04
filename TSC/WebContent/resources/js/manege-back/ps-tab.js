/**
 * Created by LING on 2014/11/24.
 */
window.FIX = window.FIX || {};

FIX.fixbar = (function(){
    var _e = {}, _p = $(window);
    _e.set = function(para){
        var obj = {};
        obj.status = {
            offsettop: $(para).offset().top
        }
        var scrollling = function(){
            var st = _p.scrollTop();
            if (st > obj.status.offsettop) {
                $(para).addClass("fixed");
            }else {
                $(para).removeClass("fixed");
            }
        }

        _p.on("scroll", scrollling);

        return {
            kill: function(){
                _p.off("scroll", scrolling);
            }
        }
    }

    return _e;
})();

$(function(){
   FIX.fixbar.set('.main-side-nav');
   $('.main-side-nav-child, .sub-side-nav').each(function(index, el) {
    if(window.location.href.indexOf($(this).attr("href")) != -1){
       $(this).addClass("choose");
       if($(this).hasClass('sub-side-nav')){
          $(this).parent().parent().parent().addClass('on');
        }
    }
   });

   $('.dropdown_li>a').click(function(){
        $(this).parent().toggleClass('on');
    });

   	$(".dropdown_li .ul-sub-side .sub-side-nav").click(function(event) {
   		$(".right-content .tab").eq($(this).parent().index()).show().siblings().hide();
   	});
});
