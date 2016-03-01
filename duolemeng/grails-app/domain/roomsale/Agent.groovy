package roomsale

// 经纪人
class Agent {
    Long            agent_iconid = 0                                                  //头像

    String          agent_mobile                                                      // 电话

    String          agent_address   = ""                                                 // 住址
    String          agent_usecity   = ""                                                 //使用城市

    String          agent_name                                                         // 姓名
    String          agent_idcard    = ""                                                   // 身份证
    Long            agent_idcardid = 0                                                //身份证附件

    String          agent_passwd                                                      //密码
    String          agent_salt
    String          agent_cashpwd     = ""                                               //提现密码


    Integer         agent_status = Enum_Agentstatus.AS_idcard_waiting.code                 //状态
    Long            agent_rank = 0                                                    //排名

    Long            agent_teamid = 0                                                //所属团队ID

    Long            agent_checkall = 0                                              //总天数
    Long            agent_checking = 0                                               //连续签到天数

    Long            agent_signdays = 0                                              // 打卡/签到 总天数
    Long            agent_coin = 0                                                  // 积分总数

    Date            agent_time                                                        // 注册时间

    static constraints = {
        agent_mobile(nullable: false, maxSize: 20)

        agent_address(nullable: true, maxSize: 30)
        agent_usecity(nullable: true, maxSize: 20)

        agent_name(nullable: false, maxSize: 20)
        agent_idcard(nullable: true, maxSize: 20)

        agent_passwd(nullable: false, maxSize: 50)
        agent_salt(nullable: false, maxSize: 20)
        agent_cashpwd(nullable: true, maxSize: 50)

        agent_time(nullable: true)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_seq'])
    }

    //得到客户数据统计
    private getcstsum(){

        def ret = [:]

        ret.agent_name = this.agent_name
        ret.agent_mobile = this.agent_mobile
        ret.agent_id = this.id

        ret.cst_num = Cst_basic.countByCst_agtid(this.id)
        ret.bdregyes_num = Cst_basic.countByCst_agtidAndCst_regbd(this.id, Enum_Regstatus.RS_regyes.code)
        ret.bdregok_num = Cst_basic.countByCst_agtidAndCst_regbd(this.id, Enum_Regstatus.RS_ok.code)

        ret.cbregyes_num = Cst_basic.countByCst_agtidAndCst_regcar(this.id, Enum_Regstatus.RS_regyes.code)
        ret.cbregok_num = Cst_basic.countByCst_agtidAndCst_regcar(this.id, Enum_Regstatus.RS_ok.code)

        return ret
    }

}

public enum Enum_Agentstatus{
    AS_idcard_waiting(0, '待实名认证'),
    AS_idcard_going(1, '待实名审核'),
    AS_idcard_fail(2, '实名审核失败'),

    AS_idcard_ok(10, '实名审核成功'),

    AS_team_leader(20, '团长'),
    AS_team_member(21, '团员'),

    AS_stop(99, '停用'),


    private final Integer code
    private final String name

    Enum_Agentstatus(Integer code, String name){
        this.code = code
        this.name = name
    }
}

