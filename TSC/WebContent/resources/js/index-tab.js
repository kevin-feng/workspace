/**
 * Created by LING on 2014/12/5.
 */
$(function(){
    var linkurl;
    var $links = $('.head-nav a');
    for(var i=0; i<$links.size(); i++){
        linkurl = $links.eq(i).attr("href");
        if(window.location.href.indexOf(linkurl) != -1){
            $links.eq(i).addClass("choose");
        }
    }
})
