package roomsale

class Agent_plan {
    Long        plan_agentid = 0

    Date        plan_date
    Integer     plan_type = Enum_Plantype.PT_month.code

    Integer     plan_roomdeal = 0                            //    楼盘成交人数
    Integer     log_roomdeal = 0                             //   实际楼盘成交人数

    Integer     plan_roomregok = 0                           //楼盘报备人数
    Integer     log_roomregok = 0                            //实际楼盘报备人数

    Integer     plan_cardeal = 0                             //    车行成交人数
    Integer     log_cardeal = 0                              //   实际车行成交人数

    Integer     plan_carregok = 0                           //车行报备人数
    Integer     log_carregok = 0                            //实际车行报备人数

    Integer     plan_sms = 0                                 //发短信次数
    Integer     log_sms = 0                                  //实际发短信次数

    Integer     plan_phone = 0                              //打电话次数
    Integer     log_phone = 0                               //实际打电话次数

    Integer     plan_cst = 0                                //新增客户人数
    Integer     log_cst = 0                                 //实际新增客户人数

    static constraints = {
        plan_date(nullable: false)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_plan_seq'])
    }

    def planinfo(){
        def plan = [:]

        plan.plan_roomdeal = this.plan_roomdeal
        plan.log_roomdeal = this.log_roomdeal

        plan.plan_roomregok = this.plan_roomregok
        plan.log_roomregok = this.log_roomregok

        plan.plan_cardeal = this.plan_cardeal
        plan.log_cardeal = this.log_cardeal

        plan.plan_carregok = this.plan_carregok
        plan.log_carregok = this.log_carregok

        plan.plan_sms = this.plan_sms
        plan.log_sms = this.log_sms

        plan.plan_phone = this.plan_phone
        plan.log_phone = this.log_phone

        plan.plan_cst = this.plan_cst
        plan.log_cst = this.log_cst

        return plan
    }



}

public enum Enum_Plantype{
    PT_month(0, '月计划'),
    PT_week(1, '周计划')

    private final  Integer code
    private final  String name

    Enum_Plantype(Integer code,String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}


