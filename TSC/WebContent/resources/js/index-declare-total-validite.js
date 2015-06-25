/**
 * Created by LING on 2014/12/2.
 */
$(function(){ 

    $(".right-content :input").blur(function(){
        var $parent = $(this).parent();
        var  reg = "";
        var resultReg;
        $parent.find('.form-result').remove();
        //验证项目名称
        if($(this).is('#item-name') && $(this).val() == ""){
            var errorMsg = '项目名不能为空';
            $parent.append(' <div class="form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
        }
        //验证申请金额
        if($(this).is('#item-money') || $(this).is('#item-bokuan') || $(this).is('#item-danweizizhu') || $(this).is('#item-qitajinfei')){
            reg = /^\d+$/;
            resultReg = this.value.match(reg);
            if(null == resultReg){
                var errorMsg = '金额必须为数字';
                $parent.append(' <div class="col-sm-5 form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
            }
        }
        //验证电话
        if($(this).is('#item-tel')){
            reg = /^(1[3|4|5|7|8])[\d]{9}$/;
            resultReg = this.value.match(reg);
            if(null == resultReg){
                var errorMsg = '手机号码格式不正确';
                $parent.append(' <div class="form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
            }
        }
        //验证邮箱
        if($(this).is('#item-email')){
            reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
            resultReg = this.value.match(reg);
            if(null == resultReg){
                var errorMsg = '邮箱格式不正确';
                $parent.append(' <div class="form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
            }
        }
         //验证
        if($(this).is('.yusuan')){
            reg = "/^\d+$/";
            resultReg = this.value.match(reg);
            if(null == resultReg){
                var errorMsg = '金额必须为数字';
                $parent.append(' <div class="form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
            }
        }
//        //验证确认密码
//        if($(this).is('#newPasswordAgain')){
//            if($('#newPassword').val() != ""){
//                if(this.value != $('#newPassword').val()){
//                    var errorMsg = '两次输入的密码不一致';
//                    $parent.append(' <div class="col-sm-5 form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
//                }
//                else {
//                    var okMsg = '输入正确';
//                    $parent.append('<div class="col-sm-5 form-result"><span class="vali-result success">'+okMsg + '</span></div>');
//                }
//            }
//        }
    });

    /*动态添加的html*/
    $("tbody").on("blur",".yusuan",function(){ 
        reg = "/^\d+$/";
        var  reg = "";
        var resultReg;
        resultReg = this.value.match(reg);
        if(null == resultReg){
            var errorMsg = '金额必须为数字';
//            $(this).append(' <div class="col-sm-5 form-result"><strong class="high">*</strong><span class="vali-result error">' + errorMsg + '</span></div>');
        }
    }); 
//    $("#sendNewAdmin").click(function() {
//
//        $('#addAdminForm input').trigger("blur");
//
//        var numError = $('#addAdminForm').find('.error').length;
//        if(numError > 0){
//            return false;
//        }
//
//    });
//
//    $('#addAdmin').on('hidden.bs.modal', function (e) {
//        $('#addAdminForm input').val("");
//    })

    
});