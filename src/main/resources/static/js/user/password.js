layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on('submit(saveBtn)', function(data){
        $.ajax({
            type:"post",
            url:ctx + "/user/updatePwd",
            data:{
                oldPwd:data.field.old_password,
                newPwd:data.field.new_password,
                repeatPwd:data.field.again_password
            },
            success:function (result) {
                if(result.code == 200){
                    layer.msg("用户密码修改成功，将在3秒之后退出",function (){
                        $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName",{domain:"localhost",path:"/crm"});
                        $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                        //在父窗口跳转
                        window.parent.location.href=ctx + "/index";
                    })
                }else {
                    layer.msg(result.msg, {icon:5})
                }

            }
        })

    })

})