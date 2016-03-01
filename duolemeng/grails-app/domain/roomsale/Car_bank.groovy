package roomsale

class Car_bank {
    String      cb_name                     //车行名称
    String      cb_shortname               //车行简称

    Long        cb_brand_tagid = 0               //主营品牌  关联Tag表
    Long        cb_tag_tagid = 0                 // 车行标签
    Long        cb_level_tagid = 0               //主营车级别类型

    String      cb_city                     // 城市
    String      cb_area                     // 区域
    String      cb_address                 // 地址

    String      cb_phone                   //车行电话

    Long        cb_avgcash = 0                 // 平均佣金

    Long        cb_focus = 0              //关注度
    Long        cb_view = 0               //浏览量
    Long        cb_submit = 0            //报备数

    String       cb_aftersale  = ""           // 售后
    String       cb_brief  = ""           // 简介
    String       cb_loan  = ""                // 车贷介绍
    String       cb_cashrule = ""             //佣金规则

    Long         file_num = 0            //车行图片



    static constraints = {
        cb_name(nullable: false, maxSize: 20)
        cb_shortname(nullable: false, maxSize: 8)

        cb_city(nullable: false, maxSize: 20)
        cb_area(nullable: false, maxSize: 20)
        cb_address(nullable: false, maxSize: 30)

        cb_phone(nullable: true, maxSize: 20)

        cb_aftersale(nullable: true, maxSize: 100)
        cb_brief(nullable: true, maxSize: 200)
        cb_loan(nullable: true, maxSize: 100)

        cb_cashrule(nullable: true, maxSize: 200)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Car_bank_seq'])
    }

}
