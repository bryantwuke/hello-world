package roomsale

// 价格区间
enum Enum_CarPrice {
    CP_all(1, '不限'),
    CP_less3(2, '3万以下'),

    CP_3to5(3, '3-5万'),
    CP_5to8(4, '5-8万'),
    CP_8to10(5, '8-10万'),

    CP_10to15(10, '10-15万'),
    CP_15to20(15, '15-20万'),
    CP_20to30(20, '20-30万'),

    CP_30to50(30, '30-50万'),
    CP_50to100(50, '50-100万'),
    CP_over100(100, '100万以上')

    private final Integer code
    private final String name

    Enum_CarPrice(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

// 级别
enum Enum_CarLevel {
    CL_all(0, '不限'),

    CL_mini(100, '微型车'),
    CL_small(101, '小型车'),
    CL_compact(102, '紧凑型车'),
    CL_middle(103, '中型车'),
    CL_mbig(104, '中大型车'),
    CL_big(105, '大型车'),
    CL_luxury(110, '豪华车'),
    CL_sport(111, '跑车'),
    CL_mpv(112, 'MPV'),

    CL_microbus(130, '微面'),
    CL_minitruck(131, '微卡'),
    CL_lightbus(132, '轻客'),
    CL_minibus(133, '面包车'),
    CL_truck(134, '皮卡'),

    CL_allsuv(150, '全部SUV'),
    CL_smallsuv(151, '小型SUV'),
    CL_compactsuv(152, '紧凑型SUV'),
    CL_middlesuv(153, '中型SUV'),
    CL_mbigsuv(154, '中大型SUV'),
    CL_bigsuv(155, '大型SUV')

    private final Integer code
    private final String name

    Enum_CarLevel(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }

}

// 国别
enum Enum_CarCountry {
    CC_all(0, '不限'),

    CC_corp(1, '合资'),
    CC_import(2, '进口'),
    CC_own(3, '自主'),
    CC_germany(4, '德系'),
    CC_korean(5, '韩系'),
    CC_usa(6, '美系'),
    CC_euro(7, '欧系'),
    CC_japan(8, '日系')

    private final Integer code
    private final String name

    Enum_CarCountry(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

// 变速箱
enum Enum_CarGear {
    CG_all(0, '不限'),
    CG_manual(11, '手动'),
    CG_auto(12, '自动')

    private final Integer code
    private final String name

    Enum_CarGear(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

// 结构
enum Enum_CarFrame {
    CF_all(0, '不限'),
    CF_2frame(2, '两厢'),
    CF_3frame(3, '三厢'),
    CF_hatchback(10, '掀背'),
    CF_travel(11, '旅行版'),
    CF_hardtop(12, '硬顶敞篷车'),
    CF_softtop(13, '软顶敞篷车'),
    CF_hardsport(14, '硬顶跑车'),
    CF_bus(15, '客车'),
    CF_truck(16, '货车')

    private final Integer code
    private final String name

    Enum_CarFrame(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

// 排量
enum Enum_CarVolume {
    CV_all(0, '不限'),
    CV_less1(1, '1.0L及以下'),
    CF_11to16(2, '1.1 — 1.6L'),
    CF_17to20(3, '1.7 — 2.0L'),
    CF_21to25(4, '2.1 — 2.5L'),
    CF_26to30(5, '2.6 — 3.0L'),
    CF_31to40(6, '3.1 — 4.0L'),
    CF_over40(7, '4.0L以上')

    private final Integer code
    private final String name

    Enum_CarVolume(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}


// 驱动
enum Enum_CarDrive {
    CD_all(0, '不限'),
    CD_front(1, '前驱'),
    CD_back(2, '后驱'),
    CD_four(3, '四驱')

    private final Integer code
    private final String name

    Enum_CarDrive(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}


// 燃料
enum Enum_CarFuel {
    CF_all(0, '不限'),
    CF_gas(1, '汽油'),
    CF_diesel(2, '柴油'),
    CF_pure(3, '纯电动'),
    CF_oilmix(4, '油电混合'),
    CF_gasmix(5, '油气混合')

    private final Integer code
    private final String name

    Enum_CarFuel(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}

// 配置
enum Enum_CarAttach {
    CA_all(0, '不限'),
    CA_window(1, '天窗'),
    CA_autoseat(2, '电动调节座椅'),
    CA_esp(3, 'ESP'),
    CA_light(4, '氩气大灯'),
    CA_gps(5, 'GPS导航'),
    CA_speed(6, '定速巡航'),
    CA_skinseat(7, '真皮座椅'),
    CA_back(8, '倒车雷达'),
    CA_aircondition(9, '全自动空调'),
    CA_wheel(10, '多功能方向盘'),
    CA_childlock(11, '儿童锁'),
    CA_turbo(12, '涡轮增压'),
    CA_tpms(13, '胎压监测'),
    CA_autopark(14, '自动泊车'),
    CA_airclean(15, '空气净化器'),
    CA_childseat(16, '儿童座椅')

    private final Integer code
    private final String name

    Enum_CarAttach(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code) {
        values().find { it.code == code }
    }
}


