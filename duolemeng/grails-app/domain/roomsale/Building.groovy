package roomsale

class Building {
    String      bd_name                     //楼盘名称
    String      bd_developer             //开发商

    String      bd_city                     // 城市
    String      bd_area                     // 区域
    String      bd_address                // 地址

    String      bd_phone                   //楼盘电话
    Integer     bd_year = 70                    //产权年限

    Integer     bd_greenrate  = 0            //绿化率
    Integer     bd_volumnrate = 0            //容积率

    Date        bd_opentime               //开盘时间

    Long        bd_tag_tagid = 0                 // 楼盘标签 表Tag
    Long        bd_type_tagid  = 0                  //类别 表Tag

    Long        bd_price = 0                // 均价，以万分之一元为单位。
    Long        bd_avgcash = 0                 // 平均佣金
    Long        bd_propcost  = 0         //物业费

    String      bd_house = ""                   //在售房源

    String      bd_agenttel  = ""             //经纪人案场电话

    String      bd_feature = ""                 //项目特色
    String      bd_structure = ""               //项目结构
    String      bd_decoration = ""             //建材装修

    Integer     bd_typenum = 0              //户型图片数目
    Integer     bd_picnum = 0             //楼盘介绍图片

    Long        bd_focus = 0              //关注度
    Long        bd_view = 0               //浏览量
    Long        bd_submit = 0            //报备数

    String      bd_cashrule = ""            //佣金规则


    static constraints = {
        bd_name(nullable: false, maxSize: 20)
        bd_developer(nullable: false, maxSize: 20)

        bd_city(nullable: false, maxSize: 20)
        bd_area(nullable: false, maxSize: 20)
        bd_address(nullable: false, maxSize: 50)

        bd_phone(nullable: true, maxSize: 20)

        bd_opentime(nullable: true)
        bd_house(nullable: true, maxSize: 50)

        bd_agenttel(nullable: true, maxSize: 20)

        bd_feature(nullable: true, maxSize: 50)
        bd_structure(nullable: true, maxSize: 30)
        bd_decoration(nullable: true, maxSize: 50)

        bd_cashrule(nullable: true, maxSize: 200)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Building_seq'])
    }

}
/*
enum Enum_Buildtype{
    BT_house(0, '住宅'),
    BT_flat(1, '公寓'),
    BT_office(2, '写字楼'),
    BT_shop(3, '商铺'),
    BT_villa(4, '别墅'),
    BT_both(5, '商住两用')


    private final Integer code
    private final String name

    Enum_Buildtype(Integer code,String name){
        this.code = code
        this.name = name
    }
}
*/
