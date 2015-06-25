/**
 * Created by LING on 2014/12/2.
 */
$(function(){
    $('#btAddAdmin').click(function () {
        $('#addAdmin').modal();
        $('#addAdminForm').find('.form-result').remove();
    });

    $("#addAdminForm :input").blur(function(){
        var $parent = $(this).parent().parent();
        var  reg = /^\w/;
        var resultReg;
        $parent.find('.form-result').remove();
        //验证用户名
        if($(this).is('#newUser') && $('#newUser').attr("disabled") != "disabled"){
            resultReg = this.value.match(reg);
            if(null == resultReg || this.value.length < 5 || this.value.length > 15 || this.value.toLowerCase( )  == "admin"){
                var errorMsg = '5~15个字符,支持数字,英文和"_",不能为admin的大小写格式';
                $parent.append(' <div class="col-sm-5 form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
            }
            else {
                var okMsg = '输入正确';
                $parent.append('<div class="col-sm-5 form-result"><span class="vali-result success">'+okMsg + '</span></div>');
            }
        }
        //验证密码
        if($(this).is('#newPassword')){
            resultReg = this.value.match(reg);
            if(null == resultReg || this.value.length < 6 || this.value.length > 15){
                var errorMsg = '6~15个字符,只能包括大小写,数字以及"_"';
                $parent.append(' <div class="col-sm-5 form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
            }
            else {
                var okMsg = '输入正确';
                $parent.append('<div class="col-sm-5 form-result"><span class="vali-result success">'+okMsg + '</span></div>');
            }
            if($('#newPasswordAgain').val() != ""){
                $('#newPasswordAgain').trigger("blur");
            }
        }
        //验证确认密码
        if($(this).is('#newPasswordAgain')){
            if($('#newPassword').val() != ""){
                if(this.value != $('#newPassword').val()){
                    var errorMsg = '两次输入的密码不一致';
                    $parent.append(' <div class="col-sm-5 form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
                }
                else {
                    var okMsg = '输入正确';
                    $parent.append('<div class="col-sm-5 form-result"><span class="vali-result success">'+okMsg + '</span></div>');
                }
            }
        }
    });

    $("#sendNewAdmin").click(function() {

        $('#addAdminForm input').trigger("blur");

        var numError = $('#addAdminForm').find('.error').length;
        if(numError > 0){
            return false;
        }

    });

    $('#addAdmin').on('hidden.bs.modal', function (e) {
        $('#addAdminForm input').val("");
    })

});