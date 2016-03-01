package roomsale

class Cst_pay {

    Long        pay_cstid = 0
    Long        pay_agentid = 0

    Long        pay_money = 0
    Integer     pay_type = Enum_Reftype.RT_building.code

    Date        pay_time
    String      pay_sn

    String      pay_note = ""

    Long        pay_fileid = 0          // 付款凭据照片

    static constraints = {
        pay_time(nullable: false)
        pay_sn(nullable: false, maxSize: 20)

        pay_note(nullable: true, maxSize: 50)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_pay_seq'])
    }
}
