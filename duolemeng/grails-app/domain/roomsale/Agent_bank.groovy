package roomsale

class Agent_bank {

    Integer         bank_id = 0                              //所属银行

    String          bank_account                                                    // 账号

    Long            bank_agentid  = 0                                                  //经纪人id

    static constraints = {
        bank_account(nullable: false, maxSize: 30)

        bank_id(nullable: false)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_bank_seq'])
    }
}

/*

public enum Enum_Bank {
    Bank_ICBC(1, '工行'),
    Bank_BOC(2, '中行'),
    Bank_ABC(3, '农行'),
    Bank_CCB(4, '建行'),

    Bank_SPD(5, '浦发银行'),
    Bank_CMB(6, '招商银行'),
    Bank_BOCS(7, '交通银行'),
    Bank_CEB(8, '光大银行'),

    Bank_HXB(9, '华夏银行'),
    Bank_IB(10, '兴业银行'),
    Bank_CMSB(11, '民生银行'),
    Bank_CCTB(12, '中信银行'),

    Bank_CGFB(13, '广发银行'),
    Bank_EB(14, '恒丰银行')

    private final Integer code
    private final String name

    Enum_Bank(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code){
        values().find { it.code == code}
    }
}


*/
