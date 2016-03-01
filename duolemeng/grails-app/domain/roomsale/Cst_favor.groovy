package roomsale

class Cst_favor {

    Long        favor_cstid = 0

    Long        favor_refid = 0
    Integer     favor_reftype = Enum_Reftype.RT_building.code


    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_favor_seq'])
    }

}
