package roomsale

//
enum Enum_RoomArea {
    RA_all(0, '不限'),

    RA_less45(45, '45平米以下'),
    RA_45to60(46, '45-60平米'),
    RA_61to90(60, '61-90平米'),
    RA_91to120(90, '91-120平米'),
    RA_121to160(120, '121-160平米'),
    RA_over161(160, '161平米以上')

    private final Integer code
    private final String name

    Enum_RoomArea(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

enum Enum_RoomType {
    RT_all(0, '不限'),

    RT_single(100, '单身公寓'),
    RT_one(10, '单间'),
    RT_11(11, '一室一厅'),

    RT_21(21, '两室一厅'),
    RT_22(22, '两室两厅'),

    RT_31(31, '三室一厅'),
    RT_32(32, '三室两厅'),

    RT_42(42, '四室两厅'),
    RT_other(500, '其它')

    private final Integer code
    private final String name

    Enum_RoomType(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}


enum Enum_RoomPrice {
    RP_all(0, '不限'),

    RP_less4(1, '4000以下'),
    RP_4to6(2, '4000-6000'),

    RP_6to8(3, '6000-8000'),
    RP_8to12(4, '8000-12000'),

    RP_12to15(5, '12000-15000'),
    RP_15to20(6, '15000-20000'),

    RP_20to30(7, '20000-30000'),
    RP_30to50(8, '30000-50000'),

    RP_50to100(9, '50000-100000'),
    RP_more100(10, '100000以上'),



    private final Integer code
    private final String name

    Enum_RoomPrice(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}
