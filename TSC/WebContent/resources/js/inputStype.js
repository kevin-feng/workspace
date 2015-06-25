/**
 * Created by LING on 2014/11/23.
 */
$(function(){
    $('#search-text').focus(function(){
        if($(this).val() == this.defaultValue){
            $(this).val("");
        }
    }).blur(function(){
        if($(this).val() == ""){
            $(this).val(this.defaultValue);
        }
    });
})