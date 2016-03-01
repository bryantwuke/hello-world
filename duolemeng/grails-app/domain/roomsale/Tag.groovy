package roomsale

class Tag {

    String          tag_name                        // 楼盘标签，如：学区房、地铁房、别墅等等
    Integer         tag_isbasic = 0               // 是否为混合标签，0为单标签，1为多个标签
    Integer         tag_type = Enum_Tagtype.TT_bd_type.code

    static constraints = {
        tag_name(nullable: false, maxSize: 256)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Tag_seq'])
    }

}


enum Enum_Tagtype{
    TT_bd_type(0, '楼盘类型'),
    TT_bd_tag(1, '楼盘卖点'),

    TT_car_type(10, '汽车品牌'),
    TT_car_tag(11, '车行标签'),
    TT_carlevel_tag(12, '车行主营级别'),

    private final Integer code
    private final String name

    Enum_Tagtype(Integer code,String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}
