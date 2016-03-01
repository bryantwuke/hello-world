package roomsale

class Agent_share {

    Long            share_agentid = 0
    Long            share_discountid = 0

    Long            share_readnum = 0
    Date            share_date                   // 按周来统计

    static constraints = {
        share_date(nullable: false)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_share_seq'])
    }

}
