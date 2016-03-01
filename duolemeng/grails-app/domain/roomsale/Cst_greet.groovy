package roomsale

class Cst_greet {
    Long            greet_cstid = 0
    Long            greet_agentid = 0

    Date            greet_date              // 问候时间
    Integer         greet_way = Enum_Greetway.GW_sms.code               // 问候方式

    Integer         greet_type = Enum_Greettype.GT_holiday.code

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_greet_seq'])
    }

}

public enum Enum_Greettype{
    GT_holiday(0, '节日问候'),
    GT_birth(1, '生日问候')

    private final Integer code
    private final String name


    Enum_Greettype(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

public enum Enum_Greetway{
    GW_sms(0, '短信问候'),
    GW_phone(1, '电话问候'),
    GW_weixin(2, '微信问候')

    private final Integer code
    private final String name

    Enum_Greetway(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

