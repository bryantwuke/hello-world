package service

class Sms_send {

    Date        sms_time
    Integer     sms_type

    String      sms_mobile
    String      sms_content

    static constraints = {
        sms_mobile(nullable: false, maxSize: 15)
        sms_content(nullable: false, maxSize: 150)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Sms_send_seq'])
    }

}

public enum Enum_Smstype {
    ST_chkmobile(1, '手机号验证'),
    ST_findpwd(2, '找回密码'),

    ST_invite(3, '邀请好友'),
    ST_setmobile(4, '更改手机号码'),

    ST_findcspwd(5, '重置提现密码'),

    ST_bddiscount(6, '楼盘优惠活动短信'),
    ST_cardiscount(7, '车行优惠活动短信'),

    ST_realname(8, '实名认证通过'),

    ST_end(999,'结束')

    private final Integer code
    private final String name

    Enum_Smstype(Integer code, String name){
        this.code = code
        this.name = name
    }
}
