//$(function(){
    var linkurl;
    var $links = $('.dropdown a');

    /*点击左边导航*/
     $links.click(function(){

         $('.wrapper-form form').eq($(this).parent().index())
             .removeClass("none")
             .siblings().addClass("none");
    //     $('.dropdown a').eq($(this).parent().index()).addClass("current-href").parent().siblings().find('a').removeClass("current-href");
    //    // return false;
     });

    for(var i=0; i<$links.size(); i++){
        linkurl = $links.eq(i).attr("href");
        if(window.location.href.indexOf(linkurl) != -1){
            $links.eq(i).addClass("current-href");
        }
    }