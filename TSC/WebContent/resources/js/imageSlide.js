$(function(){
    var adTimer = null;
    var $pagination_a = $('.pagination a');
    var index = 0;
    var length = $pagination_a.length;
    adTimer = setInterval("slide()",3000);

    $($pagination_a).click(function () {
        if(adTimer){
            clearInterval(adTimer);
        }
        var preindex = $(".selected").index(".pagination a");
        $(this).siblings().removeClass("selected").end()
            .addClass("selected");
        index = $pagination_a.index(this);
        if(index == preindex){
            adTimer = setInterval(slide,5000);
            return false;
        }

        $('.slide-link').eq(index).stop(true, true).animate({opacity:"1"}, 500)
            .siblings('.slide-link').animate({opacity:"0"}, 500);
        adTimer = setInterval("slide()",5000);
        return false;
    }).eq(0).click();
});

var slide = function(){
    var index = $('.pagination .selected').index();
    $('.pagination a').removeClass("selected")
        .eq((index+1)%($('.pagination a').length))
        .addClass("selected");
    $('.slide-link').eq((index+1)%($('.pagination a').length)).animate({opacity:"1"}, 500)
        .siblings('.slide-link').animate({opacity:"0"}, 500);
}