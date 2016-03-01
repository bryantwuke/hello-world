package roomsale

class Bank_type {
    String                  bt_prefix                              //前面6位数据
    String                  bt_name                                //银行名称

    static constraints = {
        bt_prefix(nullable: false, maxSize: 10)
        bt_name(nullable: false, maxSize: 50)

    }

    static mapping = {
        bt_prefix(index: 'bt_prefix_Idx')
        id(generator: 'sequence', params: [sequence: 'Bank_type_seq'])
    }
}
