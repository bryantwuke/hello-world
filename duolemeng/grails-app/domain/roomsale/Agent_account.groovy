package roomsale

class Agent_account {

    Long            at_usable = 0           // 可用金额
    Long            at_fixed = 0            // 冻结金额

    Long            at_sumcash = 0          // 提现总额
    // Long            at_insum = 0            // 收入总额 = 可用金额 + 冻结金额 + 提现总额


    static constraints = {
    }

    static mapping = {
        id(generator: 'assigned')
    }

}
