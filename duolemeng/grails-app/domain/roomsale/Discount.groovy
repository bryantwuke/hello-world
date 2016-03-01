package roomsale

class Discount {
    String      dc_title        // 优惠活动标题

    Date        dc_fromtime       //开始时间
    Date        dc_endtime        //结束时间

    String      dc_content = ""        //优惠内容

    Long        dc_refid = 0         // 优惠活动关联ID，对应哪个楼盘或车行，0表示平台
    Integer     dc_reftype = Enum_Reftype.RT_building.code                 // 优惠活动关联类别

    Integer     dc_type = Enum_Discounttype.DT_discount_wait.code         // 区分是优惠，还是公告

    static constraints = {
        dc_title(nullable: false, maxSize: 100)

        dc_fromtime(nullable: true)
        dc_endtime(nullable: true)

        dc_content(nullable: true)
    }

    static mapping = {
        dc_content sqlType:"text"
        id(generator: 'sequence', params: [sequence: 'Discount_seq'])
    }

}


public enum Enum_Discounttype{
    DT_discount_wait(10, '待审优惠', 0),
    DT_discount_no(11, '审核未通过的优惠', 0),
    DT_discount_ok(12, '审核通过的优惠', 0),

    DT_news_wait(20, '待审公告', 1),
    DT_news_no(21, '审核未通过的公告', 1),
    DT_news_ok(22, '审核通过的公告', 1)

    private final Integer code
    private final String name
    private final Integer type

    Enum_Discounttype(Integer code, String name, Integer type){
        this.code = code
        this.name = name
        this.type = type
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}
