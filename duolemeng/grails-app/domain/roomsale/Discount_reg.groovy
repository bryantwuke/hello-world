package roomsale

class Discount_reg {
    Long        reg_discountid = 0
    Long        reg_agentid = 0

    String      reg_name
    String      reg_mobile
    Date        reg_time = new Date()

    String      reg_note = ""

    Integer     reg_status = Enum_DiscountRegStatus.RS_no_wait.code
    Long        reg_cstid = 0

    static constraints = {
        reg_name(nullable: false, maxSize: 15)
        reg_mobile(nullable: false, maxSize: 20)

        reg_note(nullable: true, maxSize: 64)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Discount_reg_seq'])
    }

}

public enum Enum_DiscountRegStatus{
    RS_no_wait(10, '潜在客户待处理'),
    RS_no_addin(11, '潜在客户已加入及联系'),

    RS_yes_wait(20, '现有客户待处理'),
    RS_yes_link(21, '现有客户已联系')

    private final Integer code
    private final String name

    Enum_DiscountRegStatus(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}
