package roomsale

class Cst_wishroom {
    Integer       room_wishlevel = Enum_Wishlevel.WL_ordinary.code                 //意向等级

    Integer       room_area = Enum_RoomArea.RA_all.code                                                       //意向面积
    Integer        room_type = Enum_RoomType.RT_all.code                                                          //意向户型

    Long          room_tagid = 0                                                            //关注卖点
    String        room_usage = ""                                                         //意向用途

    String         room_place   = ""                                                         //  意向区域
    Integer        room_bd_type = 0                                                      //意向类型
    Integer        room_price = Enum_RoomPrice.RP_all.code                             //意向面积单价

    String        room_note = ""                                                          //其它信息

    static constraints = {
        room_usage(nullable: true, maxSize: 20)
        room_note(nullable: true, maxSize: 100)

        room_place(nullable: true, maxSize: 20)
    }

    static mapping = {
        id(generator: 'assigned')
    }

}
