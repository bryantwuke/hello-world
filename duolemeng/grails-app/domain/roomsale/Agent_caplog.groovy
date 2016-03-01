package roomsale

class Agent_caplog {
    Date            cap_time                  //时间
    Long            cap_num = 0              //金额
    Long            cap_left = 0            //剩余账号余额（可用资金）

    Integer         cap_type = Enum_Captype.CT_none.code

    Long            cap_agentid = 0

    Long            cap_cstid = 0           // 对应客户ID
    Long            cap_regid = 0           // 对应报备的ID，楼或者车，用cap_type来区分

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_caplog_seq'])
    }
}

enum Enum_Captype{
    CT_none(0, '无'),

    CT_inuse_adduser(100, '新增用户，发放可用金额'),
    CT_infixed_adduser(101, '新增用户，发放冻结金额'),
    CT_inrelease_adduser(102, '新增用户，解冻金额'),

    CT_inuse_roomok(110, '楼盘报备成功，发放可用金额'),
    CT_infixed_roomok(111, '楼盘报备成功，发放冻结金额'),
    CT_inrelease_roomok(112, '楼盘报备成功，解冻金额'),

    CT_inuse_roomdeal(120, '楼盘成交，发放可用金额'),
    CT_infixed_roomdeal(121, '楼盘成交，发放冻结金额'),
    CT_inrelease_roomdeal(122, '楼盘成交，解冻金额'),

    CT_inuse_carok(130, '车行报备成功，发放可用金额'),
    CT_infixed_carok(131, '车行报备成功，发放冻结金额'),
    CT_inrelease_carok(132, '车行报备成功，解冻金额'),

    CT_inuse_cardeal(140, '车行成交，发放可用金额'),
    CT_infixed_cardeal(141, '车行成交，发放冻结金额'),
    CT_inrelease_cardeal(142, '车行成交，解冻金额'),

    CT_out_cash(200, '提现')

    private final Integer code
    private final String name

    Enum_Captype(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code){
        values().find{ it.code == code}
    }
}
