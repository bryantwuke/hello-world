package roomsale


class Cst_basic {
    Long          cst_agtid = 0                                                          //所属经纪人

    String        cst_name                                                           //名字
    String        cst_mobile                                                         //手机
    String        cst_idcard    = ""                                                     //身份证
    String        cst_address   = ""                                                     //住址

    Integer       cst_phonenum = 0                                                  //打电话次数
    Integer       cst_smsnum = 0                                                    //短信条数

    Date          cst_phonetime                                                     //最后一次通过时间
    Date          cst_smstime                                                       //最后一次短信时间

    Date          cst_time                                                          // 客户创建时间

    Integer       cst_regbd = Enum_Regstatus.RS_wishno.code                         // 客户最高的楼盘报备状态
    Integer       cst_regcar = Enum_Regstatus.RS_wishno.code                         // 客户最高的车行报备状态

    static constraints = {
        cst_name(nullable: false, maxSize: 15)
        cst_mobile(nullable: false, maxSize: 20)
        cst_idcard(nullable: true, maxSize: 20)
        cst_address(nullable: true, maxSize: 50)

        cst_phonetime(nullable: true)
        cst_smstime(nullable: true)

        cst_time(nullable: true)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_basic_seq'])
    }
}

public enum Enum_Wishlevel{
    WL_none(0, '无意愿'),

    WL_ordinary(10, '一般'),
    WL_strong(20, '强烈'),
    WL_urgent(30, '急切'),

    WL_end(99, '结束')

    private final  Integer code
    private final  String name

    Enum_Wishlevel(Integer code,String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

public enum Enum_Regstatus {
    RS_cancel(-1, '不买了'),

    RS_wishno(0, '未有意愿'),
    RS_wishyes(10, '有意愿'),

    RS_regno(20, '报备失败'),
    RS_regyes(21, '报备成功'),

    RS_ok(30, '成交')

    private final Integer code
    private final String name

    Enum_Regstatus(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

public enum Enum_Reftype{
    RT_building(0, '楼盘', 'roomsale.Building', 'bd_name'),
    RT_car(1, '汽车', 'roomsale.Car_bank', 'cb_name'),
    RT_sys(2, '平台', '', '')

    private final Integer code
    private final String name
    private final String ref_domain
    private final String ref_field

    Enum_Reftype(Integer code, String name, String ref_domain, String ref_field){
        this.code = code
        this.name = name
        this.ref_domain = ref_domain
        this.ref_field = ref_field
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}
