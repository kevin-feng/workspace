$(function(){
    //判断用户名是否有英文和数字组成，
    $('#psUser').blur(function() {
        if(!/^[a-z0-9]+$/i.test($(this).val()) || /^[a-z]+$/i.test($(this).val()) || /^[0-9]+$/i.test($(this).val())){
            $(this).parent().append('<p class="error">用户名由英文和数字组成</p>');
        }else{

        }
    });
    //判断密码是否包含英文和数字，而且长度是否超过8位
    $('#psPassword').blur(function() {
        if(!/^\w+$/.test($(this).val())){
            $(this).parent().append('<p class="error">密码包含英文和数字</p>');
        }else if($(this).val().length < 8){
            $(this).parent().append('<p class="error">密码长度没有超过8位</p>');
        }
    });
    //判断两次输入的密码是否一致
    $('#psPasswordAgain').blur(function() {
        if($(this).val() != $('#psPassword').val()){
            $(this).parent().append('<p class="error">两次输入密码不一致</p>');
        }else{
            
        }
    });
    //判断邮箱
    $('#psEmail').blur(function(event) {
        if(!/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test($(this).val())){
            $(this).parent().append('<p class="error">邮箱地址不正确</p>');
        }
    });
    //输入框获得焦点的时候，把错误提示移除
    $('#psUser, #psPassword, #psPasswordAgain, #psEmail').focus(function(event) {
        $(this).siblings('.error').remove();
    });
    //提交注册
    $('.j-index-login').click(function(){
        // event.preventDefault();
        $('.error').remove();
        $('#psUser, #psPassword, #psPasswordAgain, #psEmail').trigger('blur');
        if($('.error').length == 0){
            
        }else{
            event.preventDefault();
        }
    });
});