package roomsale

class Cst_regcar {

    Long reg_cstid = 0                                              //客户id
    Long reg_carbankid = 0                                            //车行id

    Integer reg_status  = Enum_Regstatus.RS_wishyes.code     //状态

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_regcar_seq'])
    }
}
