package roomsale

class Cst_discount {
    Long            reg_cstid = 0
    Long            reg_discountid = 0

    Date            reg_time

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_discount_seq'])
    }

}
