package roomsale

class Cst_sale {
    Long        sale_refid = 0                                         //关联id
    Integer     sale_reftype = Enum_Reftype.RT_building.code        //成交类型

    Long        sale_cstid = 0                                        //客户id
    Long        sale_agentid = 0                                     //经纪人id

    Date        sale_time                                            //成交时间
    Long        sale_money = 0                                        // 成交总价

    Long        sale_planfee  = 0                                    // 计算的佣金
    Long        sale_logfee = 0                                    // 实际佣金

    Integer     file_num = 0                                       // 成交的附件

    static constraints = {
        sale_time(nullable: false)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_sale_seq'])
    }
}

/*
public enum Enum_Saletype{
    ST_bonus(0, '定金成交'),
    ST_contract(1, '合同成交'),

    ST_firstpay(10, '首付成交'),
    ST_fullpay(11, '全款成交')

    private final Integer code
    private final String name

    Enum_Saletype(Integer code, String name){
        this.code = code
        this.name = name
    }
}
*/
