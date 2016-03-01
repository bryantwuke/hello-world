package sysuser

class Sys_user {
    String    user_name
    String    user_mobile                                                         //手机
    String    user_idcard                                                         //身份证
    String    user_address                                                        //住址

    String    user_im                       // QQ号、微信号等
    String    user_email

    String    user_depname                  //所在部门,长度限制
    String    user_title                    //职务

    Long      user_orgid = 0                 // 0表示是平台用户，非0，表示为所对应的开发商或车行的合作伙伴用户

    Integer   user_status = Enum_userstatus.US_dlm_ok.code                  //目前状态

    String    user_note

    String     user_loginname
    String     user_pwd
    String     user_pwdsalt

    static constraints = {
        user_name(blank: false, maxSize: 20)
        user_mobile(nullable: false, maxSize: 20)
        user_idcard(nullable: false, maxSize: 20)
        user_address(nullable: true, maxSize: 50)

        user_im(nullable: true, maxSize: 20)
        user_email(nullable: true, maxSize: 30)

        user_depname(nullable: true, maxSize: 20)
        user_title(nullable: true,maxSize: 20)

        user_note(nullable: true, maxSize: 200)

        user_loginname(blank: false, maxSize: 20)
        user_pwd(blank: false, maxSize: 64)
        user_pwdsalt(nullable: false, maxSize: 32)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Sys_user_seq'])
    }
}

public enum Enum_userstatus {
//    US_dlm_wait(10, '待激活'),
    US_dlm_ok(11, '已启用'),
    US_dlm_no(12, '已停用'),

//    US_bd_wait(20, '待激活'),
    US_bd_ok(21, '已启用'),
    US_bd_no(22, '已停用'),

//    US_car_wait(30, '待激活'),
    US_car_ok(31, '已启用'),
    US_car_no(32, '已停用')

    private final Integer code
    private final String name

    Enum_userstatus(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code){
        values().find {it.code == code}
    }
}
