package service

class Back_str {
    String          value

    static constraints = {
        value(nullable: true, maxSize: 64)
    }

    static mapping = {
        id(generator:'assigned')
    }

}

public enum Enum_backstr {

    BS_sms(1, '短信发送开关'),

    BS_admin(2, 'super用户'),
    BS_adsalt(3, 'super加盐'),
    BS_adpwd(4, 'super密码'),

    BS_admobile(5, 'super手机号'),

    RS_startpic(6, '启动图'),


    BS_end(9999, 'BS_end')

    private final Integer code
    private final String name

    Enum_backstr(Integer code, String name){
        this.code = code
        this.name = name
    }

}
