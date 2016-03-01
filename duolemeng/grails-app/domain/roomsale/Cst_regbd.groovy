package roomsale

// 客户楼盘报备
class Cst_regbd {

    Long reg_cstid = 0                                              //客户id
    Long reg_bdid = 0                                            //楼盘id

    Integer reg_status  = Enum_Regstatus.RS_wishyes.code     //状态

    static constraints = {

    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Cst_regbd_seq'])
    }
}


