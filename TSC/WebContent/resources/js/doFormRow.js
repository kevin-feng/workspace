/**
 * Created by LING on 2014/12/17.
 */
$(function(){
    $('.declare-form-three .addFormRow').click(function(){
        var html = "<tr><td class='numtd'><input type='text'/></td><td><input type='text' name='name'/></td><td><input type='text' name='mobileNo'/></td><td><input type='text' name='adminPosition'/></td><td><input type='text' name='organization'/></td><td><input type='text' name='major'/></td><td><a href='#' class='deleteFormRow'>删除</a></td></tr>";
        $('.declare-form-three table tbody').append(html);
        if($('.declare-form-three tbody .numtd').size() > 0){
            $('.declare-form-three tbody .numtd').each(function(){
                $(this).find('input').val($(this).parent().index() + 1);   
            });
        }
        return false;
    });

    $('.declare-form-six .addFormRow').click(function(){
        var html = "<tr><td class='numtd'><input type='text'/></td><td><input type='text' name='subjectName'/></td><td><input type='text' class='yusuan' name='amount'/></td><td><a href='#' class='deleteFormRow'>删除</a></td></tr>";
        $('.declare-form-six table tbody').append(html);
        if($('.declare-form-six tbody .numtd').size() > 0){
            $('.declare-form-six tbody .numtd').each(function(){
                $(this).find('input').val($(this).parent().index() + 1);   
            });
        }
        return false;
    });

    $('.declare-form-three table').on("click",".deleteFormRow", deleterow);
    $('.declare-form-six table').on("click",".deleteFormRow", deleterow);

});
var deleterow = function(){
    var returnVal = window.confirm("确认删除改成员？", "提示");
    if(returnVal) {
        $(this).parent().parent().remove();
    }

    if($('.declare-form-six tbody .numtd').size() > 0){
        console.log($('.declare-form-six tbody .numtd').size());

        $('.declare-form-six .numtd').each(function(){
//                alert($(this).parent().index());
            $(this).find('input').val($(this).parent().index() + 1);
        });
    }
  //  return false;
};