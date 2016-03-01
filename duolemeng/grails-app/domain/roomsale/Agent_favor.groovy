package roomsale

//经纪人的收藏
class Agent_favor {
    Long            favor_agentid = 0                 // 经纪人id

    Long            favor_refid = 0                    // 收藏对象id
    Integer         favor_reftype = Enum_Reftype.RT_building.code                 // 收藏对象类别

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_favor_seq'])
    }
}

